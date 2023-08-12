package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.key.FeatureDetailId;
import com.n19dccn112.service.FeatureDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/featureDetails")
@Tag(name = "FeatureDetail")
public class FeatureDetailController{
    @Resource
    @Getter
    private FeatureDetailService service;

    @GetMapping("")
    public List<FeatureDetailDTO> getAll() {
        return getService().findAll();
    }

    @GetMapping("/{featureId}-{productId}")
    public FeatureDetailDTO get1(@PathVariable Long featureId, @PathVariable Long productId) {
        return service.findById(featureId, productId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{featureId}-{productId}")
    public FeatureDetailDTO update(@PathVariable Long featureId, @PathVariable Long productId, @Valid @RequestBody FeatureDetailDTO dto) {
        dto.setProductId(productId);
        dto.setFeatureId(featureId);
        return service.update(featureId, productId, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{featureId}-{productId}")
    public FeatureDetailDTO delete(@PathVariable Long featureId, @PathVariable Long productId) {
        return service.delete(featureId, productId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public FeatureDetailDTO insert(@Valid @RequestBody FeatureDetailDTO d) {
        return getService().save(d);
    }
}
