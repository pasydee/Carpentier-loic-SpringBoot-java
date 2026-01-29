package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository){
        this.repository = repository;
    }

    public Person addPerson(Person person)throws Exception{
        List<Person> persons = repository.getAllPersons();
        persons.add(person);
        repository.saveAllPersons(persons);
        return person;
    }

    public Person updatePerson(String firstName,String lastName,Person updated)throws Exception{

        List<Person> persons= repository.getAllPersons();

        for (Person p : persons){
            if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)){
                p.setAddress(updated.getAddress());
                p.setCity(updated.getCity());
                p.setZip(updated.getZip());
                p.setPhone(updated.getPhone());
                p.setEmail(updated.getEmail());
                repository.saveAllPersons(persons);
                return p;

            }
        }
        return null;
    }

    public boolean deletePerson(String firstName,String lastName)throws Exception{
        List<Person> persons = repository.getAllPersons();
        boolean removed = persons.removeIf(p ->
                p.getFirstName().equals(firstName) &&
                p.getLastName().equals(lastName)
                );
        if(removed){
            repository.saveAllPersons(persons);
        }
        return removed;
    }


}
