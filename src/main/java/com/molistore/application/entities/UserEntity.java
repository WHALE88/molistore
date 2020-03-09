package com.molistore.application.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "password")
    private String password;

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

}
