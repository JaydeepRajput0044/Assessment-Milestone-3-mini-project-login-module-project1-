package com.app.controller;

import com.app.model.User;
import com.app.dao.UserDAOImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {
    UserDAOImpl dao = new UserDAOImpl();

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Validation
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() ||
            user.getEmpid().isEmpty() || user.getEmail().isEmpty()) {
            model.addAttribute("error", "All fields are mandatory!");
            return "register";
        }
        if (!user.getEmpid().matches("^[A-Za-z]{1}\\d{4}$")) {
            model.addAttribute("error", "Employee ID format invalid (e.g., A1234).");
            return "register";
        }
        if (user.getPassword().length() != 6) {
            model.addAttribute("error", "Password must be 6 characters long.");
            return "register";
        }

        if (dao.registerUser(user)) {
            model.addAttribute("msg", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Registration failed. Try again.");
            return "register";
        }
    }
}
