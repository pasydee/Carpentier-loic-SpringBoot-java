package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.Firestation;
import  SafetyNet.Alerts.service.FirestationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService service;

    public FirestationController(FirestationService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Firestation>addFirestation(@RequestBody Firestation firestation)throws Exception {
        return ResponseEntity.ok(service.addFirestation(firestation));
    }

    @PutMapping
    public ResponseEntity<Firestation> updateFirestation( @RequestParam String address, @RequestParam int station, @RequestBody Firestation updated) throws Exception {
        Firestation result = service.updateFirestation(address, station, updated);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFirestation( @RequestParam(required = false) String address, @RequestParam(required = false) Integer station) throws Exception {
        boolean removed = service.deleteFirestation(address, station);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

