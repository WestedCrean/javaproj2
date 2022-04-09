package com.filip.pai;

import javax.annotation.PostConstruct;

import com.filip.pai.dao.userDao;
import com.filip.pai.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PaiApplication {
	@Autowired
	private userDao dao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(PaiApplication.class, args);
	}
	@PostConstruct
	public void init() {
		dao.save(new User("admin", "admin",
				"admin",passwordEncoder.encode("passwd")));
		dao.save(new User("Kralj", "Romanica",
				"kroma",passwordEncoder.encode("passwd")));
	}
}

