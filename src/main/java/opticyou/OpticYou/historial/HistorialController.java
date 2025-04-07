package opticyou.OpticYou.historial;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class HistorialController {

    private HistorialCrudScreen screen;
    private HistorialService service;
    private String token;
    private List<Historial> llistaHistorials;

    public HistorialController(HistorialCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new HistorialService();

        initListeners();
        carregarHistorials();
    }

    private void initListeners() {
        screen.setActualitzarListener(e -> actualitzarHistorialSeleccionat());
    }

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

    public Historial getHistorialPerFila(int fila) {
        if (llistaHistorials != null && fila >= 0 && fila < llistaHistorials.size()) {
            return llistaHistorials.get(fila);
        }
        return null;
    }
}
