package opticyou.OpticYou.dto;
/**
 * Autor: mrami
 */
public class LoginResponseDTO {
    private boolean success;
    private String token;
    private String rol;

    public LoginResponseDTO(boolean success, String token, String rol) {
        this.success = success;
        this.token = token;
        this.rol = rol;
    }
    // Getter y Setter para success
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter y Setter para token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter y Setter para rol
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}


