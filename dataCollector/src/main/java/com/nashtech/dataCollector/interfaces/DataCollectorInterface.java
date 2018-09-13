package com.nashtech.dataCollector.interfaces;

import java.util.List;

import com.nashtech.dataCollector.pojo.TdlogDataBlock;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;
import com.nashtech.dataCollector.pojo.TdlogResult;

public interface DataCollectorInterface {

	/**
	 * customer specific configuration for the next run to address user defined
	 * REST-API interface, this method has to be called before the RunStart is
	 * called
	 *
	 * @brief ConfigurationSetup() is used in order to enable the Test-Program
	 *        Engineer (TPE) to overrule general production setup for debug purpose.
	 *        Production setup is fixed to be done via RestAPI. This function is
	 *        optional for use in test program. In case an error occurs, this will
	 *        be feedback to test program as integer (<0), else 0 as well as error
	 *        message.
	 *
	 * @param restApiUrlPath
	 *            - customer specific REST-API URL interface like
	 *            "http://<hostname>:5005/v1"
	 * @return TdlogResult - including the potential errorCode and the corresponding
	 *         ResultMessage - in case of success = 0; Success
	 */
	public TdlogResult configurationSetup(String restApiUrlPath);

	/**
	 * customer specific configuration for the next run. this method has to be
	 * called before the RunStart is called
	 *
	 * @brief ConfigurationSetup() is used in order to enable the Test-Program
	 *        Engineer (TPE) to overrule general production setup for debug purpose.
	 *        Production setup is fixed to be done via RestAPI. This function is
	 *        optional for use in test program. In case an error occurs, this will
	 *        be feedback to test program as integer (<0), else 0 as well as error
	 *        message.
	 *
	 * @param restApiUrlPath
	 *            - customer specific REST-API URL interface like
	 *            "http://<hostname>:5005/v1"
	 * @param writeToFile
	 *            - flag (0/1) to activate the write the json records to file (1);
	 *            or send this to the REST-API (0)
	 * @param uidLogFilePath
	 *            - path of logfile - will only be used if writeToFile is activated
	 *            (1)
	 * @param uidLogFileVersion
	 *            - file extension / version - will only be used if writeToFile is
	 *            activated (1)
	 * @param debugLevel
	 *            - see enum ErrorHandler::DebugLevel;
	 *
	 * @return TdlogResult - including the potential errorCode and the corresponding
	 *         ResultMessage - in case of success = 0; Success
	 *
	 */
	public TdlogResult configurationSetup(String restApiUrlPath, boolean writeToFile, String uidLogFilePath,
			String uidLogFileVersion, int debugLevel);

	/**
	 * @brief copyConfiguration() will copy the default configuration
	 *        (tglogng.configuration) to allow the modification only for the
	 *        corresponding run. This method is called from
	 *
	 * @see DataCollector::ConfigurationSetup()
	 */
	//public boolean copyConfiguration();

	/**
	 * checkInputParameter will verify one string parameter whether it's filled and
	 * the max length is not reached.
	 * 
	 * @param paramName
	 *            - input string parameter
	 * @param name
	 *            - parameter name which is used in the error report
	 * @param paramSize
	 *            - max string length
	 *
	 * @return true if check was ok, otherwise false
	 */
	//public boolean copyConfiguration(String paramName, String name, int paramSize);

	/**
	 * RunStart is called at the beginning of every Test initializing the database
	 * with all relevant parameters which needs to be transferred to the TdlogNg
	 * post processing center
	 *
	 * @brief Each data package, for wafer level test meaning one single wafer for
	 *        final package level test this might be a SFC-lot, is initiated with
	 *        RunStart() function. Here all needed descriptive parameters are handed
	 *        over for later uniqueness checks in TDLog-DB. RunStart() will initiate
	 *        check if online connection to RestAPI-interface can be established.
	 *        Important to mention here is the new enumerator which will allow
	 *        sending a data block splitted in several parts and concatenating in
	 *        TDLog-library for transfer to RestAPI. Furthermore RunStart() will
	 *        trigger generation of a RUNGROUP_UUID to be used for the package.
	 *        Return value RunStart() will be version of TDLog-Library (>0) and in
	 *        case interface connection cant be established or other issues an error
	 *        code (<0), else 0, together with error statement.
	 *
	 * @param ocr
	 *            - ocr param
	 * @param diffusionLot
	 *            - diffusionLot param
	 * @param sfcLot
	 *            - sfcLot param
	 * @param productName
	 *            - product Name param
	 * @param waferNumber
	 *            - wafer Number param
	 * @param testStage
	 *            - testStage param
	 * @param nc12
	 *            - nc12 param
	 * @param tpName
	 *            - test Program Name param
	 * @param tpVersion
	 *            - test program version param
	 * @param processingType
	 *            - processing Type param = { Wafer; Reel }
	 * @param dataBlockIdentitier
	 *            - dataBlockIdentifier param - will not be used in the future
	 * @param dataBlockDefinitionVersion-??
	 * @param List<TdlogDataBlock>
	 *            - allowed datablockes @see TdlogDataBlock
	 *
	 *
	 * @return version of TDLog-Library (>0) and in case interface connection can't
	 *         be established or other issues an error code (<0), else 0, together
	 *         with error statement. @see TdlogResult
	 */
	public TdlogResult runStart(String ocr, String diffLot, String sfcLot, String productName, int waferNumber,
			String testStage, String nc12, String tpName, String tpVersion, String processingType,
			String dataBlockIdentitier, int dataBlockDefinitionVersion, List<TdlogDataBlock> dataBlockParts);

	/**
	 * @brief PushDataStart() will trigger incrementation of a counter counting test
	 *        of a set of dies (touch-down), this counter will be used in PushData()
	 *        function as touch-down number (unsigned int touchdownNumber) which can
	 *        be optionally provided as well by the test program. Main functionality
	 *        of PushDataStart() is initiation of data collection during one
	 *        touch-down.
	 *
	 * @return value in case of error (<0) else 0 and error message. @see
	 *         TdlogResult
	 */
	public TdlogResult pushDataStart();

	/**
	 * public interface procedure to store the data records (WaferRun) inside the
	 * mysql database
	 *
	 * @brief Test program can send data to TDLog-library in one or multiple blocks.
	 *        This is done with PushData() function. For concatenation purposes
	 *        dataBlockIndex from enumerator is added as parameter. Pushing of data
	 *        is expected to be done within loops over active test sites. PushData()
	 *        is meant to be used only at the end of test flow in order to ensure
	 *        data transmission of pass (valid) dies only. At the end this relies to
	 *        responsibility of TPE. In case an error occurs, this will be feedback
	 *        to test program as integer (<0), else 0 as well as error message.
	 *
	 * @param siteNumber
	 *            int
	 * @param xCoordinate
	 *            from the die
	 * @param yCoordinate
	 *            form the die
	 * @param dataBlockIndex
	 *            (EID, CPLC, MFUID....)
	 * @param dataBlockContent
	 *            Content (UID Stream)
	 *
	 * @return - TdlogResult (Errorcode and Error Message) negative value in case of
	 *         errors; 0 = successful / no error;
	 */
	//public TdlogResult pushData(int siteNumber, int xCoordinate, int yCoordinate, String dataBlockIndex,
	//		String dataBlockContent);

	/**
	 * public interface procedure to store the data records (WaferRun) inside the
	 * mysql database
	 *
	 * @brief Test program can send data to TDLog-library in one or multiple blocks.
	 *        This is done with PushData() function. For concatenation purposes
	 *        dataBlockIndex from enumerator is added as parameter. Pushing of data
	 *        is expected to be done within loops over active test sites. PushData()
	 *        is meant to be used only at the end of test flow in order to ensure
	 *        data transmission of pass (valid) dies only. At the end this relies to
	 *        responsibility of TPE. In case an error occurs, this will be feedback
	 *        to test program as integer (<0), else 0 as well as error message.
	 *
	 * @param siteNumber
	 *            int
	 * @param deviceNumber
	 *            -overloaded function, variant for final test (ReeLRun);
	 *            x_coordinate = deviceNumber; y_coordinate=0
	 * @param dataBlockIndex
	 *            -(EID, CPLC, MFUID....)
	 * @param dataBlockContent
	 *            -(UID Stream)
	 * @return TdlogResult - (Errorcode and Error Message) negative value in case of
	 *         errors; 0 = successful / no error;
	 */
	public TdlogResult pushData(int siteNumber, int deviceNumber, String dataBlockIndex, String dataBlockContent);

	/**
	 * private interface procedure to store the data records (WaferRun) inside the
	 * mysql database
	 *
	 * @brief Test program can send data to TDLog-library in one or multiple blocks.
	 *        This is done with PushData() function. For concatenation purposes
	 *        dataBlockIndex from enumerator is added as parameter. Pushing of data
	 *        is expected to be done within loops over active test sites. PushData()
	 *        is meant to be used only at the end of test flow in order to ensure
	 *        data transmission of pass (valid) dies only. At the end this relies to
	 *        responsibility of TPE. In case an error occurs, this will be feedback
	 *        to test program as integer (<0), else 0 as well as error message.
	 *
	 * @param siteNumber
	 *            int
	 * @param xCoordinate
	 *            from the die
	 * @param yCoordinate
	 *            form the die
	 * @param dataBlockIndex
	 *            index to (EID, CPLC, MFUID....)
	 * @param dataBlockContent
	 *            (UID Stream)
	 *
	 * @return - TdlogResult (Errorcode and Error Message) negative value in case of
	 *         errors; 0 = successful / no error;
	 */
	public TdlogResult pushData(int siteNumber, int xCoordinate, int yCoordinate, int dataBlockIndex,
			String dataBlockContent);

	/**
	 * public interface procedure to store the data records (ReelRun) inside the
	 * mysql database
	 *
	 * @brief Test program can send data to TDLog-library in one or multiple blocks.
	 *        This is done with PushData() function. For concatenation purposes
	 *        dataBlockIndex from enumerator is added as parameter. Pushing of data
	 *        is expected to be done within loops over active test sites. PushData()
	 *        is meant to be used only at the end of test flow in order to ensure
	 *        data transmission of pass (valid) dies only. At the end this relies to
	 *        responsibility of TPE. In case an error occurs, this will be feedback
	 *        to test program as integer (<0), else 0 as well as error message.
	 *
	 * @param siteNumber
	 *            int
	 * @param deviceNumber
	 *            -overloaded function, variant for final test (ReeLRun);
	 *            x_coordinate = deviceNumber; y_coordinate=0
	 * @param dataBlockIndex
	 *            index to (EID, CPLC, MFUID....)
	 * @param -
	 *            dataBlockConten (UID Stream)
	 * @return TdlogResult (Errorcode and Error Message) negative value in case of
	 *         errors; 0 = successful / no error;
	 */
//	public TdlogResult pushData(int siteNumber, int deviceNumber, int dataBlockIndex, String dataBlockContent);

	/**
	 * @brief PushDataEnd() function closes data collection for one touch-down and
	 *        returns back touch-down number (generated or defined one) and amount
	 *        of complete data blocks. Complete here means data blocks which can
	 *        completely rebuild according enumerator. Situation might be that
	 *        TDLog-library gets partly data for devices which are deactivated later
	 *        in time. PushDataEnd() will trigger generation of a TRANSPORT_UUID.
	 *        Besides complete data blocks count an error level (<0) is feedback as
	 *        integer, 1 in case of found duplicate within actual touch-down, else
	 *        0. Error level is considering buffered processing errors from RestAPI.
	 *        Additionally, error message is returned
	 *
	 * @return - TdlogResult (Errorcode and Error Message) negative value in case of
	 *         errors; 0 = successful / no error;
	 */
	public TdlogExtendResult pushDataEnd();

	/**
	 * @brief RunFinish() will finalize the package (wafer or e.g. reel in final
	 *        test).
	 *
	 * @param logCount
	 *            - number of data records
	 *
	 * @return value is error level (<0) and error statement will be returned. 1 in
	 *         case of a duplicate data block was found within package and 0
	 *         elsewise.
	 */
	public TdlogResult runFinish(int logCount);


}
