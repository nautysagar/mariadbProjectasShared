package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.Util.DatabaseDataCollectorUtil;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;

@Transactional
public class PushDataEndImplemenation extends RunFinishImplemenation {
	private static final Logger LOGGER = LoggerFactory.getLogger(PushDataEndImplemenation.class);

	public PushDataEndImplemenation() {
		super();
	}

	public PushDataEndImplemenation(DataCollectorPool pool) {
		super(pool);
	}

	public TdlogExtendResult pushDataEnd() {
		LOGGER.debug(" DataCollector::PushDataEnd()");
		long start = System.currentTimeMillis();
		pool.executorToFinish();
		processor.createOrUpdateTouchCoordinate(result, record.getTdlogrecordDbIdx(), record.getTouchdownNumber(),
				entity);
		long end = System.currentTimeMillis();
		long observableTime = end - start;
		result.setDeliveryTime(record.getPushdataDeliveryTime() + observableTime);
		DataCollectorConversionUtil.updateTouchDownStateAndTime(record, observableTime, result, entity);

		DataCollectorConversionUtil.mergeTdlogRecord(entity, result);
		if (result.getErrorLevel() != 0) {
			return result;
		}
		end = System.currentTimeMillis();
		observableTime = end - start;
		if (data.getPushdataendWaitTime() != null) {
			if (observableTime > data.getPushdataendWaitTime().longValue()) {
				LOGGER.debug(" DataCollector::PushDataEnd() ==> Timeout !!!! waiting jobs:");
				result.setErrorLevel(TdlogResultCode.PUSH_DATA_TIMEOUT.getResultCode());
				result.setErrorMessage("DataCollector::PushDataEnd() ==> Timeout");
				result.setDeliveryTime(observableTime);
				return result;
			}
		}
		result.setDeliveryTime(record.getPushdataDeliveryTime() + observableTime);
		end = System.currentTimeMillis();
		DataCollectorConversionUtil.updateTouchDownRecordState();
		entity = DatabaseDataCollectorUtil.getTdlogRecordByID(record.getTdlogrecordDbIdx(), result);
		LOGGER.info(" DataCollector::PushDataEnd() All Jobs completed within [ms]:" + (end - start));
		return result;
	}
}
