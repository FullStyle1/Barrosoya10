# Barrosoya10

Base de una aplicación Java con Spring Boot. Esta primera etapa configura el proyecto y sus dependencias; las entidades, el repositorio, el servicio y sus pruebas unitarias se incorporarán en una rama de funcionalidad posterior.

## Tecnologías

- Java 21
- Spring Boot 3.5.6
- Maven
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
mvn clean verify
mvn spring-boot:run
```

La aplicación inicia con la configuración predeterminada de Spring Boot. Esta base no requiere una base de datos ni Docker.

## Estructura inicial

```text
src/
└── main/java/mx/edu/utch/barrosoya10/
    └── Barrosoya10Application.java
pruebas/
└── evidencias/
pom.xml
README.md
```

## Flujo de ramas

`main` y `developer` contienen únicamente la base inicial. El trabajo posterior debe partir de `developer` en una rama con prefijo `feat/`, por ejemplo `feat/pruebas-unitarias-servicio`. No se deben realizar cambios directamente en `main` o `developer`; los cambios de funcionalidad se integran mediante una solicitud de revisión.

## Evidencias de la práctica

Guarda en `pruebas/evidencias/` las capturas de pantalla de la ejecución de las pruebas, mostrando el comando usado y el resultado completo (pruebas ejecutadas, fallidas y estado final). Usa nombres claros, por ejemplo `mvn-test-exitoso.png`. Agrega las imágenes a Git junto con la rama de la práctica. No incluyas contraseñas, tokens ni otros datos sensibles en las capturas.
