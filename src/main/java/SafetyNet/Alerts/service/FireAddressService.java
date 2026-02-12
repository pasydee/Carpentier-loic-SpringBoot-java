package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FireAddressResponse;
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
public class FireAddressService {

    private final FirestationRepository firestationRepo;
    private final PersonRepository personRepo;
    private final MedicalRecordRepository medicalRepo;

    public FireAddressService(FirestationRepository firestationRepo,
                              PersonRepository personRepo,
                              MedicalRecordRepository medicalRepo) {
        this.firestationRepo = firestationRepo;
        this.personRepo = personRepo;
        this.medicalRepo = medicalRepo;
    }

    public FireAddressResponse getFireInfo(String address) throws Exception {

        log.info("Fetching fire address information for address: {}", address);

        int stationNumber = 0;
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (f.getAddress().equals(address)) {
                stationNumber = f.getStation();
                log.debug("Firestation found for address {}: station {}", address, stationNumber);
                break;
            }
        }

        List<Person> persons = new ArrayList<>();
        for (Person p : personRepo.getAllPersons()) {
            if (p.getAddress().equals(address)) {
                persons.add(p);
                log.debug("Person found at address {}: {} {}", address, p.getFirstName(), p.getLastName());
            }
        }

        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();
        log.debug("Total medical records loaded: {}", medicalRecords.size());

        List<FireAddressResponse.ResidentInfo> residents = new ArrayList<>();

        for (Person p : persons) {

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

            FireAddressResponse.ResidentInfo info = new FireAddressResponse.ResidentInfo();
            info.setFirstName(p.getFirstName());
            info.setLastName(p.getLastName());
            info.setPhone(p.getPhone());
            info.setAge(calculateAge(record.getBirthdate()));
            info.setMedications(record.getMedications());
            info.setAllergies(record.getAllergies());

            residents.add(info);

            log.debug("Resident added: {} {} (age {})",
                    p.getFirstName(), p.getLastName(), info.getAge());
        }

        FireAddressResponse response = new FireAddressResponse();
        response.setStationNumber(stationNumber);
        response.setResidents(residents);

        log.info("FireAddress response generated for {}: station={}, residents={}",
                address, stationNumber, residents.size());

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
