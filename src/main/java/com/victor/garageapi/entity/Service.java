package com.victor.garageapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    @Id
    @SequenceGenerator(
            name = "service_sequence",
            sequenceName = "service_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_sequence"
    )
    @Column(name = "service_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "sku")
    private String SKU;
    @Column(name = "date_of_purchase")
    private LocalDateTime dateOfPurchase;
    @Column(name = "scheduled_appointment")
    private LocalDateTime scheduledAppointment;
    @Column(name = "is_available")
    private boolean isAvailable;
    @JsonIgnore
    @ManyToMany(mappedBy = "services")
    @ToString.Exclude
    private List<Client> clients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Service service = (Service) o;
        return id != null && Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
