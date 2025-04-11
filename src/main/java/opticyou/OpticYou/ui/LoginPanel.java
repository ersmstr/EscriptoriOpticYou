package opticyou.OpticYou.ui;

/**
 * Autor: mrami
 */

import javax.swing.*;
import java.awt.*;

/**
 * Panell de login reutilitzable que conté els camps per introduir email i contrasenya,
 * així com un botó per iniciar sessió.
 * <p>
 * Aquesta classe és utilitzada a {@link InitScreen} com a interfície inicial per accedir a l'aplicació.
 */
public class LoginPanel extends JPanel {

    private JTextField userText;
    private JPasswordField passText;
    private JButton loginButton;

    /**
     * Constructor que inicialitza i disposa els components gràfics del formulari de login.
     */
    public LoginPanel() {
        this.setLayout(new BorderLayout());

        // Barra superior decorativa
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(30, 136, 229)); // Blau
        topBar.setPreferredSize(new Dimension(600, 50));
        this.add(topBar, BorderLayout.NORTH);

        // Formulari central
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Logo (opcional)
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/recursos/Logo.jpg");
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel titleLabel = new JLabel("OpticYou");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 10, 10, 10);
        formPanel.add(logoLabel, gbc);

        gbc.gridy = 1;
        formPanel.add(titleLabel, gbc);

        // Camp Email
        JLabel userLabel = new JLabel("EMAIL:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(userLabel, gbc);

        userText = new JTextField(20);
        userText.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(userText, gbc);

        // Camp Contrasenya
        JLabel passLabel = new JLabel("CONTRASENYA:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(passLabel, gbc);

        passText = new JPasswordField(20);
        passText.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passText, gbc);

        // Botó de login
        loginButton = new JButton("Iniciar sessió");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(30, 136, 229));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(loginButton, gbc);

        this.add(formPanel, BorderLayout.CENTER);
    }

    /**
     * Retorna el text del camp email.
     *
     * @return Correu electrònic introduït.
     */
    public String getEmail() {
        return userText.getText();
    }

    /**
     * Retorna la contrasenya com a cadena.
     *
     * @return Contrasenya introduïda.
     */
    public String getPassword() {
        return new String(passText.getPassword());
    }

    /**
     * Retorna el botó de login per poder-hi afegir listeners externs.
     *
     * @return Botó d'inici de sessió.
     */
    public JButton getLoginButton() {
        return loginButton;
    }
}
