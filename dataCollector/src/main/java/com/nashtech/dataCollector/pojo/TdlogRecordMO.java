package com.nashtech.dataCollector.pojo;

import java.util.List;

/*This is class is combination of both COnfiguration and TdlogRecord in the database*/

public class TdlogRecordMO {

	int NUMBEROFTHREADS;

	int pushdataJob;
	int tdlogrecordDbIdx; // will be mapped to Id in TdRecord Entity
	int touchdownDbIdx; // will be mapped to touch down Ids
	int touchdownNumber;
	int processingType;
	String tpiVersion;
	String appName;
	String rungroupUuid;
	List<TdlogDataBlock> dataBlock;
	
	double pushdataDeliveryTime;

	/* configuration parameter settings */

	public int getNUMBEROFTHREADS() {
		return NUMBEROFTHREADS;
	}

	public void setNUMBEROFTHREADS(int nUMBEROFTHREADS) {
		NUMBEROFTHREADS = nUMBEROFTHREADS;
	}

	public int getPushdataJob() {
		return pushdataJob;
	}

	public void setPushdataJob(int pushdataJob) {
		this.pushdataJob = pushdataJob;
	}

	

	public int getTdlogrecordDbIdx() {
		return tdlogrecordDbIdx;
	}

	public void setTdlogrecordDbIdx(int tdlogrecordDbIdx) {
		this.tdlogrecordDbIdx = tdlogrecordDbIdx;
	}

	public int getTouchdownDbIdx() {
		return touchdownDbIdx;
	}

	public void setTouchdownDbIdx(int touchdownDbIdx) {
		this.touchdownDbIdx = touchdownDbIdx;
	}

	public int getTouchdownNumber() {
		return touchdownNumber;
	}

	public void setTouchdownNumber(int touchdownNumber) {
		this.touchdownNumber = touchdownNumber;
	}

	public int getProcessingType() {
		return processingType;
	}

	public void setProcessingType(int processingType) {
		this.processingType = processingType;
	}

	
	public String getTpiVersion() {
		return tpiVersion;
	}

	public void setTpiVersion(String tpiVersion) {
		this.tpiVersion = tpiVersion;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRungroupUuid() {
		return rungroupUuid;
	}

	public void setRungroupUuid(String rungroupUuid) {
		this.rungroupUuid = rungroupUuid;
	}

	public List<TdlogDataBlock> getDataBlock() {
		return dataBlock;
	}

	public void setDataBlock(List<TdlogDataBlock> dataBlock) {
		this.dataBlock = dataBlock;
	}
	
	public void incrementTouchDownNumber() {
		touchdownNumber++;
	}

	public double getPushdataDeliveryTime() {
		return pushdataDeliveryTime;
	}

	public void setPushdataDeliveryTime(double pushdataDeliveryTime) {
		this.pushdataDeliveryTime = pushdataDeliveryTime;
	}
	
	public void addPushdataDeliveryTime(double pushdataDeliveryTime) {
		double d = getPushdataDeliveryTime();
		this.pushdataDeliveryTime = pushdataDeliveryTime+d;
	}

}
