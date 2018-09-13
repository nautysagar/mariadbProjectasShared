package com.nashtech.dataCollector.invalid;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import com.nashtech.dataCollector.entry.DataCollectorInputGenerator;
import com.nashtech.dataCollector.entry.DataCollectorTest;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogDataBlock;

public class DataCollectorRunStartInValidTest {

	private static final String ocr = "xyz";
	private static final String diffLot = "xyz";
	private static final String productName = "xyz";

	private static TdlogDataBlock datablock = Mockito.mock(TdlogDataBlock.class);
	private static final List<TdlogDataBlock> dataBlockParts = Arrays.asList(datablock, datablock, datablock,
			datablock);
	private static final String nc12 = "xyz";
	private static final String tpName = "xyz";
	private static final int dataBlockDefinitionVersion = 0;
	private static final String sfcLot = "xyz";
	private static final int waferNumber = 0;
	private static final String testStage = "xyz";
	private static final String tpVersion = "xyz";
	private static final String processingType = "WAFER";
	private static final String dataBlockIdentitier = "xyz";


	@Test
	public void testRunStartWithInvalidOCR() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(DataCollectorInputGenerator.getString(21), diffLot, sfcLot, productName, waferNumber, testStage, nc12, tpName, tpVersion,
						processingType, dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithInvalidDifflot() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(ocr, DataCollectorInputGenerator.getString(21), sfcLot, productName, waferNumber, testStage, nc12, tpName, tpVersion,
						processingType, dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithInvalidProductName() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(ocr, diffLot, sfcLot, DataCollectorInputGenerator.getString(21), waferNumber, testStage, nc12, tpName, tpVersion,
						processingType, dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithInvalidTestStage() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(ocr, diffLot, sfcLot, productName, waferNumber, DataCollectorInputGenerator.getString(21), nc12, tpName, tpVersion,
						processingType, dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithInvalidNc12() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(ocr, diffLot, sfcLot, productName, waferNumber, testStage, DataCollectorInputGenerator.getString(21), tpName, tpVersion,
						processingType, dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithInvalidTpName() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(ocr, diffLot, sfcLot, productName, waferNumber, testStage, nc12, DataCollectorInputGenerator.getString(41), tpVersion,
						processingType, dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithInvalidProcessingType() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(ocr, diffLot, sfcLot, productName, waferNumber, testStage, nc12, tpName, tpVersion, DataCollectorInputGenerator.getString(21),
						dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithNullDataBlockIdentifier() {
		int logLevel = DataCollectorTest.dataCollector.runStart(ocr, diffLot, sfcLot, productName, waferNumber,
				testStage, nc12, tpName, tpVersion, processingType, null, dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithEmptyDataBlockIdentifier() {
		int logLevel = DataCollectorTest.dataCollector.runStart(ocr, diffLot, sfcLot, productName, waferNumber,
				testStage, nc12, tpName, tpVersion, processingType, "", dataBlockDefinitionVersion, dataBlockParts)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithNullDataBlockParts() {
		int logLevel = DataCollectorTest.dataCollector.runStart(ocr, diffLot, sfcLot, productName, waferNumber,
				testStage, nc12, tpName, tpVersion, processingType, dataBlockIdentitier, dataBlockDefinitionVersion, null)
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}

	@Test
	public void testRunStartWithEmptyDataBlockParts() {
		int logLevel = DataCollectorTest.dataCollector
				.runStart(ocr, diffLot, sfcLot, productName, waferNumber, testStage, nc12, tpName, tpVersion, processingType,
						dataBlockIdentitier, dataBlockDefinitionVersion, new ArrayList<TdlogDataBlock>())
				.getErrorLevel();
		assertEquals(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode(), logLevel);
	}
}
