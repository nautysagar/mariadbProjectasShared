package com.nashtech.dataCollector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nashtech.dataCollector.enums.TdlogResultCode;

public class DataCollectorPushDataEndTest {
	
	@Test
	public void testPushDataEnd() {
		int logLevel = DataCollectorTest.dataCollector.pushDataEnd().getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}

}
