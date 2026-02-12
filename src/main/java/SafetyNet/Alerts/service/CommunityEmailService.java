package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommunityEmailService {

    private final PersonRepository personRepo;

    public CommunityEmailService(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    public List<String> getEmailsByCity(String city) throws Exception {

        log.info("Fetching community emails for city: {}", city);

        List<Person> persons = personRepo.getAllPersons();
        log.debug("Total persons loaded: {}", persons.size());

        List<String> emails = new ArrayList<>();

        for (Person p : persons) {
            if (p.getCity().equalsIgnoreCase(city)) {

                log.debug("Person in city found: {} {} ({})",
                        p.getFirstName(), p.getLastName(), p.getEmail());

                if (!emails.contains(p.getEmail())) {
                    emails.add(p.getEmail());
                    log.debug("Email added: {}", p.getEmail());
                }
            }
        }

        log.info("CommunityEmail response generated for city {}: {} emails found",
                city, emails.size());

        return emails;
    }
}
