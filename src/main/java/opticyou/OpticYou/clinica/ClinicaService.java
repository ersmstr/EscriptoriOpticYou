package opticyou.OpticYou.clinica;

import opticyou.OpticYou.service.auth.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ClinicaService {

    public void carregarClinicas(String token, Callback<List<Clinica>> callback) {
        ClinicaApi clinicaApi = ApiClient.getClinicaApi(token);
        clinicaApi.getAllClinicas(token).enqueue(callback);
    }

    public void agregarClinica(Clinica clinica, String token) {
        ClinicaApi clinicaApi = ApiClient.getClinicaApi(token);

        clinicaApi.createClinica("Bearer " + token, clinica).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("✅ Clínica creada correctament!");
                } else {
                    System.out.println("❌ Error al crear clínica. Codi: " + response.code());
                    try {
                        System.out.println("🔍 Cos de l'error: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.err.println("🔥 Error de connexió: " + t.getMessage());
            }
        });
    }
    public void eliminarClinica(Long id, String token, Callback<Void> callback) {
        ClinicaApi api = ApiClient.getClinicaApi(token);
        api.deleteClinica(id, token).enqueue(callback);
    }
    public void actualitzarClinica(Clinica clinica, String token, Callback<Void> callback) {
        ClinicaApi api = ApiClient.getClinicaApi(token);
        api.updateClinica(clinica).enqueue(callback);
    }



}
