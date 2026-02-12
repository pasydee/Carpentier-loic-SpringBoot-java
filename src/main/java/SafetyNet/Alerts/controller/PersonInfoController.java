package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.PersonInfoResponse;
import SafetyNet.Alerts.service.PersonInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PersonInfoController {

    private final PersonInfoService service;

    public PersonInfoController(PersonInfoService service) {
        this.service = service;
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoResponse>> getPersonInfo(@RequestParam String lastName) throws Exception {

        log.info("GET /personInfo - Request received for lastName={}", lastName);
        log.debug("Starting person info lookup for lastName={}", lastName);

        List<PersonInfoResponse> response = service.getPersonInfo(lastName);

        log.info("PersonInfo response successfully generated for lastName={}", lastName);
        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }
}
