package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FloodStationsResponse;
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
import java.util.*;

@Service
@Slf4j
public class FloodStationsService {

    private final FirestationRepository firestationRepo;
    private final PersonRepository personRepo;
    private final MedicalRecordRepository medicalRepo;

    public FloodStationsService(FirestationRepository firestationRepo,
                                PersonRepository personRepo,
                                MedicalRecordRepository medicalRepo) {
        this.firestationRepo = firestationRepo;
        this.personRepo = personRepo;
        this.medicalRepo = medicalRepo;
    }

    public FloodStationsResponse getFloodInfo(List<Integer> stationNumbers) throws Exception {

        log.info("Fetching flood information for stations: {}", stationNumbers);

        Set<String> addresses = new HashSet<>();
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (stationNumbers.contains(f.getStation())) {
                addresses.add(f.getAddress());
                log.debug("Address covered by station {}: {}", f.getStation(), f.getAddress());
            }
        }

        List<Person> persons = personRepo.getAllPersons();
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        log.debug("Total persons loaded: {}", persons.size());
        log.debug("Total medical records loaded: {}", medicalRecords.size());

        List<FloodStationsResponse.AddressInfo> households = new ArrayList<>();

        for (String address : addresses) {

            log.debug("Processing address: {}", address);

            FloodStationsResponse.AddressInfo addressInfo = new FloodStationsResponse.AddressInfo();
            addressInfo.setAddress(address);

            List<FloodStationsResponse.ResidentInfo> residents = new ArrayList<>();

            for (Person p : persons) {
                if (!p.getAddress().equals(address)) continue;

                log.debug("Resident found at {}: {} {}", address, p.getFirstName(), p.getLastName());

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

                FloodStationsResponse.ResidentInfo info = new FloodStationsResponse.ResidentInfo();
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

            addressInfo.setResidents(residents);
            households.add(addressInfo);

            log.debug("Completed address {}: {} residents", address, residents.size());
        }

        FloodStationsResponse response = new FloodStationsResponse();
        response.setHouseholds(households);

        log.info("FloodStations response generated: {} households", households.size());

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
