package org.ladbury.devicePkg;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.ladbury.cataloguePkg.CatalogueEntry;
import org.ladbury.consumptionPatternPkg.ConsumptionPattern;
import org.ladbury.deviceActivityPkg.DeviceActivity;
import org.ladbury.persistentData.Persistable;
import org.ladbury.userInterfacePkg.UiStyle;

@NamedQuery(name = "findDeviceswithPattern", query = "SELECT d FROM Device d WHERE d.pattern = :sig")
@Entity
public class Device implements 	Serializable,
								Comparable<Device>,
								Comparator<Device>,
								Persistable<Device>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8378607274521362291L;

	public static enum DeviceType {UNKNOWN, COMPOUND, LIGHT, HEATER, MOTOR, ELECTRONICS};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long deviceId;
	private String name;
	private DeviceType type;
	private int consumption; // in watts
	@ManyToOne
	@JoinColumn(name = "PATTERN_ID")
	private ConsumptionPattern pattern;
	@ManyToOne
	@JoinColumn(name = "CATALOGUE_ID")
	private CatalogueEntry catalogueEntry;
	@OneToMany(mappedBy = "device")
	private List<DeviceActivity> deviceActivities;
	@Basic
	private boolean portable;
	@ManyToOne
	private Device componentOf;
	@OneToMany(mappedBy = "componentOf")
	private List<Device> subComponents;
	//
	//  Constructors
	//
	public Device() {
		this.deviceActivities = new ArrayList<DeviceActivity>(Collections.<DeviceActivity>emptyList());
		this.subComponents = new ArrayList<Device>(Collections.<Device>emptyList());
		this.componentOf = null;
		this.name = null;
		this.type = DeviceType.UNKNOWN;
		this.consumption = 0;
		this.pattern = null;
		this.catalogueEntry = null;
		this.portable = false;
	}

	public Device(String deviceName, DeviceType deviceType,
			int consumption, ConsumptionPattern pattern,
			CatalogueEntry catalogueEntry) {
		this(); //initialise to null values by calling Device()
		this.name = deviceName;
		this.type = deviceType;
		this.consumption = consumption;
		this.pattern = pattern;
		this.catalogueEntry = catalogueEntry;
	}
	//
	//  Access Methods for simple fields
	//
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public void setName(String deviceName) {
		this.name = deviceName;
	}

	public DeviceType getType() {
		if (type == null)
			return DeviceType.UNKNOWN;
		else
			return type;
	}

	public void setType(DeviceType deviceType) {
		this.type = deviceType;
	}

	public int getConsumption() {
		return consumption;
	}

	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}
	public void setPortable(boolean param) {
		this.portable = param;
	}

	public boolean isPortable() {
		return portable;
	}

	public ConsumptionPattern getPattern() {
		return pattern;
	}

	public void setPattern(ConsumptionPattern param) {
		//handle the bidirectional link to ConsumptionPattern
		if (this.pattern != null){
			if (param==null){ // remove old reference
				this.pattern.getDevices().remove(this);			
			}else{ //change references
				if (this.pattern.id()!=param.id()){
					this.pattern.getDevices().remove(this);
					param.getDevices().add(this);
				}
			}	
		} else{
			if (param!=null){ //add new reference			
				param.getDevices().add(this);
			}
		}
		this.pattern = param;
	}

	public CatalogueEntry getCatalogueEntry() {
		return catalogueEntry;
	}

	public void setCatalogueEntry(CatalogueEntry param) {
		//handle the bidirectional link to CatalogueEntry
		if (this.catalogueEntry != null){
			if (param==null){ // remove old reference
				this.catalogueEntry.getDevices().remove(this);			
			}else{ //change references
				if (this.catalogueEntry.id()!=param.id()){
					this.catalogueEntry.getDevices().remove(this);
					param.getDevices().add(this);
				}
			}	
		} else{
			if (param!=null){ //add new reference			
				param.getDevices().add(this);
			}
		}
		this.catalogueEntry = param;
	}

	public Device getComponentOf() {
	    return componentOf;
	}

	public void setComponentOf(Device param) {
		// handle the bidirectional link to parent device
		if (this.componentOf != null){
			if (param==null){ // remove old reference
				this.componentOf.subComponents.remove(this);			
			}else{ //change references
				if(this.id()==param.id()) return; // self reference not allowed
				if (this.componentOf.id()!=param.id()){
					this.componentOf.subComponents.remove(this);
					param.subComponents.add(this);
				}
			}	
		} else{
			if (param!=null){ //add new reference			
				if(this.id()==param.id()) return; // self reference not allowed
				param.subComponents.add(this);
			}
		}
		this.componentOf = param;
	}
	
	//
	// Access Methods for multiply referenced Entities
	//
	
	// Components
	public int componentCount(){
		return subComponents.size();
	}
	public Device getComponent(int i){
		return subComponents.get(i);
	}
	// Activity
	public int activityCount() {
		return deviceActivities.size();
	}
	
	public DeviceActivity getActivity(int i){
		return this.deviceActivities.get(i);
	}
	public List<DeviceActivity> getDeviceActivities() {
		return deviceActivities;
	}

	public void setDeviceActivities(List<DeviceActivity> param) {
		this.deviceActivities = param;
	}

	public List<Device> getSubComponents() {
	    return subComponents;
	}

	public void setSubComponents(List<Device> param) {
	    this.subComponents = param;
	}

	//
	// Methods supporting interfaces
	//
	@Override
	public String name() {
		if (name == null)
			return UiStyle.UNNAMED;
		else
			return name;
	}

	@Override
	public int compare(Device d1, Device d2) {
		return d1.compareTo(d2);
	}

	@Override
	public int compareTo(Device d) {
		return this.name.compareTo(d.name);
	}

	@Override
	public void updateFields(Device d) {
		this.deviceActivities = d.deviceActivities;
		this.subComponents = d.subComponents;
		this.name = d.name;
		this.type = d.type;
		this.consumption= d.consumption; 
		this.setPattern(d.pattern); //handle the bidirectional link to ConsumptionPattern
		this.setCatalogueEntry(d.catalogueEntry);//handle the bidirectional link to CatalogueEntry
		this.portable = d.portable;
		this.setComponentOf(d.componentOf); // handle the bidirectional link to parent device
	}

	@Override
	public long id() {
		return 	this.deviceId;
	}
	
	@Override
	public String toCSV(){
		return( this.deviceId+","
				+this.name+","
				+this.type+","
				+(this.componentOf==null?"":this.componentOf.deviceId)+","
				+(this.catalogueEntry==null?"":this.catalogueEntry.id())+","
				+this.deviceActivities.size()+","
				+this.portable);
	}
	@Override
	public String idString(){
		return new String("["+this.id()+"] "+this.name());
	}

}
