package com.visni.smartchannelling.util;

import java.beans.PropertyEditorSupport;

import com.visni.smartchannelling.entity.Address;
import com.visni.smartchannelling.service.AddressService;

public class AddressEditor extends PropertyEditorSupport {
	
	private final AddressService addressService;
	
	public AddressEditor(AddressService addressService){
		this.addressService = addressService;
		
	}
	
	@Override
    public void setAsText(String addressId) throws IllegalArgumentException {
        Address address = null;
		try {
			address = addressService.getAddressById(addressId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setValue(address);
    }

}
