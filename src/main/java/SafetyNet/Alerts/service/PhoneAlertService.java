package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.FirestationRepository;
import SafetyNet.Alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PhoneAlertService {

    private final FirestationRepository firestationRepo;
    private final PersonRepository personRepo;

    public PhoneAlertService(FirestationRepository firestationRepo, PersonRepository personRepo) {
        this.firestationRepo = firestationRepo;
        this.personRepo = personRepo;
    }

    public List<String> getPhonesByStation(int stationNumber) throws Exception {

        log.info("Fetching phone alerts for firestation {}", stationNumber);

        List<String> addresses = new ArrayList<>();
        for (Firestation f : firestationRepo.getAllFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
                log.debug("Address covered by station {}: {}", stationNumber, f.getAddress());
            }
        }

        List<String> phones = new ArrayList<>();
        for (Person p : personRepo.getAllPersons()) {
            if (addresses.contains(p.getAddress())) {

                log.debug("Person found at covered address: {} {} ({})",
                        p.getFirstName(), p.getLastName(), p.getPhone());

                if (!phones.contains(p.getPhone())) {
                    phones.add(p.getPhone());
                    log.debug("Phone added: {}", p.getPhone());
                }
            }
        }

        log.info("PhoneAlert response generated for station {}: {} phone numbers found",
                stationNumber, phones.size());

        return phones;
    }
}
