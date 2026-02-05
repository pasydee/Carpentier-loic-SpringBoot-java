package SafetyNet.Alerts.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChildAlertResponse {

    private List<ChildInfo> children;
    private List<FamilyMember> otherMembers;

    @Data
    public static class ChildInfo {
        private String firstName;
        private String lastName;
        private int age;
    }

    @Data
    public static class FamilyMember {
        private String firstName;
        private String lastName;
    }
}
