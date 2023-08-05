package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.FeatureDTO;
import com.n19dccn112.service.FeatureService;
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
@RequestMapping("api/Feature")
@Tag(name = "Feature")
public class FeatureController implements IBaseController<FeatureDTO, Long, FeatureService> {
    @Resource
    @Getter
    private FeatureService service;
    @GetMapping("")
    public List<FeatureDTO> getAll() {
        return getService().findAll();
    }
}
