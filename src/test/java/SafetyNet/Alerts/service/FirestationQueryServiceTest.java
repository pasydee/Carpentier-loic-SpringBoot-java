package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FirestationPersonsResponse;
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

class FirestationQueryServiceTest {

    private FirestationRepository firestationRepo;
    private PersonRepository personRepo;
    private MedicalRecordRepository medicalRepo;
    private FirestationQueryService service;

    @BeforeEach
    void setup() {
        firestationRepo = mock(FirestationRepository.class);
        personRepo = mock(PersonRepository.class);
        medicalRepo = mock(MedicalRecordRepository.class);
        service = new FirestationQueryService(firestationRepo, personRepo, medicalRepo);
    }

    @Test
    void testGetPersonsByStation() throws Exception {

        when(firestationRepo.getAllFirestations()).thenReturn(List.of(
                Firestation.builder().address("1509 Culver St").station(1).build()
        ));

        Person adult = Person.builder()
                .firstName("John").lastName("Boyd")
                .address("1509 Culver St")
                .phone("111-111")
                .build();

        MedicalRecord adultRecord = MedicalRecord.builder()
                .firstName("John").lastName("Boyd")
                .birthdate("03/06/1984")
                .build();

        when(personRepo.getAllPersons()).thenReturn(List.of(adult));
        when(medicalRepo.getAllMedicalRecords()).thenReturn(List.of(adultRecord));

        FirestationPersonsResponse response = service.getPersonsByStation(1);

        assertEquals(1, response.getAdultCount());
        assertEquals(0, response.getChildCount());
        assertEquals(1, response.getPersons().size());
    }
}
