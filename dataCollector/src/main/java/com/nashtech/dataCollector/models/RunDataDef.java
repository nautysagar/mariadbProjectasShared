package com.nashtech.dataCollector.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "rundatadef")
@NamedQueries ({
	@NamedQuery(name = RunDataDef.QUERY_GETALL_RECORDS, query="SELECT r FROM RunDataDef r"),
	@NamedQuery(name = RunDataDef.QUERY_RECORD_BY_DEF_TYPE, query="select r from RunDataDef r where r.defType =:type")
})
public class RunDataDef extends BaseEntity {
	public static final String QUERY_GETALL_RECORDS = "RunDataDef.GetAllRecords";
	public static final String QUERY_RECORD_BY_DEF_TYPE = "RunDataDef.GetRecordByDefType";
	
	@Column(name="def_type")
	private String defType = "WAFERRUNSTARTDATA";
	
	@Column(name="def_type_finish")
	private String defTypeFinish = "RUNFINISHDATA"; // added for bug219
	
	private String version = "1";

	public RunDataDef() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RunDataDef(int id, String defType, String version) {
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

	public String getDefTypeFinish() {
		return defTypeFinish;
	}

	public void setDefTypeFinish(String defTypeFinish) {
		this.defTypeFinish = defTypeFinish;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "RunDataDef [id=" + getId() + ", defType=" + defType + ", defTypeFinish=" + defTypeFinish + ", version="
				+ version + "]";
	}

}
