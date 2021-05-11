package com.lab4.demo.consultation;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.UrlMapping.CONSULTATIONS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultationControllerTest extends BaseControllerTest {

    @Mock
    private ConsultationService consultationService;

    @InjectMocks
    private ConsultationController consultationController;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        consultationController = new ConsultationController(consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(consultationController).build();
    }

    @Test
    void findAll() throws Exception {
        List<ConsultationDTO> consultations = TestCreationFactory.listOf(ConsultationDTO.class);
        when(consultationService.findAll()).thenReturn(consultations);

        ResultActions response = mockMvc.perform(get(CONSULTATIONS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultations));
    }

}
