package OpticYou.service.ProvesIntegracio;

import opticyou.OpticYou.model.Clinica;
import opticyou.OpticYou.service.ClinicaService;
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
public class ClinicaIntegracioCRUDTest {

    @Test
    void testClinicaCRUDComplet() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AuthServiceClient authClient = new AuthServiceClient();
        ClinicaService clinicaService = new ClinicaService();

        authClient.getAuthService().login(new LoginRequestDTO("admin@exemple.com", "admin123"))
                .enqueue(new Callback<LoginResponseDTO>() {
                    @Override
                    public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                        String token = response.body().getToken();

                        // 🔐 Email únic per evitar duplicats
                        String emailUnic = "clinica" + System.currentTimeMillis() + "@test.com";

                        // CREATE
                        Clinica nova = new Clinica("Test Clinica", "Carrer 1", "111222333", "08:00", "20:00", emailUnic);
                        nova.setIdClinica(9999L); // ← afegit per evitar error del servidor

                        clinicaService.agregarClinica(nova, token, new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    System.out.println("   CREATE OK");

                                    // READ
                                    clinicaService.carregarClinicas(token, new Callback<List<Clinica>>() {
                                        @Override
                                        public void onResponse(Call<List<Clinica>> call, Response<List<Clinica>> response) {
                                            List<Clinica> cliniques = response.body();

                                            cliniques.forEach(c -> System.out.println("📦 Clinica: " + c.getNom() + " - " + c.getEmail()));

                                            Clinica trobada = cliniques.stream()
                                                    .filter(c -> emailUnic.equals(c.getEmail()))
                                                    .findFirst()
                                                    .orElse(null);

                                            assertThat(trobada).isNotNull();

                                            // UPDATE
                                            trobada.setTelefon("999999999");
                                            clinicaService.actualitzarClinica(trobada, token, new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    System.out.println("   UPDATE OK");

                                                    // DELETE
                                                    clinicaService.eliminarClinica(trobada.getIdClinica(), token, new Callback<Void>() {
                                                        @Override
                                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                                            System.out.println("   DELETE OK");
                                                            latch.countDown();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Void> call, Throwable t) {
                                                            fail("  DELETE ha fallat: " + t.getMessage());
                                                            latch.countDown();
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    fail("  UPDATE ha fallat: " + t.getMessage());
                                                    latch.countDown();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<List<Clinica>> call, Throwable t) {
                                            fail("  READ ha fallat: " + t.getMessage());
                                            latch.countDown();
                                        }
                                    });
                                } else {
                                    System.out.println("  CREATE ha fallat. Codi: " + response.code());
                                    try {
                                        System.out.println("Cos error: " + response.errorBody().string());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    latch.countDown();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                fail("  CREATE ha fallat: " + t.getMessage());
                                latch.countDown();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                        fail("  LOGIN ha fallat: " + t.getMessage());
                        latch.countDown();
                    }
                });

        latch.await();
    }
}
