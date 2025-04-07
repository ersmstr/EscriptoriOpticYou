package opticyou.OpticYou.historial;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
/**
 * Autor: mrami
 */


public class Historial {
    private Long idhistorial;

    private String patologies;

    public Historial() {
    }

    public Historial(Long idhistorial, LocalDateTime data_creacio, String patologies) {
        this.idhistorial = idhistorial;

        this.patologies = patologies;
    }

    public Long getIdhistorial() {
        return idhistorial;
    }

    public void setIdhistorial(Long idhistorial) {
        this.idhistorial = idhistorial;
    }




    public String getPatologies() {
        return patologies;
    }

    public void setPatologies(String patologies) {
        this.patologies = patologies;
    }


}

