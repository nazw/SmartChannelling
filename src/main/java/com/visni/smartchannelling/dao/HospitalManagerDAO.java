package com.visni.smartchannelling.dao;

import java.util.List;

import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.util.AccountStatus;

public interface HospitalManagerDAO {
	
	public String updateHospitalManagerDetails(HospitalManager hospitalManager,
			String updatedUserId) throws Exception;
	
	
	/**
	 * @param accountStatus
	 * @return
	 * @throws Exception
	 */
	public List<Hospital> getAllAvailableHospitalsByStatus(AccountStatus accountStatus)throws Exception;

}
