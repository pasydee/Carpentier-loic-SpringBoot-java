package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.ChildAlertResponse;
import SafetyNet.Alerts.service.ChildAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ChildAlertController {

    private final ChildAlertService service;

    public ChildAlertController(ChildAlertService service) {
        this.service = service;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getChildAlert(@RequestParam String address) throws Exception {

        log.info("GET /childAlert - Request received for address: {}", address);
        log.debug("Starting child alert computation for address: {}", address);

        ChildAlertResponse response = service.getChildrenAtAddress(address);

        log.info("ChildAlert response successfully generated for address: {}", address);
        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }
}
