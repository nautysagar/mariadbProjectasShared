package com.nashtech.dataCollector.enums;

public enum TdlogRecordState {
	RECORD_INIT,					/**< (0) record creation ongoing by data collection process */
	RECORD_CREATED,					/**< (1) data-set created from and ready for delivering */
	RECORD_RUNSTART_PROCESSING,     /**< (2) delivery process started for RunStart Record */
	RECORD_RUNSTART_SUCCESS,		/**< (3) RunStart Record Successfully delivered */
	RECORD_RUNSTART_FAILED_RETRY,   /**< (4) RunStart Record Delivery failed */
	RECORD_RUNSTART_FAILED,         /**< (5) RunStart Record Delivery failed */
	RECORD_UIDSTREAM_PROCESSING,    /**< (6) LogUidStream delivery started / ongoing */
	RECORD_UIDSTREAM_SUCCESS,       /**< (7) LogUidStream successfully delivered */
	RECORD_UIDSTREAM_FAILED_RETRY,  /**< (8) LogUidStream delivery failed */
	RECORD_UIDSTREAM_FAILED,        /**< (9) LogUidStream delivery failed */
	RECORD_RUNFINISH_PROCESSING,    /**< (10) RunFinished Record delivery started */
	RECORD_RUNFINISH_SUCCESS,       /**< (11) RunFinish Record delivery successful */
	RECORD_RUNFINISH_FAILED_RETRY,  /**< (12) RunFinished Record delivery failed */
	RECORD_RUNFINISH_FAILED,        /**< (13) RunFinished Record delivery failed */
	RECORD_DELIVERED,               /**< (14) complete data-set delivered successfully */
	RECORD_DELIVERY_FAILED          /**< (15) one error occurred during the whole delivery process */
}
