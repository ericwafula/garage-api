package com.victor.garageapi.api.inventory;

import com.victor.garageapi.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findInventoryItemBySKU(String SKU);

    List<InventoryItem> findInventoryItemsByNameContainsIgnoreCase(String name);
}
