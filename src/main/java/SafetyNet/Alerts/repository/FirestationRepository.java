package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.DataWrapper;
import SafetyNet.Alerts.model.Firestation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private File dataFile = new File("src/main/resources/data.json");

    // ✔️ Constructeur utilisé par Spring (obligatoire)
    public FirestationRepository() {}

    // ✔️ Constructeur utilisé uniquement pour les tests
    public FirestationRepository(String path) {
        this.dataFile = new File(path);
    }

    public List<Firestation> getAllFirestations() throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);

        // ✔️ Toujours retourner une liste modifiable
        return wrapper.getFirestations() != null
                ? new ArrayList<>(wrapper.getFirestations())
                : new ArrayList<>();
    }

    public void saveAllFirestations(List<Firestation> firestations) throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);
        wrapper.setFirestations(firestations);
        mapper.writeValue(dataFile, wrapper);
    }
}
