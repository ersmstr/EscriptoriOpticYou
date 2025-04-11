package opticyou.OpticYou.historial;

import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Callback;
import java.util.List;

/**
 * Servei que encapsula les operacions relacionades amb els historials mèdics.
 * <p>
 * Utilitza {@link HistorialApi} per accedir al backend mitjançant Retrofit.
 */
/**
 * Autor: mrami
 */
public class HistorialService {

    /**
     * Carrega la llista d'historials des del servidor.
     *
     * @param token    Token JWT per autorització.
     * @param callback Callback Retrofit que rebrà una llista d'objectes {@link Historial}.
     */
    public void carregarHistorials(String token, Callback<List<Historial>> callback) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);
        api.getAllHistorials("Bearer " + token).enqueue(callback);
    }

    /**
     * Actualitza un historial mèdic al backend.
     *
     * @param historial Objecte {@link Historial} amb les dades actualitzades.
     * @param token     Token JWT per autorització.
     * @param callback  Callback Retrofit que rebrà la resposta del servidor.
     */
    public void actualitzarHistorial(Historial historial, String token, Callback<Void> callback) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);
        api.updateHistorial("Bearer " + token, historial).enqueue(callback);
    }
}
