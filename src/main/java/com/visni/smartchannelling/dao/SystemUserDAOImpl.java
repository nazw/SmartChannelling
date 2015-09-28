package com.visni.smartchannelling.dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.util.UserAccountStatus;

@Repository("systemUserDAO")
@Transactional(propagation = Propagation.SUPPORTS , readOnly = true)
public class SystemUserDAOImpl implements SystemUserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public SystemUser getSystemUserByUserName(String userName) throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(SystemUser.class,"systemUser");
        criteria.add(Restrictions.eq("userName", userName));
        criteria.add(Restrictions.eq("accountStatus", UserAccountStatus.ACTIVE));
        SystemUser systemUser=(SystemUser) criteria.uniqueResult();
        if(systemUser != null){
        	Hibernate.initialize(systemUser.getUserRoles());
        	Hibernate.initialize(systemUser.getSystemUserDetail());
        	if(!((systemUser.getHopspitalManagers()==null) || (systemUser.getHopspitalManagers().isEmpty()))){
        		for (HospitalManager hospitalManager : systemUser.getHopspitalManagers()) {
        			Hibernate.initialize(hospitalManager.getHospital());
				}        		
        	}
        }
        return systemUser;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public String updateSystemUserDetails(SystemUserDetail systemUserDetail) throws Exception {
		sessionFactory.getCurrentSession().update(systemUserDetail);
		return "SUCCESS";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public SystemUserDetail getSystemUserDetailById(String systemUserDetailId)
			throws Exception {
		// TODO Auto-generated method stub
		return (SystemUserDetail) sessionFactory.getCurrentSession().get(SystemUserDetail.class, systemUserDetailId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public SystemUser getSystemUserByUserId(String userId) throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(SystemUser.class);
		criteria.add(Restrictions.eq("userId", userId));
		return (SystemUser) criteria.uniqueResult();
	}

}
