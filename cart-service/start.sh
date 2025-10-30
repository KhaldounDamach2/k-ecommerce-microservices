#!/bin/sh

# Warten auf den Config-Server
while ! curl -s http://discovery-service:8761/actuator/health | grep "\"status\":\"UP\"" > /dev/null; do
  echo "Warte auf discovery-service..."
  sleep 5
done

# Starte die Anwendung
java -jar app.jar