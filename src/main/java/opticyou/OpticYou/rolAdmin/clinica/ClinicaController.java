package opticyou.OpticYou.rolAdmin.clinica;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controlador per gestionar operacions de CRUD sobre clíniques mitjançant la interfície gràfica i serveis REST.
 */
/**
 * Autor: mramis
 */
public class ClinicaController {

    private ClinicaCrudScreen screen;
    private ClinicaService service;
    private String token;
    private List<Clinica> llistaClinicas;

    /**
     * Constructor del controlador de clíniques.
     *
     * @param screen Pantalla de CRUD de clíniques.
     * @param token Token d'autenticació per fer les peticions.
     */
    public ClinicaController(ClinicaCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new ClinicaService();

        initListeners();
        carregarClinicas();
    }

    /**
     * Inicialitza els listeners dels botons de la UI.
     * Assigna funcionalitat als botons d'afegir, eliminar i actualitzar clíniques.
     */
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
            clinica.setIdClinica(0L);

            service.agregarClinica(clinica, token, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        JOptionPane.showMessageDialog(screen, "Clínica creada correctament.");
                        screen.clearForm();
                        carregarClinicas();
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

    /**
     * Carrega la llista de clíniques des del servei i omple la taula de la UI.
     * Ordena les clíniques per ID abans d'afegir-les a la taula.
     */
    public void carregarClinicas() {
        service.carregarClinicas(token, new Callback<List<Clinica>>() {
            @Override
            public void onResponse(Call<List<Clinica>> call, Response<List<Clinica>> response) {
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
                    JOptionPane.showMessageDialog(screen, "Error carregant les clíniques: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Clinica>> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage());
            }
        });
    }

    /**
     * Retorna la clínica corresponent a la fila seleccionada de la taula.
     *
     * @param fila Índex de la fila seleccionada.
     * @return L'objecte {@link Clinica} seleccionat, o null si l'índex és invàlid.
     */
    public Clinica getClinicaPerFila(int fila) {
        if (llistaClinicas != null && fila >= 0 && fila < llistaClinicas.size()) {
            return llistaClinicas.get(fila);
        }
        return null;
    }

    /**
     * Elimina la clínica seleccionada a la taula, amb confirmació prèvia de l'usuari.
     * Actualitza la taula després de l'eliminació.
     */
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
                    screen.setIdClinicaSeleccionada(null);
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

    /**
     * Actualitza la informació de la clínica seleccionada amb les dades del formulari.
     * Mostra missatges d'estat segons el resultat de l'operació.
     */
    private void actualitzarClinicaSeleccionada() {
        Long id = screen.getIdClinicaSeleccionada();

        if (id == null) {
            JOptionPane.showMessageDialog(screen, "Selecciona una clínica per actualitzar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Clinica actualitzada = screen.crearClinicaDesdeFormulario();
        actualitzada.setIdClinica(id);

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
