package com.wms.service;

import java.sql.SQLException;
import java.util.List;
import com.wms.dao.ItemDAO;
import com.wms.model.Item;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public List<Item> getItems() throws SQLException {
        return itemDAO.getAllItems();
    }

    public void addItem(Item item) throws SQLException {
        itemDAO.addItem(item);
    }
}
