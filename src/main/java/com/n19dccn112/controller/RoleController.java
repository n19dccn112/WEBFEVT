package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.RoleDTO;
import com.n19dccn112.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/Role")
@Tag(name = "Role")
public class RoleController implements IBaseController<RoleDTO, Long, RoleService> {
    @Resource
    @Getter
    private RoleService service;

    @GetMapping("")
    public List<RoleDTO> getAll() {
        return getService().findAll();
    }
}
