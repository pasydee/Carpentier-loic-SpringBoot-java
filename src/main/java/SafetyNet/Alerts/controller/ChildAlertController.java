package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.ChildAlertResponse;
import SafetyNet.Alerts.service.ChildAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChildAlertController {

    private final ChildAlertService service;

    public ChildAlertController(ChildAlertService service) {
        this.service = service;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getChildAlert(@RequestParam String address) throws Exception {
        return ResponseEntity.ok(service.getChildrenAtAddress(address));
    }
}
