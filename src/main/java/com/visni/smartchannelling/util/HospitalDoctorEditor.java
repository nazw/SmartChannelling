package com.visni.smartchannelling.util;

import java.beans.PropertyEditorSupport;

import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.service.VisitingService;


/**
 *@purpose to Bind the insurance Object from a String
 * @author Mushtaq
 */
public class HospitalDoctorEditor extends PropertyEditorSupport {

    private final VisitingService visitingService;

    public HospitalDoctorEditor(VisitingService visitingService) {
        this.visitingService = visitingService;
    }

  
    @Override
     public void setAsText(String hospitalDoctorId) throws IllegalArgumentException{
    	HospitalDoctor hospitalDoctor=null;
		try {
			hospitalDoctor = visitingService.getHospitalDoctorById(hospitalDoctorId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        setValue(hospitalDoctor);
    }
}
