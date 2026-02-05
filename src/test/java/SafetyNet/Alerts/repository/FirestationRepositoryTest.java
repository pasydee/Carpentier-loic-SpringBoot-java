package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.Firestation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirestationRepositoryTest {

    @Test
    void testReadJson() throws Exception {
        FirestationRepository repo = new FirestationRepository("src/test/resources/data-test.json");

        List<Firestation> firestations = repo.getAllFirestations();

        assertNotNull(firestations);
        assertFalse(firestations.isEmpty());
        assertEquals("29 15th St", firestations.get(0).getAddress());
    }

    @Test
    void testWriteJson() throws Exception {
        FirestationRepository repo = new FirestationRepository("src/test/resources/data-test.json");

        List<Firestation> firestations = repo.getAllFirestations();
        int sizeBefore = firestations.size();

        Firestation f = new Firestation();
        f.setAddress("29 15th St");
        f.setStation(2);

        firestations.add(f);
        repo.saveAllFirestations(firestations);

        List<Firestation> updated = repo.getAllFirestations();
        assertEquals(sizeBefore + 1, updated.size());
    }
}
