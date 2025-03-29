package opticyou.OpticYou.ui.admin;
/**
 * Autor: mrami
 */

import opticyou.OpticYou.clinica.ClinicaController;
import opticyou.OpticYou.service.auth.LogoutService;
import opticyou.OpticYou.ui.InitScreen;
import opticyou.OpticYou.clinica.ClinicaCrudScreen;

import javax.swing.*;
import java.awt.*;

public class AdminPanelScreen {
    private static final String APP_NAME = "OpticYou";
    private JPanel menuPanel;
    private String token;

    public AdminPanelScreen(String token) {
        JFrame frame = new JFrame(APP_NAME + " - PANELL ADMINISTRADOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new BorderLayout());

        //color fons pantalla
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230));


        // Panell del menú vertical
        //JPanel menuPanel = new JPanel();
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 files, 1 columna
        menuPanel.setBackground(new Color(173, 216, 230)); // blau

        // Crear botons del menú
        JButton btnClients = createStyledButton("Gestió de Clients");
        JButton btnTreballadors = createStyledButton("Gestió de Treballadors");
        JButton btnClinica = createStyledButton("Gestió de Clíniques");
        JButton btnHistorials = createStyledButton("Gestió de Historials");
        JButton logoutButton = createStyledButton("Logout");

        // afegir botons i embolicar a un wrap
        menuPanel.add(wrapButton(btnClients));
        menuPanel.add(wrapButton(btnTreballadors));
        menuPanel.add(wrapButton(btnClinica));
        menuPanel.add(wrapButton(btnHistorials));
        menuPanel.add(Box.createVerticalGlue()); // Espai buit
        menuPanel.add(wrapButton(logoutButton));

        // Accions per als botons
        btnClients.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Gestió de Clients"));
        btnTreballadors.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Gestió de Treballadors"));
        btnHistorials.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Gestió de Historials"));
        //acciones per obrir el CRUD
        btnClinica.addActionListener(e ->{
            //instancia
            ClinicaCrudScreen clinicaCrudScreen = new ClinicaCrudScreen(token);
            ClinicaController controller = new ClinicaController(clinicaCrudScreen, token);
            clinicaCrudScreen.setController(controller);

            // Afegim listener del botó Tornar
                clinicaCrudScreen.setTornarListener(ev -> {
                frame.getContentPane().removeAll();
                frame.add(menuPanel, BorderLayout.CENTER); // tornem a afegir el menú
                frame.revalidate();
                frame.repaint();
            });

            //afegir el CRUD
            frame.getContentPane().removeAll();  // Limpiar el contenido actual
            frame.add(clinicaCrudScreen, BorderLayout.CENTER);  // Agregar la pantalla CRUD
            frame.revalidate();  // Actualizar la interfaz
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
                System.out.println("Logout confirmat.");
                LogoutService logoutService = new LogoutService();
                logoutService.logout(token);
                frame.dispose();

                //tornar a login
                new InitScreen("OpticYou");
            }
        });

        // Afegir el panell al frame
        frame.add(menuPanel, BorderLayout.CENTER);


        // Afegir un panell per el logo a la dreta
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // A la dreta
        logoPanel.setBackground(new Color(173, 216, 230)); // Fons com al principal
        JLabel logoLabel = new JLabel(new ImageIcon("src/main/recursos/Logo.jpg")); // Ruta del logo
        logoPanel.add(logoLabel);

        // Afegir el logo a dreta del frame
        frame.add(logoPanel, BorderLayout.EAST);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    //mètode per botons
    private JPanel wrapButton(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centra el botón
        panel.setOpaque(false); // fer que el panell sigui transparent
        panel.add(button);
        return panel;
    }

    // Métode per disseny botons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 102, 204)); // Azul oscuro
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setPreferredSize(new Dimension(250, 40)); // Establece un tamaño fijo para los botones
        return button;
    }


}
