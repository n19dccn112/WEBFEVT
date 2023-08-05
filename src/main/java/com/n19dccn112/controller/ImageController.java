package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.ImageDTO;
import com.n19dccn112.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/Image")
@Tag(name = "Image")
@RequiredArgsConstructor
public class ImageController implements IBaseController<ImageDTO, Long, ImageService> {
    @Getter
    private final ImageService service;
    @GetMapping("")
    public List<ImageDTO> getAll() {
        return getService().findAll();
    }
}
