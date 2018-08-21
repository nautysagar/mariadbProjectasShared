package com.nashtech.dataCollector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nashtech.dataCollector.enums.TdlogResultCode;

public class DataCollectorpushDataStartTest{
	
	@Test
	public void testPushDataStart() {
		int logLevel = DataCollectorTest.dataCollector.pushDataStart().getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}

}
