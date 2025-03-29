#OpticYouEsc
# GestioOptiques
## Observacions
Aquesta aplicació ha estat desenvolupada amb Java Swing i segueix el patró MVC per separar la lògica del negoci de la interfície d'usuari.
Inclou connexió amb una base de dades PostgreSQL per a la gestió de dades.
S'han utilitzat llibreries externes com FlatLaf per millorar l'estil de la interfície.

## Descripció
**GestionClientesApp** és una aplicació d'escriptori desenvolupada en Java Swing que permet gestionar les dades dels clients, les seves cites i les seves prescripcions. L'aplicació compta amb dues interfícies principals: una per al negoci i una altra per a l'administrador de l'aplicació. Es connecta a un servidor remot mitjançant Retrofit per a la gestió de dades.

## Característiques
- Gestió de clients, cites i prescripcions.
- Interfície intuïtiva desenvolupada en Java Swing.
- Connexió a servidor mitjançant Retrofit.
- Dos tipus d'usuaris: negoci i administrador.

## Patrons utilitzats

- **Java Swing** per a la interfície gràfica.
- **Retrofit** per a la comunicació amb el servidor.
- **Pgadmin/PostgreSQL** (segons el servidor backend).
- **Maven per a la gestió de dependències.

## Fitxer per connectar amb el servidor (Retrofit)
-** ApiClient 

### Prerequisits
- Tenir instal·lat **JDK 21+**.
- Tenir configurat **Maven** 
- Disposar d'accés al servidor backend.


