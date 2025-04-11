package opticyou.OpticYou.rolAdmin.clients;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.List;
/**
 * autor mramis
 */

/**
 * Controlador per gestionar operacions de CRUD sobre clients mitjançant una interfície gràfica i serveis REST.
 */
public class ClientController {

    private ClientCrudScreen screen;
    private ClientService service;
    private String token;
    private List<Client> llistaClients;

    /**
     * Constructor del controlador de clients.
     *
     * @param screen Pantalla de CRUD de clients.
     * @param token Token d'autenticació per a les peticions.
     */
    public ClientController(ClientCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new ClientService();

        System.out.println("Token rebut al controller (Client): " + token);

        initListeners();
        carregarClients();
    }

    /**
     * Inicialitza els listeners dels botons de la UI.
     * Assigna funcionalitat als botons de crear, eliminar i modificar clients.
     */
    private void initListeners() {
        screen.setAfegirListener(e -> {
            String nom = screen.getNom();
            String dataNaixament = screen.getDataNaixament();
            String sexe = screen.getSexe();
            String telefon = screen.getTelefon();
            String clinicaId = screen.getClinicaId();
            String email = screen.getEmail();
            String contrasenya = screen.getContrasenya();
            String rol = screen.getRol();

            if (dataNaixament.isBlank() || sexe.isBlank() || telefon.isBlank()) {
                JOptionPane.showMessageDialog(screen, "Tots els camps són obligatoris.", "Formulari incomplet", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Client client = screen.crearClientDesdeFormulari();
            client.setIdClient(null);

            service.createClient(client, token, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        JOptionPane.showMessageDialog(screen, "Client creat correctament.");
                        screen.clearForm();
                        carregarClients();
                    } else {
                        JOptionPane.showMessageDialog(screen, "Error creant client. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        screen.setEliminarListener(e -> eliminarClientSeleccionat());
        screen.setModificarListener(e -> actualitzarClientSeleccionat());
    }

    /**
     * Carrega la llista de clients des del servei i omple la taula de la UI.
     * Ordena els clients per ID abans d'afegir-los.
     */
    public void carregarClients() {
        service.carregarClients(token, new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    llistaClients = response.body();
                    llistaClients.sort(Comparator.comparingLong(Client::getIdClient));

                    DefaultTableModel model = (DefaultTableModel) screen.getClientTable().getModel();
                    model.setRowCount(0);

                    for (Client c : llistaClients) {
                        model.addRow(new Object[]{
                                c.getIdClient(),
                                c.getNom(),
                                c.getEmail(),
                                c.getDataNaixament(),
                                c.getSexe(),
                                c.getTelefon(),
                                c.getClinicaId(),
                                c.getHistorialId()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(screen, "Error carregant clients: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage());
            }
        });
    }

    /**
     * Retorna un client basat en la fila seleccionada a la taula.
     *
     * @param fila L'índex de la fila seleccionada.
     * @return El client corresponent o null si l'índex és invàlid.
     */
    public Client getClientPerFila(int fila) {
        if (llistaClients != null && fila >= 0 && fila < llistaClients.size()) {
            return llistaClients.get(fila);
        }
        return null;
    }

    /**
     * Elimina el client seleccionat a la taula si l'usuari ho confirma.
     * Actualitza la taula després de l'eliminació.
     */
    private void eliminarClientSeleccionat() {
        Long id = screen.getIdClientSeleccionat();

        if (id == -1) {
            JOptionPane.showMessageDialog(screen, "Selecciona un client per eliminar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(screen,
                "Segur que vols eliminar el client amb ID " + id + "?",
                "Confirmació", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        service.eliminarClient(id, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Client eliminat correctament.");
                    screen.getClientTable().clearSelection();
                    screen.clearForm();
                    carregarClients();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error eliminant client. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Actualitza el client seleccionat amb les dades actuals del formulari.
     * Mostra missatges d'error si la selecció és invàlida o la connexió falla.
     */
    private void actualitzarClientSeleccionat() {
        Long id = screen.getIdClientSeleccionat();

        if (id == -1) {
            JOptionPane.showMessageDialog(screen, "Selecciona un client per actualitzar.", "Cap selecció", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Client actualitzat = screen.crearClientDesdeFormulari();
        actualitzat.setIdClient(id);

        service.actualitzarClient(actualitzat, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(screen, "Client actualitzat correctament.");
                    carregarClients();
                    screen.clearForm();
                } else {
                    JOptionPane.showMessageDialog(screen, "Error actualitzant client. Codi: " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                JOptionPane.showMessageDialog(screen, "Error de connexió: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
