package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FireAddressResponse;
import SafetyNet.Alerts.service.FireAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireAddressController {

    private final FireAddressService service;

    public FireAddressController(FireAddressService service) {
        this.service = service;
    }

    @GetMapping("/fire")
    public ResponseEntity<FireAddressResponse> getFireInfo(@RequestParam String address) throws Exception {
        return ResponseEntity.ok(service.getFireInfo(address));
    }
}
