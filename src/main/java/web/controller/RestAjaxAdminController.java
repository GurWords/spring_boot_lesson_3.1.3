package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.userservice.UserService;

@RestController
@RequestMapping("/")
public class RestAjaxAdminController {

    @Autowired
    private UserService service;

    @PostMapping(value = "insert")
    public User insertRestController(@RequestParam String name, String password, int age, String roleInsert) {
        String[] roleArray = roleInsert.split(",");
        User user1 = new User.Builder()
                .withName(name)
                .withPassword(password)
                .withAge(age)
                .withRole(roleArray)
                .build();
        service.insertUser(user1);
        User ajaxUserTest = service.loadUserByUsername(user1.getName());
        user1.setId(ajaxUserTest.getId());
        return user1;
    }

    @PostMapping(value = "delete")
    User deleteUsers(@RequestParam int idDelete) {
        service.deleteUser(idDelete);
        User user = new User.Builder().withId(idDelete).build();
        return user;
    }

    @PostMapping(value = "update")
    User updateUserPost(@RequestParam String id, String name, String password, String role, int age) {
        User userUpdate = service.getUserById(Integer.parseInt(id));
        userUpdate.setName(name);
        userUpdate.setAge(age);
        userUpdate.setPassword(password);
        if (role != null) {
            String[] roleArray = role.split(",");
            userUpdate.userUpdateRole(roleArray);
        }
        service.updateUser(userUpdate);
        User userNewUpdate = new User.Builder()
                .withId(userUpdate.getId())
                .withName(userUpdate.getName())
                .withAge(userUpdate.getAge())
                .withPassword(userUpdate.getPassword())
                .build();
        return userNewUpdate;
    }
}
