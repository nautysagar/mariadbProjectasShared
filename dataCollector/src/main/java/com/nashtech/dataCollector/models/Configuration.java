package com.nashtech.dataCollector.models;

import java.math.BigDecimal;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "configuration")
@NamedQueries({ @NamedQuery(name = Configuration.QUERY_GETALL_RECORDS, query = "SELECT r FROM Configuration r"),
		@NamedQuery(name = Configuration.QUERY_RECORD_BY_CONFIGNAME, query = "select r from Configuration r where r.configName =:name") })
public class Configuration extends BaseEntity {

	public static final String QUERY_GETALL_RECORDS = "Configuration.GetAllRecords";
	public static final String QUERY_RECORD_BY_CONFIGNAME = "Configuration.GetRecordByConfigName";

	@Size(min = 0, max = 30)
	@Column(name = "config_name")
	private String configName;
	/** < configuration name main item = default */

	@Size(min = 0, max = 16)
	@Column(name = "mysql_version")
	private String mysqlVersion;
	/** < tdlog mysql version */

	@Size(min = 0, max = 255)
	@Column(name = "rest_api_url_path")
	private String restApiUrlPath;
	/** < includes the URL Path for the remote REST-API Interface */

	@Column(name = "write_to_file")
	private Boolean writeToFile = false;
	/** < flag to activate the json record write to file feature */

	@Size(min = 0, max = 80)
	@Column(name = "unix_date_format")
	private String unixDateFormat;
	/** < Date format for the json records which has to be send out */

	@Size(min = 0, max = 80)
	@Column(name = "java_date_format")
	private String javaDateFormat;
	/** < Date format for the json records which has to be send out */

	@Column(name = "delete_immediate")
	private Boolean deleteImmediate = true;
	/**
	 * < flag to delete stored tdlog records immediately after sucessful delivery
	 */

	@Column(name = "delete_after_time")
	private Time deleteAfterTime;
	/**
	 * < if (deleteImmediate == false) delete successful delivered records after
	 * certain time
	 */

	@Column(name = "max_retries")
	private int maxRetries;
	/** < max retries for unsuccessful delivered records. */

	@Column(name = "debug_level")
	private int debugLevel;
	/** < debugging Level */

	@Column(name = "http_success")
	private int httpSuccess;
	/** < http success return code */

	@Size(min = 0, max = 255)
	@Column(name = "uid_log_file_path")
	private String uidLogFilePath;
	/** < write2file logfile path */

	@Size(min = 1, max = 10)
	@Column(name = "uid_log_file_version")
	private String uidLogFileVersion;
	/** < write2file version */

	@Column(name = "pushdataend_wait_time")
	private BigDecimal pushdataendWaitTime;

	/** waitTime which is used by the PushDataEnd */

	public Configuration() {
		super();
	}

	public Configuration(String configName, String mysqlVersion, String restApiUrlPath, Boolean writeToFile,
			String unixDateFormat, @Size(min = 0, max = 80) String javaDateFormat, Boolean deleteImmediate,
			Time deleteAfterTime, int maxRetries, int debugLevel, int httpSuccess, String uidLogFilePath,
			String uidLogFileVersion, BigDecimal pushdataendWaitTime) {
		super();
		this.configName = configName;
		this.mysqlVersion = mysqlVersion;
		this.restApiUrlPath = restApiUrlPath;
		this.writeToFile = writeToFile;
		this.unixDateFormat = unixDateFormat;
		this.javaDateFormat = javaDateFormat;
		this.deleteImmediate = deleteImmediate;
		this.deleteAfterTime = deleteAfterTime;
		this.maxRetries = maxRetries;
		this.debugLevel = debugLevel;
		this.httpSuccess = httpSuccess;
		this.uidLogFilePath = uidLogFilePath;
		this.uidLogFileVersion = uidLogFileVersion;
		this.pushdataendWaitTime = pushdataendWaitTime;
	}

	public String getConfigName() {
		return configName;
	}

	public String getMysqlVersion() {
		return mysqlVersion;
	}

	public void setMysqlVersion(String mysqlVersion) {
		this.mysqlVersion = mysqlVersion;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getRestApiUrlPath() {
		return restApiUrlPath;
	}

	public void setRestApiUrlPath(String restApiUrlPath) {
		this.restApiUrlPath = restApiUrlPath;
	}

	public Boolean getWriteToFile() {
		return writeToFile;
	}

	public void setWriteToFile(Boolean writeToFile) {
		this.writeToFile = writeToFile;
	}

	public String getUnixDateFormat() {
		return unixDateFormat;
	}

	public void setUnixDateFormat(String unixDateFormat) {
		this.unixDateFormat = unixDateFormat;
	}

	public String getJavaDateFormat() {
		return javaDateFormat;
	}

	public void setJavaDateFormat(String javaDateFormat) {
		this.javaDateFormat = javaDateFormat;
	}

	public Boolean getDeleteImmediate() {
		return deleteImmediate;
	}

	public void setDeleteImmediate(Boolean deleteImmediate) {
		this.deleteImmediate = deleteImmediate;
	}

	public Time getDeleteAfterTime() {
		return deleteAfterTime;
	}

	public void setDeleteAfterTime(Time deleteAfterTime) {
		this.deleteAfterTime = deleteAfterTime;
	}

	public String getUidLogFilePath() {
		return uidLogFilePath;
	}

	public void setUidLogFilePath(String uidLogFilePath) {
		this.uidLogFilePath = uidLogFilePath;
	}

	public String getUidLogFileVersion() {
		return uidLogFileVersion;
	}

	public void setUidLogFileVersion(String uidLogFileVersion) {
		this.uidLogFileVersion = uidLogFileVersion;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public int getDebugLevel() {
		return debugLevel;
	}

	public void setDebugLevel(int debugLevel) {
		this.debugLevel = debugLevel;
	}

	public int getHttpSuccess() {
		return httpSuccess;
	}

	public void setHttpSuccess(int httpSuccess) {
		this.httpSuccess = httpSuccess;
	}

	public BigDecimal getPushdataendWaitTime() {
		return pushdataendWaitTime;
	}

	public void setPushdataendWaitTime(BigDecimal pushdataendWaitTime) {
		this.pushdataendWaitTime = pushdataendWaitTime;
	}

	@Override
	public String toString() {
		return "Configuration [id=" + getId() + ", configName=" + configName + ", restApiUrlPath=" + restApiUrlPath
				+ ", writeToFile=" + writeToFile + ", unixDateFormat=" + unixDateFormat + ", javaDateFormat="
				+ javaDateFormat + ", deleteImmediate=" + deleteImmediate + ", deleteAfterTime=" + deleteAfterTime
				+ ", maxRetries=" + maxRetries + ", debugLevel=" + debugLevel + ", http_success=" + httpSuccess
				+ ", uidLogFilePath=" + uidLogFilePath + ", uidLogFileVersion=" + uidLogFileVersion + "]";
	}

}
