/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.IBillDAO;
import com.shinntl.laptopstore.model.Bill;

/**
 *
 * @author shinn
 */
public class BillDAO extends AbstractDAO<Bill> implements IBillDAO{

    @Override
    public Long insert(Bill bill) {
        String sql = "INSERT INTO Bill(Order_ID, Createddate, Createdby) VALUES(?,?,?)";
        return insert(sql, bill.getOrder().getOrderId(), bill.getCreatedDate(), bill.getCreatedBy());
    }
    
}
