package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@Valid @RequestBody MedicalRecord record) throws Exception {
        log.info("POST /medicalRecord - Request to add medicalRecord: {} {}",record.getFirstName(),record.getLastName());
        log.debug("Payload received : {}",record);

        MedicalRecord created = service.addMedicalRecord(record);

        log.info("medicalRecord  successfully added : {} {}",record.getFirstName(),record.getLastName());
        return ResponseEntity.ok(created);
    }

    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestBody MedicalRecord updated) throws Exception {

        log.info("PUT /medicalRecord - Request to update person: {} {}", firstName, lastName);
        log.debug("Update payload: {}", updated);

        MedicalRecord result = service.updateMedicalRecord(firstName, lastName, updated);

        if(result!=null){
            log.info("medicalRecord successfully updated: {} {}", firstName, lastName);
            return ResponseEntity.ok(result);
        }else {
            log.info("medicalRecord not found for update: {} {}", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(
            @RequestParam String firstName,
            @RequestParam String lastName) throws Exception {

        log.info("DELETE /medicalRecord - Request to delete medicalRecord: {} {}", firstName, lastName);

        try {
            service.deleteMedicalRecord(firstName, lastName);
            log.info("medicalRecord successfully deleted: {} {}", firstName, lastName);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            log.info("Error deleting person {} {}: {}", firstName, lastName, e.getMessage());
            throw e;
        }
    }
}
