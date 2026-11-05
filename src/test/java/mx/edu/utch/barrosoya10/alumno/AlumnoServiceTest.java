package mx.edu.utch.barrosoya10.alumno;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    @Test
    void findAllReturnsRepositoryResults() {
        Alumno ana = alumno(1L, "Ana López", "ana@ejemplo.mx");
        Alumno luis = alumno(2L, "Luis Pérez", "luis@ejemplo.mx");
        when(alumnoRepository.findAll()).thenReturn(List.of(ana, luis));

        List<Alumno> result = alumnoService.findAll();

        assertThat(result).containsExactly(ana, luis)
                .extracting(Alumno::getNombre, Alumno::getCorreo)
                .containsExactly(
                        org.assertj.core.groups.Tuple.tuple("Ana López", "ana@ejemplo.mx"),
                        org.assertj.core.groups.Tuple.tuple("Luis Pérez", "luis@ejemplo.mx"));
        verify(alumnoRepository).findAll();
    }

    @Test
    void createSavesAndReturnsAlumnoWithNormalizedValues() {
        Alumno input = new Alumno("  Ana López  ", "  ANA@ejemplo.mx ");
        Alumno saved = alumno(1L, "Ana López", "ANA@ejemplo.mx");
        when(alumnoRepository.existsByCorreoIgnoreCase("ANA@ejemplo.mx")).thenReturn(false);
        when(alumnoRepository.save(input)).thenReturn(saved);

        Alumno result = alumnoService.create(input);

        assertThat(result).isSameAs(saved);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Ana López");
        assertThat(result.getCorreo()).isEqualTo("ANA@ejemplo.mx");
        ArgumentCaptor<Alumno> captor = ArgumentCaptor.forClass(Alumno.class);
        verify(alumnoRepository).save(captor.capture());
        assertThat(captor.getValue().getNombre()).isEqualTo("Ana López");
        assertThat(captor.getValue().getCorreo()).isEqualTo("ANA@ejemplo.mx");
    }

    @Test
    void createRejectsNullAlumno() {
        assertThatThrownBy(() -> alumnoService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El alumno es obligatorio");

        verify(alumnoRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void createRejectsBlankName() {
        assertThatThrownBy(() -> alumnoService.create(new Alumno("  ", "ana@ejemplo.mx")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre es obligatorio");

        verify(alumnoRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void createRejectsNullName() {
        assertThatThrownBy(() -> alumnoService.create(new Alumno(null, "ana@ejemplo.mx")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre es obligatorio");

        verify(alumnoRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void createRejectsBlankEmail() {
        assertThatThrownBy(() -> alumnoService.create(new Alumno("Ana López", "  ")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El correo es obligatorio");

        verify(alumnoRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void createRejectsNullEmail() {
        assertThatThrownBy(() -> alumnoService.create(new Alumno("Ana López", null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El correo es obligatorio");

        verify(alumnoRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void createRejectsDuplicateEmail() {
        Alumno input = new Alumno("Ana López", "ana@ejemplo.mx");
        when(alumnoRepository.existsByCorreoIgnoreCase("ana@ejemplo.mx")).thenReturn(true);

        assertThatThrownBy(() -> alumnoService.create(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ya existe un alumno con ese correo");

        verify(alumnoRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void findByIdReturnsMatchingAlumno() {
        Alumno expected = alumno(1L, "Ana López", "ana@ejemplo.mx");
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(expected));

        Alumno result = alumnoService.findById(1L);

        assertThat(result).isSameAs(expected);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Ana López");
        assertThat(result.getCorreo()).isEqualTo("ana@ejemplo.mx");
        verify(alumnoRepository).findById(1L);
    }

    @Test
    void findByIdThrowsWhenAlumnoDoesNotExist() {
        when(alumnoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alumnoService.findById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No se encontró el alumno con id 99");
    }

    private Alumno alumno(Long id, String nombre, String correo) {
        Alumno alumno = new Alumno(nombre, correo);
        alumno.setId(id);
        return alumno;
    }
}
