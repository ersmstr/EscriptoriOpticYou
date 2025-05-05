package opticyou.OpticYou.service.auth;

/**
 * Autor: mrami
 */

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import opticyou.OpticYou.data.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe utilitària per configurar i obtenir instàncies de Retrofit amb autenticació per token.
 */
public class RetrofitApp {

    private static final String BASE_URL = "https://localhost:8083/";


    private static Retrofit retrofit;

    /**
     * Retorna una instància bàsica de Retrofit (sense token).
     *
     * @return Instància singleton de {@link Retrofit}.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

            OkHttpClient client = new OkHttpClient.Builder()
                    .hostnameVerifier((hostname, session) -> true)
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Retorna una instància de {@link ClientApi} amb el token injectat al header.
     *
     * @param token Token JWT.
     * @return ClientApi autenticat.
     */
    public static ClientApi getClientApi(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build()))
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWithToken.create(ClientApi.class);
    }

    /**
     * Retorna una instància de {@link HistorialApi} amb el token JWT.
     *
     * @param token Token JWT.
     * @return HistorialApi autenticat.
     */
    public static HistorialApi getHistorialApi(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build()))
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWithToken.create(HistorialApi.class);
    }

    /**
     * Proveïdor d'API per a clíniques, útil per tests.
     */
    private static ClinicaApiProvider clinicaApiProvider = RetrofitApp::createClinicaApi;

    public static void setClinicaApiProvider(ClinicaApiProvider provider) {
        clinicaApiProvider = provider;
    }

    public static ClinicaApi getClinicaApi(String token) {
        return clinicaApiProvider.getClinicaApi(token);
    }

    /**
     * Crea una instància autenticada de {@link ClinicaApi} amb token al header.
     *
     * @param token Token JWT.
     * @return ClinicaApi autenticat.
     */
    private static ClinicaApi createClinicaApi(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build()))
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWithToken.create(ClinicaApi.class);
    }
    public static TreballadorApi getTreballadorApi(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build()))
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWithToken.create(TreballadorApi.class);
    }
    public static DiagnosticApi getDiagnosticApi(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build()))
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWithToken.create(DiagnosticApi.class);
    }

}
