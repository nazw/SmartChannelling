package com.visni.smartchannelling.dao;

import java.util.List;

import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.entity.VisitingSolts;

public interface BackendProcessDAO {

	/**
	 * @return
	 * @throws Exception
	 */
	public List<VisitingSolts> getAllActiveVisitingSlotsExpired()throws Exception;
	
	/**
	 * @param visitingSlots
	 * @return
	 * @throws Exception
	 */
	public String updateAllExpiredVisitingSlots(List<VisitingSolts> visitingSlots)throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Visiting> getVisitngsToBeCreatedForAnotherThreeMonths() throws Exception;;
	
}
