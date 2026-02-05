package SafetyNet.Alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FirestationPersonsResponse {

    private List<PersonInfo> persons;
    private int adultCount;
    private int childCount;

    @Data
    public static class PersonInfo {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
    }
}