package com.nashtech.dataCollector.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.Util.DataCollectorValidator;
import com.nashtech.dataCollector.Util.DatabaseDataCollectorUtil;
import com.nashtech.dataCollector.enums.HttpResultEnum;
import com.nashtech.dataCollector.enums.TdlogRecordState;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.models.TdlogRecord;
import com.nashtech.dataCollector.pojo.TdlogDataBlock;
import com.nashtech.dataCollector.pojo.TdlogResult;

public class RunStartImplemenation extends PushDataStartImplemenation{
	private static final Logger LOGGER = LoggerFactory.getLogger(RunStartImplemenation.class);

	public RunStartImplemenation() {
		super();
	}
	
	public RunStartImplemenation(DataCollectorPool pool) {
		super(pool);
	}

	public TdlogResult runStart(String ocr, String diffLot, String sfcLot, String productName, int waferNumber,
			String testStage, String nc12, String tpName, String tpVersion, String processingType,
			String dataBlockIdentitier, int dataBlockDefinitionVersion, List<TdlogDataBlock> dataBlockParts) {

		LOGGER.debug("DataCollector::RunStart() called");
		if (DataCollectorValidator.isEmpty(dataBlockParts) || DataCollectorValidator.isEmpty(dataBlockIdentitier)) {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			result.setErrorMessage(
					DataCollectorValidator.isEmpty(dataBlockIdentitier) ? "parameter dataBlockIdentifier is empty !"
							: "dataBlockPart is wrongly defined.");
			LOGGER.warn(result.toString());
			return result;
		}
		if (!DataCollectorValidator.checkInputParameter(ocr, "ocr", 20, result)
				|| !DataCollectorValidator.checkInputParameter(diffLot, "diffLot", 20, result)
				|| !DataCollectorValidator.checkInputParameter(productName, "productName", 20, result)
				|| !DataCollectorValidator.checkInputParameter(testStage, "testStage", 20, result)
				|| !DataCollectorValidator.checkInputParameter(nc12, "nc12", 12, result)
				|| !DataCollectorValidator.checkInputParameter(tpName, "tpName", 40, result)
				|| !DataCollectorValidator.checkInputParameter(processingType, "processingType", 20, result)) {

			return result;
		}
		LOGGER.debug("DataCollector::RunStart() Validation Passed");
		initializeVariables();//TODo Need to check

		/* if not yet done, which means the configuration setup is not called before */
		if (!data.isConfigurationSetupCalled()) {
			if (!getSystemConfiguration())
				return result;
		} else {
			data.setConfigurationSetupCalled(false);
		}
		
		DataCollectorConversionUtil.checkForIncompleteRecords();
		
		TdlogRecord logRecord = DataCollectorConversionUtil.createFullTdlogRecord(ocr, diffLot, sfcLot, productName,
				waferNumber, testStage, nc12, tpName, tpVersion, processingType, dataBlockIdentitier,
				dataBlockDefinitionVersion, dataBlockParts, data);

		
		int tdlogRecordId = saveTdlogRecord(logRecord);
		if (tdlogRecordId == -2)
			return result;

		
		record.setTdlogrecordDbIdx(tdlogRecordId);
		record.setDataBlock(dataBlockParts);
	//	data.setConfigurationdDbIdx(0);
		record.setRungroupUuid(logRecord.getRungroupUUID());
		DataCollectorConversionUtil.setProcessingType(processingType, record);
		checkJobStatus(tdlogRecordId);
		
		return result;
	}
	
	private void checkJobStatus(int id) {
		long start = System.currentTimeMillis();
		boolean resultAvailable = false;
		do {
		Object[] o = DatabaseDataCollectorUtil.getTdlogRecordStatusById(id);
		if (o == null) {
			result.setErrorLevel(TdlogResultCode.SQL_EXCEPTION.getResultCode());
			result.setErrorMessage("Job Status Query for TdlogRecord by Id:" + id + " failed");
			break;
		}
		long end = System.currentTimeMillis();
		if ((end - start) / 1000 > 10) {
			result.setErrorLevel(TdlogResultCode.DC_START_DELIVERY_TIMEOUT.getResultCode());
			LOGGER.debug(result.toString());
			break;
		}

		resultAvailable = checkTdlogRecordStatus(o[0], o[1]);
		if (!resultAvailable)
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				LOGGER.debug("checkTdlogRecordStatus is interrupted");
				 Thread.currentThread().interrupt();
			}
		// TODO need to check for sleep statement
		} while (!resultAvailable);

	}

	private boolean checkTdlogRecordStatus(Object state, Object code) {
		boolean resultAvailable = true; //TODO BUG make it false, true for testing purpose
		TdlogRecordState recordState = (TdlogRecordState) state;
		String errorCode = (String) code;
		switch (recordState) {
		case RECORD_INIT:
		case RECORD_CREATED:
		case RECORD_RUNSTART_PROCESSING:
			break;
		case RECORD_RUNSTART_SUCCESS:
			result.setErrorLevel(TdlogResultCode.OK.getResultCode());
		    resultAvailable = true;
			break;
		case RECORD_RUNSTART_FAILED:
		case RECORD_RUNSTART_FAILED_RETRY:
			if (!DataCollectorValidator.isEmpty(errorCode) && Integer.valueOf(errorCode) == 0)
				resultAvailable = false;
			
			if (Integer.valueOf(errorCode) == HttpResultEnum.HTTP_RESPONSE_405.getHttpCode())
				result.setErrorLevel(TdlogResultCode.DC_START_DELIVERY_FAILED_ERR405.getResultCode());
			else if (Integer.valueOf(errorCode) == HttpResultEnum.HTTP_RESPONSE_408.getHttpCode())
				result.setErrorLevel(TdlogResultCode.DC_START_DELIVERY_FAILED_ERR408.getResultCode());
			else if (Integer.valueOf(errorCode) == HttpResultEnum.HTTP_RESPONSE_500.getHttpCode())
				result.setErrorLevel(TdlogResultCode.DC_START_DELIVERY_FAILED_ERR500.getResultCode());
			if (Integer.valueOf(errorCode) > 0) resultAvailable = true;
			
			break;
		case RECORD_UIDSTREAM_PROCESSING:
			result.setErrorLevel(TdlogResultCode.OK.getResultCode());
			resultAvailable = true;
			break;
		default:
			result.setErrorLevel(TdlogResultCode.OK.getResultCode());
			resultAvailable = true;
			break;
		}

		return resultAvailable;
	}
	

	private int saveTdlogRecord(TdlogRecord logRecord) {
		return DatabaseDataCollectorUtil.saveTdLogrecord(logRecord, result);

	}


}
