package opticyou.OpticYou.service.auth;



import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Interfície Retrofit per gestionar les operacions d'autenticació.
 * <p>
 * Inclou login i logout mitjançant crides POST al backend.
 */

/**
 * Autor: mrami
 */
public interface AuthService {

    /**
     * Envia una petició de login a l'API.
     * <p>
     * S'envia un {@link LoginRequestDTO} amb l'email i la contrasenya.
     *
     * @param loginRequest Objecte amb les credencials de l'usuari.
     * @return {@link Call} que retorna un {@link LoginResponseDTO} amb el token JWT si és correcte.
     */
    @POST("login-user")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO loginRequest);

    /**
     * Envia una petició de logout al servidor.
     * <p>
     * El token es passa com a text pla al cos de la petició. Aquest disseny és poc habitual, però funcional.
     *
     * @param token Token JWT a invalidar.
     * @return {@link Call} que retorna {@code true} si el logout s'ha realitzat correctament.
     */
    @POST("logout-string")
    Call<Boolean> logout(@Body String token);
}
