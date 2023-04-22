/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.ICustomerDAO;
import com.shinntl.laptopstore.mapper.CustomerMapper;
import com.shinntl.laptopstore.model.Customer;
import java.util.List;

/**
 *
 * @author shinn
 */
public class CustomerDAO extends AbstractDAO<Customer> implements ICustomerDAO {

    @Override
    public Customer findByUserID(Long userID) {
        String query = "SELECT * FROM Customer AS c INNER JOIN User As u ON c.User_ID = u.User_ID WHERE u.User_ID=?;";
        List<Customer> list = query(query, new CustomerMapper(), userID);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Long insert(Customer customer) {
        String sql = "INSERT INTO Customer(Name, Email, Address, User_ID) VALUES(?,?,?,?)";
        return insert(sql, customer.getName(), customer.getEmail(), customer.getAddress(),
                customer.getUser().getUserID());
    }

}
