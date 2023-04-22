/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.model.User;
import java.util.List;

/**
 *
 * @author shinn
 */
public interface IUserDAO{
    public List<User> findAll();
    public Long insert(User user);
    public User findByUsername(String userName);
}
