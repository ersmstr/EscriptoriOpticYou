package opticyou.OpticYou.ui;

import opticyou.OpticYou.service.auth.AuthServiceClient;
import opticyou.OpticYou.dto.LoginResponseDTO;
import opticyou.OpticYou.ui.rols.AdminPanelScreen;
import opticyou.OpticYou.ui.rols.ClientPanelScreen;
import opticyou.OpticYou.ui.rols.TreballadorPanelScreen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;

public class InitScreen {

    private JFrame frame;
    private LoginPanel loginPanel;

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
                            switch (loginResponse.getRol().toUpperCase()) {
                                case "TREBALLADOR":
                                    new TreballadorPanelScreen(loginResponse.getToken());
                                    break;
                                case "ADMIN":
                                    new AdminPanelScreen(loginResponse.getToken());
                                    break;
                                case "CLIENT":
                                    new ClientPanelScreen(loginResponse.getToken());
                                    break;
                                default:
                                    showErrorMessage("Rol desconegut: " + loginResponse.getRol());
                                    break;
                            }
                        } else {
                            showErrorMessage("USUARI O CONTRASENYA INCORRECTA");
                        }
                    } else {
                        showErrorMessage("Credencials incorrectes o error de resposta");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                    showErrorMessage("Error en la connexió: " + t.getMessage());
                }
            });
        });
    }

    private void showErrorMessage(String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE));
    }
}
