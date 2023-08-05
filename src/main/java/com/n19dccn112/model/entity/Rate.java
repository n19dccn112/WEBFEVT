package com.n19dccn112.model.entity;

import com.n19dccn112.model.key.RateId;
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
@Table(name = "rate")
public class Rate {
    @EmbeddedId
    private RateId rateId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "point")
    private Integer point;
}
