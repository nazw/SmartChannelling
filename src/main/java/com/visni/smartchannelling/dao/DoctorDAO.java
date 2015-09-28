/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import java.util.List;
import java.util.Map;

import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.Doctor;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.UserRoleType;

/**
 * @author visni
 */
public interface DoctorDAO {

    public String saveDoctor(Doctor doctor) throws Exception;

    /**
     * retrieves a list of doctors available for the given hospital
     * 
     * @param hospitalId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAllDoctorsForHospital(String hospitalId) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public Specialization getSpecializationById(String specializationId) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public HospitalRole getHospitalRoleById(String hospitalRoleId) throws Exception;

    /**
     * @param hospitalId
     * @return
     * @throws Exception
     */
    public Hospital getHospitalById(String hospitalId) throws Exception;

    /**
     * @param roleDoctor
     * @return
     * @throws Exception
     */
    public UserRole getUserRoleByUserRoleType(UserRoleType roleDoctor) throws Exception;

    /**
     * @param contactNumberValue
     * @return
     * @throws Exception
     */
    public ContactNumber getContactNumberByContactNumberValue(String contactNumberValue) throws Exception;

    /**
     * @param emailAddressValue
     * @return
     * @throws Exception
     */
    public EmailAddress getEmailAddressByEmailAddressValue(String emailAddressValue) throws Exception;

    public List<Map> getAllActiveDoctors() throws Exception;

    public List<Map> getAllDoctorsForAppointment(String hospitalId, String specializationId) throws Exception;

    public Doctor getDoctorById(String doctorId) throws Exception;

    public List<Map> getAllActiveDoctorsForHospitalWithVistingData(String hospitalId)throws Exception;

}
