package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDetailDTO {
    private Long userDetailId;
    private String address;
    @NotNull
    @NotBlank
    private String name;
    private int addressDefault;
}
