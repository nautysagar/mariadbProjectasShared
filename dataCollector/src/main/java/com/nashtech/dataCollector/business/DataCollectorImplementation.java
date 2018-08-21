package com.nashtech.dataCollector.business;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dataCollector.interfaces.DataCollectorInterface;


/**
 * Business implementation of DataCollector Interface
 * 
 * @author vkumar
 *
 */
@Repository
@Transactional
public class DataCollectorImplementation extends ConfigurationImplemenation implements DataCollectorInterface {

	public DataCollectorImplementation() {
		super();
	}

	public DataCollectorImplementation(DataCollectorPool pool) {
		super(pool);
	}

}
