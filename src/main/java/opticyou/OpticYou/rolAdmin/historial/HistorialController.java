package opticyou.OpticYou.rolAdmin.historial;

import opticyou.OpticYou.model.Historial;
import opticyou.OpticYou.service.HistorialService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 * autor mramis
 */

/**
 * Controlador per gestionar les operacions relacionades amb historials mèdics.
 * Permet carregar i actualitzar historials mitjançant la interfície gràfica i serveis REST.
 */
public class HistorialController {

    private HistorialCrudScreen screen;
    private HistorialService service;
    private String token;
    private List<Historial> llistaHistorials;

    /**
     * Constructor del controlador d'historials.
     *
     * @param screen Pantalla de CRUD d'historials.
     * @param token  Token d'autenticació per fer les peticions.
     */
    public HistorialController(HistorialCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new HistorialService();

        initListeners();
        carregarHistorials();
    }

    /**
     * Inicialitza els listeners de la UI.
     * Assigna funcionalitat al botó d'actualitzar historial.
     */
    private void initListeners() {
        screen.setActualitzarListener(e -> actualitzarHistorialSeleccionat());
    }

    /**
     * Carrega la llista d'historials des del servei i omple la taula de la UI.
     * Ordena els historials per ID.
     */
    public void carregarHistorials() {
        service.carregarHistorials(token, new Callback<List<Historial>>() {
            @Override
            public void onResponse(Call<List<Historial>> call, Response<List<Historial>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    llistaHistorials = response.body();
                    llistaHistorials.sort((h1, h2) -> Long.compare(h1.getIdhistorial(), h2.getIdhistorial()));

                    DefaultTableModel model = (DefaultTableModel) screen.getHistorialTable().getModel();
                    model.setRowCount(0);

                    for (Historial h : llistaHistorials) {
                        model.addRow(new Object[]{
                                h.getIdhistorial(),
                                h.getPatologies()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(screen, "Error carregant historials: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Historial>> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage());
            }
        });
    }

    /**
     * Actualitza l'historial seleccionat amb les dades del formulari.
     * Mostra missatges d'estat segons el resultat de l'operació.
     */
    private void actualitzarHistorialSeleccionat() {
        Long id = screen.getIdHistorialSeleccionat();

        if (id == null) {
            JOptionPane.showMessageDialog(screen, "Selecciona un historial per actualitzar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Historial actualitzat = screen.crearHistorialDesdeFormulari();
        actualitzat.setIdhistorial(id);

        service.actualitzarHistorial(actualitzat, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Historial actualitzat correctament.");
                    carregarHistorials();
                    screen.clearForm();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error actualitzant historial. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Retorna l'historial que correspon a una fila seleccionada de la taula.
     *
     * @param fila Índex de la fila seleccionada.
     * @return L'objecte {@link Historial} o null si no hi ha cap historial vàlid en aquella fila.
     */
    public Historial getHistorialPerFila(int fila) {
        if (llistaHistorials != null && fila >= 0 && fila < llistaHistorials.size()) {
            return llistaHistorials.get(fila);
        }
        return null;
    }
}
