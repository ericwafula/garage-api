package com.victor.garageapi.api.inventory;

import com.victor.garageapi.dto.InventoryItemDto;
import com.victor.garageapi.entity.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    @Transactional
    public void addNewInventoryItem(InventoryItemDto inventoryItem) {
        InventoryItem item = new InventoryItem();

        if(!inventoryItem.getName().equals("") && inventoryItem.getPrice() != null && !inventoryItem.getSKU().equals("")) {
            item.setName(inventoryItem.getName());
            item.setPrice(inventoryItem.getPrice());
            item.setSKU(inventoryItem.getSKU());
            item.setDateAdded(LocalDateTime.now());
            item.setIsAvailable(true);
            inventoryItemRepository.save(item);
        }
    }

    @Override
    public List<InventoryItem> allInventoryItems() {
        return inventoryItemRepository.findAll();
    }

    @Override
    public InventoryItem findItemById(Long id) {
        Optional<InventoryItem> item = inventoryItemRepository.findById(id);
        item.orElseThrow(() -> new EntityNotFoundException("NO_ITEM_FOUND_WITH_ID: " + id));
        return item.get();
    }

    @Override
    public InventoryItem findItemBySKU(String sku) {
        Optional<InventoryItem> item = inventoryItemRepository.findInventoryItemBySKU(sku);
        item.orElseThrow(() -> new EntityNotFoundException("NO_ITEM_FOUND_WITH_SKU: " + sku));
        return item.get();
    }

    @Override
    public List<InventoryItem> findInventoryItemsByName(String name) {
        return inventoryItemRepository.findInventoryItemsByNameContainsIgnoreCase(name);
    }

    @Override
    @Transactional
    public void updateInventoryItem(Long id, InventoryItemDto inventoryItemDto) {
        Optional<InventoryItem> item = inventoryItemRepository.findById(id);
        item.orElseThrow(() -> new EntityNotFoundException("NO_ITEM_FOUND_WITH_ID: " + id));

        if (!inventoryItemDto.getName().equals("")) {
            item.get().setName(inventoryItemDto.getName());
        }
        if (!inventoryItemDto.getPrice().isNaN()) {
            item.get().setPrice(inventoryItemDto.getPrice());
        }
        if (!inventoryItemDto.getSKU().equals("")) {
            item.get().setSKU(inventoryItemDto.getSKU());
        }
        if (inventoryItemDto.getIsAvailable() != null) {
            item.get().setIsAvailable(inventoryItemDto.getIsAvailable());
        }

        inventoryItemRepository.save(item.get());

    }

    @Override
    @Transactional
    public void deleteInventoryItem(Long id) {
        Optional<InventoryItem> item = inventoryItemRepository.findById(id);
        item.orElseThrow(() -> new EntityNotFoundException("NO_INVENTORY_ITEM_FOUND_WITH_ID: " + id));
        inventoryItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllInventoryItems() {
        inventoryItemRepository.deleteAll();
    }
}
