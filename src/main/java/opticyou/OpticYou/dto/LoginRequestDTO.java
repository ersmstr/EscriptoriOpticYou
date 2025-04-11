package opticyou.OpticYou.dto;

/**
 * DTO que representa les credencials d'inici de sessió enviades al backend.
 * <p>
 * Aquest objecte s'utilitza per construir la petició de login amb Retrofit.
 */
/**
 * Autor: mramis
 */
public class LoginRequestDTO {

    /**
     * Correu electrònic de l'usuari.
     */
    private String email;

    /**
     * Contrasenya de l'usuari.
     */
    private String password;

    /**
     * Constructor complet.
     *
     * @param email    Correu electrònic.
     * @param password Contrasenya.
     */
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Retorna l'email de l'usuari.
     *
     * @return Email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Estableix el correu electrònic.
     *
     * @param email Correu electrònic.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna la contrasenya.
     *
     * @return Contrasenya.
     */
    public String getContrasenya() {
        return password;
    }

    /**
     * Estableix la contrasenya.
     *
     * @param contrasenya Nova contrasenya.
     */
    public void setContrasenya(String contrasenya) {
        this.password = contrasenya;
    }
}
