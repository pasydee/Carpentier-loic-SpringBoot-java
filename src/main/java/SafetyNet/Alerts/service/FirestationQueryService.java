package SafetyNet.Alerts.service;

import SafetyNet.Alerts.dto.FirestationPersonsResponse;
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

        // 1. Récupérer les adresses couvertes par la station
        List<String> addresses = new ArrayList<>();
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }

        // 2. Récupérer les personnes habitant ces adresses
        List<Person> persons = new ArrayList<>();
        for (Person p : personRepo.getAllPersons()) {
            if (addresses.contains(p.getAddress())) {
                persons.add(p);
            }
        }

        // 3. Récupérer les dossiers médicaux
        List<MedicalRecord> medicalRecords = medicalRepo.getAllMedicalRecords();

        int adultCount = 0;
        int childCount = 0;

        List<FirestationPersonsResponse.PersonInfo> resultPersons = new ArrayList<>();

        // 4. Construire la réponse
        for (Person p : persons) {

            // Trouver le dossier médical correspondant
            MedicalRecord record = null;
            for (MedicalRecord m : medicalRecords) {
                if (m.getFirstName().equals(p.getFirstName()) &&
                        m.getLastName().equals(p.getLastName())) {
                    record = m;
                    break;
                }
            }

            // Calcul de l'âge
            if (record != null) {
                int age = calculateAge(record.getBirthdate());
                if (age <= 18) childCount++;
                else adultCount++;
            }

            // Ajouter la personne dans la réponse
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

        return response;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
