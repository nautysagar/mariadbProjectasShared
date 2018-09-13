package com.nashtech.dataCollector.entry;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.nashtech.dataCollector.AllValidTests;
import com.nashtech.dataCollector.Application;
import com.nashtech.dataCollector.Util.DatabaseDataCollectorUtil;
import com.nashtech.dataCollector.business.DataCollectorImplementation;
import com.nashtech.dataCollector.business.DataCollectorPool;
import com.nashtech.dataCollector.enums.TdlogRecordState;
import com.nashtech.dataCollector.interfaces.DataCollectorInterface;
import com.nashtech.dataCollector.invalid.AllInvalidTests;
import com.nashtech.dataCollector.models.Configuration;
import com.nashtech.dataCollector.models.TdlogRecord;
import com.nashtech.dataCollector.models.Touchdown;
import com.nashtech.dataCollector.services.ConfiguationDao;
import com.nashtech.dataCollector.services.ConfigurationService;

//@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@RunWith(Suite.class)
@SuiteClasses({ AllValidTests.class,AllInvalidTests.class })
public class DataCollectorTest {

	private static ConfigurationService configService;

	private static ConfiguationDao dao;

	private static boolean runDataInitialization = true;

	private static DataCollectorPool pool;

	private static TdlogRecord record;

	public static DataCollectorInterface dataCollector;

	@BeforeClass
	public static void beforeclass() {
		try {
			dao = Mockito.mock(ConfiguationDao.class);
			configService = Mockito.mock(ConfigurationService.class);
			pool = Mockito.mock(DataCollectorPool.class);
			dataCollector = new DataCollectorImplementation(pool);
			DatabaseDataCollectorUtil util = new DatabaseDataCollectorUtil();
			util.setTdao(dao);
			util.setTconfigService(configService);
			util.init();

			if (runDataInitialization) {
				record = getTdlogRecord();
				when(configService.getByConfigName(Mockito.any())).thenReturn(new Configuration());
				when(configService.getTouchDownDataDefEntity(Mockito.anyString())).thenReturn(null);
				when(configService.getTouchDownDataDefEntity(Mockito.anyString())).thenReturn(null);
				when(configService.getRunDataDefByType(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

				when(configService.getTdlogRecordByGroupUUID(Mockito.anyString())).thenReturn(getTdlogRecordList());
				when(configService.getTdlogRecordByID(Mockito.anyInt())).thenReturn(getTdlogRecordList());
			//	when(configService.getTdlogRecordWithTouchDownByID(Mockito.anyInt())).thenReturn(null);
				when(configService.getTdlogRecordCountByCondition()).thenReturn(0);
				when(configService.updateTdlogRecordFinishUUID(Mockito.anyString())).thenReturn(0);
				when(configService.updateTouchdownState(Mockito.any(TdlogRecordState.class),
						Mockito.any(TdlogRecordState.class))).thenReturn(0);
				when(configService.getRunDataDefByType(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
				when(configService.getRunDataDefByType(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
				when(configService.getTdlogRecordStatus(Mockito.anyInt())).thenReturn(getRecordStatus());
			//	when(configService.updateTouchdownStateAndTime(Mockito.any(), Mockito.anyDouble(), Mockito.anyDouble(),
			//			Mockito.anyInt())).thenReturn(0);
				when(dao.create(Mockito.any())).thenReturn(getTdlogRecord());
				when(dao.merge(Mockito.any())).thenReturn(updateTdlogRecord());
				runDataInitialization = false;
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List<Object[]> getRecordStatus() {
		Object[] obj = { TdlogRecordState.RECORD_UIDSTREAM_PROCESSING, "201" };
		List<Object[]> ll = new ArrayList<>();
		ll.add(obj);
		return ll;
	}

	private static TdlogRecord getTdlogRecord() {
		TdlogRecord r = new TdlogRecord();
		r.setId(123);
		return r;
	}

	private static List<TdlogRecord> getTdlogRecordList() {
		List<TdlogRecord> r = new ArrayList<>();
		r.add(record);
		return r;
	}

	private static TdlogRecord updateTdlogRecord() {
		List<Touchdown> ll = new ArrayList<Touchdown>();
		Touchdown d = new Touchdown();
		d.setId(789);

		ll.add(d);

		// TdlogRecord r = getTdlogRecord();
		record.setTouchdown(ll);

		return record;

	}

}
