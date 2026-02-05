package SafetyNet.Alerts.dto;

import lombok.Data;
import java.util.List;

@Data
public class PhoneAlertResponse {
    private List<String> phones;
}
