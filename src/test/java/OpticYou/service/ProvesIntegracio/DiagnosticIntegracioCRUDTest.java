package OpticYou.service.ProvesIntegracio;

/**
 * Autor: mrami
 */


import opticyou.OpticYou.model.Diagnostic;
import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import opticyou.OpticYou.service.DiagnosticService;
import opticyou.OpticYou.service.auth.AuthServiceClient;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.*;

public class DiagnosticIntegracioCRUDTest {

    private final AuthServiceClient authClient = new AuthServiceClient();

    @Test
    void testDiagnosticCRUDComplet() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // LOGIN COM A ADMIN
        authClient.getAuthService().login(new LoginRequestDTO("admin@exemple.com", "admin123"))
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                        assertThat(response.isSuccessful()).isTrue();
                        assertThat(response.body()).isNotNull();
                        String token = response.body().getToken();

                        DiagnosticService diagnosticService = new DiagnosticService(token);

                        // 1. CREATE
                        Diagnostic diagnostic = new Diagnostic(99L, "MIOPIA", "2025-05-12T16:00:00", 1L);

                        diagnosticService.crearDiagnostic(diagnostic, new Callback<>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                assertThat(response.isSuccessful()).isTrue();
                                System.out.println("CREATE OK");

                                // 2. READ
                                diagnosticService.getDiagnostics(1L, new Callback<>() {
                                    @Override
                                    public void onResponse(Call<List<Diagnostic>> call, Response<List<Diagnostic>> response) {
                                        assertThat(response.isSuccessful()).isTrue();
                                        List<Diagnostic> diagnostics = response.body();
                                        assertThat(diagnostics).isNotEmpty();
                                        System.out.println("READ OK");

                                        // 3. UPDATE
                                        Diagnostic diagnosticTrobat = diagnostics.get(0);
                                        diagnosticTrobat.setDescripcio("Diagnòstic actualitzat");

                                        diagnosticService.actualitzarDiagnostic(diagnosticTrobat, new Callback<>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                assertThat(response.isSuccessful()).isTrue();
                                                System.out.println("UPDATE OK");

                                                // 4. DELETE
                                                diagnosticService.esborrarDiagnostic(diagnosticTrobat.getIdDiagnostic(), new Callback<>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        assertThat(response.isSuccessful()).isTrue();
                                                        System.out.println("DELETE OK");
                                                        latch.countDown();
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
                                    public void onFailure(Call<List<Diagnostic>> call, Throwable t) {
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

        latch.await(); // Bloqueja fins que acabi tot el procés
    }
}

