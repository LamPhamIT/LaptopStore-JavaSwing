/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author shinn
 */
@NoArgsConstructor
@Data
public class Product {
    private Long id;
    private String name;
    private String descrip;
    private Long price;
    private Brand brand;
    private Integer amount;
    private byte[] imageData;
}
