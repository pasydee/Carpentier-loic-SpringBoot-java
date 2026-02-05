package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FloodStationsResponse;
import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.FirestationRepository;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FloodStationsServiceTest {

    private FirestationRepository firestationRepo;
    private PersonRepository personRepo;
    private MedicalRecordRepository medicalRepo;
    private FloodStationsService service;

    @BeforeEach
    void setup() {
        firestationRepo = mock(FirestationRepository.class);
        personRepo = mock(PersonRepository.class);
        medicalRepo = mock(MedicalRecordRepository.class);
        service = new FloodStationsService(firestationRepo, personRepo, medicalRepo);
    }

    @Test
    void testFloodInfo() throws Exception {
        when(firestationRepo.getAllFirestations()).thenReturn(List.of(
                new Firestation("1509 Culver St", 1)
        ));

        Person p = Person.builder()
                .firstName("John").lastName("Boyd")
                .address("1509 Culver St")
                .phone("111-111")
                .build();

        MedicalRecord m = MedicalRecord.builder()
                .firstName("John").lastName("Boyd")
                .birthdate("03/06/1984")
                .medications(List.of("aznol:350mg"))
                .allergies(List.of("nillacilan"))
                .build();

        when(personRepo.getAllPersons()).thenReturn(List.of(p));
        when(medicalRepo.getAllMedicalRecords()).thenReturn(List.of(m));

        FloodStationsResponse response = service.getFloodInfo(List.of(1));

        assertEquals(1, response.getHouseholds().size());
        assertEquals(1, response.getHouseholds().get(0).getResidents().size());
    }
}
