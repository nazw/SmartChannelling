package com.visni.smartchannelling.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.VisitingDAO;
import com.visni.smartchannelling.entity.CommonDomainProperty;
import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.entity.VisitingSolts;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.BookingStatus;

@Service(value = "visitingService")
public class VisitingServiceImpl implements VisitingService {

    @Autowired
    private VisitingDAO visitingDAO;
    @Autowired
    private CommonDomainProperty cdp;

    @Override
    public String createVisiting(Visiting visiting) throws Exception {
        List<Visiting> visitings = new ArrayList<Visiting>();
        visitings.add(visiting);
        return visitingDAO.createVisiting(visitings);
    }

    @Override
    public String addNewVisiting(Visiting visiting, SystemUser currentUser, String startTime, String endTime) throws Exception {
        List<Visiting> visitings = getVisitingsForTwoMonths(visiting, startTime, endTime, currentUser);
        return visitingDAO.createVisiting(visitings);
    }

    public List<Visiting> getVisitingsForTwoMonths(Visiting visiting, String startTime, String endTime, SystemUser currentUser) throws Exception {
        List<Visiting> visitings = new ArrayList<Visiting>();
        Calendar twoMonth = Calendar.getInstance();
        //setting the day of the week according to the input by user
        twoMonth.set(Calendar.DAY_OF_WEEK, visiting.getDayOfWeek());
        System.out.println("INITIAL START DAY SET :" + twoMonth.getTime());
        Date startDate = twoMonth.getTime();
        if (startDate.before(Calendar.getInstance().getTime())) {
            twoMonth.add(Calendar.DATE, 7);
        }
        startDate = twoMonth.getTime();
        System.out.println("FINALLY START DAY SET :" + twoMonth.getTime());

        twoMonth.add(Calendar.MONTH, ApplicationConstants.VISITING_TIME_CALCULATION_DURATION_MONTHS);
        Date endDate = twoMonth.getTime();
        System.out.println("FINALLY END DAY SET :" + twoMonth.getTime());

        //now generate the visitings for two months
        Calendar increment = Calendar.getInstance();
        increment.setTime(startDate);
        while (increment.getTime().before(endDate)) {
            Visiting visitingNew = new Visiting();
            visitingNew.setStartDate(getProcessedTime(startTime, increment.getTime()));
            visitingNew.setStartTime(getProcessedTime(startTime, increment.getTime()));
            if (StringUtils.isNotBlank(endTime)) {
                visitingNew.setEndTime(getProcessedTime(endTime, increment.getTime()));
                visitingNew.setEndDate(getProcessedTime(endTime, increment.getTime()));
            }
            visitingNew = getCreatedVisiting(visiting, visitingNew, currentUser);
            System.out.println("******************************************************");
            System.out.println(visitingNew.getStartDate() + " : START DATE");
            System.out.println(visitingNew.getStartTime() + " : START TIME");
            System.out.println(visitingNew.getEndDate() + " : END DATE");
            System.out.println(visitingNew.getEndTime() + " : END TIME");
            System.out.println("******************************************************");
            visitings.add(visitingNew);
            increment.add(Calendar.DATE, 7);
            System.out.println("INCREMENTED VAL :" + increment.getTime());
        }
        System.out.println("NUMBER OF VISITINGS OF DOCTOR :" + visitings.size());
        return visitings;
    }

    public Visiting getCreatedVisiting(Visiting visiting, Visiting visitingNew, SystemUser currentUser) throws Exception {
        cdp.setCreatedUser(currentUser.getUserId());
        cdp.setCreationDate(Calendar.getInstance().getTime());
        cdp.setLastModifiedDate(Calendar.getInstance().getTime());
        cdp.setLastModifiedUser(currentUser.getUserId());
        visitingNew.setVisitingStatus(ActiveStatus.ACTIVE);
        visitingNew.setCommonDomainProperty(cdp);
        visitingNew.setAvgDelayPerAppointment(visiting.getAvgDelayPerAppointment());
        visitingNew.setAvgTimePerAppointment(visiting.getAvgTimePerAppointment());
        visitingNew.setCharges(visiting.getCharges());
        visitingNew.setDayOfWeek(visiting.getDayOfWeek());
        visitingNew.setHospitalDoctor(visiting.getHospitalDoctor());
        visitingNew.setNoOfPatients(visiting.getNoOfPatients());
        visitingNew.setVisitingStatus(ActiveStatus.ACTIVE);
        visitingNew = getConstructedVisitingSlots(visitingNew);
        return visitingNew;
    }

    public Visiting getConstructedVisitingSlots(Visiting visiting) throws Exception {
        List<VisitingSolts> visitingSolts = new ArrayList<VisitingSolts>();
        int averageTime = visiting.getAvgTimePerAppointment();
        int numberOfPatients = visiting.getNoOfPatients();
        Date startTime = visiting.getStartTime();
        Date endTime = visiting.getEndTime();
        System.out.println("averageTime :" + averageTime + " :numberOfPatients: " + numberOfPatients + " :startTime: " + startTime + " :endTime: " + endTime);
        if (!(endTime == null)) {
            //the end time is given, so the slots are broken depending on average time per appointment or number of patients
            //first check if an average time per appointment is given
            if (!(averageTime <= 0)) {
                //average time is in minutes- so lets get the difference between start start time and end time in minutes
                int totalDuration = getDateDifferenceInMinutes(startTime, endTime);
                int numberOfSlots = totalDuration / averageTime;
                int slotCount = 0;
                for (int i = 0; i < numberOfSlots; i++) {
                    VisitingSolts visitingSolt = new VisitingSolts();
                    visitingSolt.setActiveStatus(ActiveStatus.ACTIVE);
                    visitingSolt.setBookingStatus(BookingStatus.VACANT);
                    visitingSolt.setCommonProperties(cdp);
                    visitingSolt.setSlotNumber((i + 1));
                    visitingSolt.setStartTime(getSlotStartTime(startTime, i, averageTime));
                    visitingSolt.setEndTime(getSlotEndTime(visitingSolt.getStartTime(), averageTime));
                    visitingSolt.setVisiting(visiting);
                    visitingSolts.add(visitingSolt);
                    slotCount = (i + 1);
                }
                visiting.setNoOfPatients(slotCount);
            } else if (!(numberOfPatients <= 0)) {
                //if not check if the number of patients is given
                int totalDuration = getDateDifferenceInMinutes(startTime, endTime);
                int averageTimePerPatient = totalDuration / numberOfPatients;
                //the number of slots=numberOfPatients
                int slotCount = 0;
                for (int i = 0; i < numberOfPatients; i++) {
                    VisitingSolts visitingSolt = new VisitingSolts();
                    visitingSolt.setActiveStatus(ActiveStatus.ACTIVE);
                    visitingSolt.setBookingStatus(BookingStatus.VACANT);
                    visitingSolt.setCommonProperties(cdp);
                    visitingSolt.setSlotNumber((i + 1));
                    visitingSolt.setStartTime(getSlotStartTime(startTime, i, averageTimePerPatient));
                    visitingSolt.setEndTime(getSlotEndTime(visitingSolt.getStartTime(), averageTimePerPatient));
                    visitingSolt.setVisiting(visiting);
                    visitingSolts.add(visitingSolt);
                    slotCount = (i + 1);
                }
                visiting.setAvgTimePerAppointment(averageTimePerPatient);
            } else {
                //should not come here // if it comes lets take a common average time and do the calculation
                int totalDuration = getDateDifferenceInMinutes(startTime, endTime);
                int numberOfSlots = totalDuration / ApplicationConstants.COMMON_AVERAGE_TIME_PER_APPOINTMENT;
                int slotCount = 0;
                for (int i = 0; i < numberOfSlots; i++) {
                    VisitingSolts visitingSolt = new VisitingSolts();
                    visitingSolt.setActiveStatus(ActiveStatus.ACTIVE);
                    visitingSolt.setBookingStatus(BookingStatus.VACANT);
                    visitingSolt.setCommonProperties(cdp);
                    visitingSolt.setSlotNumber((i + 1));
                    visitingSolt.setStartTime(getSlotStartTime(startTime, i, ApplicationConstants.COMMON_AVERAGE_TIME_PER_APPOINTMENT));
                    visitingSolt.setEndTime(getSlotEndTime(visitingSolt.getStartTime(), ApplicationConstants.COMMON_AVERAGE_TIME_PER_APPOINTMENT));
                    visitingSolt.setVisiting(visiting);
                    visitingSolts.add(visitingSolt);
                    slotCount = (i + 1);
                }
                visiting.setNoOfPatients(slotCount);
            }
        } else {
            //the and time is not given, then the average time per appointment and the number of patients is a must
            if (!(averageTime <= 0)) {
                int totalDuration = (averageTime * numberOfPatients);
                visiting.setEndTime(getVisitingEndTime(startTime, totalDuration));
                visiting.setEndDate(getVisitingEndTime(startTime, totalDuration));
                int slotCount = 0;
                for (int i = 0; i < numberOfPatients; i++) {
                    VisitingSolts visitingSolt = new VisitingSolts();
                    visitingSolt.setActiveStatus(ActiveStatus.ACTIVE);
                    visitingSolt.setBookingStatus(BookingStatus.VACANT);
                    visitingSolt.setCommonProperties(cdp);
                    visitingSolt.setSlotNumber((i + 1));
                    visitingSolt.setStartTime(getSlotStartTime(startTime, i, averageTime));
                    visitingSolt.setEndTime(getSlotEndTime(visitingSolt.getStartTime(), averageTime));
                    visitingSolt.setVisiting(visiting);
                    visitingSolts.add(visitingSolt);
                    slotCount = (i + 1);
                }
                visiting.setNoOfPatients(slotCount);
            } else {
                //should not come here// if did, use the common average time per slot
                int totalDuration = (averageTime * numberOfPatients);
                visiting.setEndTime(getVisitingEndTime(startTime, totalDuration));
                visiting.setEndDate(getVisitingEndTime(startTime, totalDuration));
                int numberOfSlots = totalDuration / ApplicationConstants.COMMON_AVERAGE_TIME_PER_APPOINTMENT;
                int slotCount = 0;
                for (int i = 0; i < numberOfSlots; i++) {
                    VisitingSolts visitingSolt = new VisitingSolts();
                    visitingSolt.setActiveStatus(ActiveStatus.ACTIVE);
                    visitingSolt.setBookingStatus(BookingStatus.VACANT);
                    visitingSolt.setCommonProperties(cdp);
                    visitingSolt.setSlotNumber((i + 1));
                    visitingSolt.setStartTime(getSlotStartTime(startTime, i, ApplicationConstants.COMMON_AVERAGE_TIME_PER_APPOINTMENT));
                    visitingSolt.setEndTime(getSlotEndTime(visitingSolt.getStartTime(), ApplicationConstants.COMMON_AVERAGE_TIME_PER_APPOINTMENT));
                    visitingSolt.setVisiting(visiting);
                    visitingSolts.add(visitingSolt);
                    slotCount = (i + 1);
                }
                visiting.setNoOfPatients(slotCount);
            }
        }
        visiting.setVisitingSolts(visitingSolts);
        return visiting;
    }

    public Date getProcessedTime(String time, Date date) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("hh:mm aaa");
        //constructing the start time date object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Date timeDate = sf.parse(time);
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(timeDate);

        cal.set(Calendar.HOUR, timeCal.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        cal.set(Calendar.AM_PM, timeCal.get(Calendar.AM_PM));
        System.out.println("startTime :" + timeCal.get(Calendar.HOUR) + ":" + timeCal.get(Calendar.MINUTE) + " " + timeCal.get(Calendar.AM_PM));
        System.out.println("FINAL START TIME DATE :" + cal.getTime());
        return cal.getTime();
    }

    public Date getSlotStartTime(Date visitingStartTime, int slotNumber, int averageTimePerSlotinMinutes) throws Exception {
        int minutesSpentSinceStart = (slotNumber * averageTimePerSlotinMinutes);
        Calendar cal = Calendar.getInstance();
        cal.setTime(visitingStartTime);
        cal.add(Calendar.MINUTE, minutesSpentSinceStart);
        return cal.getTime();
    }

    public Date getSlotEndTime(Date slotStartTime, int averageTimePerSlotinMinutes) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(slotStartTime);
        cal.add(Calendar.MINUTE, averageTimePerSlotinMinutes);
        return cal.getTime();
    }

    public Date getVisitingEndTime(Date visitingStartTime, int visitingDuration) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(visitingStartTime);
        cal.add(Calendar.MINUTE, visitingDuration);
        return cal.getTime();
    }

    public int getDateDifferenceInMinutes(Date earlierDate, Date laterDate) throws Exception {
        if (earlierDate == null || laterDate == null) {
            return 0;
        }

        return (int) ((laterDate.getTime() / 60000) - (earlierDate.getTime() / 60000));
    }

    @Override
    public Map<String, Object> listAllVisitingsForHospitalByStatus(String hospitalId, ActiveStatus activeStatus) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map> rawList = visitingDAO.listAllVisitingsForHospitalByStatus(hospitalId, activeStatus);
        Map<String, String> doctorMap = new HashMap<String, String>();
        Map<Integer, Date> monthMap = new HashMap<Integer, Date>();
        for (Map map : rawList) {
            String fullName = "";
            if (!((map.get("title") == null) || (StringUtils.isBlank((String) map.get("title"))))) {
                fullName = (String) map.get("title");
            }
            if (!((map.get("firstName") == null) || (StringUtils.isBlank((String) map.get("firstName"))))) {
                fullName = fullName + " " + (String) map.get("firstName");
            }
            if (!((map.get("middleName") == null) || (StringUtils.isBlank((String) map.get("middleName"))))) {
                fullName = fullName + " " + (String) map.get("middleName");
            }
            if (!((map.get("lastName") == null) || (StringUtils.isBlank((String) map.get("lastName"))))) {
                fullName = fullName + " " + (String) map.get("lastName");
            }
            doctorMap.put((String) map.get("doctorId"), fullName);

            Date date = (Date) map.get("startDate");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            monthMap.put(cal.get(Calendar.MONTH), date);
            map.put("fullName", fullName);
            map.put("availableSlots", visitingDAO.getActiveVisitingSlotCountByActiveAndbookingStatus((String) map.get("visitingId"), ActiveStatus.ACTIVE, BookingStatus.VACANT));
        }
        Collection<Date> dateList = monthMap.values();
        List<Date> list = new ArrayList<Date>();
        for (Date date : dateList) {
            list.add(date);
        }
        Collections.sort(list);
        resultMap.put("visitings", rawList);
        resultMap.put("doctorList", doctorMap);
        resultMap.put("dateList", list);
        return resultMap;
    }

    @Override
    public HospitalDoctor getHospitalDoctorById(String hospitalDoctorId) throws Exception {
        return visitingDAO.getHospitalDoctorById(hospitalDoctorId);
    }

    @Override
    public List<Map> listAllVisitingsForDoctor(String hospitalId, String doctorId) throws Exception {
        return visitingDAO.listAllVisitingsForDoctor(hospitalId, doctorId);

    }

    @Override
    public List<Map> getAllVisitingSlotsForVisiting(String visitingId) throws Exception {
        return visitingDAO.getAllVisitingSlotsForVisiting(visitingId);

    }

    @Override
    public String cancelVisiting(String visitingId) throws Exception {
        return visitingDAO.cancelVisiting(visitingId);

    }

    @Override
    public String activateVisiting(String visitingId) throws Exception {
        return visitingDAO.activateVisiting(visitingId);

    }

    @Override
    public List<Map> lisAllFilteredVisitingsForHospitalByStatus(
            String hospitalId, String doctorId, Date date,
            ActiveStatus activeStatus) throws Exception {
        List<Map> rawList = visitingDAO.lisAllFilteredVisitingsForHospitalByStatus(hospitalId, doctorId, date, activeStatus);
        for (Map map : rawList) {
            String fullName = "";
            if (!((map.get("title") == null) || (StringUtils.isBlank((String) map.get("title"))))) {
                fullName = (String) map.get("title");
            }
            if (!((map.get("firstName") == null) || (StringUtils.isBlank((String) map.get("firstName"))))) {
                fullName = fullName + " " + (String) map.get("firstName");
            }
            if (!((map.get("middleName") == null) || (StringUtils.isBlank((String) map.get("middleName"))))) {
                fullName = fullName + " " + (String) map.get("middleName");
            }
            if (!((map.get("lastName") == null) || (StringUtils.isBlank((String) map.get("lastName"))))) {
                fullName = fullName + " " + (String) map.get("lastName");
            }
            map.put("fullName", fullName);
        }
        return rawList;
    }

    @Override
    public Visiting getVisitingByVisitingSlotId(String visitingSlotsId) throws Exception {
        return visitingDAO.getVisitingByVisitingSlotId(visitingSlotsId);
    }

    @Override
    public List<Map> getFilteredDoctorAppointment(String hospitalId, String doctorId, Date date) throws Exception {
        return visitingDAO.getFilteredDoctorAppointment(hospitalId, doctorId, date);
    }
}
