package OpticYou.service.ProvesIntegracio;
import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import opticyou.OpticYou.service.auth.AuthService;
import opticyou.OpticYou.service.auth.AuthServiceClient;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.*;
/**
 * Autor: mrami
 */
public class LogoutIntegracioTest {
    private final AuthServiceClient authClient = new AuthServiceClient();
    private final AuthService authService = authClient.getAuthService();

    @Test
    void testLogoutCorrecte() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // LOGIN
        authService.login(new LoginRequestDTO("aaa@a.com", "1234"))
                .enqueue(new Callback<LoginResponseDTO>() {
                    @Override
                    public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                        assertThat(response.isSuccessful()).isTrue();
                        assertThat(response.body()).isNotNull();

                        String token = response.body().getToken(); // token brut

                        // LOGOUT enviant el token dins del body
                        authService.logout(token).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                assertThat(response.isSuccessful()).isTrue();
                                assertThat(response.body()).isTrue(); // esperam true
                                System.out.println("LOGOUT OK");
                                latch.countDown();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                fail("LOGOUT ha fallat: " + t.getMessage());
                                latch.countDown();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                        fail("❌ LOGIN ha fallat: " + t.getMessage());
                        latch.countDown();
                    }
                });

        latch.await(); // Espera a que es completi tot
    }


}
