package com.group6.cleansweep;

import com.group6.cleansweep.models.User;
import com.group6.cleansweep.models.UserDB;
import com.group6.cleansweep.models.roomba.CleanSweep;
import com.group6.cleansweep.models.roomba.Floor;
import com.group6.cleansweep.models.roomba.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class CleansweepApplication {


	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(CleansweepApplication.class, args);

		//Test code
		UserDB uc = UserDB.getInstance();
		User u1 = new User("user1", "123");
		User u2 = new User("user2", "123");
		CleanSweep cs = new CleanSweep(new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/superDirt.json"), false);
		u2.setCleansweep(cs);

		uc.add(u1);
		uc.add(u2);

	}

}
