package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.MedicalRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordRepositoryTest {

    @Test
    void testReadJson() throws Exception {
        MedicalRecordRepository repo = new MedicalRecordRepository("src/test/resources/data-test.json");

        List<MedicalRecord> records = repo.getAllMedicalRecords();

        assertNotNull(records);
        assertFalse(records.isEmpty());
        assertEquals("Alice", records.get(0).getFirstName());
    }

    @Test
    void testWriteJson() throws Exception {
        MedicalRecordRepository repo = new MedicalRecordRepository("src/test/resources/data-test.json");

        List<MedicalRecord> records = repo.getAllMedicalRecords();
        int sizeBefore = records.size();

        MedicalRecord m = new MedicalRecord();
        m.setFirstName("Alice");
        m.setLastName("Cooper");
        m.setBirthdate("01/01/1990");
        m.setMedications(List.of("doliprane:500mg"));
        m.setAllergies(List.of("pollen"));

        records.add(m);
        repo.saveAllMedicalRecords(records);

        List<MedicalRecord> updated = repo.getAllMedicalRecords();
        assertEquals(sizeBefore + 1, updated.size());
    }
}
