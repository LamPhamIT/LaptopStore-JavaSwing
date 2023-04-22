/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.IBrandDAO;
import com.shinntl.laptopstore.mapper.BrandMapper;
import com.shinntl.laptopstore.model.Brand;
import java.util.List;

/**
 *
 * @author shinn
 */
public class BrandDAO extends AbstractDAO<Brand> implements IBrandDAO{

    @Override
    public Brand findByName(String name) {
        String sql = "SELECT * FROM Brand WHERE Brand_Name=?";
        List<Brand> list = query(sql, new BrandMapper(), name);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
}
