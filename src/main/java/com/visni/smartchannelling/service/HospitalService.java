package com.visni.smartchannelling.service;

import java.util.List;
import java.util.Map;

import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.SystemUser;

public interface HospitalService {
	
	public Map<String, Object> createHospital(Hospital hospital, String userId) throws Exception;
	
	public Hospital getHospitalInfoById(String hospitalId)throws Exception ;
	
	public EmailAddress getEmailAddressbyId(String id) throws Exception;
	
	public ContactNumber getContactNumberbyId(String id) throws Exception;
	
	/**
	 * @param currentUserId
	 * @param isSuperAdmin 
	 * @return
	 * @throws Exception
	 */
	public List<Map> getAllActiveHospitalsForUser(String currentUserId, boolean isSuperAdmin)throws Exception ;
	
	/**
	 * Update the edited Hospital
	 * @param hospital - that needs to be edited.
	 * @param userId TODO
	 * @throws Exception 
	 */
	public void updateHospital(Hospital hospital, SystemUser systemUser) throws Exception;

	public void deleteHospital(String hospitalId) throws Exception;
	
	public Hospital getHospitalById(String hospitalId)throws Exception;
	
	
	
	public List<EmailAddress> getEmailAddressesByHospitalId(String hospitalId)throws Exception;
	
	
	public List<ContactNumber> getContactNumberByHospitalId(String hospitalId)throws Exception;
	
	
	
	
	

}
