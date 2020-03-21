package com.molistore.application.service.login;

import com.molistore.application.dto.LoginRqDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<String> login(LoginRqDto loginRqDto);
}
