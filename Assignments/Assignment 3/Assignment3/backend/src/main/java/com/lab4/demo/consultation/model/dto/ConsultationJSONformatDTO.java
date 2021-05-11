package com.lab4.demo.consultation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationJSONformatDTO {
    private Long id;
    private String doctorName;
    private String patientName;
    private Long doctorId;
    private Long patientId;
    private String date;
    private String details;
}
