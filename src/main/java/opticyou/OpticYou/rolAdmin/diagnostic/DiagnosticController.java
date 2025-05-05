package opticyou.OpticYou.rolAdmin.diagnostic;
import opticyou.OpticYou.service.DiagnosticService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;

/**
 * Autor: mrami
 */
public class DiagnosticController {

    private final DiagnosticService diagnosticService;
    private final Component parentComponent; // pot ser la pantalla que l’invoca, per mostrar JOptionPane
    private final String token;


    public DiagnosticController(String token, Component parent) {
        this.diagnosticService = new DiagnosticService();
        this.token = token;
        this.parentComponent = parent;
    }

    public void carregarDiagnostic(Long historialId, DiagnosticCallback callback) {
        diagnosticService.getDiagnostic(historialId, token, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body());
                } else {
                    JOptionPane.showMessageDialog(parentComponent, "No s'ha pogut obtenir el diagnòstic.");
                    callback.onResult(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                JOptionPane.showMessageDialog(parentComponent, "Error de connexió: " + t.getMessage());
                callback.onResult(null);
            }
        });
    }

    public void guardarDiagnostic(Long historialId, String text) {
        diagnosticService.guardarDiagnostic(historialId, text, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    JOptionPane.showMessageDialog(parentComponent, "Error guardant diagnòstic.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(parentComponent, "Error de connexió: " + t.getMessage());
            }
        });
    }

    public void esborrarDiagnostic(Long historialId) {
        diagnosticService.esborrarDiagnostic(historialId, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    JOptionPane.showMessageDialog(parentComponent, "Error esborrant diagnòstic.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(parentComponent, "Error de connexió: " + t.getMessage());
            }
        });
    }

    public interface DiagnosticCallback {
        void onResult(String diagnosticText);
    }
}
