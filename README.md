# Proyecto TTPS 2024 Grupo 15

## Descripción

Este proyecto es parte del trabajo práctico para la materia TTPS 2024. Consiste en un sistema de gestión de menús y comidas para un buffet.

## Stack Tecnológico

- **Java**: Lenguaje de programación principal.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.
- **Jakarta Persistence (JPA)**: API para la persistencia de datos.
- **MySQL**: Base de datos utilizada.
- **IntelliJ IDEA**: Entorno de desarrollo integrado (IDE).

## Estructura del Proyecto

- `src/main/java`: Contiene el código fuente de la aplicación.
- `src/main/resources`: Contiene los archivos de configuración y recursos estáticos.
- `src/test/java`: Contiene las pruebas unitarias y de integración.

## Requisitos Previos

- **Java 11** o superior
- **Maven 3.6** o superior
- **IntelliJ IDEA** (recomendado)

## Configuración del Entorno

1. **Clonar el repositorio**:
    ```bash
    git clone https://github.com/FedericoCametho/ttps2024grupo15.git
    cd ttps2024grupo15
    ```

2. **Importar el proyecto en IntelliJ IDEA**:
    - Abrir IntelliJ IDEA.
    - Seleccionar "Open" y elegir la carpeta del proyecto clonado.
    - IntelliJ IDEA detectará automáticamente el proyecto Maven y descargará las dependencias necesarias.

3. **Configurar la base de datos MySQL**:
    - Crear una base de datos en MySQL:
        ```sql
        CREATE DATABASE ttps2024grupo15;
        ```
   - Configurar las credenciales de la base de datos en el archivo `src/main/resources/META-INF/persistence.xml`:
       ```xml
       <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/ttps2024grupo15"/>
       <property name="javax.persistence.jdbc.user" value="tu_usuario"/>
       <property name="javax.persistence.jdbc.password" value="tu_contraseña"/>
       <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
       ```

## Compilar y Ejecutar Pruebas

1. **Compilar el proyecto**:
    ```bash
    mvn clean install
    ```

2. **Ejecutar las pruebas unitarias**:
    ```bash
    mvn test
    ```

## Contribuciones

1. Hacer un fork del repositorio.
2. Crear una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realizar los cambios y hacer commit (`git commit -am 'Agregar nueva funcionalidad'`).
4. Hacer push a la rama (`git push origin feature/nueva-funcionalidad`).
5. Crear un Pull Request.

## Integrantes
    - Cametho Federico
    - Carrera Ignacio
    - Castillo Franco