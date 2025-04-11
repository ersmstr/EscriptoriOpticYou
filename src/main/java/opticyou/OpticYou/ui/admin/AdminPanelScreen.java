package opticyou.OpticYou.ui.admin;


import opticyou.OpticYou.clinica.ClinicaController;
import opticyou.OpticYou.service.auth.LogoutService;
import opticyou.OpticYou.ui.InitScreen;
import opticyou.OpticYou.clinica.ClinicaCrudScreen;
import opticyou.OpticYou.clients.ClientCrudScreen;
import opticyou.OpticYou.clients.ClientController;
import opticyou.OpticYou.historial.HistorialCrudScreen;
import opticyou.OpticYou.historial.HistorialController;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Pantalla principal per a usuaris amb rol d'administrador.
 * <p>
 * Proporciona accés a la gestió de clients, treballadors, clíniques i historials,
 * així com la possibilitat de fer logout.
 */


/**
 * Autor: mramis
 */
public class AdminPanelScreen {

    private static final String APP_NAME = "OpticYou";
    private JPanel menuPanel;
    private String token;

    /**
     * Crea i mostra la interfície gràfica per a l'administrador autenticat.
     *
     * @param token Token JWT obtingut després del login.
     */
    public AdminPanelScreen(String token) {
        this.token = token;

        JFrame frame = new JFrame(APP_NAME + " - PANELL ADMINISTRADOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        Color backgroundColor = new Color(173, 216, 230);

        // HEADER amb logo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(backgroundColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        URL logoUrl = getClass().getResource("/recursos/Logo.jpg");
        if (logoUrl != null) {
            ImageIcon originalIcon = new ImageIcon(logoUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel logoLabel = new JLabel(scaledIcon);
            headerPanel.add(logoLabel, BorderLayout.WEST);
        } else {
            JLabel placeholder = new JLabel("[Logo]");
            headerPanel.add(placeholder, BorderLayout.WEST);
        }

        frame.add(headerPanel, BorderLayout.NORTH);

        // Menú lateral amb botons
        menuPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        menuPanel.setBackground(backgroundColor);

        JButton btnClients = createStyledButton("Gestió de Clients");
        JButton btnTreballadors = createStyledButton("Gestió de Treballadors");
        JButton btnClinica = createStyledButton("Gestió de Clíniques");
        JButton btnHistorials = createStyledButton("Gestió de Historials");
        JButton logoutButton = createStyledButton("Logout");

        menuPanel.add(wrapButton(btnClients));
        menuPanel.add(wrapButton(btnTreballadors));
        menuPanel.add(wrapButton(btnClinica));
        menuPanel.add(wrapButton(btnHistorials));
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(wrapButton(logoutButton));

        // Títol del panell
        JLabel titleLabel = new JLabel("Panell d'Administrador", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(backgroundColor);
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(menuPanel, BorderLayout.CENTER);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Accions dels botons

        btnTreballadors.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Gestió de Treballadors (pendents d'implementar)")
        );

        btnHistorials.addActionListener(e -> {
            HistorialCrudScreen historialCrudScreen = new HistorialCrudScreen(token);
            HistorialController historialController = new HistorialController(historialCrudScreen, token);
            historialCrudScreen.setController(historialController);

            historialCrudScreen.setTornarListener(ev -> {
                frame.getContentPane().removeAll();
                frame.add(headerPanel, BorderLayout.NORTH);
                frame.add(contentPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            });

            frame.getContentPane().removeAll();
            frame.add(headerPanel, BorderLayout.NORTH);
            frame.add(historialCrudScreen, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        btnClients.addActionListener(e -> {
            ClientCrudScreen clientCrudScreen = new ClientCrudScreen(token);
            ClientController clientController = new ClientController(clientCrudScreen, token);
            clientCrudScreen.setController(clientController);

            clientCrudScreen.setTornarListener(ev -> {
                frame.getContentPane().removeAll();
                frame.add(headerPanel, BorderLayout.NORTH);
                frame.add(contentPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            });

            frame.getContentPane().removeAll();
            frame.add(headerPanel, BorderLayout.NORTH);
            frame.add(clientCrudScreen, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        btnClinica.addActionListener(e -> {
            ClinicaCrudScreen clinicaCrudScreen = new ClinicaCrudScreen(token);
            ClinicaController controller = new ClinicaController(clinicaCrudScreen, token);
            clinicaCrudScreen.setController(controller);

            clinicaCrudScreen.setTornarListener(ev -> {
                frame.getContentPane().removeAll();
                frame.add(headerPanel, BorderLayout.NORTH);
                frame.add(contentPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            });

            frame.getContentPane().removeAll();
            frame.add(headerPanel, BorderLayout.NORTH);
            frame.add(clinicaCrudScreen, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Estàs segur que vols tancar sessió?",
                    "Confirmar logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                LogoutService logoutService = new LogoutService();
                logoutService.logout(token);
                frame.dispose();
                new InitScreen("OpticYou");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Crea un botó estilitzat amb colors personalitzats.
     *
     * @param text Text que apareixerà al botó.
     * @return Botó personalitzat.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setPreferredSize(new Dimension(250, 40));
        return button;
    }

    /**
     * Envolta un botó dins d’un panell centrat per millor alineació.
     *
     * @param button Botó a encapsular.
     * @return Panell amb el botó centrat.
     */
    private JPanel wrapButton(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.add(button);
        return panel;
    }
}
