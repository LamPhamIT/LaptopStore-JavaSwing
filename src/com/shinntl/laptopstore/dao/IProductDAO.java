/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.sort.Sorter;
import java.util.List;

/**
 *
 * @author shinn
 */
public interface IProductDAO {
    public List<Product> findAll();
    
    public Long insert(Product product);
    
    public boolean update(Product product);
    
    public List<Product> findByNameOrDescrip(String filterString);
    
    public boolean delete(Long id);
    public Integer count();
    public Product findByID(Long id);
    public boolean updateAmount(Product product);
    
    public List<Product> findByBrandAndSort(Sorter sorter, Long brandId);
    
    public Product findByBestSelling();
}
