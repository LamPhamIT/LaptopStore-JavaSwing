/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author shinn
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistic {
    
    
    private Date date;
    private Long totalMoney;
    private String brandName;
    private int quantity;
}
