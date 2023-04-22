/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.ICartDAO;
import com.shinntl.laptopstore.mapper.CartMapper;
import com.shinntl.laptopstore.model.Cart;
import java.util.List;

/**
 *
 * @author shinn
 */
public class CartDAO extends AbstractDAO<Cart> implements ICartDAO{

    @Override
    public List<Cart> findByUserID(Long userID) {
        String sql = "SELECT * FROM Cart c INNER JOIN Product p ON c.Product_ID = p.Product_ID WHERE User_ID=?";
        return query(sql, new CartMapper(), userID);
    }   

    @Override
    public void insert(Cart cart) {
        String sql = "INSERT INTO Cart(Product_ID, User_ID) VALUES(?, ?)";
        update(sql, cart.getProduct().getId(), cart.getUser().getUserID());
    }

    @Override
    public boolean deleteByProductIDAndUserID(Long productId, Long userId) {
        String sql = "DELETE FROM Cart WHERE Product_ID=? AND User_ID=?";
        return update(sql, productId, userId);
    }

    @Override
    public boolean deleteByProductID(Long productId) {
        String sql = "DELETE FROM Cart WHERE Product_ID=?";
        return update(sql, productId);
    }
}
