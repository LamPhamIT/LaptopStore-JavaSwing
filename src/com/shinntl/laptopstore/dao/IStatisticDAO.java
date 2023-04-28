/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.model.Statistic;
import java.util.List;

/**
 *
 * @author shinn
 */
public interface IStatisticDAO{
    public List<Statistic> findAll();
    public List<Statistic> findByBrand();
}
