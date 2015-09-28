package com.visni.smartchannelling.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.MasterDataDAO;
import com.visni.smartchannelling.entity.CommonDomainProperty;
import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.ActiveStatus;

@Service("masterDataService")
public class MasterDataServiceImpl implements MasterDataService {

    @Autowired
    private MasterDataDAO masterDataDAO;

    @Autowired
    private CommonDomainProperty cdp;

    @Override
    public String addSpecialization(Specialization specialization, String userId) throws Exception {
	specialization.setDescription(StringUtils.trim(specialization.getDescription().trim()));
	specialization.setName(StringUtils.trim(specialization.getName().trim()));
	specialization.setArea(StringUtils.trim(specialization.getArea().trim()));
	specialization.setActiveStatus(ActiveStatus.ACTIVE);

	Date now = Calendar.getInstance().getTime();
	cdp.setCreationDate(now);
	cdp.setLastModifiedDate(now);
	cdp.setLastModifiedUser(userId);
	cdp.setCreatedUser(userId);

	specialization.setCommonProperties(cdp);

	return masterDataDAO.saveSpecialization(specialization);

    }

    @Override
    public String updateSpecialization(Specialization specialization, String userId) throws Exception {

	Specialization specilizationFromDb = masterDataDAO.getSpecializationById(specialization.getSpecializationId());

	specilizationFromDb.setName(specialization.getName());
	specilizationFromDb.setDescription(specialization.getDescription());
	specilizationFromDb.setArea(specialization.getArea());
	specilizationFromDb.getCommonProperties().setLastModifiedDate(new GregorianCalendar().getTime());
	specilizationFromDb.getCommonProperties().setLastModifiedUser(userId);

	return masterDataDAO.updateSpecialization(specilizationFromDb);
    }

    @Override
    public String deleteSpecialzation(String specializationId) throws Exception {
	
	Specialization specialization = masterDataDAO.getSpecializationById(specializationId);
	
	return masterDataDAO.deleteSpecialzation(specialization);
    }

    @Override
    public String addHospitalRole(HospitalRole hospitalRole, String userId) throws Exception {
	hospitalRole.setDescription(StringUtils.trim(hospitalRole.getDescription().trim()));
	hospitalRole.setName(StringUtils.trim(hospitalRole.getName().toUpperCase().trim()));
	hospitalRole.setActiveStatus(ActiveStatus.ACTIVE);

	Date now = Calendar.getInstance().getTime();
	cdp.setCreationDate(now);
	cdp.setLastModifiedDate(now);
	cdp.setLastModifiedUser(userId);
	cdp.setCreatedUser(userId);
	hospitalRole.setCommonProperties(cdp);

	return masterDataDAO.saveHospitalRole(hospitalRole);
    }
    
    @Override
    public String updateHospitalRole(HospitalRole hospitalRole, String userId) throws Exception {       
    	HospitalRole hospitalRoleFromDb = masterDataDAO.getHospitalRoleById(hospitalRole.getHospitalRoleId());	
		hospitalRoleFromDb.getCommonProperties().setLastModifiedUser(userId);
		hospitalRoleFromDb.getCommonProperties().setLastModifiedDate(Calendar.getInstance().getTime());
		hospitalRoleFromDb.setDescription(hospitalRole.getDescription());
		hospitalRoleFromDb.setName(hospitalRole.getName());	
        return masterDataDAO.updateHospitalRole(hospitalRoleFromDb);
    }
    
    @Override
    public String deleteHospitalRole(String hospitalRoleId) throws Exception {
        
	HospitalRole hospitalRole = masterDataDAO.getHospitalRoleById(hospitalRoleId);
	
        return masterDataDAO.deleteHospitalRole(hospitalRole);
    }
    
    @Override
    public List<Specialization> getAllActiveSpecializations() throws Exception {

	return masterDataDAO.getAllActiveSpecializations();
    }

    @Override
    public List<HospitalRole> getAllActiveHospitalRole() throws Exception {

	return masterDataDAO.getAllActiveHospitalRole();
    }

    @Override
    public List<UserRole> getAllUserRoles() throws Exception {

	return masterDataDAO.getAllUserRoles();
    }

    @Override
    public Specialization getSpecializationById(String id) throws Exception {

	return masterDataDAO.getSpecializationById(id);
    }

    @Override
    public HospitalSpecialization getHospitalSpecializationById(String id) throws Exception {

	return masterDataDAO.getHospitalSpecializationById(id);
    }

	@Override
	public HospitalRole getHospitalroleById(String hospitalRoleId)throws Exception {
		return masterDataDAO.getHospitalRoleById(hospitalRoleId);
	}

}
