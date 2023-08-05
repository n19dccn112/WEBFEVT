package com.n19dccn112.model.entity;

import com.n19dccn112.model.key.FeatureDetailId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feature_detail")
public class FeatureDetail {
    @EmbeddedId
    private FeatureDetailId featureDetailsId;
}
