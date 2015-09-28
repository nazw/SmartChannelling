/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.Doctor;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.AccountStatus;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.UserRoleType;

/**
 * @author visni
 */
@Repository("DoctorDAO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DoctorDAOImpl implements DoctorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String saveDoctor(Doctor doctor) throws Exception {

	// save the Doctor
	sessionFactory.getCurrentSession().save(doctor);
	return "success";
    }

    /*
     * (non-Javadoc)
     * 
     * @author Nadeeshani
     * 
     * @see com.visni.smartchannelling.dao.DoctorDAO#getAllDoctorsForHospital(java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Map<String, Object>> getAllDoctorsForHospital(String hospitalId) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalDoctor.class);
	criteria.createAlias("doctor", "doctor");
	criteria.createAlias("doctor.systemUserDetail", "systemUserDetail");
	criteria.createAlias("hospital", "hospital");
	criteria.setProjection(Projections.projectionList().add(Property.forName("hospitalDoctorId").as("hospitalDoctorId"))
		.add(Property.forName("doctor.doctorId").as("doctorId"))
		.add(Property.forName("doctor.docRegistrationNumber").as("docRegistrationNumber"))
		.add(Property.forName("systemUserDetail.title").as("title")).add(Property.forName("systemUserDetail.firstName").as("firstName"))
		.add(Property.forName("systemUserDetail.middleName").as("middleName"))
		.add(Property.forName("systemUserDetail.lastName").as("lastName"))
		.add(Property.forName("systemUserDetail.profileImage").as("profileImage")));
	criteria.add(Restrictions.conjunction().add(Restrictions.eq("hospital.hospitalId", hospitalId))
		.add(Restrictions.eq("activeStatus", ActiveStatus.ACTIVE)));
	return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Specialization getSpecializationById(String specializationId) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Specialization.class);
	criteria.add(Restrictions.eq("specializationId", specializationId));
	return (Specialization) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public HospitalRole getHospitalRoleById(String hospitalRoleId) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalRole.class);
	criteria.add(Restrictions.eq("hospitalRoleId", hospitalRoleId));
	return (HospitalRole) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Hospital getHospitalById(String hospitalId) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Hospital.class);
	criteria.add(Restrictions.eq("hospitalId", hospitalId));
	return (Hospital) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserRole getUserRoleByUserRoleType(UserRoleType role) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserRole.class);
	criteria.add(Restrictions.eq("userRoleType", role));
	return (UserRole) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ContactNumber getContactNumberByContactNumberValue(String contactNumberValue) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ContactNumber.class);
	criteria.add(Restrictions.eq("contactNumberValue", contactNumberValue));
	return (ContactNumber) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public EmailAddress getEmailAddressByEmailAddressValue(String emailAddressValue) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmailAddress.class);
	criteria.add(Restrictions.eq("emailAddressValue", emailAddressValue));
	return (EmailAddress) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Map> getAllActiveDoctors() throws Exception {

	String sqlQuery = "SELECT doc.doctor_id,doc.doc_registration_number, doc.account_status,sud.first_name, " + "sud.title,sud.last_name"
		+ " FROM doctor doc INNER JOIN system_user_detail sud " + " ON doc.system_user_detail_id=sud.system_user_detail_id ";

	Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
	List<Map> doctorsList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

	return doctorsList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Map> getAllDoctorsForAppointment(String hospitalId, String specializationId) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalDoctor.class, "hdo");
	criteria.createAlias("hdo.doctor", "doctor");
	criteria.createAlias("doctor.systemUserDetail", "systemUserDetail");
	criteria.createAlias("hdo.hospital", "hospital");
	criteria.createAlias("doctor.doctorSpecializations", "doctorSpecialization");
	criteria.createAlias("doctorSpecialization.specialization", "specialization");
	criteria.setProjection(Projections.projectionList().add(Property.forName("doctor.doctorId").as("doctorId"))
		.add(Property.forName("systemUserDetail.title").as("title")).add(Property.forName("systemUserDetail.firstName").as("firstName"))
		.add(Property.forName("systemUserDetail.lastName").as("lastName"))

	);
	criteria.add(Restrictions.conjunction().add(Restrictions.eq("hospital.hospitalId", hospitalId))
		.add(Restrictions.eq("specialization.specializationId", specializationId)));
	return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Doctor getDoctorById(String doctorId) throws Exception {
	// TODO Auto-generated method stub
	return (Doctor) sessionFactory.getCurrentSession().get(Doctor.class, doctorId);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Map> getAllActiveDoctorsForHospitalWithVistingData(String hospitalId) throws Exception{
        
    Calendar calWeekEnd=Calendar.getInstance();
    calWeekEnd.set(Calendar.DAY_OF_WEEK, calWeekEnd.getActualMaximum(Calendar.DAY_OF_WEEK));
    Date weekEnd=calWeekEnd.getTime();
    System.out.println("weekEnd :" + weekEnd);
    
    Calendar calWeekStart=Calendar.getInstance();
    calWeekStart.set(Calendar.DAY_OF_WEEK, calWeekStart.getActualMinimum(Calendar.DAY_OF_WEEK));
    Date weekStart=calWeekStart.getTime();
    System.out.println("weekStart :" + weekStart);
    
    
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HospitalDoctor.class);
	criteria.createAlias("hospital", "hospital");
	criteria.createAlias("doctor", "doctor");
	criteria.createAlias("doctor.doctorSpecializations", "doctorSpecializations");
	criteria.createAlias("doctorSpecializations.specialization", "specialization");
	criteria.createAlias("doctor.systemUserDetail", "systemUserDetail");
	criteria.createAlias("visitingList", "visitingList");
	
	criteria.setProjection(
			Projections.projectionList()
				.add(Property.forName("doctor.doctorId").as("doctorId"))
				.add(Property.forName("doctorSpecializations.doctorSpecializationId").as("doctorSpecializationId"))
				.add(Property.forName("specialization.specializationId").as("specializationId"))
				.add(Property.forName("specialization.name").as("name"))
				.add(Property.forName("systemUserDetail.title").as("title"))
				.add(Property.forName("systemUserDetail.firstName").as("firstName"))
				.add(Property.forName("systemUserDetail.middleName").as("middleName"))
				.add(Property.forName("systemUserDetail.lastName").as("lastName"))
				.add(Property.forName("visitingList.visitingId").as("visitingId"))
				.add(Property.forName("visitingList.dayOfWeek").as("dayOfWeek"))
				.add(Property.forName("visitingList.startTime").as("startTime"))
				.add(Property.forName("visitingList.endTime").as("endTime"))
				.add(Property.forName("visitingList.avgTimePerAppointment").as("avgTimePerAppointment"))
	);
		
	criteria.add(Restrictions.conjunction()
		.add(Restrictions.eq("hospital.hospitalId", hospitalId))
		.add(Restrictions.eq("doctor.accountStatus", AccountStatus.ACTIVE))
		.add(Restrictions.between("visitingList.startDate", weekStart, weekEnd))
//		.add(Restrictions.eq("visitingList.visitingStatus", ActiveStatus.ACTIVE))
	);
	List<Map> list=criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	System.out.println("list :" + list);
        return list;
    }
    
}
