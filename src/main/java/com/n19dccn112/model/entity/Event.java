package com.n19dccn112.model.entity;

import com.n19dccn112.model.enumeration.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "discount_value")
    private int discountValue;

    @Column(name = "event_status")
    private EventStatus eventStatus;

    @OneToMany(mappedBy = "eventProductId.event")
    private List<EventProduct> eventProducts;
}
