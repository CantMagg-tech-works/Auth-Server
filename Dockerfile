# Usa la imagen base de Maven para compilar la aplicación
FROM maven:3.8.4-openjdk-17 AS build

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia los archivos de tu proyecto al contenedor
COPY pom.xml .
COPY src ./src

# Construye el proyecto y empaqueta el archivo JAR
RUN mvn clean package

# Usa una imagen más ligera para el contenedor final
FROM eclipse-temurin:21

# Crea un directorio para tu aplicación
RUN mkdir /opt/app

# Copia el archivo JAR desde la fase de construcción
COPY --from=build /app/target/auth-server-0.0.1-SNAPSHOT.jar /opt/app/auth-server.jar

# Exponer el puerto 9000 para la aplicación
EXPOSE 9000

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/opt/app/auth-server.jar"]

