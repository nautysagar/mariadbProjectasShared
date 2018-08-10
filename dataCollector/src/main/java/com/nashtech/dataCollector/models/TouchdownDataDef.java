package com.nashtech.dataCollector.models;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="touchdowndatadef")
@NamedQueries ({
	@NamedQuery(name = TouchdownDataDef.QUERY_GETALL_RECORDS, query="SELECT r FROM TouchdownDataDef r")
})
public class TouchdownDataDef extends BaseEntity {

	public static final String QUERY_GETALL_RECORDS = "TouchdownDataDef.GetAllRecords";
	
	@Column(name="def_type")
	private String defType = "TOUCHDOENDATA";
	private String version = "1";
	
	public TouchdownDataDef() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TouchdownDataDef(int id, String defType, String version) {
		super(id);
		this.defType = defType;
		this.version = version;
	}


	
	
	public String getDefType() {
		return defType;
	}
	public void setDefType(String defType) {
		this.defType = defType;
	}
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "TouchdownDataDef [id=" + getId() + ", defType=" + defType + ", version=" + version + "]";
	}

}
