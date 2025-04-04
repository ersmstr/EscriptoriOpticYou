package opticyou.OpticYou.clinica;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClinicaController {

    private ClinicaCrudScreen screen;
    private ClinicaService service;
    private String token;
    private List<Clinica> llistaClinicas;


    public ClinicaController(ClinicaCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new ClinicaService();

        System.out.println("Token rebut al controller: " + token);

        initListeners();
        carregarClinicas();
    }

    private void initListeners() {
        screen.setAfegirListener(e -> {
            String nom = screen.getNomCentre();
            String direccio = screen.getDireccio();
            String telefon = screen.getTelefon();
            String horariObertura = screen.getHorariApertura();
            String horariTancament = screen.getHorariTancament();
            String email = screen.getEmail();

            if (nom.isBlank() || direccio.isBlank() || telefon.isBlank()
                    || horariObertura.isBlank() || horariTancament.isBlank() || email.isBlank()) {

                JOptionPane.showMessageDialog(screen, "Tots els camps són obligatoris.", "Formulari incomplet", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Clinica clinica = screen.crearClinicaDesdeFormulario();
            clinica.setIdClinica(0L); // o null si ho permet

            service.agregarClinica(clinica, token, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        JOptionPane.showMessageDialog(screen, "Clínica creada correctament.");
                        screen.clearForm();
                        carregarClinicas(); // només un cop creada
                    } else {
                        JOptionPane.showMessageDialog(screen, "Error creant clínica. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        screen.setEliminarListener(e -> eliminarClinicaSeleccionada());
        screen.setActualitzarListener(e -> actualitzarClinicaSeleccionada());
    }



    public void carregarClinicas() {
        System.out.println("Executant carregarClinicas()...");
        service.carregarClinicas(token, new Callback<List<Clinica>>() {
            @Override
            public void onResponse(Call<List<Clinica>> call, Response<List<Clinica>> response) {
                System.out.println("Resposta rebuda del servidor!");
                if (response.isSuccessful() && response.body() != null) {
                    llistaClinicas = response.body();
                    llistaClinicas.sort((c1, c2) -> Long.compare(c1.getIdClinica(), c2.getIdClinica()));

                    DefaultTableModel model = (DefaultTableModel) screen.getClinicaTable().getModel();
                    model.setRowCount(0); // Esborra les files existents

                    for (Clinica c : llistaClinicas) {
                        model.addRow(new Object[]{
                                c.getIdClinica(),
                                c.getNom(),
                                c.getDireccio(),
                                c.getTelefon(),
                                c.getHorari_opertura(),
                                c.getHorari_tancament(),
                                c.getEmail()
                        });
                    }

                } else {
                    System.err.println("Resposta amb error: " + response.code());
                    JOptionPane.showMessageDialog(screen, "Error carregant les clíniques: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Clinica>> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage());
            }
        });
    }
    public Clinica getClinicaPerFila(int fila) {
        if (llistaClinicas != null && fila >= 0 && fila < llistaClinicas.size()) {
            return llistaClinicas.get(fila);
        }
        return null;
    }

    private void eliminarClinicaSeleccionada() {
        Long id = screen.getIdClinicaSeleccionada();

        if (id == null) {
            JOptionPane.showMessageDialog(screen, "Selecciona una clínica per eliminar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(screen,
                "Segur que vols eliminar la clínica amb ID " + id + "?",
                "Confirmació", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        service.eliminarClinica(id, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Clínica eliminada correctament.");
                    screen.getClinicaTable().clearSelection();
                    screen.clearForm();
                    carregarClinicas();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error eliminant clínica. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void actualitzarClinicaSeleccionada() {
        Long id = screen.getIdClinicaSeleccionada();

        if (id == null) {
            JOptionPane.showMessageDialog(screen, "Selecciona una clínica per actualitzar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Clinica actualitzada = screen.crearClinicaDesdeFormulario();
        actualitzada.setIdClinica(id); // 🔑 Reutilitzam la id seleccionada

        service.actualitzarClinica(actualitzada, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Clínica actualitzada correctament.");
                    carregarClinicas();
                    screen.clearForm();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error actualitzant clínica. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


}
