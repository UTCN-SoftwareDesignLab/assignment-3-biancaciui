package com.lab4.demo.patient;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.patient.model.dto.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.PATIENTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientControllerTest extends BaseControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        patientController = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void findAll() throws Exception{
        List<PatientDTO> patientDTOs = TestCreationFactory.listOf(PatientDTO.class);
        when(patientService.allPatients()).thenReturn(patientDTOs);

        ResultActions result = mockMvc.perform(get(PATIENTS));

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTOs));
    }

    @Test
    void getPatient() throws Exception{
        PatientDTO patientDTO = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        when(patientService.findDTOById(patientDTO.getId())).thenReturn(patientDTO);

        ResultActions result = performGetWithPathVariable(PATIENTS+ENTITY,patientDTO.getId() );
                mockMvc.perform(get(PATIENTS));

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTO));
    }

    @Test
    void delete() throws Exception{
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);

        ResultActions result = performDeleteWIthPathVariable(PATIENTS+ENTITY,patient.getId().toString());

        result.andExpect(status().isOk());
    }
}
