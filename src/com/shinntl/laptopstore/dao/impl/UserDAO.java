/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.IUserDAO;
import com.shinntl.laptopstore.mapper.UserMapper;
import com.shinntl.laptopstore.model.User;
import java.util.List;

public class UserDAO extends AbstractDAO<User> implements IUserDAO{

    @Override
    public List<User> findAll() {   
        String query = "SELECT * FROM User;";
        return query(query, new UserMapper());
    }

    @Override
    public Long insert(User user) {
        String query = "INSERT INTO User(UserName, Password, Role_ID) VALUES(?, ?, ?)";
        return insert(query, user.getUserName(), user.getPassword(), user.getRole().getRoleID());
    }

    @Override
    public User findByUsername(String userName) {
        String query = "SELECT * FROM User AS u INNER JOIN Role AS r ON u.Role_ID = r.Role_ID WHERE u.Username=?;";
        List<User> list = query(query, new UserMapper(), userName);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }    
}
