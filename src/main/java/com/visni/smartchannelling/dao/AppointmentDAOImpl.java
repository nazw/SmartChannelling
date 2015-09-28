/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.Appointment;
import com.visni.smartchannelling.entity.SystemUserDetail;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.BookingStatus;
import com.visni.smartchannelling.util.UserRoleType;

@Repository("AppointmentDAO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AppointmentDAOImpl implements AppointmentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserRole getUserRoleByUserRoleType(UserRoleType role) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserRole.class);
        criteria.add(Restrictions.eq("userRoleType", role));
        return (UserRole) criteria.uniqueResult();
    }
    
        	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public EmailAddress getEmailAddressById(String emailAddressId) throws Exception {
		return (EmailAddress)sessionFactory.getCurrentSession().get(EmailAddress.class, emailAddressId);
	
	}
                
            	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ContactNumber getContactNumberById(String contactNumberId) throws Exception {
		return (ContactNumber)sessionFactory.getCurrentSession().get(ContactNumber.class, contactNumberId);
	
	}
                
    	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public EmailAddress getEmailAddressByEmailAddressValue(String emailAddressValue) throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(EmailAddress.class);
		criteria.add(Restrictions.eq("emailAddressValue", emailAddressValue));
		return (EmailAddress) criteria.uniqueResult();
	}
        
        	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ContactNumber getContactNumberByContactNumberValue(String contactNumberValue) throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(ContactNumber.class);
		criteria.add(Restrictions.eq("contactNumberValue", contactNumberValue));
		return (ContactNumber) criteria.uniqueResult();
	}
                
                    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String saveAppointment(Appointment appointment) throws Exception {

         return (String)sessionFactory.getCurrentSession().save(appointment);
    
    }
                    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String cancelAppointment(String visitingSlotsId) throws Exception {

        
               String sqlQuery = "UPDATE appointment SET booking_status = '" + BookingStatus.CANCELLED + "',active_status='"+ActiveStatus.CANCELLED+"' WHERE visiting_id= '" + visitingSlotsId + "'AND booking_status = '" + BookingStatus.BOOKED + "'AND active_status='"+ActiveStatus.ACTIVE+"'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
        query.executeUpdate();
        
                       String sqlQueryStr = "UPDATE visiting_solts SET booking_status = '" + BookingStatus.VACANT + "'WHERE visiting_slots_id= '" + visitingSlotsId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate();
        
           
      return ApplicationConstants.SUCCESS;
    
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String updateAppointment(String newTimeSlotId, String currentvVsitingSlotsId) throws Exception {  

        
                       String sqlQueryStr = "UPDATE visiting_solts SET booking_status = '" + BookingStatus.VACANT + "'WHERE visiting_slots_id= '" + currentvVsitingSlotsId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate();
        
                              String sqlQueryStrNew = "UPDATE visiting_solts SET booking_status = '" + BookingStatus.BOOKED + "'WHERE visiting_slots_id= '" + newTimeSlotId + "'";
        Query querystrnew = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStrNew);
        querystrnew.executeUpdate();
        
                               String sqlQuery = "UPDATE appointment SET visiting_id='"+newTimeSlotId+"' WHERE visiting_id= '" + currentvVsitingSlotsId + "'AND booking_status = '" + BookingStatus.BOOKED + "'AND active_status='"+ActiveStatus.ACTIVE+"'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
        query.executeUpdate();
        
      return ApplicationConstants.SUCCESS;
    }

    @Override
        @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String updatePatient(SystemUserDetail systemUserDetail) throws Exception {
         sessionFactory.getCurrentSession().saveOrUpdate(systemUserDetail);
        return ApplicationConstants.SUCCESS;
    }
                                        
         	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void deleteEmailAddressByHospitalId(String hospitalId) throws Exception {
		 String sqlQueryStr = "DELETE from email_address WHERE hospital_id= '" + hospitalId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate();
	}

    @Override
    public void deleteContactNumberByHospitalId(String hospitalId) throws Exception {
        		 String sqlQueryStr = "DELETE from contact_number WHERE hospital_id= '" + hospitalId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate();
    }

    @Override
    public void deleteEmailAddressBySystemUserDetaild(String systemUserDetailId,String emailAddressId) throws Exception {
        
       		 String sqlQueryStr = "DELETE from email_address WHERE system_user_detail_id= '" + systemUserDetailId + "' AND email_address_id= '" + emailAddressId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate(); 
    }

    @Override
    public void deleteContactNumberBySystemUserDetailId(String systemUserDetailId,String contactNumberId) throws Exception {
        		 String sqlQueryStr = "DELETE from contact_number WHERE system_user_detail_id= '" + systemUserDetailId + "' AND contact_number_id= '" + contactNumberId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate();
    }
     @Override
    public void deleteEmailAddressById(String emailAddressId) throws Exception {
        
       		 String sqlQueryStr = "DELETE from email_address WHERE email_address_id= '" + emailAddressId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate(); 
    }

    @Override
    public void deleteContactNumberById(String contactNumberId) throws Exception {
        		 String sqlQueryStr = "DELETE from contact_number WHERE contact_number_id= '" + contactNumberId + "'";
        Query querystr = sessionFactory.getCurrentSession().createSQLQuery(sqlQueryStr);
        querystr.executeUpdate();
    }
}
