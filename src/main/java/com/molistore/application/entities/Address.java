package com.molistore.application.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Address implements Serializable {

    @Column(name = "region")
    private String region;

    @NotEmpty
    @NotNull
    @Column(name = "city")
    private String city;

    private String street;

    private String building;

    private String apartment;

    @NotEmpty
    @NotNull
    @Column(name = "zip_code")
    private String zipCode;

    private String country;

}
