package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.user.UserCreateDTO;
import com.aranmakina.backend.dto.user.UserViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.Result;
import com.aranmakina.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<DataResult<List<UserViewDTO>>> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DataResult<UserViewDTO>> addUser(@RequestBody UserCreateDTO userCreateDTO) {
        return new ResponseEntity<>(userService.addUser(userCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Result> deleteUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
}
