package com.nashtech.dataCollector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Time;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.nashtech.dataCollector.Application;
import com.nashtech.dataCollector.business.ConfigurationImplemenation;
import com.nashtech.dataCollector.business.DataCollectorImplementation;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.interfaces.DataCollectorInterface;
import com.nashtech.dataCollector.models.Configuration;
import com.nashtech.dataCollector.services.ConfiguationDao;
import com.nashtech.dataCollector.services.ConfigurationService;


public class DataCollectorConfigurationValidTest {
	
	private static final String restApiUrlPath = "xyz";
	private static final boolean writeToFile = false;
	private static final String uidLogFilePath = "/tmp/log";
	private static final String uidLogFileVersion = "1.0.0";
	private static final int debugLevel = 0;

	
	@Test
	public void testConfiguration() {
		int logLevel = DataCollectorTest.dataCollector.configurationSetup(restApiUrlPath).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}
	
	@Test
	public void testConfigurationWithParams() {
		int logLevel =DataCollectorTest.dataCollector.configurationSetup(restApiUrlPath, writeToFile, uidLogFilePath, uidLogFileVersion, debugLevel).getErrorLevel();
		assertEquals(TdlogResultCode.OK.getResultCode(), logLevel);
	}
	
	

}
