package opticyou.OpticYou.clinica;

import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ClinicaService {
    private final ClinicaApiProvider apiProvider;


    // Constructor per defecte (per compatibilitat amb el codi existent)
    public ClinicaService() {
        this(RetrofitApp::getClinicaApi);
    }

    public void carregarClinicas(String token, retrofit2.Callback<List<Clinica>> callback) {
        ClinicaApi clinicaApi = apiProvider.getClinicaApi(token);
        clinicaApi.getAllClinicas(token).enqueue(callback);
    }

    public void agregarClinica(Clinica clinica, String token) {
        ClinicaApi clinicaApi = apiProvider.getClinicaApi(token);
        clinicaApi.createClinica("Bearer " + token, clinica).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                // gestionar resposta
            }

            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                // gestionar error
            }
        });
    }

    public void eliminarClinica(Long id, String token, retrofit2.Callback<Void> callback) {
        ClinicaApi api = apiProvider.getClinicaApi(token);
        api.deleteClinica(id, token).enqueue(callback);
    }

    public void actualitzarClinica(Clinica clinica, String token, retrofit2.Callback<Void> callback) {
        ClinicaApi api = apiProvider.getClinicaApi(token);
        api.updateClinica(clinica).enqueue(callback);
    }

    public void agregarClinica(Clinica clinica, String token, retrofit2.Callback<Void> callback) {
        ClinicaApi api = apiProvider.getClinicaApi(token);
        api.createClinica("Bearer " + token, clinica).enqueue(callback);
    }
    public ClinicaService(ClinicaApiProvider apiProvider) {
        this.apiProvider = apiProvider;
    }

}
