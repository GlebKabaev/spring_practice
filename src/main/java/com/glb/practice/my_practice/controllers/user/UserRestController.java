package com.glb.practice.my_practice.controllers.user;
import java.util.List;


import org.springframework.web.bind.annotation.*;


import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.srevice.user.UserService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;
    
    @GetMapping({"/",""})
    public List<User> getUsers() {
        return userService.getUsers("id");
    }
    @PostMapping({"/save_user","/save_user/"})
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }
    @GetMapping({"/{id}","/{id}/"})
    public User findByIDUser(@PathVariable int id){
        return userService.findByIDUser(id);
    }
    @PutMapping({"/update_user", "/update_user/"})
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @DeleteMapping({"delete_user/{id}","delete_user/{id}/"})
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }
    
}
