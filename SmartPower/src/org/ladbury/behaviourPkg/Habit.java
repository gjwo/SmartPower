package org.ladbury.behaviourPkg;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.ladbury.periodPkg.TimePeriod;
import org.ladbury.persistentData.Persistable;
import org.ladbury.userInterfacePkg.UiStyle;

@Entity
public class Habit implements	Serializable,
								Persistable<Habit>, 
								Comparable<Habit>,
								Comparator<Habit>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2889924724792316010L;
	public static enum HabitType {UNKNOWN,MANUAL,AUTOMATIC};
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Basic
	private String name;
	@Basic
	private HabitType type;
	@ManyToOne
	private TimePeriod timePeriod;
	@ManyToOne
	private Cluster cluster;
	
	public Habit(){
		this.name = UiStyle.UNNAMED;
		this.type = HabitType.UNKNOWN;
		this.timePeriod = null;
		this.cluster = null;
	}
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String param) {
		this.name = param;
	}
	@Override
	public String name() {
		return name;
	}

	public void setType(HabitType param) {
		this.type = param;
	}

	public HabitType getType() {
		return type;
	}

	public TimePeriod getTimePeriod() {
	    return timePeriod;
	}

	public void setTimePeriod(TimePeriod param) {
		//handle the bidirectional link to TimePeriod
		if (this.timePeriod != null){
			if (param==null){ // remove old reference
				this.timePeriod.getHabits().remove(this);			
			}else{ //change references
				if (this.timePeriod.id()!=param.id()){
					this.timePeriod.getHabits().remove(this);
					param.getHabits().add(this);
				}
			}	
		} else{
			if (param!=null){ //add new reference			
				param.getHabits().add(this);
			}
		}
	    this.timePeriod = param;
	}

	public Cluster getCluster() {
	    return cluster;
	}

	public void setCluster(Cluster param) {
		//handle the bidirectional link to Cluster
		if (this.cluster != null){
			if (param==null){ // remove old reference
				this.cluster.getHabits().remove(this);			
			}else{ //change references
				if (this.cluster.id()!=param.id()){
					this.cluster.getHabits().remove(this);
					param.getHabits().add(this);
				}
			}	
		} else{
			if (param!=null){ //add new reference			
				param.getHabits().add(this);
			}
		}
		this.cluster = param;
	}
	@Override
	public int compare(Habit arg0, Habit arg1) {
		return arg0.compareTo(arg1);
	}
	@Override
	public int compareTo(Habit h) {
		return this.name.compareTo(h.name);
	}
	@Override
	public long id() {
		return this.id;
	}
	@Override
	public String toCSV() {
		return 	id+","+
				name+","+
				type+","+
				timePeriod.name()+","+
				cluster.name();
	
	}
	@Override
	public void updateFields(Habit element) {
		name = element.name;
		type = element.type;
		setTimePeriod(element.timePeriod);
		setCluster(element.cluster);
	}
	@Override
	public String idString() {
		return  "["+this.id()+"] "+this.name();
	}
}