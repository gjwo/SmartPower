package org.ladbury.persistentData;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.ladbury.abodePkg.Abode;
import org.ladbury.abodePkg.Room;
import org.ladbury.behaviourPkg.Cluster;
import org.ladbury.behaviourPkg.Habit;
import org.ladbury.cataloguePkg.CatalogueEntry;
import org.ladbury.cataloguePkg.Category;
import org.ladbury.cataloguePkg.Make;
import org.ladbury.consumptionPatternPkg.ConsumptionPattern;
import org.ladbury.deviceActivityPkg.DeviceActivity;
import org.ladbury.devicePkg.Device;
import org.ladbury.meterPkg.Meter;
import org.ladbury.meterPkg.Metric;
import org.ladbury.meterPkg.TimedRecord;
import org.ladbury.periodPkg.CalendarPeriod;
import org.ladbury.periodPkg.TimePeriod;
import org.ladbury.periodPkg.WeekdayType;
import org.ladbury.readingEventPkg.ReadingEvents;

/**
 * This is a class to act as the store of the persistent data for
 * the package, and ensure all such data and it's local copy
 * is accessed using a common persistence mechanism such as
 * PersistentList
 * 
 * @author GJWood
 * @version 3.0
 * @see PersistentLink
 */
public class PersistentData {
	public static enum EntityType{	UNDEFINED, DEVICE, PARENT_DEVICE, READINGS, EVENTS, //phase1
									ACTIVITY, CATALOGUE, PATTERN, ABODE, ROOM,			//phase1
									MAKE, CATEGORY, WEEKDAYTYPE, TIMEPERIOD, CALENDARPERIOD, HABIT, CLUSTER, //phase2
									METER,METRIC,TIMEDRECORD}; //phase 3

	private EntityManagerFactory factory;
	private	EntityManager entityManager;
	private	Query query;

	private ReadingEvents readingEvents; // subclass of PersitentList with additional summary fields & methods
	private	PersistentList <Device> devices;
	private	PersistentList <CatalogueEntry> catalogue;
	private	PersistentList <ConsumptionPattern> patterns;
	private	PersistentList <Abode> abodes;
	private PersistentList <Room> rooms;
	private PersistentList <DeviceActivity> activity;
	private PersistentList <Make> makes;
	private PersistentList <Category> categories;
	private PersistentList <WeekdayType> weekdayTypes;
	private PersistentList <TimePeriod> timePeriods;
	private PersistentList <CalendarPeriod> calendarPeriods;
	private PersistentList <Habit> habits;
	private PersistentList <Cluster> clusters;
	private PersistentList <Meter> meters;
	private PersistentList <Metric> metrics;
	private PersistentList <TimedRecord> timedRecords;
	

	public PersistentData(){	
		// set up mechanism for persistence
		factory = Persistence.createEntityManagerFactory("devices");
		entityManager = factory.createEntityManager();
	}
	
	
	@SuppressWarnings("unchecked")
	public void loadPersistentData(){
		query = entityManager.createQuery("SELECT d FROM Device d");
		devices = new PersistentList<Device>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT ce FROM CatalogueEntry ce");
		catalogue = new PersistentList<CatalogueEntry>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT cp FROM ConsumptionPattern cp");
		patterns = new PersistentList<ConsumptionPattern>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT a FROM Abode a");
		abodes = new PersistentList<Abode>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT r FROM Room r");
		rooms = new PersistentList<Room>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT a FROM DeviceActivity a");
		activity = new PersistentList<DeviceActivity>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT re FROM ReadingEvent re");
		readingEvents = new ReadingEvents(query.getResultList()); //load persistent objects, initialise & sort
		query = entityManager.createQuery("SELECT m FROM Make m");
		makes = new PersistentList<Make>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT c FROM Category c");
		categories = new PersistentList<Category>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT w FROM WeekdayType w");
		weekdayTypes = new PersistentList<WeekdayType>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT t FROM TimePeriod t");
		timePeriods = new PersistentList<TimePeriod>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT c FROM CalendarPeriod c");
		calendarPeriods = new PersistentList<CalendarPeriod>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT h FROM Habit h");
		habits = new PersistentList<Habit>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT c FROM Cluster c");
		clusters = new PersistentList<Cluster>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT m FROM Meter m");
		meters = new PersistentList<Meter>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT m FROM Metric m");
		metrics = new PersistentList<Metric>(query.getResultList()); //"unchecked" warning suppressed
		query = entityManager.createQuery("SELECT tr FROM TimedRecord tr");
		timedRecords = new PersistentList<TimedRecord>(query.getResultList()); //"unchecked" warning suppressed
	}
	
	//
	// Generic access methods for persistent objects
	//
	@SuppressWarnings("rawtypes")
	public PersistentList getDataList(EntityType type){
		switch (type) {
			case DEVICE: return devices;
			case PARENT_DEVICE: return devices;
			case EVENTS: return readingEvents;
			case ACTIVITY: return activity;
			case CATALOGUE: return catalogue;
			case PATTERN: return patterns;
			case ABODE:	return abodes;
			case ROOM: return rooms;
			case MAKE: return makes;
			case CATEGORY: return categories;
			case WEEKDAYTYPE: return weekdayTypes;
			case TIMEPERIOD: return timePeriods;
			case CALENDARPERIOD: return calendarPeriods;
			case HABIT: return habits;
			case CLUSTER: return clusters;
			case METER: return meters;
			case METRIC: return metrics;
			case TIMEDRECORD: return timedRecords; 
			default: return null;
		}
	}
	
	//
	// Access methods for individual persistent objects
	//

	public ReadingEvents getReadingEvents() {return this.readingEvents;}

	public PersistentList<Device> getDevices(){return devices;}

	public PersistentList<CatalogueEntry> getCatalogue() {return catalogue;}

	public PersistentList<ConsumptionPattern> getConsumptionPatterns(){return patterns;}

	public PersistentList<Abode> getAbodes(){return abodes;}

	public PersistentList<DeviceActivity> getActivity(){return activity;}

	public PersistentList<Room> getRooms(){return rooms;}

	public PersistentList<Make> getMakes(){return makes;}

	public PersistentList<Category> getCategories(){return categories;}

	public PersistentList<WeekdayType> getWeekdayTypes(){return weekdayTypes;}

	public PersistentList<TimePeriod> getTimePeriods(){return timePeriods;}

	public PersistentList<CalendarPeriod> getCalendarPeriods(){return calendarPeriods;}

	public PersistentList<Habit> getHabits(){return habits;}

	public PersistentList<Cluster> getClusters(){return clusters;}

	public PersistentList<Meter> getMeters(){return meters;}

	public PersistentList<Metric> getMetrics(){return this.metrics;}

	public PersistentList<TimedRecord> getTimedRecords(){return timedRecords;}
	
	public EntityManager getEntityManager() {return entityManager;}
}
