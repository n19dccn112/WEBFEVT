package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.UserDetailDTO;
import com.n19dccn112.service.UserDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@CrossOrigin("*")
@RestController
@RequestMapping("api/UserDetail")
@Tag(name = "UserDetail")
public class UserDetailController implements IBaseController<UserDetailDTO, Long, UserDetailService> {
    @Resource
    @Getter
    private UserDetailService service;
}
