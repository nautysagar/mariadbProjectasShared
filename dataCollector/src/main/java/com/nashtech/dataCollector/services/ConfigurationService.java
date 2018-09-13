package com.nashtech.dataCollector.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dataCollector.enums.TdlogRecordState;
import com.nashtech.dataCollector.models.Configuration;
import com.nashtech.dataCollector.models.TdlogRecord;

@Service
@Transactional
public class ConfigurationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationService.class);

	@Autowired
	EntityManager em;

	/**
	 * Return the user having the passed email.
	 */
	public Configuration getByConfigName(String name) {

		LOGGER.debug("Configuration::QUERY_RECORD_BY_CONFIGNAME " + name);

		Query q = em.createNamedQuery("Configuration.GetRecordByConfigName");
		q.setParameter("name", name);
		List<Configuration> result = q.getResultList();
		return result.get(0);

	}

	/**
	 * Return the user having the passed email.
	 * 
	 * @param <T>
	 */
	public <T> List<T> getTouchDownDataDefEntity(String entityName) {

		LOGGER.debug(entityName + "::QUERY_GETALL_RECORDS");

		Query q = em.createNamedQuery(entityName + ".GetAllRecords");

		return (List<T>) q.getResultList();

	}

	/**
	 * Return the user having the passed email.
	 */
	public <T> List<T> getRunDataDefByType(String type, String entityName) {

		LOGGER.debug(entityName + "::QUERY_RECORD_BY_DEF_TYPE; " + type);

		Query q = em.createNamedQuery(entityName + ".GetRecordByDefType");
		q.setParameter("type", type);

		List<T> result = q.getResultList();
		result.stream().forEach(action -> em.refresh(action));
		return result;

	}

	public List<Object[]> getTdlogRecordStatus(int id) {
		LOGGER.debug("TdlogRecord::QUERY_RECORD_STATE_ERROR_CODE_BY_ID " + id);
		em.clear();
		Query q = em.createNamedQuery("TdlogRecord.GetTdlogRecordStateAndErrorCodeById");
		q.setParameter("id", id);
		return q.getResultList();
	}

	public List<TdlogRecord> getTdlogRecordByGroupUUID(String groupId) {
		LOGGER.debug("TdlogRecord::QUERY_RECORD_BY_RUNGROUP_ID " + groupId);
		em.clear();
		Query q = em.createNamedQuery("TdlogRecord.GetTdlogRecordByRunGroupId");
		q.setParameter("rungroupUUID", groupId);
		List<TdlogRecord> result = q.getResultList();
		// result.stream().forEach(action->em.refresh(action));
		return result;
	}

	public List<TdlogRecord> getTdlogRecordByID(int id) {
		LOGGER.debug("TdlogRecord.GetTdlogRecordById " + id);
	//	em.clear();
		Query q = em.createNamedQuery("TdlogRecord.GetTdlogRecordById");
		q.setParameter("id", id);
		List<TdlogRecord> result = q.getResultList();
		// result.stream().forEach(action->em.refresh(action));
		return result;
	}

	/*public List<TdlogRecord> getTdlogRecordWithTouchDownByID(int id) {
		LOGGER.debug("TdlogRecord.GetRecordsWithTouchDown " + id);
		em.clear();
		Query q = em.createNamedQuery("TdlogRecord.GetRecordsWithTouchDown");
		q.setParameter("id", id);
		List<TdlogRecord> result = q.getResultList();
		return result;
	}*/

	public List<Object> getTouchdownId(int tdlogRecordId, int idx) {
		LOGGER.debug("TouchDown::GET_RECORD_ID_BY_TDLOGRECORDID_AND_IDX " + tdlogRecordId + "::" + idx);
		em.clear();
		Query q = em.createNamedQuery("Touchdown.getTouchDownIdByTdlogRecordIdAndIdx");
		q.setParameter("tdlogrecordId", tdlogRecordId);
		q.setParameter("idx", idx);
		return q.getResultList();
	}

	public int getTdlogRecordCountByCondition() {
		LOGGER.debug("TdlogRecord::QUERY_COUNT_RECORD_BY_FINISHTRANSPORTUUID_OR_RECORD_STATE ");
		em.clear();
		Query q = em.createNamedQuery("TdlogRecord.CountRecordByNullFinishUUIDAndRecordState");
		int val = ((Number) q.getSingleResult()).intValue();
		return val;
	}

	/**
	 * update the TdlogRecord in the database.
	 */
	public int updateTdlogRecordFinishUUID(String uId) {
		LOGGER.debug("TdlogRecord::UPDATE_RECORD_FINISHTRANSPORTUUID " + uId);
		Query q = em.createNamedQuery("TdlogRecord.UpdateRecordTransportUUID");
		q.setParameter("uId", uId);
		int result = q.executeUpdate();
		return result;
	}

	/**
	 * update the TouchDown in the database.
	 */
	public int updateTouchdownState(TdlogRecordState recordState, TdlogRecordState updateState) {
		LOGGER.debug("TouchDown:UPDATE_RECORD_BY_STATE ");
		Query q = em.createNamedQuery("Touchdown.UpdateRecordByRecordState");
		q.setParameter("updateState", updateState);
		q.setParameter("recordState", recordState);
		int result = q.executeUpdate();
		return result;
	}

	/**
	 * update the TouchDown table with record state, deliveryTime and peWait Time
	 * database.
	 */
	/*public int updateTouchdownStateAndTime(TdlogRecordState recordState, double deliveryTime, double peWaitTime,
			int id) {
		LOGGER.debug("TouchDown:UPDATE_RECORD_BY_RECORD_STATE_DELIVERYTIME_PETIME ");
		Query q = em.createNamedQuery("Touchdown.UpdateStateDeliveryPeTime");
		q.setParameter("recordState", recordState);
		q.setParameter("deliveryTime", deliveryTime);
		q.setParameter("peWaitTime", peWaitTime);
		q.setParameter("id", id);
		int result = q.executeUpdate();
		return result;

	}*/

}
