package org.ladbury.persistentData;

import java.sql.Timestamp;

import javax.persistence.EntityManager;

import org.ladbury.abodePkg.Abode;
import org.ladbury.abodePkg.Room;
import org.ladbury.abodePkg.Abode.AbodeType;
import org.ladbury.abodePkg.Room.RoomType;
import org.ladbury.cataloguePkg.CatalogueEntry;
import org.ladbury.consumptionPatternPkg.ConsumptionPattern;
import org.ladbury.consumptionPatternPkg.ConsumptionPattern.ConsumptionType;
import org.ladbury.deviceActivityPkg.DeviceActivity;
import org.ladbury.devicePkg.Device;
import org.ladbury.devicePkg.Device.*;
import org.ladbury.readingEventPkg.ReadingEvent;

public class TestDataGenerator {
	//private ConsumptionPattern[] patterns;
	//private CatalogueEntry[] catalog;
	//private Device[] devices;
	
	private Abode home;
	
	private Room bathroom;
	private Room kitchen;
	private Room lounge;
	private Room study;
	private Room bedroom1;
	private Room bedroom2;
	private Room hallway;
	
	private ConsumptionPattern thermostat;
	private ConsumptionPattern manual;
	private ConsumptionPattern dimmable;
	
	private CatalogueEntry philipslightbulb;
	private CatalogueEntry arctisfreezer;
	private CatalogueEntry uFHeater;
	
	private Device light11w;
	private Device light14w;
	private Device light60w;
	private Device light100w;
	private Device lightCluster150w;
	private Device lightCluster150wDimmable;
	private Device underfloorHeating;
	private Device freezer;
	
	private ReadingEvent re1,re2;
	private DeviceActivity da1;
	
public TestDataGenerator(){
	//patterns = new ConsumptionPattern[3];
}
public void definePatterns(EntityManager em){
	thermostat = new ConsumptionPattern(ConsumptionType.CYCLIC,1,3600,1,3600); //thermostat
	manual = new ConsumptionPattern();
	dimmable = new ConsumptionPattern(ConsumptionType.VARIABLE,-1,0,-1,0);
	em.persist(thermostat);
	em.persist(manual);
	em.persist(dimmable);
	
}
public void defineCatalogue(EntityManager em){
	philipslightbulb = new CatalogueEntry("Philps", "lightbulb","v2");
	uFHeater = new CatalogueEntry("unknown","wallmount","v1");
	arctisfreezer = new CatalogueEntry("AEG","Arctis","v1");
	em.persist(philipslightbulb);
	em.persist(uFHeater);
	em.persist(arctisfreezer);

}
public void defineDevices(EntityManager em){
	light11w = new Device("Energy saving lightbulb",DeviceType.LIGHT,11,manual,philipslightbulb);
	light14w = new Device("Energy saving lightbulb",DeviceType.LIGHT,14,manual,philipslightbulb);
	light60w = new Device("lightbulb",DeviceType.LIGHT,60,manual,philipslightbulb);
	light100w = new Device("lightbulb",DeviceType.LIGHT,100,manual,philipslightbulb);
	lightCluster150w = new Device("light cluster",DeviceType.LIGHT,150,manual,philipslightbulb);
	lightCluster150wDimmable = new Device("light cluster",DeviceType.LIGHT,150,dimmable,philipslightbulb);
	
	underfloorHeating = new Device("Bathroom floor",DeviceType.HEATER,1000,thermostat,uFHeater);
	freezer = new Device("Freezer",DeviceType.MOTOR,120,thermostat,arctisfreezer);
	em.persist(light11w);
	em.persist(light14w);
	em.persist(light60w);
	em.persist(light100w);
	em.persist(lightCluster150w);
	em.persist(lightCluster150wDimmable);
	em.persist(underfloorHeating);
	em.persist(freezer);

}
public void defineAbode(EntityManager em){
	home = new Abode("7 Kestrel Walk", "7kestrel",AbodeType.HOUSE);
	em.persist(home);
}
public void defineRooms(EntityManager em){
	bathroom = new Room("Bathroom",RoomType.BATHROOM);
	kitchen = new Room("Kitchen",RoomType.KITCHEN);
	lounge = new Room("Lounge",RoomType.LIVING_ROOM);
	study = new Room("Study",RoomType.WORK_ROOM);
	bedroom1 = new Room("Master Bedroom",RoomType.BEDROOM);
	bedroom2 = new Room("Guest room",RoomType.BEDROOM);
	hallway = new Room("Hall stairs & landing",RoomType.TRANSIT_SPACE);
	home.addRoom(bathroom);
	home.addRoom(bedroom1);
	home.addRoom(bedroom2);
	home.addRoom(kitchen);
	home.addRoom(lounge);
	home.addRoom(study);
	home.addRoom(hallway);
	em.persist(bathroom);
	em.persist(kitchen);
	em.persist(lounge);
	em.persist(study);
	em.persist(bedroom1);
	em.persist(bedroom2);
	em.persist(hallway);
}

public void placeDevices(EntityManager em){
	/*
	light11w.setRoom(hallway);
	light14w.setRoom(bedroom2);
	light60w.setRoom(bathroom);
	light100w.setRoom(study);
	lightCluster150w.setRoom(kitchen);
	lightCluster150wDimmable.setRoom(lounge);
	underfloorHeating.setRoom(bathroom);
	freezer.setRoom(kitchen);*/
}
public void generateEvents(EntityManager em) {
	re1= new ReadingEvent(new Timestamp(100000),150);
	re2= new ReadingEvent(new Timestamp(200000),-150);
	em.persist(re1);
	em.persist(re2);
}

public void generateActivity(EntityManager em){
	da1 = new DeviceActivity(re1.timestamp(),re2.timestamp(),15,null);
	em.persist(da1);
}
}
