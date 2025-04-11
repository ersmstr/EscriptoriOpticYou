package opticyou.OpticYou.rolAdmin.historial;

import opticyou.OpticYou.model.Historial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Vista Swing per gestionar els historials mèdics.
 * <p>
 * Permet visualitzar, editar i actualitzar els historials dels clients.
 */
/**
 * Autor: mramis
 */
public class HistorialCrudScreen extends JPanel {

    private JTextArea txtPatologies;
    private JTable historialTable;

    private JButton btnActualitzar;
    private JButton btnTornar;

    private Long idHistorialSeleccionat;
    private HistorialController controller;
    private String token;

    /**
     * Constructor que inicialitza la pantalla amb el token d'autenticació.
     *
     * @param token Token JWT d'autenticació (actualment no utilitzat directament).
     */
    public HistorialCrudScreen(String token) {
        this.token = token;
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));

        // Formulari esquerre
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Camp de patologies
        JLabel lblPatologies = new JLabel("Patologies:");
        gbc.gridx = 0; gbc.gridy = row;
        leftPanel.add(lblPatologies, gbc);

        txtPatologies = new JTextArea(5, 20);
        txtPatologies.setLineWrap(true);
        txtPatologies.setWrapStyleWord(true);
        JScrollPane scrollPatologies = new JScrollPane(txtPatologies);
        gbc.gridx = 1;
        leftPanel.add(scrollPatologies, gbc);
        row++;

        // Botons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(173, 216, 230));

        btnActualitzar = new JButton("Actualitzar Historial");
        btnTornar = new JButton("Tornar");

        buttonPanel.add(btnActualitzar);
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        leftPanel.add(buttonPanel, gbc);

        gbc.gridy = row++;
        leftPanel.add(btnTornar, gbc);

        // Taula de visualització
        historialTable = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Patologies"}
        ));

        historialTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = historialTable.getSelectedRow();
                if (fila >= 0 && controller != null) {
                    Historial h = controller.getHistorialPerFila(fila);
                    if (h != null) {
                        idHistorialSeleccionat = h.getIdhistorial();
                        txtPatologies.setText(h.getPatologies());
                        System.out.println("✅ Historial carregat - ID: " + idHistorialSeleccionat);
                    }
                }
            }
        });

        JScrollPane scrollTaula = new JScrollPane(historialTable);
        scrollTaula.getViewport().setBackground(new Color(173, 216, 230));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(leftPanel), scrollTaula);

        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1000, 600));
    }

    // Getters

    /**
     * Retorna el text introduït a l'àrea de patologies.
     *
     * @return Cadena de text amb les patologies.
     */
    public String getPatologies() {
        return txtPatologies.getText();
    }

    /**
     * Retorna l'ID de l'historial seleccionat.
     *
     * @return ID de l'historial seleccionat o {@code null} si no n'hi ha cap.
     */
    public Long getIdHistorialSeleccionat() {
        return idHistorialSeleccionat;
    }

    /**
     * Retorna la taula de llistat d'historials.
     *
     * @return Component JTable de la vista.
     */
    public JTable getHistorialTable() {
        return historialTable;
    }

    // Listeners

    /**
     * Assigna el listener per al botó "Actualitzar Historial".
     *
     * @param l Acció a executar quan es clica el botó.
     */
    public void setActualitzarListener(ActionListener l) {
        btnActualitzar.addActionListener(l);
    }

    /**
     * Assigna el listener per al botó "Tornar".
     *
     * @param l Acció a executar quan es clica el botó.
     */
    public void setTornarListener(ActionListener l) {
        btnTornar.addActionListener(l);
    }

    /**
     * Assigna el controlador que interactua amb aquesta vista.
     *
     * @param controller Instància de {@link HistorialController}.
     */
    public void setController(HistorialController controller) {
        this.controller = controller;
    }

    /**
     * Neteja el formulari i la selecció de la taula.
     */
    public void clearForm() {
        idHistorialSeleccionat = null;
        txtPatologies.setText("");
        historialTable.clearSelection();
    }

    /**
     * Construeix un objecte {@link Historial} amb les dades actuals del formulari.
     *
     * @return Instància de {@link Historial} amb o sense ID.
     */
    public Historial crearHistorialDesdeFormulari() {
        Historial h = new Historial();
        h.setPatologies(getPatologies());

        if (idHistorialSeleccionat != null) {
            h.setIdhistorial(idHistorialSeleccionat);
        }

        return h;
    }

    /**
     * Omple la taula amb una llista d'historials.
     *
     * @param historials Llista d'objectes {@link Historial} a mostrar.
     */
    public void mostrarHistorials(List<Historial> historials) {
        DefaultTableModel model = (DefaultTableModel) historialTable.getModel();
        model.setRowCount(0);
        for (Historial h : historials) {
            model.addRow(new Object[]{
                    h.getIdhistorial(),
                    h.getPatologies()
            });
        }
    }
}
