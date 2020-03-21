package com.molistore.application.service.login;

import com.molistore.application.dto.LoginRqDto;
import com.molistore.application.util.authentication.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private JwtUtil jwtUtil;
    private AuthenticationProvider authenticationProvider;

    @Override
    public ResponseEntity<String> login(LoginRqDto loginRqDto) {
        Authentication authenticate;
        try {
            authenticate = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRqDto.getEmail(), loginRqDto.getPassword())
            );
        } catch (BadCredentialsException ex) {
            log.error("Incorrect login or password. {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

        UserDetails user = (UserDetails) authenticate.getPrincipal();
        final String jwt = jwtUtil.generateToken(user);

        log.info("user successfully loggin {}", user.getUsername());
        return ResponseEntity.ok(jwt);
    }

}
