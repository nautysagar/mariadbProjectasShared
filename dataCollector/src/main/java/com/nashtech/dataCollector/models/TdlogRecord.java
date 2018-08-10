package com.nashtech.dataCollector.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.nashtech.dataCollector.enums.TdlogRecordState;


@Entity
@Table(name = "tdlogrecord")
/*@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="employee")*/
@NamedQueries({ @NamedQuery(name = TdlogRecord.QUERY_GETALL_RECORDS, query = "SELECT r FROM TdlogRecord r"),
		@NamedQuery(name = TdlogRecord.QUERY_GET_RECORDS_WITH_TOUCHDOWN, query = "SELECT DISTINCT r FROM TdlogRecord r JOIN FETCH r.touchdown t where r.id=:id"),
		@NamedQuery(name = TdlogRecord.QUERY_RECORD_BY_ID, query = "select r from TdlogRecord r where r.id =:id"),
		@NamedQuery(name = TdlogRecord.QUERY_RECORD_BY_RUNGROUP_ID, query = "select r from TdlogRecord r where r.rungroupUUID =:rungroupUUID"),
		@NamedQuery(name = TdlogRecord.QUERY_RECORD_STATE_ERROR_CODE_BY_ID, query = "select r.recordState,r.errorCode from TdlogRecord r where r.id =:id"),
		@NamedQuery(name = TdlogRecord.QUERY_COUNT_RECORD_BY_FINISHTRANSPORTUUID_OR_RECORD_STATE, query = "select count(r)from TdlogRecord r where LENGTH(r.finishTransportUUID) < 2 OR "
																											+ "r.finishTransportUUID IS NULL OR r.recordState = 0"),
		@NamedQuery(name = TdlogRecord.UPDATE_FINISHTRANSPORTUUID_AND_LOGCOUNT_BY_ID, query = "UPDATE TdlogRecord r SET r.finishTransportUUID=:uId, r.logCount=:count where r.id=:id"),
		@NamedQuery(name = TdlogRecord.UPDATE_RECORD_FINISHTRANSPORTUUID, query = "UPDATE TdlogRecord r SET r.finishTransportUUID=:uId where LENGTH(r.finishTransportUUID) < 2 OR r.finishTransportUUID IS NULL") })
/*
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery (
                    name = "addTouchDownData",
                    procedureName = "add_touchdown_data",
                    parameters = {
                                    @StoredProcedureParameter(mode = ParameterMode.IN,  type=Integer.class,    name = "p_touchdown_db_idx"),
                                    @StoredProcedureParameter(mode = ParameterMode.IN,  type=Integer.class,    name = "p_sitenumber"),
                                    @StoredProcedureParameter(mode = ParameterMode.IN,  type=Integer.class,    name = "p_x"),
                                    @StoredProcedureParameter(mode = ParameterMode.IN,  type=Integer.class,    name = "p_y"),
                                    @StoredProcedureParameter(mode = ParameterMode.IN,  type=Integer.class,    name = "p_db_idx"),
                                    @StoredProcedureParameter(mode = ParameterMode.IN,  type=String.class,    name = "p_uuidstream"),
                                    @StoredProcedureParameter(mode = ParameterMode.IN,  type=Integer.class,    name = "p_record_unique_idx"),
                                    @StoredProcedureParameter(mode = ParameterMode.OUT, type=Integer.class,    name = "p_return")
                    }
    )
    
})*/

public class TdlogRecord extends BaseEntity {
	public static final String QUERY_GETALL_RECORDS = "TdlogRecord.GetAllRecords";
	public static final String QUERY_GET_RECORDS_WITH_TOUCHDOWN = "TdlogRecord.GetRecordsWithTouchDown";
	public static final String QUERY_RECORD_BY_ID = "TdlogRecord.GetTdlogRecordById";
	public static final String QUERY_RECORD_BY_RUNGROUP_ID = "TdlogRecord.GetTdlogRecordByRunGroupId";
	public static final String QUERY_RECORD_STATE_ERROR_CODE_BY_ID = "TdlogRecord.GetTdlogRecordStateAndErrorCodeById";
	public static final String QUERY_COUNT_RECORD_BY_FINISHTRANSPORTUUID_OR_RECORD_STATE = "TdlogRecord.CountRecordByNullFinishUUIDAndRecordState";
	public static final String UPDATE_RECORD_FINISHTRANSPORTUUID = "TdlogRecord.UpdateRecordTransportUUID";
	public static final String UPDATE_FINISHTRANSPORTUUID_AND_LOGCOUNT_BY_ID = "TdlogRecord.UpdateRecordTransportUUIDAndLogCount";
	public static final String UPDATE_TOUCHDOWN_RECORD_STATE_BY_ID= "TdlogRecord.UpdateTouchDownRecordByTdRecordId";
	
	@Column(name = "record_state")
	private TdlogRecordState recordState = TdlogRecordState.RECORD_CREATED;

	@Size(min = 0, max = 6)
	@Column(name = "error_code")
	private String errorCode;

	@Size(min = 0, max = 125)
	@Column(name = "error_text")
	private String errorText;

	@Size(min = 0, max = 10)
	@Column(name = "pid")
	private String pid;

	private int retries;

	@Column(name = "log_count")
	private int logCount;

	@Size(min = 1, max = 40)
	@Column(name = "rungroupuuid")
	private String rungroupUUID;

	@Size(min = 1, max = 40)
	@Column(name = "start_transportuuid")
	private String startTransportUUID;

	@Size(min = 1, max = 40)
	@Column(name = "finish_transportuuid")
	private String finishTransportUUID;

	@Size(min = 1, max = 36)
	@Column(name = "date_time_local_offset")
	private String dateTimeLocalOffset; /* date +%FT%T%:z */

	@Column(name = "production_host")
	private String productionHost;

	@Column(name = "production_user")
	private String productionUser;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "device_data_def_id")
	private DeviceDataDef deviceDataDef;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "run_data_def_id")
	private RunDataDef runDataDef;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "touchdown_data_def_id")
	private TouchdownDataDef touchdownDataDef;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "device_stream_def_id")
	private DeviceStreamDef deviceStreamDef;

	// private String dataBlockId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "wafer_data_id")
	private WaferData waferData;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "tdlogrecord_id")
	private List<Touchdown> touchdown;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "tdlogrecord_id") // TBD
	private List<KeyOrder> keyorder;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "configuration_id")
	private Configuration configuration;

	public TdlogRecord() {
		super();
	}

	public TdlogRecord(int id, TdlogRecordState recordState, String errorCode, String errorText, String pid,
			int retries, int logCount, String rungroupUUID, String startTransportUUID, String finishTransportUUID,
			String dateTimeLocalOffset, String productionHost, String productionUser, DeviceDataDef deviceDataDef,
			RunDataDef runDataDef, TouchdownDataDef touchdownDataDef, /* DeviceStreamDef deviceStreamDef, */
			WaferData waferData, List<Touchdown> touchdown, List<KeyOrder> keyorder) {
		super(id);
		this.recordState = recordState;
		this.errorCode = errorCode;
		this.errorText = errorText;
		this.pid = pid;
		this.retries = retries;
		this.logCount = logCount;
		this.rungroupUUID = rungroupUUID;
		this.startTransportUUID = startTransportUUID;
		this.finishTransportUUID = finishTransportUUID;
		this.dateTimeLocalOffset = dateTimeLocalOffset;
		this.productionHost = productionHost;
		this.productionUser = productionUser;
		this.deviceDataDef = deviceDataDef;
		this.runDataDef = runDataDef;
		this.touchdownDataDef = touchdownDataDef;
		// this.deviceStreamDef = deviceStreamDef;
		this.waferData = waferData;
		this.touchdown = touchdown;
	}

	// @JsonIgnore
	// @JsonProperty("RECORD_STATE")
	public TdlogRecordState getRecordState() {
		return recordState;
	}

	public void setRecordState(TdlogRecordState recordState) {
		this.recordState = recordState;
	}

	// @JsonIgnore
	// @JsonProperty("PID")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	// @JsonIgnore
	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public void incrementRetries() {
		this.retries++;
	}

	// @JsonIgnore
	// @JsonProperty("LOG_COUNT")
	public int getLogCount() {
		return logCount;
	}

	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}

	// @JsonProperty("RUNGROUP_UUID")
	public String getRungroupUUID() {
		return rungroupUUID;
	}

	public void setRungroupUUID(String rungroupUUID) {
		this.rungroupUUID = rungroupUUID;
	}

	public String getStartTransportUUID() {
		return startTransportUUID;
	}

	public void setStartTransportUUID(String startTransportUUID) {
		this.startTransportUUID = startTransportUUID;
	}

	// @JsonProperty("FINISH_TRANSPORT_UUID")
	public String getFinishTransportUUID() {
		return finishTransportUUID;
	}

	public void setFinishTransportUUID(String finishTransportUUID) {
		this.finishTransportUUID = finishTransportUUID;
	}

	// @JsonProperty("PRODUCTION_DateTimeISO8601")
	public String getDateTimeLocalOffset() {
		return dateTimeLocalOffset;
	}

	public void setDateTimeLocalOffset(String dateTimeLocalOffset) {
		this.dateTimeLocalOffset = dateTimeLocalOffset;
	}

	// @JsonProperty("PRODUCTION_HOST")
	public String getProductionHost() {
		return productionHost;
	}

	public void setProductionHost(String productionHost) {
		this.productionHost = productionHost;
	}

	// @JsonProperty("ERROR_CODE")
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	// @JsonProperty("ERROR_MSG")
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

	// @JsonProperty("PRODUCTION_USER")
	public String getProductionUser() {
		return productionUser;
	}

	public void setProductionUser(String productionUser) {
		this.productionUser = productionUser;
	}

	public DeviceDataDef getDeviceDataDef() {
		return deviceDataDef;
	}

	public void setDeviceDataDef(DeviceDataDef deviceDataDef) {
		this.deviceDataDef = deviceDataDef;
	}

	public RunDataDef getRunDataDef() {
		return runDataDef;
	}

	public void setRunDataDef(RunDataDef runDataDef) {
		this.runDataDef = runDataDef;
	}

	public TouchdownDataDef getTouchdownDataDef() {
		return touchdownDataDef;
	}

	public void setTouchdownDataDef(TouchdownDataDef touchdownDataDef) {
		this.touchdownDataDef = touchdownDataDef;
	}

	public DeviceStreamDef getDeviceStreamDef() {
		return deviceStreamDef;
	}

	public void setDeviceStreamDef(DeviceStreamDef deviceStreamDef) {
		this.deviceStreamDef = deviceStreamDef;
	}

	public WaferData getWaferData() {
		return waferData;
	}

	public void setWaferData(WaferData waferData) {
		this.waferData = waferData;
	}

	public List<Touchdown> getTouchdown() {
		return touchdown;
	}

	public void setTouchdown(List<Touchdown> touchdown) {
		this.touchdown = touchdown;
	}

	public List<KeyOrder> getKeyorder() {
		return keyorder;
	}

	public void setKeyorder(List<KeyOrder> keyorder) {
		this.keyorder = keyorder;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public String toString() {
		return "TdlogRecord [id=" + getId() + ", recordState=" + recordState + ", errorCode=" + errorCode
				+ ", errorText=" + errorText + ", pid=" + pid + ", retries=" + retries + ", logCount=" + logCount
				+ ", rungroupUUID=" + rungroupUUID + ", startTransportUUID=" + startTransportUUID
				+ ", finishTransportUUID=" + finishTransportUUID + ", dateTimeLocalOffset=" + dateTimeLocalOffset
				+ ", productionHost=" + productionHost + ", productionUser=" + productionUser + ", deviceDataDef="
				+ deviceDataDef + ", runDataDef=" + runDataDef + ", touchdownDataDef=" + touchdownDataDef
				+ ", deviceStreamDef=" + deviceStreamDef + ", waferData=" + waferData + ", keyorder=" + keyorder
				+ ", configuration=" + configuration + "]";
	}

	public String toStatus() {
		return "TdlogRecord [id=" + getId() + ", recordState=" + recordState + ", errorCode=" + errorCode
				+ ", errorText=" + errorText + ", pid=" + pid + ", retries=" + retries + "]";
	}

}
