package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.PersonInfoResponse;
import SafetyNet.Alerts.service.PersonInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonInfoController {

    private final PersonInfoService service;

    public PersonInfoController(PersonInfoService service) {
        this.service = service;
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoResponse>> getPersonInfo(@RequestParam String lastName) throws Exception {
        return ResponseEntity.ok(service.getPersonInfo(lastName));
    }
}
