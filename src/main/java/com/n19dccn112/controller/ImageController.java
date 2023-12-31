package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.ImageDTO;
import com.n19dccn112.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/images")
@Tag(name = "Image")
@RequiredArgsConstructor
public class ImageController implements IBaseController<ImageDTO, Long, ImageService> {
    @Getter
    private final ImageService service;

    @GetMapping("")
    public List<ImageDTO> getAll(@RequestParam(required = false) Long productId) {
        if (productId != null)
            return getService().findAll(productId);
        else
            return getService().findAll();
    }
}
