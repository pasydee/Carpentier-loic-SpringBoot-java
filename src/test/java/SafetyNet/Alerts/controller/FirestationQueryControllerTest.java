package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FirestationPersonsResponse;
import SafetyNet.Alerts.service.FirestationQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FirestationQueryController.class)
class FirestationQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FirestationQueryService service;

    @Test
    void testGetPersonsByStation() throws Exception {

        FirestationPersonsResponse response = new FirestationPersonsResponse();
        response.setAdultCount(1);
        response.setChildCount(0);
        response.setPersons(List.of());

        when(service.getPersonsByStation(1)).thenReturn(response);

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value(1))
                .andExpect(jsonPath("$.childCount").value(0));
    }
}
