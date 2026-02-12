package SafetyNet.Alerts.repository;

import SafetyNet.Alerts.model.Firestation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirestationRepositoryTest {

    private  FirestationRepository repo;
    private List<Firestation> firestations;

    @BeforeEach
    void setup()throws Exception{
        repo = new FirestationRepository("src/test/resources/data-test.json");
        firestations = repo.getAllFirestations();
    }

    @Test
    void testReadJson() throws Exception {

        assertNotNull(firestations);
        assertFalse(firestations.isEmpty());
        assertEquals("29 15th St", firestations.get(0).getAddress());
    }

    @Test
    void testWriteJson() throws Exception {
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
