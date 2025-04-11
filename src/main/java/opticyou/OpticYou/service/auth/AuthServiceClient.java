package opticyou.OpticYou.service.auth;

/**
 * Autor: mrami
 */

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import opticyou.OpticYou.dto.LoginRequestDTO;
import opticyou.OpticYou.dto.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client que encapsula les crides d'autenticació mitjançant {@link AuthService}.
 * <p>
 * Gestiona login i logout amb suport per testing mitjançant injecció del servei.
 */
public class AuthServiceClient {

    /** URL base per a les crides d'autenticació. */
    private static final String BASE_URL = "http://localhost:8083/auth/";

    private Retrofit retrofit;
    private AuthService authService;

    /**
     * Constructor principal. Configura Retrofit amb logging i crea el servei {@link AuthService}.
     */
    public AuthServiceClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        this.authService = retrofit.create(AuthService.class);
    }

    /**
     * Constructor alternatiu per a testing.
     *
     * @param authService Instància mock o alternativa de {@link AuthService}.
     */
    public AuthServiceClient(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Fa una petició de login amb email i contrasenya.
     *
     * @param email    Correu electrònic de l'usuari.
     * @param password Contrasenya de l'usuari.
     * @param callback Callback que rebrà la resposta amb el token o l'error.
     */
    public void login(String email, String password, Callback<LoginResponseDTO> callback) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
        Call<LoginResponseDTO> call = authService.login(loginRequestDTO);
        call.enqueue(callback);
    }

    /**
     * Fa logout enviant el token com a string.
     * <p>
     * El backend espera el token al cos de la petició.
     *
     * @param token    Token JWT a invalidar.
     * @param callback Callback amb la resposta booleana.
     */
    public void logout(String token, Callback<Boolean> callback) {
        authService.logout(token).enqueue(callback);
    }

    /**
     * Retorna la instància actual de {@link AuthService}.
     *
     * @return Servei d'autenticació.
     */
    public AuthService getAuthService() {
        return authService;
    }
}
