package ProvesUnitaries;

/**
 * Autor: mramis
 */


import opticyou.OpticYou.model.Treballador;
import opticyou.OpticYou.data.TreballadorApi;
import opticyou.OpticYou.service.TreballadorService;
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

public class testTreballadorCRUD {

    @Mock
    private TreballadorApi treballadorApiMock;

    @Mock
    private Call<Void> callMockVoid;

    @Mock
    private Call<List<Treballador>> callMockList;

    private TreballadorService treballadorService;
    private final String token = "fake-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        treballadorService = new TreballadorService() {
            @Override
            public void carregarTreballadors(String token, Callback<List<Treballador>> callback) {
                treballadorApiMock.getAllTreballadors(token).enqueue(callback);
            }

            @Override
            public void createTreballador(Treballador treballador, String token, Callback<Void> callback) {
                treballadorApiMock.createTreballador(treballador, "Bearer " + token).enqueue(callback);
            }

            @Override
            public void actualitzarTreballador(Treballador treballador, String token, Callback<Void> callback) {
                treballadorApiMock.updateTreballador(treballador, "Bearer " + token).enqueue(callback);
            }

            @Override
            public void eliminarTreballador(Long id, String token, Callback<Void> callback) {
                treballadorApiMock.deleteTreballador(id, token).enqueue(callback);
            }
        };
    }

    @Test
    void testTreballadorCRUD() {
        Treballador treballador = new Treballador();
        treballador.setNom("Joan");
        treballador.setEmail("joan@z.com");
        treballador.setContrasenya("1234");
        treballador.setClinicaId(1L);

        // CREATE
        when(treballadorApiMock.createTreballador(eq(treballador), anyString())).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        treballadorService.createTreballador(treballador, token, new Callback<>() {
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
        List<Treballador> llista = new ArrayList<>();
        llista.add(treballador);

        when(treballadorApiMock.getAllTreballadors(any())).thenReturn(callMockList);
        doAnswer(invocation -> {
            Callback<List<Treballador>> callback = invocation.getArgument(0);
            callback.onResponse(callMockList, Response.success(llista));
            return null;
        }).when(callMockList).enqueue(any());

        treballadorService.carregarTreballadors(token, new Callback<>() {
            @Override
            public void onResponse(Call<List<Treballador>> call, Response<List<Treballador>> response) {
                assertNotNull(response.body());
                assertEquals(1, response.body().size());
                System.out.println("READ correcte");
            }

            @Override
            public void onFailure(Call<List<Treballador>> call, Throwable t) {
                fail("READ ha fallat");
            }
        });

        // UPDATE
        treballador.setNom("Joan Josep");
        when(treballadorApiMock.updateTreballador(eq(treballador), anyString())).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        treballadorService.actualitzarTreballador(treballador, token, new Callback<>() {
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
        Long treballadorId = 1L;
        when(treballadorApiMock.deleteTreballador(eq(treballadorId), anyString())).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        treballadorService.eliminarTreballador(treballadorId, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                assertTrue(response.isSuccessful());
                System.out.println("DELETE correcte");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                fail("DELETE ha fallat");
            }
        });
    }
}

