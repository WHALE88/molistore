package com.molistore.application.rest;

import com.molistore.application.dto.LoginRqDto;
import com.molistore.application.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {

    private final LoginService loginService;


    @GetMapping("/hello")
    public String hello() {
        return "HELLO WORLD";
    }


    @PostMapping("/")
    public ResponseEntity<String> createLogin(@Valid @RequestBody LoginRqDto loginRqDto) {
        return loginService.login(loginRqDto);
    }

}