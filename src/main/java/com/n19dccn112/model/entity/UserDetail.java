package com.n19dccn112.model.entity;

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
@Table(name = "user_detail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long userDetailId;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "address_default")
    private int addressDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
