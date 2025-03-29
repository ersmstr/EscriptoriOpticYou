package opticyou.OpticYou.ui;
/**
 * Autor: mrami
 */

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField userText;
    private JPasswordField passText;
    private JButton loginButton;

    public LoginPanel() {
        // Usamos BorderLayout para el panel principal
        this.setLayout(new BorderLayout());

        // Crear y configurar la barra superior (topBar)
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(30, 136, 229)); // Azul
        topBar.setPreferredSize(new Dimension(600, 50)); // Ajustar altura
        this.add(topBar, BorderLayout.NORTH);  // Se coloca arriba

        // Crear panel para el formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Agregar el logo al formulario
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/recursos/Logo.jpg"); // Ruta del logo
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Título "OpticYou"
        JLabel titleLabel = new JLabel("OpticYou");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Posicionamos el logo en la parte superior
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 10, 10, 10); // Aumentar el espacio superior
        formPanel.add(logoLabel, gbc);

        // afegir títol baix del logo
        gbc.gridy = 1;
        formPanel.add(titleLabel, gbc);

        // Email label y camp de text
        JLabel userLabel = new JLabel("EMAIL:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Solo una columna
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(userLabel, gbc);

        userText = new JTextField(20); // TAmany del del JTextField
        userText.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Llenar horizontalmente
        formPanel.add(userText, gbc);

        // Contraseña label y campo de texto
        JLabel passLabel = new JLabel("CONTRASENYA:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(passLabel, gbc);

        passText = new JPasswordField(20); //
        passText.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passText, gbc);

        // Botón de login
        loginButton = new JButton("Iniciar sessió");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(30, 136, 229));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Afegir el botón de login darrera fila
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // El botón ocupará dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Hacer que el botón ocupe todo el ancho
        formPanel.add(loginButton, gbc);

        // afegir el formulari (formPanel) al centre
        this.add(formPanel, BorderLayout.CENTER);
    }

    // Métodos per obtenir els valors
    public String getEmail() {
        return userText.getText();
    }

    public String getPassword() {
        return new String(passText.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
