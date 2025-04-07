package opticyou.OpticYou.ui;
/**
 * Autor: mrami
 */

import opticyou.OpticYou.service.auth.AuthServiceClient;
import opticyou.OpticYou.dto.LoginResponseDTO;
import opticyou.OpticYou.ui.admin.AdminPanelScreen;
import opticyou.OpticYou.treballador.TreballadorScreen;
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


                            frame.dispose();


                            // obrir pantalla segon el rol

                            if ("TREBALLADOR".equalsIgnoreCase(loginResponse.getRol())) {

                                new TreballadorScreen(loginResponse.getToken());
                            } else if ("ADMIN".equalsIgnoreCase(loginResponse.getRol())) {

                                new AdminPanelScreen(loginResponse.getToken());


                            }

                        } else {
                            showErrorMessage("USUARI O CONTRASENYA INCORRECTA");
                        }
                    } else {
                        showErrorMessage("USUARI O CONTRASENYA INCORRECTA ");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                    showErrorMessage("Error en la conexió: " + t.getMessage());
                }
            });
        });
    }

    private void showErrorMessage(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE));
    }
}


