package org.example.hsf302_group1.controller;

import jakarta.servlet.http.HttpSession;
import org.example.hsf302_group1.entity.UserAccount;
import org.example.hsf302_group1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        var userOpt = userService.login(username, password);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "username or password is invalid!");
            return "login";
        }

        UserAccount user = userOpt.get();

        // ĐỂ INTERCEPTOR XỬ LÝ GUEST
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", Integer.valueOf(user.getRole()));
        session.setAttribute("user", user);

        return "redirect:/products";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}