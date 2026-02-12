package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MedicalRecordService {

    private final MedicalRecordRepository repository;

    public MedicalRecordService(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    public MedicalRecord addMedicalRecord(MedicalRecord record) throws Exception {

        log.info("Adding new record: {} {}", record.getFirstName(), record.getLastName());
        log.debug("record payload: {}", record);

        List<MedicalRecord> list = repository.getAllMedicalRecords();
        list.add(record);
        repository.saveAllMedicalRecords(list);

        log.info("record successfully added: {} {}", record.getFirstName(), record.getLastName());
        return record;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updated) throws Exception {

        log.info("Updating MedicalRecord: {} {}", firstName, lastName);
        log.debug("Update payload: {}", updated);

        List<MedicalRecord> list = repository.getAllMedicalRecords();

        for (MedicalRecord m : list) {
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {

                log.debug("MedicalRecord found, applying updates");

                m.setBirthdate(updated.getBirthdate());
                m.setMedications(updated.getMedications());
                m.setAllergies(updated.getAllergies());

                repository.saveAllMedicalRecords(list);

                log.info("MedicalRecord successfully updated: {} {}", firstName, lastName);
                return m;
            }
        }
        log.error("MedicalRecord not found for update: {} {}", firstName, lastName);
        return null;
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) throws Exception {

        log.info("Deleting MedicalRecord: {} {}", firstName, lastName);
        List<MedicalRecord> list = repository.getAllMedicalRecords();

        boolean removed = list.removeIf(m ->
                m.getFirstName().equals(firstName) &&
                        m.getLastName().equals(lastName)
        );

        if (removed) {
            repository.saveAllMedicalRecords(list);
            log.info("MedicalRecord successfully deleted: {} {}", firstName, lastName);
        }else{
            log.error("MedicalRecord not found for deletion: {} {}", firstName, lastName);
        }

        return removed;
    }
}
