package com.visni.smartchannelling.util;

import java.beans.PropertyEditorSupport;

import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.service.MasterDataService;

public class HospitalSpecializationEditor extends PropertyEditorSupport {
	
	private final MasterDataService masterDataService;
	
	public HospitalSpecializationEditor(MasterDataService masterDataService){
		this.masterDataService = masterDataService;
		
	}
	
	@Override
    public void setAsText(String hospitalSpecializationId) throws IllegalArgumentException {
        HospitalSpecialization hospitalSpecialization = null;
		try {
			hospitalSpecialization = masterDataService.getHospitalSpecializationById(hospitalSpecializationId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setValue(hospitalSpecialization);
    }

}
