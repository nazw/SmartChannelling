/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.service;

import com.visni.smartchannelling.dao.HospitalRoleDAO;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalRole;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author visni
 */
@Service(value = "HospitalRoleService")
public class HospitalRoleServiceImpl implements HospitalRoleService {
    
            @Autowired
    private HospitalRoleDAO hospitalRoleDAO;


    @Override
    public HospitalRole getHospitalRoleInfoById(String hospitalRoleId)throws Exception {
        
        return hospitalRoleDAO.getHospitalRoleInfoById(hospitalRoleId);
    }
    
}
