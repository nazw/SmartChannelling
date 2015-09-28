/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.service;

import com.visni.smartchannelling.dao.AppointmentDAO;
import com.visni.smartchannelling.dao.SystemUserDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.VisitingDAO;
import com.visni.smartchannelling.dao.VisitingSoltsDAO;
import com.visni.smartchannelling.entity.Address;
import com.visni.smartchannelling.entity.Appointment;
import com.visni.smartchannelling.entity.CommonDomainProperty;
import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.entity.VisitingSolts;
import com.visni.smartchannelling.util.AccountStatus;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.BookingStatus;
import com.visni.smartchannelling.util.NotifyToContactStatus;
import com.visni.smartchannelling.util.PriorityStatus;
import com.visni.smartchannelling.util.RandomStringGenerator;
import com.visni.smartchannelling.util.UserAccountStatus;
import com.visni.smartchannelling.util.UserRoleType;
import java.util.HashSet;
import java.util.Set;

@Service(value = "AppointmentService")
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private VisitingDAO visitingDAO;
    @Autowired
    private CommonDomainProperty cdp;
    @Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private VisitingSoltsDAO visitingSoltsDAO;
    @Autowired
    private SystemUserDAO systemUserDAO;

    @Override
    public Appointment createAppointment(SystemUserDetail systemUserAllDetail, SystemUser currentUser, String hospitalId, String visitingSlotsId) throws Exception {

        cdp.setCreatedUser(currentUser.getUserId());
        cdp.setCreationDate(Calendar.getInstance().getTime());
        cdp.setLastModifiedDate(Calendar.getInstance().getTime());
        cdp.setLastModifiedUser(currentUser.getUserId());

        SystemUserDetail systemUserDetail = getConstructedSystemUserDetail(systemUserAllDetail);

        VisitingSolts visitingSolts = visitingSoltsDAO.getVisitingSoltsById(visitingSlotsId);
        visitingSolts.setBookingStatus(BookingStatus.BOOKED);

        Appointment appointment = new Appointment();
        appointment.setActiveStatus(ActiveStatus.ACTIVE);
        appointment.setBookingStatus(BookingStatus.BOOKED);
        appointment.setCommonProperties(cdp);
        appointment.setPatient(systemUserDetail.getSystemUser());
        appointment.setVisitingSolt(visitingSolts);

        appointment.setAppointmentId(saveAppointment(appointment));
        return appointment;
    }

    @Override
    public String saveAppointment(Appointment appointment) throws Exception {
        return appointmentDAO.saveAppointment(appointment);
    }

    private SystemUserDetail getConstructedSystemUserDetail(SystemUserDetail systemUserDetail) throws Exception {

        systemUserDetail.setAccountStatus(AccountStatus.ACTIVE);
        systemUserDetail.setCommanDomainProperty(cdp);
        systemUserDetail.setContactNumbers(getConstructedcontactNumbers(systemUserDetail));
        systemUserDetail.setEmailAddresses(getConstructedEmailAddresses(systemUserDetail));
        systemUserDetail.setSystemUser(getConstructedSystemUser(systemUserDetail));
        return systemUserDetail;
    }

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
        systemUser.setUserRoles(getUserRolesForPatient(systemUser));
        return systemUser;
    }

    private Address getConstructedAddress(SystemUser systemUser) throws Exception {
        Address address = systemUser.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setCommonProperties(cdp);
        address.setSystemUser(systemUser);
        return address;
    }

    private Set<UserRole> getUserRolesForPatient(SystemUser systemUser) throws Exception {
        Set<UserRole> userRoles = new HashSet<UserRole>();
        UserRole userRole = appointmentDAO.getUserRoleByUserRoleType(UserRoleType.ROLE_PATIENT);
        userRoles.add(userRole);
        return userRoles;
    }

    private List<EmailAddress> getConstructedEmailAddresses(SystemUserDetail systemUserDetail) throws Exception {
        List<EmailAddress> newContactList = new ArrayList<EmailAddress>();
        List<EmailAddress> emailAddresses = systemUserDetail.getEmailAddresses();
        if (!((emailAddresses == null) || (emailAddresses.isEmpty()))) {
            for (EmailAddress emailAddress : emailAddresses) {
                if (StringUtils.isNotBlank(emailAddress.getEmailAddressValue())) {
                    EmailAddress test = appointmentDAO.getEmailAddressByEmailAddressValue(emailAddress.getEmailAddressValue());
                    if (test == null) {
                        emailAddress.setCommanDomainProperty(cdp);
                        emailAddress.setEmailPriorityStatus(PriorityStatus.PRIMARY);
                        emailAddress.setNotifyToContactStatus(NotifyToContactStatus.YES);
                        emailAddress.setUserDetail(systemUserDetail);
                        newContactList.add(emailAddress);
                    }
                }
            }
        }
        return newContactList;
    }

    private List<ContactNumber> getConstructedcontactNumbers(SystemUserDetail systemUserDetail) throws Exception {
        List<ContactNumber> newContactList = new ArrayList<ContactNumber>();
        List<ContactNumber> contactNumbers = systemUserDetail.getContactNumbers();
        if (!((contactNumbers == null) || (contactNumbers.isEmpty()))) {
            for (ContactNumber contactNumber : contactNumbers) {
                if (StringUtils.isNotBlank(contactNumber.getContactNumberValue())) {
                    ContactNumber test = appointmentDAO.getContactNumberByContactNumberValue(contactNumber.getContactNumberValue());
                    if (test == null) {
                        contactNumber.setCommanDomainProperty(cdp);
                        contactNumber.setContactNumberPriorityStatus(PriorityStatus.PRIMARY);
                        contactNumber.setNotifyToContactStatus(NotifyToContactStatus.YES);
                        contactNumber.setUserDetail(systemUserDetail);
                        newContactList.add(contactNumber);
                    }
                }
            }
        }
        return newContactList;
    }

    @Override
    public String cancelAppointment(String visitingSlotsId) throws Exception {
        return appointmentDAO.cancelAppointment(visitingSlotsId);
    }

    @Override
    public String updateAppointment(String newTimeSlotId, String currentvVsitingSlotsId) throws Exception {
        return appointmentDAO.updateAppointment(newTimeSlotId, currentvVsitingSlotsId);
    }

    @Override
    public String updatePatient(SystemUserDetail systemUserDetail, SystemUser currentUser) throws Exception {

        cdp.setLastModifiedDate(Calendar.getInstance().getTime());
        cdp.setLastModifiedUser(currentUser.getUserId());

        systemUserDetail = getConstructedSystemUserDetailForUpdate(systemUserDetail, currentUser);

        return appointmentDAO.updatePatient(systemUserDetail);
    }

    private SystemUserDetail getConstructedSystemUserDetailForUpdate(SystemUserDetail systemUserDetailAll, SystemUser currentUser) throws Exception {

        SystemUserDetail systemUserDetail = systemUserDAO.getSystemUserDetailById(systemUserDetailAll.getSystemUserDetailId());

        systemUserDetail.setTitle(systemUserDetailAll.getTitle());
        systemUserDetail.setFirstName(systemUserDetailAll.getFirstName());
        systemUserDetail.setLastName(systemUserDetailAll.getLastName());
        systemUserDetail.setMiddleName(systemUserDetailAll.getMiddleName());
        systemUserDetail.setGender(systemUserDetailAll.getGender());
        systemUserDetail.getCommanDomainProperty().setLastModifiedDate(Calendar.getInstance().getTime());
        systemUserDetail.getCommanDomainProperty().setLastModifiedUser(currentUser.getUserId());

        systemUserDetail.getSystemUser().getAddress().setZipCode(systemUserDetailAll.getSystemUser().getAddress().getZipCode());
        systemUserDetail.getSystemUser().getAddress().setStreetNumber(systemUserDetailAll.getSystemUser().getAddress().getStreetNumber());
        systemUserDetail.getSystemUser().getAddress().setStreetName(systemUserDetailAll.getSystemUser().getAddress().getStreetName());
        systemUserDetail.getSystemUser().getAddress().setCity(systemUserDetailAll.getSystemUser().getAddress().getCity());
        systemUserDetail.getSystemUser().getAddress().setState(systemUserDetailAll.getSystemUser().getAddress().getState());
        systemUserDetail.getSystemUser().getAddress().setCountry(systemUserDetailAll.getSystemUser().getAddress().getCountry());
        systemUserDetail.getSystemUser().getAddress().setPostalCode(systemUserDetailAll.getSystemUser().getAddress().getPostalCode());
        systemUserDetail.getSystemUser().getAddress().getCommonProperties().setLastModifiedDate(Calendar.getInstance().getTime());
        systemUserDetail.getSystemUser().getAddress().getCommonProperties().setLastModifiedUser(currentUser.getUserId());


        systemUserDetail.setContactNumbers(getConstructedcontactNumbersForUpdate(systemUserDetail, systemUserDetailAll, currentUser));
        systemUserDetail.setEmailAddresses(getConstructedEmailAddressesForUpdate(systemUserDetail, systemUserDetailAll, currentUser));

        return systemUserDetail;
    }

    private List<ContactNumber> getConstructedcontactNumbersForUpdate(SystemUserDetail systemUserDetail, SystemUserDetail systemUserDetailAll, SystemUser currentUser) throws Exception {

        Date now = Calendar.getInstance().getTime();
        cdp.setCreationDate(now);
        cdp.setLastModifiedDate(now);
        cdp.setLastModifiedUser(currentUser.getUserId());
        cdp.setCreatedUser(currentUser.getUserId());

        List<ContactNumber> newContactList = new ArrayList<ContactNumber>();
        List<ContactNumber> contactNumbers = systemUserDetailAll.getContactNumbers();
       
        if (!((contactNumbers == null) || (contactNumbers.isEmpty() || contactNumbers.size() == 0))) {
            for (ContactNumber contactNumber : contactNumbers) {
                if (StringUtils.isNotBlank(contactNumber.getContactNumberValue())) {
                    ContactNumber cNo = appointmentDAO.getContactNumberByContactNumberValue(contactNumber.getContactNumberValue());
                    if (cNo == null) {
                        if (StringUtils.isNotBlank(contactNumber.getContactNumberId())) {
                            ContactNumber conNumber = new ContactNumber();
                            conNumber = appointmentDAO.getContactNumberById(contactNumber.getContactNumberId());
                            conNumber.setContactNumberValue(contactNumber.getContactNumberValue());
                            conNumber.getCommanDomainProperty().setLastModifiedDate(now);
                            conNumber.getCommanDomainProperty().setLastModifiedUser(currentUser.getUserId());
                            newContactList.add(conNumber);
                        } else {

                            ContactNumber cNumber = new ContactNumber();
                            cNumber.setContactNumberValue(contactNumber.getContactNumberValue());
                            cNumber.setNotifyToContactStatus(NotifyToContactStatus.YES);
                            cNumber.setContactNumberPriorityStatus(PriorityStatus.SECONDARY);
                            cNumber.setCommanDomainProperty(cdp);
                            cNumber.setUserDetail(systemUserDetail);
                            newContactList.add(cNumber);
                        }
                    }
                }else if(StringUtils.isBlank(contactNumber.getContactNumberValue()) && StringUtils.isNotBlank(contactNumber.getContactNumberId())){
                     appointmentDAO.deleteContactNumberById(contactNumber.getContactNumberId());
                }
            }
        }
        return newContactList;
    }

    private List<EmailAddress> getConstructedEmailAddressesForUpdate(SystemUserDetail systemUserDetail, SystemUserDetail systemUserDetailAll, SystemUser currentUser) throws Exception {

        Date now = Calendar.getInstance().getTime();
        cdp.setCreationDate(now);
        cdp.setLastModifiedDate(now);
        cdp.setLastModifiedUser(currentUser.getUserId());
        cdp.setCreatedUser(currentUser.getUserId());

        List<EmailAddress> newContactList = new ArrayList<EmailAddress>();
        List<EmailAddress> emailAddresses = systemUserDetailAll.getEmailAddresses();
       
        if (!((emailAddresses == null) || (emailAddresses.isEmpty()))) {
            for (EmailAddress emailAddress : emailAddresses) {
                if (StringUtils.isNotBlank(emailAddress.getEmailAddressValue())) {
                    EmailAddress mail = appointmentDAO.getEmailAddressByEmailAddressValue(emailAddress.getEmailAddressValue());
                    if (mail == null) {
                        if (StringUtils.isNotBlank(emailAddress.getEmailAddressId())) {
                            EmailAddress email = new EmailAddress();
                            email = appointmentDAO.getEmailAddressById(emailAddress.getEmailAddressId());
                            email.setEmailAddressValue(emailAddress.getEmailAddressValue());
                            email.getCommanDomainProperty().setLastModifiedUser(currentUser.getUserId());
                            email.getCommanDomainProperty().setLastModifiedDate(now);
                            newContactList.add(email);
                        } else {
                            EmailAddress emailAdd = new EmailAddress();
                            emailAdd.setEmailAddressValue(emailAddress.getEmailAddressValue());
                            emailAdd.setNotifyToContactStatus(NotifyToContactStatus.YES);
                            emailAdd.setEmailPriorityStatus(PriorityStatus.SECONDARY);
                            emailAdd.setCommanDomainProperty(cdp);
                            emailAdd.setUserDetail(systemUserDetail);
                            newContactList.add(emailAdd);

                        }

                    }
                }else if(StringUtils.isBlank(emailAddress.getEmailAddressValue()) && StringUtils.isNotBlank(emailAddress.getEmailAddressId())){
                      appointmentDAO.deleteEmailAddressById(emailAddress.getEmailAddressId());
                }
            }
        }





        return newContactList;
    }
}
