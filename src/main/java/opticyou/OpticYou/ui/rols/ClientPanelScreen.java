package opticyou.OpticYou.ui.rols;




import opticyou.OpticYou.data.ClientApi;
import opticyou.OpticYou.model.Client;
import opticyou.OpticYou.rolClient.ClientHistorialScreen;
import opticyou.OpticYou.service.auth.RetrofitApp;
import opticyou.OpticYou.ui.InitScreen;

import javax.swing.*;
import java.awt.*;
/**
 * Autor: mrami
 */
public class ClientPanelScreen  {
    private static final String APP_NAME = "OpticYou";
    private final String token;


    public ClientPanelScreen(String token) {
        this.token = token;

        JFrame frame = new JFrame(APP_NAME + " - Panell Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.add(new JLabel("Benvingut al teu panell, client!"));
        frame.add(headerPanel, BorderLayout.NORTH);

        // Menú botons
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnDadesPersonals = new JButton("Les meves dades");
        JButton btnHistorial = new JButton("El meu historial");
        JButton btnCites = new JButton("Les meves cites");
        JButton btnLogout = new JButton("Tancar sessió");

        menuPanel.add(btnDadesPersonals);
        menuPanel.add(btnHistorial);
        menuPanel.add(btnCites);
        menuPanel.add(btnLogout);

        frame.add(menuPanel, BorderLayout.CENTER);

        // Accions botons
      //  btnDadesPersonals.addActionListener(e -> new ClientPerfilScreen(token));
       // btnHistorial.addActionListener(e -> new ClientHistorialScreen(token));  // Adaptar o crear
        //btnCites.addActionListener(e -> new ClientCitesScreen(token));          // Crear si no existeix
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame, "Vols tancar sessió?", "Logout", JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                new InitScreen(APP_NAME);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
