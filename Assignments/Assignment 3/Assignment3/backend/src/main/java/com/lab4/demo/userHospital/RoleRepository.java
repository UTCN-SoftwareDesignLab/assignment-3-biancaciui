package com.lab4.demo.userHospital;

import com.lab4.demo.userHospital.model.ERole;
import com.lab4.demo.userHospital.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<com.lab4.demo.userHospital.model.Role, Long> {
    Optional<Role> findByName(ERole role);
}