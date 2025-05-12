package opticyou.OpticYou.rolAdmin.treballador;

import opticyou.OpticYou.model.Treballador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pantalla d'interfície gràfica per gestionar treballadors (crear, consultar, actualitzar, eliminar).
 * <p>
 * Proporciona un formulari i una taula per visualitzar i manipular dades de treballadors.
 * Inclou control d'accions mitjançant {@link TreballadorController}.
 * </p>
 *
 * @author mrami
 */
public class TreballadorCrudScreen extends JPanel {

    // Components del formulari
    private JTextField txtNom;
    private JTextField txtEmail;
    private JTextField txtContrasenya;
    private JTextField txtEspecialitat;
    private JComboBox<String> comboEstat;
    private JTextField txtIniciJornada;
    private JTextField txtDiesJornada;
    private JTextField txtFiJornada;
    private JTextField txtClinicaId;

    // Botons d'acció
    private JButton btnAfegir, btnActualitzar, btnEliminar, btnTornar;

    // Taula per llistar treballadors
    private JTable treballadorTable;

    // Identificador del treballador seleccionat
    private Long idTreballadorSeleccionat;

    // Controlador associat
    private TreballadorController controller;

    // Identificador de l'historial (no usat actualment)
    private Long historialIdSeleccionat;

    // Indica si l'usuari actual és treballador
    boolean esTreballador;

    /**
     * Crea la pantalla de gestió de treballadors.
     *
     * @param token         Token d'autenticació.
     * @param esTreballador Indica si l'usuari és treballador (per limitar accions).
     */
    public TreballadorCrudScreen(String token, boolean esTreballador) {
        this.esTreballador = esTreballador;

        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));

        // Formulari a l'esquerra
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addFormRow(leftPanel, gbc, row++, "Nom:", txtNom = new JTextField(20));
        addFormRow(leftPanel, gbc, row++, "Email:", txtEmail = new JTextField(20));
        addFormRow(leftPanel, gbc, row++, "Contrasenya:", txtContrasenya = new JTextField(20));
        addFormRow(leftPanel, gbc, row++, "Especialitat:", txtEspecialitat = new JTextField(20));
        addFormRow(leftPanel, gbc, row++, "Estat:", comboEstat = new JComboBox<>(new String[]{"actiu", "inactiu"}));
        addFormRow(leftPanel, gbc, row++, "Inici Jornada:", txtIniciJornada = new JTextField(20));
        addFormRow(leftPanel, gbc, row++, "Fi Jornada:", txtFiJornada = new JTextField(20));
        addFormRow(leftPanel, gbc, row++, "Dies Jornada:", txtDiesJornada = new JTextField(20));
        addFormRow(leftPanel, gbc, row++, "ID Clínica:", txtClinicaId = new JTextField(20));

        // Botons d'acció
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(173, 216, 230));
        btnAfegir = new JButton("Afegir");
        btnActualitzar = new JButton("Actualitzar");
        btnEliminar = new JButton("Eliminar");
        if (esTreballador) btnEliminar.setEnabled(false);
        btnTornar = new JButton("Tornar");

        buttonPanel.add(btnAfegir);
        buttonPanel.add(btnActualitzar);
        buttonPanel.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        leftPanel.add(buttonPanel, gbc);

        gbc.gridy = row++;
        leftPanel.add(btnTornar, gbc);

        // Taula a la dreta
        treballadorTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{
                "ID", "Nom", "Email", "Especialitat", "Estat", "Inici Jornada", "Dies Jornada", "Fi Jornada", "Clínica"
        }));

        JScrollPane tableScroll = new JScrollPane(treballadorTable);
        tableScroll.getViewport().setBackground(new Color(173, 216, 230));

        // Divisió esquerra-dreta
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPanel), tableScroll);
        split.setDividerLocation(400);
        split.setResizeWeight(0.5);

        add(split, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1000, 600));
    }

    /**
     * Afegeix una fila de formulari al panell.
     */
    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // Getters del formulari
    public String getNom() { return txtNom.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getContrasenya() { return txtContrasenya.getText(); }
    public String getEspecialitat() { return txtEspecialitat.getText(); }
    public String getEstat() { return (String) comboEstat.getSelectedItem(); }
    public String getIniciJornada() { return txtIniciJornada.getText(); }
    public String getFiJornada() { return txtFiJornada.getText(); }
    public String getDiesJornada() { return txtDiesJornada.getText(); }
    public String getClinicaId() { return txtClinicaId.getText(); }

    // Assignació d'ActionListeners
    public void setAfegirListener(ActionListener l) { btnAfegir.addActionListener(l); }
    public void setModificarListener(ActionListener l) { btnActualitzar.addActionListener(l); }
    public void setEliminarListener(ActionListener l) { btnEliminar.addActionListener(l); }
    public void setTornarListener(ActionListener l) { btnTornar.addActionListener(l); }

    /**
     * Retorna l'ID del treballador seleccionat o -1 si no hi ha cap selecció.
     */
    public Long getIdTreballadorSeleccionat() {
        return idTreballadorSeleccionat != null ? idTreballadorSeleccionat : -1;
    }

    /**
     * Neteja tots els camps del formulari i des-selecciona la taula.
     */
    public void clearForm() {
        txtNom.setText("");
        txtEmail.setText("");
        txtContrasenya.setText("");
        txtEspecialitat.setText("");
        comboEstat.setSelectedIndex(0);
        txtIniciJornada.setText("");
        txtFiJornada.setText("");
        txtClinicaId.setText("");
        txtDiesJornada.setText("");
        idTreballadorSeleccionat = null;
        historialIdSeleccionat = null;
        treballadorTable.clearSelection();
    }

    /**
     * Crea un objecte {@link Treballador} a partir del contingut del formulari.
     */
    public Treballador crearTreballadorDesdeFormulari() {
        Treballador treballador = new Treballador();
        treballador.setNom(getNom());
        treballador.setEmail(getEmail());
        if (!getContrasenya().isBlank()) {
            treballador.setContrasenya(getContrasenya());
        }
        treballador.setRol("TREBALLADOR");
        treballador.setEspecialitat(getEspecialitat());
        treballador.setEstat(getEstat());
        treballador.setIniciJornada(getIniciJornada());
        treballador.setDiesJornada(getDiesJornada());
        treballador.setFiJornada(getFiJornada());
        treballador.setClinicaId(getClinicaId().isBlank() ? null : Long.parseLong(getClinicaId()));
        if (idTreballadorSeleccionat != null) {
            treballador.setIdTreballador(idTreballadorSeleccionat);
        }
        return treballador;
    }

    /**
     * Mostra una llista de treballadors a la taula.
     */
    public void mostrarTreballadors(List<Treballador> treballadors) {
        DefaultTableModel model = (DefaultTableModel) treballadorTable.getModel();
        model.setRowCount(0);
        for (Treballador t : treballadors) {
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
    }

    /**
     * Assigna el controlador de la pantalla.
     */
    public void setController(TreballadorController controller) {
        this.controller = controller;
    }

    /**
     * Retorna la taula de treballadors.
     */
    public JTable getTreballadorTable() {
        return treballadorTable;
    }

    /**
     * Carrega un treballador al formulari per editar-lo.
     *
     * @param t Treballador a carregar.
     */
    public void carregarTreballadorAlFormulari(Treballador t) {
        txtNom.setText(t.getNom());
        txtEmail.setText(t.getEmail());
        txtContrasenya.setText(t.getContrasenya());
        txtEspecialitat.setText(t.getEspecialitat());
        comboEstat.setSelectedItem(t.getEstat());
        txtIniciJornada.setText(t.getIniciJornada());
        txtDiesJornada.setText(t.getDiesJornada());
        txtFiJornada.setText(t.getFiJornada());
        txtClinicaId.setText(t.getClinicaId() != null ? t.getClinicaId().toString() : "");
        idTreballadorSeleccionat = t.getIdTreballador();
    }
}
