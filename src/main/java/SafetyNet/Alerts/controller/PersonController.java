package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person)throws Exception{
        return ResponseEntity.ok(service.addPerson(person));
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestParam String firstName, @RequestParam String lastName, @RequestBody Person updated) throws Exception{
        Person result = service.updatePerson(firstName,lastName,updated);
        return (result!=null) ? ResponseEntity.ok().build(): ResponseEntity.notFound().build();

    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }
    @PostMapping("/test")
    public String test1(){
        return test();
    }

}
