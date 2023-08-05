package com.n19dccn112.model.dto;

import com.n19dccn112.model.enumeration.EventStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class EventDTO {
    private Long eventId;
    @NotNull
    @NotBlank
    private String eventName;
    private String description;
    private Date startDate;
    private int amoutEndDate;
    @NotNull
    @NotBlank
    private String discountCode;
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private int discountValue;
    private EventStatus eventStatus;
}
