package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.DataWrapper;
import SafetyNet.Alerts.model.Firestation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository
public class FirestationRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final File dataFile = new File("src/main/resources/data.json");

    public List<Firestation> getAllFirestations() throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);
        return wrapper.getFirestations();
    }

    public void saveAllFirestations(List<Firestation> firestations) throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);
        wrapper.setFirestations(firestations);
        mapper.writeValue(dataFile, wrapper);
    }
}
