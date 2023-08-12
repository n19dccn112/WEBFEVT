package com.n19dccn112.model.key;

import com.n19dccn112.model.entity.Feature;
import com.n19dccn112.model.entity.Product;
import lombok.*;

import javax.persistence.Column;
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
public class FeatureDetailId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
