package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.consultation.model.dto.ConsultationJSONformatDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    Consultation fromDto(ConsultationDTO consultationDTO);
    Consultation fromDtoJ(ConsultationJSONformatDTO consultationDTO);

    ConsultationDTO toDto(Consultation consultation);
    ConsultationJSONformatDTO toDtoJ(Consultation consultation);

}
