package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.dto.RateDTO;
import com.n19dccn112.model.key.RateId;
import com.n19dccn112.service.RateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/Rate")
@Tag(name = "Rate")
public class RateController {
    @Resource
    @Getter
    private RateService service;

    @GetMapping("")
    public List<RateDTO> getAll(@RequestParam(required = false) Long product_id) {
        if (product_id == null)
            return getService().findAll();
        else
            return getService().findAll(product_id);
    }

    @GetMapping("/{user_id}-{product_id}")
    public RateDTO get1(@PathVariable Long user_id, @PathVariable Long product_id) {
        return service.findById(user_id, product_id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{user_id}-{product_id}")
    public RateDTO update(@PathVariable Long user_id, @PathVariable Long product_id, @Valid @RequestBody RateDTO dto) {
        dto.setProductId(product_id);
        dto.setUserId(user_id);
        return service.update(user_id, product_id, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{user_id}-{product_id}")
    public RateDTO delete(@PathVariable Long user_id, @PathVariable Long product_id) {
        return service.delete(user_id, product_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public RateDTO insert(@Valid @RequestBody RateDTO d) {
        return getService().save(d);
    }
}
