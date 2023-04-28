/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.model.Order;
import java.util.List;

/**
 *
 * @author shinn
 */
public interface IOrderDAO {
    public Long insert(Order order);
    public boolean deleteByProduct_ID(Long productId);
    public List<Order> findByCustomerID(Long id);
    public boolean deleteByOrderID(Long orderId);
    public boolean updateByOrderID(Order order);
    public Order findByOrderID(Long orderId);
    public List<Order> findAll();
    public boolean updateStatus(Long orderId,Long statusId);
}
