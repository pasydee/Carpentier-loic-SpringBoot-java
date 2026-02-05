package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.DataWrapper;
import SafetyNet.Alerts.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository
public class PersonRepository {

    private ObjectMapper mapper = new ObjectMapper();
    private File dataFile = new File("src/main/resources/data.json");

    public PersonRepository(String path){
        this.dataFile = new File(path);
        this.mapper = new ObjectMapper();
    }


    public List<Person> getAllPersons() throws Exception{
        DataWrapper wrapper = mapper.readValue(dataFile,DataWrapper.class);
        return wrapper.getPersons();
    }

    public void saveAllPersons(List<Person> persons)throws Exception{
        DataWrapper wrapper = new DataWrapper();
        wrapper.setPersons(persons);
        mapper.writeValue(dataFile, wrapper);
    }
}
