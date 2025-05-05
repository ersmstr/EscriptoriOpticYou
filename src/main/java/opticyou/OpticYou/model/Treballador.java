package opticyou.OpticYou.model;

import com.google.gson.annotations.SerializedName;

/**
 * Autor: mrami
 */
public class Treballador {
    /** Identificador únic del treballador, compartit amb la taula d'usuaris (idUsuari). */
    @SerializedName("idUsuari")
    private Long idTreballador;

    @SerializedName("nom")
    private String nom;

    @SerializedName("email")
    private String email;

    @SerializedName("contrasenya")
    private String contrasenya;

    @SerializedName("rol")
    private String rol;

    @SerializedName("especialitat")
    private String especialitat;

    @SerializedName("estat")
    private String estat;

    @SerializedName("iniciJornada")
    private String iniciJornada;

    @SerializedName("diesJornada")
    private String diesJornada;

    @SerializedName("fiJornada")
    private String fiJornada;

    @SerializedName("clinicaId")
    private Long clinicaId;



    public Treballador() {
        // Constructor buit per a ús amb formularis o frameworks
    }




    public Long getIdTreballador() {
        return idTreballador;
    }

    public void setIdTreballador(Long idTreballador) {
        this.idTreballador = idTreballador;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public String getEspecialitat() {
        return especialitat;
    }

    public void setEspecialitat(String especialitat) {
        this.especialitat = especialitat;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIniciJornada() {
        return iniciJornada;
    }

    public void setIniciJornada(String iniciJornada) {
        this.iniciJornada = iniciJornada;
    }

    public String getDiesJornada() {
        return diesJornada;
    }

    public void setDiesJornada(String diesJornada) {
        this.diesJornada = diesJornada;
    }

    public String getFiJornada() {
        return fiJornada;
    }

    public void setFiJornada(String fiJornada) {
        this.fiJornada = fiJornada;
    }

    public Long getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(Long clinicaId) {
        this.clinicaId = clinicaId;
    }


}
