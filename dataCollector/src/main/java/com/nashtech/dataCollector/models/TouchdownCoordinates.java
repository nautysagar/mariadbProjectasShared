package com.nashtech.dataCollector.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tdcoordinates")

public class TouchdownCoordinates extends BaseEntity {

	@Column(name = "x_coordinate")
	private int xCoordinate;

	@Column(name = "y_coordinate")
	private int yCoordinate;

	@Column(name = "site_number")
	private int siteNumber;

	private boolean valid;

	@Transient
	private int KeyOderSize;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "tdc_id")
	private List<TouchdownData> touchdownData;

	// @Size(min=4, max=1024)
	// private String uidStreamData;

	public TouchdownCoordinates() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TouchdownCoordinates(int siteNumber, /* String uidStreamData, */ int xCoordinate, int yCoordinate) {
		this.siteNumber = siteNumber;
		// this.uidStreamData = uidStreamData;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public List<TouchdownData> getTouchdownData() {
		return touchdownData;
	}

	public void setTouchdownData(List<TouchdownData> touchdownData) {
		this.touchdownData = touchdownData;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public int getKeyOderSize() {
		return KeyOderSize;
	}

	public void setKeyOderSize(int keyOderSize) {
		KeyOderSize = keyOderSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// result = prime * result + siteNumber;
		// result = prime * result + ((touchdownData == null) ? 0 :
		// touchdownData.hashCode());
		result = prime * result + xCoordinate;
		result = prime * result + yCoordinate;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TouchdownCoordinates other = (TouchdownCoordinates) obj;

		if (xCoordinate != other.xCoordinate)
			return false;
		if (yCoordinate != other.yCoordinate)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TouchdownDataCoordinate [id=" + getId() + ", xCoordinate=" + xCoordinate + ", yCoordinate="
				+ yCoordinate + ", siteNumber=" + siteNumber + ", touchdowndata=" + touchdownData + "]";
	}

}
