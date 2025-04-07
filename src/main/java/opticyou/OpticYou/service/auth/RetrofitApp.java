package opticyou.OpticYou.service.auth;

/**
 * Autor: mrami
 */
import opticyou.OpticYou.clinica.ClinicaApiProvider;
import opticyou.OpticYou.historial.LocalDateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import opticyou.OpticYou.clients.ClientApi;
import opticyou.OpticYou.clinica.ClinicaApi;
import opticyou.OpticYou.historial.HistorialApi;
import opticyou.OpticYou.historial.LocalDateTimeDeserializer;
import opticyou.OpticYou.historial.LocalDateTimeSerializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDateTime;

public class RetrofitApp {
    private static final String BASE_URL = "http://host.docker.internal:8083/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    // Métode per autentificar login(logout
    public static RetrofitApp getClientApi() {
        return getClient().create(RetrofitApp.class);
   }



    // Métode per obtenir
    private static ClinicaApi createClinicaApi(String token){
    // Aquí passarem el token per afegir-lo manualment als encapçalaments si és necessari.
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    // Afegir el token a l'encapçalament Authorization
                    return chain.proceed(chain.request().newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .build());
                })
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWithToken.create(ClinicaApi.class);
    }



    public static ClientApi getClientApi(String token) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    return chain.proceed(
                            chain.request().newBuilder()
                                    .header("Authorization", "Bearer " + token)
                                    .build());
                })
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWithToken.create(ClientApi.class);
    }

    public static HistorialApi getHistorialApi(String token) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()) // ✨ afegit
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build()))
                .build();

        Retrofit retrofitWithToken = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofitWithToken.create(HistorialApi.class);
    }

    // PER FER ELS TEST
    private static ClinicaApiProvider clinicaApiProvider = RetrofitApp::createClinicaApi;

    public static void setClinicaApiProvider(ClinicaApiProvider provider) {
        clinicaApiProvider = provider;
    }

    // Refactoritza el mètode existent perquè usi el provider
    public static ClinicaApi getClinicaApi(String token) {
        return clinicaApiProvider.getClinicaApi(token);
    }



}

