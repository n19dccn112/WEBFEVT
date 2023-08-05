package com.n19dccn112.model.dto;

import com.n19dccn112.model.key.EventProductId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import java.util.Date;

@Getter
@Setter
public class EventProductDTO {
    private Long eventId;
    private Long productId;
    private Date createDate;
    private Date updateDate;
}
