package SafetyNet.Alerts.dto;

import lombok.Data;
import java.util.List;

@Data
public class FireAddressResponse {

    private int stationNumber;
    private List<ResidentInfo> residents;

    @Data
    public static class ResidentInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;
    }
}
