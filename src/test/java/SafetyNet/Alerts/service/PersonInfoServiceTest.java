package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.PersonInfoResponse;
import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PersonInfoServiceTest {

    private PersonRepository personRepo;
    private MedicalRecordRepository medicalRepo;
    private PersonInfoService service;

    @BeforeEach
    void setup() {
        personRepo = mock(PersonRepository.class);
        medicalRepo = mock(MedicalRecordRepository.class);
        service = new PersonInfoService(personRepo, medicalRepo);
    }

    @Test
    void testPersonInfo() throws Exception {
        Person p = Person.builder()
                .firstName("John").lastName("Boyd")
                .address("1509 Culver St")
                .email("john@mail.com")
                .build();

        MedicalRecord m = MedicalRecord.builder()
                .firstName("John").lastName("Boyd")
                .birthdate("03/06/1984")
                .medications(List.of("aznol:350mg"))
                .allergies(List.of("nillacilan"))
                .build();

        when(personRepo.getAllPersons()).thenReturn(List.of(p));
        when(medicalRepo.getAllMedicalRecords()).thenReturn(List.of(m));

        List<PersonInfoResponse> response = service.getPersonInfo("Boyd");

        assertEquals(1, response.size());
        assertEquals("John", response.get(0).getFirstName());
    }
}
