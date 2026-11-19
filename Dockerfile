# ==========================================
# ETAPA 1: Construcción (Build)
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS build

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# 1. Copiamos solo el pom.xml y descargamos dependencias
# (Esto se hace primero para aprovechar la caché de Docker. Si no cambias el pom, no vuelve a descargar)
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copiamos el código fuente y compilamos (saltando los tests para hacerlo más rápido)
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: Ejecución (Run)
# ==========================================
# Usamos una imagen ligera de Java 21 (JRE, no JDK)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos SOLO el .jar generado en la Etapa 1
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto (documentativo, Spring Boot usa el 8080 por defecto)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]