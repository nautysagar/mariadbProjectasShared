package com.nashtech.dataCollector.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nashtech.dataCollector.models.TdlogRecord;

public class DataCollectorRunnableTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorRunnableTask.class);

	private int siteNumber, xCoordinate, yCoordinate, dataBlockIndex;
	private String dataBlockContent;

	private TdlogRecord record;

	private TouchDownProcessor processor;

	public DataCollectorRunnableTask(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent, TouchDownProcessor processor,TdlogRecord record) {
		this.siteNumber = siteNumber;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.dataBlockIndex = dataBlockIndex;
		this.dataBlockContent = dataBlockContent;
		this.processor = processor;
		this.record = record;
	}

	@Override
	public void run() {
		LOGGER.debug("Started Running the Thread:::" + Thread.currentThread().getName());
		processor.addCachedRecord(siteNumber, xCoordinate, yCoordinate, dataBlockIndex, dataBlockContent,record);
	}

}
