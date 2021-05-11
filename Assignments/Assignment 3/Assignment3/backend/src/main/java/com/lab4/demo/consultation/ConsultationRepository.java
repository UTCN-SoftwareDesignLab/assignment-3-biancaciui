package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Query("SELECT consultation from Consultation consultation where consultation.doctor.id = ?1")
    List<Consultation> findAllConsultationForDoctor(Long id);

    @Query("SELECT consultation from Consultation consultation where consultation.patient.id = ?1")
    List<Consultation> findAllConsultationForPatient(Long id);
}
