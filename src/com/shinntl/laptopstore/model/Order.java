/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author shinn
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private Long orderId;
    private Product product;
    private Customer customer;
    private Integer amount;
    private Status status;
}
