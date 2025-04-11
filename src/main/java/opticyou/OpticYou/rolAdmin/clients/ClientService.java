package opticyou.OpticYou.rolAdmin.clients;

import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Callback;

import java.util.List;

/**
 * Servei que encapsula les crides a l'API de clients {@link ClientApi}.
 * Proporciona mètodes per carregar, crear, actualitzar i eliminar clients.
 */
public class ClientService {

    /**
     * Carrega la llista de clients del servidor.
     *
     * @param token    Token JWT d'autenticació.
     * @param callback Callback Retrofit per manejar la resposta amb la llista de {@link Client}.
     */
    public void carregarClients(String token, Callback<List<Client>> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.getAllClients(token).enqueue(callback);
    }

    /**
     * Crea un client nou i delega la gestió de la resposta a través d’un {@link Callback}.
     *
     * @param client   Objecte {@link Client} a crear.
     * @param token    Token JWT d'autenticació.
     * @param callback Callback Retrofit per manejar la resposta.
     */
    public void createClient(Client client, String token, Callback<Void> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.createClient(client, "Bearer " + token).enqueue(callback);
    }

    /**
     * Elimina un client pel seu ID.
     *
     * @param id       Identificador del client a eliminar.
     * @param token    Token JWT d'autenticació.
     * @param callback Callback Retrofit per manejar la resposta.
     */
    public void eliminarClient(Long id, String token, Callback<Void> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.deleteClient(id, token).enqueue(callback);
    }

    /**
     * Actualitza les dades d’un client.
     *
     * @param client   Client amb les dades actualitzades.
     * @param token    Token JWT d'autenticació.
     * @param callback Callback Retrofit per manejar la resposta.
     */
    public void actualitzarClient(Client client, String token, Callback<Void> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.updateClient(client, "Bearer " + token).enqueue(callback);
    }
}
