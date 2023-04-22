/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.model.Cart;
import java.util.List;

/**
 *
 * @author shinn
 */
public interface ICartDAO {
    public List<Cart> findByUserID(Long UserID); 
    public void insert(Cart cart);
    public boolean deleteByProductIDAndUserID(Long productId, Long userId);
    public boolean deleteByProductID(Long productId);
}
