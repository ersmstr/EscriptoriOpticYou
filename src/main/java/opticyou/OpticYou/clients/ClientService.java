package opticyou.OpticYou.clients;

import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ClientService {

    public void carregarClients(String token, Callback<List<Client>> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.getAllClients(token).enqueue(callback);
    }

    public void createClient(Client client, String token) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);

        clientApi.createClient(client, "Bearer " + token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Client creat correctament!");
                } else {
                    System.out.println(" Error al crear client. Codi: " + response.code());
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

    public void eliminarClient(Long id, String token, Callback<Void> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.deleteClient(id, token).enqueue(callback);
    }

    public void actualitzarClient(Client client, String token, Callback<Void> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.updateClient(client, "Bearer " + token).enqueue(callback);
    }
    public void createClient(Client client, String token, Callback<Void> callback) {
        ClientApi clientApi = RetrofitApp.getClientApi(token);
        clientApi.createClient(client, "Bearer " + token).enqueue(callback);
    }

}
