package com.nashtech.dataCollector.services;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.nashtech.dataCollector.models.TdlogRecord;

@Repository
@Transactional
public class ConfiguationDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguationDao.class);
	

	@Autowired
	EntityManager entityManager;
	
	/**
	   * Save the TdlogRecord in the database.
	   */
	  public int create(TdlogRecord record) {
		LOGGER.debug("Persisting TdlogRecord in dataBase");
	    entityManager.persist(record);
	    entityManager.flush();
	    return record.getId();
	  }
	  
	  /**
	   * Merge the TdlogRecord in the database.
	   */
	  
	  public TdlogRecord merge(TdlogRecord record) {
		  LOGGER.debug("merging TdlogRecord in dataBase");
		  entityManager.merge(record);
		  entityManager.flush();
		  return record;
	  }
	  
	 
		

}
