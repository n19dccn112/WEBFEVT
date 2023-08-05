package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.service.ProductService;
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
@RequestMapping("api/Product")
@Tag(name = "Product")
public class ProductController implements IBaseController<ProductDTO, Long, ProductService> {
    @Resource
    @Getter
    private ProductService service;

    @GetMapping("")
    public List<ProductDTO> getAll() {
        return getService().findAll();
    }
}
