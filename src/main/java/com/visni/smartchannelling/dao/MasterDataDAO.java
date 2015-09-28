package com.visni.smartchannelling.dao;

import java.util.List;

import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.UserRole;

public interface MasterDataDAO {

    public String saveSpecialization(Specialization specialization) throws Exception;

    public String saveHospitalRole(HospitalRole hospitalRole) throws Exception;
    
    public String updateHospitalRole(HospitalRole hospitalRole) throws Exception;
    
    public List<Specialization> getAllActiveSpecializations() throws Exception;

    public List<HospitalRole> getAllActiveHospitalRole() throws Exception;

    public List<UserRole> getAllUserRoles() throws Exception;

    public Specialization getSpecializationById(String id) throws Exception;

    public HospitalSpecialization getHospitalSpecializationById(String id) throws Exception;

    public String updateSpecialization(Specialization specialization) throws Exception;

    public String deleteSpecialzation(Specialization specialization) throws Exception;

    public HospitalRole getHospitalRoleById(String hospitalRoleId) throws Exception;

    public String deleteHospitalRole(HospitalRole hospitalRole)throws Exception;

}
