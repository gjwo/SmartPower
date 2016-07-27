package org.ladbury.cataloguePkg;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.*;

import org.ladbury.persistentData.Persistable;

@Entity
public class Make 	 implements	Serializable,
								Persistable<Make>, 
								Comparable<Make>,
								Comparator<Make>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2724678825668556304L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Basic
	private String name;

	public long id() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String param) {
		this.name = param;
	}

	public String name() {
		return name;
	}

	@Override
	public int compare(Make o1, Make o2) {
		return o1.compareTo(o2);
	}

	@Override
	public int compareTo(Make o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toCSV() {
		return id+","+name;
	}

	@Override
	public void updateFields(Make element) {
		this.name = element.name;		
	}

	@Override
	public String idString() {
		return "["+this.id()+"]-"+this.name();
	}
}