/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.Address;
import com.visni.smartchannelling.entity.Appointment;
import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.entity.VisitingSolts;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.BookingStatus;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
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

@Repository("VisitingSoltsDAO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VisitingSoltsDAOImpl implements VisitingSoltsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public VisitingSolts getVisitingSoltsById(String visitingSlotsId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VisitingSolts.class);
        criteria.add(Restrictions.eq("visitingSlotsId", visitingSlotsId));
        return (VisitingSolts) criteria.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String activateVisitingSlot(String visitingSlotsId) throws Exception {

        VisitingSolts visitingSolts = (VisitingSolts) sessionFactory.getCurrentSession().get(VisitingSolts.class, visitingSlotsId);
        visitingSolts.setBookingStatus(BookingStatus.VACANT);
        System.out.println("sssssss" + visitingSolts.getBookingStatus());
        sessionFactory.getCurrentSession().saveOrUpdate(visitingSolts);

        return ApplicationConstants.SUCCESS;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String cancelVisitingSlot(String visitingSlotsId) throws Exception {

        VisitingSolts visitingSolts = (VisitingSolts) sessionFactory.getCurrentSession().get(VisitingSolts.class, visitingSlotsId);
        visitingSolts.setBookingStatus(BookingStatus.CANCELLED);
        System.out.println("hhhhh" + visitingSolts.getBookingStatus());
        sessionFactory.getCurrentSession().saveOrUpdate(visitingSolts);

        return ApplicationConstants.SUCCESS;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Map> getPatientByVisitingSlotId(String visitingSlotsId) throws Exception {

		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Appointment.class, "app");
		criteria.createAlias("app.visitingSolt", "visitingSolt");
                criteria.createAlias("app.patient", "systemUser");
		criteria.createAlias("systemUser.systemUserDetail", "systemUserDetail");
           
		criteria.setProjection(
				Projections.projectionList()					
					.add(Property.forName("systemUserDetail.systemUserDetailId").as("systemUserDetailId"))					
					.add(Property.forName("systemUserDetail.title").as("title"))
					.add(Property.forName("systemUserDetail.firstName").as("firstName"))			
					.add(Property.forName("systemUserDetail.lastName").as("lastName"))	
                                        
		);
                      	criteria.add(Restrictions.conjunction()
                            .add(Restrictions.eq("activeStatus", ActiveStatus.ACTIVE))
                            .add(Restrictions.eq("bookingStatus", BookingStatus.BOOKED))
                            .add(Restrictions.eq("visitingSolt.visitingSlotsId", visitingSlotsId))
					
		);
             	
		return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }
    
                    @Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map> findTimeSlotsForDoctor(String firstName,String lastName,String specializationId,Date startDate,String hospitalId)throws Exception {
               
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(VisitingSolts.class, "vs");
		criteria.createAlias("vs.visiting", "visiting");    
                criteria.createAlias("visiting.hospitalDoctor", "hospitalDoctor"); 
                criteria.createAlias("hospitalDoctor.doctor", "doctor");
                criteria.createAlias("doctor.systemUserDetail", "systemUserDetail");
                criteria.createAlias("doctor.doctorSpecializations", "doctorSpecializations");
                criteria.createAlias("doctorSpecializations.specialization", "specialization");
                criteria.createAlias("hospitalDoctor.hospital", "hospital");
		criteria.setProjection(
                Projections.projectionList()					
                        .add(Property.forName("visitingSlotsId").as("visitingSlotsId"))					
                        .add(Property.forName("slotNumber").as("slotNumber"))
                        .add(Property.forName("bookingStatus").as("bookingStatus"))
                        .add(Property.forName("startTime").as("startTime"))
                        .add(Property.forName("endTime").as("endTime"))  
                        .add(Property.forName("doctor.doctorId").as("doctorId")) 
					
		);
                criteria.add(Restrictions.conjunction()
                        .add(Restrictions.eq("hospital.hospitalId", hospitalId))
                        .add(Restrictions.eq("systemUserDetail.firstName", firstName))
                         .add(Restrictions.eq("systemUserDetail.lastName", lastName))
                        .add(Restrictions.eq("specialization.specializationId", specializationId))
                        .add(Restrictions.eq("visiting.startDate", startDate))
					
		);

                criteria.addOrder(Order.asc("visitingSlotsId"));
		return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
                    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public SystemUserDetail getSystemUserDetailByVisitingSlotId(String visitingSlotsId) throws Exception {

      
                Criteria systemUserCriteria = sessionFactory.getCurrentSession().createCriteria(SystemUserDetail.class, "systemUserDetail");  
                systemUserCriteria.createAlias("systemUserDetail.systemUser", "systemUser");
                systemUserCriteria.createAlias("systemUser.patientsList", "appointment");
                systemUserCriteria.createAlias("appointment.visitingSolt", "visitingSolt");
                
//               
                    systemUserCriteria.add(Restrictions.conjunction()
                            .add(Restrictions.eq("appointment.activeStatus", ActiveStatus.ACTIVE))
                            .add(Restrictions.eq("appointment.bookingStatus", BookingStatus.BOOKED))
                            .add(Restrictions.eq("visitingSolt.visitingSlotsId", visitingSlotsId)));
            
                SystemUserDetail systemUserDetail = (SystemUserDetail) systemUserCriteria.list().get(0);    
                if(systemUserDetail.getEmailAddresses()!=null){
                    Hibernate.initialize(EmailAddress.class);
                }
                if(systemUserDetail.getSystemUser().getAddress()!=null){
                    Hibernate.initialize(Address.class);
                }
                if(systemUserDetail.getContactNumbers()!=null){
                    Hibernate.initialize(ContactNumber.class);
                }
                
                 
		return systemUserDetail;
    }
}
