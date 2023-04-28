/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.model.Brand;
import java.util.List;

/**
 *
 * @author shinn
 */
public interface IBrandDAO {
    public Brand findByName(String name);
    public List<Brand> findAll();
}
