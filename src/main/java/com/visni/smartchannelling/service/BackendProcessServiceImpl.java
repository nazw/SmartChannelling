package com.visni.smartchannelling.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.BackendProcessDAO;
import com.visni.smartchannelling.entity.Appointment;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.entity.VisitingSolts;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.BookingStatus;

@Service(value="backendProcessService")
public class BackendProcessServiceImpl implements BackendProcessService {
	
	@Autowired
	private BackendProcessDAO backendProcessDAO;
	
	@Autowired
	private VisitingService visitingService;
	
	@Autowired
	private SystemUserService systemUserService;
	
	
	//runs every 5 min
	@Scheduled(fixedDelay=300000)
	@Override
	public void updateExpiredVisitingSlots() {
		try {
			List<VisitingSolts> expiredvisiSolts=backendProcessDAO.getAllActiveVisitingSlotsExpired();
			if(!(expiredvisiSolts==null || expiredvisiSolts.isEmpty())){
				for (VisitingSolts visitingSolt : expiredvisiSolts) {
					visitingSolt.setActiveStatus(ActiveStatus.EXPIRED);
					visitingSolt.setBookingStatus(BookingStatus.EXPIRED);
					if(!(visitingSolt.getAppointments()==null || visitingSolt.getAppointments().isEmpty())){
						for (Appointment appointment : visitingSolt.getAppointments()) {							
							appointment.setActiveStatus(ActiveStatus.EXPIRED);
							appointment.setBookingStatus(BookingStatus.EXPIRED);
						}
					}
				}
			}
			backendProcessDAO.updateAllExpiredVisitingSlots(expiredvisiSolts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	//runs on 1st of every month
	@Scheduled(cron="0 0 0 1 * *")
	@Override
	public void addNewVisitingTimes() {
		try {
			List<Visiting> visitings=backendProcessDAO.getVisitngsToBeCreatedForAnotherThreeMonths();
			System.out.println(visitings.size() + " :VISITING SIZE");
			for (Visiting visiting : visitings) {
				SystemUser systemUser=systemUserService.getSystemUserByUserId(visiting.getCommonDomainProperty().getCreatedUser());
				Date startTimeDate=visiting.getStartTime();
				Date endTimeDate=visiting.getEndTime();			
				SimpleDateFormat sf = new SimpleDateFormat("hh:mm aaa");				
				String startTime=sf.format(startTimeDate);				
				String endTime=sf.format(endTimeDate);
				System.out.println(startTime.toString() + "START TIME");
				System.out.println(endTime.toString() + "END TIME");				
				visitingService.addNewVisiting(visiting, systemUser, startTime, endTime);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
}
