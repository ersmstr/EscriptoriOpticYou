package opticyou.OpticYou;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class HttpsTest {
    public static void main(String[] args) throws Exception {
        // Ruta i contrasenya del truststore
        String trustStorePath = "C:/EscriptoriOpticYou/escrOpticYou/src/main/resources/truststore.jks";
        String trustStorePassword = "password";

        // Desactiva la verificació del nom de l'host (només en local amb cert autofirmat)
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        // Carreguem el truststore
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream trustStream = new java.io.FileInputStream(trustStorePath)) {
            trustStore.load(trustStream, trustStorePassword.toCharArray());
        }

        // Configurem SSLContext
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

        // Preparam la connexió HTTPS amb POST
        URL url = new URL("https://localhost:8083/auth/login-user");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(sslContext.getSocketFactory());
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        // Cos de la petició JSON
        String jsonBody = "{\"email\":\"admin@exemple.com\", \"password\":\"admin123\"}";

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Llegeix el codi i el missatge de resposta
        int responseCode = conn.getResponseCode();
        System.out.println("Resposta del servidor: " + responseCode);

        // Mostra el contingut de la resposta (si hi ha)
        try (InputStream is = conn.getInputStream()) {
            String response = new String(is.readAllBytes(), "utf-8");
            System.out.println("Cos de la resposta:\n" + response);
        } catch (Exception e) {
            System.out.println("No s'ha pogut llegir el cos de la resposta.");
        }
    }
}
