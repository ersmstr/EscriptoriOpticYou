package opticyou.OpticYou;
/**
 * Autor: mrami
 */
import com.formdev.flatlaf.FlatLightLaf;
import opticyou.OpticYou.ui.InitScreen;

import javax.swing.*;

public class AppOpticYou {
    private static final String APP_NAME = "OpticYou";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf()); // Aplicar Look & Feel
            } catch (Exception e) {
                e.printStackTrace();
            }

            new InitScreen(APP_NAME); // Inicia la finestra principal
        });
    }
}
