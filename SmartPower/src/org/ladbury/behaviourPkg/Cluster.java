package org.ladbury.behaviourPkg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.ladbury.devicePkg.Device;
import org.ladbury.persistentData.Persistable;
import org.ladbury.userInterfacePkg.UiStyle;
import javax.persistence.JoinColumn;

@Entity
public class Cluster implements	Serializable,
								Persistable<Cluster>, 
								Comparable<Cluster>,
								Comparator<Cluster>{

	private static final long serialVersionUID = -4397310656052725484L;
	public static enum ClusterType {UNKNOWN,ROOM,HABIT};
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToMany(mappedBy = "cluster")
	private List<Habit> habits;
	@Basic
	private String name;
	@Basic
	private ClusterType type;
	@OneToMany
	@JoinColumn(name = "Cluster_id", referencedColumnName = "id")
	private List<Device> devices;
	public Cluster(){
		this.name = UiStyle.UNNAMED;
		this.type = ClusterType.UNKNOWN;
		this.devices = new ArrayList<Device>(Collections.<Device>emptyList());;
		this.habits = new ArrayList<Habit>(Collections.<Habit>emptyList());;
	}
	public long id() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String param) {
		this.name = param;
	}

	public ClusterType getType() {
		return type;
	}
	public void setType(ClusterType type) {
		this.type = type;
	}
	//
	//access methods for referenced habits
	//
	public void clearHabits(){
		this.habits = new ArrayList<Habit>(Collections.<Habit>emptyList());;	
	}
	public int habitCount(){
		return habits.size();
	}
	public void addHabit(Habit h){
		if (habits.indexOf(h) == -1) habits.add(h);
	}
	public void removeHabit(Habit h){
		habits.remove(h);
	}
	public Habit getHabit(int i){
		if(i>=0 && i<habits.size()) return this.habits.get(i);
		return null;
	}
	public List<Habit> getHabits() {
		return habits;
	}
/*
	public void setHabits(List<Habit> param) {
		this.habits = param;
	}*/
	
	//
	//access methods for referenced Devices
	//
	public void clearDevices(){
		this.devices = new ArrayList<Device>(Collections.<Device>emptyList());;
	}
	public int deviceCount(){
		return devices.size();
	}
	public void addDevice(Device d){
		if (devices.indexOf(d) == -1) devices.add(d);
	}
	public void removeDevice(Device d){
		devices.remove(d);
	}
	public Device getDevice(int i){
		if(i>=0 && i<devices.size()) return this.devices.get(i);
		return null;
	}
	public List<Device> getDevices() {
	    return devices;
	}
/*	
	public void setDevices(List<Device> param) {
	    this.devices = param;
	}*/
	
	//
	// Methods to support Interfaces
	//
	@Override
	public String name() {
		return name;
	}
	@Override
	public int compare(Cluster c1, Cluster c2) {
		return c1.compareTo(c2);
	}
	@Override
	public int compareTo(Cluster c) {
		return this.name.compareTo(c.name);
	}
	@Override
	public String toCSV() {
		return 	id+","+
				name+","+
				type+","+
				habits.size()+","+
				devices.size();
	}
	@Override
	public void updateFields(Cluster element) {
		this.habits = element.habits;
		this.devices =  element.devices;
		this.name = element.name;
		this.type = element.type;
	}
	@Override
	public String idString() {
		return  "["+this.id()+"] "+this.name();
	}
}