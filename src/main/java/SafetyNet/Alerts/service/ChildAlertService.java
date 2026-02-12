package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.ChildAlertResponse;
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
public class ChildAlertService {

    private final PersonRepository personRepo;
    private final MedicalRecordRepository medicalRepo;

    public ChildAlertService(PersonRepository personRepo, MedicalRecordRepository medicalRepo) {
        this.personRepo = personRepo;
        this.medicalRepo = medicalRepo;
    }

    public ChildAlertResponse getChildrenAtAddress(String address) throws Exception {

        log.info("Fetching child alert information for address: {}", address);

        List<Person> persons = personRepo.getAllPersons();
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        log.debug("Total persons loaded: {}", persons.size());
        log.debug("Total medical records loaded: {}", medicalRecords.size());

        List<ChildAlertResponse.ChildInfo> children = new ArrayList<>();
        List<ChildAlertResponse.FamilyMember> others = new ArrayList<>();

        for (Person p : persons) {

            if (!p.getAddress().equals(address)) continue;

            log.debug("Person at address found: {} {}", p.getFirstName(), p.getLastName());

            MedicalRecord record = medicalRecords.stream()
                    .filter(m -> m.getFirstName().equals(p.getFirstName())
                            && m.getLastName().equals(p.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record == null) {
                log.debug("No medical record found for {} {}", p.getFirstName(), p.getLastName());
                continue;
            }

            int age = calculateAge(record.getBirthdate());
            log.debug("Calculated age for {} {}: {}", p.getFirstName(), p.getLastName(), age);

            if (age <= 18) {
                ChildAlertResponse.ChildInfo child = new ChildAlertResponse.ChildInfo();
                child.setFirstName(p.getFirstName());
                child.setLastName(p.getLastName());
                child.setAge(age);
                children.add(child);

                log.debug("Added child: {} {} ({} years)", p.getFirstName(), p.getLastName(), age);

            } else {
                ChildAlertResponse.FamilyMember member = new ChildAlertResponse.FamilyMember();
                member.setFirstName(p.getFirstName());
                member.setLastName(p.getLastName());
                others.add(member);

                log.debug("Added adult family member: {} {}", p.getFirstName(), p.getLastName());
            }
        }

        ChildAlertResponse response = new ChildAlertResponse();
        response.setChildren(children);
        response.setOtherMembers(others);

        log.info("ChildAlert response generated for address {}: {} children, {} other members",
                address, children.size(), others.size());

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
