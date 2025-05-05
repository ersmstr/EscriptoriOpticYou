package opticyou.OpticYou.rolAdmin.treballador;

import opticyou.OpticYou.model.Treballador;
import opticyou.OpticYou.service.TreballadorService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.List;

public class TreballadorController {

    private final TreballadorCrudScreen screen;
    private final TreballadorService service;
    private final String token;
    private List<Treballador> llistaTreballadors;

    public TreballadorController(TreballadorCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new TreballadorService();

        System.out.println("Token rebut al controller (Treballador): " + token);

        initListeners();
        carregarTreballadors();
        initTableSelection();
    }

    private void initListeners() {
        screen.setAfegirListener(e -> afegirTreballador());
        screen.setEliminarListener(e -> eliminarTreballadorSeleccionat());
        screen.setModificarListener(e -> actualitzarTreballadorSeleccionat());
    }

    private boolean validarFormulari() {
        if (screen.getNom().isBlank() || screen.getEmail().isBlank() || screen.getContrasenya().isBlank()
                || screen.getEspecialitat().isBlank() || screen.getIniciJornada().isBlank()
                || screen.getDiesJornada().isBlank() || screen.getFiJornada().isBlank()) {
            JOptionPane.showMessageDialog(screen, "Tots els camps són obligatoris.", "Formulari incomplet", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void afegirTreballador() {
        if (!validarFormulariCreacio()) {
            JOptionPane.showMessageDialog(screen, "Tots els camps són obligatoris per crear el treballador.", "Formulari incomplet", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Treballador treballador = screen.crearTreballadorDesdeFormulari();
        treballador.setIdTreballador(null);

        service.createTreballador(treballador, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Treballador creat correctament.");
                    screen.clearForm();
                    carregarTreballadors();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error creant treballador. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void eliminarTreballadorSeleccionat() {
        Long id = screen.getIdTreballadorSeleccionat();

        if (id == -1) {
            JOptionPane.showMessageDialog(screen, "Selecciona un treballador per eliminar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(screen,
                "Segur que vols eliminar el treballador amb ID " + id + "?",
                "Confirmació", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        service.eliminarTreballador(id, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Treballador eliminat correctament.");
                    screen.getTreballadorTable().clearSelection();
                    screen.clearForm();
                    carregarTreballadors();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error eliminant treballador. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void actualitzarTreballadorSeleccionat() {
        Long id = screen.getIdTreballadorSeleccionat();

        if (id == -1) {
            JOptionPane.showMessageDialog(screen, "Selecciona un treballador per actualitzar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validarFormulariModificacio()) {
            JOptionPane.showMessageDialog(screen, "Tots els camps (excepte contrasenya) són obligatoris per modificar.", "Formulari incomplet", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Treballador actualitzat = screen.crearTreballadorDesdeFormulari();
        actualitzat.setIdTreballador(id);

        service.actualitzarTreballador(actualitzat, token, new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Treballador actualitzat correctament.");
                    carregarTreballadors();
                    screen.clearForm();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error actualitzant treballador. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    public void carregarTreballadors() {
        service.carregarTreballadors(token, new Callback<>() {
            @Override
            public void onResponse(Call<List<Treballador>> call, Response<List<Treballador>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    llistaTreballadors = response.body();
                    llistaTreballadors.sort(Comparator.comparingLong(Treballador::getIdTreballador));

                    DefaultTableModel model = (DefaultTableModel) screen.getTreballadorTable().getModel();
                    model.setRowCount(0);

                    for (Treballador t : llistaTreballadors) {
                        model.addRow(new Object[]{
                                t.getIdTreballador(),
                                t.getNom(),
                                t.getEmail(),
                                t.getEspecialitat(),
                                t.getEstat(),
                                t.getIniciJornada(),
                                t.getDiesJornada(),
                                t.getFiJornada(),
                                t.getClinicaId()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(screen, "Error carregant treballadors: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Treballador>> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage());
            }
        });
    }

    private void initTableSelection() {
        screen.getTreballadorTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = screen.getTreballadorTable().getSelectedRow();
                if (fila >= 0) {
                    Treballador t = getTreballadorPerFila(fila);
                    if (t != null) {
                        screen.carregarTreballadorAlFormulari(t);
                    }
                }
            }
        });
    }

    public Treballador getTreballadorPerFila(int fila) {
        if (llistaTreballadors != null && fila >= 0 && fila < llistaTreballadors.size()) {
            return llistaTreballadors.get(fila);
        }
        return null;
    }
    private boolean validarFormulariCreacio() {
        return !screen.getNom().isBlank()
                && !screen.getEmail().isBlank()
                && !screen.getContrasenya().isBlank()
                && !screen.getEspecialitat().isBlank()
                && !screen.getEstat().isBlank()
                && !screen.getIniciJornada().isBlank()
                && !screen.getDiesJornada().isBlank()
                && !screen.getFiJornada().isBlank()
                && !screen.getClinicaId().isBlank();
    }

    private boolean validarFormulariModificacio() {
        return !screen.getNom().isBlank()
                && !screen.getEmail().isBlank()
                && !screen.getEspecialitat().isBlank()
                && !screen.getEstat().isBlank()
                && !screen.getIniciJornada().isBlank()
                && !screen.getDiesJornada().isBlank()
                && !screen.getFiJornada().isBlank()
                && !screen.getClinicaId().isBlank();
    }

}
