package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;

public interface SystemUserDAO {
	
	public SystemUser getSystemUserByUserName(String userName) throws Exception;
	
	public String updateSystemUserDetails(SystemUserDetail systemUserDetail) throws Exception;
	
	public SystemUserDetail getSystemUserDetailById(String systemUserDetailId) throws Exception;
	
	public SystemUser getSystemUserByUserId(String userId)throws Exception;

}
