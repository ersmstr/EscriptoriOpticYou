package opticyou.OpticYou.rolAdmin.diagnostic;
import opticyou.OpticYou.rolAdmin.diagnostic.DiagnosticController;

import javax.swing.*;
import java.awt.*;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * Autor: mrami
 */
public class DiagnosticCrudScreen extends JDialog {
    private JTextArea txtDiagnostico;
    private JButton btnGuardar;
    private JButton btnEsborrar;
    private JButton btnTancar;

    private Long historialId;
    private DiagnosticController controller;
    private String token;

    public DiagnosticCrudScreen(Long historialId, DiagnosticController controller, String token) {
        this.historialId = historialId;
        this.controller = controller;
        this.token = token;
        setTitle("Gestió del Diagnòstic");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setModal(true);

        initComponents();
        carregarDiagnostic();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Àrea de diagnòstic
        txtDiagnostico = new JTextArea(10, 40);
        txtDiagnostico.setLineWrap(true);
        txtDiagnostico.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDiagnostico);
        add(scroll, BorderLayout.CENTER);

        // Botons
        JPanel buttonPanel = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnEsborrar = new JButton("Esborrar");
        btnTancar = new JButton("Tancar");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnEsborrar);
        buttonPanel.add(btnTancar);
        add(buttonPanel, BorderLayout.SOUTH);

        // Accions
        btnGuardar.addActionListener(e -> {
            String text = txtDiagnostico.getText().trim();
            controller.guardarDiagnostic(historialId, text);
            JOptionPane.showMessageDialog(this, "Diagnòstic guardat!");
        });

        btnEsborrar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Vols esborrar el diagnòstic?", "Confirmació", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.esborrarDiagnostic(historialId);
                txtDiagnostico.setText("");
                JOptionPane.showMessageDialog(this, "Diagnòstic esborrat.");
            }
        });

        btnTancar.addActionListener(e -> dispose());
    }

    private void carregarDiagnostic() {
        controller.carregarDiagnostic(historialId, diagnostic -> {
            if (diagnostic != null) {
                txtDiagnostico.setText(diagnostic);
            }
        });


    }
}
