# k-ecommerce-microservices

e-commerce-backend



\# Microservices E-Commerce-backend



\## Quick Start

1\. Clone this repo: `git clone \[your-repo-url]`

2\. Copy `.env.example` to `.env` and set your passwords

3\. Run `docker-compose up`  docker-compose.yml ist in k-ecommerceApp folder

4\. Access services:

&nbsp;  -Config Server

&nbsp;  - Eureka Dashboard: http://localhost:8761

&nbsp;  - PGAdmin:          http://localhost:5050/browser/

&nbsp;  - Product Service:  http://localhost:8052/product-service

&nbsp;  - Customer Service: http://localhost:8051/customer-service

&nbsp;  - Cart service:     http://localhost:8053/cart-service

&nbsp;  - Order service:    http://localhost:8054/order-service









\## 📁 Excel-Datei-Upload via Postman - Wichtige Hinweise



\### Schritt-für-Schritt Anleitung:



1\. \*\*Postman öffnen\*\* und POST-Request erstellen:

&nbsp;   POST http://localhost:8052/product-service/import-products



2\. \*\*Body-Tab auswählen\*\* und folgende Einstellungen wählen:

\- ✅ \*\*form-data\*\*

\- ✅ \*\*Key\*\*: `file` (Typ: File)

\- ✅ \*\*Value\*\*: Datei auswählen über "Select Files"



3\. \*\*Datei-Pfad beachten\*\*:

\- Die Excel-Datei muss sich in diesem Ordner befinden:

&nbsp; k-ecommerce-microservices\\k-ecommerceApp\\uploads\\



4\. \*\*WICHTIG - Postman Cloud Upload\*\*:

\- Nach Klick auf "Select Files" zeigt Postman:

&nbsp; This file is not in your active synced folder. Upload to cloud?

\- ✅ \*\*"Upload to Cloud" MUSS angeklickt werden!\*\*

\- Die Datei wird temporär in Postman Cloud hochgeladen für den Request



5\. \*\*Request abschicken\*\* - Die Datei wird an Ihre Microservice-Anwendung gesendet



\### Wichtig:

\- Nur .xlsx Dateien werden akzeptiert

\- "Upload to Cloud" ist erforderlich damit Postman die Datei senden kann

\- Die Datei wird nur temporär hochgeladen und bleibt lokal gespeichert











\### Import-Funktionalität:



\*\*Beim Hochladen einer .xlsx Datei geschieht Folgendes:\*\*



1\. \*\*Automatische Konvertierung:\*\*

&nbsp;  - Die Excel-Datei (.xlsx) wird automatisch in CSV umgewandelt

&nbsp;  - Das System verarbeitet die Daten im CSV-Format



2\. \*\*Datenbank-Import:\*\*

&nbsp;  - Die konvertierten Daten werden in die Produkt-Datenbank eingefügt

&nbsp;  - Produkte und Varianten werden automatisch erstellt











\## 🗄️ Database Zugriff mit PGAdmin



\### Wichtiger Hinweis:



\*\*Für PGAdmin müssen Sie die Datenbank-Verbindungen MANUELL einrichten:\*\*



1\. \*\*PGAdmin öffnen\*\*: http://localhost:5050

2\. \*\*Mit Ihren .env Passwörtern anmelden\*\*

3\. \*\*Server-Verbindung erstellen\*\*:

&nbsp;  - Rechtsklick auf "Servers" → "Register" → "Server"

&nbsp;  - \*\*Name\*\*: Beliebiger Name (z.B. "Product-DB")

&nbsp;  - \*\*Host\*\*: `product-db` (oder `customer-db`, `order-db`, `cart-db`)

&nbsp;  - \*\*Port\*\*: `5432`

&nbsp;  - \*\*Username\*\*: Ihr DB\_USER aus .env

&nbsp;  - \*\*Password\*\*: Ihr DB\_PASSWORD aus .env



\### Alternative ohne PGAdmin:



\*\*Sie können die APIs direkt via Postman nutzen:\*\*

\- Die Daten sind in den Datenbanken vorhanden

\- Alle Controller-Endpoints sind verfügbar

\- PGAdmin ist optional für direkte Datenbank-Abfragen



\### Verfügbare Datenbanken:

\- `kproductdb` - Product Service

\- `kcustomerdb` - Customer Service  

\- `korderdb` - Order Service

\- `kcartdb` - Cart Service



\*\*Jede benötigt eine separate Server-Verbindung in PGAdmin!\*\*








\## 👤 Wichtiger Hinweis für Bestellungen und Warenkorb



\### Benutzer erstellen erforderlich:



\*\*Bevor Sie Bestellungen aufgeben oder Produkte in den Warenkorb legen können, müssen Sie mindestens EINEN Kunden erstellen:\*\*



1\. \*\*Kunden erstellen via Postman:\*\*





