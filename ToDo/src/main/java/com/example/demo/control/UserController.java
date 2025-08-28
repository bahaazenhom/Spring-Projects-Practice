package com.example.demo.control;

import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public void register(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PutMapping("/{id}")
    public void UpdateUser(@PathVariable Long id,@RequestBody User user){
        user.setId(id);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }


}
