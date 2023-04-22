/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Customer;
import com.shinntl.laptopstore.model.Order;
import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.model.Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinn
 */
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet) {
        Order order = new Order();
        try {
            order.setOrderId(resultSet.getLong("Order_ID"));
            Product product = null;
            try {
                product = new ProductMapper().mapRow(resultSet);
            } catch (Exception e) {
            }
            Customer customer = null;
            try {
                customer = new CustomerMapper().mapRow(resultSet);
            } catch (Exception e) {
            }
            order.setAmount(resultSet.getInt("Amount"));
            order.setProduct(product);
            order.setCustomer(customer);
            Status status = new Status();
            try {
                status.setId(resultSet.getLong("Status_ID"));
                status.setName(resultSet.getString("Status_Name"));
            } catch (Exception e) {
            }
            order.setStatus(status);
        } catch (SQLException ex) {
            Logger.getLogger(OrderMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

}
