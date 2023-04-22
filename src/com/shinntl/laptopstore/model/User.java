/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author shinn
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class User{
    private Long userID;
    private String userName;
    private String password;
    private Role role;
}
