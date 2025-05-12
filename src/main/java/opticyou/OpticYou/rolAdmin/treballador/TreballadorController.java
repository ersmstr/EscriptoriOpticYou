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

/**
 * Controlador per gestionar lògica de negoci i accions relacionades amb la gestió de treballadors.
 * <p>
 * Interactua amb la pantalla {@link TreballadorCrudScreen} i amb el servei {@link TreballadorService}
 * per realitzar operacions CRUD (crear, llegir, actualitzar i eliminar treballadors).
 * </p>
 *
 * @author mrami
 */
public class TreballadorController {

    /** Referència a la interfície gràfica de gestió de treballadors. */
    private final TreballadorCrudScreen screen;

    /** Servei que proporciona les operacions d'accés a dades dels treballadors. */
    private final TreballadorService service;

    /** Token JWT per a l'autenticació de l'usuari actual. */
    private final String token;

    /** Llista de treballadors recuperats del backend. */
    private List<Treballador> llistaTreballadors;

    /**
     * Constructor del controlador. Inicia els listeners, la taula i carrega les dades.
     *
     * @param screen Pantalla de gestió de treballadors.
     * @param token  Token d'autenticació.
     */
    public TreballadorController(TreballadorCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new TreballadorService();

        System.out.println("Token rebut al controller (Treballador): " + token);

        initListeners();
        carregarTreballadors();
        initTableSelection();
    }

    /**
     * Assigna els listeners als botons de la pantalla.
     */
    private void initListeners() {
        screen.setAfegirListener(e -> afegirTreballador());
        screen.setEliminarListener(e -> eliminarTreballadorSeleccionat());
        screen.setModificarListener(e -> actualitzarTreballadorSeleccionat());
    }

    /**
     * Valida si el formulari conté tots els camps obligatoris (excepte contrasenya).
     *
     * @return Cert si el formulari és vàlid, fals altrament.
     */
    private boolean validarFormulari() {
        return !(screen.getNom().isBlank() || screen.getEmail().isBlank() || screen.getContrasenya().isBlank()
                || screen.getEspecialitat().isBlank() || screen.getIniciJornada().isBlank()
                || screen.getDiesJornada().isBlank() || screen.getFiJornada().isBlank());
    }

    /**
     * Afegeix un nou treballador utilitzant les dades del formulari.
     */
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

    /**
     * Elimina el treballador seleccionat a la taula després de confirmació.
     */
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

    /**
     * Actualitza el treballador seleccionat amb les dades del formulari.
     */
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

    /**
     * Carrega la llista de treballadors des del backend i l’omple a la taula.
     */
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

    /**
     * Inicialitza el comportament de selecció de files a la taula per carregar dades al formulari.
     */
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

    /**
     * Obté el treballador de la llista en funció de la fila seleccionada.
     *
     * @param fila Índex de fila seleccionada.
     * @return Treballador corresponent o null si no és vàlid.
     */
    public Treballador getTreballadorPerFila(int fila) {
        if (llistaTreballadors != null && fila >= 0 && fila < llistaTreballadors.size()) {
            return llistaTreballadors.get(fila);
        }
        return null;
    }

    /**
     * Valida que tots els camps del formulari estiguin plens per crear un treballador.
     *
     * @return true si tots els camps són vàlids.
     */
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

    /**
     * Valida que tots els camps (excepte contrasenya) estiguin plens per modificar un treballador.
     *
     * @return true si els camps requerits són vàlids.
     */
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
