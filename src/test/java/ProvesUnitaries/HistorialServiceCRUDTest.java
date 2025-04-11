package ProvesUnitaries;

import opticyou.OpticYou.model.Historial;
import opticyou.OpticYou.data.HistorialApi;
import opticyou.OpticYou.service.HistorialService;
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
/**
 * Autor: mrami
 */

public class HistorialServiceCRUDTest {

    @Mock
    private HistorialApi historialApiMock;

    @Mock
    private Call<List<Historial>> callMockList;

    @Mock
    private Call<Void> callMockVoid;

    private HistorialService historialService;
    private final String token = "fake-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear servei amb mock injectat via "truc" (perquè és una classe simple)
        historialService = new HistorialService() {
            @Override
            public void carregarHistorials(String token, Callback<List<Historial>> callback) {
                historialApiMock.getAllHistorials("Bearer " + token).enqueue(callback);
            }

            @Override
            public void actualitzarHistorial(Historial historial, String token, Callback<Void> callback) {
                historialApiMock.updateHistorial("Bearer " + token, historial).enqueue(callback);
            }
        };
    }

    @Test
    void testCarregarHistorials() {
        List<Historial> mockLlista = new ArrayList<>();
        mockLlista.add(new Historial()); // afegeix un historial buit com a exemple

        when(historialApiMock.getAllHistorials(anyString())).thenReturn(callMockList);

        doAnswer(invocation -> {
            Callback<List<Historial>> callback = invocation.getArgument(0);
            callback.onResponse(callMockList, Response.success(mockLlista));
            return null;
        }).when(callMockList).enqueue(any());

        historialService.carregarHistorials(token, new Callback<>() {
            @Override
            public void onResponse(Call<List<Historial>> call, Response<List<Historial>> response) {
                assertNotNull(response.body());
                assertEquals(1, response.body().size());
                System.out.println("READ historial correcte");
            }

            @Override
            public void onFailure(Call<List<Historial>> call, Throwable t) {
                fail("READ ha fallat");
            }
        });
    }

    @Test
    void testActualitzarHistorial() {
        Historial h = new Historial(); // pots afegir camps si vols provar més
        when(historialApiMock.updateHistorial(anyString(), eq(h))).thenReturn(callMockVoid);

        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        historialService.actualitzarHistorial(h, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                assertTrue(response.isSuccessful());
                System.out.println("UPDATE historial correcte");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                fail("UPDATE ha fallat");
            }
        });
    }
}

