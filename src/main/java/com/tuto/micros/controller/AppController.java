package com.tuto.micros.controller;

import com.tuto.micros.entity.User;
import com.tuto.micros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AppController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    private String rootPage() {

        return "Welcome";
    }

    @GetMapping("/users/search/name/{name}")
    private List<User> searchUsersByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @GetMapping("/users/search/age/{age}")
    private List<User> searchUsersByAge(@PathVariable int age) {
        return userRepository.findByAge(age);
    }

    @GetMapping("/users/search/nameAge/{name}/{age}")
    private List<User> searchUsersByNameAge(@PathVariable String name, @PathVariable int age) {
        List<User> usersFoundByName = userRepository.findByName(name);
        List<User> usersFoundByAge = userRepository.findByAge(age);
        List<User> intersection = usersFoundByName.stream()
                .filter(usersFoundByAge::contains)
                .collect(Collectors.toList());
        return intersection;
    }

    @GetMapping("/users/all")
    private Iterable<User> getAllUsersInformation() {
        return userRepository.findAll();

    }

    @PostMapping("/users/add")
    private User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("users/update/{id}")
    private ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> existedUser = userRepository.findById(id);
        if (existedUser.isPresent()) {
            user.setId(id);
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
