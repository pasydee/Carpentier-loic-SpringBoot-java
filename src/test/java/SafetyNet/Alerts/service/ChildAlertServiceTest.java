package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.ChildAlertResponse;
import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ChildAlertServiceTest {

    private PersonRepository personRepo;
    private MedicalRecordRepository medicalRepo;
    private ChildAlertService service;

    @BeforeEach
    void setup() {
        personRepo = mock(PersonRepository.class);
        medicalRepo = mock(MedicalRecordRepository.class);
        service = new ChildAlertService(personRepo, medicalRepo);
    }

    @Test
    void testChildrenFound() throws Exception {
        Person child = Person.builder().firstName("Tenley").lastName("Boyd").address("1509 Culver St").build();
        Person adult = Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").build();

        MedicalRecord childRecord = MedicalRecord.builder().firstName("Tenley").lastName("Boyd").birthdate("02/18/2018").build();
        MedicalRecord adultRecord = MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984").build();

        when(personRepo.getAllPersons()).thenReturn(List.of(child, adult));
        when(medicalRepo.getAllMedicalRecords()).thenReturn(List.of(childRecord, adultRecord));

        ChildAlertResponse response = service.getChildrenAtAddress("1509 Culver St");

        assertEquals(1, response.getChildren().size());
        assertEquals(1, response.getOtherMembers().size());
    }
}
