package com.visni.smartchannelling.util;

import java.beans.PropertyEditorSupport;

import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.service.MasterDataService;

public class SpecializationEditor extends PropertyEditorSupport {
	
	private final MasterDataService masterDataService;
	
	public SpecializationEditor(MasterDataService masterDataService){
		this.masterDataService = masterDataService;
		
	}
	
	@Override
    public void setAsText(String specializaionId) throws IllegalArgumentException {
        Specialization specialization = null;
		try {
			specialization = masterDataService.getSpecializationById(specializaionId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setValue(specialization);
    }

}
