package opticyou.OpticYou.clients;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

/**
 * Interfície Retrofit per gestionar operacions sobre clients.
 * <p>
 * Permet obtenir, crear, actualitzar i eliminar clients, així com consultar-ne un per ID.
 */
public interface ClientApi {

    /**
     * Obté tots els clients.
     *
     * @param token Token JWT per autorització (format "Bearer ...").
     * @return Crida Retrofit que retorna una llista de {@link Client}.
     */
    @GET("/client")
    Call<List<Client>> getAllClients(@Header("Authorization") String token);

    /**
     * Obté un client pel seu identificador.
     *
     * @param id    ID del client.
     * @param token Token JWT per autorització (format "Bearer ...").
     * @return Crida Retrofit que retorna un {@link Client}.
     */
    @GET("/client/{id}")
    Call<Client> getClientById(@Path("id") Long id, @Header("Authorization") String token);


    /**
     * Crea un nou client.
     *
     * @param client Objecte {@link Client} amb les dades del nou client.
     * @param token  Token JWT per autorització.
     * @return Crida Retrofit que no retorna cos, només codi d'estat.
     */
    @POST("/client")
    Call<Void> createClient(@Body Client client, @Header("Authorization") String token);

    /**
     * Actualitza un client existent.
     *
     * @param client Objecte {@link Client} amb les dades actualitzades.
     * @param token  Token JWT per autorització.
     * @return Crida Retrofit que no retorna cos, només codi d'estat.
     */
    @PUT("/client/update")
    Call<Void> updateClient(@Body Client client, @Header("Authorization") String token);

    /**
     * Elimina un client pel seu ID.
     *
     * @param id    Identificador del client.
     * @param token Token JWT per autorització.
     * @return Crida Retrofit que no retorna cos, només codi d'estat.
     */
    @DELETE("/client/{id}")
    Call<Void> deleteClient(@Path("id") Long id, @Header("Authorization") String token);
}
