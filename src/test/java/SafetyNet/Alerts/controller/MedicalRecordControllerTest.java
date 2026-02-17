package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.MedicalRecord;
import SafetyNet.Alerts.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalRecordService service;

    @Test
    void testAddMedicalRecord() throws Exception {
        MedicalRecord record = new MedicalRecord();
        record.setFirstName("John");
        record.setLastName("Boyd");
        record.setBirthdate("03/06/1984");
        record.setMedications(List.of("aznol:350mg"));
        record.setAllergies(List.of("nillacilan"));

        when(service.addMedicalRecord(any())).thenReturn(record);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "firstName": "John",
                      "lastName": "Boyd",
                      "birthdate": "03/06/1984",
                      "medications": ["aznol:350mg"],
                      "allergies": ["nillacilan"]
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthdate").value("03/06/1984"));
    }

    @Test
    void testUpdateMedicalRecord() throws Exception {
        MedicalRecord updated = new MedicalRecord();
        updated.setBirthdate("01/01/1990");

        when(service.updateMedicalRecord(eq("John"), eq("Boyd"), any())).thenReturn(updated);

        mockMvc.perform(put("/medicalRecord?firstName=John&lastName=Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "birthdate": "01/01/1990"
                    }
                    """))
                .andExpect(status().isOk());
    }
    @Test
    void testDeleteMedicalRecord() throws Exception {
        when(service.deleteMedicalRecord(eq("John"), eq("Boyd"))).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(status().isNoContent()); // <-- 204 attendu
    }

}
