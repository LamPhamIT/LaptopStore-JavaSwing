/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Customer;
import com.shinntl.laptopstore.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinn
 */
public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet resultSet) {
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getLong("Customer_ID"));
            try {
                customer.setName(resultSet.getString("Name"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setAddress(resultSet.getString("Address"));
                User user = new User();
                user.setUserName(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                user.setUserID(resultSet.getLong("User_ID"));
                customer.setUser(user);
            } catch (Exception e) {
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }

}
