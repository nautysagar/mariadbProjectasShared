package com.nashtech.dataCollector.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name="devicestreamdef")
@NamedQueries ({
	@NamedQuery(name = DeviceStreamDef.QUERY_GETALL_RECORDS, query="SELECT r FROM DeviceStreamDef r"),
	@NamedQuery(name = DeviceStreamDef.QUERY_RECORD_BY_DEF_TYPE, query="select r from DeviceStreamDef r where r.devType =:type")
})
public class DeviceStreamDef extends BaseEntity {
	
	public static final String QUERY_GETALL_RECORDS = "DeviceStreamDef.GetAllRecords";
	public static final String QUERY_RECORD_BY_DEF_TYPE = "DeviceStreamDef.GetRecordByDefType";

	@Column(name="dev_type")
	private String devType;
	
	private String version = "0";

	public DeviceStreamDef() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceStreamDef(int id, String devType, String version) {
		super(id);		
		this.devType = devType;
		this.version = version;
	}

	
	
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}

	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}


	@Override
	public String toString() {
		return "DeviceStreamDef [id=" + getId() + ", devType=" + devType + ", version=" + version + "]";
	}

	
}
