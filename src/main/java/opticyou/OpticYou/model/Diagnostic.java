package opticyou.OpticYou.model;

/**
 * Autor: mrami
 */
public class Diagnostic {
    public Diagnostic() {

    }

    private Long idDiagnostic;

    public Long getIdDiagnostic() {
        return idDiagnostic;
    }

    public void setIdDiagnostic(Long idDiagnostic) {
        this.idDiagnostic = idDiagnostic;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    private String descripcio;


}
