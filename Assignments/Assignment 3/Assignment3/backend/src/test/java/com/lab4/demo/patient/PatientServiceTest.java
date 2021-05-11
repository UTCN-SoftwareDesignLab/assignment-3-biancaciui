package com.lab4.demo.patient;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDTO> all = patientService.allPatients();
        Assertions.assertEquals(all.size(), patients.size());
    }

    @Test
    void create() {
        PatientDTO patientDTO = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);

        Patient patient = Patient.builder()
                .id(patientDTO.getId())
                .name(patientDTO.getName())
                .identityCardNr(patientDTO.getIdentityCardNr())
                .numericalCode(patientDTO.getNumericalCode())
                .address(patientDTO.getAddress())
                .dateOfBirth(patientDTO.getDateOfBirth())
                .build();

        when(patientMapper.fromDto(patientDTO)).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(patientDTO);
        when(patientRepository.save(patient)).thenReturn(patient);

        Assertions.assertEquals(patientService.create(patientDTO), patientDTO);
    }

    @Test
    void deleteAll() {
        patientService.deleteAll();
        List<PatientDTO> all = patientService.allPatients();
        Assertions.assertEquals(all.size(), 0);
    }


}
