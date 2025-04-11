package opticyou.OpticYou.dto;

/**
 * DTO que representa la resposta del backend després d’un intent de login.
 * <p>
 * Conté informació sobre si el login ha estat satisfactori, el token d’autenticació
 * i el rol de l’usuari (per exemple: ADMIN, CLIENT...).
 *
 * Autor: mrami
 */
public class LoginResponseDTO {

    /**
     * Indica si el login ha tingut èxit.
     */
    private boolean success;

    /**
     * Token JWT retornat pel servidor si el login és correcte.
     */
    private String token;

    /**
     * Rol de l’usuari autenticat.
     */
    private String rol;

    /**
     * Constructor complet.
     *
     * @param success Estat de l’autenticació.
     * @param token   Token JWT si el login és vàlid.
     * @param rol     Rol de l’usuari autenticat.
     */
    public LoginResponseDTO(boolean success, String token, String rol) {
        this.success = success;
        this.token = token;
        this.rol = rol;
    }

    /**
     * Retorna si el login ha estat exitós.
     *
     * @return {@code true} si ha tingut èxit, {@code false} altrament.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Estableix l’estat de l’autenticació.
     *
     * @param success Estat del login.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Retorna el token JWT.
     *
     * @return Token JWT com a cadena.
     */
    public String getToken() {
        return token;
    }

    /**
     * Estableix el token JWT.
     *
     * @param token Token JWT rebut del servidor.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Retorna el rol de l’usuari autenticat.
     *
     * @return Rol de l’usuari (ex: ADMIN, CLIENT...).
     */
    public String getRol() {
        return rol;
    }

    /**
     * Estableix el rol de l’usuari autenticat.
     *
     * @param rol Valor del rol.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
