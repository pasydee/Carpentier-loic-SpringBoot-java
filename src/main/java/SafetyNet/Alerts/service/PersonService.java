package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository){
        this.repository = repository;
    }

    public Person addPerson(Person person) throws Exception {

        log.info("Adding new person: {} {}", person.getFirstName(), person.getLastName());
        log.debug("Person payload: {}", person);

        List<Person> persons = repository.getAllPersons();
        persons.add(person);
        repository.saveAllPersons(persons);

        log.info("Person successfully added: {} {}", person.getFirstName(), person.getLastName());
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person updated) throws Exception {

        log.info("Updating person: {} {}", firstName, lastName);
        log.debug("Update payload: {}", updated);

        List<Person> persons = repository.getAllPersons();

        for (Person p : persons) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {

                log.debug("Person found, applying updates");

                p.setAddress(updated.getAddress());
                p.setCity(updated.getCity());
                p.setZip(updated.getZip());
                p.setPhone(updated.getPhone());
                p.setEmail(updated.getEmail());

                repository.saveAllPersons(persons);

                log.info("Person successfully updated: {} {}", firstName, lastName);
                return p;
            }
        }

        log.error("Person not found for update: {} {}", firstName, lastName);
        return null;
    }

    public boolean deletePerson(String firstName, String lastName) throws Exception {

        log.info("Deleting person: {} {}", firstName, lastName);

        List<Person> persons = repository.getAllPersons();

        boolean removed = persons.removeIf(p ->
                p.getFirstName().equals(firstName) &&
                        p.getLastName().equals(lastName)
        );

        if (removed) {
            repository.saveAllPersons(persons);
            log.info("Person successfully deleted: {} {}", firstName, lastName);
        } else {
            log.error("Person not found for deletion: {} {}", firstName, lastName);
        }

        return removed;
    }
}
