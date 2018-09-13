package com.nashtech.dataCollector.invalid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nashtech.dataCollector.entry.DataCollectorInputGenerator;
import com.nashtech.dataCollector.entry.DataCollectorTest;
import com.nashtech.dataCollector.enums.TdlogResultCode;

public class DataCollectorPushDataInValidTest {

	private static final int siteNumber = 0;
	private static final int xCoordinate = 0;
	private static final int yCoordinate = 0;
	private static final int dataBlockIndex = 0;
	private static final String dataBlockIndexStr = null;
	private static final String dataBlockContent = "xyz";
	private static final int deviceNumber = 123;

	
	@Test
	public void testPushDataWithNullDataContent() {
		int logLevel = DataCollectorTest.dataCollector
				.pushData(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, null).getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}
	
	@Test
	public void testPushDataWithEmptyDataContent() {
		int logLevel = DataCollectorTest.dataCollector
				.pushData(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, "").getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}
	
	@Test
	public void testPushDataWithInValidDataContent() {
		int logLevel = DataCollectorTest.dataCollector
				.pushData(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, DataCollectorInputGenerator.getString(257)).getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	
	
}
