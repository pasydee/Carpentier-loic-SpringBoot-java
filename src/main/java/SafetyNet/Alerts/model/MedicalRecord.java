package SafetyNet.Alerts.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}
