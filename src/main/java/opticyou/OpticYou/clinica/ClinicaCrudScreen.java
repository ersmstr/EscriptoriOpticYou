package opticyou.OpticYou.clinica;

/**
 * Autor: mrami
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

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




    public ClinicaCrudScreen() {
        // Layout de la pantalla
        setLayout(new BorderLayout());  //  BorderLayout per dividir la pantalla en dues seccions
        setBackground(new Color(173, 216, 230));  // Fons amb color blau clar

        // Panell esquerre per al formulari
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        leftPanel.setBackground(new Color(173, 216, 230));  // Color del panell esquerre

        // Crear camp de text per al nom de la clínica
        JLabel lblNomCentre = new JLabel("CLÍNICA:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);  // Espaciat entre els components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(lblNomCentre, gbc);

        txtNom = new JTextField(20);  // Ample fix per al camp de text
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(txtNom, gbc);

        // Crear camp de text per la direcció
        JLabel lblDireccio = new JLabel("Direcció:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(lblDireccio, gbc);

        txtDireccio = new JTextField(20);  // Ample fix per al camp de text
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(txtDireccio, gbc);

        // Crear camp de text per l'email
        JLabel lblEmail = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(lblEmail, gbc);

        txtEmail = new JTextField(20);  // Ample fix per al camp de text
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(txtEmail, gbc);

        // Crear camp de text per al telèfon
        JLabel lblTelefon = new JLabel("Telèfon:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(lblTelefon, gbc);

        txtTelefon = new JTextField(20);  // Ample fix per al camp de text
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(txtTelefon, gbc);

        // Crear camp de text per l'horari d'obertura
        JLabel lblHorariA = new JLabel("Horari Apertura:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(lblHorariA, gbc);

        txtHorariApertura = new JTextField(20);  // Ample fix per al camp de text
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(txtHorariApertura, gbc);

        // Crear camp de text per l'horari de tancament
        JLabel lblHorariT = new JLabel("Horari Tancament:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(lblHorariT, gbc);

        txtHorariTancament = new JTextField(20);  // Ample fix per al camp de text
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(txtHorariTancament, gbc);

        // Crear panell per als botons CRUD
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Botons alineats horitzontalment
        buttonPanel.setBackground(new Color(173, 216, 230));  // Fons igual al del formulari

        btnAfegir = new JButton("Afegir Clínica");
        btnActualitzar = new JButton("Actualitzar Clínica");
        btnEliminar = new JButton("Eliminar Clínica");
        btnTornar = new JButton("Tornar");

        buttonPanel.add(btnAfegir);
        buttonPanel.add(btnActualitzar);
        buttonPanel.add(btnEliminar);


        // Col·locar el panell de botons al formulari
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;  // El panell de botons ocupa dues columnes
        gbc.insets = new Insets(10, 10, 10, 10);
        leftPanel.add(buttonPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        leftPanel.add(btnTornar, gbc);

        // Panell dret per a la taula
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;  // Perquè la taula s'expandeixi
        gbc.weightx = 1;  // Expandir horitzontalment
        gbc.weighty = 1;  // Expandir verticalment
        gbc.insets = new Insets(10, 10, 10, 10);

// Taula per mostrar les clíniques
        clinicaTable = new JTable();
        clinicaTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[] {"ID", "CLINICA"}
        ));

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

                                idClinicaSeleccionada = seleccionada.getIdClinica(); // Desa l’ID per futurs updates

                            }
                        }
                    }
        });


        // Configurar el color de fons de la taula perquè coincideixi amb el panell
        clinicaTable.setBackground(new Color(173, 216, 230));
        clinicaTable.setOpaque(true); // Assegurar que el color de fons s'aplica


        JScrollPane scrollPane = new JScrollPane(clinicaTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));  // Ajustar el tamany de la taula
        // Configurar el color de fons del JScrollPane (si es veu espai en blanc)
        scrollPane.getViewport().setBackground(new Color(173, 216, 230));


        // Col·locar la taula al panell dret
        rightPanel.add(scrollPane, gbc);


        // Crear una divisió en el BorderLayout
      //  JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        JScrollPane scrollLeft = new JScrollPane(leftPanel);
        scrollLeft.setPreferredSize(new Dimension(400, 500)); // o ajusta segons et convingui
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLeft, rightPanel);

        splitPane.setDividerLocation(400);  // Ajustar la posició de la divisió
        splitPane.setResizeWeight(0.5);  // Permetre redimensionar equitativament
        add(splitPane, BorderLayout.CENTER);

        // Ajustar mida del panell
        setPreferredSize(new Dimension(1000, 600));
    }

    // Mètodes d'accés als camps, per obtenir els valors del formulari:
    public String getNomCentre() {
        return txtNom.getText();
    }

    public String getDireccio() {
        return txtDireccio.getText();
    }

    public String getEmail() {
        return txtEmail.getText();
    }

    public String getTelefon() {
        return txtTelefon.getText();
    }

    public String getHorariApertura() {
        return txtHorariApertura.getText();
    }

    public String getHorariTancament() {
        return txtHorariTancament.getText();
    }

    public JTable getClinicaTable() {
        return clinicaTable;
    }

    // Mètodes per afegir els listeners als botons
    public void setAfegirListener(ActionListener listener) {
        btnAfegir.addActionListener(listener);
    }

    public void setActualitzarListener(ActionListener listener) {
        btnActualitzar.addActionListener(listener);
    }

    public void setEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }
    public void setTornarListener(ActionListener listener) {
        btnTornar.addActionListener(listener);
    }


    public Clinica crearClinicaDesdeFormulario() {
        String nom = getNomCentre();
        String direccio = getDireccio();
        String telefon = getTelefon();
        String horariApertura = getHorariApertura();
        String horariTancament = getHorariTancament();
        String email = getEmail();

        return new Clinica(nom, direccio, telefon, horariApertura, horariTancament, email);
    }
    private String token;

    public ClinicaCrudScreen(String token) {
        this(); // Crida al constructor per defecte que ja tens
        this.token = token;


    }
    private Long idClinicaSeleccionada;
    public Long getIdClinicaSeleccionada() {
        return idClinicaSeleccionada;
    }
    private ClinicaController controller;

    public void setController(ClinicaController controller) {
        this.controller = controller;
    }

    public void clearForm() {
        txtNom.setText("");
        txtDireccio.setText("");
        txtTelefon.setText("");
        txtHorariApertura.setText("");
        txtHorariTancament.setText("");
        txtEmail.setText("");
        idClinicaSeleccionada = null;
        clinicaTable.clearSelection(); // neteja també la selecció
    }



   public void setIdClinicaSeleccionada(Long idClinicaSeleccionada) {
        this.idClinicaSeleccionada = idClinicaSeleccionada;
    }




}
