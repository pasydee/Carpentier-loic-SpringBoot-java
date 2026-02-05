package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.FirestationRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneAlertService {

    private final FirestationRepository firestationRepo;
    private final PersonRepository personRepo;

    public PhoneAlertService(FirestationRepository firestationRepo, PersonRepository personRepo) {
        this.firestationRepo = firestationRepo;
        this.personRepo = personRepo;
    }

    public List<String> getPhonesByStation(int stationNumber) throws Exception {

        // 1. Récupérer les adresses couvertes par la caserne
        List<String> addresses = new ArrayList<>();
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }

        // 2. Récupérer les numéros de téléphone des personnes vivant à ces adresses
        List<String> phones = new ArrayList<>();
        for (Person p : personRepo.getAllPersons()) {
            if (addresses.contains(p.getAddress())) {
                if (!phones.contains(p.getPhone())) {
                    phones.add(p.getPhone());
                }
            }
        }

        return phones;
    }
}
