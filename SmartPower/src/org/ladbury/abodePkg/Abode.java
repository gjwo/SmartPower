package org.ladbury.abodePkg;

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

import org.ladbury.persistentData.Persistable;
import org.ladbury.meterPkg.Meter;

@Entity
public class Abode implements 	Serializable,
								Persistable<Abode>, 
								Comparable<Abode>,
								Comparator<Abode>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 946826793962955657L;

	public static enum AbodeType {
		UNKNOWN, HOUSE, FLAT, BUNGALOW, MAISONETTE
	};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long abodeId;
	@Basic
	private String address;
	@Basic
	private AbodeType type;
	@OneToMany(mappedBy = "abode")
	private List<Room> rooms;
	@OneToMany(mappedBy = "abode")
	private List<Meter> meters;

	//
	// Constructors
	//
	public Abode() {
		super();
		this.rooms = new ArrayList<Room>(Collections.<Room>emptyList());
		this.type = AbodeType.UNKNOWN;
	}

	public Abode( String address, String monitorName,
			AbodeType type) {
		super();
		this.address = address;
		this.type = type;
		this.rooms = new ArrayList<Room>(Collections.<Room>emptyList());
		this.meters = new ArrayList<Meter>(Collections.<Meter>emptyList());
	}

	//
	// Access Methods for contained list of rooms
	//
	public int roomCount(){
		return this.rooms.size();
	}
	public void addRoom(Room r){
		this.rooms.add(r);
	}
	public Room getRoom(int i){
		return this.rooms.get(i);
	}
	public boolean removeRoom(Room r){
		return this.rooms.remove(r);
	}
	public boolean removeRoom(int r){
		return this.rooms.remove(r)!=null;
	}
	//
	// Access Methods for contained list of meters
	//
	public int meterCount(){
		return this.meters.size();
	}
	public void addMeter(Meter m){
		this.meters.add(m);
	}
	public Meter getmeter(int i){
		return this.meters.get(i);
	}
	public boolean removeMeter(Meter m){
		return this.meters.remove(m);
	}
	public boolean removeMeters(int m){
		return this.meters.remove(m)!=null;
	}

	public void updateMeter(int m, Meter param) {
	    this.meters.get(m).updateFields(param) ;
	}
	// Access Methods for summary variables
	//
	public long getAbodeId() {
		return abodeId;
	}

	public void setAbodeId(long id) {
		this.abodeId = id;
	}

	public void setAddress(String param) {
		this.address = param;
	}

	public String getAddress() {
		return address;
	}

	public List<Room> getRooms() {
		return rooms;
	}


	public void setType(AbodeType param) {
		this.type = param;
	}

	public AbodeType getType() {
		return type;
	}

	//
	// Interface support Methods
	//
	@Override
	public String toCSV(){
		return( this.abodeId+","
				+this.address+","
				+this.type+","
				+this.rooms.size()+","
				+this.meters.size());
	}
	
	@Override
	public void updateFields(Abode a) {
		this.address =a.address;
		this.type = a.type;
		this.rooms = a.rooms;
		this.meters = a.meters;
	}

	@Override
	public long id() {
		return abodeId;
	}
	
	@Override
	public int compareTo(Abode a) {
		return this.address.compareTo(a.address);
	}

	@Override
	public int compare(Abode a0, Abode a1) {
		return a0.compareTo(a1);
	}

	@Override
	public String name() {
		return this.address;
	}
	@Override
	public String idString(){
		return "["+this.id()+"] "+this.name();
	}
}