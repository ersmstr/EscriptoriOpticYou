package opticyou.OpticYou.clients;

import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("idUsuari")
    private Long idClient;

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    @SerializedName("nom")
    private String nom;

    @SerializedName("email")
    private String email;

    @SerializedName("contrasenya")
    private String contrasenya;

    @SerializedName("rol")
    private String rol;

    @SerializedName("dataNaixament")
    private String dataNaixament;

    @SerializedName("sexe")
    private String sexe;

    @SerializedName("telefon")
    private String telefon;

    @SerializedName("clinicaId")
    private Long clinicaId;

    @SerializedName("historialId")
    private Long historialId;

    // Getters i Setters

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasenya() { return contrasenya; }
    public void setContrasenya(String contrasenya) { this.contrasenya = contrasenya; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getDataNaixament() { return dataNaixament; }
    public void setDataNaixament(String dataNaixament) { this.dataNaixament = dataNaixament; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public Long getClinicaId() { return clinicaId; }
    public void setClinicaId(Long clinicaId) { this.clinicaId = clinicaId; }

    public Long getHistorialId() { return historialId; }
    public void setHistorialId(Long historialId) { this.historialId = historialId; }
}
