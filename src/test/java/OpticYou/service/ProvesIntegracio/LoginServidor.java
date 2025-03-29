package OpticYou.service.ProvesIntegracio;
/**
 * Autor: mrami
 */
import opticyou.OpticYou.service.auth.AuthService;
import opticyou.OpticYou.service.auth.AuthServiceClient;
import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class LoginServidor {

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        AuthServiceClient authServiceClient = new AuthServiceClient(); // Crear instància manualment
        authService = authServiceClient.getAuthService();
    }

    @Test
    public void testLoginCorrecte() {
        // Credencials correctes
        String email = "bbb@a.com";
        String password = "1234";

        // Creem la petició de login
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);
        Call<LoginResponseDTO> call = authService.login(loginRequest);

        // Executem la crida asíncrona
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    assertThat(response.body().getToken()).isNotEmpty();
                    assertThat(response.body().getRol()).isEqualTo("ADMIN");
                } else {
                    fail("Resposta no exitosa. Codi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                fail("Error en la petició: " + t.getMessage());
            }
        });
    }

    @Test
    public void testLoginIncorrecte() {
        // Credencials incorrectes
        String email = "bbb@a.com";
        String password = "incorrecta";

        // Creem la petició de login
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);
        Call<LoginResponseDTO> call = authService.login(loginRequest);

        // Executem la crida asíncrona
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                assertThat(response.isSuccessful()).isFalse();
                assertThat(response.code()).isIn(400, 401); // Error d'autenticació
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                fail("Error en la petició: " + t.getMessage());
            }
        });
    }
}
