package com.lab4.demo.userHospital;

import com.lab4.demo.userHospital.model.Role;
import com.lab4.demo.userHospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRolesContaining(Role role);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
