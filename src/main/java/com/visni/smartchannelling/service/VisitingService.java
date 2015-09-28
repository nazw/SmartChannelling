package com.visni.smartchannelling.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.util.ActiveStatus;

public interface VisitingService {

    /**
     * @param visiting
     * @return
     * @throws Exception
     */
    public String createVisiting(Visiting visiting) throws Exception;

    /**
     * @param visiting
     * @param currentUser
     * @return
     * @throws Exception
     */
    public String addNewVisiting(Visiting visiting, SystemUser currentUser, String startTime, String endTime) throws Exception;

    /**
     * @param hospitalId
     * @param activeStatus
     * @return
     * @throws Exception
     */
    public Map<String, Object> listAllVisitingsForHospitalByStatus(String hospitalId, ActiveStatus activeStatus) throws Exception;

    /**
     * @param hospitalDoctorId
     * @return
     * @throws Exception
     */
    public HospitalDoctor getHospitalDoctorById(String hospitalDoctorId) throws Exception;

    public List<Map> listAllVisitingsForDoctor(String hospitalId, String doctorId) throws Exception;

    public List<Map> getAllVisitingSlotsForVisiting(String visitingId) throws Exception;

    public String cancelVisiting(String visitingId) throws Exception;

    public String activateVisiting(String visitingId) throws Exception;

    /**
     * @param hospitalId
     * @param doctorId
     * @param date
     * @param activeStatus
     * @return
     * @throws Exception
     */
    public List<Map> lisAllFilteredVisitingsForHospitalByStatus(
            String hospitalId, String doctorId, Date date,
            ActiveStatus activeStatus) throws Exception;

    public Visiting getVisitingByVisitingSlotId(String visitingSlotsId) throws Exception;

    public List<Map> getFilteredDoctorAppointment(String hospitalId, String doctorId, Date date) throws Exception;
}
