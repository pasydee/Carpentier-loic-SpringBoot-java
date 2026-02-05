package SafetyNet.Alerts.service;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.repository.FirestationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FirestationServiceTest {

    private FirestationRepository repository;
    private FirestationService service;

    @BeforeEach
    void setup() {
        repository = mock(FirestationRepository.class);
        service = new FirestationService(repository);
    }

    @Test
    void testAddFirestation() throws Exception {
        List<Firestation> list = new ArrayList<>();
        when(repository.getAllFirestations()).thenReturn(list);

        Firestation f = new Firestation();
        f.setAddress("1509 Culver St");
        f.setStation(3);

        Firestation result = service.addFirestation(f);

        assertEquals("1509 Culver St", result.getAddress());
        verify(repository).saveAllFirestations(list);
    }

    @Test
    void testUpdateFirestation() throws Exception {
        Firestation existing = new Firestation();
        existing.setAddress("1509 Culver St");
        existing.setStation(3);

        List<Firestation> list = new ArrayList<>();
        list.add(existing);

        when(repository.getAllFirestations()).thenReturn(list);

        Firestation updated = new Firestation();
        updated.setStation(5);

        Firestation result = service.updateFirestation("1509 Culver St", 3, updated);

        assertEquals(5, result.getStation());
        verify(repository).saveAllFirestations(list);
    }

    @Test
    void testDeleteFirestationByAddress() throws Exception {
        Firestation f = new Firestation();
        f.setAddress("1509 Culver St");
        f.setStation(3);

        List<Firestation> list = new ArrayList<>();
        list.add(f);

        when(repository.getAllFirestations()).thenReturn(list);

        boolean result = service.deleteFirestation("1509 Culver St", null);

        assertTrue(result);
        verify(repository).saveAllFirestations(any());
    }
}
