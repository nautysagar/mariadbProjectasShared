package com.nashtech.dataCollector.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.enums.TdlogProcessingType;
import com.nashtech.dataCollector.enums.TdlogRecordState;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.models.Configuration;
import com.nashtech.dataCollector.models.DeviceDataDef;
import com.nashtech.dataCollector.models.DeviceStreamDef;
import com.nashtech.dataCollector.models.KeyOrder;
import com.nashtech.dataCollector.models.RunDataDef;
import com.nashtech.dataCollector.models.TdlogRecord;
import com.nashtech.dataCollector.models.Touchdown;
import com.nashtech.dataCollector.models.TouchdownCoordinates;
import com.nashtech.dataCollector.models.TouchdownDataDef;
import com.nashtech.dataCollector.models.WaferData;
import com.nashtech.dataCollector.pojo.ConfigurationMO;
import com.nashtech.dataCollector.pojo.TdlogDataBlock;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;
import com.nashtech.dataCollector.pojo.TdlogRecordMO;

public class DataCollectorConversionUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorConversionUtil.class);

	private DataCollectorConversionUtil() {
	}

	public static void convertConfigurationEntityToMO(Configuration config, ConfigurationMO data) {
		data.setConfigName("userdefined");
		data.setMysqlVersion(config.getMysqlVersion());
		data.setRestApiUrlPath(config.getRestApiUrlPath());
		data.setUidLogFilePath(config.getUidLogFilePath());
		data.setUidLogFileVersion(config.getUidLogFileVersion());
		data.setWriteToFile(config.getWriteToFile());
		data.setJavaDateFormat(config.getJavaDateFormat());
		data.setUnixDateFormat(config.getUnixDateFormat());
		data.setDebugLevel(config.getDebugLevel());
		data.setMaxRetries(config.getMaxRetries());
		data.setDeleteImmediate(config.getDeleteImmediate());
		data.setDeleteAfterTime(config.getDeleteAfterTime());
		data.setHttpSuccess(config.getHttpSuccess());
		data.setPushdataendWaitTime(config.getPushdataendWaitTime());

	}

	public static Configuration convertConfigurationMOToEntity(ConfigurationMO data) {
		return new Configuration(data.getConfigName(), data.getMysqlVersion(), data.getRestApiUrlPath(),
				data.isWriteToFile(), data.getUnixDateFormat(), data.getJavaDateFormat(), data.getDeleteImmediate(),
				data.getDeleteAfterTime(), data.getMaxRetries(), data.getDebugLevel(), data.getHttpSuccess(),
				data.getUidLogFilePath(), data.getUidLogFileVersion(), data.getPushdataendWaitTime());
	}

	public static TdlogRecord createFullTdlogRecord(String ocr, String diffLot, String sfcLot, String productName,
			int waferNumber, String testStage, String nc12, String tpName, String tpVersion, String processingType,
			String dataBlockIdentitier, int dataBlockDefinitionVersion, List<TdlogDataBlock> dataBlockParts,
			ConfigurationMO data) {
		TdlogRecord enityRecord = new TdlogRecord();
		enityRecord.setConfiguration(convertConfigurationMOToEntity(data));
		enityRecord.setWaferData(
				convertWaferData(ocr, diffLot, sfcLot, productName, waferNumber, testStage, nc12, tpName, tpVersion));
		enityRecord.setKeyorder(getKeyorderEntity(dataBlockParts));
		enityRecord.setRunDataDef(getRunDataDef(processingType));
		enityRecord.setDeviceDataDef(getDeviceDataDef());
		enityRecord.setDeviceStreamDef(getDeviceStreamDef(dataBlockIdentitier));
		enityRecord.setTouchdownDataDef(getTouchDownDataDef());
		convertTdlogRecordToEntity(enityRecord);

		return enityRecord;
	}

	public static WaferData convertWaferData(String ocr, String diffLot, String sfcLot, String productName,
			int waferNumber, String testStage, String nc12, String tpName, String tpVersion) {

		return new WaferData(ocr, diffLot, sfcLot, waferNumber, testStage, nc12, productName, tpName, tpVersion,
				"TDlogv3_0.0.1");
		// TODO Check with Mathias for tpiversion

	}

	public static void convertTdlogRecordToEntity(TdlogRecord record) {
		record.setDateTimeLocalOffset(getCurrentTime());
		record.setErrorCode(null);
		record.setErrorText(null);
		record.setFinishTransportUUID(null);
		record.setLogCount(0);
		record.setPid(String.valueOf(0));
		record.setProductionHost(getHostName());
		record.setProductionUser(System.getProperty("user.name"));
		record.setRetries(0);
		record.setRungroupUUID(getGeneratedUUID());
		record.setStartTransportUUID(getGeneratedUUID());

	}

	private static String getGeneratedUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}

	private static String getHostName() {
		InetAddress ip;
		String hostname = null;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();

		} catch (UnknownHostException e) {

			LOGGER.error("Ubable to fetch HostName of the machine");
		}
		return hostname;
	}

	private static String getCurrentTime() {
		return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS));
	}

	public static List<KeyOrder> getKeyorderEntity(List<TdlogDataBlock> dataBlockParts) {
		List<KeyOrder> list = new ArrayList<>();
		dataBlockParts.stream().forEach(b -> {
			if (b != null) {
				KeyOrder k = new KeyOrder();
				k.setEnumId(b.getKey());
				k.setEnumKey(b.getValue());

				list.add(k);
			}
		});
		/*
		 * Iterator<TdlogDataBlock> itr = dataBlockParts.iterator(); while
		 * (itr.hasNext()) { TdlogDataBlock b = itr.next(); KeyOrder k = new KeyOrder();
		 * 
		 * k.setEnumId(b.getKey()); k.setEnumKey(b.getValue());
		 * 
		 * list.add(k); }
		 */

		return list;
	}

	public static RunDataDef getRunDataDef(String defType) {
		RunDataDef r = DatabaseDataCollectorUtil.getRunDataDefByType(defType, "RunDataDef");

		if (r == null) {
			r = new RunDataDef();
			r.setDefType(defType);
			// TODO Check for version
		}
		return r;
	}

	public static TouchdownDataDef getTouchDownDataDef() {
		TouchdownDataDef config = DatabaseDataCollectorUtil.getTouchDownDataDefConfig("TouchdownDataDef");
		if (config == null)
			config = new TouchdownDataDef();
		return config;
	}

	public static DeviceDataDef getDeviceDataDef() {
		DeviceDataDef config = DatabaseDataCollectorUtil.getDataDefConfig("DeviceDataDef");
		if (config == null)
			config = new DeviceDataDef();
		return config;
	}

	public static DeviceStreamDef getDeviceStreamDef(String devType) {
		DeviceStreamDef r = DatabaseDataCollectorUtil.getDeviceStreamDefByType(devType, "DeviceStreamDef");

		if (r == null) {
			r = new DeviceStreamDef();
			r.setDevType(devType);
			// TODO Check for version
		}
		return r;
	}

	public static void createOrUpdateTouchDownRecord(TdlogRecord tdlogrecordEntity, TdlogRecordMO record) {
		List<Touchdown> touchdown = tdlogrecordEntity.getTouchdown();
		if (DataCollectorValidator.isEmpty(touchdown)) {
			touchdown = new ArrayList<>();
		} else {
			updateTouchDownRecordState(touchdown);
		}

		Touchdown t = new Touchdown(record.getTouchdownNumber(), getGeneratedUUID(), null, TdlogRecordState.RECORD_INIT,
				null, null, 0, 0);
		touchdown.add(t);
		tdlogrecordEntity.setTouchdown(touchdown);
	}

	public static void setProcessingType(String processingType, TdlogRecordMO record) {
		String type = processingType.toUpperCase();
		if (type.contains("WAFER")) {
			record.setProcessingType(TdlogProcessingType.WAFERRUN.ordinal());
		} else if (type.contains("REEL")) {
			record.setProcessingType(TdlogProcessingType.REELRUN.ordinal());
		}

	}

	public static int findDataBlockId(String dataBlockIndex, TdlogRecordMO record) {
		if (!DataCollectorValidator.isEmpty(record.getDataBlock())) {
			return record.getDataBlock().stream().filter(t -> t.getValue() == dataBlockIndex)
					.map(TdlogDataBlock::getKey).findAny().orElse(-1);
		}
		return -1;
	}

	public static TdlogRecord mergeTdlogRecord(TdlogRecord logRecord, TdlogExtendResult result) {
		return DatabaseDataCollectorUtil.mergeTdLogrecord(logRecord, result);
	}

	public static void checkForIncompleteRecords() {
		if (DatabaseDataCollectorUtil.getTdlogRecordCountByCondition() > 0) {
			DatabaseDataCollectorUtil.updateTdlogRecordByFinishUUID("00000001-0002-0003-0004-000000000005");
			DatabaseDataCollectorUtil.updateTouchDownRecordState(TdlogRecordState.RECORD_INIT,
					TdlogRecordState.RECORD_CREATED);
		}

	}

	public static void updateFinalRecords(TdlogRecord tdlogrecordEntity, int logCount, TdlogExtendResult result) {

		updateTouchDownRecordState(tdlogrecordEntity.getTouchdown());
		updateTdlogRecordEntity(tdlogrecordEntity, logCount);
		mergeTdlogRecord(tdlogrecordEntity, result);
	}

	private static void updateTdlogRecordEntity(TdlogRecord tdlogrecordEntity, int logCount) {
		tdlogrecordEntity.setFinishTransportUUID(getGeneratedUUID());
		tdlogrecordEntity.setLogCount(logCount);

	}

	private static void updateTouchDownRecordState(List<Touchdown> td) {
		if (!DataCollectorValidator.isEmpty(td)) {
			td.forEach(item -> {
				if (item.getRecordState() == TdlogRecordState.RECORD_INIT) {
					item.setRecordState(TdlogRecordState.RECORD_CREATED);
				}
			});
		}

	}

	public static void checkForInValidTouchDownRecords(TdlogRecord tdlogrecordEntity, TdlogExtendResult result) {
		List<Touchdown> td = tdlogrecordEntity.getTouchdown();
		/*
		 * List<Touchdown> list = td.stream() .filter(d ->
		 * d.getTouchdownCoordinates().parallelStream().allMatch(tdc -> !tdc.isValid()))
		 * .collect(Collectors.toList());
		 */

		List<Touchdown> list = td.stream().filter(d -> {
			if (d.getTouchdownCoordinates() != null) {
				d.getTouchdownCoordinates().parallelStream().filter(tdc -> {
					if (!tdc.isValid()) {
						return true;
					}
					return false;
				});
			}
			return false;
		}).collect(Collectors.toList());

		if (list.size() > 1)
			result.setErrorLevel(TdlogResultCode.OK.getResultCode()); // TODO need to check behaviours

	}

	public static void addTouchdownRecordId(TdlogRecord entityRecord, List<Integer> touchdownIds, TdlogRecordMO record,
			TdlogExtendResult result) {
		List<Touchdown> idList = entityRecord.getTouchdown();
		List<Integer> tIds = new ArrayList<>();
		if (!DataCollectorValidator.isEmpty(idList)) {
			idList.forEach(t -> tIds.add(t.getId()));
		}

		tIds.removeAll(touchdownIds);

		if (DataCollectorValidator.isEmpty(tIds)) {
			result.setErrorLevel(TdlogResultCode.SQL_INSERT_FAULT.getResultCode());
			result.setErrorMessage("TdlogRecord merge Failed");
			LOGGER.warn(result.toString());

		}
		touchdownIds.add(tIds.get(0));
		record.setTouchdownDbIdx(tIds.get(0));
		result.setErrorLevel(TdlogResultCode.OK.getResultCode());

	}

	public static void updateTouchDownStateAndTime(TdlogRecordMO record, long observableTime,
			TdlogExtendResult result) {
		Touchdown td = null;
		TdlogRecord r = DatabaseDataCollectorUtil.getTdlogRecordByID(record.getTdlogrecordDbIdx(), result);
		DatabaseDataCollectorUtil.updateTouchDownRecordStateAndTime(TdlogRecordState.RECORD_CREATED,
				result.getDeliveryTime(), observableTime, record.getTouchdownDbIdx());
		if (result.getErrorLevel() != 0)
			return;
		if (!DataCollectorValidator.isEmpty(r.getTouchdown())) {
			td = r.getTouchdown().stream().filter(t -> {
				if (t.getId() == record.getTouchdownDbIdx()) {
					return true;
				}
				return false;
			}).findAny().orElse(null);

		}
		if (td != null) {
			record.setTouchdownNumber(td.getIdx());
			final List<Integer> keyOrderIds = new ArrayList<>();
			if (r.getKeyorder() != null)
				r.getKeyorder().stream().forEach(j -> keyOrderIds.add(j.getEnumId()));

			if (td != null && !DataCollectorValidator.isEmpty(td.getTouchdownCoordinates())) {
				result.setCompleteDataBlocks(getTdCordinateCountByKeyOrder(td.getTouchdownCoordinates(), keyOrderIds));
			}
		} else {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			return;
		}
		result.setErrorLevel(TdlogResultCode.OK.getResultCode());

	}

	/*
	 * Here We need to count the tdCordinate based on below condition if there are n
	 * keyorder in the tdlogrecord then there should be n touch down data in each
	 * coordinate else don't count
	 */
	private static int getTdCordinateCountByKeyOrder(List<TouchdownCoordinates> tdc, List<Integer> keys) {
		int[] count = { 0 };
		List<Integer> tddKeyOrderIds = new ArrayList<>();
		if (!DataCollectorValidator.isEmpty(tdc)) {
			tdc.stream().forEach(t -> {

				if (!DataCollectorValidator.isEmpty(t.getTouchdownData())) {
					t.getTouchdownData().stream().forEach(k -> {
						tddKeyOrderIds.add(k.getKeyOrder());
					});
					tddKeyOrderIds.removeAll(keys);
					if (DataCollectorValidator.isEmpty(tddKeyOrderIds)) {
						count[0]++;
					}
					tddKeyOrderIds.clear();

				}
			});
		}

		return count[0];
	}

}
