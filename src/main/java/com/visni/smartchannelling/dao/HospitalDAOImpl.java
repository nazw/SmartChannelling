/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.util.PriorityStatus;

/**
 * @author visni
 */
@Repository("HospitalDAO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HospitalDAOImpl implements HospitalDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Hospital getHospitalById(String hospitalId) throws Exception{
	
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Hospital.class);
	
	criteria.add(Restrictions.eq("hospitalId", hospitalId));
	
	return (Hospital) criteria.uniqueResult();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Hospital getHospitalInfoById(String hospitalId) throws Exception {

	String getHospitalByIdQuery = " SELECT hsp.hospital_id,hsp.hospital_name" + " FROM hospital hsp" + " WHERE hsp.hospital_id='" + hospitalId+ "'";

	Query query = sessionFactory.getCurrentSession().createSQLQuery(getHospitalByIdQuery);

	Map mapOfGetHospitalById = (Map) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();

	Hospital hospital = new Hospital();

	hospital.setHospitalId((String) mapOfGetHospitalById.get("hospital_id"));
	hospital.setHospitalName((String) mapOfGetHospitalById.get("hospital_name"));

	return hospital;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SystemUserDetail saveHospital(Hospital hospital) throws Exception {
	sessionFactory.getCurrentSession().save(hospital);
	for (HospitalManager hospitalManager : hospital.getHopspitalManagers()) {

	    return hospitalManager.getSystemUser().getSystemUserDetail();

	}
	return null;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateHospital(Hospital hospital) throws Exception {
	
	sessionFactory.getCurrentSession().saveOrUpdate(hospital);
	
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHospital(Hospital hospital) throws Exception{
	
	sessionFactory.getCurrentSession().delete(hospital);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public EmailAddress getEmailAddressbyId(String id) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmailAddress.class, "en");
	criteria.setProjection(Projections.projectionList().add(Property.forName("en.emailAddressId").as("emailAddressId"))
		.add(Property.forName("en.emailAddressValue").as("emailAddressValue")).add(Property.forName("en.versionId").as("versionId")));
	criteria.add(Restrictions.conjunction().add(Restrictions.eq("en.emailAddressId", id)));
	EmailAddress emailAddress = (EmailAddress) criteria.setResultTransformer(new AliasToBeanResultTransformer(EmailAddress.class)).uniqueResult();
	return emailAddress;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ContactNumber getContactNumberbyId(String id) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ContactNumber.class, "cn");
	criteria.setProjection(Projections.projectionList().add(Property.forName("cn.contactNumberId").as("contactNumberId"))
		.add(Property.forName("cn.contactNumberValue").as("contactNumberValue")).add(Property.forName("cn.versionId").as("versionId")));
	criteria.add(Restrictions.conjunction().add(Restrictions.eq("cn.contactNumberId", id)));
	ContactNumber contactNumber = (ContactNumber) criteria.setResultTransformer(new AliasToBeanResultTransformer(ContactNumber.class))
		.uniqueResult();
	return contactNumber;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Map> getAllActiveHospitalsForUser(String currentUserId, boolean isSuperAdmin) throws Exception {

	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Hospital.class);
	criteria.createAlias("address", "address");
	criteria.createAlias("emailAddresses", "emailAddresses");
	criteria.createAlias("hopspitalManagers", "hopspitalManagers");
	criteria.createAlias("hopspitalManagers.systemUser", "systemUser");

	criteria.setProjection(Projections.projectionList().add(Property.forName("hospitalId").as("hospitalId"))
		.add(Property.forName("hospitalName").as("hospitalName")).add(Property.forName("registrationNumber").as("registrationNumber"))
		.add(Property.forName("address.addressValue").as("addressValue"))
		.add(Property.forName("emailAddresses.emailAddressValue").as("emailAddressValue"))
		.add(Property.forName("accountStatus").as("accountStatus"))
	);

	if (!(isSuperAdmin)) {		
	    criteria.add(Restrictions.conjunction()
	    		.add(Restrictions.eq("systemUser.userId", currentUserId))
	    		.add(Restrictions.eq("emailAddresses.emailPriorityStatus", PriorityStatus.PRIMARY))
	   );
	}else{
		criteria.add(Restrictions.conjunction()	    		
	    		.add(Restrictions.eq("emailAddresses.emailPriorityStatus", PriorityStatus.PRIMARY))
	   );
	}

	// String sqlQuery =
	// "SELECT hs.hospital_id,hs.account_status, hs.hospital_name,hs.registration_number, "
	// + "add.address_value,em.email_address_value "
	// + " FROM hospital hs INNER JOIN address add "
	// + " ON hs.address_id=add.address_id "
	// + " INNER JOIN email_address em ON em.hospital_id=hs.hospital_id ";
	//
	// Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
	// List<Map> hospitalList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

	return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }
    
        @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<EmailAddress> getEmailAddressesByHospitalId(String hospitalId) throws Exception{
	
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmailAddress.class,"email");
	criteria.createAlias("email.hospital", "hospital");        
	criteria.add(Restrictions.eq("hospital.hospitalId", hospitalId));
	
	return criteria.list();
    }
     
                @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<ContactNumber> getContactNumberByHospitalId(String hospitalId) throws Exception{
	
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ContactNumber.class,"con");
	criteria.createAlias("con.hospital", "hospital");        
	criteria.add(Restrictions.eq("hospital.hospitalId", hospitalId));
	
	return criteria.list();
    }
                
                                @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<HospitalSpecialization> getHospitalSpecializationByHospitalId(String hospitalId) throws Exception{
	
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalSpecialization.class,"hs");
	criteria.createAlias("hs.hospital", "hospital");        
	criteria.add(Restrictions.eq("hospital.hospitalId", hospitalId));
	
	return criteria.list();
    }
     
                                    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHospitalSpecializationByHospitalId(String hospitalId) throws Exception{	
	        
       String sqlQueryStr = "DELETE from hospital_specialization WHERE hospital_id= '" + hospitalId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate();
    }
}
