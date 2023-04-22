/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import java.sql.ResultSet;

/**
 *
 * @author shinn
 */
public interface RowMapper<T> {
    T mapRow(ResultSet resultSet);
}
