package com.nashtech.dataCollector.invalid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nashtech.dataCollector.entry.DataCollectorTest;
import com.nashtech.dataCollector.enums.TdlogResultCode;

public class DataCollectorpushDataStartInvalidTest{
	
	@Test
	public void testPushDataStart() {
		int logLevel = DataCollectorTest.dataCollector.pushDataStart().getErrorLevel();
		assertEquals(TdlogResultCode.DC_WRONG_SEQUENCE.getResultCode(), logLevel);
	}

}
