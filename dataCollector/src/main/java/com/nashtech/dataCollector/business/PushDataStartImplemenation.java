package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogResult;

public class PushDataStartImplemenation extends PushDataImplemenation {
	private static final Logger LOGGER = LoggerFactory.getLogger(PushDataStartImplemenation.class);

	public PushDataStartImplemenation() {
		super();
	}

	public PushDataStartImplemenation(DataCollectorPool pool) {
		super(pool);
	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public TdlogResult pushDataStart() {
		LOGGER.debug("DataCollector::pushDataStart() called");
		record.setPushdataDeliveryTime(0.0);
		long timeBefore = System.currentTimeMillis();
		if (record.getTdlogrecordDbIdx() < 1) {
			result.setErrorLevel(TdlogResultCode.DC_WRONG_SEQUENCE.getResultCode());
			result.setErrorMessage("PushData Start failed as TdlogRecord Id is wrong");
			return result;
		}

		record.incrementTouchDownNumber();
		processor = new TouchDownProcessor();
		DataCollectorConversionUtil.createOrUpdateTouchDownRecord(entity, record);
		long timeAfter = System.currentTimeMillis();
		long dTime = timeAfter - timeBefore;
		record.addPushdataDeliveryTime(dTime);
		LOGGER.debug(result.toString());
		return result;
	}

}
