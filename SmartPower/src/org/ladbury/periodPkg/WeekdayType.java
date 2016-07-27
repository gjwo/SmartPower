package org.ladbury.periodPkg;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.ladbury.persistentData.Persistable;

@Entity
public class WeekdayType implements Serializable,
									Persistable <WeekdayType>,
									Comparable<WeekdayType>,
									Comparator<WeekdayType> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6230207248785726352L;

	public static enum DayCategory {ANYDAY, WORKINGDAY, RESTDAY};
	public static enum DayOfWeek {MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY};
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Basic
	private DayOfWeek weekday;
	@Basic
	private DayCategory dayCategory;
	
	public void setId(long id) {
		this.id = id;
	}

	public void setWeekday(DayOfWeek param) {
		this.weekday = param;
	}

	public DayOfWeek getWeekday() {
		return weekday;
	}

	public void setType(DayCategory param) {
		this.dayCategory = param;
	}

	public DayCategory getType() {
		return dayCategory;
	}
	//
	// methods to implement Persistable
	//
	@Override
	public long id() {
		return id;
	}

	@Override
	public String name() {
		return dayCategory+"-"+weekday;
	}

	@Override
	public String toCSV() {
		return weekday+","+dayCategory;
	}

	@Override
	public void updateFields(WeekdayType element) {
		this.dayCategory = element.dayCategory;
		this.weekday = element.weekday;	
	}
	@Override
	public String idString() {
		return "["+this.id()+"] "+this.name();
	}
	//
	// methods to implement Comparable & Comparator
	//
	public int compareTo(WeekdayType wt) {
		return this.weekday.compareTo(wt.weekday);
	}

	public  int compare(WeekdayType wt1, WeekdayType wt2) {
		return wt1.compareTo(wt2);
	}

}