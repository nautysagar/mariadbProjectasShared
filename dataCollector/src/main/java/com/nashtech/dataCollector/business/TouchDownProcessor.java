package com.nashtech.dataCollector.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nashtech.dataCollector.Util.DataCollectorValidator;
import com.nashtech.dataCollector.Util.DatabaseDataCollectorUtil;
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

	public TouchDownProcessor() {
		tdcCachedRecord = new HashSet<>();
	}

	public synchronized void addCachedRecord(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent) {

		if (DataCollectorValidator.isEmpty(tdcCachedRecord)) {
			tdcCachedRecord
					.add(createTdCordinate(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent));
		} else {
			updateTdCordinate(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent);
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

	private void updateTdCordinate(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent) {

		TouchdownCoordinates tdc = null;
		tdc = tdcCachedRecord.stream().filter(t -> {
			if (t.getxCoordinate() == xCoordinate && t.getyCoordinate() == yCoordinate) {
				return true;
			}
			return false;
		}).findAny().orElse(null);

		if (tdc == null) {

			tdcCachedRecord
					.add(createTdCordinate(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent));
		} else {

			updateTdCordinate(tdc, dataBlockIndex, dataBlockContent);
		}

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
			int touchDownId) {

		TdlogRecord tdlogrecordEntity = DatabaseDataCollectorUtil.getTdlogRecordById(tdlogrecordId, result);
		if (result.getErrorLevel() != 0)
			return result;
		Touchdown touchdown = null;
		List<Touchdown> td = null;

		if (tdlogrecordEntity != null) {
			td = tdlogrecordEntity.getTouchdown();
			if (DataCollectorValidator.isEmpty(td)) {
				result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
				return result;
			}
			touchdown = td.stream().filter(t -> t.getId() == touchDownId).findAny().orElse(null);
		}

		if (touchdown == null) {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			return result;
		}
		long start = System.currentTimeMillis();

		collectAllTouchDown(td);
		long end1 = System.currentTimeMillis();
		LOGGER.error(" Time for collect all touchdown:" + (end1 - start));

		start = System.currentTimeMillis();
		touchdown.setTouchdownCoordinates(startMergingtdCoordeinatesAndData(touchdown.getTouchdownCoordinates()));
		end1 = System.currentTimeMillis();
		LOGGER.error(" Time for merging cache:" + (end1 - start));
		start = System.currentTimeMillis();
		DatabaseDataCollectorUtil.mergeTdLogrecord(tdlogrecordEntity, result);
		end1 = System.currentTimeMillis();
		LOGGER.error(" Time for merging database:" + (end1 - start));

		return result;
	}

	private List<TouchdownCoordinates> startMergingtdCoordeinatesAndData(List<TouchdownCoordinates> tdc) {
		tdcCachedRecord.stream().forEach(action -> {
			indexDuplicatetouchDown(action.getTouchdownData());
		});

		if (DataCollectorValidator.isEmpty(tdc))
			tdc = new ArrayList<>();
		tdc.addAll(tdcCachedRecord);

		return tdc;

	}

	private void collectAllTouchDown(List<Touchdown> td) {
		tddEntityRecord = new ArrayList<>();
		td.stream().forEach(action -> {
			if (action.getTouchdownCoordinates() != null) {
				// tddEntityRecord =
				// td.stream().map(m->m.getTouchdownCoordinates()).flatMap(k->k.stream().map(x->x.getTouchdownData()).flatMap(j->j.stream())).collect(Collectors.toList());
				tddEntityRecord.addAll(action.getTouchdownCoordinates().stream().map(m -> m.getTouchdownData())
						.flatMap(k -> k.stream()).collect(Collectors.toList()));
			}
		});

	}

	private void indexDuplicatetouchDown(List<TouchdownData> ctdd) {
		ctdd.stream().forEach(k -> {
			TouchdownData data = tddEntityRecord.parallelStream().filter(t -> {
				if (t.getKeyOrder() == k.getKeyOrder() && t.getUidStreamData().equals(k.getUidStreamData())) {

					return true;
				}
				return false;
			}).findAny().orElse(null);
			if (data != null)
				k.setRecordUnique(data.getId());
		});

	}

	public void emptyAllCache() {
		tdcCachedRecord.clear();
		tddEntityRecord.clear();
	}

}
