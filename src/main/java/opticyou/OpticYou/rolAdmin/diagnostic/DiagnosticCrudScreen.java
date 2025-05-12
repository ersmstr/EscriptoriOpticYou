package opticyou.OpticYou.rolAdmin.diagnostic;

import opticyou.OpticYou.model.Diagnostic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Diàleg Swing per gestionar diagnòstics associats a un historial clínic de l'òptica.
 * <p>
 * Permet visualitzar, crear, editar i esborrar diagnòstics mitjançant una taula i botons d'acció.
 * La informació es carrega i s'envia a través del {@link DiagnosticController}.
 * </p>
 *
 * @author mrami
 */
public class DiagnosticCrudScreen extends JDialog {

    /** Taula que mostra la llista de diagnòstics. */
    private JTable taulaDiagnostics;

    /** Model de dades per la taula de diagnòstics. */
    private DefaultTableModel modelTaula;

    /** Botó per afegir un diagnòstic nou. */
    private JButton btnAfegir;

    /** Botó per esborrar el diagnòstic seleccionat. */
    private JButton btnEsborrar;

    /** Botó per tancar el diàleg. */
    private JButton btnTancar;

    /** Identificador de l'historial mèdic al qual pertanyen els diagnòstics. */
    private Long historialId;

    /** Controlador que gestiona la comunicació amb el servei de diagnòstics. */
    private DiagnosticController controller;

    /** Token d'autenticació (no utilitzat directament en aquesta classe). */
    private String token;

    /**
     * Crea i mostra la pantalla CRUD de diagnòstics.
     *
     * @param historialId ID de l'historial mèdic associat.
     * @param controller  Controlador per gestionar operacions de diagnòstic.
     * @param token       Token JWT per autenticació (si s’utilitza).
     */
    public DiagnosticCrudScreen(Long historialId, DiagnosticController controller, String token) {
        this.historialId = historialId;
        this.controller = controller;
        this.token = token;

        setTitle("Diagnòstics associats");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setModal(true);

        initComponents();
        carregarDiagnostics();
    }

    /**
     * Inicialitza els components de la interfície d'usuari.
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        modelTaula = new DefaultTableModel(new Object[]{"ID", "Descripció", "Data"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taulaDiagnostics = new JTable(modelTaula);
        add(new JScrollPane(taulaDiagnostics), BorderLayout.CENTER);

        JPanel panelBotons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAfegir = new JButton("Afegir");
        btnEsborrar = new JButton("Esborrar");
        btnTancar = new JButton("Tancar");

        panelBotons.add(btnAfegir);
        panelBotons.add(btnEsborrar);
        panelBotons.add(btnTancar);
        add(panelBotons, BorderLayout.SOUTH);

        btnAfegir.addActionListener(e -> mostrarFormulariNouDiagnostic());
        btnEsborrar.addActionListener(e -> esborrarDiagnosticSeleccionat());
        btnTancar.addActionListener(e -> dispose());

        taulaDiagnostics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) editarDiagnosticSeleccionat();
            }
        });
    }

    /**
     * Carrega els diagnòstics associats a l'historial i els mostra a la taula.
     */
    private void carregarDiagnostics() {
        controller.carregarDiagnostic(historialId, diagnostics -> {
            modelTaula.setRowCount(0);
            for (Diagnostic d : diagnostics) {
                modelTaula.addRow(new Object[]{d.getIdDiagnostic(), d.getDescripcio(), d.getDate()});
            }
        });
    }

    /**
     * Mostra un formulari per afegir un diagnòstic nou.
     * Assigna automàticament la data actual i l'historial associat.
     */
    private void mostrarFormulariNouDiagnostic() {
        String descripcio = JOptionPane.showInputDialog(this, "Nova descripció:");
        if (descripcio != null && !descripcio.trim().isEmpty()) {
            Diagnostic d = new Diagnostic();
            d.setIdDiagnostic(0L);
            d.setDescripcio(descripcio.trim());

            // Assignar Timestamp actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String dataActual = LocalDateTime.now().format(formatter);
            d.setDate(dataActual);

            d.setHistorialId(historialId);

            controller.crearDiagnostic(d, this::carregarDiagnostics);
        }
    }

    /**
     * Permet editar la descripció d’un diagnòstic seleccionat a la taula.
     * També actualitza la data amb el moment de l’edició.
     */
    private void editarDiagnosticSeleccionat() {
        int row = taulaDiagnostics.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "No has seleccionat cap diagnòstic.");
            return;
        }

        Object idValue = modelTaula.getValueAt(row, 0);
        if (idValue == null) {
            JOptionPane.showMessageDialog(this, "El diagnòstic seleccionat no té ID.");
            return;
        }

        Long idDiagnostic = ((Number) idValue).longValue();
        String descripcioActual = (String) modelTaula.getValueAt(row, 1);

        String novaDescripcio = JOptionPane.showInputDialog(this, "Editar descripció:", descripcioActual);

        if (novaDescripcio != null && !novaDescripcio.trim().isEmpty()) {
            Diagnostic d = new Diagnostic();
            d.setIdDiagnostic(idDiagnostic);
            d.setDescripcio(novaDescripcio.trim());
            d.setHistorialId(historialId);

            // Assignar Timestamp actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String dataActual = LocalDateTime.now().format(formatter);
            d.setDate(dataActual);

            controller.actualitzarDiagnostic(d);
            carregarDiagnostics();
        }
    }

    /**
     * Esborra el diagnòstic seleccionat a la taula, amb confirmació de l’usuari.
     */
    private void esborrarDiagnosticSeleccionat() {
        int row = taulaDiagnostics.getSelectedRow();
        if (row >= 0) {
            Long idDiagnostic = ((Number) modelTaula.getValueAt(row, 0)).longValue();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Vols esborrar el diagnòstic amb ID " + idDiagnostic + "?",
                    "Confirmació", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.esborrarDiagnostic(idDiagnostic);
                carregarDiagnostics();
            }
        }
    }
}
