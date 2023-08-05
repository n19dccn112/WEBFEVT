package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/User")
@Tag(name = "User")
public class UserController implements IBaseController<UserDTO, Long, UserService> {
    @Resource
    @Getter
    private UserService service;

    @GetMapping("")
    public List<UserDTO> getAll() {
        return getService().findAll();
    }
}
