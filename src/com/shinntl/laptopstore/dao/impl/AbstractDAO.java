/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.dao.impl;

import com.shinntl.laptopstore.dao.GenericDAO;
import com.shinntl.laptopstore.mapper.RowMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> implements GenericDAO<T> {
    
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Mid_Exam";
            String userName = "root";
            String password = "lamdz2k4";
            return DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Connection fail");
        }
        return null;
    }
    
    @Override
    public <T> List<T> query(String query, RowMapper<T> rowMapper, Object... parameters) {
        Connection connection = getConnection();
        List<T> listResult = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            setParameter(statement, parameters);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listResult.add(rowMapper.mapRow(resultSet));
            }
            return listResult;
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                System.out.println("Close fail");
            }
        }
        return listResult;
    }
    
    @Override
    public Long insert(String sql, Object... parameters) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Long id = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(statement, parameters);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                id = resultSet.getLong(1);
                return id;
            }
        } catch (SQLException ex) {
            System.out.println("Insert fail");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                System.out.println("Close fail");
            }
        }
        return id;
    }
    
    public void setParameter(PreparedStatement statement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                Object item = parameters[i];
                if (item instanceof Long) {
                    statement.setLong(i + 1, (Long) item);
                } else if (item instanceof Integer) {
                    statement.setInt(i + 1, (Integer) item);
                } else if (item instanceof String) {
                    statement.setString(i + 1, (String) item);
                } else if (item instanceof byte[]) {
                    statement.setBytes(i + 1, (byte[]) item);
                    
                } else if (item instanceof Date) {
                    Date date = (Date) item;
                    Timestamp timestamp = new Timestamp(date.getTime());
                    statement.setTimestamp(i + 1, timestamp);
                }
            }
        } catch (Exception e) {
            System.out.println("Set parameters fail");
        }
    }
    
    @Override
    public <T> boolean update(String sql, Object... parameters) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            
            boolean check = statement.executeUpdate() > 0;
            connection.commit();
            return check;
        } catch (SQLException ex) {
            System.out.println("Insert fail");
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public <T> int count(String sql, Object... parameters) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
                return count;
            }
        } catch (SQLException ex) {
            System.out.println("Insert fail");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                System.out.println("Close fail");
            }
        }
        return count;
    }
    
}
