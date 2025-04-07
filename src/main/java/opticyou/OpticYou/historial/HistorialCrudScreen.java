package opticyou.OpticYou.historial;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
/**
 * Autor: mrami
 */
    public class HistorialCrudScreen extends JPanel {
        private JTextArea txtPatologies;
        private JTable historialTable;

        private JButton btnActualitzar;
        private JButton btnTornar;

        private Long idHistorialSeleccionat;
        private HistorialController controller;
        private String token;

        public HistorialCrudScreen(String token) {
            this.token = token;
            setLayout(new BorderLayout());
            setBackground(new Color(173, 216, 230));

            // Panell esquerre (formulari)
            JPanel leftPanel = new JPanel(new GridBagLayout());
            leftPanel.setBackground(new Color(173, 216, 230));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 5, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            int row = 0;

            // Àrea de text per patologies
            JLabel lblPatologies = new JLabel("Patologies:");
            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(lblPatologies, gbc);

            txtPatologies = new JTextArea(5, 20);
            txtPatologies.setLineWrap(true);
            txtPatologies.setWrapStyleWord(true);
            JScrollPane scrollPatologies = new JScrollPane(txtPatologies);
            gbc.gridx = 1;
            leftPanel.add(scrollPatologies, gbc); row++;

            // Botons CRUD
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

            // Panell dret (taula)
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
        public String getPatologies() {
            return txtPatologies.getText();
        }

        public Long getIdHistorialSeleccionat() {
            return idHistorialSeleccionat;
        }

        public JTable getHistorialTable() {
            return historialTable;
        }



        public void setActualitzarListener(ActionListener l) {
            btnActualitzar.addActionListener(l);
        }



        public void setTornarListener(ActionListener l) {
            btnTornar.addActionListener(l);
        }

        public void setController(HistorialController controller) {
            this.controller = controller;
        }

        public void clearForm() {
            idHistorialSeleccionat = null;

            txtPatologies.setText("");
            historialTable.clearSelection();
        }

        public Historial crearHistorialDesdeFormulari() {

            Historial h = new Historial();
            h.setPatologies(getPatologies());

            if (idHistorialSeleccionat != null) {
                h.setIdhistorial(idHistorialSeleccionat);
            }

            return h;
        }

        public void mostrarHistorials(java.util.List<Historial> historials) {
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



