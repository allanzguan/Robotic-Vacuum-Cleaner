package com.group6.cleansweep;

import com.group6.cleansweep.models.User;
import com.group6.cleansweep.models.UserDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

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

		//Test code that was from the Old Repo
		/*Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/longjohn.json");
		CleanSweep c = new CleanSweep(f);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();

		System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));


		log.createLog();*/

	}

}
