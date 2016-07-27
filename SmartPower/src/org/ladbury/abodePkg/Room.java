package org.ladbury.abodePkg;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.ladbury.persistentData.Persistable;

import javax.persistence.ManyToOne;
import org.ladbury.behaviourPkg.Cluster;
import javax.persistence.OneToOne;

@Entity
public class Room implements	Serializable,
								Comparator<Room>,
								Comparable<Room>,
								Persistable<Room>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2982697621056344888L;

	public static enum RoomType {
		UNKNOWN, LIVING_ROOM, // sitting room, dining room, lounge etc
		BEDROOM, // place for sleeping
		KITCHEN, // any room where food is prepared
		BATHROOM, // any wet room
		OUTBUILDING, // any outside space, garage, greenhouse shed etc
		WORK_ROOM, // any workspace, office, workshop etc
		TRANSIT_SPACE
	}; // hallway, corridor, lobby etc

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roomId;
	@Basic
	private String name;
	@Basic
	private RoomType type;
	@ManyToOne
	private Abode abode;
	@OneToOne
	private Cluster cluster;
	//
	// Constructors
	//
	public Room() {
		super();
		this.name = null;
		this.type = RoomType.UNKNOWN;
		this.cluster = null;
	}

	public Room(String name, RoomType type) {
		super();
		this.name = name;
		this.type = type;
		this.cluster = null;
	}

	//
	// Support Methods
	//
	@Override
	public String toCSV(){
		return( this.roomId+","
				+this.name+","
				+this.type+","
				+this.cluster);
	}
	//
	// Access Methods
	//

	public void setID(long id) {
		this.roomId = id;
	}

	public void setName(String param) {
		this.name = param;
	}

	public void setType(RoomType param) {
		this.type = param;
	}

	public RoomType getType() {
		return type;
	}

	//
	// Methods Supporting Interfaces
	//
	@Override
	public int compareTo(Room r) {
		return this.name.compareTo(r.name);
	}

	@Override
	public int compare(Room r1, Room r2) {
		return r1.name.compareTo(r2.name);
	}

	@Override
	public void updateFields(Room r) {
		this.name = r.name;
		this.type = r.type;
		this.cluster = r.cluster;
		this.setAbode(r.abode);
	}

	@Override
	public long id() {
		return roomId;
	}
	
	@Override
	public String name() {
		return name;
	}
	@Override
	public String idString(){
		return new String("["+this.id()+"] "+this.name());
	}

	public Abode getAbode() {
	    return abode;
	}

	public void setAbode(Abode param) {
		//handle the bidirectional link to Cluster
		if (this.abode != null){
			if (param==null){ // remove old reference
				this.abode.getRooms().remove(this);			
			}else{ //change references
				if (this.abode.id()!=param.id()){
					this.abode.getRooms().remove(this);
					param.getRooms().add(this);
				}
			}	
		} else{
			if (param!=null){ //add new reference			
				param.getRooms().add(this);
			}
		}
		this.abode = param;
	}

	public Cluster getCluster() {
	    return cluster;
	}

	public void setCluster(Cluster param) {
	    this.cluster = param;
	}
}