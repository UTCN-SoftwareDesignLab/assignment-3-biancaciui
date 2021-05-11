package com.lab4.demo.userHospital;

import com.lab4.demo.userHospital.UserRepository;
import com.lab4.demo.userHospital.dto.UserDTO;
import com.lab4.demo.userHospital.dto.UserListDTO;
import com.lab4.demo.userHospital.dto.UserMinimalDTO;
import com.lab4.demo.userHospital.mapper.UserMapper;
import com.lab4.demo.userHospital.model.ERole;
import com.lab4.demo.userHospital.model.Role;
import com.lab4.demo.userHospital.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private Role findRolesFromStrings(String role){
        return roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(()-> new EntityNotFoundException("Invalid role"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDTO> findAllByRole(String role){
        Optional<Role> r = roleRepository.findByName(Enum.valueOf(ERole.class, role));
        //if(r.isPresent())
            return userRepository.findAllByRolesContaining(r.orElseThrow(()->new EntityNotFoundException("Role "+role+" not found!"))).
                    stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public User findByName(String name){
        return userRepository.findByUsername(name).orElseThrow(()->new RuntimeException("User with name = "+name+" not found"));
    }

    public UserDTO getUserDTO(Long id) {
        return userMapper.toDto(findById(id));
    }

    public UserDTO create(UserDTO userDTO) {
        User actUser = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();             // userMapper.fromDto(userDTO);

        Set<Role> roles = new HashSet<>();
        //getting the roles for this new user, else insert the default one: SECRETARY
        if(userDTO.getRoles()==null){
            Role role = roleRepository.findByName(ERole.SECRETARY).orElseThrow(() -> new RuntimeException("Role not found!"));
            roles.add(role);
        }
        else {
            Set<String> r = userDTO.getRoles();
            Role role;
            for(String s: r){
                role = roleRepository.findByName(ERole.valueOf(s)).orElseThrow(()->new RuntimeException("User doesn't have an assigned role!"));
                roles.add(role);
            }
        }
        actUser.setRoles(roles);

        if (userRepository.existsByUsername(actUser.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        if (userRepository.existsByEmail(actUser.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");

        return userMapper.toDto(userRepository.save(actUser));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }
    public UserDTO edit(Long id, UserDTO userDTO){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Set<String> roles = userDTO.getRoles();
        Set<Role> r = new HashSet<>();
        for(String s: roles){
            r.add(findRolesFromStrings(s));
        }
        user.setRoles(r);

        return userMapper.toDto(userRepository.save(user));
    }
    public UserDTO changePassword(Long id, String newPassword) {
        User user = findById(id);
        user.setPassword(newPassword);
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id){
       userRepository.deleteById(id);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

}
