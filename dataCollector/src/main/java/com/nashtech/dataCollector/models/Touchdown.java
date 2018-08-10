package com.nashtech.dataCollector.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.nashtech.dataCollector.enums.TdlogRecordState;
@Entity
@Table(name="touchdown")
@NamedQueries ({
	  @NamedQuery(name = Touchdown.UPDATE_RECORD_BY_RECORD_STATE, query="UPDATE Touchdown r SET r.recordState=:updateState where r.recordState=:recordState"),
	  @NamedQuery(name = Touchdown.UPDATE_RECORD_BY_RECORD_STATE_DELIVERYTIME_PETIME, query="UPDATE Touchdown r SET r.recordState=:recordState,r.deliveryTime=:deliveryTime,r.peWaitTime=:peWaitTime where r.id =:id")
	//@NamedQuery(name = Touchdown.GET_RECORD_BY_RECORD_STATE, query="Select r.id from Touchdown r where r.tdlogrecord_id=:tdlogrecordId and r.idx=:idx")
})
public class Touchdown extends BaseEntity {
//	public static final String UPDATE_RECORD_BY_STATE = "Touchdown.updateRecordsState";
//	public static final String INSERT_RECORD = "Touchdown.insertRecord";
	public static final String UPDATE_RECORD_BY_RECORD_STATE = "Touchdown.UpdateRecordByRecordState";
	public static final String UPDATE_RECORD_BY_RECORD_STATE_DELIVERYTIME_PETIME = "Touchdown.UpdateStateDeliveryPeTime";
	private int idx;

	@Size(min=1, max=40)
	@Column(name="transportuuid")
	private String transportUUID;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="touchdownnumber_id")
	private List<TouchdownCoordinates> touchdownCoordinates;
	
	@Column(name="record_state")
	private TdlogRecordState recordState = TdlogRecordState.RECORD_CREATED;
	
	@Column(name="error_code")
	private String errorCode;
	
	@Column(name="error_text")
	private String errorText;
	
	private int retries;
	
	@Column(name="delivery_time")
	private double deliveryTime = 0;
	
	@Column(name="pe_wait_time")
	private double peWaitTime = 0;
	
	public Touchdown() {
		super();
	}
	public Touchdown(int id, int idx) {
		super(id);
		this.idx = idx;
	}
	public Touchdown(int idx, String transportUUID, List<TouchdownCoordinates> touchdownCoordinates,
			TdlogRecordState recordState, String errorCode, String errorText,double deliveryTime, double peWaitTime ) {
	//	super(id);		
		this.idx = idx;
		this.transportUUID = transportUUID;
		this.touchdownCoordinates = touchdownCoordinates;
		this.recordState = recordState;
		this.errorCode = errorCode;
		this.errorText = errorText;
		this.deliveryTime = deliveryTime;
		this.peWaitTime = peWaitTime;
	}
	
	
	
	//@JsonProperty("IDX")
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	//@JsonProperty("DATA")
	public List<TouchdownCoordinates> getTouchdownCoordinates() {
		return touchdownCoordinates;
	}
	public void setTouchdownCoordinates(List<TouchdownCoordinates> touchdownCoordinates) {
		this.touchdownCoordinates = touchdownCoordinates;
	}
	
	public String getTransportUUID() {
		return transportUUID;
	}
	public void setTransportUUID(String transportUUID) {
		this.transportUUID = transportUUID;
	}

	//@JsonIgnore
	public TdlogRecordState getRecordState() {
		return recordState;
	}
	public void setRecordState(TdlogRecordState recordState) {
		this.recordState = recordState;
	}

	//@JsonIgnore
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	//@JsonIgnore
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	
	public void setDeliveryState(int errCode, String errMsg) {
		setErrorCode(Integer.toString(errCode));
		setErrorText(errMsg);
	}
	
	//@JsonIgnore
	public int getRetries() {
		return retries;
	}
	public void setRetries(int retries) {
		this.retries = retries;
	}
	public int incrementRetry() {
		return retries++;
	}
	
	

	public double getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(double deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public double getPeWaitTime() {
		return peWaitTime;
	}
	public void setPeWaitTime(double peWaitTime) {
		this.peWaitTime = peWaitTime;
	}
	@Override
	public String toString() {
		return "Touchdown [id=" + getId() + ", idx=" + idx + ", transportUUID=" + transportUUID + ", touchdownCoordinates="
				+ touchdownCoordinates + ", recordState=" + recordState + ", errorCode=" + errorCode + ", errorText="
				+ errorText + "]";
	}
	
	public String toStatus() {
		return "Touchdown [id=" + getId() + ", idx=" + idx + ", recordState=" + recordState + ", errorCode=" 
				+ errorCode + ", errorText=" + errorText + " retires=" + retries + "]";		
	}

}
