package opticyou.OpticYou.rolAdmin.clinica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista Swing per a la gestió CRUD de clíniques.
 * <p>
 * Permet crear, modificar, eliminar i visualitzar clíniques a través d'un formulari i una taula.
 */
/**
 * Autor: mramis
 */
public class ClinicaCrudScreen extends JPanel {

    private JTextField txtNom;
    private JTextField txtDireccio;
    private JTextField txtEmail;
    private JTextField txtTelefon;
    private JTextField txtHorariApertura;
    private JTextField txtHorariTancament;

    private JButton btnAfegir;
    private JButton btnActualitzar;
    private JButton btnEliminar;
    private JButton btnTornar;
    private JTable clinicaTable;

    private Long idClinicaSeleccionada;
    private ClinicaController controller;
    private String token;

    /**
     * Constructor per defecte que inicialitza la interfície d'usuari.
     */
    public ClinicaCrudScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));

        // Panell esquerre (formulari)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Nom
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("CLÍNICA:"), gbc);
        txtNom = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtNom, gbc); row++;

        // Direcció
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Direcció:"), gbc);
        txtDireccio = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtDireccio, gbc); row++;

        // Email
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtEmail, gbc); row++;

        // Telèfon
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Telèfon:"), gbc);
        txtTelefon = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtTelefon, gbc); row++;

        // Horari Apertura
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Horari Apertura:"), gbc);
        txtHorariApertura = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtHorariApertura, gbc); row++;

        // Horari Tancament
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(new JLabel("Horari Tancament:"), gbc);
        txtHorariTancament = new JTextField(20);
        gbc.gridx = 1;
        leftPanel.add(txtHorariTancament, gbc); row++;

        // Botons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(173, 216, 230));

        btnAfegir = new JButton("Afegir Clínica");
        btnActualitzar = new JButton("Actualitzar Clínica");
        btnEliminar = new JButton("Eliminar Clínica");
        btnTornar = new JButton("Tornar");

        buttonPanel.add(btnAfegir);
        buttonPanel.add(btnActualitzar);
        buttonPanel.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        leftPanel.add(buttonPanel, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        leftPanel.add(btnTornar, gbc);

        // Panell dret (taula)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0; gbcRight.gridy = 0;
        gbcRight.fill = GridBagConstraints.BOTH;
        gbcRight.weightx = 1;
        gbcRight.weighty = 1;
        gbcRight.insets = new Insets(10, 10, 10, 10);

        clinicaTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "CLINICA"}));
        clinicaTable.setBackground(new Color(173, 216, 230));

        clinicaTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int fila = clinicaTable.getSelectedRow();
                if (fila >= 0 && controller != null) {
                    Clinica seleccionada = controller.getClinicaPerFila(fila);
                    if (seleccionada != null) {
                        txtNom.setText(seleccionada.getNom());
                        txtDireccio.setText(seleccionada.getDireccio());
                        txtTelefon.setText(seleccionada.getTelefon());
                        txtHorariApertura.setText(seleccionada.getHorari_opertura());
                        txtHorariTancament.setText(seleccionada.getHorari_tancament());
                        txtEmail.setText(seleccionada.getEmail());
                        idClinicaSeleccionada = seleccionada.getIdClinica();
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(clinicaTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        scrollPane.getViewport().setBackground(new Color(173, 216, 230));
        rightPanel.add(scrollPane, gbcRight);

        // Divisió esquerra/dreta
        JScrollPane scrollLeft = new JScrollPane(leftPanel);
        scrollLeft.setPreferredSize(new Dimension(400, 500));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLeft, rightPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1000, 600));
    }

    /**
     * Constructor que permet passar el token d'autenticació.
     * @param token Token JWT d'autenticació.
     */
    public ClinicaCrudScreen(String token) {
        this();
        this.token = token;
    }

    // Getters dels camps de formulari
    public String getNomCentre() { return txtNom.getText(); }
    public String getDireccio() { return txtDireccio.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getTelefon() { return txtTelefon.getText(); }
    public String getHorariApertura() { return txtHorariApertura.getText(); }
    public String getHorariTancament() { return txtHorariTancament.getText(); }
    public JTable getClinicaTable() { return clinicaTable; }

    // Listeners dels botons
    public void setAfegirListener(ActionListener listener) { btnAfegir.addActionListener(listener); }
    public void setActualitzarListener(ActionListener listener) { btnActualitzar.addActionListener(listener); }
    public void setEliminarListener(ActionListener listener) { btnEliminar.addActionListener(listener); }
    public void setTornarListener(ActionListener listener) { btnTornar.addActionListener(listener); }

    /**
     * Retorna l'ID de la clínica seleccionada a la taula.
     * @return ID de la clínica seleccionada o {@code null} si no n'hi ha cap.
     */
    public Long getIdClinicaSeleccionada() {
        return idClinicaSeleccionada;
    }

    public void setIdClinicaSeleccionada(Long idClinicaSeleccionada) {
        this.idClinicaSeleccionada = idClinicaSeleccionada;
    }

    /**
     * Assigna el controlador a la pantalla.
     * @param controller Instància de {@link ClinicaController}.
     */
    public void setController(ClinicaController controller) {
        this.controller = controller;
    }

    /**
     * Crea un objecte {@link Clinica} amb les dades actuals del formulari.
     * @return Nova instància de {@link Clinica}.
     */
    public Clinica crearClinicaDesdeFormulario() {
        String nom = getNomCentre();
        String direccio = getDireccio();
        String telefon = getTelefon();
        String horariApertura = getHorariApertura();
        String horariTancament = getHorariTancament();
        String email = getEmail();

        return new Clinica(nom, direccio, telefon, horariApertura, horariTancament, email);
    }

    /**
     * Neteja els camps del formulari
     */
    public void clearForm() {
        txtNom.setText("");
        txtDireccio.setText("");
        txtTelefon.setText("");
        txtHorariApertura.setText("");
        txtHorariTancament.setText("");
        txtEmail.setText("");
        idClinicaSeleccionada = null;
        clinicaTable.clearSelection();
    }
}
