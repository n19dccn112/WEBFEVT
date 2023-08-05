package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.Role;
import com.n19dccn112.model.enumeration.RoleNames;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoleDTO {
    private Long roleId;
    @NotNull
    @NotBlank
    private RoleNames name;
}
