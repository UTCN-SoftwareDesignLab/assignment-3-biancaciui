package com.lab4.demo.userHospital;

import com.lab4.demo.userHospital.dto.UserDTO;
import com.lab4.demo.userHospital.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @GetMapping(ENTITY)
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserDTO(id);
    }

    @PostMapping
    public void create(@RequestBody UserDTO user){
        userService.create(user);
    }

    @PutMapping(ENTITY)
    public void edit(@PathVariable Long id, @RequestBody UserDTO user){
        userService.edit(id, user);
    }

    @PatchMapping(ENTITY)
    public UserDTO changePassword(@PathVariable Long id, @RequestBody String password) {
        return userService.changePassword(id, password);
    }

    @DeleteMapping(ENTITY) //delete by id
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }
    @DeleteMapping()
    public void deleteAll(){
        userService.deleteAll();
    }

    @GetMapping("/doctors")
    public List<UserDTO> findDoctors(){return userService.findAllByRole("DOCTOR");}

}