package com.group6.cleansweep;

import com.group6.cleansweep.models.User;
import com.group6.cleansweep.models.UserDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CleansweepApplication {


	public static void main(String[] args) {
		SpringApplication.run(CleansweepApplication.class, args);


		//Test code
		UserDB uc = UserDB.getInstance();
		User u1 = new User("user1", "123");
		User u2 = new User("user2", "123");
		u2.setCleansweep("AUHF(**!@#@813  <--some clean sweep hash");
		uc.add(u1);
		uc.add(u2);

	}

}
