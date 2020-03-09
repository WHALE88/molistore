package com.molistore.application.entities;

import com.molistore.application.enums.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


@NamedQuery(name = "PlainProfile.findByActive",
        query = "select p from PlainProfile p where  p.userEntity.active = true order by p.id desc ")

@Getter
@Setter
@Entity
@Table(name = "plain_profiles")
@EqualsAndHashCode
@ToString(exclude = {"id"})
public class PlainProfile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "account")
    private String account;

    @Column(name = "language")
    private String language;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_addresses", joinColumns = @JoinColumn(name = "plain_profile_id"))
    private Set<Address> addressList = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "plain_profile_id"))
    @Column(name = "phone_number")
    private Set<String> phoneNumbers = new HashSet<>();

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Calendar birthDate;

    @OneToOne(mappedBy = "plainProfile")
    private UserEntity userEntity;

    public boolean isProfileActive() {
        return this.getUserEntity() != null
                && this.getUserEntity().getActive() != null
                && this.getUserEntity().getActive();
    }
}
