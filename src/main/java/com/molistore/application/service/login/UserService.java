package com.molistore.application.service.login;

import com.molistore.application.dto.UserRegistrationDto;
import com.molistore.application.entities.UserEntity;
import com.molistore.application.enums.Role;
import com.molistore.application.exception.EmailExistsException;

public interface UserService {

    UserEntity save(UserEntity user);

    UserEntity registerNewUserAccount(UserRegistrationDto userRegistrationDto) throws EmailExistsException;

    UserEntity registerNewUserAccount(UserRegistrationDto userRegistrationDto, Role role) throws EmailExistsException;

    void createResetPasswordToken(UserEntity user, String token);

    UserEntity findByEmail(String email);

    void updatePassword(String userId, String password);
}
