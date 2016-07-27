package org.ladbury.cataloguePkg;

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
import org.ladbury.userInterfacePkg.UiStyle;
import org.ladbury.cataloguePkg.Make;
import javax.persistence.ManyToOne;

@Entity
public class CatalogueEntry implements	Serializable,
										Persistable<CatalogueEntry>,
										Comparator<CatalogueEntry>,
										Comparable<CatalogueEntry>{
	
	private static final long serialVersionUID = -8378607274521362292L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long catalogueId;
	private String name;
	private String model;
	private String description;
	@OneToMany(targetEntity=Device.class, mappedBy="catalogueEntry",cascade=CascadeType.ALL)
	private List <Device> devices;
	@ManyToOne
	private Category category;
	@ManyToOne
	private Make make;
	
	//
	// Constructors
	//
	public CatalogueEntry() {
		super();
		this.name = UiStyle.UNNAMED;
		this.model = UiStyle.EMPTY;
		this.description = UiStyle.EMPTY;
		this.category = null;
		this.make = null;
		this.devices = new ArrayList<Device>(Collections.<Device>emptyList());
	}

	public CatalogueEntry(String name, String model, String description) {
		this();
		this.name = name;
		this.model = model;
		this.description = description;
	}
	
	//
	// support routines
	//
	public String toCSV(){
		return( this.catalogueId+","
				+this.name+","
				+this.model+","
				+this.description+","
				+this.devices.size());
	}
	//
	// access methods for embedded devices
	//
	public int deviceCount(){
		return devices.size();
	}
	public Device getDevice(int i){
		return this.devices.get(i);
	}
	//
	// access methods
	//

	public void setCatalogueId(long catalogueId) {
		this.catalogueId = catalogueId;
	}

	public String name() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
//	public String name(){
//		return this.name+"-"+this.model;
//	}

	public int compareTo(CatalogueEntry  ce) {
		if (this.name.compareTo(ce.name) != 0) return this.name.compareTo(ce.name);
		if (this.model.compareTo(ce.model) != 0) return this.model.compareTo(ce.model);
		return this.description.compareTo(ce.description);
	}


	public  int compare(CatalogueEntry ce1, CatalogueEntry ce2) {
		return ce1.compareTo(ce2);
	}

	@Override
	public  void updateFields(CatalogueEntry ce) {

		this.name = ce.name;
		this.model = ce.model;
		this.description = ce.description;
		//devices?
	}

	@Override
	public long id() {
		return catalogueId;
	}
	@Override
	public String idString(){
		return new String("["+this.id()+"] "+this.name());
	}

	public Category getCategory() {
	    return category;
	}

	public void setCategory(Category param) {
	    this.category = param;
	}

	public Make getMake() {
	    return make;
	}

	public void setMake(Make param) {
	    this.make = param;
	}
}