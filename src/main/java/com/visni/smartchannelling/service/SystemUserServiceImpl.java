package com.visni.smartchannelling.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.SystemUserDAO;
import com.visni.smartchannelling.entity.CommonDomainProperty;
import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.util.NotifyToContactStatus;
import com.visni.smartchannelling.util.PriorityStatus;

@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {
	
	@Autowired
	private SystemUserDAO systemUserDAO;
	
	@Autowired
	private CommonDomainProperty cdp;

	@Override
	public SystemUser getSystemUserByUserName(String userName) throws Exception {
		
		return systemUserDAO.getSystemUserByUserName(userName);
	}

	@Override
	public String updateSystemUserDetails(SystemUserDetail systemUserDetail,
			String updatedUserId) throws Exception {
		SystemUserDetail systemUserDetailDB = systemUserDAO.getSystemUserDetailById(systemUserDetail.getSystemUserDetailId());
		
		Date now = Calendar.getInstance().getTime();
		cdp.setCreationDate(now);
		cdp.setLastModifiedDate(now);
		cdp.setLastModifiedUser(updatedUserId);
		cdp.setCreatedUser(updatedUserId);
		
		List<ContactNumber> newContactNumberList = new ArrayList<ContactNumber>();
		int i=0;
		for(ContactNumber contactNumber :systemUserDetail.getContactNumbers()){
			if(StringUtils.isNotBlank(contactNumber.getContactNumberValue())){
				contactNumber.setNotifyToContactStatus(NotifyToContactStatus.YES);
				if(i==0){
					contactNumber.setContactNumberPriorityStatus(PriorityStatus.PRIMARY);
				}else{
					contactNumber.setContactNumberPriorityStatus(PriorityStatus.SECONDARY);
				}				
				contactNumber.setCommanDomainProperty(cdp);
				contactNumber.setContactNumberValue(StringUtils.trim(contactNumber.getContactNumberValue()));
				contactNumber.setUserDetail(systemUserDetailDB);
				newContactNumberList.add(contactNumber);
				i++;
			}			
		}
		
		i=0;
		List<EmailAddress> newEmailAddressesList = new ArrayList<EmailAddress>();
		for(EmailAddress emailAddress :systemUserDetail.getEmailAddresses()){
			if(StringUtils.isNotBlank(emailAddress.getEmailAddressValue())){
				if(i==0){
					emailAddress.setEmailPriorityStatus(PriorityStatus.PRIMARY);
				}else{
					emailAddress.setEmailPriorityStatus(PriorityStatus.SECONDARY);
				}	
				emailAddress.setNotifyToContactStatus(NotifyToContactStatus.YES);				
				emailAddress.setCommanDomainProperty(cdp);
				emailAddress.setEmailAddressValue(StringUtils.trim(emailAddress.getEmailAddressValue()));
				emailAddress.setUserDetail(systemUserDetailDB);
				newEmailAddressesList.add(emailAddress);
				i++;
			}			
		}
		systemUserDetailDB.setEmailAddresses(newEmailAddressesList);
		systemUserDetailDB.setContactNumbers(newContactNumberList);
		
		
		return systemUserDAO.updateSystemUserDetails(systemUserDetailDB);
	}

	@Override
	public SystemUserDetail getSystemUserDetailById(String systemUserDetailId)
			throws Exception {
		
		return systemUserDAO.getSystemUserDetailById(systemUserDetailId);
	}

	@Override
	public SystemUser getSystemUserByUserId(String userId) throws Exception {
		return systemUserDAO.getSystemUserByUserId(userId);
	}

}
