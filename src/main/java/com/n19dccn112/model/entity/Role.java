package com.n19dccn112.model.entity;

import com.n19dccn112.model.enumeration.RoleNames;
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
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleNames name;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
