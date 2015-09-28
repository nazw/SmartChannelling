package com.visni.smartchannelling.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sun.security.krb5.internal.crypto.Crc32CksumType;

import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.util.AccountStatus;

@Repository("hospitalManagerDAO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HospitalManagerDAOImpl implements HospitalManagerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String updateHospitalManagerDetails(HospitalManager hospitalManager,
			String updatedUserId) throws Exception {
		// get the Hospital Manager from the db
		
		// get the contact details & email adddress from front end
		// update the manager & save it
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Hospital> getAllAvailableHospitalsByStatus(AccountStatus accountStatus) throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Hospital.class);
		if(!(accountStatus==null)){
			criteria.add(Restrictions.eq("accountStatus", accountStatus));
		}		
		return criteria.list();
	}

}
