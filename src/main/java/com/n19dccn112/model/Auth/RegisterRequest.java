package com.n19dccn112.model.Auth;

import com.n19dccn112.model.enumeration.RoleNames;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterRequest {
    private Long signup_id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    private String address;
    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;
    @Email
    private String email;
    private RoleNames rolename;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
