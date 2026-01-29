package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord record) throws Exception {
        return ResponseEntity.ok(service.addMedicalRecord(record));
    }

    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestBody MedicalRecord updated) throws Exception {

        MedicalRecord result = service.updateMedicalRecord(firstName, lastName, updated);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(
            @RequestParam String firstName,
            @RequestParam String lastName) throws Exception {

        boolean removed = service.deleteMedicalRecord(firstName, lastName);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
