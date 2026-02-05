package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.DataWrapper;
import SafetyNet.Alerts.model.MedicalRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private File dataFile = new File("src/main/resources/data.json");

    // ✔️ Constructeur utilisé par Spring
    public MedicalRecordRepository() {}

    // ✔️ Constructeur utilisé uniquement pour les tests
    public MedicalRecordRepository(String path) {
        this.dataFile = new File(path);
    }

    public List<MedicalRecord> getAllMedicalRecords() throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);

        // ✔️ Toujours retourner une liste modifiable
        return wrapper.getMedicalrecords() != null
                ? new ArrayList<>(wrapper.getMedicalrecords())
                : new ArrayList<>();
    }

    public void saveAllMedicalRecords(List<MedicalRecord> records) throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);
        wrapper.setMedicalrecords(records);
        mapper.writeValue(dataFile, wrapper);
    }
}
