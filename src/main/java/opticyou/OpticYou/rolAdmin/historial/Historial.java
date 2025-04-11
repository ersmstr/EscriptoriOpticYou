package opticyou.OpticYou.rolAdmin.historial;

/**
 * Autor: mramis
 */

/**
 * Classe que representa un historial mèdic associat a un client.
 *
 * <p>Conté informació sobre l'ID de l'historial i les patologies registrades.</p>
 */


public class Historial {

    /** Identificador únic de l'historial. */
    private Long idhistorial;

    /** Llista de patologies associades a l'historial. */
    private String patologies;

    /**
     * Constructor buit per defecte.
     */
    public Historial() {
    }

    /**
     * Constructor amb paràmetres.
     *
     * @param idhistorial ID de l'historial.
     * @param patologies Patologies associades.
     */
    public Historial(Long idhistorial, String patologies) {
        this.idhistorial = idhistorial;
        this.patologies = patologies;
    }

    /**
     * Retorna l'identificador de l'historial.
     *
     * @return ID de l'historial.
     */
    public Long getIdhistorial() {
        return idhistorial;
    }

    /**
     * Estableix l'identificador de l'historial.
     *
     * @param idhistorial Nou ID de l'historial.
     */
    public void setIdhistorial(Long idhistorial) {
        this.idhistorial = idhistorial;
    }

    /**
     * Retorna les patologies associades a l'historial.
     *
     * @return Patologies com a cadena de text.
     */
    public String getPatologies() {
        return patologies;
    }

    /**
     * Estableix les patologies de l'historial.
     *
     * @param patologies Nova informació de patologies.
     */
    public void setPatologies(String patologies) {
        this.patologies = patologies;
    }
}
