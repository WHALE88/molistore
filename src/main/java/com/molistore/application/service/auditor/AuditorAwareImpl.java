package com.molistore.application.service.auditor;


import com.molistore.application.dao.profile.UserRepository;
import com.molistore.application.entities.profile.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@Service("auditor")
public class AuditorAwareImpl implements AuditorAware<UserEntity> {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @NotNull
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<UserEntity> getCurrentAuditor() {
        UserEntity currentUser = getCurrentUser();
        if (currentUser == null) {
            return Optional.empty();
        }
        return Optional.of(currentUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username;
        Object details = authentication.getDetails();
        if (details instanceof UserDetails) {
            username = ((UserEntity) details).getUsername();
        } else {
            username = authentication.getName();
        }
        return userRepository.findByEmail(username);
    }
}
