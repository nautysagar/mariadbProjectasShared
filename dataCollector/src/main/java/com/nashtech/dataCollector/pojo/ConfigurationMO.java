package com.nashtech.dataCollector.pojo;

import java.math.BigDecimal;
import java.sql.Time;

public class ConfigurationMO {

//	private int configurationdDbIdx; // will be mapped to Id of Configuration Entity
	private String configName;
	private String mysqlVersion;
	boolean writeToFile;
	private String restApiUrlPath;
	private String uidLogFilePath;
	private String uidLogFileVersion;
	private String unixDateFormat;
	private int debugLevel;
	private boolean configurationSetupCalled;
	private String javaDateFormat;
	private Boolean deleteImmediate;
	private Time deleteAfterTime;
	private int maxRetries;
	private int httpSuccess;
	private BigDecimal pushdataendWaitTime;

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getMysqlVersion() {
		return mysqlVersion;
	}

	public void setMysqlVersion(String mysqlVersion) {
		this.mysqlVersion = mysqlVersion;
	}

	public boolean isWriteToFile() {
		return writeToFile;
	}

	public void setWriteToFile(boolean writeToFile) {
		this.writeToFile = writeToFile;
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

	public String getUnixDateFormat() {
		return unixDateFormat;
	}

	public void setUnixDateFormat(String unixDateFormat) {
		this.unixDateFormat = unixDateFormat;
	}

	public int getDebugLevel() {
		return debugLevel;
	}

	public void setDebugLevel(int debugLevel) {
		this.debugLevel = debugLevel;
	}

	public boolean isConfigurationSetupCalled() {
		return configurationSetupCalled;
	}

	public void setConfigurationSetupCalled(boolean configurationSetupCalled) {
		this.configurationSetupCalled = configurationSetupCalled;
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

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public String getRestApiUrlPath() {
		return restApiUrlPath;
	}

	public void setRestApiUrlPath(String restApiUrlPath) {
		this.restApiUrlPath = restApiUrlPath;
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

	/*public int getConfigurationdDbIdx() {
		return configurationdDbIdx;
	}

	public void setConfigurationdDbIdx(int configurationdDbIdx) {
		this.configurationdDbIdx = configurationdDbIdx;
	}
	*/
	

	

}
