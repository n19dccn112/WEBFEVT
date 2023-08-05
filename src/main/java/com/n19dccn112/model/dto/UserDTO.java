package com.n19dccn112.model.dto;

import com.n19dccn112.model.enumeration.RoleNames;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    @NotBlank
    private String username;
    private String phone;
    private RoleNames role;
    private Long userDetailId;
    private String address;
    private String name;
    private int addressDefault;
}
