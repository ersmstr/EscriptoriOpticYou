package opticyou.OpticYou.clinica;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Autor: mrami
 */
public interface ClinicaApi {
        @POST("/clinica")
        Call<Void> createClinica(@Header("Authorization") String token, @Body Clinica clinica);


        // Obtener una clínica per ID
        @GET("/clinica/{id}/{token}")
        Call<Clinica> getClinicaById(@Path("id") Long id, @Path("token") String token);

        // Totes les clíniques
        @GET("/clinica")
        Call<List<Clinica>> getAllClinicas(@Header("Authorization") String token);


        // Actualitzar una clínica
        @PUT("/clinica/update")
        Call<Void> updateClinica(@Body Clinica clinica);

        // Eliminar una clínica
        @DELETE("/clinica/{id}")
        Call<Void> deleteClinica(@Path("id") Long id, @Header("Authorization") String token);

}


