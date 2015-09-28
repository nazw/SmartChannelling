package com.visni.smartchannelling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.HospitalManagerDAO;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.util.AccountStatus;

@Service("hospitalManagerService")
public class HospitalManagerServiceImpl implements HospitalManagerService {
	
	@Autowired
	private HospitalManagerDAO hospitalManagerDAO;

	@Override
	public String updateHospitalManagerDetails(HospitalManager hospitalManager,
			String updatedUserId) throws Exception {
		
		return hospitalManagerDAO.updateHospitalManagerDetails(hospitalManager, updatedUserId);
	}

	@Override
	public List<Hospital> getAllAvailableHospitalsByStatus(AccountStatus accountStatus) throws Exception {
		return hospitalManagerDAO.getAllAvailableHospitalsByStatus(accountStatus);
	}

}
