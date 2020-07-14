package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web.dao.userdao.UserDao;
import web.model.User;
import web.service.userservice.UserService;
import java.util.List;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private UserService service;

    @Autowired
    private UserDao dao;

    @PostMapping(value = "admin/insert")
    public ResponseEntity<User> insertRestController(@RequestBody User userInsert) {
        userInsert.userUpdateRole(userInsert.getRole());
        service.insertUser(userInsert);
        return ResponseEntity.ok(userInsert);
    }

    @DeleteMapping(value = "admin/delete")
    ResponseEntity<User> deleteUsers(@RequestParam int idDelete) {
        service.deleteUser(idDelete);
        User user = new User.Builder().withId(idDelete).build();
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "admin/update")
    ResponseEntity<User> updateUserPost(@RequestBody User userUpdate) {
//        if (role != null) {
//            String[] roleArray = role.split(",");
//            userUpdate.userUpdateRole(roleArray);
//        }
        userUpdate.userUpdateRole(userUpdate.getRole());
        service.updateUser(userUpdate);
        return ResponseEntity.ok(userUpdate);
    }

    @GetMapping(value = "admin/list")
    public ResponseEntity<List<User>> getUserList(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping(value = "admin/userCurrent")
    public ResponseEntity<User> getUserCurrent(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.loadUserByUsername(user.getName()));
    }

}
