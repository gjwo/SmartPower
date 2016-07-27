package org.ladbury.periodPkg;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.ladbury.behaviourPkg.Habit;
import org.ladbury.periodPkg.WeekdayType.DayCategory;
import org.ladbury.persistentData.Persistable;
import org.ladbury.userInterfacePkg.UiStyle;

import java.util.List;
import javax.persistence.OneToMany;

@Entity
public class TimePeriod implements 	Serializable,
									Persistable <TimePeriod>,
									Comparable<TimePeriod>,
									Comparator<TimePeriod> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8506302524409789058L;

	public static enum PeriodType {UNDEFINED, ACTIVITY, HABIT, READINGS, MATCHED};
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	@Basic
	private String name;
	@Basic
	private PeriodType type;
	@Basic
	private Time startDate;
	@Basic
	private Time endDate;
	@Basic
	private boolean usage;
	@Basic
	private DayCategory dayCategory;
	@OneToMany(mappedBy = "timePeriod")
	private List<Habit> habits;

	public TimePeriod(){
		this.name = UiStyle.UNNAMED;
		this.type = PeriodType.UNDEFINED;
		this.startDate = new Time(0);
		this.endDate = new Time(0);
		this.usage = true;
		this.dayCategory = DayCategory.ANYDAY;
		this.habits = new ArrayList<Habit>(Collections.<Habit>emptyList());
	}
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String param) {
		this.name = param;
	}

	public void setStartTime(Time param) {
		this.startDate = param;
	}

	public void setStartTime(Long param) {
		this.startDate = new Time(param);
	}

	public Time startTime() {
		return startDate;
	}

	public void setEndTime(Time param) {
		this.endDate = param;
	}
	public void setEndTime(Long param) {
		this.endDate = new Time(param);
	}

	public Time endTime() {
		return endDate;
	}

	public void setUsage(boolean param) {
		this.usage = param;
	}

	public boolean used() {
		return usage;
	}

	public DayCategory getDayCategory() {
		return dayCategory;
	}

	public void setDayCategory(DayCategory param) {
		this.dayCategory = param;
	}

	public void setType(PeriodType param) {
		this.type = param;
	}

	public PeriodType getType() {
		return type;
	}
	public boolean contains(Time t){
		//Time timeOnly;
		if (t==null || startDate==null || endDate==null) return false;
		//timeOnly = (Time)t.clone();
		if (t.before(startDate))return false;
		if (t.after(endDate))return false;
		return true;
	}

	///
	//Methods to access habits
	//
	public int getHabitCount(){
		return habits.size();
	}
	public Habit getHabit(int i){
		return habits.get(i);
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
		return name;
	}

	@Override
	public String toCSV() {
		return 	 id +","+
				name +","+
				type +","+
				startDate +","+
				endDate +","+
				usage +","+
				dayCategory;
	}

	@Override
	public void updateFields(TimePeriod element) {
		this.name = element.name;
		this.startDate = element.startDate;
		this.endDate = element.endDate;
		this.usage = element.usage;
		this.dayCategory= element.dayCategory;
		this.type = element.type;
	}
	@Override
	public String idString() {
		return "["+this.id()+"] "+this.name();
	}
	//
	// methods to implement Comparable & Comparator
	//
	public int compareTo(TimePeriod p) {
		if( this.startDate.compareTo(p.startDate)!=0) return this.startDate.compareTo(p.startDate);
		return this.endDate.compareTo(p.endDate);
	}

	public  int compare(TimePeriod p1, TimePeriod p2) {
		return p1.compareTo(p2);
	}

	public List<Habit> getHabits() {
	    return habits;
	}

	public void setHabits(List<Habit> param) {
	    this.habits = param;
	}
}