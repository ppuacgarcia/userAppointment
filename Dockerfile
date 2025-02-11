# Dockerfile
# Este archivo define cómo construir la imagen de tu aplicación
FROM eclipse-temurin:21.0.6_7-jdk-ubi9-minimal

WORKDIR /app

# Copiar archivos necesarios para Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos de ejecución al mvnw
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline

# Copiar el código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/UserAppointment-0.0.1-SNAPSHOT.jar"]