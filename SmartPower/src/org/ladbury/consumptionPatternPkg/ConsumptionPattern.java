package org.ladbury.consumptionPatternPkg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.ladbury.devicePkg.Device;
import org.ladbury.persistentData.Persistable;

@Entity
public class ConsumptionPattern implements 	Serializable,
											Comparator<ConsumptionPattern>,
											Comparable<ConsumptionPattern>,
											Persistable<ConsumptionPattern>{

	private static final long serialVersionUID = -8378607274521362294L;	
public enum ConsumptionType {	CONSTANT, // manual on or off
								CYCLIC, // timed or thermostatic control 
								SPORADIC, // on or of but periods vary randomly within ranges
								VARIABLE} // variable power consumption
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private long 			consumptionId;
private ConsumptionType type;
private int 			onTime; //minutes
private	int 			onSpread; //+-minutes
private int 			offTime; //minutes
private int 			offSpread; //+-minutes
// consider a curve function for the variable profile
@OneToMany(targetEntity=Device.class, mappedBy="pattern",cascade=CascadeType.ALL)
private List <Device> devices;

public ConsumptionPattern() {
	super();
	this.devices = new ArrayList<Device>(Collections.<Device>emptyList());
	this.type = ConsumptionType.CONSTANT; 
	this.onTime = -1;
	this.onSpread = 0;
	this.offTime = -1;
	this.offSpread= 0;		
}

public ConsumptionPattern(ConsumptionType consumptionType, int onTime,
		int onSpread, int offTime, int offSpread) {
	super();
	this.devices = new ArrayList<Device>(Collections.<Device>emptyList());
	this.type = consumptionType;
	this.onTime = onTime;
	this.onSpread = onSpread;
	this.offTime = offTime;
	this.offSpread = offSpread;
}

//
// access methods
//
public ConsumptionType getType() {
	return type;
}
public void setType(ConsumptionType consumptionType) {
	this.type = consumptionType;
}
public int getOnTime() {
	return onTime;
}
public void setOnTime(Long onTime) {
	this.onTime = onTime.intValue();
}
public int getOnSpread() {
	return onSpread;
}
public void setOnSpread(int onSpread) {
	this.onSpread = onSpread;
}
public int getOffTime() {
	return offTime;
}
public void setOffTime(Long offTime) {
	this.offTime = offTime.intValue();
}
public int getOffSpread() {
	return offSpread;
}
public void setOffSpread(int offSpread) {
	this.offSpread = offSpread;
}

//
//access methods for referenced Devices
//
public List<Device> getWithPattern() {
	return devices;
}

public void setWithPattern(List<Device> withPattern) {
	this.devices = withPattern;
}
public Device getDevice(int i){
	return devices.get(i);
}
public List<Device> getDevices(){
	return devices;
}
public void  setDevices(List<Device> d){
	devices = d;
}
public int deviceCount(){
	return devices.size();
}//
// Methods implementing interfaces
//
@Override
public int compareTo(ConsumptionPattern cp) {
	return this.type.compareTo(cp.type);
}

@Override
public int compare(ConsumptionPattern cp1, ConsumptionPattern cp2) {
	return cp1.compareTo(cp2);
}

@Override
public void updateFields(ConsumptionPattern cp) {
	this.devices = cp.devices;
	this.type = cp.type; 
	this.onTime = cp.onTime;
	this.onSpread = cp.onSpread;
	this.offTime = cp.offTime;
	this.offSpread= cp.offSpread;
}

@Override
public long id() {
	return consumptionId;
}
@Override
public String toCSV(){
	return(  this.consumptionId+","
			+this.type+","
			+this.onTime+","
			+this.onSpread+","
			+this.offTime+","
			+this.offSpread+","
			+this.devices.size());
}

@Override
public String name() {
	return(  this.onTime+"-"
			+this.type);
}
@Override
public String idString(){
	return new String("["+this.id()+"] "+this.name());
}

}
