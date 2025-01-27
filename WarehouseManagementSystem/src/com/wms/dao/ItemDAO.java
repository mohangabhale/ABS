package com.wms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.wms.model.Item;

public class ItemDAO {
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/WarehouseDB", "root", "mysql@123");
    }

    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Items";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setLocation(rs.getString("location"));
                items.add(item);
            }
        }
        return items;
    }

    public void addItem(Item item) throws SQLException {
        String query = "INSERT INTO Items (name, quantity, location) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getQuantity());
            stmt.setString(3, item.getLocation());
            stmt.executeUpdate();
        }
    }
}
