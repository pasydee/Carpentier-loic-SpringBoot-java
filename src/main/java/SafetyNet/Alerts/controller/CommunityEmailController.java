package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.service.CommunityEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommunityEmailController {

    private final CommunityEmailService service;

    public CommunityEmailController(CommunityEmailService service) {
        this.service = service;
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmails(@RequestParam String city) throws Exception {
        return ResponseEntity.ok(service.getEmailsByCity(city));
    }
}
