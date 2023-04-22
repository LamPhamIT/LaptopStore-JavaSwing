/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Brand;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinn
 */
public class BrandMapper implements RowMapper<Brand>{

    @Override
    public Brand mapRow(ResultSet resultSet) {
        Brand brand = new Brand();
        try {
            brand.setId(resultSet.getLong("Brand_ID"));
            brand.setName(resultSet.getString("Brand_Name"));
        } catch (SQLException ex) {
            Logger.getLogger(BrandMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return brand;
    }
}
