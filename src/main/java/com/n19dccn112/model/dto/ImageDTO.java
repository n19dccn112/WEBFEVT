package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ImageDTO {
    private Long imageId;
    @URL
    @NotNull
    private String url;
    @NotNull
    private Long productId;
}
