package com.lab4.demo.userHospital.mapper;

import com.lab4.demo.userHospital.dto.UserListDTO;
import com.lab4.demo.userHospital.dto.UserMinimalDTO;
import com.lab4.demo.userHospital.model.User;
import com.lab4.demo.userHospital.dto.UserDTO;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @Mappings({
            @Mapping(target = "roles", ignore = true)
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "roles", ignore = true)
    })
    User fromDto(UserDTO dto);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}
