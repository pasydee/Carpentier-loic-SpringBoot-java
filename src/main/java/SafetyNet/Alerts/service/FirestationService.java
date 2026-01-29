package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.repository.FirestationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    private final FirestationRepository repository;

    public FirestationService(FirestationRepository repository) {
        this.repository = repository;
    }

    public Firestation addFirestation(Firestation firestation) throws Exception {
        List<Firestation> list = repository.getAllFirestations();
        list.add(firestation);
        repository.saveAllFirestations(list);
        return firestation;
    }

    public Firestation updateFirestation(String address, int station, Firestation updated) throws Exception {
        List<Firestation> list = repository.getAllFirestations();

        for (Firestation f : list) {
            if (f.getAddress().equals(address) && f.getStation() == station) {
                f.setStation(updated.getStation());
                repository.saveAllFirestations(list);
                return f;
            }
        }
        return null;
    }

    public boolean deleteFirestation(String address, Integer station) throws Exception {
        List<Firestation> list = repository.getAllFirestations();

        boolean removed = list.removeIf(f ->
                (address != null && f.getAddress().equals(address)) ||
                        (station != null && f.getStation() == station)
        );

        if (removed) {
            repository.saveAllFirestations(list);
        }

        return removed;
    }
}
