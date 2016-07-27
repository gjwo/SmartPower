package org.ladbury.cataloguePkg;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.*;

import org.ladbury.persistentData.Persistable;

@Entity
public class Category 	 implements	Serializable,
									Persistable<Category>, 
									Comparable<Category>,
									Comparator<Category>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -907270934113936341L;
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
	public int compare(Category o1, Category o2) {
		return o1.compareTo(o2);
	}

	@Override
	public int compareTo(Category o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toCSV() {
		return id+","+name;
	}

	@Override
	public void updateFields(Category element) {
		this.name = element.name;		
	}

	@Override
	public String idString() {
		return "["+this.id()+"]-"+this.name();
	}
}