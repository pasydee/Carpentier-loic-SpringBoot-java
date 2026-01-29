package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    @Test
    void testReadJson() throws Exception {
        PersonRepository repo = new PersonRepository("src/test/resources/data-test.json");

        List<Person> persons = repo.getAllPersons();

        assertNotNull(persons);
        assertTrue(persons.size() > 0);
    }

    @Test
    void testWriteJson() throws Exception {
        PersonRepository repo = new PersonRepository("src/test/resources/data-test.json");

        List<Person> persons = repo.getAllPersons();
        int sizeBefore = persons.size();

        Person p = new Person();
        p.setFirstName("Test");
        p.setLastName("User");

        persons.add(p);
        repo.saveAllPersons(persons);

        List<Person> updated = repo.getAllPersons();
        assertEquals(sizeBefore + 1, updated.size());
    }
}
