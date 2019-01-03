package com.wmh.programmeTransaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by 周大侠
 * 2018-11-24 10:57
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void add(String id,String name) {
        String sql = "INSERT INTO user(id,name) VALUES(?,?)";
        int updateResult = jdbcTemplate.update(sql, id, name);
        System.out.println("updateResult:" + updateResult);
    }
}
