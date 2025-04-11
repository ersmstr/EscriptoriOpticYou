package opticyou.OpticYou.rolAdmin.clinica;

@FunctionalInterface
public interface ClinicaApiProvider {
    ClinicaApi getClinicaApi(String token);
}
