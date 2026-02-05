package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.service.PhoneAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PhoneAlertController {

    private final PhoneAlertService service;

    public PhoneAlertController(PhoneAlertService service) {
        this.service = service;
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhones(@RequestParam("firestation") int stationNumber) throws Exception {
        return ResponseEntity.ok(service.getPhonesByStation(stationNumber));
    }
}
