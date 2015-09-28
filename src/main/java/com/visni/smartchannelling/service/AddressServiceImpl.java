package com.visni.smartchannelling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.AddressDAO;
import com.visni.smartchannelling.entity.Address;

@Service("addressService")
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressDAO addressDAO;

	@Override
	public Address getAddressById(String id) throws Exception {
		
		return addressDAO.getAddressById(id);
	}

}
