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

    // Crear un historial nou
    @POST("/historial")
    Call<Void> createHistorial(@Header("Authorization") String token, @Body Historial historial);

    // Actualitzar un historial
    @PUT("/historial")
    Call<Void> updateHistorial(@Header("Authorization") String token, @Body Historial historial);

    // Eliminar un historial per ID
    @DELETE("/historial/{id}")
    Call<Void> deleteHistorial(@Path("id") Long id, @Header("Authorization") String token);
}

