package com.lab4.demo.patient;

import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import com.lab4.demo.userHospital.UserService;
import com.lab4.demo.userHospital.dto.UserDTO;
import com.lab4.demo.userHospital.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.PATIENTS;

@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> allPatients() {
        return patientService.allPatients();
    }

    @GetMapping(ENTITY)
    public PatientDTO getPatient(@PathVariable Long id) {
        return patientService.findDTOById(id);
    }

    @PostMapping
    public void create(@RequestBody PatientDTO patient){
        patientService.create(patient);
    }

    @PutMapping(ENTITY)
    public void edit(@PathVariable Long id, @RequestBody PatientDTO patientDTO){
        patientService.edit(id, patientDTO);
    }

    @DeleteMapping(ENTITY) //delete by id
    public void deleteById(@PathVariable Long id){
        patientService.delete(id);
    }

    @DeleteMapping()
    public void deleteAll(){
        patientService.deleteAll();
    }

}
