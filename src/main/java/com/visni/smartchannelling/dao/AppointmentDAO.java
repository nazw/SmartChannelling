/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.Appointment;
import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.UserRoleType;

/**
 *
 * @author visni
 */
public interface AppointmentDAO {

    public UserRole getUserRoleByUserRoleType(UserRoleType roleDoctor) throws Exception;

    public EmailAddress getEmailAddressByEmailAddressValue(String emailAddressValue) throws Exception;

    public ContactNumber getContactNumberByContactNumberValue(String contactNumberValue) throws Exception;

    public String saveAppointment(Appointment appointment) throws Exception;

    public String cancelAppointment(String visitingSlotsId) throws Exception;

    public String updateAppointment(String newTimeSlotId, String currentvVsitingSlotsId) throws Exception;

    public String updatePatient(SystemUserDetail systemUserDetail) throws Exception;

    public EmailAddress getEmailAddressById(String emailAddressId) throws Exception;
    
    public ContactNumber getContactNumberById(String contactNumberId) throws Exception;
    
        public void deleteEmailAddressByHospitalId(String hospitalId) throws Exception;
         public void deleteContactNumberByHospitalId(String hospitalId) throws Exception;
         public void deleteEmailAddressBySystemUserDetaild(String systemUserDetailId,String emailAddressId) throws Exception;
         public void deleteContactNumberBySystemUserDetailId(String systemUserDetailId,String contactNumberId) throws Exception;
          public void deleteEmailAddressById(String emailAddressId)throws Exception;
         public void deleteContactNumberById(String contactNumberId) throws Exception;
}
