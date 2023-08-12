package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.FeatureTypeDTO;
import com.n19dccn112.service.FeatureTypeService;
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
@RequestMapping("api/featureTypes")
@Tag(name = "FeatureType")
public class FeatureTypeController implements IBaseController<FeatureTypeDTO, Long, FeatureTypeService> {
    @Resource
    @Getter
    private FeatureTypeService service;

    @GetMapping("")
    public List<FeatureTypeDTO> getAll() {
        return getService().findAll();
    }
}
