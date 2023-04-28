/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.IStatisticDAO;
import com.shinntl.laptopstore.mapper.StatisticMapper;
import com.shinntl.laptopstore.model.Statistic;
import java.util.List;

/**
 *
 * @author shinn
 */
public class StatisticDAO extends AbstractDAO<Statistic> implements IStatisticDAO{

    @Override
    public List<Statistic> findAll() {
        String sql = "Select DATE(t.Createddate) as date, Sum(t.Total) as 'Totalday' from (Select b.*,p.Price * o.Amount as 'Total' from Bill as b Inner join OrderProduct as o On o.Order_ID=b.Order_Id Inner join Product\n" +
"as p ON o.Product_ID=p.Product_ID) AS t GROUP BY date;";
        return query(sql, new StatisticMapper());
    }

    @Override
    public List<Statistic> findByBrand() {
        String sql = "SELECT x.Brand_Name,COUNT(*) AS Quantity FROM (SELECT b.*, o.Product_ID,  p.Brand_ID, r.Brand_Name FROM Bill AS b INNER JOIN OrderProduct AS o ON b.Order_ID=o.Order_ID INNER JOIN Product AS p ON o.Product_ID=p.Product_ID INNER JOIN Brand AS r ON r.Brand_ID=p.Brand_ID) AS x GROUP BY x.Brand_Name;";
        return query(sql, new StatisticMapper());
    }
  
}
