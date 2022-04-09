package com.filip.pai.dao;

import com.filip.pai.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface userDao extends CrudRepository<User, Integer> {
    public User findByLogin(String login);
    public List<User> findAll();
    public void deleteById(Integer userId);
}
