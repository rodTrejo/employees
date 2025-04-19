# Employees API

**Employees API** es un servicio REST desarrollado en **Java 21** con **Spring Boot**, enfocado en la **gestión de empleados**. Utiliza **MongoDB** como base de datos, incluye reglas de validación configurables, y ofrece generación de reportes de calidad de código. Además, cuenta con una interfaz de documentación **Swagger UI**.

---

## Características principales

- CRUD completo de empleados
- Validación de edad configurable (mínima y máxima)
- Persistencia en MongoDB
- Documentación técnica integrada con Swagger
- Configuración flexible vía `application.yml` o variables de entorno

---

## Tecnologías utilizadas

| Tecnología                 | Uso principal        |
|---------------------------|----------------------|
| Java 21                   | Lenguaje base        |
| Spring Boot 3.x           | Framework principal  |
| MongoDB 6.x               | Base de datos        |
| Lombok                    | RReducción de Código Repetitivo |
| Springdoc OpenAPI         | Documentación y pruebas de endpoints |

---

## Variables de entorno necesarias

Puedes configurar las siguientes propiedades desde variables de entorno o dentro del archivo `application.yml`:

| Variable                           | Descripción                                                                                           |
|------------------------------------|-------------------------------------------------------------------------------------------------------|
| `SPRING_DATA_MONGODB_URI`          | URI de conexión a MongoDB. Incluye usuario, contraseña y host. Ejemplo: `mongodb://mongo:mongo@localhost:27017/empleados?authSource=admin` |
| `API_BASE-PATH`                    | Ruta base para exponer los endpoints de la API. Ejemplo: `/api/v1/employees`                         |
| `EMPLOYEE_AGE_MINIMUM`             | Edad mínima permitida para registrar empleados. Valor por defecto: `18`                              |
| `EMPLOYEE_AGE_MAXIMUM`             | Edad máxima permitida para registrar empleados. Valor por defecto: `99`                              |
| `EMPLOYEE_AGE_MSG`                 | Mensaje de validación de edad. Usa `{0}` y `{1}` como placeholders. Ejemplo: `Age must be between {0} and {1} years old` |
| `LOGGING_LEVEL_ROOT`               | Nivel de log general de la aplicación. Ejemplos: `INFO`, `DEBUG`, `ERROR`                            |
| `LOGGING_LEVEL_ROD_TREJO`          | Nivel de log para el paquete específico `rod.trejo`                                                  |
---

## Documentación Swagger

Una vez en ejecución, puedes acceder a la documentación interactiva en la siguiente URL:

```
http://localhost:8080/swagger-ui.html
```

---

## Reportes de calidad de código

Se puede compilar y generar reportes de calidad de código usando:
```bash
./mvnw clean verify site -Pdevelopment_reporting
```
La primera vez puede tardar varios minutos. Cuando termine, abre el archivo index.html en tu navegador desde esta ruta:
```bash
employees/target/site/index.html
```
---



### Levantar MongoDB en Docker

Si no tienes MongoDB instalado, puedes levantarlo usando Docker con el siguiente comando:

```bash
docker run -d -p 27017:27017 --name mongodb -e MONGO_INITDB_ROOT_USERNAME=<usuario> -e MONGO_INITDB_ROOT_PASSWORD=<contraseña> mongo:6.0
```

---



## Ejecución local

Para correr el proyecto localmente, asegúrate de tener MongoDB activo y usa el siguiente comando:

```bash
./mvnw spring-boot:run
```

---

## Autor

Rodrigo Trejo  
GitHub: [https://github.com/rodTrejo](https://github.com/rodTrejo)

---

## Nota adicional

Este proyecto forma parte de un sistema modular más amplio orientado a la administración interna de empleados, con un enfoque en la seguridad, validaciones robustas y buenas prácticas de desarrollo.
