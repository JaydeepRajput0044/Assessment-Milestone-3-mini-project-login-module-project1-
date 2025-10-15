package com.app.controller;

import com.app.dao.UserDAOImpl;
import com.app.model.User;
import javax.servlet.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    UserDAOImpl dao = new UserDAOImpl();

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Username and password required!");
            return "login";
        }

        User user = dao.validateUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Invalid credentials!");
            return "login";
        }
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
