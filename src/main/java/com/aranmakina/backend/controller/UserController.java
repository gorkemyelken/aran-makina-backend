package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.featurename.FeatureNameViewDTO;
import com.aranmakina.backend.dto.user.UserViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
