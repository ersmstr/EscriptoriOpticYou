package ProvesUnitaries;

/**
 * Autor: mrami
 */
import opticyou.OpticYou.model.Client;
import opticyou.OpticYou.data.ClientApi;
import opticyou.OpticYou.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceCRUDTest {

    @Mock
    private ClientApi clientApiMock;

    @Mock
    private Call<Void> callMockVoid;

    @Mock
    private Call<List<Client>> callMockList;

    private ClientService clientService;
    private final String token = "fake-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creem un ClientService amb RetrofitApp simulat
        clientService = new ClientService() {
            @Override
            public void carregarClients(String token, Callback<List<Client>> callback) {
                clientApiMock.getAllClients("Bearer " + token).enqueue(callback);
            }

            @Override
            public void createClient(Client client, String token, Callback<Void> callback) {
                clientApiMock.createClient(client, "Bearer " + token).enqueue(callback);
            }

            @Override
            public void actualitzarClient(Client client, String token, Callback<Void> callback) {
                clientApiMock.updateClient(client, "Bearer " + token).enqueue(callback);
            }

            @Override
            public void eliminarClient(Long id, String token, Callback<Void> callback) {
                clientApiMock.deleteClient(id, "Bearer " + token).enqueue(callback);
            }
        };
    }

    @Test
    void testClientCRUD() {
        Client client = new Client();
        client.setNom("Maria");
        client.setEmail("maria@test.com");
        client.setContrasenya("1234");
        client.setRol("CLIENT");
        client.setDataNaixament("1990-01-01");
        client.setSexe("F");
        client.setTelefon("123456789");
        client.setClinicaId(1L);
        client.setHistorialId(null);

        // CREATE
        when(clientApiMock.createClient(eq(client), anyString())).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        clientService.createClient(client, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                assertTrue(response.isSuccessful());
                System.out.println("CREATE correcte");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                fail("CREATE ha fallat");
            }
        });

        // READ
        List<Client> llista = new ArrayList<>();
        llista.add(client);

        when(clientApiMock.getAllClients(any())).thenReturn(callMockList);
        doAnswer(invocation -> {
            Callback<List<Client>> callback = invocation.getArgument(0);
            callback.onResponse(callMockList, Response.success(llista));
            return null;
        }).when(callMockList).enqueue(any());

        clientService.carregarClients(token, new Callback<>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                assertNotNull(response.body());
                assertEquals(1, response.body().size());
                System.out.println("READ correcte");
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                fail("READ ha fallat");
            }
        });

        // UPDATE
        client.setTelefon("999111222");
        when(clientApiMock.updateClient(eq(client), anyString())).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        clientService.actualitzarClient(client, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                assertTrue(response.isSuccessful());
                System.out.println("UPDATE correcte");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                fail("UPDATE ha fallat");
            }
        });

        // DELETE
        Long clientId = 1L;
        when(clientApiMock.deleteClient(eq(clientId), anyString())).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        clientService.eliminarClient(clientId, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                assertTrue(response.isSuccessful());
                System.out.println("DELETE correcte");

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                fail(" DELETE ha fallat");
            }
        });
    }
}