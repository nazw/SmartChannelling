package com.visni.smartchannelling.service;

import com.visni.smartchannelling.dao.AppointmentDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.HospitalDAO;
import com.visni.smartchannelling.dao.MasterDataDAO;
import com.visni.smartchannelling.entity.Address;
import com.visni.smartchannelling.entity.CommonDomainProperty;
import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.AccountStatus;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.EncryptionUtil;
import com.visni.smartchannelling.util.NotifyToContactStatus;
import com.visni.smartchannelling.util.PriorityStatus;
import com.visni.smartchannelling.util.RandomNumberGenerator;
import com.visni.smartchannelling.util.UserAccountStatus;
import com.visni.smartchannelling.util.UserRoleType;

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private CommonDomainProperty cdp;
    @Autowired
    private RandomNumberGenerator randomNumberGenerator;
    @Autowired
    private MasterDataDAO masterDataDAO;
    @Autowired
    private HospitalDAO hospitalDAO;
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private AppointmentDAO appointmentDAO;

    @Override
    public Map<String, Object> createHospital(Hospital hospital, String userId)
            throws Exception {
        hospital.setDescription(StringUtils.trim(hospital.getDescription()));
        hospital.setHospitalName(StringUtils.trim(hospital.getHospitalName()));
        hospital.setRegistrationNumber(StringUtils.trim(hospital.getRegistrationNumber()));
        hospital.setParentCompanyName(StringUtils.trim(hospital.getParentCompanyName()));
        hospital.setFacilities(StringUtils.trim(hospital.getFacilities()));
        hospital.setWebUrl(StringUtils.trim(hospital.getWebUrl()));
        hospital.setAccountStatus(AccountStatus.ACTIVE);

        Date now = Calendar.getInstance().getTime();
        cdp.setCreationDate(now);
        cdp.setLastModifiedDate(now);
        cdp.setLastModifiedUser(userId);
        cdp.setCreatedUser(userId);

        hospital.setCommonProperties(cdp);
        if (hospital.getEmailAddresses().size() > 0) {
            List<EmailAddress> hospitalMailList = hospital.getEmailAddresses();
            List<EmailAddress> hospitalMailListToSave = new ArrayList<EmailAddress>();
            int i=0;
            for (EmailAddress emailAddress : hospitalMailList) {
                if (StringUtils.isNotBlank(emailAddress.getEmailAddressValue())) {
                    EmailAddress hospitalMail = new EmailAddress();
                    hospitalMail.setCommanDomainProperty(cdp);
                    hospitalMail.setEmailAddressValue(StringUtils.trim(emailAddress.getEmailAddressValue()));                    
                    hospitalMail.setHospital(hospital);
                    if(i==0){
                    	hospitalMail.setEmailPriorityStatus(PriorityStatus.PRIMARY);
                    }else{
                    	hospitalMail.setEmailPriorityStatus(PriorityStatus.SECONDARY);
                    }
                    hospitalMail.setNotifyToContactStatus(NotifyToContactStatus.YES);
                    hospitalMailListToSave.add(hospitalMail);
                    i++;
                }

            }

            hospital.setEmailAddresses(hospitalMailListToSave);
        }

        if (hospital.getContactNumbers().size() > 0) {
            List<ContactNumber> hospitalContactList = hospital.getContactNumbers();
            List<ContactNumber> hospitalContactListToSave = new ArrayList<ContactNumber>();
            int i=0;
            for (ContactNumber contactNumber : hospitalContactList) {
                if (StringUtils.isNotBlank(contactNumber.getContactNumberValue())) {
                    ContactNumber hospitalcontact = new ContactNumber();
                    hospitalcontact.setCommanDomainProperty(cdp);
                    hospitalcontact.setContactNumberValue(StringUtils.trim(contactNumber.getContactNumberValue()));
                    if(i==0){
                    	 hospitalcontact.setContactNumberPriorityStatus(PriorityStatus.PRIMARY);
                    }else{
                    	 hospitalcontact.setContactNumberPriorityStatus(PriorityStatus.SECONDARY);
                    }                   
                    hospitalcontact.setHospital(hospital);
                    hospitalcontact.setNotifyToContactStatus(NotifyToContactStatus.YES);
                    hospitalContactListToSave.add(hospitalcontact);
                    i++;
                }
            }
            hospital.setContactNumbers(hospitalContactListToSave);
        }
        Address hospitalAddress = hospital.getAddress();
        hospitalAddress.setAddressValue(StringUtils.trim(hospitalAddress.getAddressValue()));
        hospitalAddress.setCity(StringUtils.trim(hospitalAddress.getCity()));
        hospitalAddress.setCommonProperties(cdp);
        hospitalAddress.setCountry(StringUtils.trim(hospitalAddress.getCountry()));
        hospitalAddress.setPostalCode(StringUtils.trim(hospitalAddress.getPostalCode()));
        hospitalAddress.setState(StringUtils.trim(hospitalAddress.getState()));
        hospitalAddress.setStreetName(StringUtils.trim(hospitalAddress.getStreetName()));
        hospitalAddress.setStreetNumber(StringUtils.trim(hospitalAddress.getStreetNumber()));
        hospitalAddress.setZipCode(StringUtils.trim(hospitalAddress.getZipCode()));

        SystemUser systemUser = new SystemUser();
        systemUser.setUserName(hospital.getHospitalName().trim() + "_" + randomNumberGenerator.getRandomnumber(3));
        String password = Long.toString(randomNumberGenerator.getRandomnumber(7));
        systemUser.setPassword(encryptionUtil.encrypt(password));

        System.out.println("Before fetching userRolesList");
        List<UserRole> userRolesList = masterDataDAO.getAllUserRoles();
        Set<UserRole> userRoles = new HashSet<UserRole>();

        System.out.println("After fetching userRolesList");
        for (UserRole userRole : userRolesList) {

            if ((userRole.getUserRoleType() == UserRoleType.ROLE_HOSPITAL_MANAGER)
                    || (userRole.getUserRoleType() == UserRoleType.ROLE_USER)) {

                userRoles.add(userRole);

            }

        }

        systemUser.setUserRoles(userRoles);
        systemUser.setAccountStatus(UserAccountStatus.ACTIVE);
        systemUser.setCommonDomainProperty(cdp);

        SystemUserDetail systemUserDetail = new SystemUserDetail();
        systemUserDetail.setAccountStatus(AccountStatus.ACTIVE);
        systemUserDetail.setCommanDomainProperty(cdp);
        systemUserDetail.setSystemUser(systemUser);

        systemUser.setSystemUserDetail(systemUserDetail);

        Address address = new Address();
        address.setCommonProperties(cdp);
        address.setSystemUser(systemUser);

        systemUser.setAddress(address);

        HospitalManager hospitalManager = new HospitalManager();
        hospitalManager.setActiveStatus(ActiveStatus.ACTIVE);
        hospitalManager.setCommonProperties(cdp);
        hospitalManager.setSystemUser(systemUser);
        hospitalManager.setHospital(hospital);

        List<HospitalManager> hospitalManagers = new ArrayList<HospitalManager>();
        hospitalManagers.add(hospitalManager);
        hospital.setHopspitalManagers(hospitalManagers);

        List<HospitalSpecialization> hospitalSpecializations = hospital.getHospitalSpecializations();
        List<Specialization> activeSpecializations = masterDataDAO.getAllActiveSpecializations();
        List<HospitalSpecialization> hospitalSpecializationsToSave = new ArrayList<HospitalSpecialization>();
        if (!(hospitalSpecializations == null)) {
            for (HospitalSpecialization hospitalSpecialization : hospitalSpecializations) {

                hospitalSpecialization.setActiveStatus(ActiveStatus.ACTIVE);
                hospitalSpecialization.setCommonProperties(cdp);
                hospitalSpecialization.setHospital(hospital);

                for (Specialization specialization : activeSpecializations) {
                    if (StringUtils.equals(specialization.getSpecializationId(),
                            hospitalSpecialization.getSpecialization().getSpecializationId())) {

                        hospitalSpecialization.setSpecialization(specialization);

                    }
                }

                hospitalSpecializationsToSave.add(hospitalSpecialization);

            }
        }

        hospital.setHospitalSpecializations(hospitalSpecializationsToSave);


        SystemUserDetail systemUserDetailSaved = hospitalDAO.saveHospital(hospital);

        Map<String, Object> returnValues = new HashMap<String, Object>();
        returnValues.put("systemUserDetail", systemUserDetailSaved);
        returnValues.put("userName", systemUser.getUserName());
        returnValues.put("password", password);

        return returnValues;
    }

    @Override
    public Hospital getHospitalInfoById(String hospitalId) throws Exception {
        return hospitalDAO.getHospitalInfoById(hospitalId);
    }

    @Override
    public EmailAddress getEmailAddressbyId(String id) throws Exception {

        return hospitalDAO.getEmailAddressbyId(id);
    }

    @Override
    public ContactNumber getContactNumberbyId(String id) throws Exception {

        return hospitalDAO.getContactNumberbyId(id);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> getAllActiveHospitalsForUser(String currentUserId, boolean isSuperAdmin) throws Exception {
        return hospitalDAO.getAllActiveHospitalsForUser(currentUserId, isSuperAdmin);
    }

    @Override
    public void updateHospital(Hospital hospital, SystemUser systemUser) throws Exception {

        Date now = Calendar.getInstance().getTime();
        cdp.setCreationDate(now);
        cdp.setLastModifiedDate(now);
        cdp.setLastModifiedUser(systemUser.getUserId());
        cdp.setCreatedUser(systemUser.getUserId());

        Hospital hospitalFromDb = new Hospital();
        hospitalFromDb = hospitalDAO.getHospitalById(hospital.getHospitalId());

        hospitalFromDb.setHospitalName(hospital.getHospitalName());
        hospitalFromDb.setRegistrationNumber(hospital.getRegistrationNumber());
        hospitalFromDb.setParentCompanyName(hospital.getParentCompanyName());
        hospitalFromDb.setDescription(hospital.getDescription());
        hospitalFromDb.setFacilities(hospital.getFacilities());
        hospitalFromDb.setWebUrl(hospital.getWebUrl());
        hospitalFromDb.setCharges(hospital.getCharges());

        hospitalFromDb.getAddress().setAddressValue(hospital.getAddress().getAddressValue());
        hospitalFromDb.getAddress().setZipCode(hospital.getAddress().getZipCode());
        hospitalFromDb.getAddress().setStreetNumber(hospital.getAddress().getStreetNumber());
        hospitalFromDb.getAddress().setStreetName(hospital.getAddress().getStreetName());
        hospitalFromDb.getAddress().setCity(hospital.getAddress().getCity());
        hospitalFromDb.getAddress().setState(hospital.getAddress().getState());
        hospitalFromDb.getAddress().setCountry(hospital.getAddress().getCountry());
        hospitalFromDb.getAddress().setPostalCode(hospital.getAddress().getPostalCode());
        hospitalFromDb.getAddress().getCommonProperties().setLastModifiedDate(Calendar.getInstance().getTime());
        hospitalFromDb.getAddress().getCommonProperties().setLastModifiedUser(systemUser.getUserId());


        hospitalFromDb.getCommonProperties().setLastModifiedUser(systemUser.getUserId());
        hospitalFromDb.getCommonProperties().setLastModifiedDate(Calendar.getInstance().getTime());

        List<HospitalSpecialization> hospitalSpecializations = hospital.getHospitalSpecializations();
        List<Specialization> activeSpecializations = masterDataDAO.getAllActiveSpecializations();
       // List<HospitalSpecialization> hospitalSpecializationsFromDb = hospitalDAO.getHospitalSpecializationByHospitalId(hospitalFromDb.getHospitalId());
        List<HospitalSpecialization> hospitalSpecializationsToSave = new ArrayList<HospitalSpecialization>();

        if (!(hospitalSpecializations == null)) {
            for (HospitalSpecialization hospitalSpecialization : hospitalSpecializations) {
                
                    for (Specialization specialization : activeSpecializations) {
                        if (StringUtils.equals(specialization.getSpecializationId(),
                                hospitalSpecialization.getSpecialization().getSpecializationId())) {

                            HospitalSpecialization hSpecialization = new HospitalSpecialization();
                            hSpecialization.setSpecialization(specialization);
                            hSpecialization.setActiveStatus(ActiveStatus.ACTIVE);
                            hSpecialization.setHospital(hospital);
                            hSpecialization.setCommonProperties(cdp);                    
                            hospitalSpecializationsToSave.add(hSpecialization);
                        }
                    }               

            }
        }

        hospitalDAO.deleteHospitalSpecializationByHospitalId(hospital.getHospitalId());
        hospitalFromDb.setHospitalSpecializations(hospitalSpecializationsToSave);
        
        hospitalFromDb.setContactNumbers(getConstructedcontactNumbersForUpdate(hospital, systemUser,hospitalFromDb));
        hospitalFromDb.setEmailAddresses(getConstructedEmailAddressesForUpdate(hospital, systemUser, hospitalFromDb));
        
        hospitalDAO.updateHospital(hospitalFromDb);
    }

    private List<ContactNumber> getConstructedcontactNumbersForUpdate(Hospital hospital, SystemUser systemUser, Hospital hospitalFromDb) throws Exception {

        Date now = Calendar.getInstance().getTime();
        cdp.setCreationDate(now);
        cdp.setLastModifiedDate(now);
        cdp.setLastModifiedUser(systemUser.getUserId());
        cdp.setCreatedUser(systemUser.getUserId());

        List<ContactNumber> newContactList = new ArrayList<ContactNumber>();
        List<ContactNumber> contactNumbers = hospital.getContactNumbers();
        if (!((contactNumbers == null) || (contactNumbers.isEmpty()))) {
            for (ContactNumber contactNumber : contactNumbers) {
                if (StringUtils.isNotBlank(contactNumber.getContactNumberValue())) {
                    ContactNumber cNo = appointmentDAO.getContactNumberByContactNumberValue(contactNumber.getContactNumberValue());
                    if (cNo == null) {
                        if (StringUtils.isNotBlank(contactNumber.getContactNumberId())) {
                            ContactNumber conNumber = new ContactNumber();
                            conNumber = appointmentDAO.getContactNumberById(contactNumber.getContactNumberId());
                            conNumber.setContactNumberValue(contactNumber.getContactNumberValue());
                            conNumber.getCommanDomainProperty().setLastModifiedDate(now);
                            conNumber.getCommanDomainProperty().setLastModifiedUser(systemUser.getUserId());
                            newContactList.add(conNumber);
                        } else {
                            ContactNumber cNumber = new ContactNumber();
                            cNumber.setContactNumberValue(contactNumber.getContactNumberValue());
                            cNumber.setNotifyToContactStatus(NotifyToContactStatus.YES);
                            cNumber.setContactNumberPriorityStatus(PriorityStatus.SECONDARY);
                            cNumber.setCommanDomainProperty(cdp);
                            cNumber.setHospital(hospitalFromDb);
                            newContactList.add(cNumber);
                        }
                    }
                }
            }
        }
        return newContactList;
    }

    private List<EmailAddress> getConstructedEmailAddressesForUpdate(Hospital hospital, SystemUser systemUser, Hospital hospitalFromDb) throws Exception {

        Date now = Calendar.getInstance().getTime();
        cdp.setCreationDate(now);
        cdp.setLastModifiedDate(now);
        cdp.setLastModifiedUser(systemUser.getUserId());
        cdp.setCreatedUser(systemUser.getUserId());

        List<EmailAddress> newContactList = new ArrayList<EmailAddress>();
        List<EmailAddress> emailAddresses = hospital.getEmailAddresses();
        if (!((emailAddresses == null) || (emailAddresses.isEmpty()))) {
            for (EmailAddress emailAddress : emailAddresses) {
                if (StringUtils.isNotBlank(emailAddress.getEmailAddressValue())) {
                    EmailAddress mail = appointmentDAO.getEmailAddressByEmailAddressValue(emailAddress.getEmailAddressValue());
                    if (mail == null) {
                        if (StringUtils.isNotBlank(emailAddress.getEmailAddressId())) {
                            EmailAddress email = new EmailAddress();
                            email = appointmentDAO.getEmailAddressById(emailAddress.getEmailAddressId());
                            email.setEmailAddressValue(emailAddress.getEmailAddressValue());
                            email.getCommanDomainProperty().setLastModifiedUser(systemUser.getUserId());
                            email.getCommanDomainProperty().setLastModifiedDate(now);
                            newContactList.add(email);
                        } else {
                            EmailAddress emailAdd = new EmailAddress();
                            emailAdd.setEmailAddressValue(emailAddress.getEmailAddressValue());
                            emailAdd.setNotifyToContactStatus(NotifyToContactStatus.YES);
                            emailAdd.setEmailPriorityStatus(PriorityStatus.SECONDARY);
                            emailAdd.setCommanDomainProperty(cdp);
                            emailAdd.setHospital(hospitalFromDb);
                            newContactList.add(emailAdd);

                        }

                    }
                }
            }
        }
        return newContactList;
    }

    @Override
    public void deleteHospital(String hospitalId) throws Exception {

        Hospital hospital = hospitalDAO.getHospitalById(hospitalId);

        hospitalDAO.deleteHospital(hospital);
    }

    @Override
    public Hospital getHospitalById(String hospitalId) throws Exception {

        return hospitalDAO.getHospitalById(hospitalId);
    }

    @Override
    public List<EmailAddress> getEmailAddressesByHospitalId(String hospitalId) throws Exception {
        return hospitalDAO.getEmailAddressesByHospitalId(hospitalId);
    }

    @Override
    public List<ContactNumber> getContactNumberByHospitalId(String hospitalId) throws Exception {
        return hospitalDAO.getContactNumberByHospitalId(hospitalId);
    }
}
