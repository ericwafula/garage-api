package com.victor.garageapi.entity;

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
@Table(
        name = "inventory_item"
)
public class InventoryItem {
    @Id
    @SequenceGenerator(
            name = "inventory_item_sequence",
            sequenceName = "inventory_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inventory_item_sequence"
    )
    @Column(name = "inventory_item_id")
    private Long id;
    @Column(name = "sku")
    private String SKU;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "date_added")
    private LocalDateTime dateAdded;
    @Column(name = "date_of_purchase")
    private LocalDateTime dateOfPurchase;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @ManyToOne
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InventoryItem that = (InventoryItem) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
