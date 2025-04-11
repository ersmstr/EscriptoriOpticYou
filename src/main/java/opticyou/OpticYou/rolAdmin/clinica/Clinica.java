package opticyou.OpticYou.rolAdmin.clinica;

/**
 * Representa una clínica dins del sistema.
 * <p>
 * Inclou informació general com el nom, adreça, telèfon, horaris i email.
 */
/**
 * Autor: mramis
 */
public class Clinica {

    /** Nom de la clínica. */
    private String nom;

    /** Adreça física de la clínica. */
    private String direccio;

    /** Número de telèfon de la clínica. */
    private String telefon;

    /** Hora d'obertura de la clínica (ex: 08:00). */
    private String horari_opertura;

    /** Hora de tancament de la clínica (ex: 18:00). */
    private String horari_tancament;

    /** Correu electrònic de la clínica. */
    private String email;

    /** Identificador únic de la clínica. */
    private Long idClinica;

    /**
     * Constructor principal de la classe {@code Clinica}.
     *
     * @param nom              Nom de la clínica.
     * @param direccio         Adreça de la clínica.
     * @param telefon          Telèfon de contacte.
     * @param horari_opertura  Hora d'obertura.
     * @param horari_tancament Hora de tancament.
     * @param email            Correu electrònic.
     */
    public Clinica(String nom, String direccio, String telefon, String horari_opertura, String horari_tancament, String email) {
        this.nom = nom;
        this.direccio = direccio;
        this.telefon = telefon;
        this.horari_opertura = horari_opertura;
        this.horari_tancament = horari_tancament;
        this.email = email;
    }

    /**
     * Retorna l'ID de la clínica.
     * @return ID únic de la clínica.
     */
    public Long getIdClinica() {
        return idClinica;
    }

    /**
     * Estableix l'ID de la clínica.
     * @param idClinica Nou ID.
     */
    public void setIdClinica(Long idClinica) {
        this.idClinica = idClinica;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getHorari_opertura() {
        return horari_opertura;
    }

    public void setHorari_opertura(String horari_opertura) {
        this.horari_opertura = horari_opertura;
    }

    public String getHorari_tancament() {
        return horari_tancament;
    }

    public void setHorari_tancament(String horari_tancament) {
        this.horari_tancament = horari_tancament;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
