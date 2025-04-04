package opticyou.OpticYou.clients;



import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ClientApi {

    @GET("/client")
    Call<List<Client>> getAllClients(@Header("Authorization") String token);

    @GET("/client/{id}/{token}")
    Call<Client> getClientById(@Path("id") Long id, @Path("token") String token);

//    @POST("/client")
//    Call<Void> createClient(@Header("Authorization") String token, @Body Client client);
@POST("/client")
Call<Void> createClient(@Body Client client, @Header("Authorization") String token);


//    @PUT("/client")
//    Call<Void> updateClient(@Header("Authorization") String token, @Body Client client);

    @PUT("/client/update")
    Call<Void> updateClient(@Body Client client, @Header("Authorization") String token);


    @DELETE("/client/{id}")
    Call<Void> deleteClient(@Path("id") Long id, @Header("Authorization") String token);
}

