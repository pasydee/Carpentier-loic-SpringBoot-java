package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.DataWrapper;
import SafetyNet.Alerts.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private File dataFile = new File("src/main/resources/data.json");

    // ✔️ Constructeur utilisé par Spring
    public PersonRepository() {}

    // ✔️ Constructeur utilisé uniquement pour les tests
    public PersonRepository(String path) {
        this.dataFile = new File(path);
    }

    public List<Person> getAllPersons() throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);

        // ✔️ Toujours retourner une liste modifiable
        return wrapper.getPersons() != null
                ? new ArrayList<>(wrapper.getPersons())
                : new ArrayList<>();
    }

    public void saveAllPersons(List<Person> persons) throws Exception {
        DataWrapper wrapper = mapper.readValue(dataFile, DataWrapper.class);
        wrapper.setPersons(persons);
        mapper.writeValue(dataFile, wrapper);
    }
}
