package ProvesUnitaries;

/**
 * Autor: mramis
 */

import opticyou.OpticYou.data.DiagnosticApi;
import opticyou.OpticYou.model.Diagnostic;
import opticyou.OpticYou.service.DiagnosticService;
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

public class DiagnosticServiceCRUDTest {

    @Mock
    private DiagnosticApi diagnosticApiMock;

    @Mock
    private Call<Void> callMockVoid;

    @Mock
    private Call<List<Diagnostic>> callMockList;

    private DiagnosticService diagnosticService;
    private final String token = "fake-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Injectem el mock manualment
        diagnosticService = new DiagnosticService(token) {
            @Override
            public void getDiagnostics(Long historialId, Callback<List<Diagnostic>> callback) {
                diagnosticApiMock.getDiagnosticByHistorial(historialId).enqueue(callback);
            }

            @Override
            public void crearDiagnostic(Diagnostic diagnostic, Callback<Void> callback) {
                diagnosticApiMock.crearDiagnostic(diagnostic).enqueue(callback);
            }

            @Override
            public void actualitzarDiagnostic(Diagnostic diagnostic, Callback<Void> callback) {
                diagnosticApiMock.actualitzarDiagnostic(diagnostic).enqueue(callback);
            }

            @Override
            public void esborrarDiagnostic(Long idDiagnostic, Callback<Void> callback) {
                diagnosticApiMock.esborrarDiagnostic(idDiagnostic).enqueue(callback);
            }
        };
    }

    @Test
    void testDiagnosticCRUD() {
        Diagnostic diagnostic = new Diagnostic(null, "Miopia", "2025-05-12T15:00:00", 1L);

        // CREATE
        when(diagnosticApiMock.crearDiagnostic(eq(diagnostic))).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        diagnosticService.crearDiagnostic(diagnostic, new Callback<>() {
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
        List<Diagnostic> llista = new ArrayList<>();
        llista.add(diagnostic);

        when(diagnosticApiMock.getDiagnosticByHistorial(anyLong())).thenReturn(callMockList);
        doAnswer(invocation -> {
            Callback<List<Diagnostic>> callback = invocation.getArgument(0);
            callback.onResponse(callMockList, Response.success(llista));
            return null;
        }).when(callMockList).enqueue(any());

        diagnosticService.getDiagnostics(1L, new Callback<>() {
            @Override
            public void onResponse(Call<List<Diagnostic>> call, Response<List<Diagnostic>> response) {
                assertNotNull(response.body());
                assertEquals(1, response.body().size());
                System.out.println("READ correcte");
            }

            @Override
            public void onFailure(Call<List<Diagnostic>> call, Throwable t) {
                fail("READ ha fallat");
            }
        });

        // UPDATE
        diagnostic.setDescripcio("Miopia");
        when(diagnosticApiMock.actualitzarDiagnostic(eq(diagnostic))).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        diagnosticService.actualitzarDiagnostic(diagnostic, new Callback<>() {
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
        Long diagnosticId = 1L;
        when(diagnosticApiMock.esborrarDiagnostic(eq(diagnosticId))).thenReturn(callMockVoid);
        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(callMockVoid, Response.success(null));
            return null;
        }).when(callMockVoid).enqueue(any());

        diagnosticService.esborrarDiagnostic(diagnosticId, new Callback<>() {
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
