package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.model.Person;
import SafetyNet.Alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService service;

    @Test
    void testAddPerson() throws Exception {
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Boyd");

        when(service.addPerson(any())).thenReturn(p);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Boyd\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testUpdatePerson() throws Exception {
        Person updated = new Person();
        updated.setAddress("New Street");

        when(service.updatePerson(eq("John"), eq("Boyd"), any())).thenReturn(updated);

        mockMvc.perform(put("/person?firstName=John&lastName=Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"New Street\"}"))
                .andExpect(status().isOk());
    }
    @Test
    void testDeletePerson() throws Exception {
        when(service.deletePerson(eq("John"), eq("Boyd"))).thenReturn(true);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(status().isNoContent());
    }

}
