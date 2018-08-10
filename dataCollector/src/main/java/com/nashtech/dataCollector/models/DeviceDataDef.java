package com.nashtech.dataCollector.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;




@Entity
@Table(name="devicedatadef")
@NamedQueries ({
	@NamedQuery(name = DeviceDataDef.QUERY_GETALL_RECORDS, query="SELECT r FROM DeviceDataDef r")
})
public class DeviceDataDef extends BaseEntity {

	public static final String QUERY_GETALL_RECORDS = "DeviceDataDef.GetAllRecords";
	
	@Column(name="dev_type")
	private String devType = "WAFERDEVICEDATA";
	private String Version = "1";
	
	public DeviceDataDef() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DeviceDataDef(int id, String devType, String version) {
		super(id);
		this.devType = devType;
		Version = version;
	}
	
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	
	
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	
	@Override
	public String toString() {
		return "DeviceDataDef [devType=" + devType + ", Version=" + Version + "]";
	}


}
