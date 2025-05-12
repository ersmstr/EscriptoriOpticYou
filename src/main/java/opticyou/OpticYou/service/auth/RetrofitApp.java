package opticyou.OpticYou.service.auth;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import opticyou.OpticYou.data.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class RetrofitApp {

    private static final String BASE_URL = "https://localhost:8083/";

    private static Retrofit retrofit;

    // ------------------ Client Configurat ------------------
    private static OkHttpClient getUnsafeOkHttpClient(String token, HttpLoggingInterceptor.Level logLevel) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(logLevel);

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .addInterceptor(loggingInterceptor);

            if (token != null && !token.isEmpty()) {
                builder.addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build()));
            }

            return builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------ Retrofit Sense Token (Singleton) ------------------
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = getUnsafeOkHttpClient(null, HttpLoggingInterceptor.Level.BODY);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // ------------------ Retrofit Amb Token (Nou per Cada Token) ------------------
    private static Retrofit getRetrofitWithToken(String token) {
        OkHttpClient client = getUnsafeOkHttpClient(token, HttpLoggingInterceptor.Level.BODY);
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // ------------------ APIS ------------------
    public static ClientApi getClientApi(String token) {
        return getRetrofitWithToken(token).create(ClientApi.class);
    }

    public static HistorialApi getHistorialApi(String token) {
        return getRetrofitWithToken(token).create(HistorialApi.class);
    }

    private static ClinicaApiProvider clinicaApiProvider = RetrofitApp::createClinicaApi;

    public static void setClinicaApiProvider(ClinicaApiProvider provider) {
        clinicaApiProvider = provider;
    }

    public static ClinicaApi getClinicaApi(String token) {
        return clinicaApiProvider.getClinicaApi(token);
    }

    private static ClinicaApi createClinicaApi(String token) {
        return getRetrofitWithToken(token).create(ClinicaApi.class);
    }

    public static TreballadorApi getTreballadorApi(String token) {
        return getRetrofitWithToken(token).create(TreballadorApi.class);
    }

    public static DiagnosticApi getDiagnosticApi(String token) {
        return getRetrofitWithToken(token).create(DiagnosticApi.class);
    }
}
