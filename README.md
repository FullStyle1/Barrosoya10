# Barrosoya10

Aplicación Java con Spring Boot para administrar alumnos. La capa de servicio valida y persiste registros mediante Spring Data JPA; sus pruebas unitarias usan Mockito para no iniciar Spring ni conectarse a una base de datos.

## Tecnologías

- Java 21
- Spring Boot 3.5.6
- Maven
- Spring Data JPA y H2 para ejecución local
- JUnit 5, Mockito y AssertJ, incluidos en `spring-boot-starter-test`

## Requisitos

- JDK 21
- Maven 3.9 o posterior

Verifica las versiones instaladas con:

```shell
java -version
mvn -version
```

## Compilar y ejecutar

Desde la raíz del proyecto:

```shell
mvn test
mvn clean verify
mvn spring-boot:run
```

La aplicación inicia con H2 en memoria, sin instalar una base de datos ni Docker. Las pruebas unitarias del servicio sustituyen el repositorio con Mockito y no levantan el contexto de Spring.

## Funcionalidad

```text
src/
├── main/java/mx/edu/utch/barrosoya10/
│   ├── Barrosoya10Application.java
│   └── alumno/
│       ├── Alumno.java
│       ├── AlumnoRepository.java
│       └── AlumnoService.java
└── test/java/mx/edu/utch/barrosoya10/alumno/
    └── AlumnoServiceTest.java
pruebas/
└── evidencias/
pom.xml
README.md
```

`AlumnoService` permite recuperar todos los alumnos, buscar uno por identificador y registrar alumnos nuevos. Al crear, valida nombre y correo, normaliza espacios y rechaza correos duplicados sin distinguir mayúsculas. El correo también tiene una restricción de unicidad en la entidad.

## Flujo de ramas

`main` y `developer` contienen únicamente la base inicial. El trabajo posterior debe partir de `developer` en una rama con prefijo `feat/`, por ejemplo `feat/pruebas-unitarias-servicio`. No se deben realizar cambios directamente en `main` o `developer`; los cambios de funcionalidad se integran mediante una solicitud de revisión.

## Evidencias de la práctica

Guarda en `pruebas/evidencias/` las capturas de pantalla de la ejecución de las pruebas, mostrando el comando usado y el resultado completo (pruebas ejecutadas, fallidas y estado final). Usa nombres claros, por ejemplo `mvn-test-alumno-service.png`. Agrega las imágenes a Git junto con la rama de la práctica. No incluyas contraseñas, tokens ni otros datos sensibles en las capturas.
