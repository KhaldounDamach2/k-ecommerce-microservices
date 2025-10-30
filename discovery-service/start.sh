#!/bin/sh

# Warten auf den Config-Server
while ! curl -s http://config-server:8888/actuator/health | grep "\"status\":\"UP\"" > /dev/null; do
  echo "Warte auf config-server..."
  sleep 5
done

# Starte die Anwendung
java -jar app.jar