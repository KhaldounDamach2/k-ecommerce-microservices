FROM openjdk:17-jdk-slim

# Installiere curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere das JAR-File in das Container-Verzeichnis
COPY target/cart-service-*.jar app.jar

# Kopiere das Startskript in das Container-Verzeichnis
COPY start.sh /app/start.sh

# Stelle sicher, dass das Skript ausführbar ist
RUN chmod +x /app/start.sh

# Exponiere den Port, falls erforderlich
EXPOSE 8053

# Verwende das Startskript als EntryPoint
ENTRYPOINT ["./start.sh"]