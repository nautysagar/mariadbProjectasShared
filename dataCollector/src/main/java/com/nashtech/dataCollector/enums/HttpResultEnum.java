package com.nashtech.dataCollector.enums;

public enum HttpResultEnum {
	
		HTTP_RESPONSE_405(405),
		HTTP_RESPONSE_408(408),
		HTTP_RESPONSE_500(500);
	
	private int value;

	HttpResultEnum(int value) {
		this.value = value;
	}

	public int getHttpCode() {
		return value;
	}
	
}
