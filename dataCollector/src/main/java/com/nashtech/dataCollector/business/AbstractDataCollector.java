package com.nashtech.dataCollector.business;

import com.nashtech.dataCollector.pojo.TdlogRecordMO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nashtech.dataCollector.Util.DataCollectorConversionUtil;
import com.nashtech.dataCollector.Util.DatabaseDataCollectorUtil;
import com.nashtech.dataCollector.models.Configuration;
import com.nashtech.dataCollector.pojo.ConfigurationMO;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;

public abstract class AbstractDataCollector {

	@Autowired
	protected DataCollectorPool pool;
	
	protected ConfigurationMO data;
	
	protected TdlogRecordMO record;

	private final String VERSION = "TDlogv3_0.0.1";
	
	protected TdlogExtendResult result;
	
	protected List<Integer> touchdownIds;
	
	protected TouchDownProcessor processor;
	

	public AbstractDataCollector() {
		initializeVariables();
		init();
		

	}

	private void init() {
	//	data.setConfigurationdDbIdx(0);
		data.setConfigurationSetupCalled(false);
		data.setPushdataendWaitTime(new BigDecimal(5000));
	}

	protected void initializeVariables() {

		record  = new TdlogRecordMO();
		data = new ConfigurationMO();
		result = new TdlogExtendResult();
	//	processor = new TouchDownProcessor();
		touchdownIds = new ArrayList<>();
		record.setTouchdownDbIdx(0); //currently i am not using this
		record.setTdlogrecordDbIdx(0);
		record.setTouchdownNumber(0);
		record.setProcessingType(0);
		record.setTpiVersion(VERSION);
		record.setPushdataJob(0);
		record.setPushdataDeliveryTime(0.0);

	}
	

	protected boolean getSystemConfiguration() {
		Configuration config = DatabaseDataCollectorUtil.getDefaultConfiguration(result);
		if (config == null)
			return false;
		DataCollectorConversionUtil.convertConfigurationEntityToMO(config, data);
		return true;
	}
	
	

}
