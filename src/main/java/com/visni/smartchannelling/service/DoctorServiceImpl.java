/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.visni.smartchannelling.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.DoctorDAO;
import com.visni.smartchannelling.dao.VisitingDAO;
import com.visni.smartchannelling.entity.Address;
import com.visni.smartchannelling.entity.CommonDomainProperty;
import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.Doctor;
import com.visni.smartchannelling.entity.DoctorHospitalRole;
import com.visni.smartchannelling.entity.DoctorSpecialization;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.AccountStatus;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.BookingStatus;
import com.visni.smartchannelling.util.NotifyToContactStatus;
import com.visni.smartchannelling.util.PriorityStatus;
import com.visni.smartchannelling.util.RandomStringGenerator;
import com.visni.smartchannelling.util.UserAccountStatus;
import com.visni.smartchannelling.util.UserRoleType;

/**
 * @author visni
 */
@Service(value = "DoctorService")
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDAO doctorDAO;
    @Autowired
    private CommonDomainProperty cdp;
    @Autowired
    private VisitingDAO visitingDAO;

    @Override
    public String createNewDoctor(Doctor doctor, SystemUser currentUser, String[] hospitalRoleIds, String[] spcailizationIds, String hospitalId)
	    throws Exception {
	cdp.setCreatedUser(currentUser.getUserId());
	cdp.setCreationDate(Calendar.getInstance().getTime());
	cdp.setLastModifiedDate(Calendar.getInstance().getTime());
	cdp.setLastModifiedUser(currentUser.getUserId());

	doctor.setAccountStatus(AccountStatus.ACTIVE);
	doctor.setCommonProperties(cdp);

	// set the specializations to the doctor
	doctor.setDoctorSpecializations(getConstructedDoctorSpecilizationList(doctor, spcailizationIds));

	// set the doctor hospital roles to doctor
	doctor.setDoctorHospitalRoles(getConstructedDoctorHospitalRoleList(doctor, hospitalRoleIds));

	// set the hospital doctors
	doctor.setHospitalDoctors(getConstructedHospitalDoctorList(doctor, hospitalId));

	doctor.setSystemUserDetail(getConstructedSystemUserDetail(doctor));

	return saveDoctor(doctor);
    }

    /**
     * @param doctor
     * @return
     * @throws Exception
     */
    private SystemUserDetail getConstructedSystemUserDetail(Doctor doctor) throws Exception {
	SystemUserDetail systemUserDetail = doctor.getSystemUserDetail();
	systemUserDetail.setAccountStatus(AccountStatus.ACTIVE);
	systemUserDetail.setCommanDomainProperty(cdp);
	systemUserDetail.setContactNumbers(getConstructedcontactNumbers(systemUserDetail));
	systemUserDetail.setDoctor(doctor);
	systemUserDetail.setEmailAddresses(getConstructedEmailAddresses(systemUserDetail));
	systemUserDetail.setSystemUser(getConstructedSystemUser(systemUserDetail));
	return systemUserDetail;
    }

    /**
     * @param systemUserDetail
     * @return
     * @throws Exception
     */
    private List<EmailAddress> getConstructedEmailAddresses(SystemUserDetail systemUserDetail) throws Exception {
	List<EmailAddress> newContactList = new ArrayList<EmailAddress>();
	List<EmailAddress> emailAddresses = systemUserDetail.getEmailAddresses();
	int i=0;
	if (!((emailAddresses == null) || (emailAddresses.isEmpty()))) {
	    for (EmailAddress emailAddress : emailAddresses) {
		if (StringUtils.isNotBlank(emailAddress.getEmailAddressValue())) {
		    EmailAddress test = doctorDAO.getEmailAddressByEmailAddressValue(emailAddress.getEmailAddressValue());
		    if (test == null) {
			emailAddress.setCommanDomainProperty(cdp);
			if(i==0){
				emailAddress.setEmailPriorityStatus(PriorityStatus.PRIMARY);
			}else{
				emailAddress.setEmailPriorityStatus(PriorityStatus.SECONDARY);
			}			
			emailAddress.setNotifyToContactStatus(NotifyToContactStatus.YES);
			emailAddress.setUserDetail(systemUserDetail);
			newContactList.add(emailAddress);
			i++;
		    }
		}
	    }
	}
	return newContactList;
    }

    /**
     * @param systemUserDetail
     * @return
     * @throws Exception
     */
    private List<ContactNumber> getConstructedcontactNumbers(SystemUserDetail systemUserDetail) throws Exception {
	List<ContactNumber> newContactList = new ArrayList<ContactNumber>();
	List<ContactNumber> contactNumbers = systemUserDetail.getContactNumbers();
	if (!((contactNumbers == null) || (contactNumbers.isEmpty()))) {
		int i=0;
	    for (ContactNumber contactNumber : contactNumbers) {
		if (StringUtils.isNotBlank(contactNumber.getContactNumberValue())) {
		    ContactNumber test = doctorDAO.getContactNumberByContactNumberValue(contactNumber.getContactNumberValue());
		    if (test == null) {
			contactNumber.setCommanDomainProperty(cdp);
			if(i==0){
				contactNumber.setContactNumberPriorityStatus(PriorityStatus.PRIMARY);
			}else{
				contactNumber.setContactNumberPriorityStatus(PriorityStatus.SECONDARY);
			}			
			contactNumber.setNotifyToContactStatus(NotifyToContactStatus.YES);
			contactNumber.setUserDetail(systemUserDetail);
			newContactList.add(contactNumber);
			i++;
		    }
		}
	    }
	}
	return newContactList;
    }

    /**
     * @param systemUserDetail
     * @return
     * @throws Exception
     */
    private SystemUser getConstructedSystemUser(SystemUserDetail systemUserDetail) throws Exception {
	SystemUser systemUser = systemUserDetail.getSystemUser();
	if (systemUser == null) {
	    systemUser = new SystemUser();
	}
	systemUser.setAccountStatus(UserAccountStatus.ACTIVE);
	systemUser.setAddress(getConstructedAddress(systemUser));
	systemUser.setCommonDomainProperty(cdp);
	systemUser.setSystemUserDetail(systemUserDetail);
	RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
	systemUser.setPassword(randomStringGenerator.getRandomString());
	systemUser.setUserName(systemUserDetail.getFirstName() + "_" + randomStringGenerator.getRandomString());
	systemUser.setUserRoles(getUserRolesForDocotr(systemUser));
	return systemUser;
    }

    /**
     * @param systemUser
     * @return
     * @throws Exception
     */
    private Set<UserRole> getUserRolesForDocotr(SystemUser systemUser) throws Exception {
	Set<UserRole> userRoles = new HashSet<UserRole>();
	// get the role_user and role_doctor to set to the doctor
	UserRole userRole = doctorDAO.getUserRoleByUserRoleType(UserRoleType.ROLE_DOCTOR);
	userRoles.add(userRole);
	UserRole userRoleUser = doctorDAO.getUserRoleByUserRoleType(UserRoleType.ROLE_USER);
	userRoles.add(userRoleUser);
	return userRoles;
    }

    /**
     * @param systemUser
     * @return
     * @throws Exception
     */
    private Address getConstructedAddress(SystemUser systemUser) throws Exception {
	Address address = systemUser.getAddress();
	if (address == null) {
	    address = new Address();
	}
	address.setCommonProperties(cdp);
	address.setSystemUser(systemUser);
	return address;
    }

    /**
     * @param doctor
     * @param spcailizationIds
     * @return
     * @throws Exception
     */
    private Set<DoctorSpecialization> getConstructedDoctorSpecilizationList(Doctor doctor, String[] spcailizationIds) throws Exception {
	if (!(spcailizationIds == null || spcailizationIds.length <= 0)) {
	    Set<DoctorSpecialization> doctorSpecializations = new HashSet<DoctorSpecialization>();
	    for (String specializationId : spcailizationIds) {
		Specialization specialization = doctorDAO.getSpecializationById(specializationId);
		DoctorSpecialization doctorSpecialization = new DoctorSpecialization();
		doctorSpecialization.setActiveStatus(ActiveStatus.ACTIVE);
		doctorSpecialization.setCommonProperties(cdp);
		doctorSpecialization.setDoctor(doctor);
		doctorSpecialization.setSpecialization(specialization);
		doctorSpecializations.add(doctorSpecialization);
	    }
	    return doctorSpecializations;
	}
	return null;
    }

    /**
     * @param doctor
     * @param hospitalRoleIds
     * @return
     * @throws Exception
     */
    private Set<DoctorHospitalRole> getConstructedDoctorHospitalRoleList(Doctor doctor, String[] hospitalRoleIds) throws Exception {
	if (!(hospitalRoleIds == null || hospitalRoleIds.length <= 0)) {
	    Set<DoctorHospitalRole> doctorHospitalRoles = new HashSet<DoctorHospitalRole>();
	    for (String hospitalRoleId : hospitalRoleIds) {
		DoctorHospitalRole doctorHospitalRole = new DoctorHospitalRole();
		HospitalRole hospitalRole = doctorDAO.getHospitalRoleById(hospitalRoleId);
		doctorHospitalRole.setActiveStatus(ActiveStatus.ACTIVE);
		doctorHospitalRole.setCommonProperties(cdp);
		doctorHospitalRole.setDoctor(doctor);
		doctorHospitalRole.setHospitalRole(hospitalRole);
		doctorHospitalRoles.add(doctorHospitalRole);
	    }
	    return doctorHospitalRoles;
	}
	return null;
    }

    /**
     * @param doctor
     * @param hospitalId
     * @return
     * @throws Exception
     */
    private Set<HospitalDoctor> getConstructedHospitalDoctorList(Doctor doctor, String hospitalId) throws Exception {
	if (StringUtils.isNotBlank(hospitalId)) {
	    Set<HospitalDoctor> hospitalDoctors = doctor.getHospitalDoctors();
	    if (hospitalDoctors == null) {
		hospitalDoctors = new HashSet<HospitalDoctor>();
	    }
	    HospitalDoctor hospitalDoctor = new HospitalDoctor();
	    hospitalDoctor.setActiveStatus(ActiveStatus.ACTIVE);
	    hospitalDoctor.setCommonProperties(cdp);
	    hospitalDoctor.setDoctor(doctor);
	    hospitalDoctor.setHospital(doctorDAO.getHospitalById(hospitalId));
	    hospitalDoctors.add(hospitalDoctor);
	    return hospitalDoctors;
	}
	return null;
    }

    @Override
    public String saveDoctor(Doctor doctor) throws Exception {
	return doctorDAO.saveDoctor(doctor);
    }

    /*
     * (non-Javadoc) We are getting the title, first name, middle name and the last name separately
     * up to here therefore iterate over the list and construct the full name in here
     * 
     * @see
     * com.visni.smartchannelling.service.DoctorService#getAllDoctorsForHospital(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> getAllDoctorsForHospital(String hospitalId) throws Exception {
	List<Map<String, Object>> rawList = doctorDAO.getAllDoctorsForHospital(hospitalId);
	for (Map map : rawList) {
	    String fullName = "";
	    if (!((map.get("title") == null) || (StringUtils.isBlank((String) map.get("title"))))) {
		fullName = (String) map.get("title");
	    }
	    if (!((map.get("firstName") == null) || (StringUtils.isBlank((String) map.get("firstName"))))) {
		fullName = fullName + " " + (String) map.get("firstName");
	    }
	    if (!((map.get("middleName") == null) || (StringUtils.isBlank((String) map.get("middleName"))))) {
		fullName = fullName + " " + (String) map.get("middleName");
	    }
	    if (!((map.get("lastName") == null) || (StringUtils.isBlank((String) map.get("lastName"))))) {
		fullName = fullName + " " + (String) map.get("lastName");
	    }
	    map.put("fullName", fullName);
	}
	return rawList;
    }

    @Override
    public List<Map> getAllActiveDoctors() throws Exception {

	return doctorDAO.getAllActiveDoctors();
    }

    @Override
    public List<Map> getAllDoctorsForAppointment(String hospitalId, String specializationId) throws Exception {
	List<Map> rawList = doctorDAO.getAllDoctorsForAppointment(hospitalId, specializationId);

	System.out.println("hospitalIda" + hospitalId);
	System.out.println("specializationIda" + specializationId);
	System.out.println("rawList.size()" + rawList.size());
	return rawList;
    }

    @Override
    public Doctor getDoctorById(String doctorId) throws Exception {

	return doctorDAO.getDoctorById(doctorId);
    }

    
    @Override
    public Collection<Map> getAllActiveDoctorsForHospitalWithVistingData(String hospitalId)throws Exception {      
    	System.out.println("getAllActiveDoctorsForHospitalWithVistingData");
    	List<Map> results=doctorDAO.getAllActiveDoctorsForHospitalWithVistingData(hospitalId);
    	Map<String,Map> resultsMap=new HashMap<String, Map>();
    	for (Map map : results) {
    		String doctorId=(String) map.get("doctorId");
    		Map<String,Object> doctorMap=resultsMap.get(doctorId);
    		if(doctorMap==null){
    			doctorMap=new HashMap<String, Object>();
    			doctorMap.put("doctorId", doctorId);			
    			String fullName = "";
    		    if (!((map.get("title") == null) || (StringUtils.isBlank((String) map.get("title"))))) {
    			fullName = (String) map.get("title");
    		    }
    		    if (!((map.get("firstName") == null) || (StringUtils.isBlank((String) map.get("firstName"))))) {
    			fullName = fullName + " " + (String) map.get("firstName");
    		    }
    		    if (!((map.get("middleName") == null) || (StringUtils.isBlank((String) map.get("middleName"))))) {
    			fullName = fullName + " " + (String) map.get("middleName");
    		    }
    		    if (!((map.get("lastName") == null) || (StringUtils.isBlank((String) map.get("lastName"))))) {
    			fullName = fullName + " " + (String) map.get("lastName");
    		    }
    		    doctorMap.put("fullName", fullName);
    		}
    		Map<String,String> doctorSpecializations=null;
    		if(!(doctorMap.get("doctorSpecializations")==null)){
    			doctorSpecializations=(Map<String,String>) doctorMap.get("doctorSpecializations");
    		}else{
    			doctorSpecializations=new HashMap<String, String>();
    		}
    		doctorSpecializations.put((String)map.get("doctorSpecializationId"), (String)map.get("name"));
    		doctorMap.put("doctorSpecializations",doctorSpecializations);
    		Map<String,Map<String,Object>> visitingMap=null;
    		if(!(doctorMap.get("visitingsMap")==null)){
    			visitingMap=(Map<String, Map<String, Object>>) doctorMap.get("visitingsMap");
    		}else{
    			visitingMap=new HashMap<String, Map<String,Object>>();
    		}
    		String visitingId=(String) map.get("visitingId");
    		Map<String, Object> visiting=visitingMap.get(visitingId);
    		if(visiting==null){
    			visiting=new HashMap<String, Object>();
    			visiting.put("dayOfWeek", map.get("dayOfWeek"));
    			visiting.put("startTime", map.get("startTime"));
    			visiting.put("endTime", map.get("endTime"));
    			visiting.put("avgTimePerAppointment", map.get("avgTimePerAppointment"));
    			long allSlots=visitingDAO.getActiveVisitingSlotCountByActiveAndbookingStatus(visitingId, ActiveStatus.ACTIVE, null);
    			long vacantSlots=visitingDAO.getActiveVisitingSlotCountByActiveAndbookingStatus(visitingId, ActiveStatus.ACTIVE, BookingStatus.VACANT);
    			long bookedSlots=allSlots-vacantSlots;
    			visiting.put("allSlots", allSlots);
    			visiting.put("vacantSlots", vacantSlots);
    			visiting.put("bookedSlots", bookedSlots);
    			visitingMap.put(visitingId, visiting);
    		} 
    		doctorMap.put("visitings",visitingMap.values());
                doctorMap.put("visitingsMap",visitingMap);
    		resultsMap.put(doctorId, doctorMap);
		}    	
        return resultsMap.values();
    }
    
}
