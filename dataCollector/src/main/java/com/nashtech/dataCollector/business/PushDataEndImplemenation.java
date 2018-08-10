package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;

public class PushDataEndImplemenation extends RunFinishImplemenation{
	private static final Logger LOGGER = LoggerFactory.getLogger(PushDataEndImplemenation.class);

	public PushDataEndImplemenation() {
		super();
	}
	
	
	public TdlogExtendResult pushDataEnd() {
		LOGGER.debug(" DataCollector::PushDataEnd()");
		long start = System.currentTimeMillis();
		pool.executorToFinish();
	//	long end1 = System.currentTimeMillis();
	//	LOGGER.error(" Time for cache population to Finish:"+ (end1-start));
	//	long start1 = System.currentTimeMillis();
		processor.createOrUpdateTouchCoordinate(result, record.getTdlogrecordDbIdx(), record.getTouchdownDbIdx());
		// end1 = System.currentTimeMillis();
		// LOGGER.error(" Time for Calculation and merging to Finish:"+ (end1-start1));
		 long end = System.currentTimeMillis(); 
		long observableTime = end - start;
		LOGGER.error(" DataCollector::PushDataEnd() ==> merging jobs:"+ observableTime);
		if (observableTime > data.getPushdataendWaitTime().longValue()) {
			LOGGER.debug(" DataCollector::PushDataEnd() ==> Timeout !!!! waiting jobs:");
			result.setErrorLevel(TdlogResultCode.PUSH_DATA_TIMEOUT.getResultCode());
			result.setDeliveryTime(observableTime);
			return result;
		} 
		result.setDeliveryTime(record.getPushdataDeliveryTime()+observableTime);
		LOGGER.info(" DataCollector::PushDataEnd() All Jobs completed within [ms]: "+observableTime);
		LOGGER.debug("DataCollector::PushDataEnd() preparing PushDataEnd sql!");
		
		DataCollectorConversionUtil.updateTouchDownStateAndTime(record,observableTime,result);
		end = System.currentTimeMillis();
		LOGGER.error(" DataCollector::PushDataEnd() update==>:"+ (end - start));
		return result;
	}
}
