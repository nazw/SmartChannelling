package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.Address;

public interface AddressDAO {
	
	public Address getAddressById(String id) throws Exception;

}
