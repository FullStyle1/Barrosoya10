package mx.edu.utch.barrosoya10.alumno;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    boolean existsByCorreoIgnoreCase(String correo);
}
