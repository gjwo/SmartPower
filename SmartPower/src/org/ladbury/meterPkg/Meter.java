package org.ladbury.meterPkg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.ladbury.meterPkg.Metric.MetricType;
import org.ladbury.persistentData.Persistable;
import org.ladbury.userInterfacePkg.UiStyle;
import org.ladbury.abodePkg.Abode;


@Entity
public class Meter	implements	Serializable,
								Persistable <Meter>,
								Comparable<Meter>,
								Comparator<Meter>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5911387336252034382L;

	public static enum MeterType {UNDEFINED, OWLCM160, ONZO};
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long meterId;				// persistence ID field
	@Basic
	private String name;			// the name of the meter
	@Basic
	private MeterType type;			// the meter type - which defines what metrics are stored
	@OneToMany(mappedBy = "meter")
	private List<Metric> metrics;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Abode abode;
	static final String ONZODATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	//
	// Constructors
	//
	
	public Meter(){
		this.name = UiStyle.UNNAMED;
		this.type = MeterType.UNDEFINED;
		this.metrics = new ArrayList<Metric>(Collections.<Metric>emptyList());
		this.abode = null;
	}
	public Meter(Abode a,MeterType t){
		
		this.name = UiStyle.UNNAMED;
		this.metrics = new ArrayList<Metric>(Collections.<Metric>emptyList());
		this.type = t;
		this.abode = a;
		switch (t){
		case OWLCM160:{ //POWER
			this.name = "OWL CM160";
			this.metrics.add(new Metric(this,MetricType.POWER));
		}
		case ONZO: { //ENERGY_LOW_RES, ENERGY_HIGH_RES, POWER_REAL_STANDARD, POWER_REAL_FINE, POWER_REACTIVE_STANDARD
			this.name = "ONZO";
			this.metrics.add(new Metric(this,MetricType.ENERGY_LOW_RES));
			this.metrics.add(new Metric(this,MetricType.ENERGY_HIGH_RES));
			this.metrics.add(new Metric(this,MetricType.POWER_REAL_STANDARD));
			this.metrics.add(new Metric(this,MetricType.POWER_REAL_FINE));
			this.metrics.add(new Metric(this,MetricType.POWER_REACTIVE_STANDARD));
		}
		default: 
		}
	}
	//
	// Access methods
	//
	
	/**
	 * getType
	 * @return the type of the meter
	 */
	public MeterType getType() {
		return type;
	}

	/**
	 * setType
	 * @param type the type value to be set
	 */
	public void setType(MeterType type) {
		this.type = type;
	}
	public void setMetric(MetricType t, Metric metric) {
		this.metrics.set(getMetricIndex(t), metric);
	}

	public void setName(String n){
		this.name = n;
	}

	
	/**
	 * get Metric
	 * @param metricT the type of the metric to be returned
	 * @return one of the meter's recoded metrics
	 */
	public Metric getMetric(MetricType metricT) {
		/*
		 * 	POWER = 0;
		 *	ENERGY_LOW_RES = 0;
		 *	ENERGY_HIGH_RES = 1;
		 *	POWER_REAL_STANDARD = 2;
		 *	POWER_REAL_FINE = 3;
		 *	POWER_REACTIVE_STANDARD = 4;
		 */
		switch(type){
		case OWLCM160: return metrics.get(0);
		case ONZO: return metrics.get(metricT.ordinal()-2);
		default: return null;
		}
	}
	public int getMetricIndex(MetricType metricT) {
		/*
		 * 	POWER = 0;
		 *	ENERGY_LOW_RES = 0;
		 *	ENERGY_HIGH_RES = 1;
		 *	POWER_REAL_STANDARD = 2;
		 *	POWER_REAL_FINE = 3;
		 *	POWER_REACTIVE_STANDARD = 4;
		 */
		switch(type){
		case OWLCM160: return 0;
		case ONZO: return metricT.ordinal()-2;
		default: return -1;
		}
	}

	//
	// Methods to implement interfaces
	//

	@Override
	public int compare(Meter arg0, Meter arg1) {
		return arg0.compareTo(arg1);
	}

	@Override
	public int compareTo(Meter arg0) {
		return this.type.compareTo(arg0.type);
	}

	@Override
	public long id() {
		return meterId;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toCSV() {
		return 	meterId +","+
				name +","+
				type;
	}

	@Override
	public void updateFields(Meter element) {
		this.name = element.name;
		this.type = element.type;
		this.metrics = element.metrics;
		this.abode = element.abode;
	}

	@Override
	public String idString() {
		return "["+this.id()+"] "+this.name();
	}
	public Abode getAbode() {
	    return abode;
	}
	public void setAbode(Abode param) {
	    this.abode = param;
	}
}
