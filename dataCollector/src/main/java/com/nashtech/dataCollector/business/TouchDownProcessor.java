package com.nashtech.dataCollector.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nashtech.dataCollector.Util.DataCollectorValidator;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.models.TdlogRecord;
import com.nashtech.dataCollector.models.Touchdown;
import com.nashtech.dataCollector.models.TouchdownCoordinates;
import com.nashtech.dataCollector.models.TouchdownData;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;

public class TouchDownProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TouchDownProcessor.class);

	private Set<TouchdownCoordinates> tdcCachedRecord;

	private List<TouchdownData> tddEntityRecord;

	// private Map<String,TouchdownData> tddEntityRecord;

	public TouchDownProcessor() {
		tdcCachedRecord = new HashSet<>();
	}

	public synchronized void addCachedRecord(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent, TdlogRecord record) {

		TouchdownCoordinates tdc = null;

		if (DataCollectorValidator.isEmpty(tdcCachedRecord)) {
			tdc = createTdCordinate(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent);
		} else {
			tdc = updateTdCordinate(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent);
		}

		if (tdc != null) {
			indexDuplicatetouchDown(record, tdc);
			tdcCachedRecord.add(tdc);
		}

	}

	private TouchdownCoordinates createTdCordinate(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent) {
		TouchdownCoordinates c = new TouchdownCoordinates(siteNumber, xCoordinate, yCoordinate);
		c.setTouchdownData(createTouchdownData(new ArrayList<TouchdownData>(), dataBlockIndex, dataBlockContent));
		return c;
	}

	private List<TouchdownData> createTouchdownData(List<TouchdownData> tdd, int dataBlockIndex,
			String dataBlockContent) {
		tdd.add(createTouchdownData(dataBlockIndex, dataBlockContent, 0));
		return tdd;

	}

	private static TouchdownData createTouchdownData(int dataBlockIndex, String dataBlockContent, int recordUnique) {
		TouchdownData d = new TouchdownData(dataBlockIndex, dataBlockContent, recordUnique);
		return d;
	}

	private TouchdownCoordinates updateTdCordinate(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent) {

		TouchdownCoordinates tdc = null;
		tdc = tdcCachedRecord.stream().filter(t -> {
			if (t.getxCoordinate() == xCoordinate && t.getyCoordinate() == yCoordinate) {
				return true;
			}
			return false;
		}).findAny().orElse(null);

		if (tdc == null) {
			tdc = createTdCordinate(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent);
		} else {
			updateTdCordinate(tdc, dataBlockIndex, dataBlockContent);
		}
		return tdc;

	}

	private void updateTdCordinate(TouchdownCoordinates tdc, int dataBlockIndex, String dataBlockContent) {

		int recordUnique = updateTouchDownData(tdc.getTouchdownData(), dataBlockIndex, dataBlockContent);
		if (recordUnique == 1)
			tdc.setValid(false);
	}

	private int updateTouchDownData(List<TouchdownData> tdd, int dataBlockIndex, String dataBlockContent) {

		boolean recordUnique = true;
		TouchdownData dd = tdd.stream().filter(t -> {
			if (t.getKeyOrder() == dataBlockIndex) {
				return true;
			}
			return false;
		}).findAny().orElse(null);
		if (dd == null) {
			tdd.add(createTouchdownData(dataBlockIndex, dataBlockContent, 0));
		} else {
			recordUnique = false;
			dd.setRecordUnique(1);
		}

		return recordUnique ? 0 : 1;

	}

	public TdlogExtendResult createOrUpdateTouchCoordinate(TdlogExtendResult result, int tdlogrecordId,
			int touchDownIdx, TdlogRecord enityRecord) {

		Touchdown touchdown = null;
		List<Touchdown> td = null;

		if (enityRecord != null) {
			td = enityRecord.getTouchdown();
			if (DataCollectorValidator.isEmpty(td)) {
				result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
				return result;
			}
			touchdown = td.parallelStream().filter(t -> t.getIdx() == touchDownIdx).findAny().orElse(null);
		}

		if (touchdown == null) {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			return result;
		}

		touchdown.setTouchdownCoordinates(startMergingtdCoordeinatesAndData(touchdown.getTouchdownCoordinates()));

		return result;
	}

	private void indexDuplicatetouchDown(TdlogRecord enityRecord, TouchdownCoordinates tdc) {

		List<Touchdown> td = null;
		if (enityRecord != null) {
			td = enityRecord.getTouchdown();
		}
		if (!DataCollectorValidator.isEmpty(td)) {
			collectAllTouchDownData(td);
			indexDuplicatetouchDown(tdc);
		}
	}

	private void indexDuplicatetouchDown(TouchdownCoordinates tdc) {
		indexDuplicatetouchDown(tdc.getTouchdownData());
	}

	private List<TouchdownCoordinates> startMergingtdCoordeinatesAndData(List<TouchdownCoordinates> tdc) {
		if (DataCollectorValidator.isEmpty(tdc))
			tdc = new ArrayList<>();
		tdc.addAll(tdcCachedRecord);

		return tdc;

	}

	private void collectAllTouchDownData(List<Touchdown> td) {
		if (DataCollectorValidator.isEmpty(tddEntityRecord)) {
			tddEntityRecord = new ArrayList<>();
			td.stream().forEach(action -> {
				if (action.getTouchdownCoordinates() != null) {
					tddEntityRecord.addAll(action.getTouchdownCoordinates().parallelStream()
							.map(m -> m.getTouchdownData()).flatMap(k -> k.stream()).collect(Collectors.toList()));
				}
			});
		}
	}

	private void indexDuplicatetouchDown(List<TouchdownData> ctdd) {
		ctdd.stream().forEach(k -> {
			TouchdownData data = tddEntityRecord.parallelStream().filter(t -> {
				if (t.getKeys().equals(k.getKeys())) {
					return true;
				}
				return false;
			}).findFirst().orElse(null);
			if (data != null) {
				k.setRecordUnique(data.getId());
			}
		});

	}

	public void emptyAllCache() {
		tdcCachedRecord.clear();
		if (!DataCollectorValidator.isEmpty(tddEntityRecord))
			tddEntityRecord.clear();
	}

}
