/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.visni.smartchannelling.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.visni.smartchannelling.entity.Doctor;
import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.SystemUser;

/**
 * @author visni
 */
public interface DoctorService {

    /**
     * @param doctor
     * @param currentUser
     * @param spcailizationIds
     * @param hospitalRoleIds
     * @return
     * @throws Exception
     */
    public String createNewDoctor(Doctor doctor, SystemUser currentUser, String[] hospitalRoleIds, String[] spcailizationIds, String hospitalId)
	    throws Exception;

    /**
     * @param doctor
     * @return
     * @throws Exception
     */
    public String saveDoctor(Doctor doctor) throws Exception;

    /**
     * retrieves a list of doctors available for the given hospital
     * 
     * @param hospitalId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAllDoctorsForHospital(String hospitalId) throws Exception;

    public List<Map> getAllActiveDoctors() throws Exception;

    public List<Map> getAllDoctorsForAppointment(String hospitalId, String specializationId) throws Exception;

    public Doctor getDoctorById(String doctorId) throws Exception;

    /**
     * @param hospitalId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_HOSPITAL_MANAGER')")
    public Collection<Map> getAllActiveDoctorsForHospitalWithVistingData(String hospitalId)throws Exception;
}
