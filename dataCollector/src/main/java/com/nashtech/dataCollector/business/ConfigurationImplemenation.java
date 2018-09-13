package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dataCollector.Util.DataCollectorValidator;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogResult;

@Transactional
public class ConfigurationImplemenation extends RunStartImplemenation{
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationImplemenation.class);

	public ConfigurationImplemenation() {
		super();
	}
	
	
	public ConfigurationImplemenation(DataCollectorPool pool) {
		super(pool);
	}


	public TdlogResult configurationSetup(String restApiUrlPath) {
		LOGGER.debug("DataCollector::ConfigurationSetup");
		result.setErrorLevel(TdlogResultCode.OK.getResultCode());
		if (!DataCollectorValidator.checkTdlogRecordIdx(result, record))
			return result;

		if (!DataCollectorValidator.checkInputParameter(restApiUrlPath, "restApiUrlPath", 255, result)) {
			return result;
		}

		if (!getSystemConfiguration())
			return result;
		data.setConfigurationSetupCalled(true);
		data.setRestApiUrlPath(restApiUrlPath);
		return result;
	}

	public TdlogResult configurationSetup(String restApiUrlPath, boolean writeToFile, String uidLogFilePath,
			String uidLogFileVersion, int debugLevel) {
		if (!DataCollectorValidator.checkInputParameter(uidLogFilePath, "uidLogFilePath", 255, result)
				|| !DataCollectorValidator.checkInputParameter(uidLogFileVersion, "uidLogFileVersion", 255, result)) {
			return result;
		}

		data.setWriteToFile(writeToFile);
		data.setUidLogFilePath(uidLogFilePath);
		data.setUidLogFileVersion(uidLogFileVersion);
		data.setDebugLevel(debugLevel);
		return configurationSetup(restApiUrlPath);
	}

}
