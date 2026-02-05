package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordServiceTest {

    private MedicalRecordRepository repository;
    private MedicalRecordService service;

    @BeforeEach
    void setup() {
        repository = mock(MedicalRecordRepository.class);
        service = new MedicalRecordService(repository);
    }

    @Test
    void testAddMedicalRecord() throws Exception {
        List<MedicalRecord> list = new ArrayList<>();
        when(repository.getAllMedicalRecords()).thenReturn(list);

        MedicalRecord m = new MedicalRecord();
        m.setFirstName("John");
        m.setLastName("Boyd");

        MedicalRecord result = service.addMedicalRecord(m);

        assertEquals("John", result.getFirstName());
        verify(repository).saveAllMedicalRecords(list);
    }

    @Test
    void testUpdateMedicalRecord() throws Exception {
        MedicalRecord existing = new MedicalRecord();
        existing.setFirstName("John");
        existing.setLastName("Boyd");

        List<MedicalRecord> list = new ArrayList<>();
        list.add(existing);

        when(repository.getAllMedicalRecords()).thenReturn(list);

        MedicalRecord updated = new MedicalRecord();
        updated.setBirthdate("01/01/1990");

        MedicalRecord result = service.updateMedicalRecord("John", "Boyd", updated);

        assertEquals("01/01/1990", result.getBirthdate());
        verify(repository).saveAllMedicalRecords(list);
    }

    @Test
    void testDeleteMedicalRecord() throws Exception {
        MedicalRecord m = new MedicalRecord();
        m.setFirstName("John");
        m.setLastName("Boyd");

        List<MedicalRecord> list = new ArrayList<>();
        list.add(m);

        when(repository.getAllMedicalRecords()).thenReturn(list);

        boolean result = service.deleteMedicalRecord("John", "Boyd");

        assertTrue(result);
        verify(repository).saveAllMedicalRecords(any());
    }
}
