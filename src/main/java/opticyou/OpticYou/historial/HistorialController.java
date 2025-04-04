package opticyou.OpticYou.historial;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import opticyou.OpticYou.historial.*;

/**
 * Autor: mrami
 */
public class HistorialController {
    private HistorialCrudScreen screen;
    private HistorialService service;
    private String token;
    private List<Historial> llistaHistorials;

    public HistorialController(HistorialCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new HistorialService();

        System.out.println("Token rebut al controller (Historial): " + token);

        initListeners();
        carregarHistorials();
    }
    private void initListeners() {
        screen.setAfegirListener(e -> {
            String patologies = screen.getPatologies();

            if (patologies.isBlank()) {
                JOptionPane.showMessageDialog(screen, "El camp de patologies és obligatori.", "Formulari incomplet", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Historial historial = screen.crearHistorialDesdeFormulari();
            historial.setIdhistorial(0L); // o null si l'API ho permet

            service.crearHistorial(historial, token, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        JOptionPane.showMessageDialog(screen, "Historial creat correctament.");
                        screen.clearForm();
                        carregarHistorials();
                    } else {
                        JOptionPane.showMessageDialog(screen, "Error creant historial. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        screen.setEliminarListener(e -> eliminarHistorialSeleccionat());
        screen.setActualitzarListener(e -> actualitzarHistorialSeleccionat());
    }

    public void carregarHistorials() {
        System.out.println("Executant carregarHistorials()...");
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
                                h.getData_creacio(),
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

    public Historial getHistorialPerFila(int fila) {
        if (llistaHistorials != null && fila >= 0 && fila < llistaHistorials.size()) {
            return llistaHistorials.get(fila);
        }
        return null;
    }

    private void eliminarHistorialSeleccionat() {
        Long id = screen.getIdHistorialSeleccionat();

        if (id == null) {
            JOptionPane.showMessageDialog(screen, "Selecciona un historial per eliminar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(screen,
                "Segur que vols eliminar l'historial amb ID " + id + "?",
                "Confirmació", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        service.eliminarHistorial(id, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Historial eliminat correctament.");
                    screen.getHistorialTable().clearSelection();
                    screen.clearForm();
                    carregarHistorials();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error eliminant historial. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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



}
