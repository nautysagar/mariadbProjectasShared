package com.nashtech.dataCollector.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="touchdowndata")
public class TouchdownData extends BaseEntity {

	@Column(name="key_order")
	private int keyOrder;
	
	@NotNull
	@Size(min=1, max=255)
	@Column(name="uid_stream_data")
	private String uidStreamData;
	
	@Column(name="record_unique")
	private int recordUnique;

	public TouchdownData() {
		super();
	}

	public TouchdownData(int keyOrder, String uidStreamData,int recordUnique) {
		this.keyOrder = keyOrder;
		this.uidStreamData = uidStreamData;
		this.recordUnique = recordUnique;
	}




	public int getKeyOrder() {
		return keyOrder;
	}
	public void setKeyOrder(int keyOrder) {
		this.keyOrder = keyOrder;
	}

	
	public String getUidStreamData() {
		return uidStreamData;
	}
	public void setUidStreamData(String uidStreamData) {
		this.uidStreamData = uidStreamData;
	}
	

	public int getRecordUnique() {
		return recordUnique;
	}

	public void setRecordUnique(int recordUnique) {
		this.recordUnique = recordUnique;
	}


	@Override
	public String toString() {
		return "TouchdownData [id=" + getId() + ", keyOrder=" + keyOrder + ", uidStreamData=" + uidStreamData + "]";
	}
}
