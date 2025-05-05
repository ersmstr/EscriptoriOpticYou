package opticyou.OpticYou.service;

import opticyou.OpticYou.data.DiagnosticApi;
import opticyou.OpticYou.service.auth.RetrofitApp;
import retrofit2.Callback;

/**
 * Servei que encapsula les operacions relacionades amb els diagnòstics mèdics.
 * Utilitza {@link DiagnosticApi} per accedir al backend mitjançant Retrofit.

 */

/**
 * Autor: mrami
 */


public class DiagnosticService {


        public void getDiagnostic(Long historialId, String token, Callback<String> callback) {
            DiagnosticApi api = RetrofitApp.getDiagnosticApi(token);
            api.getDiagnostic(historialId).enqueue(callback);
        }

        public void guardarDiagnostic(Long historialId, String text, String token, Callback<Void> callback) {
            DiagnosticApi api = RetrofitApp.getDiagnosticApi(token);
            api.guardarDiagnostic(historialId, text).enqueue(callback);
        }

        public void esborrarDiagnostic(Long historialId, String token, Callback<Void> callback) {
            DiagnosticApi api = RetrofitApp.getDiagnosticApi(token);
            api.esborrarDiagnostic(historialId).enqueue(callback);
        }
    }





