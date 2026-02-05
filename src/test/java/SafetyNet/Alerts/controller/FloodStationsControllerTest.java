package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FloodStationsResponse;
import SafetyNet.Alerts.service.FloodStationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FloodStationsController.class)
class FloodStationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FloodStationsService service;

    @Test
    void testFloodStations() throws Exception {
        FloodStationsResponse response = new FloodStationsResponse();
        response.setHouseholds(List.of());

        when(service.getFloodInfo(List.of(1, 2))).thenReturn(response);

        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.households").isArray());
    }
}
