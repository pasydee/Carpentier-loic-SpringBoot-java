package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.PersonInfoResponse;
import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PersonInfoService {

    private final PersonRepository personRepo;
    private final MedicalRecordRepository medicalRepo;

    public PersonInfoService(PersonRepository personRepo, MedicalRecordRepository medicalRepo) {
        this.personRepo = personRepo;
        this.medicalRepo = medicalRepo;
    }

    public List<PersonInfoResponse> getPersonInfo(String lastName) throws Exception {

        log.info("Fetching person info for lastName={}", lastName);

        List<Person> persons = personRepo.getAllPersons();
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        log.debug("Total persons loaded: {}", persons.size());
        log.debug("Total medical records loaded: {}", medicalRecords.size());

        List<PersonInfoResponse> result = new ArrayList<>();

        for (Person p : persons) {
            if (!p.getLastName().equalsIgnoreCase(lastName)) continue;

            log.debug("Matching person found: {} {}", p.getFirstName(), p.getLastName());

            MedicalRecord record = null;
            for (MedicalRecord m : medicalRecords) {
                if (m.getFirstName().equals(p.getFirstName()) &&
                        m.getLastName().equals(p.getLastName())) {
                    record = m;
                    break;
                }
            }

            if (record == null) {
                log.debug("No medical record found for {} {}", p.getFirstName(), p.getLastName());
                continue;
            }

            PersonInfoResponse info = new PersonInfoResponse();
            info.setFirstName(p.getFirstName());
            info.setLastName(p.getLastName());
            info.setAddress(p.getAddress());
            info.setEmail(p.getEmail());
            info.setAge(calculateAge(record.getBirthdate()));
            info.setMedications(record.getMedications());
            info.setAllergies(record.getAllergies());

            result.add(info);

            log.debug("PersonInfo added: {} {} (age {})",
                    p.getFirstName(), p.getLastName(), info.getAge());
        }

        log.info("PersonInfo response generated for lastName {}: {} persons found",
                lastName, result.size());

        return result;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
