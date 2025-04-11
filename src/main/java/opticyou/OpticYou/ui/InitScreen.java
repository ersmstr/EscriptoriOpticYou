package opticyou.OpticYou.ui;



import opticyou.OpticYou.service.auth.AuthServiceClient;
import opticyou.OpticYou.dto.LoginResponseDTO;
import opticyou.OpticYou.ui.admin.AdminPanelScreen;
import opticyou.OpticYou.rolAdmin.treballador.TreballadorScreen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;

/**
 * Pantalla d'inici de l'aplicació. Mostra el formulari de login i redirigeix l'usuari segons el seu rol.
 * <p>
 * En cas d'autenticació correcta, es mostra la interfície corresponent (admin o treballador).
 */

/**
 * Autor: mramis
 */
public class InitScreen {

    private JFrame frame;
    private LoginPanel loginPanel;

    /**
     * Crea la finestra de login i inicialitza els components.
     *
     * @param appName Nom de l'aplicació que es mostrarà al títol de la finestra.
     */
    public InitScreen(String appName) {
        System.out.println("FINESTRA LOGIN...");

        frame = new JFrame(appName + " - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginPanel = new LoginPanel();
        frame.add(loginPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        setupLoginListener();
    }

    /**
     * Configura el listener del botó de login per iniciar sessió.
     * Si l'autenticació és correcta, obre la pantalla corresponent segons el rol.
     */
    private void setupLoginListener() {
        loginPanel.getLoginButton().addActionListener(e -> {
            String email = loginPanel.getEmail();
            String password = loginPanel.getPassword();

            AuthServiceClient authServiceClient = new AuthServiceClient();
            authServiceClient.login(email, password, new Callback<LoginResponseDTO>() {
                @Override
                public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponseDTO loginResponse = response.body();
                        if (loginResponse.isSuccess()) {
                            frame.dispose(); // Tanquem la pantalla de login

                            // Obrim la interfície segons el rol
                            if ("TREBALLADOR".equalsIgnoreCase(loginResponse.getRol())) {
                                new TreballadorScreen(loginResponse.getToken());
                            } else if ("ADMIN".equalsIgnoreCase(loginResponse.getRol())) {
                                new AdminPanelScreen(loginResponse.getToken());
                            }
                        } else {
                            showErrorMessage("USUARI O CONTRASENYA INCORRECTA");
                        }
                    } else {
                        showErrorMessage("USUARI O CONTRASENYA INCORRECTA");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                    showErrorMessage("Error en la connexió: " + t.getMessage());
                }
            });
        });
    }

    /**
     * Mostra un missatge d’error en una finestra emergent.
     *
     * @param message Text del missatge a mostrar.
     */
    private void showErrorMessage(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                frame, message, "Error", JOptionPane.ERROR_MESSAGE));
    }
}
