package com.nashtech.dataCollector.entry;

public class DataCollectorInputGenerator {

	private DataCollectorInputGenerator() {
	}

	public static String getString(int length) {
		if (length < 1)
			return null;

		StringBuffer outputBuffer = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			outputBuffer.append(" ");
		}
		return outputBuffer.toString();

	}

}
