package com.lab4.demo.patient.model;

import com.lab4.demo.consultation.model.Consultation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Long identityCardNr;

    @Column(nullable = false, length = 13)
    private String numericalCode;

    @Column
    private Date dateOfBirth;

    @Column(nullable = false, length = 255)
    private String address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Consultation> consultations;
}

