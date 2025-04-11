package opticyou.OpticYou.data;

import opticyou.OpticYou.model.Historial;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Interfície Retrofit per gestionar les operacions de xarxa relacionades amb historials mèdics.
 * /Inclou mètodes per obtenir i actualitzar historials
 //*
 * Autor: mramis
 */
public interface HistorialApi {

    /**
     * Obté la llista de tots els historials disponibles.
     *
     * @param token Token d'autenticació en el format "Bearer ...".
     * @return Una crida Retrofit que retorna una llista d'objectes {@link Historial}.
     */
    @GET("/historial")
    Call<List<Historial>> getAllHistorials(@Header("Authorization") String token);

    /**
     * Actualitza un historial específic.
     *
     * @param token Token d'autenticació.
     * @param historial Objecte {@link Historial} amb les dades a actualitzar.
     * @return Una crida Retrofit que no retorna contingut (HTTP 204 si tot va bé).
     */
    @PUT("/historial/update")
    Call<Void> updateHistorial(@Header("Authorization") String token, @Body Historial historial);
}
