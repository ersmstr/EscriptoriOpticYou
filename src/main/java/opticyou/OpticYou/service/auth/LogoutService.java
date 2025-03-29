package opticyou.OpticYou.service.auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;

public class LogoutService {
        public void logout(String token) {


            AuthServiceClient authServiceClient = new AuthServiceClient();
            authServiceClient.logout(token, new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Boolean success = response.body();
                        if (success) {
                            System.out.println("Logout FET.");

                        } else {
                            JOptionPane.showMessageDialog(null, "No s'ha pogut fer logout.", "Error de Logout", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en la resposta del servidor.", "Error de Logout", JOptionPane.ERROR_MESSAGE);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    JOptionPane.showMessageDialog(null, "Error de xarxa al fer logout.", "Error de Logout", JOptionPane.ERROR_MESSAGE);
                }
            });

    }
}

