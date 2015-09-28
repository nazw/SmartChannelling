/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.service;

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
import com.visni.smartchannelling.entity.*;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.BookingStatus;


@Service(value="VisitingSoltsService")
public class VisitingSoltsServiceImpl implements VisitingSoltsService {
    
    	@Autowired
	private VisitingSoltsDAO visitingSoltsDAO;
	
	@Autowired
	private CommonDomainProperty cdp;
        
            @Override
    public String activateVisitingSlot(String visitingSlotsId) throws Exception {
        return visitingSoltsDAO.activateVisitingSlot(visitingSlotsId);

    }
            
                 @Override
    public String cancelVisitingSlot(String visitingSlotsId) throws Exception {
        return visitingSoltsDAO.cancelVisitingSlot(visitingSlotsId);

    }

    @Override
    public List<Map> getPatientByVisitingSlotId(String visitingSlotsId) throws Exception {
       return visitingSoltsDAO.getPatientByVisitingSlotId(visitingSlotsId);
    }

    @Override
    public List<Map> findTimeSlotsForDoctor(String firstName,String lastName, String specializationId, Date startDate, String hospitalId) throws Exception {
        return visitingSoltsDAO.findTimeSlotsForDoctor(firstName,lastName,specializationId,startDate,hospitalId);
    }

    @Override
    public SystemUserDetail getSystemUserDetailByVisitingSlotId(String visitingSlotsId) throws Exception {
        return visitingSoltsDAO.getSystemUserDetailByVisitingSlotId(visitingSlotsId);
    }

    @Override
    public VisitingSolts getVisitingSoltsById(String visitingSlotsId) throws Exception {
        return visitingSoltsDAO.getVisitingSoltsById(visitingSlotsId);
    }
                 
    
    
    
}
