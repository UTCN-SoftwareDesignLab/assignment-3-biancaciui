package com.lab4.demo;

import com.lab4.demo.consultation.ConsultationRepository;
import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.patient.PatientRepository;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.userHospital.RoleRepository;
import com.lab4.demo.userHospital.UserRepository;
import com.lab4.demo.userHospital.model.ERole;
import com.lab4.demo.userHospital.model.Role;
import com.lab4.demo.userHospital.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ConsultationRepository consultationRepository;

    private final AuthService authService;

    private final PatientRepository patientRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {

            consultationRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            patientRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("DOCTOR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("bianca@email.com")
                    .username("bianca")
                    .password("WooHoo1!")
                    .roles(Set.of("SECRETARY"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("biancaA@email.com")
                    .username("biancaA")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());

//            Set<Role> roles =new HashSet<>();
//            roles.add(roleRepository.findByName(ERole.DOCTOR)
//                    .orElseThrow(() -> new RuntimeException("Cannot find DOCTOR role")));
//            User doc = User.builder().email("alex@email.com")
//                    .username("alex")
//                    .password("WooHoo1!")
//                    .roles(roles)
//                    .build();
//            userRepository.save(doc);

            authService.register(SignupRequest.builder()
                    .email("biancaDoc@email.com")
                    .username("biancaDoc")
                    .password("WooHoo1!")
                    .roles(Set.of("DOCTOR"))
                    .build());


            User doc = userRepository.findByUsername("alex").orElseThrow(()->new EntityNotFoundException("Donctor not found!"));

            Patient andrei = Patient.builder()
                    .name("Andrei Prusu")
                    .numericalCode("1234567890")
                    .identityCardNr(1180398324252L)
                    .address("Sobarilor 48, Cluj-Napoca")
                    .dateOfBirth(Date.valueOf("1998-03-18"))
                    .build();
            Patient cristiana = Patient.builder()
                    .name("Lupu Cristiana")
                    .numericalCode("1234567890")
                    .identityCardNr(1180398235754L)
                    .address("Sobarilor 50, Cluj-Napoca")
                    .dateOfBirth(Date.valueOf("1999-04-02"))
                    .build();
            Patient monica = Patient.builder()
                    .name("Monica Pasca")
                    .numericalCode("1234567890")
                    .identityCardNr(1090399324252L)
                    .address("Sobarilor 89, Cluj-Napoca")
                    .dateOfBirth(Date.valueOf("1999-09-03"))
                    .build();
            patientRepository.save(andrei);
            patientRepository.save(cristiana);
            patientRepository.save(monica);

            Patient p1 = patientRepository.findByName("Andrei Prusu").orElseThrow(()->new EntityNotFoundException("Patient not found"));
            Patient p2 = patientRepository.findByName("Lupu Cristiana").orElseThrow(()->new EntityNotFoundException("Patient not found"));
            Patient p3 = patientRepository.findByName("Monica Pasca").orElseThrow(()->new EntityNotFoundException("Patient not found"));

            consultationRepository.save(Consultation.builder()
                    .doctor(doc)
                    .patient(p1)
                    .date_time(LocalDateTime.of(2021, Month.JULY, 29, 19, 00, 00))
                    .date("2021-06-29 19")
                    .details("ORL consultation")
                    .build());
            consultationRepository.save(Consultation.builder()
                    .doctor(doc)
                    .patient(p3)
                    .date_time(LocalDateTime.of(2021, Month.JULY, 30, 19, 00, 00))
                    .date("2021-06-30 19")
                    .details("dentist consultation")
                    .build());
            consultationRepository.save(Consultation.builder()
                    .doctor(doc)
                    .patient(p1)
                    .date_time(LocalDateTime.of(2021, Month.MARCH, 29, 12, 00, 00))
                    .date("2021-03-29 12")
                    .details("ORL consultation")
                    .build());

        }
    }
}
