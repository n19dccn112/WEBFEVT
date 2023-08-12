package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("api/products")
@Tag(name = "Product")
public class ProductController implements IBaseController<ProductDTO, Long, ProductService> {
    @Resource
    @Getter
    private ProductService service;

    @GetMapping("")
    public List<ProductDTO> getAll(@RequestParam(required = false) Long categoryId,
                                   @RequestParam(required = false) List<Long> featureIds,
                                   @RequestParam(required = false) List<Long> products) {
        if (products != null) {
            return getService().findAllByListProducts(products);
        }
        if (categoryId != null && featureIds != null) {
            return getService().findAll(categoryId, featureIds);
        } else if (categoryId != null) {
            return getService().findAll(categoryId);
        } else if (featureIds != null)
            return getService().findAll(featureIds);
        else
            return getService().findAll();
    }
}
