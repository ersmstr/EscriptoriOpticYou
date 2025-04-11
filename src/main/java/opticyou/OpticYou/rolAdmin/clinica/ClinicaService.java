package opticyou.OpticYou.rolAdmin.clinica;

import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Callback;
import java.util.List;

/**
 * Servei encarregat de gestionar les operacions relacionades amb {@link Clinica}
 * mitjançant crides a {@link ClinicaApi}.
 */

/**
 * autor mramis
 */
public class ClinicaService {

    private final ClinicaApiProvider apiProvider;

    /**
     * Constructor per defecte. Utilitza {@link RetrofitApp} com a proveïdor de l'API.
     */
    public ClinicaService() {
        this(RetrofitApp::getClinicaApi);
    }

    /**
     * Constructor personalitzat que permet injectar un {@link ClinicaApiProvider}.
     *
     * @param apiProvider Proveïdor de l'API de clíniques.
     */
    public ClinicaService(ClinicaApiProvider apiProvider) {
        this.apiProvider = apiProvider;
    }

    /**
     * Carrega totes les clíniques des del backend.
     *
     * @param token    Token JWT per autorització.
     * @param callback Callback Retrofit per gestionar la resposta amb una llista de {@link Clinica}.
     */
    public void carregarClinicas(String token, Callback<List<Clinica>> callback) {
        ClinicaApi clinicaApi = apiProvider.getClinicaApi(token);
        clinicaApi.getAllClinicas(token).enqueue(callback);
    }

    /**
     * Afegeix una nova clínica al sistema.
     *
     * @param clinica  Objecte {@link Clinica} amb les dades a guardar.
     * @param token    Token JWT per autorització.
     * @param callback Callback Retrofit per gestionar la resposta.
     */
    public void agregarClinica(Clinica clinica, String token, Callback<Void> callback) {
        ClinicaApi api = apiProvider.getClinicaApi(token);
        api.createClinica("Bearer " + token, clinica).enqueue(callback);
    }

    /**
     * Elimina una clínica donat el seu identificador.
     *
     * @param id       ID de la clínica a eliminar.
     * @param token    Token JWT per autorització.
     * @param callback Callback Retrofit per gestionar la resposta.
     */
    public void eliminarClinica(Long id, String token, Callback<Void> callback) {
        ClinicaApi api = apiProvider.getClinicaApi(token);
        api.deleteClinica(id, token).enqueue(callback);
    }

    /**
     * Actualitza les dades d'una clínica.
     *
     * @param clinica  Objecte {@link Clinica} amb les dades noves.
     * @param token    Token JWT per autorització.
     * @param callback Callback Retrofit per gestionar la resposta.
     */
    public void actualitzarClinica(Clinica clinica, String token, Callback<Void> callback) {
        ClinicaApi api = apiProvider.getClinicaApi(token);
        api.updateClinica(clinica).enqueue(callback);
    }
}
