# OpticYouEsc – Gestió d'Òptiques

## 📝 Descripció
**OpticYouEsc** és una aplicació d'escriptori desenvolupada amb **Java Swing** que permet gestionar òptiques mitjançant una interfície gràfica moderna i funcional. Utilitza el patró **MVC** per separar la lògica de negoci de la interfície d'usuari i es comunica amb un servidor backend mitjançant **Retrofit**.

L’aplicació admet dos perfils d’usuari:
- **Administrador**: pot gestionar clients, treballadors, diagnòstics i més.
- **Treballador**: accés limitat a les funcionalitats relacionades amb la seva clínica.

## 🛠️ Tecnologies i biblioteques

- **Java 21+** – Llenguatge principal.
- **Java Swing** – Interfície gràfica.
- **Retrofit** – Client HTTP per a comunicació amb l'API REST.
- **PostgreSQL / PgAdmin** – Base de dades utilitzada al backend.
- **FlatLaf** – Llibreria per estil modern a Swing.
- **Maven** – Gestor de projectes i dependències.

## ✅ Característiques principals

- Gestió de **clients**, **cites**, **diagnòstics** i **treballadors**.
- **Pantalles adaptades** al rol de l'usuari.
- **Connexió segura** amb token JWT mitjançant Retrofit.
- **Actualització dinàmica** de la interfície segons les accions.
- **Modularitat** i mantenibilitat gràcies al patró MVC.

## 📁 Estructura del projecte

src/
├── model/ # Classes de domini (Client, Treballador, etc.)
├── service/ # Serveis que encapsulen les crides Retrofit
├── data/ # Interfícies API (Retrofit)
├── rolAdmin/ # Pantalles i controladors per administradors
├── rolTreballador/ # Pantalles per usuaris treballadors
├── auth/ # Login i gestió de token
├── main/ # Punt d’entrada de l'aplicació


## ⚙️ Requisits previs

- **JDK 21** o superior
- **Maven** instal·lat i configurat
- Accés al **servidor backend** (en funcionament)

## 🚀 Execució

1. Clona el repositori:
   ```bash
   git clone https://github.com/ersmstr/EscriptoriOpticYou.git
git   cd OpticYouEsc
