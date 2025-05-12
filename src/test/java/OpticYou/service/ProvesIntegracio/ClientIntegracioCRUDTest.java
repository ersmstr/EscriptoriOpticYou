package OpticYou.service.ProvesIntegracio;

import opticyou.OpticYou.model.Client;
import opticyou.OpticYou.service.ClientService;
import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import opticyou.OpticYou.service.auth.AuthServiceClient;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.*;


/**
 * Autor: mrami
 */
public class ClientIntegracioCRUDTest {


    private final AuthServiceClient authClient = new AuthServiceClient();
    private final ClientService clientService = new ClientService();

    @Test
    void testClientCRUDComplet() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // 1. LOGIN
        authClient.getAuthService().login(new LoginRequestDTO("admin@exemple.com", "admin123"))
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                        assertThat(response.isSuccessful()).isTrue();
                        assertThat(response.body()).isNotNull();
                        String token = response.body().getToken();
                        // TEST DEL CRUD
                        // 2. CREATE
                        Client client = new Client();
                        client.setNom("Usuari CRUD");
                        client.setEmail("client-crud@a.com");
                        client.setContrasenya("1234");
                        client.setDataNaixament("1995-06-05");
                        client.setSexe("Dona");
                        client.setTelefon("933123123");
                        client.setClinicaId(1L);

                        clientService.createClient(client, token, new Callback<>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                assertThat(response.isSuccessful()).isTrue();
                                System.out.println("CREATE OK");

                                // 3. READ
                                clientService.carregarClients(token, new Callback<>() {
                                    @Override
                                    public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                                        assertThat(response.isSuccessful()).isTrue();
                                        List<Client> clients = response.body();
                                        assertThat(clients).isNotEmpty();
                                        System.out.println("READ OK");

                                        // 4. UPDATE
                                        Client clientTrobat = clients.stream()
                                                .filter(c -> "client-crud@a.com".equals(c.getEmail()))
                                                .findFirst()
                                                .orElse(null);
                                        assertThat(clientTrobat).isNotNull();

                                        clientTrobat.setTelefon("777888999");

                                        clientService.actualitzarClient(clientTrobat, token, new Callback<>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                assertThat(response.isSuccessful()).isTrue();
                                                System.out.println("UPDATE OK");

                                                // 5. DELETE
                                                clientService.eliminarClient(clientTrobat.getIdClient(), token, new Callback<>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        assertThat(response.isSuccessful()).isTrue();
                                                        System.out.println("DELETE OK");
                                                        latch.countDown(); // Finalitza test
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                        fail("DELETE ha fallat: " + t.getMessage());
                                                        latch.countDown();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                fail("UPDATE ha fallat: " + t.getMessage());
                                                latch.countDown();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<List<Client>> call, Throwable t) {
                                        fail("READ ha fallat: " + t.getMessage());
                                        latch.countDown();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                fail("CREATE ha fallat: " + t.getMessage());
                              //esperar que elsl processos acabin
                                latch.countDown();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                        fail("LOGIN ha fallat: " + t.getMessage());
                        latch.countDown();
                    }
                });

        latch.await(); // Espera que tot el procés acabi abans d'acabar el test
    }
}
