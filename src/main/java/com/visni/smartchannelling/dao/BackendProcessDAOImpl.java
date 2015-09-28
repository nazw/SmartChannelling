package com.visni.smartchannelling.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.entity.VisitingSolts;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;

@Repository(value="backendProcessDAO")
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class BackendProcessDAOImpl implements BackendProcessDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<VisitingSolts> getAllActiveVisitingSlotsExpired()throws Exception {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(VisitingSolts.class);
		criteria.add(
				Restrictions.conjunction()
					.add(Restrictions.lt("endTime", Calendar.getInstance().getTime()))
					.add(Restrictions.lt("activeStatus", ActiveStatus.ACTIVE))
		);
		return criteria.list();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public String updateAllExpiredVisitingSlots(List<VisitingSolts> visitingSlots) throws Exception {
		for (VisitingSolts visitingSolt : visitingSlots) {
			sessionFactory.getCurrentSession().update(visitingSolt);
		}
		return ApplicationConstants.SUCCESS;
	}

	@Override
	public List<Visiting> getVisitngsToBeCreatedForAnotherThreeMonths()throws Exception {
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -3);
		Date date=calendar.getTime();
		System.out.println("BEFORE THREE MONTHS :" + date);
		
		String queryString="SELECT visiting_id as visiting_id"
						 + " FROM visiting"
						 + " WHERE start_date IN (SELECT Max(start_date)"
						 + " FROM visiting"
						 + " WHERE visiting_status='ACTIVE'"
						 + " GROUP BY avg_time_per_appointment, created_user, no_of_patients, hospital_doctor_id, day_of_week)";
		
		SQLQuery sqlQuery=sessionFactory.getCurrentSession().createSQLQuery(queryString);
		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> result=sqlQuery.list();
		List<String> results=new ArrayList<String>();
		for (Map map : result) {
			results.add((String) map.get("visiting_id"));
		}
		
		
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Visiting.class);
		criteria.add(
				Restrictions.conjunction()
					.add(Restrictions.in("visitingId", results))
					.add(Restrictions.le("startDate", date))
		);
		
		return criteria.list();
	}

}
