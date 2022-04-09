package com.filip.pai.controllers;

import java.security.Principal;

import com.filip.pai.dao.userDao;
import com.filip.pai.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private userDao dao;
    @GetMapping("/login")
    public String loginPage(Principal principal) {
        //zwrócenie nazwy widoku logowania - login.html
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model m) {
        //dodanie do modelu nowego użytkownika
        m.addAttribute("user", new User());
        //zwrócenie nazwy widoku rejestracji - register.html
        return "register";
    }
    @PostMapping("/register")
    public String registerPagePOST(@ModelAttribute(value = "user") User user) {
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        //przekierowanie do adresu url: /login
        return "redirect:/login";
    }
    @GetMapping("/delete")
    public String deleteAccount(@RequestParam Integer userId){
    dao.deleteById(userId);
    return "redirect:/logout";
    }
    @GetMapping("/profile")
    public String profilePage(Model m, Principal principal) {
        //dodanie do modelu obiektu user - aktualnie zalogowanego użytkownika:
        m.addAttribute("user", dao.findByLogin(principal.getName()));
        //zwrócenie nazwy widoku profilu użytkownika - profile.html
        return "profile";
    }
    @GetMapping("/users")
    public String usersPage(Model m) {

        //dodanie do modelu obiektu user - aktualnie zalogowanego użytkownika:
        m.addAttribute("users", dao.findAll());
        //zwrócenie nazwy widoku profilu użytkownika - profile.html

        //definicja metody, która zwróci do widoku users.html listę użytkowników z bd
        return "users";
    }
    @GetMapping("/edit")
    public String editPage(Model m, Principal principal){
        m.addAttribute("user", new User());
        User userInfo = dao.findByLogin(principal.getName());
        m.addAttribute("userInfo",userInfo);
        return "edit";
    }
    @PostMapping("/edition")
    public String editPagePUT(Principal principal,@ModelAttribute(value = "user") User user){
        User updateUser = dao.findByLogin(principal.getName());
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        updateUser.setLogin(user.getLogin());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(updateUser);
        return "redirect:/logout";
    }
}