package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.Util.DatabaseDataCollectorUtil;
import com.nashtech.dataCollector.models.TdlogRecord;
import com.nashtech.dataCollector.pojo.TdlogResult;

public class RunFinishImplemenation extends AbstractDataCollector {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunFinishImplemenation.class);

	public RunFinishImplemenation() {
		super();
	}
	
	
	public TdlogResult runFinish(int logCount) {
		LOGGER.error(" DataCollector::runFinish()");
		TdlogRecord tdlogrecordEntity = DatabaseDataCollectorUtil.getTdlogRecordByRunGroupUUIDId(record.getRungroupUuid(),result);
		if (result.getErrorLevel() != 0) return result;
		DataCollectorConversionUtil.updateFinalRecords(tdlogrecordEntity,logCount,result);
		if (result.getErrorLevel() != 0) return result;
		DataCollectorConversionUtil.checkForInValidTouchDownRecords(tdlogrecordEntity,result); //whats the point of this since we are returning always success
//		pool.shutdown();
		processor.emptyAllCache();
		initializeVariables();
		return result;
	}
}
