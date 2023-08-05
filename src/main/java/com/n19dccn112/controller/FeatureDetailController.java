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
@RequestMapping("api/FeatureDetail")
@Tag(name = "FeatureDetail")
public class FeatureDetailController{
    @Resource
    @Getter
    private FeatureDetailService service;

    @GetMapping("")
    public List<FeatureDetailDTO> getAll() {
        return getService().findAll();
    }

    @GetMapping("/{feature_id}-{product_id}")
    public FeatureDetailDTO get1(@PathVariable Long feature_id, @PathVariable Long product_id) {
        return service.findById(feature_id, product_id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{feature_id}-{product_id}")
    public FeatureDetailDTO update(@PathVariable Long feature_id, @PathVariable Long product_id, @Valid @RequestBody FeatureDetailDTO dto) {
        dto.setProductId(product_id);
        dto.setFeatureId(feature_id);
        return service.update(feature_id, product_id, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{feature_id}-{product_id}")
    public FeatureDetailDTO delete(@PathVariable Long feature_id, @PathVariable Long product_id) {
        return service.delete(feature_id, product_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public FeatureDetailDTO insert(@Valid @RequestBody FeatureDetailDTO d) {
        return getService().save(d);
    }
}
