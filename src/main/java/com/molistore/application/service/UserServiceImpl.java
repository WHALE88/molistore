package com.molistore.application.service;

import com.molistore.application.dao.profile.AuthorityRepository;
import com.molistore.application.dao.profile.UserRepository;
import com.molistore.application.dto.UserRegistrationDto;
import com.molistore.application.entities.profile.UserEntity;
import com.molistore.application.enums.Role;
import com.molistore.application.exception.EmailExistsException;
import com.molistore.application.service.login.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity registerNewUserAccount(UserRegistrationDto userRegistrationDto) throws EmailExistsException {
        return registerNewUserAccount(userRegistrationDto, Role.ROLE_CUSTOMER);
    }

    @Override
    @Transactional
    public UserEntity registerNewUserAccount(UserRegistrationDto userRegistrationDto, Role role) throws EmailExistsException {
        if (emailExists(userRegistrationDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address:" + userRegistrationDto.getEmail());
        }
        UserEntity user = new UserEntity();
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.addAuthorities(authorityRepository.findByRole(role));
//        user.setPlainProfile(new PlainProfile());
        user.setActive(true);

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public void createResetPasswordToken(UserEntity user, String token) {

    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(String userId, String password) {

    }
}
