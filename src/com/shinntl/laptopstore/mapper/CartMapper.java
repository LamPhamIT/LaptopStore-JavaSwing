/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Cart;
import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinn
 */
public class CartMapper implements RowMapper<Cart> {

    @Override
    public Cart mapRow(ResultSet resultSet) {
        Cart cart = new Cart();
        Product product = new ProductMapper().mapRow(resultSet);
        cart.setProduct(product);
        User user = new UserMapper().mapRow(resultSet);
        cart.setUser(user);
        return cart;
    }

}
