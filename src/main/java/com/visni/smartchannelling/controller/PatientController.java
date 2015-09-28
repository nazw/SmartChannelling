/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.controller;

import com.visni.smartchannelling.entity.Appointment;
import com.visni.smartchannelling.entity.Doctor;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.service.AddressService;
import com.visni.smartchannelling.service.AppointmentService;
import com.visni.smartchannelling.service.DoctorService;
import com.visni.smartchannelling.service.HospitalService;
import com.visni.smartchannelling.service.MasterDataService;
import com.visni.smartchannelling.service.VisitingService;
import com.visni.smartchannelling.service.VisitingSoltsService;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.visni.smartchannelling.util.ApplicationConstants;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;  
import java.util.*; 

@Controller(value = "patientController")
public class PatientController {

    @Autowired
    private MasterDataService masterDataService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private VisitingService visitingService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private VisitingSoltsService visitingSoltsService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private AddressService AddressService;

    @RequestMapping(value = "/createPatient", method = RequestMethod.GET)
    public ModelAndView createPatient(HttpServletRequest request) {

        List<Specialization> specializationList = null;
        ModelMap map = new ModelMap();

        try {
            specializationList = masterDataService.getAllActiveSpecializations();
            SystemUserDetail systemUserDetail = new SystemUserDetail();

            map.put("systemUserDetail", systemUserDetail);
            map.put("specializationList", specializationList);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return new ModelAndView("addPatient", map);
    }

    @RequestMapping(value = "/getAllDoctorsforAppointment", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> getAllDoctorsforAppointment(@RequestParam("specializationId") String specializationId, HttpServletRequest request) {
        List<Map> doctorList = new ArrayList<Map>();
        try {

            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
            doctorList.addAll(doctorService.getAllDoctorsForAppointment(hospitalId, specializationId));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return doctorList;
    }

    @RequestMapping(value = "/getDoctorforAppointment", method = RequestMethod.GET)
    public @ResponseBody
    Map getDoctorforAppointment(@RequestParam("doctorId") String doctorId, HttpServletRequest request) {
        List<Map> doctorList = new ArrayList<Map>();
        Map mapAll = new HashMap();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("MMMM");
        
        try {

            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
            doctorList.addAll(visitingService.listAllVisitingsForDoctor(hospitalId, doctorId));


            Map<Integer, Date> monthMap = new HashMap<Integer, Date>();
            LinkedHashMap<String, Date> dateMap = new LinkedHashMap<String, Date>();
            for (Map map : doctorList) {
                Date date = (Date) map.get("startDate");
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                monthMap.put(cal.get(Calendar.MONTH), date);

            }

            Collection<Date> dateList = monthMap.values();
                      
            List list = new ArrayList();
            for (Date date : dateList) {  
                dateMap.put(simpleDateformat.format(date), date);
                list.add(dateMap);
                System.out.println("datef"+date);
                System.out.println("datefil"+simpleDateformat.format(date));
            }
     
            mapAll.put("doctorList", doctorList);
            mapAll.put("dateList", list);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mapAll;
    }

    @RequestMapping(value = "/getAllVisitingSlotsForVisiting", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> getAllVisitingSlotsForVisiting(@RequestParam("visitingId") String visitingId, HttpServletRequest request) {
        List<Map> visitingSlotList = new ArrayList<Map>();
        try {
            visitingSlotList.addAll(visitingService.getAllVisitingSlotsForVisiting(visitingId));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return visitingSlotList;
    }

    @RequestMapping(value = "/savePatient", method = RequestMethod.POST)
    public ModelAndView savePatient(@ModelAttribute("systemUserDetail") SystemUserDetail systemUserDetail, BindingResult result, HttpServletRequest request) {

        ModelMap map = new ModelMap();
        try {
            SystemUser currentUser = (SystemUser) request.getSession().getAttribute(ApplicationConstants.SYSTEM_USER);
            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);

            Appointment appointment = appointmentService.createAppointment(systemUserDetail, currentUser, hospitalId, request.getParameter("visitingSlotsIdHidden"));

            map.put("appointment", appointment);
            map.put("systemUserDetail", appointment.getPatient().getSystemUserDetail());
            map.put("visitingSolts", appointment.getVisitingSolt());
            map.put("doctor", doctorService.getDoctorById(request.getParameter("doctorIdHidden")).getSystemUserDetail());

            Hospital hospital = hospitalService.getHospitalById(hospitalId);
            double hospitalCharges = hospital.getCharges();

            map.put("hospital", hospital);
            map.put("address", AddressService.getAddressById(hospital.getAddress().getAddressId()));
            map.put("contactNumbersList", hospitalService.getContactNumberByHospitalId(hospital.getHospitalId()));
            map.put("EmailAddressList", hospitalService.getEmailAddressesByHospitalId(hospital.getHospitalId()));

            Visiting visiting = visitingService.getVisitingByVisitingSlotId(appointment.getVisitingSolt().getVisitingSlotsId());
            double doctorCharges = visiting.getCharges();

            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            map.put("hospitalCharges", df.format(hospitalCharges));
            map.put("doctorCharges", df.format(doctorCharges));
            map.put("total", df.format(hospitalCharges + doctorCharges));
            map.put("date", new Date());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("appointmentConfirmation", map);
    }

    @RequestMapping(value = "/cancelAppointment", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> cancelAppointment(@RequestParam("visitingSlotsId") String visitingSlotsId, HttpServletRequest request) {
        Map map = new HashMap();
        List<Map> resultList = new ArrayList<Map>();

        try {

            map.put("result", appointmentService.cancelAppointment(visitingSlotsId));
            resultList.add(map);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultList;
    }

    @RequestMapping(value = "/getPatientByVisitingSlotId", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> getPatientByVisitingSlotId(@RequestParam("visitingSlotsId") String visitingSlotsId, HttpServletRequest request) {
        List<Map> patientDetailsList = new ArrayList<Map>();
        try {
            patientDetailsList.addAll(visitingSoltsService.getPatientByVisitingSlotId(visitingSlotsId));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return patientDetailsList;
    }

    @RequestMapping(value = "/findTimeSlotsForDoctor", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> findTimeSlotsForDoctor(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,@RequestParam("specializationId") String specializationId,@RequestParam("startDate") Date startDate, HttpServletRequest request) {
        List<Map> visitingSlotList = new ArrayList<Map>();
        try {

            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
             visitingSlotList.addAll(visitingSoltsService.findTimeSlotsForDoctor(firstName,lastName,specializationId,startDate,hospitalId));
            System.out.println("visitingSlotListaaaa" + visitingSlotList.size());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return visitingSlotList;
    }

    @RequestMapping(value = "/editPatient", method = RequestMethod.GET)
    public ModelAndView editPatient(@RequestParam("visitingSlotsId") String visitingSlotsId) {

        SystemUserDetail systemUserDetail = new SystemUserDetail();
        try {
            systemUserDetail = visitingSoltsService.getSystemUserDetailByVisitingSlotId(visitingSlotsId);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return new ModelAndView("editPatient", "systemUserDetail", systemUserDetail);

    }

    @RequestMapping(value = "/updateAppointment", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> updateAppointment(@RequestParam("newTimeSlotId") String newTimeSlotId, @RequestParam("currentvVsitingSlotsId") String currentvVsitingSlotsId, HttpServletRequest request) {
        Map map = new HashMap();
        List<Map> resultList = new ArrayList<Map>();

        try {
            map.put("result", appointmentService.updateAppointment(newTimeSlotId, currentvVsitingSlotsId));
            resultList.add(map);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultList;
    }

    @RequestMapping(value = "/updatePatient", method = RequestMethod.POST)
    public ModelAndView updatePatient(@ModelAttribute("systemUserDetail") SystemUserDetail systemUserDetail, BindingResult result, HttpServletRequest request) {

        try {
            SystemUser currentUser = (SystemUser) request.getSession().getAttribute(ApplicationConstants.SYSTEM_USER);
            // String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);       
            appointmentService.updatePatient(systemUserDetail, currentUser);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("adminDashboad");
    }
    
    
        @RequestMapping(value = "/getFilteredDoctorAppointment", method = RequestMethod.GET)
    public @ResponseBody
    Map getFilteredDoctorAppointment(@RequestParam("doctorId") String doctorId, @RequestParam("monthFil") String monthFil,HttpServletRequest request) {
        List<Map> doctorList = new ArrayList<Map>();
        Map mapAll = new HashMap();
        try {

            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
            
            Date date=null;
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            date=sf.parse(monthFil);
            System.out.println("dateee" + date);
            doctorList.addAll(visitingService.getFilteredDoctorAppointment(hospitalId, doctorId,date));

            mapAll.put("doctorList", doctorList);
        

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mapAll;
    }
        
        
}
