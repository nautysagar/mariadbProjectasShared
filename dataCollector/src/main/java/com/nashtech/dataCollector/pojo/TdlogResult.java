package com.nashtech.dataCollector.pojo;

public class TdlogResult {

	private int errorLevel;

	private String errorMessage;

	public int getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(int errorLevel) {
		this.errorLevel = errorLevel;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "TdlogResult [errorLevel=" + errorLevel + ", errorMessage=" + errorMessage + "]";
	}
	
	
	

}
