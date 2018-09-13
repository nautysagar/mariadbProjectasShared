package com.nashtech.dataCollector.invalid;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.nashtech.dataCollector.DataCollectorConfigurationValidTest;

@RunWith(Suite.class)
@SuiteClasses({
        DataCollectorConfigurationInValidTest.class,
        DataCollectorRunStartInValidTest.class,DataCollectorpushDataStartInvalidTest.class,DataCollectorPushDataInValidTest.class})

public class AllInvalidTests {

}
