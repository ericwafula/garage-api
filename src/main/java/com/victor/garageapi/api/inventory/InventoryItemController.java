package com.victor.garageapi.api.inventory;

import com.victor.garageapi.dto.InventoryItemDto;
import com.victor.garageapi.dto.ResponseMessage;
import com.victor.garageapi.entity.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;

    @PostMapping("new-item")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addNewInventoryItem(@RequestBody InventoryItemDto inventoryItem) {
        inventoryItemService.addNewInventoryItem(inventoryItem);
        ResponseMessage message = ResponseMessage.builder()
                .message(inventoryItem.getName() + "added successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryItem>> allInventoryItems() {
        return new ResponseEntity<>(inventoryItemService.allInventoryItems(), HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryItem> findItemById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(inventoryItemService.findItemById(id), HttpStatus.OK);
    }

    @GetMapping("sku/{sku}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryItem> findItemBySKU(@PathVariable("sku") String SKU) {
        return new ResponseEntity<>(inventoryItemService.findItemBySKU(SKU), HttpStatus.OK);
    }

    @GetMapping("name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryItem>> findInventoryItemsByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(inventoryItemService.findInventoryItemsByName(name), HttpStatus.OK);
    }

    @PutMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> updateInventoryItem(@PathVariable("id") Long id, @RequestBody InventoryItemDto inventoryItemDto) {
        inventoryItemService.updateInventoryItem(id, inventoryItemDto);
        ResponseMessage message = ResponseMessage.builder()
                .message(inventoryItemDto + "updated successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteInventoryItem(@PathVariable("id") Long id) {
        inventoryItemService.deleteInventoryItem(id);
        ResponseMessage message = ResponseMessage.builder()
                .message(id + "deleted successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete-all-inventory-items")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteAllInventoryItems() {
        inventoryItemService.deleteAllInventoryItems();
        ResponseMessage message = ResponseMessage.builder()
                .message("all inventory items deleted successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }
}
