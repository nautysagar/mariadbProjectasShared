package com.nashtech.dataCollector.pojo;

public class TdlogExtendResult extends TdlogResult {

	private int touchDownCount;
	private int completeDataBlocks;
	private double deliveryTime;

	public int getTouchDownCount() {
		return touchDownCount;
	}

	public void setTouchDownCount(int touchDownCount) {
		this.touchDownCount = touchDownCount;
	}

	public int getCompleteDataBlocks() {
		return completeDataBlocks;
	}

	public void setCompleteDataBlocks(int completeDataBlocks) {
		this.completeDataBlocks = completeDataBlocks;
	}

	public double getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(double deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

}
