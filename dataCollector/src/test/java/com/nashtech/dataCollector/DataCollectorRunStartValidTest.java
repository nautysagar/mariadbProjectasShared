package com.nashtech.dataCollector;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogDataBlock;

public class DataCollectorRunStartValidTest {
	
	
	private static final String ocr = "xyz";
	private static final String diffLot = "xyz";
	private static final String productName = "xyz";
	
	private static  TdlogDataBlock datablock = Mockito.mock(TdlogDataBlock.class);
	private static final List<TdlogDataBlock> dataBlockParts = Arrays.asList(datablock,datablock,datablock,datablock);
	private static final String nc12 = "xyz";
	private static final String tpName = "xyz";
	private static final int dataBlockDefinitionVersion = 0;
	private static final String sfcLot = "xyz";
	private static final int waferNumber = 0;
	private static final String testStage = "xyz";
	private static final String tpVersion ="xyz";
	private static final String processingType = "WAFER";
	private static final String dataBlockIdentitier = "xyz";

	@Test
	public void testRunStart() {		
		int logLevel = DataCollectorTest.dataCollector.runStart(ocr, diffLot, sfcLot, productName, waferNumber, testStage, nc12, tpName, tpVersion, processingType, dataBlockIdentitier, dataBlockDefinitionVersion, dataBlockParts).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}

}
