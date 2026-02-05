package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.ChildAlertResponse;
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
public class ChildAlertService {

    private final PersonRepository personRepo;
    private final MedicalRecordRepository medicalRepo;

    public ChildAlertService(PersonRepository personRepo, MedicalRecordRepository medicalRepo) {
        this.personRepo = personRepo;
        this.medicalRepo = medicalRepo;
    }

    public ChildAlertResponse getChildrenAtAddress(String address) throws Exception {

        List<Person> persons = personRepo.getAllPersons();
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        List<ChildAlertResponse.ChildInfo> children = new ArrayList<>();
        List<ChildAlertResponse.FamilyMember> others = new ArrayList<>();

        for (Person p : persons) {
            if (!p.getAddress().equals(address)) continue;

            MedicalRecord record = medicalRecords.stream()
                    .filter(m -> m.getFirstName().equals(p.getFirstName())
                            && m.getLastName().equals(p.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record == null) continue;

            int age = calculateAge(record.getBirthdate());

            if (age <= 18) {
                ChildAlertResponse.ChildInfo child = new ChildAlertResponse.ChildInfo();
                child.setFirstName(p.getFirstName());
                child.setLastName(p.getLastName());
                child.setAge(age);
                children.add(child);
            } else {
                ChildAlertResponse.FamilyMember member = new ChildAlertResponse.FamilyMember();
                member.setFirstName(p.getFirstName());
                member.setLastName(p.getLastName());
                others.add(member);
            }
        }

        ChildAlertResponse response = new ChildAlertResponse();
        response.setChildren(children);
        response.setOtherMembers(others);

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
