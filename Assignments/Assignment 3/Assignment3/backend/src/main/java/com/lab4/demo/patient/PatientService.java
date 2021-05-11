package com.lab4.demo.patient;

import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    private Patient findById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id = "+id+" not found!"));
    }
    public PatientDTO findDTOById(Long id){
        return patientMapper.toDto(findById(id));
    }

    public List<PatientDTO> allPatients(){
        return patientRepository.findAll()
                .stream().map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO create(PatientDTO patientDTO) {
        return patientMapper.toDto(patientRepository.save(patientMapper.fromDto(patientDTO)));
    }

    public PatientDTO edit(Long id, PatientDTO patientDTO){
        Patient patient = findById(id);

        patient.setName(patientDTO.getName());
        patient.setNumericalCode(patientDTO.getNumericalCode());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setIdentityCardNr(patientDTO.getIdentityCardNr());
        patient.setAddress(patientDTO.getAddress());

        return patientMapper.toDto(patientRepository.save(patient));
    }

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    public void deleteAll(){
        patientRepository.deleteAll();
    }

    public long numberOfEntries(){
        return patientRepository.count();
    }

}
