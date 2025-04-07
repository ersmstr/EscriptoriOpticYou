package OpticYou.service.ProvesIntegracio;



import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import opticyou.OpticYou.historial.Historial;
import opticyou.OpticYou.historial.HistorialService;
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

public class HistorialIntegracioCRUDTest {

    @Test
    void testHistorialUpdateComplet() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AuthServiceClient authClient = new AuthServiceClient();
        HistorialService historialService = new HistorialService();

        authClient.getAuthService().login(new LoginRequestDTO("aaa@a.com", "1234"))
                .enqueue(new Callback<LoginResponseDTO>() {
                    @Override
                    public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                        String token = response.body().getToken();

                        // GET (READ)
                        historialService.carregarHistorials(token, new Callback<List<Historial>>() {
                            @Override
                            public void onResponse(Call<List<Historial>> call, Response<List<Historial>> response) {
                                List<Historial> historials = response.body();
                                assertThat(historials).isNotEmpty();

                                Historial h = historials.get(0); // Agafam el primer
                                String original = h.getPatologies(); // Guardam el valor original

                                // UPDATE amb observació única
                                String novaPatologia = "Modificat per test " + System.currentTimeMillis();
                                h.setPatologies(novaPatologia);

                                historialService.actualitzarHistorial(h, token, new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        System.out.println("✔️ UPDATE OK");

                                        // REVERT (com si fos un DELETE lògic)
                                        h.setPatologies(original);
                                        historialService.actualitzarHistorial(h, token, new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                System.out.println("REVERT OK");
                                                latch.countDown();
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                fail("REVERT ha fallat: " + t.getMessage());
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
                            public void onFailure(Call<List<Historial>> call, Throwable t) {
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

        latch.await();
    }
}

