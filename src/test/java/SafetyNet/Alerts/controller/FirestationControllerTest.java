package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.Firestation;
import SafetyNet.Alerts.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FirestationController.class)
class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FirestationService service;

    @Test
    void testAddFirestation() throws Exception {
        Firestation f = new Firestation();
        f.setAddress("1509 Culver St");
        f.setStation(3);

        when(service.addFirestation(any())).thenReturn(f);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "address": "1509 Culver St",
                      "station": 3
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value(3));
    }

    @Test
    void testUpdateFirestation() throws Exception {
        Firestation updated = new Firestation();
        updated.setStation(5);

        when(service.updateFirestation(eq("1509 Culver St"), eq(3), any())).thenReturn(updated);

        mockMvc.perform(put("/firestation?address=1509 Culver St&station=3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "station": 5
                    }
                    """))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteFirestation() throws Exception {
        when(service.deleteFirestation(eq("1509 Culver St"), eq(null))).thenReturn(true);

        mockMvc.perform(delete("/firestation?address=1509 Culver St"))
                .andExpect(status().isOk());
    }
}
