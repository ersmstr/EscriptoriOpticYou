package opticyou.OpticYou.clients;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pantalla de CRUD per a la gestió de clients.
 * <p>
 * Permet crear, modificar, eliminar i consultar clients, així com visualitzar-los en una taula.
 * Aquesta classe és una vista (Swing) que es connecta amb {@link ClientController}.
 */

/**
 * autor mramis
 */
public class ClientCrudScreen extends JPanel {

    // Camps de formulari
    private JTextField txtDataNaixament;
    private JTextField txtSexe;
    private JTextField txtTelefon;
    private JTextField txtClinicaId;
    private JTextField txtEmail;
    private JComboBox<String> comboRol;
    private JTextField txtNom;
    private JTextField txtContrasenya;

    // Botons i taula
    private JButton btnAfegir, btnActualitzar, btnEliminar, btnTornar;
    private JTable clientTable;

    // Camps d'estat
    private Long idClientSeleccionat;
    private ClientController controller;
    private Long historialIdSeleccionat;

    /**
     * Constructor que crea i inicialitza la pantalla CRUD de clients.
     *
     * @param token Token d'autenticació (actualment no utilitzat directament en aquesta classe).
     */
    public ClientCrudScreen(String token) {
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));

        // Panell de formulari (esquerra)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Camps del formulari
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Nom:"), gbc);
        txtNom = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtNom, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Contrasenya:"), gbc);
        txtContrasenya = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtContrasenya, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtEmail, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Rol:"), gbc);
        comboRol = new JComboBox<>(new String[]{"CLIENT", "TREBALLADOR", "ADMIN"});
        gbc.gridx = 1;
        leftPanel.add(comboRol, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Data Naixement:"), gbc);
        txtDataNaixament = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtDataNaixament, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Sexe:"), gbc);
        txtSexe = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtSexe, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Telèfon:"), gbc);
        txtTelefon = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtTelefon, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("ID Clínica:"), gbc);
        txtClinicaId = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtClinicaId, gbc); row++;

        // Botons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(173, 216, 230));
        btnAfegir = new JButton("Afegir Client");
        btnActualitzar = new JButton("Actualitzar Client");
        btnEliminar = new JButton("Eliminar Client");
        btnTornar = new JButton("Tornar");

        buttonPanel.add(btnAfegir);
        buttonPanel.add(btnActualitzar);
        buttonPanel.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        leftPanel.add(buttonPanel, gbc);

        gbc.gridy = row++;
        leftPanel.add(btnTornar, gbc);

        // Taula
        clientTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{
                "ID", "Nom", "email", "Data Naixement", "Sexe", "Telèfon", "Clínica", "Historial"
        }));

        JScrollPane tableScroll = new JScrollPane(clientTable);
        tableScroll.getViewport().setBackground(new Color(173, 216, 230));

        clientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaVista = clientTable.getSelectedRow();
                if (filaVista >= 0 && controller != null) {
                    int filaModel = clientTable.convertRowIndexToModel(filaVista);
                    Client c = controller.getClientPerFila(filaModel);
                    if (c != null) {
                        txtNom.setText(c.getNom());
                        txtEmail.setText(c.getEmail());
                        txtContrasenya.setText(c.getContrasenya());
                        txtDataNaixament.setText(c.getDataNaixament());
                        txtSexe.setText(c.getSexe());
                        txtTelefon.setText(c.getTelefon());
                        txtClinicaId.setText(c.getClinicaId() != null ? c.getClinicaId().toString() : "");
                        idClientSeleccionat = c.getIdClient();
                        historialIdSeleccionat = c.getHistorialId();
                    }
                }
            }
        });

        // Panell combinat
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPanel), tableScroll);
        split.setDividerLocation(400);
        split.setResizeWeight(0.5);

        add(split, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1000, 600));
    }

    // Getters per a camps específics del formulari
    public String getNom() { return txtNom.getText(); }
    public String getContrasenya() { return txtContrasenya.getText(); }
    public String getDataNaixament() { return txtDataNaixament.getText(); }
    public String getSexe() { return txtSexe.getText(); }
    public String getTelefon() { return txtTelefon.getText(); }
    public String getClinicaId() { return txtClinicaId.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getRol() { return (String) comboRol.getSelectedItem(); }

    public JTable getClientTable() { return clientTable; }

    // Mètodes per afegir listeners d'accions
    public void setAfegirListener(ActionListener l) { btnAfegir.addActionListener(l); }
    public void setModificarListener(ActionListener l) { btnActualitzar.addActionListener(l); }
    public void setEliminarListener(ActionListener l) { btnEliminar.addActionListener(l); }
    public void setTornarListener(ActionListener l) { btnTornar.addActionListener(l); }

    /**
     * Retorna l'ID del client seleccionat, o -1 si cap està seleccionat.
     * @return ID del client seleccionat.
     */
    public Long getIdClientSeleccionat() {
        return idClientSeleccionat != null ? idClientSeleccionat : -1;
    }

    /**
     * Neteja tots els camps del formulari i desselecciona qualsevol fila de la taula.
     */
    public void clearForm() {
        txtNom.setText("");
        txtContrasenya.setText("");
        txtEmail.setText("");
        txtDataNaixament.setText("");
        txtSexe.setText("");
        txtTelefon.setText("");
        txtClinicaId.setText("");
        idClientSeleccionat = null;
        historialIdSeleccionat = null;
        clientTable.clearSelection();
    }

    /**
     * Omple la taula amb la llista de clients proporcionada.
     *
     * @param clients Llista de clients per mostrar.
     */
    public void mostrarClients(List<Client> clients) {
        DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
        model.setRowCount(0);
        for (Client c : clients) {
            model.addRow(new Object[]{
                    c.getIdClient(),
                    c.getNom(),
                    c.getDataNaixament(),
                    c.getSexe(),
                    c.getTelefon(),
                    c.getClinicaId(),
                    c.getHistorialId()
            });
        }
    }

    /**
     * Construeix un objecte {@link Client} a partir de les dades del formulari.
     * Inclou l'ID del client si està en edició.
     *
     * @return Objecte client amb les dades del formulari.
     */
    public Client crearClientDesdeFormulari() {
        String nom = getNom();
        String email = getEmail();
        String contrasenya = getContrasenya();
        String rol = getRol();
        String data = getDataNaixament();
        String sexe = getSexe();
        String telefon = getTelefon();
        Long clinicaId = getClinicaId().isBlank() ? null : Long.parseLong(getClinicaId());
        Long historialId = historialIdSeleccionat;

        Client client = new Client();
        client.setNom(nom);
        client.setEmail(email);
        client.setContrasenya(contrasenya);
        client.setRol(rol);
        client.setDataNaixament(data);
        client.setSexe(sexe);
        client.setTelefon(telefon);
        client.setClinicaId(clinicaId);
        client.setHistorialId(historialId);
        if (idClientSeleccionat != null) {
            client.setIdClient(idClientSeleccionat);
        }

        return client;
    }

    /**
     * Assigna el controlador associat a aquesta pantalla.
     *
     * @param controller Instància de {@link ClientController}.
     */
    public void setController(ClientController controller) {
        this.controller = controller;
    }
}
