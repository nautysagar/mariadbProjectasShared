package com.nashtech.dataCollector.Util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.QueryTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nashtech.dataCollector.enums.TdlogRecordState;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.models.Configuration;
import com.nashtech.dataCollector.models.DeviceDataDef;
import com.nashtech.dataCollector.models.DeviceStreamDef;
import com.nashtech.dataCollector.models.RunDataDef;
import com.nashtech.dataCollector.models.TdlogRecord;
import com.nashtech.dataCollector.models.TouchdownDataDef;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;
import com.nashtech.dataCollector.services.ConfiguationDao;
import com.nashtech.dataCollector.services.ConfigurationService;

@Component
public class DatabaseDataCollectorUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseDataCollectorUtil.class);

	private static ConfigurationService configService;

	private static ConfiguationDao dao;

	@Autowired
	private ConfigurationService tconfigService;

	@Autowired
	private ConfiguationDao tdao;

	@PostConstruct
	public void init() {
		DatabaseDataCollectorUtil.configService = tconfigService;
		DatabaseDataCollectorUtil.dao = tdao;
	}

	private DatabaseDataCollectorUtil() {
	}

	public static Configuration getDefaultConfiguration(TdlogExtendResult result) {

		Configuration config = null;

		try {
			config = configService.getByConfigName("default");

		} catch (NoResultException | NonUniqueResultException e) {
			result.setErrorLevel(TdlogResultCode.SQL_EXECUTE_FAILED.getResultCode());
			result.setErrorMessage("No Configuration record found by name default");
			LOGGER.warn(result.toString());
			return null;
		}

		return config;
	}

	public static TouchdownDataDef getTouchDownDataDefConfig(String entityName) {

		List<TouchdownDataDef> config = null;

		try {
			config = configService.getTouchDownDataDefEntity(entityName);

		} catch (IllegalStateException | QueryTimeoutException e) {
			LOGGER.warn("No record found in TouchdownDataDef ");
			return null;
		}

		if (DataCollectorValidator.isEmpty(config))
			return null;

		return config.get(0);
	}

	public static DeviceDataDef getDataDefConfig(String entityName) {

		List<DeviceDataDef> config = null;

		try {
			config = configService.getTouchDownDataDefEntity(entityName);

		} catch (IllegalStateException | QueryTimeoutException e) {
			LOGGER.warn("No record found in TouchdownDataDef ");
			return null;
		}

		if (DataCollectorValidator.isEmpty(config))
			return null;

		return config.get(0);
	}

	public static RunDataDef getRunDataDefByType(String type, String entityName) {
		List<RunDataDef> config = null;

		try {
			config = configService.getRunDataDefByType(type.toUpperCase(), entityName);

		} catch (IllegalStateException | QueryTimeoutException e) {
			LOGGER.warn("No record found in RunDataDef ");
			return null;
		}

		if (DataCollectorValidator.isEmpty(config))
			return null;

		return config.get(0);
	}

	public static DeviceStreamDef getDeviceStreamDefByType(String type, String entityName) {
		List<DeviceStreamDef> config = null;

		try {
			config = configService.getRunDataDefByType(type.toUpperCase(), entityName);

		} catch (IllegalStateException | QueryTimeoutException e) {
			LOGGER.warn("No record found in DeviceStreamDef ");
			return null;
		}

		if (DataCollectorValidator.isEmpty(config))
			return null;

		return config.get(0);
	}

	public static int saveTdLogrecord(TdlogRecord record, TdlogExtendResult result) {
		int id = -2;

		try {
			id = dao.create(record);

		} catch (RuntimeException e) {
			result.setErrorLevel(TdlogResultCode.SQL_INSERT_FAULT.getResultCode());
			result.setErrorMessage("TdlogRecord Persist Failed::" + e.getMessage());
			LOGGER.debug(result.toString());
			return id;
		}

		return id;
	}

	public static TdlogRecord mergeTdLogrecord(TdlogRecord record, TdlogExtendResult result) {

		try {
			dao.merge(record);

		} catch (RuntimeException e) {
			result.setErrorLevel(TdlogResultCode.SQL_INSERT_FAULT.getResultCode());
			result.setErrorMessage("TdlogRecord merge Failed::" + e.getMessage());
			LOGGER.warn(result.toString());

		}
		result.setErrorLevel(TdlogResultCode.OK.getResultCode());
		return record;
	}

	public static Object[] getTdlogRecordStatusById(int id) {
		List<Object[]> config = null;

		try {
			config = configService.getTdlogRecordStatus(id);

		} catch (IllegalStateException | QueryTimeoutException e) {
			LOGGER.warn("No Tdlog record by id execution failed ");
			return null;
		}

		return config.get(0);
	}

	public static TdlogRecord getTdlogRecordByRunGroupUUIDId(String groupId, TdlogExtendResult result) {
		List<TdlogRecord> config = null;

		try {
			config = configService.getTdlogRecordByGroupUUID(groupId);

		} catch (IllegalStateException | QueryTimeoutException e) {
			result.setErrorLevel(TdlogResultCode.SQL_EXECUTE_FAILED.getResultCode());
			result.setErrorMessage("Tdlog record fetch by Group UUID failed:::" + groupId);
			LOGGER.warn(result.toString());
			return null;
		}
		if (DataCollectorValidator.isEmpty(config)) {
			result.setErrorLevel(TdlogResultCode.SQL_NOT_FOUND.getResultCode());
			result.setErrorMessage("No Tdlog record found by Group UUID:::" + groupId);
			LOGGER.warn(result.toString());
			return null;
		}
		result.setErrorLevel(TdlogResultCode.OK.getResultCode());
		return config.get(0);
	}

	public static TdlogRecord getTdlogRecordById(int id, TdlogExtendResult result) {
		List<TdlogRecord> config = null;

		try {
			config = configService.getTdlogRecordByID(id);

		} catch (IllegalStateException | QueryTimeoutException e) {
			result.setErrorLevel(TdlogResultCode.SQL_EXECUTE_FAILED.getResultCode());
			result.setErrorMessage("Named Query Exception for TdlogRecordById: " + id);
			LOGGER.debug("Execution failed for getTdlogRecordByID ");
			return null;
		}
		if (DataCollectorValidator.isEmpty(config)) {
			result.setErrorLevel(TdlogResultCode.SQL_EXECUTE_FAILED.getResultCode());
			result.setErrorMessage("Unable to find TdlogRecord By Id: " + id);
			LOGGER.debug("No Tdlog record by id " + id);
			return null;
		}
		result.setErrorLevel(TdlogResultCode.OK.getResultCode());
		return config.get(0);
	}

	public static TdlogRecord getTdlogRecordWithTouchDownById(int id) {
		List<TdlogRecord> config = null;

		try {
			config = configService.getTdlogRecordWithTouchDownByID(id);

		} catch (IllegalStateException | QueryTimeoutException e) {
			LOGGER.warn("No Tdlog record by id execution failed ");
			return null;
		}
		if (DataCollectorValidator.isEmpty(config))
			return null;

		return config.get(0);
	}

	public static int getrecordCountByUUIDORRecordState() {
		int val = 0;

		try {
			val = configService.getTdlogRecordCountByCondition();

		} catch (NoResultException | IllegalStateException | QueryTimeoutException e) {
			return 0;
		}

		return val;
	}

	public static void updateTdlogRecordByUUID(String uId) {

		try {
			configService.updateTdlogRecordFinishUUID(uId);

		} catch (IllegalArgumentException e) {
			LOGGER.debug("Update fail for tdlogRecordByUUID");
		}

	}

	public static void updateTouchDownRecordState(TdlogRecordState recordState, TdlogRecordState updateState) {

		try {
			configService.updateTouchdownState(recordState, updateState);

		} catch (IllegalArgumentException e) {
			LOGGER.error("Update fail for touch down record");
		}

	}

	public static void updateTouchDownRecordStateAndTime(TdlogRecordState recordState, double deliveryTime,
			double peWaitTime, int id) {

		try {
			configService.updateTouchdownStateAndTime(recordState, deliveryTime, peWaitTime, id);

		} catch (RuntimeException e) {
			LOGGER.error("Update fail for touch down record");
		}

	}

	/*
	 * public static void callTouchDownDataprocedure(int touchDownId, int
	 * sitenumber, int x, int y,int dataBlockId, String uuid, int
	 * uniqueIdx,TdlogExtendResult result) { try {
	 * result.setErrorLevel(dao.createConfiguration(touchDownId, sitenumber, x, y,
	 * dataBlockId, uuid, uniqueIdx));
	 * 
	 * } catch (IllegalArgumentException e) { LOGGER.error("Procedure call failed");
	 * result.setErrorLevel(-2); } }
	 */

}
