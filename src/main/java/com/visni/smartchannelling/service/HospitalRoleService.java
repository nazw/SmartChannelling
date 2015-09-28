/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.service;

import com.visni.smartchannelling.entity.HospitalRole;

/**
 *
 * @author visni
 */
public interface HospitalRoleService {
    
     public HospitalRole getHospitalRoleInfoById(String hospitalRoleId)throws Exception ;
}
