package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.FeatureDTO;
import com.n19dccn112.service.FeatureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/features")
@Tag(name = "Feature")
public class FeatureController implements IBaseController<FeatureDTO, Long, FeatureService> {
    @Resource
    @Getter
    private FeatureService service;
    @GetMapping("")
    public List<FeatureDTO> getAll(@RequestParam(required = false) Long featureType, @RequestParam(required = false) Long productId) {
        if (featureType != null)
            return getService().findAll1(featureType);
        if (productId != null)
            return getService().findAll(productId);
        return getService().findAll();
    }
}
