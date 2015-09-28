/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.SystemUserDetail;

import java.util.List;
import java.util.Map;

/**
 * @author visni
 */
public interface HospitalDAO {

    public Hospital getHospitalInfoById(String hospitalId) throws Exception;

    public SystemUserDetail saveHospital(Hospital hospital) throws Exception;

    public EmailAddress getEmailAddressbyId(String id) throws Exception;

    public ContactNumber getContactNumberbyId(String id) throws Exception;

    /**
     * @param currentUserId
     * @return
     * @throws Exception
     */
    public List<Map> getAllActiveHospitalsForUser(String currentUserId, boolean isSuperAdmin) throws Exception;

    /**
     * @param hospital
     * @throws Exception
     */
    public void updateHospital(Hospital hospital) throws Exception;

    /**
     * @param hospitalId
     * @return
     * @throws Exception
     */
    public Hospital getHospitalById(String hospitalId) throws Exception;

    /**
     * @param hospital
     * @throws Exception
     */
    public void deleteHospital(Hospital hospital) throws Exception;

      public List<EmailAddress> getEmailAddressesByHospitalId(String hospitalId)throws Exception;
      
      public List<ContactNumber> getContactNumberByHospitalId(String hospitalId)throws Exception;
      
           public List<HospitalSpecialization> getHospitalSpecializationByHospitalId(String hospitalId)throws Exception;
           
            public void deleteHospitalSpecializationByHospitalId(String hospitalId)throws Exception;
}
