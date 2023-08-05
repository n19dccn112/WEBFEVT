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
@Table(name = "feature_type")
public class FeatureType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_type_id")
    private Long featureTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @OneToMany(mappedBy = "featuretype")
    private List<Feature> features;
}
