package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class PersonServiceTest {

    @Test
    void testAddPerson() throws Exception {
        PersonRepository repo = mock(PersonRepository.class);
        PersonService service = new PersonService(repo);

        List<Person> persons = new ArrayList<>();
        when(repo.getAllPersons()).thenReturn(persons);

        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Boyd");

        Person result = service.addPerson(p);

        assertEquals("John", result.getFirstName());
        verify(repo, times(1)).saveAllPersons(persons);
    }

    @Test
    void testUpdatePerson() throws Exception {
        PersonRepository repo = mock(PersonRepository.class);
        PersonService service = new PersonService(repo);

        Person existing = new Person();
        existing.setFirstName("John");
        existing.setLastName("Boyd");
        existing.setAddress("Old Street");

        List<Person> persons = new ArrayList<>();
        persons.add(existing);

        when(repo.getAllPersons()).thenReturn(persons);

        Person updated = new Person();
        updated.setAddress("New Street");

        Person result = service.updatePerson("John", "Boyd", updated);

        assertNotNull(result);
        assertEquals("New Street", result.getAddress());
        verify(repo).saveAllPersons(persons);
    }

    @Test
    void testDeletePerson() throws Exception {
        PersonRepository repo = mock(PersonRepository.class);
        PersonService service = new PersonService(repo);

        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Boyd");

        List<Person> persons = new ArrayList<>();
        persons.add(p);

        when(repo.getAllPersons()).thenReturn(persons);

        boolean removed = service.deletePerson("John", "Boyd");

        assertTrue(removed);
        verify(repo).saveAllPersons(persons);
    }
}
