package com.example.springbootapp;

import com.example.springbootapp.data.dao.AdminDao;
import com.example.springbootapp.data.entities.Admin;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

//@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringBootAppApplicationTests {

	@Autowired
	private AdminDao adminDao;

	@Test
	void contextLoads() {
		Optional<Admin> ad= adminDao.findById("12");
		if(ad.isPresent()){
			System.out.println(ad.get().getFirstName());
		}
		else{
			System.out.println("Admin not found");
		}
	}

}
