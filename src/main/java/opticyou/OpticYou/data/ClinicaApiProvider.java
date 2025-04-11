package opticyou.OpticYou.data;

@FunctionalInterface
public interface ClinicaApiProvider {
    ClinicaApi getClinicaApi(String token);
}
