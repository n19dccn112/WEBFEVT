package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CategoryDTO {
    private Long categoryId;
    @NotNull
    private String categoryName;
    private String description;
    private Integer amountProducts;
}
