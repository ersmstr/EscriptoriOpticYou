package opticyou.OpticYou.rolAdmin.diagnostic;

import opticyou.OpticYou.model.Diagnostic;
import opticyou.OpticYou.service.DiagnosticService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Controlador per gestionar diagnòstics des de la interfície gràfica.
 * <p>
 * Permet carregar, crear, actualitzar i esborrar diagnòstics, mostrant missatges
 * d'informació o error a l'usuari mitjançant finestres de diàleg.
 * </p>
 *
 * @author mrami
 */
public class DiagnosticController {

    /** Servei que proporciona l'accés a les operacions de diagnòstic. */
    private final DiagnosticService diagnosticService;

    /** Component gràfic pare per mostrar missatges a l'usuari. */
    private final Component parentComponent;

    /**
     * Crea un controlador de diagnòstics.
     *
     * @param token  Token JWT per a l'autenticació.
     * @param parent Component gràfic pare per mostrar missatges.
     */
    public DiagnosticController(String token, Component parent) {
        this.diagnosticService = new DiagnosticService(token);
        this.parentComponent = parent;
    }

    /**
     * Carrega la llista de diagnòstics associats a un historial mèdic.
     *
     * @param historialId Identificador de l'historial mèdic.
     * @param callback    Callback per rebre la llista de diagnòstics.
     */
    public void carregarDiagnostic(Long historialId, DiagnosticListCallback callback) {
        diagnosticService.getDiagnostics(historialId, new Callback<List<Diagnostic>>() {
            @Override
            public void onResponse(Call<List<Diagnostic>> call, Response<List<Diagnostic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else {
                    showError("No s'ha pogut obtenir la llista de diagnòstics.", response);
                    callback.onResult(List.of());
                }
            }

            @Override
            public void onFailure(Call<List<Diagnostic>> call, Throwable t) {
                showConnectionError(t);
                callback.onResult(List.of());
            }
        });
    }

    /**
     * Crea un diagnòstic nou.
     *
     * @param diagnostic Objecte {@link Diagnostic} a crear.
     * @param onSuccess  Acció a executar si la creació té èxit.
     */
    public void crearDiagnostic(Diagnostic diagnostic, Runnable onSuccess) {
        diagnosticService.crearDiagnostic(diagnostic, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(parentComponent, "Diagnòstic creat correctament.");
                    onSuccess.run();
                } else {
                    showError("Error creant el diagnòstic.", response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showConnectionError(t);
            }
        });
    }

    /**
     * Actualitza un diagnòstic existent.
     *
     * @param diagnostic Objecte {@link Diagnostic} amb les dades actualitzades.
     */
    public void actualitzarDiagnostic(Diagnostic diagnostic) {
        diagnosticService.actualitzarDiagnostic(diagnostic, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(parentComponent, "Diagnòstic actualitzat correctament.");
                } else {
                    showError("Error actualitzant el diagnòstic.", response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showConnectionError(t);
            }
        });
    }

    /**
     * Esborra un diagnòstic pel seu identificador.
     *
     * @param idDiagnostic Identificador del diagnòstic a esborrar.
     */
    public void esborrarDiagnostic(Long idDiagnostic) {
        diagnosticService.esborrarDiagnostic(idDiagnostic, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(parentComponent, "Diagnòstic esborrat correctament.");
                } else {
                    showError("Error esborrant el diagnòstic.", response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showConnectionError(t);
            }
        });
    }

    /**
     * Interfície funcional per rebre la llista de diagnòstics.
     */
    public interface DiagnosticListCallback {
        /**
         * Mètode cridat quan es rep la llista de diagnòstics.
         *
         * @param diagnostics Llista de diagnòstics recuperats.
         */
        void onResult(List<Diagnostic> diagnostics);
    }

    /**
     * Mostra un missatge d'error detallat amb informació del {@link Response}.
     *
     * @param message  Missatge base a mostrar.
     * @param response Resposta HTTP que conté el codi i el cos d'error.
     */
    private void showError(String message, Response<?> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Sense cos d'error";
            JOptionPane.showMessageDialog(parentComponent, message + "\nCodi: " + response.code() + "\nDetall: " + errorBody);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentComponent, message + "\nCodi: " + response.code() + "\nError desconegut.");
        }
    }

    /**
     * Mostra un missatge d'error de connexió.
     *
     * @param t Excepció que descriu l'error de connexió.
     */
    private void showConnectionError(Throwable t) {
        JOptionPane.showMessageDialog(parentComponent, "Error de connexió: " + t.getMessage());
    }
}
