package com.lab4.demo.consultation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.userHospital.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_Id")
    @NotNull
    @JsonIgnore
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_Id")
    @NotNull
    @JsonIgnore
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime date_time;

    private String date;

    @Column(length = 1023)
    private String details;


}
