package opticyou.OpticYou.clients;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientCrudScreen extends JPanel {

    private JTextField txtDataNaixament;
    private JTextField txtSexe;
    private JTextField txtTelefon;
    private JTextField txtClinicaId;
    private JTextField txtEmail;
    private JComboBox<String> comboRol;
    private JTextField txtNom;
    private JTextField txtContrasenya;

    private JButton btnAfegir, btnActualitzar, btnEliminar, btnTornar;
    private JTable clientTable;
    private Long idClientSeleccionat;
    private ClientController controller;
    private Long historialIdSeleccionat; // Nou camp per conservar l'historial


    public ClientCrudScreen(String token) {
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));

        // Panell esquerre
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;


        // Nom
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Nom:"), gbc);
        txtNom = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtNom, gbc); row++;

        // Contrasenya
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Contrasenya:"), gbc);
        txtContrasenya = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtContrasenya, gbc); row++;

        // Email
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtEmail, gbc); row++;

        // Rol
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Rol:"), gbc);
        comboRol = new JComboBox<>(new String[]{"CLIENT", "TREBALLADOR", "ADMIN"});
        gbc.gridx = 1;
        leftPanel.add(comboRol, gbc); row++;


        // Data naixement
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Data Naixement:"), gbc);
        txtDataNaixament = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtDataNaixament, gbc); row++;

        // Sexe
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Sexe:"), gbc);
        txtSexe = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtSexe, gbc); row++;

        // Telèfon
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Telèfon:"), gbc);
        txtTelefon = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtTelefon, gbc); row++;

        // Clínica ID
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
                "ID", "Naixement", "Sexe", "Telèfon", "Clínica", "Historial"
        }));

        JScrollPane tableScroll = new JScrollPane(clientTable);
        tableScroll.getViewport().setBackground(new Color(173, 216, 230));

        clientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = clientTable.getSelectedRow();
                if (fila >= 0 && controller != null) {
                    Client c = controller.getClientPerFila(fila);
                    if (c != null) {

                        //txtIdClient.setText(String.valueOf(c.getIdClient()));
                        txtDataNaixament.setText(c.getDataNaixament());
                        txtSexe.setText(c.getSexe());
                        txtTelefon.setText(c.getTelefon());
                        txtClinicaId.setText(c.getClinicaId() != null ? c.getClinicaId().toString() : "");
                       // txtHistorialId.setText(String.valueOf(c.getHistorialId()));
                        idClientSeleccionat = c.getIdClient();
                        historialIdSeleccionat = c.getHistorialId();
                    }
                }
            }
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPanel), tableScroll);
        split.setDividerLocation(400);
        split.setResizeWeight(0.5);

        add(split, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1000, 600));
    }

    // Getters per controladors

    //public String getIdClient() { return txtIdClient.getText(); }
    public String getDataNaixament() { return txtDataNaixament.getText(); }
    public String getSexe() { return txtSexe.getText(); }
    public String getTelefon() { return txtTelefon.getText(); }
    public String getClinicaId() { return txtClinicaId.getText(); }
    //public String getHistorialId() { return txtHistorialId.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getRol() { return (String) comboRol.getSelectedItem(); }


    public JTable getClientTable() { return clientTable; }

    public void setAfegirListener(ActionListener l) { btnAfegir.addActionListener(l); }
    public void setModificarListener(ActionListener l) { btnActualitzar.addActionListener(l); }
    public void setEliminarListener(ActionListener l) { btnEliminar.addActionListener(l); }
    public void setTornarListener(ActionListener l) { btnTornar.addActionListener(l); }

    public Long getIdClientSeleccionat() {
        return idClientSeleccionat != null ? idClientSeleccionat : -1;
    }

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

    public void mostrarClients(java.util.List<Client> clients) {
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



    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public String getNom() {
        return txtNom.getText();
    }
    public String getContrasenya() {
        return txtContrasenya.getText();
    }


}
