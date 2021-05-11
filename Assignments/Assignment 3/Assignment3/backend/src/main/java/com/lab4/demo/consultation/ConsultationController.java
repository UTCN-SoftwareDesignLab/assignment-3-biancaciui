package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.consultation.model.dto.ConsultationJSONformatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lab4.demo.UrlMapping.CONSULTATIONS;
import static com.lab4.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public List<ConsultationJSONformatDTO> allConsultations() {
        return consultationService.findAllJSONformat();
    }

    @GetMapping("/doc"+ENTITY)
    public List<ConsultationDTO> getByDoc(@PathVariable Long id) {
        return consultationService.getDoctorConsultations(id);
    }

    @GetMapping("/patient"+ENTITY)
    public List<ConsultationDTO> getByPatient(@PathVariable Long id) {
        return consultationService.getPatientConsultations(id);
    }

    @GetMapping(ENTITY)
    public Consultation getConsultation(@PathVariable Long id) {
        return consultationService.findById(id);
    }

    @PostMapping
    public void create(@Valid @RequestBody ConsultationJSONformatDTO consultation) throws Exception {
        consultationService.create(consultation);
    }

    @PatchMapping(ENTITY)
    public void edit(@PathVariable Long id, @Valid @RequestBody ConsultationJSONformatDTO consultation) throws Exception {
        consultationService.edit(id, consultation);
    }

    @PatchMapping("/description"+ENTITY)
    public void editDescription(@PathVariable Long id,@Valid @RequestBody String details){
        consultationService.editDescription(id, details);
    }
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        consultationService.delete(id);
    }

    @DeleteMapping()
    public void deleteAll(){
        consultationService.deleteAll();
    }

}
