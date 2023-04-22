/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shinntl.laptopstore.dao;

import com.shinntl.laptopstore.mapper.RowMapper;
import java.util.List;

/**parameters
 *
 * @author shinn
 */
public interface GenericDAO<T> {
   <T> List<T> query(String query, RowMapper<T> rowMapper,Object... parameters);
   Long insert(String sql, Object... parameters);
   <T> boolean update(String sql, Object... parameters);
   <T> int count(String sql, Object... parameters);
}
