package com.nashtech.dataCollector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nashtech.dataCollector.entry.DataCollectorTest;
import com.nashtech.dataCollector.enums.TdlogResultCode;

public class DataCollectorPushDataTest {

	private static final int siteNumber = 0;
	private static final int xCoordinate = 0;
	private static final int yCoordinate = 0;
	private static final int dataBlockIndex = 0;
	private static final String dataBlockIndexStr = null;
	private static final String dataBlockContent = "xyz";
	private static final int deviceNumber = 123;

	/*@Test
	public void testPushData() {
		int logLevel = DataCollectorTest.dataCollector
				.pushData(siteNumber, xCoordinate, yCoordinate, dataBlockIndexStr, dataBlockContent).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}*/

	/*@Test
	public void testPushData2() {
		int logLevel = DataCollectorTest.dataCollector
				.pushData(siteNumber, deviceNumber, dataBlockIndexStr, dataBlockContent).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}*/

	@Test
	public void testPushData3() {
		int logLevel = DataCollectorTest.dataCollector
				.pushData(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}

	/*@Test
	public void testPushData4() {
		int logLevel = DataCollectorTest.dataCollector
				.pushData(siteNumber, deviceNumber, dataBlockIndex, dataBlockContent).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}*/

	
	
}
