package opticyou.OpticYou.data;

import opticyou.OpticYou.model.Clinica;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Interfície Retrofit per gestionar operacions sobre clíniques.
 *
 * <p>Inclou mètodes per crear, obtenir, actualitzar i eliminar clíniques.</p>
 */
 /**
 * Autor: mramis
 */
public interface ClinicaApi {

        /**
         * Crea una nova clínica.
         *
         * @param token   Token d'autenticació (format "Bearer ...").
         * @param clinica Objecte {@link Clinica} amb les dades de la nova clínica.
         * @return Crida Retrofit que retorna un {@code Void} (normalment HTTP 201 si és exitós).
         */
        @POST("/clinica")
        Call<Void> createClinica(@Header("Authorization") String token, @Body Clinica clinica);

         /**
          * Obté una clínica pel seu identificador.
         */
         @GET("/clinica/{id}")
         Call<Clinica> getClinicaById(@Path("id") Long id, @Header("Authorization") String token);


         /**
         * Obté totes les clíniques disponibles.
         *
         * @param token Token d'autenticació.
         * @return Crida Retrofit que retorna una llista de {@link Clinica}.
         */
        @GET("/clinica")
        Call<List<Clinica>> getAllClinicas(@Header("Authorization") String token);

        /**
         * Actualitza les dades d'una clínica.
         *
         * @param clinica Objecte {@link Clinica} amb les dades actualitzades.
         * @return Crida Retrofit que no retorna contingut (HTTP 204 si és correcte).
         */
        @PUT("/clinica/update")
        Call<Void> updateClinica(@Body Clinica clinica);

        /**
         * Elimina una clínica pel seu ID.
         *
         * @param id    Identificador de la clínica.
         * @param token Token d'autenticació.
         * @return Crida Retrofit que no retorna contingut (HTTP 204 si s'elimina correctament).
         */
        @DELETE("/clinica/{id}")
        Call<Void> deleteClinica(@Path("id") Long id, @Header("Authorization") String token);
}
