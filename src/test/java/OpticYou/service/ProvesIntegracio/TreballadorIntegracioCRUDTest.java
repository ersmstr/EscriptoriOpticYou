package OpticYou.service.ProvesIntegracio;

import opticyou.OpticYou.model.Treballador;
import opticyou.OpticYou.service.TreballadorService;
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

public class TreballadorIntegracioCRUDTest {

    private final AuthServiceClient authClient = new AuthServiceClient();
    private final TreballadorService treballadorService = new TreballadorService();

    @Test
    void testTreballadorCRUDComplet() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // LOGIN COM A ADMIN O TREBALLADOR AMB PERMISOS
        authClient.getAuthService().login(new LoginRequestDTO("admin@exemple.com", "admin123"))
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                        assertThat(response.isSuccessful()).isTrue();
                        assertThat(response.body()).isNotNull();
                        String token = response.body().getToken();

                        // 1. CREATE
                        Treballador treballador = new Treballador();
                        treballador.setNom("Treballador CRUD");
                        treballador.setEmail("treballador-crud@a.com");
                        treballador.setContrasenya("1234");
                        treballador.setClinicaId(1L);

                        treballadorService.createTreballador(treballador, token, new Callback<>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                assertThat(response.isSuccessful()).isTrue();
                                System.out.println("CREATE OK");

                                // 2. READ
                                treballadorService.carregarTreballadors(token, new Callback<>() {
                                    @Override
                                    public void onResponse(Call<List<Treballador>> call, Response<List<Treballador>> response) {
                                        assertThat(response.isSuccessful()).isTrue();
                                        List<Treballador> treballadors = response.body();
                                        assertThat(treballadors).isNotEmpty();
                                        System.out.println("READ OK");

                                        // 3. UPDATE
                                        Treballador treballadorTrobat = treballadors.stream()
                                                .filter(t -> "treballador-crud@a.com".equals(t.getEmail()))
                                                .findFirst()
                                                .orElse(null);
                                        assertThat(treballadorTrobat).isNotNull();

                                        treballadorTrobat.setNom("Treballador CRUD Actualitzat");

                                        treballadorService.actualitzarTreballador(treballadorTrobat, token, new Callback<>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                assertThat(response.isSuccessful()).isTrue();
                                                System.out.println("UPDATE OK");

                                                // 4. DELETE
                                                treballadorService.eliminarTreballador(treballadorTrobat.getIdTreballador(), token, new Callback<>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        assertThat(response.isSuccessful()).isTrue();
                                                        System.out.println("DELETE OK");
                                                        latch.countDown(); // Finalitza el test
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
                                    public void onFailure(Call<List<Treballador>> call, Throwable t) {
                                        fail("READ ha fallat: " + t.getMessage());
                                        latch.countDown();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                fail("CREATE ha fallat: " + t.getMessage());
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

        latch.await(); // Espera que tot acabi abans de tancar el test
    }

    @Test
    void testTreballadorVeureElsDeLaSevaClinica() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // LOGIN COM A TREBALLADOR
        authClient.getAuthService().login(new LoginRequestDTO("t@t.com", "1"))
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                        assertThat(response.isSuccessful()).isTrue();
                        assertThat(response.body()).isNotNull();
                        String token = response.body().getToken();

                        // CONSULTA ELS TREBALLADORS
                        treballadorService.carregarTreballadors(token, new Callback<>() {
                            @Override
                            public void onResponse(Call<List<Treballador>> call, Response<List<Treballador>> response) {
                                assertThat(response.isSuccessful()).isTrue();
                                List<Treballador> treballadors = response.body();
                                assertThat(treballadors).isNotEmpty();

                                // OPCIONAL: validar que tots siguin de la mateixa clínica
                                Long clinicaIdTreballador = treballadors.get(0).getClinicaId();
                                assertThat(treballadors)
                                        .allMatch(t -> t.getClinicaId().equals(clinicaIdTreballador));

                                System.out.println("READ com a treballador OK");
                                latch.countDown();
                            }

                            @Override
                            public void onFailure(Call<List<Treballador>> call, Throwable t) {
                                fail("READ ha fallat: " + t.getMessage());
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

        latch.await(); // Espera a que el test acabi
    }

}



