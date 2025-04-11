package opticyou.OpticYou.model;

import com.google.gson.annotations.SerializedName;

/**
 * Classe que representa un client dins l'aplicació.
 *
 * Conté informació personal del client i la seva associació amb una clínica i un historial mèdic
 */

/**
 * autor mramis
 */

public class Client {

    /** Identificador únic del client, compartit amb la taula d'usuaris (idUsuari). */
    @SerializedName("idUsuari")
    private Long idClient;

    /** Nom complet del client. */
    @SerializedName("nom")
    private String nom;

    /** Correu electrònic del client. */
    @SerializedName("email")
    private String email;

    /** Contrasenya encriptada o codificada del client. */
    @SerializedName("contrasenya")
    private String contrasenya;

    /** Rol del client dins l'aplicació (ex: CLIENT, ADMIN, etc.). */
    @SerializedName("rol")
    private String rol;

    /** Data de naixement del client en format ISO (ex: yyyy-MM-dd). */
    @SerializedName("dataNaixament")
    private String dataNaixament;

    /** Sexe del client (ex: M, F, altre...). */
    @SerializedName("sexe")
    private String sexe;

    /** Número de telèfon del client. */
    @SerializedName("telefon")
    private String telefon;

    /** ID de la clínica associada al client. */
    @SerializedName("clinicaId")
    private Long clinicaId;

    /** ID de l'historial mèdic associat al client. */
    @SerializedName("historialId")
    private Long historialId;

    // Getters i Setters

    /**
     * Retorna l'ID del Client.
     * @return ID del Client.
     */
    public Long getIdClient() {
        return idClient;
    }

    /**
     * Estableix l'ID del client.
     * @param idClient Nou ID del Client.
     */
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    /**
     * Retorna el nom del client.
     * @return Nom del client.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom del client.
     * @param nom Nom a establir.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retorna l'email del client.
     * @return Email del client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Estableix l'email del client.
     * @param email Nou email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna la contrasenya del client.
     * @return Contrasenya del client.
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     * Estableix la contrasenya del client.
     * @param contrasenya Nova contrasenya.
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Retorna el rol del client.
     * @return Rol del client.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Estableix el rol del client.
     * @param rol Nou rol.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Retorna la data de naixement del client.
     * @return Data de naixement en format text.
     */
    public String getDataNaixament() {
        return dataNaixament;
    }

    /**
     * Estableix la data de naixement del client.
     * @param dataNaixament Nova data de naixement.
     */
    public void setDataNaixament(String dataNaixament) {
        this.dataNaixament = dataNaixament;
    }

    /**
     * Retorna el sexe del client.
     * @return Sexe del client.
     */
    public String getSexe() {
        return sexe;
    }

    /**
     * Estableix el sexe del client.
     * @param sexe Nou sexe.
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    /**
     * Retorna el número de telèfon del client.
     * @return Telèfon del client.
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Estableix el número de telèfon del client.
     * @param telefon Nou número de telèfon.
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Retorna l'ID de la clínica associada.
     * @return ID de la clínica.
     */
    public Long getClinicaId() {
        return clinicaId;
    }

    /**
     * Estableix l'ID de la clínica associada.
     * @param clinicaId Nou ID de clínica.
     */
    public void setClinicaId(Long clinicaId) {
        this.clinicaId = clinicaId;
    }

    /**
     * Retorna l'ID de l'historial associat.
     * @return ID de l'historial.
     */
    public Long getHistorialId() {
        return historialId;
    }

    /**
     * Estableix l'ID de l'historial associat.
     * @param historialId Nou ID d'historial.
     */
    public void setHistorialId(Long historialId) {
        this.historialId = historialId;
    }
}
