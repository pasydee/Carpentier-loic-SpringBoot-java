package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.service.CommunityEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CommunityEmailController {

    private final CommunityEmailService service;

    public CommunityEmailController(CommunityEmailService service) {
        this.service = service;
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmails(@RequestParam String city) throws Exception {

        log.info("GET /communityEmail - Request received for city: {}", city);
        log.debug("Starting email extraction for city: {}", city);

        List<String> emails = service.getEmailsByCity(city);

        log.info("CommunityEmail response successfully generated for city: {}", city);
        log.debug("Emails found: {}", emails);

        return ResponseEntity.ok(emails);
    }
}
