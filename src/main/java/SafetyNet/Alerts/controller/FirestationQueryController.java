package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FirestationPersonsResponse;
import SafetyNet.Alerts.service.FirestationQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FirestationQueryController {

    private final FirestationQueryService service;

    public FirestationQueryController(FirestationQueryService service) {
        this.service = service;
    }

    @GetMapping("/firestation")
    public ResponseEntity<FirestationPersonsResponse> getPersonsByStation(
            @RequestParam("stationNumber") int stationNumber) throws Exception {

        log.info("GET /firestation - Request received for stationNumber={}", stationNumber);
        log.debug("Starting computation for firestation query, station={}", stationNumber);

        FirestationPersonsResponse response = service.getPersonsByStation(stationNumber);

        log.info("Firestation query successfully processed for stationNumber={}", stationNumber);
        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }
}
