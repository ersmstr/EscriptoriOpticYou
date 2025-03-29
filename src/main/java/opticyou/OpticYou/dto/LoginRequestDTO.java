package opticyou.OpticYou.dto;
/**
 * Autor: mrami
 */
public class LoginRequestDTO {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return password;
    }

    public void setContrasenya(String contrasenya) {
        this.password = contrasenya;
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
