package opticyou.OpticYou.data;

import opticyou.OpticYou.model.Treballador;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Interfície Retrofit per gestionar operacions sobre clients.
 * <p>
 * Permet obtenir, crear, actualitzar i eliminar clients, així com consultar-ne un per ID.
 */
/**
 * Autor: mrami
 */

public interface TreballadorApi {


        /**
         * Obté tots els treballador.
         *
         * @param token Token JWT per autorització (format "Bearer ...").
         * @return Crida Retrofit que retorna una llista de {@link Treballador}.
         */
        @GET("/treballador")
        Call<List<Treballador>> getAllTreballadors(@Header("Authorization") String token);

        /**
         * Obté un treballador pel seu identificador.
         *
         * @param id    ID del client.
         * @param token Token JWT per autorització (format "Bearer ...").
         * @return Crida Retrofit que retorna un {@link Treballador}.
         */
       @GET("/treballador/{id}")
       Call<Treballador>  getTreballadorById(@Path("id") Long id, @Header("Authorization") String token);


        /**
         * Crea un nou Treballador.
         *
         * @param treballador Objecte {@link Treballador} amb les dades del nou client.
         * @param token  Token JWT per autorització.
         * @return Crida Retrofit que no retorna cos, només codi d'estat.
         */
        @POST("/treballador")
        Call<Void> createTreballador(@Body Treballador treballador, @Header("Authorization") String token);

        /**
         * Actualitza un treballador existent.
         *
         * @param treballador Objecte {@link Treballador} amb les dades actualitzades.
         * @param token  Token JWT per autorització.
         * @return Crida Retrofit que no retorna cos, només codi d'estat.
         */
        @PUT("/treballador/update")
        Call<Void> updateTreballador(@Body Treballador treballador, @Header("Authorization") String token);

        /**
         * Elimina un treballador pel seu ID.
         *
         * @param id    Identificador del Treballador.
         * @param token Token JWT per autorització.
         * @return Crida Retrofit que no retorna cos, només codi d'estat.
         */
        @DELETE("/treballador/{id}")
        Call<Void> deleteTreballador(@Path("id") Long id, @Header("Authorization") String token);

        @PUT("/treballador/update")
        Call<Void> updateTreballadorTreballador(@Body Treballador client, @Header("Authorization") String token);

        @DELETE("/treballador/delete_treballador")
        Call<Void> deleteTreballadorTreballador(@Header("Authorization") String token);
    }

