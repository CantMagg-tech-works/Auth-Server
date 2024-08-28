# Usa la imagen base Eclipse Temurin para Java 21
FROM eclipse-temurin:21

# Crea un directorio para tu aplicación en el contenedor
RUN mkdir /opt/app

# Copia el archivo JAR de tu proyecto en el contenedor
COPY target/auth-server-0.0.1-SNAPSHOT.jar /opt/app/auth-server.jar

# Exponer el puerto 9000 para la aplicación
EXPOSE 9000

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/opt/app/auth-server.jar"]

