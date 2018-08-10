package com.nashtech.dataCollector.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	public BaseEntity() {

	}

	public BaseEntity(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** < indexed entity and mysql table */

	/** getter & setter method for the corresponding attributes */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
