package opticyou.OpticYou.model;

import com.google.gson.annotations.SerializedName;

/**
 * Representa un treballador dins del sistema de gestió d'òptiques.
 * <p>
 * Inclou informació personal, laboral i la clínica a la qual està associat.
 * Aquesta classe es fa servir per a la creació, consulta, actualització i esborrat
 * de treballadors a través dels serveis de l'aplicació.
 * </p>
 *
 * @author mrami
 */
public class Treballador {

    /** Identificador únic del treballador, compartit amb la taula d'usuaris (idUsuari). */
    @SerializedName("idUsuari")
    private Long idTreballador;

    /** Nom complet del treballador. */
    @SerializedName("nom")
    private String nom;

    /** Correu electrònic del treballador. */
    @SerializedName("email")
    private String email;

    /** Contrasenya del treballador. */
    @SerializedName("contrasenya")
    private String contrasenya;

    /** Rol del treballador dins del sistema (per exemple, ADMIN, TREBALLADOR, etc.). */
    @SerializedName("rol")
    private String rol;

    /** Especialitat professional del treballador, si aplica. */
    @SerializedName("especialitat")
    private String especialitat;

    /** Estat laboral actual del treballador (actiu, inactiu, etc.). */
    @SerializedName("estat")
    private String estat;

    /** Hora d'inici de la jornada laboral. */
    @SerializedName("iniciJornada")
    private String iniciJornada;

    /** Dies de la setmana en què el treballador té jornada laboral. */
    @SerializedName("diesJornada")
    private String diesJornada;

    /** Hora de finalització de la jornada laboral. */
    @SerializedName("fiJornada")
    private String fiJornada;

    /** Identificador de la clínica a la qual està associat el treballador. */
    @SerializedName("clinicaId")
    private Long clinicaId;

    /**
     * Constructor buit requerit per a frameworks o per inicialitzacions manuals.
     */
    public Treballador() {}

    // Getters i setters

    /** @return l'identificador únic del treballador. */
    public Long getIdTreballador() { return idTreballador; }

    /** @param idTreballador l'identificador únic a assignar al treballador. */
    public void setIdTreballador(Long idTreballador) { this.idTreballador = idTreballador; }

    /** @return el nom del treballador. */
    public String getNom() { return nom; }

    /** @param nom el nom a assignar al treballador. */
    public void setNom(String nom) { this.nom = nom; }

    /** @return el correu electrònic del treballador. */
    public String getEmail() { return email; }

    /** @param email el correu electrònic a assignar al treballador. */
    public void setEmail(String email) { this.email = email; }

    /** @return la contrasenya del treballador. */
    public String getContrasenya() { return contrasenya; }

    /** @param contrasenya la contrasenya a assignar al treballador. */
    public void setContrasenya(String contrasenya) { this.contrasenya = contrasenya; }

    /** @return el rol del treballador. */
    public String getRol() { return rol; }

    /** @param rol el rol a assignar al treballador. */
    public void setRol(String rol) { this.rol = rol; }

    /** @return l'especialitat del treballador. */
    public String getEspecialitat() { return especialitat; }

    /** @param especialitat l'especialitat a assignar al treballador. */
    public void setEspecialitat(String especialitat) { this.especialitat = especialitat; }

    /** @return l'estat laboral del treballador. */
    public String getEstat() { return estat; }

    /** @param estat l'estat laboral a assignar al treballador. */
    public void setEstat(String estat) { this.estat = estat; }

    /** @return l'hora d'inici de la jornada laboral. */
    public String getIniciJornada() { return iniciJornada; }

    /** @param iniciJornada l'hora d'inici a assignar a la jornada laboral. */
    public void setIniciJornada(String iniciJornada) { this.iniciJornada = iniciJornada; }

    /** @return els dies de la jornada laboral. */
    public String getDiesJornada() { return diesJornada; }

    /** @param diesJornada els dies a assignar a la jornada laboral. */
    public void setDiesJornada(String diesJornada) { this.diesJornada = diesJornada; }

    /** @return l'hora de finalització de la jornada laboral. */
    public String getFiJornada() { return fiJornada; }

    /** @param fiJornada l'hora de finalització a assignar a la jornada laboral. */
    public void setFiJornada(String fiJornada) { this.fiJornada = fiJornada; }

    /** @return l'identificador de la clínica associada. */
    public Long getClinicaId() { return clinicaId; }

    /** @param clinicaId l'identificador de la clínica a assignar. */
    public void setClinicaId(Long clinicaId) { this.clinicaId = clinicaId; }
}
