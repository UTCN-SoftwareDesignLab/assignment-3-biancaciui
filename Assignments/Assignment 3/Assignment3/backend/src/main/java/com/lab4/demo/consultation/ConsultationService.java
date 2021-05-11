package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.consultation.model.dto.ConsultationJSONformatDTO;
import com.lab4.demo.patient.PatientRepository;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.userHospital.UserRepository;
import com.lab4.demo.userHospital.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationMapper consultationMapper;

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public Consultation findById(Long id){
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existing consultation with id = " + id));
    }

    public List<ConsultationDTO> findAll(){
        return consultationRepository.findAll()
                .stream().map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ConsultationJSONformatDTO> findAllJSONformat(){
        List<ConsultationJSONformatDTO> consultationJSONformatDTOList = new ArrayList<>();

        List<ConsultationDTO> consultationDTOList = findAll();
        for(ConsultationDTO c: consultationDTOList)
            consultationJSONformatDTOList.add(convertToJsonFormat(c));

        return consultationJSONformatDTOList;
    }

    public List<ConsultationDTO> getPatientConsultations(Long id){
        return consultationRepository.findAllConsultationForPatient(id)
                .stream().map(consultationMapper::toDto).collect(Collectors.toList());
    }
    public List<ConsultationDTO> getDoctorConsultations(Long id){
        return consultationRepository.findAllConsultationForDoctor(id)
                .stream().map(consultationMapper::toDto).collect(Collectors.toList());
    }

    public ConsultationDTO create(ConsultationJSONformatDTO consultationJSONformatDTO) throws Exception {
        //I know it's messy
        consultationJSONformatDTO.setDoctorId(userRepository.findByUsername(consultationJSONformatDTO.getDoctorName()).orElseThrow(() -> new EntityNotFoundException("Doctor with name "+consultationJSONformatDTO.getDoctorName()+" not found!")).getId());
        consultationJSONformatDTO.setPatientId(patientRepository.findByName(consultationJSONformatDTO.getPatientName()).orElseThrow(() -> new EntityNotFoundException("Patient with name "+consultationJSONformatDTO.getPatientName()+" not found!")).getId());

        List<ConsultationJSONformatDTO> all = findAllJSONformat();

        List<ConsultationJSONformatDTO> areThereConflicts = all.stream().
                filter(dto -> dto.getDate().equals(consultationJSONformatDTO.getDate())
                        && dto.getDoctorId().equals(consultationJSONformatDTO.getDoctorId())).collect(Collectors.toList());

        if(areThereConflicts.isEmpty()){
            Consultation consultation = consultationMapper.fromDto(convertFromJsonFormat(consultationJSONformatDTO));
            //we need to set the date for this consultation manually because the mapper doesn't resolve this
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
            consultation.setDate(consultation.getDate_time().format(formatter));

            return consultationMapper.toDto(consultationRepository.save(consultation));
        }
        else
            throw new Exception("The selected time is unavailable, conflict arises between this and another consultation.");
    }

    public ConsultationJSONformatDTO convertToJsonFormat(ConsultationDTO consultationDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

        return ConsultationJSONformatDTO.builder()
                .id(consultationDTO.getId())
                .doctorName(consultationDTO.getDoctor().getUsername())
                .patientName(consultationDTO.getPatient().getName())
                .doctorId(consultationDTO.getDoctor().getId())
                .patientId(consultationDTO.getPatient().getId())
                .date(consultationDTO.getDate_time().format(formatter))
                .details(consultationDTO.getDetails())
                .build();
    }

    public ConsultationDTO convertFromJsonFormat(ConsultationJSONformatDTO consultationJSONformatDTO){
        User doc = userRepository.findByUsername(consultationJSONformatDTO.getDoctorName()).orElseThrow(() -> new EntityNotFoundException("Doctor with name "+consultationJSONformatDTO.getDoctorName()+" not found!"));
        Patient patient = patientRepository.findByName(consultationJSONformatDTO.getPatientName()).orElseThrow(() -> new EntityNotFoundException("Patient with name "+consultationJSONformatDTO.getPatientName()+" not found!"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

        return ConsultationDTO.builder()
                .id(consultationJSONformatDTO.getId())
                .doctor(doc)
                .patient(patient)
                .date_time(LocalDateTime.parse(consultationJSONformatDTO.getDate(), formatter))
                .details(consultationJSONformatDTO.getDetails())
                .build();
    }

    public ConsultationDTO edit(Long id, ConsultationJSONformatDTO consultationJSONformatDTO) throws Exception {
        ConsultationDTO consultationDTO = convertFromJsonFormat(consultationJSONformatDTO);

        List<ConsultationJSONformatDTO> all = findAllJSONformat();

        List<ConsultationJSONformatDTO> areThereConflicts = all.stream().
                filter(dto -> dto.getDate().equals(consultationJSONformatDTO.getDate())
                        && dto.getDoctorId().equals(consultationJSONformatDTO.getDoctorId())).collect(Collectors.toList());

        if(areThereConflicts.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

            Consultation consultation = findById(id);
            consultation.setDate(consultationJSONformatDTO.getDate());
            consultation.setDoctor(userRepository.findById(consultationJSONformatDTO.getDoctorId()).orElseThrow(() -> new EntityNotFoundException("Doctor not found")));
            consultation.setPatient(patientRepository.findById(consultationJSONformatDTO.getPatientId()).orElseThrow(() -> new EntityNotFoundException("Patient not found")));
            consultation.setDate_time(LocalDateTime.parse(consultationJSONformatDTO.getDate(), formatter));
            consultation.setDate(consultation.getDate_time().format(formatter));
            consultation.setDetails(consultationJSONformatDTO.getDetails());


            return consultationMapper.toDto(consultationRepository.save(consultation));
        }
        else throw new Exception("The selected time is unavailable, conflict arises between this and another consultation.");
    }

    public void delete(Long id) {
        consultationRepository.deleteById(id);
    }

    public void deleteAll() {
        consultationRepository.deleteAll();
    }

    public void editDescription(Long id, String details) {
        Consultation consultation = consultationRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Consultation not found"));
        consultation.setDetails(details);
        consultationRepository.save(consultation);
    }
}
