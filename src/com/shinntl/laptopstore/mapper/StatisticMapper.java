/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Statistic;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinn
 */
public class StatisticMapper implements RowMapper<Statistic> {

    @Override
    public Statistic mapRow(ResultSet resultSet) {
        Statistic statistic = new Statistic();
        try {
            statistic.setDate(resultSet.getTimestamp("date"));
            statistic.setTotalMoney(resultSet.getLong("Totalday"));
        } catch (SQLException e) {
        }
        try {
            statistic.setBrandName(resultSet.getString("Brand_Name"));
            statistic.setQuantity(resultSet.getInt("Quantity"));
        } catch (SQLException e) {
        }
        return statistic;
    }

}
