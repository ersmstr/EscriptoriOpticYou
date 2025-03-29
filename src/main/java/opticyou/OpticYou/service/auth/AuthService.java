package opticyou.OpticYou.service.auth;
/**
 * Autor: mrami
 */
import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface AuthService {

    // Métode per fer login
       @POST("login-user")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO loginRequest);

    // Métode per fer logout
    @POST("logout-string")
    Call<Boolean> logout(@Body String token);
}
