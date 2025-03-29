package opticyou.OpticYou.service.auth;

/**
 * Autor: mrami
 */
public class SessionManager {

        private static SessionManager instance;
        private String token;
        private String rol;

        private SessionManager() {
            // Constructor privat per evitar instàncies externes
        }

        public static SessionManager getInstance() {
            if (instance == null) {
                instance = new SessionManager();
            }
            return instance;
        }

        // Guardar el token i el rol després del login
        public void saveSession(String token, String rol) {
            this.token = token;
            this.rol = rol;
        }

        // Obtenir el token
        public String getToken() {
            return token;
        }

        // Obtenir el rol
        public String getRol() {
            return rol;
        }

        // Esborrar la sessió (logout)
        public void clearSession() {
            token = null;
            rol = null;
        }

        // Comprovar si l'usuari està autenticat
        public boolean isAuthenticated() {
            return token != null;
        }
}
