package org.ladbury.periodPkg;

import java.io.Serializable;
import java.sql.Date;
import java.util.Comparator;

import javax.persistence.*;

import org.ladbury.periodPkg.TimePeriod.PeriodType;
import org.ladbury.persistentData.Persistable;
import org.ladbury.userInterfacePkg.UiStyle;

@Entity
public class CalendarPeriod	implements Serializable,
										Persistable <CalendarPeriod>,
										Comparable<CalendarPeriod>,
										Comparator<CalendarPeriod>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9138360024558441917L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Basic
	private String name;
	@Basic
	private PeriodType type;
	@Basic
	private Date startDate;
	@Basic
	private Date endDate;

	//
	// Constructors
	//
	
	public CalendarPeriod(){
		this.name = UiStyle.UNNAMED;
		this.type = PeriodType.UNDEFINED;
		this.startDate = new Date(0);
		this.endDate = new Date(0);

	}
	//
	// Access methods
	//
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String param) {
		this.name = param;
	}

	public void setType(PeriodType param) {
		this.type = param;
	}

	public PeriodType getType() {
		return type;
	}

	public void setStart(Date param) {
		this.startDate = param;
	}
	public void setStart(long param) {
		this.startDate = new Date(param);
	}

	public Date start() {
		return startDate;
	}

	public void setEnd(long param) {
		this.endDate = new Date(param);
	}
	public void setEnd(Date param) {
		this.endDate = param;
	}

	public Date end() {
		return endDate;
	}
	public boolean contains(Date d){
		if (d==null || startDate==null || endDate==null) return false;
		if (d.before(startDate))return false;
		if (d.after(endDate))return false;
		return true;
	}
	//
	// Methods to implement interfaces
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
	public int compare(CalendarPeriod arg0, CalendarPeriod arg1) {
		return arg0.compareTo(arg1);
	}

	@Override
	public int compareTo(CalendarPeriod arg0) {
		if (this.startDate.compareTo(arg0.startDate) == 0){
			return this.endDate.compareTo(arg0.endDate);
		} else return this.startDate.compareTo(arg0.startDate);
	}

	@Override
	public String toCSV() {
		return 	id +","+
				name +","+
				type +","+
				startDate +","+
				endDate;
	}

	@Override
	public void updateFields(CalendarPeriod element) {
		this.name = element.name;
		this.type = element.type;
		this.startDate = element.startDate;
		this.endDate = element.endDate;

	}

	@Override
	public String idString() {
		return "["+this.id()+"] "+this.name();
	}

}