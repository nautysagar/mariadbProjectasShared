package com.nashtech.dataCollector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nashtech.dataCollector.enums.TdlogResultCode;

public class DataCollectorRunFinishTest  {
	
	@Test
	public void testRunFinish() {
		int logLevel = DataCollectorTest.dataCollector.runFinish(1111).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}


}
