package com.wms.controller;

import java.util.List;
import com.wms.model.Item;
import com.wms.service.ItemService;

public class MainController {
    private ItemService itemService = new ItemService();

    public void showItems() {
        try {
            List<Item> items = itemService.getItems();
            for (Item item : items) {
                System.out.println("Item ID: " + item.getItemId());
                System.out.println("Name: " + item.getName());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Location: " + item.getLocation());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewItem(String name, int quantity, String location) {
        Item item = new Item();
        item.setName(name);
        item.setQuantity(quantity);
        item.setLocation(location);
        try {
            itemService.addItem(item);
            System.out.println("Item added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
