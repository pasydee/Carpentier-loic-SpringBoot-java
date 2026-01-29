package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repository;

    public MedicalRecordService(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    public MedicalRecord addMedicalRecord(MedicalRecord record) throws Exception {
        List<MedicalRecord> list = repository.getAllMedicalRecords();
        list.add(record);
        repository.saveAllMedicalRecords(list);
        return record;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updated) throws Exception {
        List<MedicalRecord> list = repository.getAllMedicalRecords();

        for (MedicalRecord m : list) {
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                m.setBirthdate(updated.getBirthdate());
                m.setMedications(updated.getMedications());
                m.setAllergies(updated.getAllergies());
                repository.saveAllMedicalRecords(list);
                return m;
            }
        }
        return null;
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) throws Exception {
        List<MedicalRecord> list = repository.getAllMedicalRecords();

        boolean removed = list.removeIf(m ->
                m.getFirstName().equals(firstName) &&
                        m.getLastName().equals(lastName)
        );

        if (removed) {
            repository.saveAllMedicalRecords(list);
        }

        return removed;
    }
}
