/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Role;
import com.shinntl.laptopstore.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author shinn
 */
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet) {
        User user = new User();
        try {

            user.setUserID(resultSet.getLong("User_ID"));
            Role role = new Role();
            try {
                user.setUserName(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                role.setRoleID(resultSet.getLong("Role_ID"));
                role.setName(resultSet.getString("Name"));
                user.setRole(role);
            } catch (Exception e) {
                System.out.println("Query not join query");
            }
            return user;
        } catch (SQLException ex) {
            System.out.println("Mappper fail");

        }
        return null;
    }

}
