package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.Util.DataCollectorValidator;
import com.nashtech.dataCollector.enums.TdlogProcessingType;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogResult;

public class PushDataImplemenation extends PushDataEndImplemenation {
	private static final Logger LOGGER = LoggerFactory.getLogger(PushDataImplemenation.class);

	public PushDataImplemenation() {
		super();
	}

	public PushDataImplemenation(DataCollectorPool pool) {
		super(pool);
	}

	public TdlogResult pushData(int siteNumber, int xCoordinate, int yCoordinate, String dataBlockIndex,
			String dataBlockContent) {
		LOGGER.debug("DataCollector::PushData(I) called");
		int dataBlockKey = validateDataBlockIndex(dataBlockIndex);

		if (dataBlockKey < 0) {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			result.setErrorMessage("DataCollector::PushData() dataBlockIndex not found !");
			return result;
		}
		if (record.getProcessingType() != TdlogProcessingType.WAFERRUN.ordinal()) {
			result.setErrorLevel(TdlogResultCode.DC_WRONG_PUSHDATA_INTERFACE.getResultCode());
			return result;
		}

		return pushData(siteNumber, xCoordinate, yCoordinate, dataBlockKey, dataBlockContent);
	}

	public TdlogResult pushData(int siteNumber, int deviceNumber, String dataBlockIndex, String dataBlockContent) {
		LOGGER.debug("DataCollector::PushData(II) called");
		int dataBlockKey = validateDataBlockIndex(dataBlockIndex);

		if (dataBlockKey < 0) {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			result.setErrorMessage("DataCollector::PushData() dataBlockIndex not found !");
			return result;
		}

		if (record.getProcessingType() != TdlogProcessingType.REELRUN.ordinal()) {
			result.setErrorLevel(TdlogResultCode.DC_WRONG_PUSHDATA_INTERFACE.getResultCode());
			return result;
		}

		return pushData(siteNumber, deviceNumber, 0, dataBlockKey, dataBlockContent);
	}

	public TdlogResult pushData(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent) {
		LOGGER.debug("DataCollector::PushData(III) called");
		long startTime = System.currentTimeMillis();
		if (DataCollectorValidator.isEmpty(dataBlockContent) || dataBlockContent.length() > 256) {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			result.setErrorMessage(
					"DataCollector::PushData() dataBlockContent is empty or the max length overwritten !");
			return result;
		}

		// if (record.getTouchdownDbIdx() == 0) {
		if (record.getTouchdownNumber() == 0) {
			result.setErrorLevel(TdlogResultCode.SQL_NOT_FOUND.getResultCode());
			return result;
		}
		// Todo need to check for condition without parallel processing
		pool.submitTask(new DataCollectorRunnableTask(siteNumber, xCoordinate, yCoordinate, dataBlockIndex,
				dataBlockContent, processor, entity));
		result.setErrorLevel(TdlogResultCode.OK.getResultCode());
		long endTime = System.currentTimeMillis();
		long dTime = endTime - startTime;
		record.addPushdataDeliveryTime(dTime);
		return result;
	}

	public TdlogResult pushData(int siteNumber, int deviceNumber, int dataBlockIndex, String dataBlockContent) {
		LOGGER.debug("DataCollector::PushData(IV) called");
		return pushData(siteNumber, deviceNumber, 0, dataBlockIndex, dataBlockContent);
	}

	private int validateDataBlockIndex(String dataBlockIndex) {
		return DataCollectorConversionUtil.findDataBlockId(dataBlockIndex, record);
	}

}
