package com.molistore.application.service.login;

import com.molistore.application.dto.LoginRqDto;
import com.molistore.application.entities.UserEntity;
import com.molistore.application.enums.ExceptionCode;
import com.molistore.application.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> login(LoginRqDto loginRqDto) {
        UserEntity user = userService.findByEmail(loginRqDto.getEmail());
        checkExistsUser(user);
        checkMatchingPassword(user.getPassword(), loginRqDto.getPassword());

        log.info("user successfully loggin {}", user.getEmail());
        return ResponseEntity.ok("Successfully login");
    }

    private void checkMatchingPassword(String originalPassword, String incomePassword) {
        boolean isMatch = passwordEncoder.matches(incomePassword, originalPassword);
        if (!isMatch) {
            throw new UserException(ExceptionCode.MATCH_PASSWORD_EXCEPTION);
        }
    }

    private void checkExistsUser(UserEntity user) {
        if (Objects.isNull(user)) {
            throw new UserException(ExceptionCode.USER_NOT_FOUND_EXCEPTION);
        }
    }

}
