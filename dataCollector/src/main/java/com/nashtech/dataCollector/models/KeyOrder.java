package com.nashtech.dataCollector.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name="keyorder")
public class KeyOrder extends BaseEntity {

	@Column(name="enum_id")
	private int EnumId;
	
	@Size(min=1, max=10)
	@Column(name="enum_key")
	private String EnumKey;

	public KeyOrder() {
		super();
	}

	public KeyOrder(int id, int enumId, String enumKey) {
		super(id);
		EnumId = enumId;
		EnumKey = enumKey;
	}


	
	public int getEnumId() {
		return EnumId;
	}
	public void setEnumId(int enumId) {
		EnumId = enumId;
	}


	public String getEnumKey() {
		return EnumKey;
	}
	public void setEnumKey(String enumKey) {
		EnumKey = enumKey;
	}

	
	@Override
	public String toString() {
		return "KeyOrder [id=" + getId() + ", EnumId=" + EnumId + ", EnumKey=" + EnumKey + "]";
	}


	
}
