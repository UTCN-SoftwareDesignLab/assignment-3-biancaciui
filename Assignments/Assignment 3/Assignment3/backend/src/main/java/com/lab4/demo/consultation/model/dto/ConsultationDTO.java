package com.lab4.demo.consultation.model.dto;

import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.userHospital.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {
    private Long id;
    private User doctor;
    private Patient patient;
    private LocalDateTime date_time;
    private String details;

}
