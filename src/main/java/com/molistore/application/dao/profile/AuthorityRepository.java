package com.molistore.application.dao.profile;

import com.molistore.application.entities.profile.Authority;
import com.molistore.application.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findByRole(Role role);
}
