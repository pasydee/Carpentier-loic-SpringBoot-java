package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FirestationPersonsResponse;
import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.FirestationRepository;
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
public class FirestationQueryService {

    private final FirestationRepository firestationRepo;
    private final PersonRepository personRepo;
    private final MedicalRecordRepository medicalRepo;

    public FirestationQueryService(FirestationRepository firestationRepo,
                                   PersonRepository personRepo,
                                   MedicalRecordRepository medicalRepo) {
        this.firestationRepo = firestationRepo;
        this.personRepo = personRepo;
        this.medicalRepo = medicalRepo;
    }

    public FirestationPersonsResponse getPersonsByStation(int stationNumber) throws Exception {

        log.info("Fetching persons covered by firestation {}", stationNumber);

        List<String> addresses = new ArrayList<>();
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
                log.debug("Address covered by station {}: {}", stationNumber, f.getAddress());
            }
        }

        List<Person> persons = new ArrayList<>();
        for (Person p : personRepo.getAllPersons()) {
            if (addresses.contains(p.getAddress())) {
                persons.add(p);
                log.debug("Person found at covered address: {} {} ({})",
                        p.getFirstName(), p.getLastName(), p.getAddress());
            }
        }

        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();
        log.debug("Total medical records loaded: {}", medicalRecords.size());

        int adultCount = 0;
        int childCount = 0;

        List<FirestationPersonsResponse.PersonInfo> resultPersons = new ArrayList<>();

        for (Person p : persons) {

            MedicalRecord record = null;
            for (MedicalRecord m : medicalRecords) {
                if (m.getFirstName().equals(p.getFirstName()) &&
                        m.getLastName().equals(p.getLastName())) {
                    record = m;
                    break;
                }
            }

            if (record != null) {
                int age = calculateAge(record.getBirthdate());
                log.debug("Age calculated for {} {}: {}", p.getFirstName(), p.getLastName(), age);

                if (age <= 18) childCount++;
                else adultCount++;
            } else {
                log.debug("No medical record found for {} {}", p.getFirstName(), p.getLastName());
            }

            FirestationPersonsResponse.PersonInfo info = new FirestationPersonsResponse.PersonInfo();
            info.setFirstName(p.getFirstName());
            info.setLastName(p.getLastName());
            info.setAddress(p.getAddress());
            info.setPhone(p.getPhone());

            resultPersons.add(info);
        }

        FirestationPersonsResponse response = new FirestationPersonsResponse();
        response.setPersons(resultPersons);
        response.setAdultCount(adultCount);
        response.setChildCount(childCount);

        log.info("Firestation query result for station {}: {} adults, {} children, {} persons total",
                stationNumber, adultCount, childCount, resultPersons.size());

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
