package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FireAddressResponse;
import SafetyNet.Alerts.service.FireAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireAddressController.class)
class FireAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireAddressService service;

    @Test
    void testFireAddress() throws Exception {
        FireAddressResponse response = new FireAddressResponse();
        response.setStationNumber(3);
        response.setResidents(List.of());

        when(service.getFireInfo("1509 Culver St")).thenReturn(response);

        mockMvc.perform(get("/fire")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumber").value(3));
    }
}
