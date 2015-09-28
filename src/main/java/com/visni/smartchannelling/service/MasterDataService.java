package com.visni.smartchannelling.service;

import java.util.List;

import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.UserRole;

public interface MasterDataService {
	
	/**
	 * @param specialization
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String addSpecialization(Specialization specialization, String userId) throws Exception;
	
	/**
	 * @param specialization
	 * @param userId
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String updateSpecialization(Specialization specialization, String userId) throws Exception;
	
	/**
	 * @param specializationId
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String deleteSpecialzation(String specializationId) throws Exception;
	
	/**
	 * @param hospitalRole
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String addHospitalRole(HospitalRole hospitalRole, String userId) throws Exception;
	
	/**
	 * @param hospitalRole
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String updateHospitalRole(HospitalRole hospitalRole, String userId) throws Exception;
	
	/**
	 * @param hospitalRoleId
	 * @return
	 * @throws Exception
	 */
	public String deleteHospitalRole(String hospitalRoleId) throws Exception;
	
	public List<Specialization> getAllActiveSpecializations() throws Exception;
	
	public List<HospitalRole> getAllActiveHospitalRole() throws Exception;
	
	public List<UserRole> getAllUserRoles() throws Exception;
	
	public Specialization getSpecializationById(String id)throws Exception;
	
	public HospitalSpecialization getHospitalSpecializationById(String id) throws Exception;

	/**
	 * @param hospitalRoleId
	 * @return
	 * @throws Exception
	 */
	public HospitalRole getHospitalroleById(String hospitalRoleId)throws Exception;

}
