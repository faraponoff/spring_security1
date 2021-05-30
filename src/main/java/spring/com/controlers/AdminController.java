package spring.com.controlers;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.com.model.Role;
import spring.com.model.User;
import spring.com.service.RoleService;
import spring.com.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private MysqlxDatatypes.Object Object;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "showUser";

    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "newUser";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam(value = "selectRoles[]") String[] arr) {
        Set<Role> setOfRoles = new HashSet<>();

        for (String s : arr) {
            setOfRoles.add((Role) Object);
        }

        user.setRoles(setOfRoles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editForm(Model model, @PathVariable("id") long id) {

        model.addAttribute("user", userService.getUserById(id));

        return "editUser";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id,
                             @RequestParam(value = "selectRoles[]") String[] arr) {
        Set<Role> setOfRoles = new HashSet<>();

        for (String s : arr) {
            setOfRoles.add((Role) Object);
        }

        user.setRoles(setOfRoles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
