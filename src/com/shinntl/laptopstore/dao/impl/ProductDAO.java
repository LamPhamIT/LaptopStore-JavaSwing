/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.IProductDAO;
import com.shinntl.laptopstore.mapper.ProductMapper;
import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.sort.Sorter;
import java.util.List;

/**
 *
 * @author shinn
 */
public class ProductDAO extends AbstractDAO<Product> implements IProductDAO {

    @Override
    public List<Product> findAll() {
        StringBuilder builder = new StringBuilder("SELECT * FROM Product p INNER JOIN Brand b ON p.Brand_ID = b.Brand_ID ");
        return query(builder.toString(), new ProductMapper());

    }

    @Override
    public Long insert(Product product) {
        String sql = "INSERT INTO Product(Product_Name, Descrip, Price, Amount, Brand_ID, Image) VALUES(?, ?, ?, ?, ?, ?)";
        return insert(sql, product.getName(), product.getDescrip(), product.getPrice(),
                product.getAmount(), product.getBrand().getId(), product.getImageData());
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE Product SET Product_Name=?, Descrip=?, Price=?, Amount=?, Brand_ID=?, Image=? WHERE Product_ID =?";
        return update(sql, product.getName(), product.getDescrip(), product.getPrice(), product.getAmount(),
                product.getBrand().getId(), product.getImageData(), product.getId());

    }

    @Override
    public List<Product> findByNameOrDescrip(String filterString) {
        String sql = "SELECT * FROM Product p INNER JOIN Brand b ON p.Brand_ID = b.Brand_ID WHERE p.Product_ID LIKE '%" + filterString + "%' OR p.Product_Name LIKE '%" + filterString + "%' OR p.Descrip LIKE '%"
                + filterString + "%'";
        return query(sql, new ProductMapper());
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM Product WHERE Product_ID=?";
        return update(sql, id);
    }

    @Override
    public Integer count() {
        String sql = "SELECT Count(*) FROM Product";
        return count(sql);
    }

    @Override
    public Product findByID(Long id) {
        String sql = "SELECT * FROM Product p INNER JOIN Brand b ON p.Brand_ID = b.Brand_ID WHERE p.Product_ID=?";
        List<Product> list = query(sql, new ProductMapper(), id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public boolean updateAmount(Product product) {
        String sql = "UPDATE Product SET Amount=? WHERE Product_ID=?";
        return update(sql, product.getAmount(), product.getId());
    }

    @Override
    public List<Product> findByBrandAndSort(Sorter sorter, Long brandId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Product ");
        if (brandId != null) {
            sql.append("WHERE Brand_ID=" + brandId);
        }
        if (sorter.getSortBy() != null) {
            sql.append(" ORDER BY " + sorter.getSortBy() + " " + sorter.getSortName());
        }
        return query(sql.toString(), new ProductMapper());
    }

    @Override
    public Product findByBestSelling() {
       String sql = "SELECT p.*,Count(*) Price FROM Product as p INNER JOIN OrderProduct as o ON p.Product_ID=o.Product_ID GROUP BY p.Product_ID LIMIT 0,1;";
       List<Product> list = query(sql, new ProductMapper());
       if(list.isEmpty()) {
           return null;
       } 
       return list.get(0);
    }
}
