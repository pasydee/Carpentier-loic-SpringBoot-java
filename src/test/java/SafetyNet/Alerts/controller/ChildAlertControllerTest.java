package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.ChildAlertResponse;
import SafetyNet.Alerts.service.ChildAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChildAlertController.class)
class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChildAlertService service;

    @Test
    void testChildAlert() throws Exception {
        ChildAlertResponse response = new ChildAlertResponse();
        response.setChildren(List.of());
        response.setOtherMembers(List.of());

        when(service.getChildrenAtAddress("1509 Culver St")).thenReturn(response);

        mockMvc.perform(get("/childAlert")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children").isArray());
    }
}
