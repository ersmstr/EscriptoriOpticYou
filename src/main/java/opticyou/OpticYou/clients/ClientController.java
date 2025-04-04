package opticyou.OpticYou.clients;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.List;

public class ClientController {

    private ClientCrudScreen screen;
    private ClientService service;
    private String token;
    private List<Client> llistaClients;

    public ClientController(ClientCrudScreen screen, String token) {
        this.screen = screen;
        this.token = token;
        this.service = new ClientService();

        System.out.println("Token rebut al controller (Client): " + token);

        initListeners();
        carregarClients();
    }

    private void initListeners() {
        screen.setAfegirListener(e -> {
                   // String IdClient = screen.getIdClient();
                    String dataNaixament = screen.getDataNaixament();
                    String sexe = screen.getSexe();
                    String telefon = screen.getTelefon();
                    String clinicaId = screen.getClinicaId();
                   // String historialId = screen.getHistorialId();
                    String nom = screen.getNom();
                    String email = screen.getEmail();
                    String contrasenya = screen.getContrasenya();
                    String rol = screen.getRol();

                    if (dataNaixament.isBlank() || sexe.isBlank()
                            || telefon.isBlank()) {
                        JOptionPane.showMessageDialog(screen, "Tots els camps són obligatoris.", "Formulari incomplet", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Client client = screen.crearClientDesdeFormulari();
                    client.setIdClient(null); //

                    service.createClient(client, token, new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                JOptionPane.showMessageDialog(screen, "Client creat correctament.");
                                screen.clearForm();
                                carregarClients(); // només ara recarreguem per veure el client nou
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

    public void carregarClients() {
        System.out.println("Executant carregarClients()...");
        service.carregarClients(token, new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                System.out.println("Resposta rebuda del servidor (Clients)");
                if (response.isSuccessful() && response.body() != null) {
                    llistaClients = response.body();
                    llistaClients.sort(Comparator.comparingLong(Client::getIdClient));

                    DefaultTableModel model = (DefaultTableModel) screen.getClientTable().getModel();
                    model.setRowCount(0); // esborra la taula

                    for (Client c : llistaClients) {
                        model.addRow(new Object[]{
                                c.getIdClient(),
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

    public Client getClientPerFila(int fila) {
        if (llistaClients != null && fila >= 0 && fila < llistaClients.size()) {
            return llistaClients.get(fila);
        }
        return null;
    }

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

        service.eliminarClient((long)id, token, new Callback<Void>() {
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
