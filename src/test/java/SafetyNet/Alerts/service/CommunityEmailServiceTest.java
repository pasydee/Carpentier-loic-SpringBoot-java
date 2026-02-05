package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CommunityEmailServiceTest {

    private PersonRepository personRepo;
    private CommunityEmailService service;

    @BeforeEach
    void setup() {
        personRepo = mock(PersonRepository.class);
        service = new CommunityEmailService(personRepo);
    }

    @Test
    void testEmailsByCity() throws Exception {
        when(personRepo.getAllPersons()).thenReturn(List.of(
                Person.builder().city("Culver").email("a@mail.com").build(),
                Person.builder().city("Culver").email("b@mail.com").build()
        ));

        List<String> emails = service.getEmailsByCity("Culver");

        assertEquals(2, emails.size());
    }
}
