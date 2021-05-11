package com.lab4.demo.patient;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientService.deleteAll();
    }

    @Test
    void findAll(){
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        patientRepository.saveAll(patients);

        List<PatientDTO> all = patientService.allPatients();

        Assertions.assertEquals(patients.size(), all.size());
    }

    @Test
    void create(){
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);

        patient.setId(null);
        Assertions.assertNotNull(patientService.create(patient).getId());
    }

    @Test
    void delete(){
        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);

        patient.setId(null);
        patient = patientRepository.save(patient);

        patientService.delete(patient.getId());
    }

}
