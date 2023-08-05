package com.n19dccn112.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    private Long featureId;

    @Column(name = "point")
    private Integer point;

    @Column(name = "specific")
    private String specific;

    @ManyToOne
    @JoinColumn(name = "feature_type_id")
    private FeatureType featuretype;

    @OneToMany(mappedBy = "featureDetailsId.feature")
    private List<FeatureDetail> featureDetails;
}
