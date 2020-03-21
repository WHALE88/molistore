package com.molistore.application.entities.profile;

import com.molistore.application.entities.PlainProfile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.molistore.application.util.CollectionsUtil.mapList;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<Authority> authorities = new HashSet<>();

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
        return mapList(authorities, authority -> new SimpleGrantedAuthority(authority.getRole().toString()));
    }

    public void addAuthorities(@NotNull Authority authority) {
        authorities.add(authority);
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
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

        if (!email.equals(that.email)) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return active != null ? active.equals(that.active) : that.active == null;
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }
}
