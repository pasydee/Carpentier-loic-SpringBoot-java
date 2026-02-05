package SafetyNet.Alerts.dto;

import lombok.Data;
import java.util.List;

@Data
public class CommunityEmailResponse {
    private List<String> emails;
}
