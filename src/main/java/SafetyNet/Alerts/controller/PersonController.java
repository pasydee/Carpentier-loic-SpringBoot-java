package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@Slf4j
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws Exception {
        log.info("POST /person - Request to add person: {} {}", person.getFirstName(), person.getLastName());
        log.debug("Payload received: {}", person);

        Person created = service.addPerson(person);

        log.info("Person successfully added: {} {}", created.getFirstName(), created.getLastName());
        return ResponseEntity.ok(created);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestBody Person updated) throws Exception {

        log.info("PUT /person - Request to update person: {} {}", firstName, lastName);
        log.debug("Update payload: {}", updated);

        Person result = service.updatePerson(firstName, lastName, updated);

        if (result != null) {
            log.info("Person successfully updated: {} {}", firstName, lastName);
            return ResponseEntity.ok(result);
        } else {
            log.error("Person not found for update: {} {}", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePerson(
            @RequestParam String firstName,
            @RequestParam String lastName) throws Exception {

        log.info("DELETE /person - Request to delete person: {} {}", firstName, lastName);

        try {
            service.deletePerson(firstName, lastName);
            log.info("Person successfully deleted: {} {}", firstName, lastName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting person {} {}: {}", firstName, lastName, e.getMessage());
            throw e;
        }
    }
}
