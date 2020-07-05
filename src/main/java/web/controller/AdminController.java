package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.Role;
import web.model.User;
import web.service.userservice.UserService;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    //private User ajaxUserTest;

    @Autowired
    private UserService service;


    @RequestMapping(method = RequestMethod.GET)
    public String printListUser(Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> roleList = (List<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        model.addAttribute("rolelist",roleList);
        model.addAttribute("userCurrent",user);
        model.addAttribute("userlist", service.getAllUsers());
        //model.addAttribute("ajaxUserResp",ajaxUserTest);
        return "admin";
    }

}
