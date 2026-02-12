package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FireAddressResponse;
import SafetyNet.Alerts.service.FireAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FireAddressController {

    private final FireAddressService service;

    public FireAddressController(FireAddressService service) {
        this.service = service;
    }

    @GetMapping("/fire")
    public ResponseEntity<FireAddressResponse> getFireInfo(@RequestParam String address) throws Exception {

        log.info("GET /fire - Request received for address: {}", address);
        log.debug("Starting fire address computation for address: {}", address);

        FireAddressResponse response = service.getFireInfo(address);

        log.info("FireAddress response successfully generated for address: {}", address);
        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }
}
