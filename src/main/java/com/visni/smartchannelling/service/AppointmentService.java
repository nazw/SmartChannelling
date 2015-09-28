/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.service;

import com.visni.smartchannelling.entity.*;
import java.util.List;
import java.util.Map;

import com.visni.smartchannelling.util.ActiveStatus;


public interface AppointmentService {
    
   public Appointment createAppointment(SystemUserDetail systemUserDetail, SystemUser currentUser, String hospitalId,String visitingSlotsId) throws Exception ; 
    
    public String saveAppointment(Appointment appointment)throws Exception ;
    
      public String cancelAppointment(String visitingSlotsId)throws Exception ;
      public String updateAppointment(String newTimeSlotId,String currentvVsitingSlotsId)throws Exception ;
      public String updatePatient(SystemUserDetail systemUserDetail,SystemUser currentUser)throws Exception ;
      
       
    
}
