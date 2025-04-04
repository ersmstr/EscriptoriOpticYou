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

    public void crearHistorial(Historial historial, String token) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);

        api.createHistorial("Bearer " + token, historial).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Historial creat correctament!");
                } else {
                    System.out.println("Error al crear historial. Codi: " + response.code());
                    try {
                        System.out.println("Cos de l'error: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.err.println("Error de connexió: " + t.getMessage());
            }
        });
    }

    public void crearHistorial(Historial historial, String token, Callback<Void> callback) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);
        api.createHistorial("Bearer " + token, historial).enqueue(callback);
    }

    public void eliminarHistorial(Long id, String token, Callback<Void> callback) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);
        api.deleteHistorial(id, "Bearer " + token).enqueue(callback);
    }

    public void actualitzarHistorial(Historial historial, String token, Callback<Void> callback) {
        HistorialApi api = RetrofitApp.getHistorialApi(token);
        api.updateHistorial("Bearer " + token, historial).enqueue(callback);
    }
}

