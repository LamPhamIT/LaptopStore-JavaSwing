/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.mapper;

import com.shinntl.laptopstore.model.Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinn
 */
public class StatusMapper implements RowMapper<Status> {

    @Override
    public Status mapRow(ResultSet resultSet) {
        Status status = new Status();
        try {
            status.setId(resultSet.getLong("Status_Id"));
            status.setName(resultSet.getString("Status_Name"));
        } catch (SQLException ex) {
            Logger.getLogger(StatusMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

}
