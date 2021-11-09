package com.group6.cleansweep;

import com.group6.cleansweep.models.roomba.CleanSweep;
import com.group6.cleansweep.models.roomba.Floor;
import com.group6.cleansweep.models.roomba.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CleansweepApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	@Test
	public void emergencyShutdown() throws IOException, InterruptedException {
		Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/trap.json");
		CleanSweep c = new CleanSweep(f, true);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();
		//System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		String LogString = log.LogContent();
		boolean Shutdown = LogString.contains("SHUTDOWN!");

		assertEquals(true, Shutdown);
	}

	@Test
	public void chargingTest() throws IOException, InterruptedException {
		Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/TwoTileFloor.json");
		CleanSweep c = new CleanSweep(f, true);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();
		//System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		String LogString = log.LogContent();
		//System.out.println(LogString);
		boolean charged = LogString.contains("battery: 250.0/250.0");

		assertEquals(true, charged);
	}

	@Test
	public void stairTest() throws IOException, InterruptedException {
		Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/stairDemo.json");
		CleanSweep c = new CleanSweep(f, true);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();
		//System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		String LogString = log.LogContent();
		//System.out.println(LogString);
		boolean stairs = LogString.contains("type: stairs");

		assertEquals(false, stairs);
	}

	@Test
	public void LowBatteryTest() throws IOException, InterruptedException {
		Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/floor1.json");
		CleanSweep c = new CleanSweep(f, true);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();
		//System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		String LogString = log.LogContent();
		//System.out.println(LogString);
		boolean stairs = LogString.contains("Returning to Cleaning Cycle");

		assertEquals(true, stairs);
	}

	@Test
	public void dirtRetreatTest() throws IOException, InterruptedException {
		Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/superDirt.json");
		CleanSweep c = new CleanSweep(f, true);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();
		//System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		String LogString = log.LogContent();
		//System.out.println(LogString);
		boolean stairs = LogString.contains("Dirt Capacity Reached. Empty Me");

		assertEquals(true, stairs);
	}

	@Test
	public void doorTest() throws IOException, InterruptedException {
		Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/doorDemo.json");
		CleanSweep c = new CleanSweep(f, true);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();
		//System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		String LogString = log.LogContent();
		//System.out.println(LogString);
		boolean closedDoors = LogString.contains("Closed Door");

		assertEquals(false, closedDoors);
	}

	@Test
	public void openDoorTest() throws IOException, InterruptedException {
		Floor f = new Floor("src/main/java/com/group6/cleansweep/models/roomba/floorplans/doorDemoOpen.json");
		CleanSweep c = new CleanSweep(f, true);
		Logger log = Logger.getInstance();
		//activate the clean sweeps
		c.run();
		//System.out.println("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		log.write("Ending tile of CleanSweep run: " + Arrays.toString(c.getCurrentTile()));
		String LogString = log.LogContent();
		//System.out.println(LogString);
		boolean closedDoors = LogString.contains("Open Door");

		assertEquals(true, closedDoors);
	}

}
