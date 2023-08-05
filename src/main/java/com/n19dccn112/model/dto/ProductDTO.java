package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.Image;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Long productId;
    private Date createDate;
    private String description;
    @NotNull
    @NotBlank
    private String name;
    @DecimalMin(value = "0")
    private Integer price;
    @DecimalMin(value = "0")
    private Integer remain;
    private Date updateDate;
    private Date expirationDate;
    private List<String> imageUrl;
}
