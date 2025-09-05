package com.mailong245.hexagonalarchitecture.features.user.web;

import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.features.user.app.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @GetMapping("/v1/user")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/v1/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/v1/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/v1/user/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
