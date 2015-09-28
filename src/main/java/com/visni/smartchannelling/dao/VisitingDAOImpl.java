package com.visni.smartchannelling.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.entity.VisitingSolts;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.BookingStatus;

import org.hibernate.criterion.Order;

@Repository(value="visitingDAO")
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class VisitingDAOImpl implements VisitingDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=false)
	public String createVisiting(List<Visiting> visitings) throws Exception {
		for (Visiting visiting : visitings) {
			sessionFactory.getCurrentSession().save(visiting);
		}		
		return ApplicationConstants.SUCCESS;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Map> listAllVisitingsForHospitalByStatus(String hospitalId,ActiveStatus activeStatus) throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Visiting.class);
		criteria.createAlias("hospitalDoctor", "hospitalDoctor");
		criteria.createAlias("hospitalDoctor.hospital", "hospital");
		criteria.createAlias("hospitalDoctor.doctor", "doctor");
		criteria.createAlias("doctor.systemUserDetail", "systemUserDetail");
		criteria.setProjection(
				Projections.projectionList()
					.add(Property.forName("visitingId").as("visitingId"))
					.add(Property.forName("startDate").as("startDate"))
					.add(Property.forName("startTime").as("startTime"))
					.add(Property.forName("endDate").as("endDate"))
					.add(Property.forName("endTime").as("endTime"))
					.add(Property.forName("avgTimePerAppointment").as("avgTimePerAppointment"))
					.add(Property.forName("noOfPatients").as("noOfPatients"))
					.add(Property.forName("charges").as("charges"))
					.add(Property.forName("hospitalDoctor.hospitalDoctorId").as("hospitalDoctorId"))
					.add(Property.forName("doctor.doctorId").as("doctorId"))
					.add(Property.forName("doctor.docRegistrationNumber").as("docRegistrationNumber"))
					.add(Property.forName("systemUserDetail.title").as("title"))
					.add(Property.forName("systemUserDetail.firstName").as("firstName"))
					.add(Property.forName("systemUserDetail.middleName").as("middleName"))
					.add(Property.forName("systemUserDetail.lastName").as("lastName"))
					.add(Property.forName("systemUserDetail.profileImage").as("profileImage"))
		);
		criteria.add(
				Restrictions.conjunction()
					.add(Restrictions.eq("hospital.hospitalId", hospitalId))
					.add(Restrictions.eq("visitingStatus", activeStatus))
		);
		criteria.addOrder(Order.asc("startDate"));
		return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public HospitalDoctor getHospitalDoctorById(String hospitalDoctorId)throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(HospitalDoctor.class);
		criteria.add(Restrictions.eq("hospitalDoctorId", hospitalDoctorId));
		return (HospitalDoctor) criteria.uniqueResult();
	}

        @Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Map> listAllVisitingsForDoctor(String hospitalId,String doctorId) throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Visiting.class);
		criteria.createAlias("hospitalDoctor", "hospitalDoctor");
		criteria.createAlias("hospitalDoctor.hospital", "hospital");
		criteria.createAlias("hospitalDoctor.doctor", "doctor");	
		criteria.setProjection(
				Projections.projectionList()
					.add(Property.forName("visitingId").as("visitingId"))
					.add(Property.forName("startDate").as("startDate"))
					.add(Property.forName("startTime").as("startTime"))
					.add(Property.forName("endDate").as("endDate"))
					.add(Property.forName("endTime").as("endTime"))
					.add(Property.forName("visitingStatus").as("visitingStatus"))
					.add(Property.forName("noOfPatients").as("noOfPatients"))
					.add(Property.forName("charges").as("charges"))

		);
		criteria.add(
				Restrictions.conjunction()
					.add(Restrictions.eq("hospital.hospitalId", hospitalId))
					.add(Restrictions.eq("doctor.doctorId", doctorId))
                                        .add(Restrictions.eq("visitingStatus", ActiveStatus.ACTIVE))
		);
		return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
        
                @Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map> getAllVisitingSlotsForVisiting(String visitingId)throws Exception {
                    System.out.println("visitingIdaa"+visitingId);
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(VisitingSolts.class, "vs");
		criteria.createAlias("vs.visiting", "visiting");                  
		criteria.setProjection(
				Projections.projectionList()					
					.add(Property.forName("visitingSlotsId").as("visitingSlotsId"))					
					.add(Property.forName("slotNumber").as("slotNumber"))
					.add(Property.forName("bookingStatus").as("bookingStatus"))
                                        .add(Property.forName("startTime").as("startTime"))
                                        .add(Property.forName("endTime").as("endTime"))                                 				
					
		);
                criteria.add(Restrictions.eq("visiting.visitingId", visitingId));
                criteria.addOrder(Order.asc("visitingSlotsId"));

		return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
                
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String cancelVisiting(String visitingId) throws Exception {

           Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Visiting.class);
           //criteria.createAlias("visitingSolt", "visitingSolt");   
        criteria.add(Restrictions.eq("visitingId", visitingId));
       Visiting visiting = (Visiting)criteria.uniqueResult();
        visiting.setVisitingStatus(ActiveStatus.CANCELLED);
        
      sessionFactory.getCurrentSession().saveOrUpdate(visiting);
      return ApplicationConstants.SUCCESS;
    
    }
    
        @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String activateVisiting(String visitingId) throws Exception {

           Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Visiting.class);
           //criteria.createAlias("visitingSolt", "visitingSolt");   
        criteria.add(Restrictions.eq("visitingId", visitingId));
       Visiting visiting = (Visiting)criteria.uniqueResult();
        visiting.setVisitingStatus(ActiveStatus.ACTIVE);
        
      sessionFactory.getCurrentSession().saveOrUpdate(visiting);
      return ApplicationConstants.SUCCESS;
    
    }

	@Override
	public List<Map> lisAllFilteredVisitingsForHospitalByStatus(
			String hospitalId, String doctorId, Date date,
			ActiveStatus activeStatus) throws Exception {
		
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Visiting.class);
		criteria.createAlias("hospitalDoctor", "hospitalDoctor");
		criteria.createAlias("hospitalDoctor.hospital", "hospital");
		criteria.createAlias("hospitalDoctor.doctor", "doctor");
		criteria.createAlias("doctor.systemUserDetail", "systemUserDetail");
		criteria.setProjection(
				Projections.projectionList()
					.add(Property.forName("visitingId").as("visitingId"))
					.add(Property.forName("startDate").as("startDate"))
					.add(Property.forName("startTime").as("startTime"))
					.add(Property.forName("endDate").as("endDate"))
					.add(Property.forName("endTime").as("endTime"))
					.add(Property.forName("avgTimePerAppointment").as("avgTimePerAppointment"))
					.add(Property.forName("noOfPatients").as("noOfPatients"))
					.add(Property.forName("charges").as("charges"))
					.add(Property.forName("hospitalDoctor.hospitalDoctorId").as("hospitalDoctorId"))
					.add(Property.forName("doctor.doctorId").as("doctorId"))
					.add(Property.forName("doctor.docRegistrationNumber").as("docRegistrationNumber"))
					.add(Property.forName("systemUserDetail.title").as("title"))
					.add(Property.forName("systemUserDetail.firstName").as("firstName"))
					.add(Property.forName("systemUserDetail.middleName").as("middleName"))
					.add(Property.forName("systemUserDetail.lastName").as("lastName"))
					.add(Property.forName("systemUserDetail.profileImage").as("profileImage"))
		);
		if(!(date==null)){
			Calendar calDate=Calendar.getInstance();
			calDate.setTime(date);
			
			Calendar firstCal=Calendar.getInstance();
			firstCal.setTime(date);
			firstCal.set(Calendar.DAY_OF_MONTH, calDate.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date firstDate=firstCal.getTime();
			System.out.println("FIRST DATE :" + firstDate);
			
			Calendar lastCal=Calendar.getInstance();
			lastCal.setTime(date);
			lastCal.set(Calendar.DAY_OF_MONTH, calDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date lastDate=lastCal.getTime();
			System.out.println("LAST DATE :" + lastDate);
			
			if(StringUtils.isNotBlank(doctorId)){
				criteria.add(
						Restrictions.conjunction()
							.add(Restrictions.eq("hospital.hospitalId", hospitalId))
							.add(Restrictions.eq("visitingStatus", activeStatus))
							.add(Restrictions.eq("doctor.doctorId", doctorId))
							.add(Restrictions.between("startDate", firstDate, lastDate))
				);
			}else{
				criteria.add(
						Restrictions.conjunction()
							.add(Restrictions.eq("hospital.hospitalId", hospitalId))
							.add(Restrictions.eq("visitingStatus", activeStatus))
							.add(Restrictions.between("startDate", firstDate, lastDate))
				);
			}
		}else{
			if(StringUtils.isNotBlank(doctorId)){
				criteria.add(
						Restrictions.conjunction()
							.add(Restrictions.eq("hospital.hospitalId", hospitalId))
							.add(Restrictions.eq("visitingStatus", activeStatus))
							.add(Restrictions.eq("doctor.doctorId", doctorId))
				);
			}else{
				criteria.add(
						Restrictions.conjunction()
							.add(Restrictions.eq("hospital.hospitalId", hospitalId))
							.add(Restrictions.eq("visitingStatus", activeStatus))
				);
			}
		}	
		criteria.addOrder(Order.asc("startDate"));
		return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
        
         @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Visiting getVisitingByVisitingSlotId(String visitingSlotsId) throws Exception {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Visiting.class,"vis");
        criteria.createAlias("vis.visitingSolts", "visitingSolts");  
        
	criteria.add(Restrictions.conjunction().add(Restrictions.eq("visitingSolts.visitingSlotsId", visitingSlotsId)));
	Visiting visiting = (Visiting) criteria.uniqueResult();
	return visiting;
    } 
         
         
                 @Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Map> getFilteredDoctorAppointment(String hospitalId,String doctorId,Date date) throws Exception {
                     
                     
                     Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Visiting.class);
		criteria.createAlias("hospitalDoctor", "hospitalDoctor");
		criteria.createAlias("hospitalDoctor.hospital", "hospital");
		criteria.createAlias("hospitalDoctor.doctor", "doctor");	
		criteria.setProjection(
				Projections.projectionList()
					.add(Property.forName("visitingId").as("visitingId"))
					.add(Property.forName("startDate").as("startDate"))
					.add(Property.forName("startTime").as("startTime"))
					.add(Property.forName("endDate").as("endDate"))
					.add(Property.forName("endTime").as("endTime"))
					.add(Property.forName("visitingStatus").as("visitingStatus"))
					.add(Property.forName("noOfPatients").as("noOfPatients"))
					.add(Property.forName("charges").as("charges"))

		);
                
              
			Calendar calDate=Calendar.getInstance();
			calDate.setTime(date);
			
			Calendar firstCal=Calendar.getInstance();
			firstCal.setTime(date);
			firstCal.set(Calendar.DAY_OF_MONTH, calDate.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date firstDate=firstCal.getTime();
			System.out.println("FIRST DATEv :" + firstDate);
			
			Calendar lastCal=Calendar.getInstance();
			lastCal.setTime(date);
			lastCal.set(Calendar.DAY_OF_MONTH, calDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date lastDate=lastCal.getTime();
			System.out.println("LAST DATEv :" + lastDate);                        
                     
		
		criteria.add(
				Restrictions.conjunction()
					.add(Restrictions.eq("hospital.hospitalId", hospitalId))
					.add(Restrictions.eq("doctor.doctorId", doctorId))
                                        .add(Restrictions.eq("visitingStatus", ActiveStatus.ACTIVE))
                                        .add(Restrictions.between("startDate", firstDate, lastDate))
                        
		);
                criteria.addOrder(Order.asc("startDate"));
                          
		return criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

                 
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public long getActiveVisitingSlotCountByActiveAndbookingStatus(String visitingId, ActiveStatus activeStatus,BookingStatus bookingStatus) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(VisitingSolts.class);
		criteria.createAlias("visiting", "visiting");
		criteria.setProjection(Projections.countDistinct("visitingSlotsId"));
		if(!(activeStatus==null)){
			if(!(bookingStatus==null)){
				criteria.add(
						Restrictions.conjunction()
							.add(Restrictions.eq("activeStatus", activeStatus))
							.add(Restrictions.eq("bookingStatus", bookingStatus))
						    .add(Restrictions.eq("visiting.visitingId", visitingId))
				);
			}else{
				criteria.add(
						Restrictions.conjunction()
							.add(Restrictions.eq("activeStatus", activeStatus))							
						    .add(Restrictions.eq("visiting.visitingId", visitingId))
				);
			}			
		}else{
			if(!(bookingStatus==null)){
				criteria.add(
						Restrictions.conjunction()							
							.add(Restrictions.eq("bookingStatus", bookingStatus))
						    .add(Restrictions.eq("visiting.visitingId", visitingId))
				);
			}else{
				criteria.add(
						Restrictions.conjunction()										
						    .add(Restrictions.eq("visiting.visitingId", visitingId))
				);
			}
		}	
		return (Long) criteria.uniqueResult();
	}
     
                 
}
