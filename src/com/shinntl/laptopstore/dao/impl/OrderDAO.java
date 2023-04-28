/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.IOrderDAO;
import com.shinntl.laptopstore.mapper.OrderMapper;
import com.shinntl.laptopstore.model.Order;
import java.util.List;
import javax.print.attribute.standard.MediaSize;

/**
 *
 * @author shinn
 */
public class OrderDAO extends AbstractDAO<Order> implements IOrderDAO {

    @Override
    public Long insert(Order order) {
        String sql = "INSERT INTO OrderProduct(Product_ID, Customer_ID, Amount, Status_ID) VALUES(?, ?, ?, ?)";
        return insert(sql, order.getProduct().getId(), order.getCustomer().getId(), order.getAmount(),
                order.getStatus().getId());
    }

    @Override
    public boolean deleteByProduct_ID(Long productId) {
        String sql = "DELETE FROM OrderProduct WHERE Product_ID=?";
        return update(sql, productId);
    }

    @Override
    public List<Order> findByCustomerID(Long id) {
        String query = "SELECT * FROM OrderProduct AS o INNER JOIN Product AS p ON p.Product_ID=o.Product_ID WHERE o.Customer_ID=?";
        return query(query, new OrderMapper(), id);
    }

    @Override
    public boolean deleteByOrderID(Long orderId) {
        String sql = "DELETE FROM OrderProduct WHERE Order_ID=?";
        return update(sql, orderId);
    }

    @Override
    public boolean updateByOrderID(Order order) {
        String sql = "UPDATE OrderProduct SET Amount=? WHERE Order_ID=?";
        return update(sql, order.getAmount(), order.getOrderId());
    }

    @Override
    public Order findByOrderID(Long orderId) {
        String query = "SELECT * FROM OrderProduct o INNER JOIN Customer AS c ON o.Customer_ID=c.Customer_ID INNER JOIN Product AS p ON p.Product_ID=o.Product_ID INNER JOIN Status AS s ON s.Status_ID=o.Status_ID WHERE o.Order_ID=?";
        List<Order> list = query(query, new OrderMapper(), orderId);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT * FROM OrderProduct o INNER JOIN Customer AS c ON o.Customer_ID=c.Customer_ID INNER JOIN Product AS p ON p.Product_ID=o.Product_ID INNER JOIN Status AS s ON s.Status_ID=o.Status_ID";
        return query(query, new OrderMapper());
    }

    @Override
    public boolean updateStatus(Long orderId, Long statusId) {
        String sql = "UPDATE OrderProduct SET Status_ID=? WHERE Order_ID=?";
        return update(sql, orderId, statusId);
    }
    
    
}
