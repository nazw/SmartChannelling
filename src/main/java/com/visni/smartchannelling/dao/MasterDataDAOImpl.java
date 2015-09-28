package com.visni.smartchannelling.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.ActiveStatus;

@Repository("masterDataDAO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@SuppressWarnings("unchecked")
public class MasterDataDAOImpl implements MasterDataDAO {
    
    private String SUCCESS = "success";
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String saveSpecialization(Specialization specialization) throws Exception {

	return (String) sessionFactory.getCurrentSession().save(specialization);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String updateSpecialization(Specialization specialization) throws Exception {

	sessionFactory.getCurrentSession().save(specialization);

	return SUCCESS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String deleteSpecialzation(Specialization specialization) throws Exception {

	sessionFactory.getCurrentSession().delete(specialization);

	return SUCCESS;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String saveHospitalRole(HospitalRole hospitalRole) throws Exception {
    	return (String) sessionFactory.getCurrentSession().save(hospitalRole);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String updateHospitalRole(HospitalRole hospitalRole) throws Exception {     
    	sessionFactory.getCurrentSession().update(hospitalRole);	
        return SUCCESS ;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String deleteHospitalRole(HospitalRole hospitalRole) throws Exception {        
    	sessionFactory.getCurrentSession().delete(hospitalRole);	
        return SUCCESS;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public HospitalRole getHospitalRoleById(String hospitalRoleId) throws Exception{
	
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalRole.class);
	criteria.add(Restrictions.eq("hospitalRoleId", hospitalRoleId));
	
	return (HospitalRole) criteria.uniqueResult();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Specialization> getAllActiveSpecializations() throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Specialization.class, "sp");
	criteria.setProjection(Projections.projectionList().add(Property.forName("sp.specializationId").as("specializationId"))
		.add(Property.forName("sp.activeStatus").as("activeStatus")).add(Property.forName("sp.name").as("name"))
		.add(Property.forName("sp.description").as("description")).add(Property.forName("sp.area").as("area"))
		.add(Property.forName("sp.versionId").as("versionId"))

	);
	criteria.add(Restrictions.eq("sp.activeStatus", ActiveStatus.ACTIVE));
	criteria.addOrder(Order.asc("specializationId"));
	List<Specialization> specializations = criteria.setResultTransformer(new AliasToBeanResultTransformer(Specialization.class)).list();
	return specializations;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<HospitalRole> getAllActiveHospitalRole() throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalRole.class, "hr");
	criteria.setProjection(Projections.projectionList().add(Property.forName("hr.hospitalRoleId").as("hospitalRoleId"))
		.add(Property.forName("hr.activeStatus").as("activeStatus")).add(Property.forName("hr.name").as("name"))
		.add(Property.forName("hr.description").as("description")).add(Property.forName("hr.versionId").as("versionId")));
	criteria.add(Restrictions.eq("hr.activeStatus", ActiveStatus.ACTIVE));
	criteria.addOrder(Order.asc("hospitalRoleId"));
	List<HospitalRole> hospitalRoles = criteria.setResultTransformer(new AliasToBeanResultTransformer(HospitalRole.class)).list();
	return hospitalRoles;
    }

 
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UserRole> getAllUserRoles() throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserRole.class, "ur");
	return criteria.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Specialization getSpecializationById(String id) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Specialization.class, "sp");
//	criteria.setProjection(Projections.projectionList()
//		.add(Property.forName("sp.specializationId").as("specializationId"))
//		.add(Property.forName("sp.name").as("name"))
//		.add(Property.forName("sp.versionId").as("versionId")));
	
	criteria.add(Restrictions.conjunction().add(Restrictions.eq("sp.specializationId", id))
		.add(Restrictions.eq("sp.activeStatus", ActiveStatus.ACTIVE)));
	
	return (Specialization) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public HospitalSpecialization getHospitalSpecializationById(String id) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalSpecialization.class, "hs");
	criteria.setProjection(Projections.projectionList().add(Property.forName("hs.hospitalSpecializationId").as("hospitalSpecializationId"))
		.add(Property.forName("hs.versionId").as("versionId")));
	criteria.add(Restrictions.conjunction().add(Restrictions.eq("hs.specializationId", id))
		.add(Restrictions.eq("hs.activeStatus", ActiveStatus.ACTIVE)));
	HospitalSpecialization hospitalSpecialization = (HospitalSpecialization) criteria.setResultTransformer(
		new AliasToBeanResultTransformer(HospitalSpecialization.class)).uniqueResult();
	return hospitalSpecialization;
    }

}
