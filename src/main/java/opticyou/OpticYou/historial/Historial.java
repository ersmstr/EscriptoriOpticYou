package opticyou.OpticYou.historial;
import java.time.LocalDateTime;
/**
 * Autor: mrami
 */


public class Historial {
    private Long idhistorial;
    private String data_creacio;
    private String patologies;

    public Historial() {
    }

    public Historial(Long idhistorial, String data_creacio, String patologies) {
        this.idhistorial = idhistorial;
        this.data_creacio = data_creacio;
        this.patologies = patologies;
    }

    public Long getIdhistorial() {
        return idhistorial;
    }

    public void setIdhistorial(Long idhistorial) {
        this.idhistorial = idhistorial;
    }

    public String getData_creacio() {
        return data_creacio;
    }

    public void setData_creacio(String data_creacio) {
        this.data_creacio = data_creacio;
    }

    public String getPatologies() {
        return patologies;
    }

    public void setPatologies(String patologies) {
        this.patologies = patologies;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "idhistorial=" + idhistorial +
                ", data_creacio='" + data_creacio + '\'' +
                ", patologies='" + patologies + '\'' +
                '}';
    }
}

