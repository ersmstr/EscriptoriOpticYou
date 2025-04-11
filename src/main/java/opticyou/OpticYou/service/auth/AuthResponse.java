package opticyou.OpticYou.service.auth;

/**
 * Representa la resposta del servidor després d'una autenticació.
 * Aquesta classe es fa servir per deserialitzar el JSON rebut amb el token JWT.
  */

/**
 * Autor: mrami
 */
public class AuthResponse {

    /**
     * Token JWT retornat pel backend després del login.
     */
    private String token;

    /**
     * Retorna el token JWT rebut del backend.
     * @return Token JWT com a cadena.
     */
    public String getToken() {
        return token;
    }

    /**
     * Assigna el token JWT rebut del backend.
     *
     * @param token Cadena amb el token.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
