package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.service.PhoneAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PhoneAlertController {

    private final PhoneAlertService service;

    public PhoneAlertController(PhoneAlertService service) {
        this.service = service;
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhones(@RequestParam("firestation") int stationNumber) throws Exception {

        log.info("GET /phoneAlert - Request received for firestation={}", stationNumber);
        log.debug("Starting phone alert lookup for station {}", stationNumber);

        List<String> phones = service.getPhonesByStation(stationNumber);

        log.info("PhoneAlert response successfully generated for firestation={}", stationNumber);
        log.debug("Phones found: {}", phones);

        return ResponseEntity.ok(phones);
    }
}
