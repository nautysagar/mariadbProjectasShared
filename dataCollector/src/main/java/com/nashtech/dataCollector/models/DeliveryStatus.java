package com.nashtech.dataCollector.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nashtech.dataCollector.enums.TdlogRecordState;


@Entity
@Table(name="delivery_status")
public class DeliveryStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="time_stamp")
	private String timeStamp;
	
	@Column(name="rungroupuuid")
	private String rungroupuuid;
	
	@Column(name="runfinishuuid")
	private String runfinishuuid;
	
	@Column(name="record_state")
	private TdlogRecordState record_state;

	@Column(name="error_code")
	private String error_code;
	
	@Column(name="error_msg")
	private String error_msg;
	
	@Column(name="pid")
	private String pid;
	
	@Column(name="received_records")
	private int received_records;
	
	@Column(name="delivered")
	private int delivered;
	
	@Column(name="buffered")
	private int buffered;
	
	@Column(name="pending")
	private int pending;

	public DeliveryStatus() {
		super();
	}


	public DeliveryStatus(int id, String timeStamp, String rungroupuuid, String runfinishuuid,
			TdlogRecordState record_state, String error_code, String error_msg, String pid, int received_records,
			int delivered, int buffered, int pending) {
		super();
		this.id = id;
		this.timeStamp = timeStamp;
		this.rungroupuuid = rungroupuuid;
		this.runfinishuuid = runfinishuuid;
		this.record_state = record_state;
		this.error_code = error_code;
		this.error_msg = error_msg;
		this.pid = pid;
		this.received_records = received_records;
		this.delivered = delivered;
		this.buffered = buffered;
		this.pending = pending;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getRungroupuuid() {
		return rungroupuuid;
	}

	public void setRungroupuuid(String rungroupuuid) {
		this.rungroupuuid = rungroupuuid;
	}

	public String getRunfinishuuid() {
		return runfinishuuid;
	}

	public void setRunfinishuuid(String runfinishuuid) {
		this.runfinishuuid = runfinishuuid;
	}

	public TdlogRecordState getRecord_state() {
		return record_state;
	}

	public void setRecord_state(TdlogRecordState record_state) {
		this.record_state = record_state;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getReceived_records() {
		return received_records;
	}

	public void setReceived_records(int received_records) {
		this.received_records = received_records;
	}

	
	public int getDelivered() {
		return delivered;
	}

	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}
	
	public int getBuffered() {
		return buffered;
	}

	public void setBuffered(int buffered) {
		this.buffered = buffered;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	@Override
	public String toString() {
		return "DeliveryStatus [id=" + id + ", timeStamp=" + timeStamp + ", rungroupuuid=" + rungroupuuid
				+ ", runfinishuuid=" + runfinishuuid + ", record_state=" + record_state + ", error_code=" + error_code
				+ ", error_msg=" + error_msg + ", pid=" + pid + ", received_records=" + received_records + ", deliverd="
				+ delivered + ", buffered=" + buffered + ", pending=" + pending + "]";
	}
	
	
}
