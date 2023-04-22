/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.model;

import lombok.Data;

@Data
public class Customer{
    private Long id;
    private String name;
    private String email;
    private String address;
    private User user;
 
}
