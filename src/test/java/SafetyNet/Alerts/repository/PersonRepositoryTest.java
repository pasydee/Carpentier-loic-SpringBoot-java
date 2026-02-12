package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.Person;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    private PersonRepository repo;
    private List<Person> persons;

    @BeforeEach
    void setUp()throws Exception{
        repo = new PersonRepository("src/test/resources/data-test.json");
        persons = repo.getAllPersons();
    }

    @Test
    void testReadJson() throws Exception {
        assertNotNull(persons);
        assertTrue(persons.size() > 0);
    }

    @Test
    void testWriteJson() throws Exception {;
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
