package opticyou.OpticYou.service;

import opticyou.OpticYou.data.DiagnosticApi;
import opticyou.OpticYou.model.Diagnostic;
import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.List;

/**
 * Servei que encapsula les operacions relacionades amb la gestió de diagnòstics mèdics.
 * <p>
 * Proporciona mètodes per obtenir, crear, actualitzar i esborrar diagnòstics
 * fent ús del client {@link DiagnosticApi}.
 * </p>
 *
 * Autor: mrami
 */
public class DiagnosticService {

    /** Client API per comunicar-se amb el backend de diagnòstics. */
    private final DiagnosticApi diagnosticApi;

    /**
     * Crea una instància del servei de diagnòstics amb autenticació.
     *
     * @param token Token JWT per a l'autenticació de les peticions.
     */
    public DiagnosticService(String token) {
        this.diagnosticApi = RetrofitApp.getDiagnosticApi(token);
    }

    /**
     * Obté la llista de diagnòstics associats a un historial mèdic.
     *
     * @param historialId Identificador de l'historial mèdic.
     * @param callback    Callback per gestionar la resposta.
     */
    public void getDiagnostics(Long historialId, Callback<List<Diagnostic>> callback) {
        diagnosticApi.getDiagnosticByHistorial(historialId).enqueue(callback);
    }

    /**
     * Crea un diagnòstic nou.
     *
     * @param diagnostic Diagnòstic a crear.
     * @param callback   Callback per gestionar la resposta.
     */
    public void crearDiagnostic(Diagnostic diagnostic, Callback<Void> callback) {
        diagnosticApi.crearDiagnostic(diagnostic).enqueue(callback);
    }

    /**
     * Actualitza un diagnòstic existent.
     *
     * @param diagnostic Diagnòstic amb les dades actualitzades.
     * @param callback   Callback per gestionar la resposta.
     */
    public void actualitzarDiagnostic(Diagnostic diagnostic, Callback<Void> callback) {
        diagnosticApi.actualitzarDiagnostic(diagnostic).enqueue(callback);
    }

    /**
     * Esborra un diagnòstic pel seu identificador.
     *
     * @param idDiagnostic Identificador del diagnòstic a esborrar.
     * @param callback     Callback per gestionar la resposta.
     */
    public void esborrarDiagnostic(Long idDiagnostic, Callback<Void> callback) {
        diagnosticApi.esborrarDiagnostic(idDiagnostic).enqueue(callback);
    }
}
