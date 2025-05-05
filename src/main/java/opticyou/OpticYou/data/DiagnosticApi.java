package opticyou.OpticYou.data;
import retrofit2.Call;
import retrofit2.http.*;





    public interface DiagnosticApi {

        @GET("diagnostic/{historialId}")
        Call<String> getDiagnostic(@Path("historialId") Long historialId);

        @POST("diagnostic/{historialId}")
        Call<Void> guardarDiagnostic(@Path("historialId") Long historialId, @Body String text);

        @DELETE("diagnostic/{historialId}")
        Call<Void> esborrarDiagnostic(@Path("historialId") Long historialId);
    }

