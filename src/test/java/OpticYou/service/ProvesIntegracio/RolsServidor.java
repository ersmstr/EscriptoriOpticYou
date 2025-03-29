package OpticYou.service.ProvesIntegracio;

import opticyou.OpticYou.service.auth.AuthServiceClient;
import opticyou.OpticYou.dto.LoginResponseDTO;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Autor: mrami
 */
public class RolsServidor {

    @Test
    public void testLoginAdmin() {
        AuthServiceClient authServiceClient = new AuthServiceClient();

        // Realitza el login amb les credencials de l'admin
        authServiceClient.login("admin@exemple.com", "1234", new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                // Comprovem que la resposta del servidor no és null
                assert response.body() != null;

                // Verifiquem que el rol sigui "ADMIN"
                assert "ADMIN".equals(response.body().getRol());
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                // Si falla la crida, el test hauria de fallar
                fail("El servidor hauria de respondre correctament");
            }
        });
    }

    @Test
    public void testLoginTreballador() {
        AuthServiceClient authServiceClient = new AuthServiceClient();

        // Realitza el login amb les credencials de treballador
        authServiceClient.login("treballador@exemple.com", "1234", new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                // Comprovem que la resposta del servidor no és null
                assert response.body() != null;

                // Verifiquem que el rol sigui "TREBALLADOR"
                assert "TREBALLADOR".equals(response.body().getRol());
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                // Si falla la crida, el test hauria de fallar
                fail("El servidor hauria de respondre correctament");
            }
        });
    }
}
