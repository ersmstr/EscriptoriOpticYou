package opticyou.OpticYou.historial;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Autor: mrami
 */
public interface HistorialApi {

    // Obtenir tots els historials
    @GET("/historial")
    Call<List<Historial>> getAllHistorials(@Header("Authorization") String token);



    @PUT("/historial/update")
    Call<Void> updateHistorial(@Header("Authorization") String token, @Body Historial historial);
}

