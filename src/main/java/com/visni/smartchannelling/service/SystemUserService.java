package com.visni.smartchannelling.service;

import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;

public interface SystemUserService {
	
	public SystemUser getSystemUserByUserName(String userName) throws Exception;
	
	public String updateSystemUserDetails(SystemUserDetail systemUserDetail, String updatedUserId) throws Exception;
	
	public SystemUserDetail getSystemUserDetailById(String systemUserDetailId) throws Exception;

	public SystemUser getSystemUserByUserId(String userId)throws Exception;

}
