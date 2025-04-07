package opticyou.OpticYou.historial;

import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
/**
 * Autor: mrami
 */
public class HistorialService {

    public void carregarHistorials(String token, Callback<List<Historial>> callback) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);
        api.getAllHistorials("Bearer " + token).enqueue(callback);
    }



    public void actualitzarHistorial(Historial historial, String token, Callback<Void> callback) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);
        api.updateHistorial("Bearer " + token, historial).enqueue(callback);
    }
}

