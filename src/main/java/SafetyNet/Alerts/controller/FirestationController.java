package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.service.FirestationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
@Slf4j
public class FirestationController {

    private final FirestationService service;

    public FirestationController(FirestationService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) throws Exception {

        log.info("POST /firestation - Request to add firestation mapping: {} -> station {}",
                firestation.getAddress(), firestation.getStation());
        log.debug("Payload received: {}", firestation);

        Firestation created = service.addFirestation(firestation);

        log.info("Firestation mapping successfully added: {} -> station {}",
                created.getAddress(), created.getStation());

        return ResponseEntity.ok(created);
    }

    @PutMapping
    public ResponseEntity<Firestation> updateFirestation(
            @RequestParam String address,
            @RequestParam int station,
            @RequestBody Firestation updated) throws Exception {

        log.info("PUT /firestation - Request to update mapping for address: {} (old station: {})",
                address, station);
        log.debug("Update payload: {}", updated);

        Firestation result = service.updateFirestation(address, station, updated);

        if (result != null) {
            log.info("Firestation mapping successfully updated for address: {}", address);
            return ResponseEntity.ok(result);
        } else {
            log.error("Firestation mapping not found for update: {} (station {})", address, station);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFirestation(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Integer station) throws Exception {

        log.info("DELETE /firestation - Request to delete mapping with address={} station={}",
                address, station);

        boolean removed = service.deleteFirestation(address, station);

        if (removed) {
            log.info("Firestation mapping successfully deleted (address={} station={})",
                    address, station);
            return ResponseEntity.ok().build();
        } else {
            log.error("Firestation mapping not found for deletion (address={} station={})",
                    address, station);
            return ResponseEntity.notFound().build();
        }
    }
}
