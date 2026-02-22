package SafetyNet.Alerts.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Lasr name cannot be empty")
    private String lastName;

    private String address;
    private String city;
    private String zip;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}",message = "phone must be in format XXX-XXX-XXXX")
    private String phone;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",message = "email must be valid")
    private String email;

}
