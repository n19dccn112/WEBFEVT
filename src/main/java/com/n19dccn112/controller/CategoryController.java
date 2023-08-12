package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.OrderDTO;
import com.n19dccn112.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/categories")
@Tag(name = "Category")
public class CategoryController implements IBaseController<CategoryDTO, Long, CategoryService> {
    @Resource
    @Getter
    private CategoryService service;

    @GetMapping("")
    public List<CategoryDTO> getAll() {
        return getService().findAll();
    }
}
