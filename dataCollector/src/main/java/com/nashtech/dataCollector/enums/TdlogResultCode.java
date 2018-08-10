package com.nashtech.dataCollector.enums;

public enum TdlogResultCode {
	OK(0),
	SQL_DOUBLE_RECORD_RF(1), /** < double record during RunFinisch detected */
	/**
	 * critical Errors (stop working)
	 */
	SQL_EXCEPTION(-1),
	/** < general sql exception */
	SQL_INSERT_FAULT(-2),
	/** < insertion of new Data record failed */
	SQL_NOT_FOUND(-3),
	/** < sql entry not found (query unsuccessful) */
	SQL_EXECUTE_FAILED(-4),
	/** < sql command execution failed */
	SQL_DB_BUSY(-5),
	/** < database busy */
	SQL_DB_MISUSE(-6),
	/** < wrong database used */
	SQL_FETCH_ERROR(-7),
	/** < fetch data failed */
	SQL_NOT_SPECIFIED(-8),
	/** < unknown sql exception */
	PUSH_DATA_TIMEOUT(-10),
	/** < pushData Timeout */

	DC_START_DELIVERY_FAILED_ERR405(-12),
	/** < delivery failed error 405 received */
	DC_START_DELIVERY_FAILED_ERR408(-13),
	/** < delivery failed error 405 received */
	DC_START_DELIVERY_TIMEOUT(-14), /** < delivery timeout - no feedback from REST-API within 10 sec. */

	/**
	 * Data collecor major Errors stop working
	 */
	DC_INPUT_INCOMPLETE(-101),
	/** < missing or wrong input data, reject request */
	DC_START_DELIVERY_FAILED_ERR500(-102),
	/** < delivery failed error 500 received */
	DC_WRONG_PUSHDATA_INTERFACE(-104),
	/** < wrong Pushdata interface used for Run (Wafer / Reel) */
	DC_WRONG_SEQUENCE(-105), /** < duplicates detected */

	/**
	 * Data Collector warning/minor errors . (continue working)
	 */
	SQL_DOUBLE_RECORD_TDD(-200),
	/** < duplicated touchdown data during PushData record found */
	SQL_CONCURRENT_INSERT(-201);
	/** < can occur in case of multiple parallel inserts. */

	private int value;

	TdlogResultCode(int value) {
		this.value = value;
	}

	public int getResultCode() {
		return value;
	}
}
