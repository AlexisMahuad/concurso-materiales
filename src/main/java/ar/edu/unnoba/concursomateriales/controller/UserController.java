package ar.edu.unnoba.concursomateriales.controller;

import ar.edu.unnoba.concursomateriales.model.User;
import ar.edu.unnoba.concursomateriales.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Principal;

@Controller
@RequestMapping(path = "/")
public class UserController implements WebMvcConfigurer {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Principal principal) {
        // Check if user is already logged in
        if (principal != null) return "redirect:/";
        return "user/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, Principal principal) {
        // Check if user is already logged in
        if (principal != null) return "redirect:/";
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user, Errors errors) {
        // Check if user already exists
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            return "redirect:/register?exist";
        }
        // Data validation
        if (errors.hasErrors()) return "user/register";

        userService.create(user);
        return "redirect:/login";
    }

}