package org.ladbury.periodPkg;

import javax.persistence.*;

import org.ladbury.persistentData.Persistable;
import org.ladbury.readingEventPkg.ReadingEvent;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import org.ladbury.deviceActivityPkg.DeviceActivity;
import org.ladbury.meterPkg.TimedRecord;

@Entity
public class Coincidence implements Serializable,
									Persistable <Coincidence>,
									Comparable<Coincidence>,
									Comparator<Coincidence>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8882368764977164154L;
	public static enum CoincidenceType {UNDEFINED, ACTIVITY, HABIT};
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private CoincidenceType type;
	@OneToMany
	@JoinColumn(name = "Entity1_id", referencedColumnName = "id")
	private List<ReadingEvent> readingEvent;
	@OneToOne
	private TimePeriod timePeriod;
	@OneToOne
	private CalendarPeriod calendarPeriod;
	@OneToOne
	private DeviceActivity deviceActivity;
	@OneToMany
	@JoinColumn(name = "Coincidence_id", referencedColumnName = "id")
	private List<TimedRecord> timedRecord;

	@Override
	public long id() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String name() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CoincidenceType getType() {
		return type;
	}

	public void setType(CoincidenceType type) {
		this.type = type;
	}

	public List<ReadingEvent> getReadingEvent() {
	    return readingEvent;
	}

	public void setReadingEvent(List<ReadingEvent> param) {
	    this.readingEvent = param;
	}

	public TimePeriod getTimePeriod() {
	    return timePeriod;
	}

	public void setTimePeriod(TimePeriod param) {
	    this.timePeriod = param;
	}

	public CalendarPeriod getCalendarPeriod() {
	    return calendarPeriod;
	}

	public void setCalendarPeriod(CalendarPeriod param) {
	    this.calendarPeriod = param;
	}

	public DeviceActivity getDeviceActivity() {
	    return deviceActivity;
	}

	public void setDeviceActivity(DeviceActivity param) {
	    this.deviceActivity = param;
	}

	@Override
	public int compare(Coincidence arg0, Coincidence arg1) {
		return arg0.compareTo(arg1);
	}

	@Override
	public int compareTo(Coincidence arg0) {
		if (arg0==null) return 1;
		if (this.calendarPeriod!=null){
			if (this.calendarPeriod.compareTo(arg0.calendarPeriod) == 0){
				return this.timePeriod.compareTo(arg0.timePeriod);
			}
		}
		return 0;
	}

	@Override
	public String toCSV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateFields(Coincidence element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String idString() {
		return "["+this.id()+"] "+this.name();
	}

	public List<TimedRecord> getTimedRecord() {
	    return timedRecord;
	}

	public void setTimedRecord(List<TimedRecord> param) {
	    this.timedRecord = param;
	}

}