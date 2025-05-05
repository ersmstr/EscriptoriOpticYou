package opticyou.OpticYou.rolAdmin.treballador;

import opticyou.OpticYou.model.Treballador;
import opticyou.OpticYou.rolAdmin.treballador.TreballadorController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pantalla de CRUD per a la gestió de treballadors.
 * autor: mrami
 */
     public class TreballadorCrudScreen extends JPanel {

        private JTextField txtNom;
        private JTextField txtEmail;
        private JTextField txtContrasenya;
        private JTextField txtEspecialitat;
        private JComboBox<String> comboEstat;
        private JTextField txtIniciJornada;
        private JTextField txtDiesJornada;
        private JTextField txtFiJornada;
        private JTextField txtClinicaId;

        private JButton btnAfegir, btnActualitzar, btnEliminar, btnTornar;
        private JTable treballadorTable;

        private Long idTreballadorSeleccionat;
        private TreballadorController controller;
        private Long historialIdSeleccionat;

        boolean esTreballador;

        public TreballadorCrudScreen(String token, boolean esTreballador) {
            this.esTreballador = esTreballador;

            setLayout(new BorderLayout());
            setBackground(new Color(173, 216, 230));

            JPanel leftPanel = new JPanel(new GridBagLayout());
            leftPanel.setBackground(new Color(173, 216, 230));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 5, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            int row = 0;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Nom:"), gbc);
            txtNom = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtNom, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Email:"), gbc);
            txtEmail = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtEmail, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Contrasenya:"), gbc);
            txtContrasenya = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtContrasenya, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Especialitat:"), gbc);
            txtEspecialitat = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtEspecialitat, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Estat:"), gbc);
            comboEstat = new JComboBox<>(new String[]{"actiu", "inactiu"});
            gbc.gridx = 1;
            leftPanel.add(comboEstat, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Inici Jornada:"), gbc);
            txtIniciJornada = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtIniciJornada, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Fi Jornada:"), gbc);
            txtFiJornada = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtFiJornada, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("Dies Jornada:"), gbc);
            txtDiesJornada = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtDiesJornada, gbc); row++;

            gbc.gridx = 0; gbc.gridy = row;
            leftPanel.add(new JLabel("ID Clínica:"), gbc);
            txtClinicaId = new JTextField(20);
            gbc.gridx = 1;
            leftPanel.add(txtClinicaId, gbc); row++;

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

            gbc.gridx = 0; gbc.gridy = row++;
            gbc.gridwidth = 2;
            leftPanel.add(buttonPanel, gbc);

            gbc.gridy = row++;
            leftPanel.add(btnTornar, gbc);

            treballadorTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{
                    "ID", "Nom", "Email", "Especialitat", "Estat", "Inici Jornada", "Dies Jornada","Fi Jornada","Clínica"
            }));

            JScrollPane tableScroll = new JScrollPane(treballadorTable);
            tableScroll.getViewport().setBackground(new Color(173, 216, 230));

            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPanel), tableScroll);
            split.setDividerLocation(400);
            split.setResizeWeight(0.5);

            add(split, BorderLayout.CENTER);
            setPreferredSize(new Dimension(1000, 600));
        }

        public String getNom() { return txtNom.getText(); }
        public String getEmail() { return txtEmail.getText(); }
        public String getContrasenya() { return txtContrasenya.getText(); }
        public String getEspecialitat() { return txtEspecialitat.getText(); }
        public String getEstat() { return (String) comboEstat.getSelectedItem(); }
        public String getIniciJornada() { return txtIniciJornada.getText(); }
        public String getFiJornada() { return txtFiJornada.getText(); }
        public String getDiesJornada() { return txtDiesJornada.getText(); }
        public String getClinicaId() { return txtClinicaId.getText(); }

        public void setAfegirListener(ActionListener l) { btnAfegir.addActionListener(l); }
        public void setModificarListener(ActionListener l) { btnActualitzar.addActionListener(l); }
        public void setEliminarListener(ActionListener l) { btnEliminar.addActionListener(l); }
        public void setTornarListener(ActionListener l) { btnTornar.addActionListener(l); }

        public Long getIdTreballadorSeleccionat() {
            return idTreballadorSeleccionat != null ? idTreballadorSeleccionat : -1;
        }

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
            System.out.println("FI JORNADA del formulari abans de desar: " + getFiJornada());
            return treballador;


        }

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

        public void setController(TreballadorController controller) {
            this.controller = controller;
        }

        public JTable getTreballadorTable() {
            return treballadorTable;
        }
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

