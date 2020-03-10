package com.molistore.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class LoginRqDto implements Serializable {

    final static long serialVersionUID = 7232546670110633767L;
    @NotNull
    private String email;
    @NotNull
    private String password;

    /**
     * Creates a new LoginRqDto.
     */
    public LoginRqDto() {
        super();
    }

    /**
     * Creates a new LoginRqDto.
     */
    public LoginRqDto(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }


    public int hashCode() {
        return new HashCodeBuilder().append(email).append(password).toHashCode();
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        LoginRqDto otherObject = ((LoginRqDto) other);
        return new EqualsBuilder().append(email, otherObject.email).append(password, otherObject.password).isEquals();
    }

    public String toString() {
        return new ToStringBuilder(this).append("userName", email).append("password", password).toString();
    }

}
