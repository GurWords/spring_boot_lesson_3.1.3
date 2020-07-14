package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import web.model.User;
import web.service.userservice.UserService;
import web.service.userservice.UserServiceImpl;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping(value = "user")
    public ResponseEntity<User> getUser(Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(service.loadUserByUsername(user.getName()));
    }
}
