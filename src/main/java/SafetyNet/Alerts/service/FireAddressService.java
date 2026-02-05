package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FireAddressResponse;
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
import java.util.ArrayList;
import java.util.List;

@Service
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

        // 1. Trouver la caserne qui couvre l'adresse
        int stationNumber = 0;
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (f.getAddress().equals(address)) {
                stationNumber = f.getStation();
                break;
            }
        }

        // 2. Récupérer les habitants de l'adresse
        List<Person> persons = new ArrayList<>();
        for (Person p : personRepo.getAllPersons()) {
            if (p.getAddress().equals(address)) {
                persons.add(p);
            }
        }

        // 3. Récupérer les dossiers médicaux
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        List<FireAddressResponse.ResidentInfo> residents = new ArrayList<>();

        for (Person p : persons) {

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

            // Construire l'objet ResidentInfo
            FireAddressResponse.ResidentInfo info = new FireAddressResponse.ResidentInfo();
            info.setFirstName(p.getFirstName());
            info.setLastName(p.getLastName());
            info.setPhone(p.getPhone());
            info.setAge(calculateAge(record.getBirthdate()));
            info.setMedications(record.getMedications());
            info.setAllergies(record.getAllergies());

            residents.add(info);
        }

        // 4. Construire la réponse finale
        FireAddressResponse response = new FireAddressResponse();
        response.setStationNumber(stationNumber);
        response.setResidents(residents);

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
