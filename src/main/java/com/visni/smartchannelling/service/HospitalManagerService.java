package com.visni.smartchannelling.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.util.AccountStatus;
import com.visni.smartchannelling.util.ActiveStatus;

public interface HospitalManagerService {
	
	/**
	 * @param hospitalManager
	 * @param updatedUserId
	 * @return
	 * @throws Exception
	 */
	public String updateHospitalManagerDetails(HospitalManager hospitalManager, String updatedUserId)throws Exception;
	
	
	/**
	 * @param accountStatus
	 * @return
	 * @throws Exception
	 */
	 /**
     * @param hospitalId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public List<Hospital> getAllAvailableHospitalsByStatus(AccountStatus accountStatus)throws Exception;

}
