package ProvesUnitaries;

import opticyou.OpticYou.clinica.*;
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
public class ClinicaServiceCRUDTest {

        @Mock
        private ClinicaApi clinicaApiMock;

        @Mock
        private Call<Void> callMockVoid;

        @Mock
        private Call<List<Clinica>> callMockList;

        private ClinicaService clinicaService;

        private final String token = "fake-token";

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            ClinicaApiProvider mockProvider = t -> clinicaApiMock;
            clinicaService = new ClinicaService(mockProvider);
        }

        @Test
        void testCRUDClinica() {
            Clinica clinica = new Clinica(
                    "Clínica Test", "Carrer Test", "600000000", "08:00", "18:00", "test@clinica.com"
            );
            clinica.setIdClinica(1L);

            // ---------------------
            // CREATE
            // ---------------------
            when(clinicaApiMock.createClinica(anyString(), eq(clinica))).thenReturn(callMockVoid);

            doAnswer(invocation -> {
                Callback<Void> callback = invocation.getArgument(0);
                callback.onResponse(callMockVoid, Response.success(null));
                return null;
            }).when(callMockVoid).enqueue(any());

            clinicaService.agregarClinica(clinica, token, new Callback<>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    assertTrue(response.isSuccessful());
                    System.out.println("✔️ CREATE correcte");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    fail("❌ CREATE ha fallat: " + t.getMessage());
                }
            });

            // ---------------------
            // READ
            // ---------------------
            List<Clinica> mockLlista = new ArrayList<>();
            mockLlista.add(clinica);

            when(clinicaApiMock.getAllClinicas(any())).thenReturn(callMockList);
            doAnswer(invocation -> {
                Callback<List<Clinica>> callback = invocation.getArgument(0);
                callback.onResponse(callMockList, Response.success(mockLlista));
                return null;
            }).when(callMockList).enqueue(any());

            clinicaService.carregarClinicas(token, new Callback<>() {
                @Override
                public void onResponse(Call<List<Clinica>> call, Response<List<Clinica>> response) {
                    assertNotNull(response.body());
                    assertEquals(1, response.body().size());
                    System.out.println("✔️ READ correcte");
                }

                @Override
                public void onFailure(Call<List<Clinica>> call, Throwable t) {
                    fail("❌ READ ha fallat: " + t.getMessage());
                }
            });

            // ---------------------
            // UPDATE
            // ---------------------
            clinica.setTelefon("999888777");
            when(clinicaApiMock.updateClinica(clinica)).thenReturn(callMockVoid);
            doAnswer(invocation -> {
                Callback<Void> callback = invocation.getArgument(0);
                callback.onResponse(callMockVoid, Response.success(null));
                return null;
            }).when(callMockVoid).enqueue(any());

            clinicaService.actualitzarClinica(clinica, token, new Callback<>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    assertTrue(response.isSuccessful());
                    System.out.println("✔️ UPDATE correcte");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    fail("❌ UPDATE ha fallat: " + t.getMessage());
                }
            });

            // ---------------------
            // DELETE
            // ---------------------
            when(clinicaApiMock.deleteClinica(clinica.getIdClinica(), token)).thenReturn(callMockVoid);
            doAnswer(invocation -> {
                Callback<Void> callback = invocation.getArgument(0);
                callback.onResponse(callMockVoid, Response.success(null));
                return null;
            }).when(callMockVoid).enqueue(any());

            clinicaService.eliminarClinica(clinica.getIdClinica(), token, new Callback<>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    assertTrue(response.isSuccessful());
                    System.out.println("✔️ DELETE correcte");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    fail("DELETE ha fallat: " + t.getMessage());
                }
            });
        }
    }


