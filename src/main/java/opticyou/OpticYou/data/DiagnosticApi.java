package opticyou.OpticYou.data;

import opticyou.OpticYou.model.Diagnostic;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Interfície d'API de Retrofit per a la gestió de diagnòstics.
 * <p>
 * Defineix les operacions disponibles sobre els diagnòstics:
 * obtenir per historial, crear, actualitzar i esborrar.
 * </p>
 */

/**
 * autor mramis
 */
public interface DiagnosticApi {

        /**
         * Obté la llista de diagnòstics associats a un historial mèdic.
         *
         * @param historialId Identificador de l'historial mèdic.
         * @return Crida Retrofit amb una llista de diagnòstics.
         */
        @GET("/diagnostic/historial/{id}")
        Call<List<Diagnostic>> getDiagnosticByHistorial(@Path("id") Long historialId);

        /**
         * Crea un nou diagnòstic.
         *
         * @param diagnostic Objecte {@link Diagnostic} a crear.
         * @return Crida Retrofit que retorna un {@code Void} si té èxit.
         */
        @POST("/diagnostic")
        Call<Void> crearDiagnostic(@Body Diagnostic diagnostic);

        /**
         * Actualitza un diagnòstic existent.
         *
         * @param diagnostic Objecte {@link Diagnostic} amb les dades actualitzades.
         * @return Crida Retrofit que retorna un {@code Void} si té èxit.
         */
        @PUT("/diagnostic/update")
        Call<Void> actualitzarDiagnostic(@Body Diagnostic diagnostic);

        /**
         * Esborra un diagnòstic pel seu identificador.
         *
         * @param idDiagnostic Identificador únic del diagnòstic a eliminar.
         * @return Crida Retrofit que retorna un {@code Void} si té èxit.
         */
        @DELETE("/diagnostic/{id}")
        Call<Void> esborrarDiagnostic(@Path("id") Long idDiagnostic);
}
