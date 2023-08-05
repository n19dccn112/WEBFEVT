package com.n19dccn112.model.key;

import com.n19dccn112.model.entity.Event;
import com.n19dccn112.model.entity.Product;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class
EventProductId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
