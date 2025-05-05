package opticyou.OpticYou.ui.rols;

import opticyou.OpticYou.rolAdmin.clients.ClientCrudScreen;
import opticyou.OpticYou.rolAdmin.clients.ClientController;
import opticyou.OpticYou.rolAdmin.historial.HistorialCrudScreen;
import opticyou.OpticYou.rolAdmin.historial.HistorialController;
import opticyou.OpticYou.rolAdmin.treballador.TreballadorCrudScreen;
import opticyou.OpticYou.rolAdmin.treballador.TreballadorController;

import opticyou.OpticYou.service.auth.LogoutService;
import opticyou.OpticYou.ui.InitScreen;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Pantalla principal per a usuaris amb rol de treballador.
 * <p>
 * Proporciona accés a la gestió de clients i historials,
 * així com la possibilitat de fer logout.
 */


/**
 * Autor: mramis
 */

public class TreballadorPanelScreen {
    private static final String APP_NAME = "OpticYou";
    private JPanel menuPanel;
    private String token;

    public TreballadorPanelScreen(String token) {
        this.token = token;

        JFrame frame = new JFrame(APP_NAME + " - PANELL TREBALLADOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        Color backgroundColor = new Color(200, 230, 255);

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
        menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        menuPanel.setBackground(backgroundColor);

        JButton btnClients = createStyledButton("Gestió de Clients");
        JButton btnTreballadors = createStyledButton("Gestió de Treballadors");
        JButton btnHistorials = createStyledButton("Gestió de Historials");
        JButton logoutButton = createStyledButton("Logout");

        menuPanel.add(wrapButton(btnClients));
        menuPanel.add(wrapButton(btnTreballadors));
        menuPanel.add(wrapButton(btnHistorials));
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(wrapButton(logoutButton));

        JLabel titleLabel = new JLabel("Panell de Treballador", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(backgroundColor);
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(menuPanel, BorderLayout.CENTER);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Accions dels botons
        btnClients.addActionListener(e -> {
            ClientCrudScreen clientCrudScreen = new ClientCrudScreen(token, true);
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
        btnTreballadors.addActionListener(e -> {
           TreballadorCrudScreen treballadorCrudScreen = new TreballadorCrudScreen(token, true);
           TreballadorController treballadorController = new TreballadorController(treballadorCrudScreen, token);
            treballadorCrudScreen.setController(treballadorController);

            treballadorCrudScreen.setTornarListener(ev -> {
                frame.getContentPane().removeAll();
                frame.add(headerPanel, BorderLayout.NORTH);
                frame.add(contentPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            });

            frame.getContentPane().removeAll();
            frame.add(headerPanel, BorderLayout.NORTH);
            frame.add(treballadorCrudScreen, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });


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

    private JPanel wrapButton(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.add(button);
        return panel;
    }
}
