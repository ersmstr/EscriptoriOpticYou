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


public class AuthServiceClient {
    private static final String BASE_URL = "http://localhost:8083/auth/";

    private Retrofit retrofit;
    private AuthService authService;

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
    // Mètode per a login
    public void login(String email, String password, Callback<LoginResponseDTO> callback) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
        Call<LoginResponseDTO> call = authService.login(loginRequestDTO);
        call.enqueue(callback);
    }

    // Mètode per a logout
    public void logout(String token, Callback<Boolean> callback) {
        // passem token com a string
        authService.logout(token).enqueue(callback);
    }
    // Constructor per a tests (permiteix injectar un mock)
    public AuthServiceClient(AuthService authService) {
        this.authService = authService;
    }

    public AuthService getAuthService() {
        return authService;
    }
}


