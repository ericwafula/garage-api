package com.victor.garageapi.api.inventory;

import com.victor.garageapi.dto.InventoryItemDto;
import com.victor.garageapi.entity.InventoryItem;

import java.util.List;

public interface InventoryItemService {
    void addNewInventoryItem(InventoryItemDto inventoryItem);

    List<InventoryItem> allInventoryItems();

    InventoryItem findItemById(Long id);

    InventoryItem findItemBySKU(String sku);

    List<InventoryItem> findInventoryItemsByName(String name);

    void updateInventoryItem(Long id, InventoryItemDto inventoryItemDto);

    void deleteInventoryItem(Long id);

    void deleteAllInventoryItems();
}
