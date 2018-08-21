package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.Util.DatabaseDataCollectorUtil;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.models.TdlogRecord;
import com.nashtech.dataCollector.pojo.TdlogResult;

public class PushDataStartImplemenation extends PushDataImplemenation{
	private static final Logger LOGGER = LoggerFactory.getLogger(PushDataStartImplemenation.class);

	public PushDataStartImplemenation() {
		super();
	}
	
	
	public PushDataStartImplemenation(DataCollectorPool pool) {
		super(pool);
	}


	public TdlogResult pushDataStart() {
		LOGGER.debug("DataCollector::pushDataStart() called");
		record.setPushdataDeliveryTime(0.0);
		long timeBefore = System.currentTimeMillis();
		if (record.getTdlogrecordDbIdx() < 1) {
			result.setErrorLevel(TdlogResultCode.DC_WRONG_SEQUENCE.getResultCode());
			return result;
		}

		record.incrementTouchDownNumber();
		processor = new TouchDownProcessor();
		TdlogRecord tdlogrecordEntity = DatabaseDataCollectorUtil.getTdlogRecordByGroupUUID(record.getRungroupUuid(),result);
			if (result.getErrorLevel() != 0) return result; 
		DataCollectorConversionUtil.createOrUpdateTouchDownRecord(tdlogrecordEntity, record);
		TdlogRecord entityRecord = DataCollectorConversionUtil.mergeTdlogRecord(tdlogrecordEntity,result);
			DataCollectorConversionUtil.addTouchdownRecordId(entityRecord,touchdownIds,record,result);
			if (result.getErrorLevel() != 0) return result;
			long timeAfter = System.currentTimeMillis();
			long dTime = timeAfter - timeBefore;
			record.addPushdataDeliveryTime(dTime);
		LOGGER.debug(result.toString());	
		return result;
	}

	
}
