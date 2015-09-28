/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.entity.VisitingSolts;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author visni
 */
public interface VisitingSoltsDAO {
    
     public VisitingSolts getVisitingSoltsById(String visitingSlotsId)throws Exception;
         public String activateVisitingSlot(String visitingSlotsId)throws Exception;
          public String cancelVisitingSlot(String visitingSlotsId)throws Exception;
            public List<Map> getPatientByVisitingSlotId(String visitingSlotsId)throws Exception;
            public List<Map> findTimeSlotsForDoctor(String firstName,String lastName,String specializationId,Date startDate,String hospitalId)throws Exception;
    
            public SystemUserDetail getSystemUserDetailByVisitingSlotId(String visitingSlotsId)throws Exception;
            
}
