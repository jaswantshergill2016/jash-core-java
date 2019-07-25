package com.bank.dao;

import com.bank.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RegisterDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void register(User user) {

        String sql = "insert into users values(?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[] { user.getUsername(), user.getPassword(), user.getFirstname(),
                user.getLastname(), user.getEmail(), user.getAddress(), user.getPhone() });

    }
}
