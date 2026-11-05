package mx.edu.utch.barrosoya10.alumno;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    public List<Alumno> findAll() {
        return alumnoRepository.findAll();
    }

    public Alumno findById(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el alumno con id " + id));
    }

    public Alumno create(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("El alumno es obligatorio");
        }
        if (alumno.getNombre() == null || alumno.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (alumno.getCorreo() == null || alumno.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        alumno.setNombre(alumno.getNombre().trim());
        alumno.setCorreo(alumno.getCorreo().trim());

        if (alumnoRepository.existsByCorreoIgnoreCase(alumno.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un alumno con ese correo");
        }

        return alumnoRepository.save(alumno);
    }
}
