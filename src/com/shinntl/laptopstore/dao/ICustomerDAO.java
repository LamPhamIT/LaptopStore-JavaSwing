/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.model.Customer;

/**
 *
 * @author shinn
 */
public interface ICustomerDAO {
    public Customer findByUserID(Long userID);
    public Long insert(Customer customer);
}
