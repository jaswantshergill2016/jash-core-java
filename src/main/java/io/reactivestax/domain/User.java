package io.reactivestax.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Data
@Entity
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
public class User {

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message="Email Address is compulsory")
    @Email(message = "Email Address is not a valid format")
    @Column(unique=true,length = 20)
    private String email;

    @NotEmpty(message = "First name is compulsory")
    @Pattern(regexp = "[a-z-A-Z]*", message = "First name has invalid characters")
    @Column(length = 30)
    private String firstName;

    @Pattern(regexp = "[a-z-A-Z]*", message = "Middle name has invalid characters")
    @Column(length = 30)
    private String middleName;

    @NotEmpty(message = "Last name is compulsory")
    @Pattern(regexp = "[a-z-A-Z]*", message = "Last name has invalid characters")
    @Column(length = 30)
    private String lastName;

    @NotEmpty(message = "Password is compulsory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 15)
    private String password;

    @NotEmpty(message = "Date of birth is compulsory")
    private String dob;
}
