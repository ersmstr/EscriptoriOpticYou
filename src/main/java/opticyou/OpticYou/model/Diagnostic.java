package opticyou.OpticYou.model;

import com.google.gson.annotations.SerializedName;

/**
 * Representa un diagnòstic mèdic associat a un historial clínic.
 * <p>
 * Inclou informació com l'identificador, la descripció, la data i l'historial al qual està vinculat.
 * Aquesta classe s'utilitza per crear, consultar, actualitzar i esborrar diagnòstics mitjançant l'API.
 * </p>
 *
 * @author mrami
 */
public class Diagnostic {

    /** Identificador únic del diagnòstic. */
    @SerializedName("iddiagnostic")
    private Long idDiagnostic;

    /** Descripció del diagnòstic mèdic. */
    @SerializedName("descripcio")
    private String descripcio;

    /** Data associada al diagnòstic en format ISO-8601. */
    @SerializedName("date")
    private String date;

    /** Identificador de l'historial mèdic associat al diagnòstic. */
    @SerializedName("historialId")
    private Long historialId;

    /**
     * Constructor buit per ús amb frameworks o inicialitzacions manuals.
     */
    public Diagnostic() {}

    /**
     * Constructor complet per crear un diagnòstic amb totes les seves propietats.
     *
     * @param idDiagnostic Identificador del diagnòstic.
     * @param descripcio   Descripció del diagnòstic.
     * @param date         Data del diagnòstic en format ISO-8601.
     * @param historialId  Identificador de l'historial associat.
     */
    public Diagnostic(Long idDiagnostic, String descripcio, String date, Long historialId) {
        this.idDiagnostic = idDiagnostic;
        this.descripcio = descripcio;
        this.date = date;
        this.historialId = historialId;
    }

    /** @return l'identificador del diagnòstic. */
    public Long getIdDiagnostic() { return idDiagnostic; }

    /** @param idDiagnostic l'identificador a assignar al diagnòstic. */
    public void setIdDiagnostic(Long idDiagnostic) { this.idDiagnostic = idDiagnostic; }

    /** @return la descripció del diagnòstic. */
    public String getDescripcio() { return descripcio; }

    /** @param descripcio la descripció a assignar al diagnòstic. */
    public void setDescripcio(String descripcio) { this.descripcio = descripcio; }

    /** @return la data del diagnòstic en format ISO-8601. */
    public String getDate() { return date; }

    /** @param date la data a assignar al diagnòstic. */
    public void setDate(String date) { this.date = date; }

    /** @return l'identificador de l'historial associat. */
    public Long getHistorialId() { return historialId; }

    /** @param historialId l'identificador de l'historial a assignar. */
    public void setHistorialId(Long historialId) { this.historialId = historialId; }
}
