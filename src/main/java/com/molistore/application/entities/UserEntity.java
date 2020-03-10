package com.molistore.application.entities;

import com.molistore.application.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;

import static java.util.Collections.singleton;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plain_profile_id", nullable = false)
    private PlainProfile plainProfile;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean active;

    @Column(name = "create_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Calendar createDateTime;

    @Column(name = "delete_date_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar deleteDateTime;

    @PrePersist
    public void prePersist() {
        active = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singleton(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return email.equals(that.email) &&
                password.equals(that.password) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role);
    }
}
