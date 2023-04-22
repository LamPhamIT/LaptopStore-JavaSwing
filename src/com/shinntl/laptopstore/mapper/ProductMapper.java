/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Brand;
import com.shinntl.laptopstore.model.Product;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinn
 */
public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet) {
        Product product = new Product();
        try {

            product.setId(resultSet.getLong("Product_ID"));

            try {
                product.setName(resultSet.getString("Product_Name"));
                product.setDescrip(resultSet.getString("Descrip"));
                product.setPrice(resultSet.getLong("Price"));
                product.setAmount(resultSet.getInt("Amount"));
                product.setImageData(resultSet.getBinaryStream("Image").readAllBytes());
                Brand brand = new Brand();
                brand.setName(resultSet.getString("Brand_Name"));
                product.setBrand(brand);
            } catch (Exception e) {
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }

}
