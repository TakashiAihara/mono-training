package com.handson.interfaces;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.handson.spring_boot_handson.domain.repositories.UserRepository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
public class UsersController {
    private final UserRepository userRepository;

    @CrossOrigin(origins = "http://ubuntu:8080")
    @GetMapping("/users")
    public  List<User> getUsers(@RequestParam(required = false) String q) {
        if (isNumber(q)) {
            return userRepository.findByNameContainingIgnoreCaseOrId(q,Integer.parseInt(q));
        } else {
            return userRepository.findByNameContainingIgnoreCase(q);
        }
    }

    @CrossOrigin(origins = "http://ubuntu:8080")
    @GetMapping("/users/{id}")
    public  User getUser(@PathVariable(name = "id") int id) {
            return userRepository.findById(id).get();
    }

    private boolean isNumber(String num) {
        String regex = "^[0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(num);
        return m.find();
    }
}
