package com.nashtech.dataCollector.Util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogRecordMO;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;
import com.nashtech.dataCollector.pojo.TdlogResult;

public class DataCollectorValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorValidator.class);

	private DataCollectorValidator() {
	}

	public static boolean checkInputParameter(String paramName, String name, int paramSize, TdlogResult result) {
		boolean status = true;
		if (paramName == null || paramName.equals("") || paramName.length() > paramSize) {
			result.setErrorLevel(TdlogResultCode.DC_INPUT_INCOMPLETE.getResultCode());
			result.setErrorMessage("parameter " + name + " length to long !");
			LOGGER.warn(result.toString());
			status = false;
		}
		return status;
	}

	public static boolean checkTdlogRecordIdx(TdlogExtendResult result, TdlogRecordMO record) {
		boolean flag = true;
		if (record.getTdlogrecordDbIdx() > 0) {
			result.setErrorLevel(TdlogResultCode.DC_WRONG_SEQUENCE.getResultCode());
			LOGGER.warn(result.toString());
			flag = false;
		}

		return flag;
	}

	public static <E> boolean isEmpty(List<E> list) {

		if (list != null && !list.isEmpty())
			return false;
		return true;
	}

	public static <E> boolean isEmpty(Collection<E> c) {
		if (c != null && !c.isEmpty())
			return false;
		return true;
	}
	
	public static <E> boolean isEmpty(String s) {
		if (s != null && !s.equals(""))
			return false;
		return true;
	}
	public static <K,V> boolean isEmpty(Map<K, V> m) {
		if (m != null && !m.isEmpty())
			return false;
		return true;
	}
	

	

}
