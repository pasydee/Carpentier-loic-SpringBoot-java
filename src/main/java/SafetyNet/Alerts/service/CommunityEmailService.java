package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityEmailService {

    private final PersonRepository personRepo;

    public CommunityEmailService(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    public List<String> getEmailsByCity(String city) throws Exception {

        List<Person> persons = personRepo.getAllPersons();
        List<String> emails = new ArrayList<>();

        for (Person p : persons) {
            if (p.getCity().equalsIgnoreCase(city)) {
                if (!emails.contains(p.getEmail())) {
                    emails.add(p.getEmail());
                }
            }
        }

        return emails;
    }
}
