package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FloodStationsResponse;
import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.FirestationRepository;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
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

        // 1. Récupérer toutes les adresses couvertes par les stations
        Set<String> addresses = new HashSet<>();
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (stationNumbers.contains(f.getStation())) {
                addresses.add(f.getAddress());
            }
        }

        // 2. Récupérer toutes les personnes
        List<Person> persons = personRepo.getAllPersons();
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        // 3. Regrouper par adresse
        List<FloodStationsResponse.AddressInfo> households = new ArrayList<>();

        for (String address : addresses) {

            FloodStationsResponse.AddressInfo addressInfo = new FloodStationsResponse.AddressInfo();
            addressInfo.setAddress(address);

            List<FloodStationsResponse.ResidentInfo> residents = new ArrayList<>();

            for (Person p : persons) {
                if (!p.getAddress().equals(address)) continue;

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

                FloodStationsResponse.ResidentInfo info = new FloodStationsResponse.ResidentInfo();
                info.setFirstName(p.getFirstName());
                info.setLastName(p.getLastName());
                info.setPhone(p.getPhone());
                info.setAge(calculateAge(record.getBirthdate()));
                info.setMedications(record.getMedications());
                info.setAllergies(record.getAllergies());

                residents.add(info);
            }

            addressInfo.setResidents(residents);
            households.add(addressInfo);
        }

        FloodStationsResponse response = new FloodStationsResponse();
        response.setHouseholds(households);

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
