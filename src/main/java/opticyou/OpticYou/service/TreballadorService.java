package opticyou.OpticYou.service;

import opticyou.OpticYou.model.Treballador;
import opticyou.OpticYou.data.TreballadorApi;
import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Callback;

import java.util.List;


public class TreballadorService {


    public void carregarTreballadors(String token, Callback<List<Treballador>> callback) {


        TreballadorApi treballadorApi = RetrofitApp.getTreballadorApi(token);
        treballadorApi.getAllTreballadors(token).enqueue(callback);
    }

    /**
     * Crea un treballador nou i delega la gestió de la resposta a través d’un {@link Callback}.
     *
     * @param treballador   Objecte {@link Treballador} a crear.
     * @param token    Token JWT d'autenticació.
     * @param callback Callback Retrofit per manejar la resposta.
     */
    public void createTreballador(Treballador treballador, String token, Callback<Void> callback) {
        TreballadorApi treballadorApi = RetrofitApp.getTreballadorApi(token);
        treballadorApi.createTreballador(treballador, "Bearer " + token).enqueue(callback);
    }

    /**
     * Elimina un treballador pel seu ID.
     *
     * @param id       Identificador del treballador a eliminar.
     * @param token    Token JWT d'autenticació.
     * @param callback Callback Retrofit per manejar la resposta.
     */
    public void eliminarTreballador(Long id, String token, Callback<Void> callback) {
        TreballadorApi treballadorApi = RetrofitApp.getTreballadorApi(token);
        treballadorApi.deleteTreballador(id, token).enqueue(callback);
    }

    /**
     * Actualitza les dades d’un treballador.
     *
     * @param treballador   Treballador amb les dades actualitzades.
     * @param token    Token JWT d'autenticació.
     * @param callback Callback Retrofit per manejar la resposta.
     */
    public void actualitzarTreballador(Treballador treballador, String token, Callback<Void> callback) {
        TreballadorApi treballadorApi = RetrofitApp.getTreballadorApi(token);
        treballadorApi.updateTreballador(treballador, "Bearer " + token).enqueue(callback);
    }
}

