package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.PersonInfoResponse;
import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonInfoService {

    private final PersonRepository personRepo;
    private final MedicalRecordRepository medicalRepo;

    public PersonInfoService(PersonRepository personRepo, MedicalRecordRepository medicalRepo) {
        this.personRepo = personRepo;
        this.medicalRepo = medicalRepo;
    }

    public List<PersonInfoResponse> getPersonInfo(String lastName) throws Exception {

        List<Person> persons = personRepo.getAllPersons();
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        List<PersonInfoResponse> result = new ArrayList<>();

        for (Person p : persons) {
            if (!p.getLastName().equalsIgnoreCase(lastName)) continue;

            // Trouver le dossier médical
            MedicalRecord record = null;
            for (MedicalRecord m : medicalRecords) {
                if (m.getFirstName().equals(p.getFirstName()) &&
                        m.getLastName().equals(p.getLastName())) {
                    record = m;
                    break;
                }
            }

            if (record == null) continue;

            PersonInfoResponse info = new PersonInfoResponse();
            info.setFirstName(p.getFirstName());
            info.setLastName(p.getLastName());
            info.setAddress(p.getAddress());
            info.setEmail(p.getEmail());
            info.setAge(calculateAge(record.getBirthdate()));
            info.setMedications(record.getMedications());
            info.setAllergies(record.getAllergies());

            result.add(info);
        }

        return result;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
