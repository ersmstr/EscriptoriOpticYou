package opticyou.OpticYou.clinica;

@FunctionalInterface
public interface ClinicaApiProvider {
    ClinicaApi getClinicaApi(String token);
}
