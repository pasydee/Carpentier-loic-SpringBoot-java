package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FirestationPersonsResponse;
import SafetyNet.Alerts.service.FirestationQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationQueryController {

    private final FirestationQueryService service;

    public FirestationQueryController(FirestationQueryService service) {
        this.service = service;
    }

    @GetMapping("/firestation")
    public ResponseEntity<FirestationPersonsResponse> getPersonsByStation(
            @RequestParam("stationNumber") int stationNumber) throws Exception {

        return ResponseEntity.ok(service.getPersonsByStation(stationNumber));
    }
}
