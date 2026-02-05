package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.FirestationRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PhoneAlertServiceTest {

    private FirestationRepository firestationRepo;
    private PersonRepository personRepo;
    private PhoneAlertService service;

    @BeforeEach
    void setup() {
        firestationRepo = mock(FirestationRepository.class);
        personRepo = mock(PersonRepository.class);
        service = new PhoneAlertService(firestationRepo, personRepo);
    }

    @Test
    void testPhonesByStation() throws Exception {
        when(firestationRepo.getAllFirestations()).thenReturn(List.of(
                new Firestation("1509 Culver St", 1)
        ));

        when(personRepo.getAllPersons()).thenReturn(List.of(
                Person.builder().address("1509 Culver St").phone("111-111").build(),
                Person.builder().address("1509 Culver St").phone("222-222").build()
        ));

        List<String> phones = service.getPhonesByStation(1);

        assertEquals(2, phones.size());
    }
}
