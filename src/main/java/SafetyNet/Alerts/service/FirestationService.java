package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.repository.FirestationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FirestationService {

    private final FirestationRepository repository;

    public FirestationService(FirestationRepository repository) {
        this.repository = repository;
    }

    public Firestation addFirestation(Firestation firestation) throws Exception {

        log.info("Adding new firestation mapping: {} -> station {}", firestation.getAddress(), firestation.getStation());
        log.debug("Firestation payload: {}", firestation);

        List<Firestation> list = repository.getAllFirestations();
        list.add(firestation);
        repository.saveAllFirestations(list);

        log.info("Firestation mapping successfully added: {} -> station {}", firestation.getAddress(), firestation.getStation());
        return firestation;
    }

    public Firestation updateFirestation(String address, int station, Firestation updated) throws Exception {

        log.info("Updating firestation mapping for address {} (old station {})", address, station);
        log.debug("Update payload: {}", updated);

        List<Firestation> list = repository.getAllFirestations();

        for (Firestation f : list) {
            if (f.getAddress().equals(address) && f.getStation() == station) {

                log.debug("Firestation mapping found, applying update");

                f.setStation(updated.getStation());
                repository.saveAllFirestations(list);

                log.info("Firestation mapping successfully updated: {} -> station {}", address, updated.getStation());
                return f;
            }
        }

        log.error("Firestation mapping not found for update: {} (station {})", address, station);
        return null;
    }

    public boolean deleteFirestation(String address, Integer station) throws Exception {

        log.info("Deleting firestation mapping with address={} station={}", address, station);

        List<Firestation> list = repository.getAllFirestations();

        boolean removed = list.removeIf(f ->
                (address != null && f.getAddress().equals(address)) ||
                        (station != null && f.getStation() == station)
        );

        if (removed) {
            repository.saveAllFirestations(list);
            log.info("Firestation mapping successfully deleted (address={} station={})", address, station);
        } else {
            log.error("Firestation mapping not found for deletion (address={} station={})", address, station);
        }

        return removed;
    }
}
