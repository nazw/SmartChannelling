package com.visni.smartchannelling.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.visni.smartchannelling.entity.HospitalDoctor;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.Visiting;
import com.visni.smartchannelling.service.DoctorService;
import com.visni.smartchannelling.service.VisitingService;
import com.visni.smartchannelling.service.VisitingSoltsService;
import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.HospitalDoctorEditor;

@Controller(value = "visitingController")
public class VisitingController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private VisitingService visitingService;
    @Autowired
    private VisitingSoltsService visitingSoltsService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(HospitalDoctor.class, new HospitalDoctorEditor(visitingService));
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }

    @RequestMapping(value = "/createVisitingTime", method = RequestMethod.GET)
    public ModelAndView createVisitingTime(HttpServletRequest request) {
        ModelMap map = new ModelMap();
        try {
            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
            map.put("hospitalDoctors", doctorService.getAllDoctorsForHospital(hospitalId));
            map.put("visiting", new Visiting());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ModelAndView("addVisitingTime", map);
    }

    @RequestMapping(value = "/addNewVisiting", method = RequestMethod.POST)
    public String addNewVisiting(@ModelAttribute("visiting") Visiting visiting, HttpServletRequest request) {
        SystemUser currentUser = (SystemUser) request.getSession().getAttribute(ApplicationConstants.SYSTEM_USER);        
         //String startDate=request.getParameter("visStartDate");
        String startTime = request.getParameter("visStartTime");
        // String endDate=request.getParameter("visEndDate");
        String endTime = request.getParameter("visEndTime");
        try {            
            visitingService.addNewVisiting(visiting, currentUser, startTime, endTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:listAllVisitings.htm";
    }

    @RequestMapping(value = "/listAllVisitings", method = RequestMethod.GET)
    public ModelAndView listAllVisitings(HttpServletRequest request) {
        ModelMap map = new ModelMap();
        try {
            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
            Map<String,Object> resultMap=visitingService.listAllVisitingsForHospitalByStatus(hospitalId, ActiveStatus.ACTIVE);
            map.put("visitings", resultMap.get("visitings"));
            map.put("doctorList", resultMap.get("doctorList"));
            map.put("dateList", resultMap.get("dateList"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ModelAndView("listVisitings", map);
    }
    
 
    @RequestMapping(value = "/filterAllVisitings", method = RequestMethod.POST)
    public ModelAndView filterAllVisitings(HttpServletRequest request) {
        ModelMap map = new ModelMap();
        try {
        	String[] criteriaNames=request.getParameterValues("criteriaName");
        	String month=request.getParameter("month");
        	String doctorId=null;
        	Date date=null;
        	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        	if(!(criteriaNames==null || criteriaNames.length<=0)){
        		for (String string : criteriaNames) {
					if(string.equals("DOCTOR")){
						doctorId=request.getParameter("doctorId");
					}else if(string.equals("MONTH")){
						date=sf.parse(month);
					}					
				}
        	}else{
        		doctorId=request.getParameter("doctorId");
        		date=sf.parse(month);
        	}
            String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
            map.put("visitings", visitingService.lisAllFilteredVisitingsForHospitalByStatus(hospitalId,doctorId,date, ActiveStatus.ACTIVE));            
            Map<String,Object> resultMap=visitingService.listAllVisitingsForHospitalByStatus(hospitalId, ActiveStatus.ACTIVE);
            map.put("doctorList", resultMap.get("doctorList"));
            map.put("doctorId", doctorId);
            map.put("date", date);
            map.put("dateList", resultMap.get("dateList"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ModelAndView("listVisitings", map);
    }

    @RequestMapping(value = "/cancelVisitings", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> cancelVisitings(@RequestParam("visitingId") String visitingId, HttpServletRequest request) {
        List<Map> visitingsList = new ArrayList<Map>();
        try {


            if (visitingService.cancelVisiting(visitingId).equals(ApplicationConstants.SUCCESS)) {
                String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
                Map<String,Object> resultMap=visitingService.listAllVisitingsForHospitalByStatus(hospitalId, ActiveStatus.ACTIVE);
                visitingsList=(List<Map>) resultMap.get("visitings");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return visitingsList;
    }

    @RequestMapping(value = "/activateVisiting", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> activateVisiting(@RequestParam("visitingId") String visitingId, HttpServletRequest request) {
        List<Map> visitingsList = new ArrayList<Map>();
        try {


            if (visitingService.activateVisiting(visitingId).equals(ApplicationConstants.SUCCESS)) {
                String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
                Map<String,Object> resultMap=visitingService.listAllVisitingsForHospitalByStatus(hospitalId, ActiveStatus.ACTIVE);
                visitingsList=(List<Map>) resultMap.get("visitings");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return visitingsList;
    }

    @RequestMapping(value = "/activateVisitingSlot", method = RequestMethod.GET)
    public @ResponseBody
    List<Map> activateVisitingSlot(@RequestParam("visitingSlotsId") String visitingSlotsId, HttpServletRequest request) {
        Map map = new HashMap();
        List<Map> resultList = new ArrayList<Map>();
        String result = null;
        try {

            result = visitingSoltsService.activateVisitingSlot(visitingSlotsId);
            map.put("result", result);
            resultList.add(map);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultList;
    }

    @RequestMapping(value = "/cancelVisitingSlot", method = RequestMethod.GET)
    public @ResponseBody List<Map> cancelVisitingSlot(@RequestParam("visitingSlotsId") String visitingSlotsId, HttpServletRequest request) {
        Map map = new HashMap();
        List<Map> resultList = new ArrayList<Map>();
  
        try {
     
            map.put("result", visitingSoltsService.cancelVisitingSlot(visitingSlotsId));
            resultList.add(map);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultList;
    }

    @RequestMapping(value = "/manageVisitingSlots", method = RequestMethod.POST)
    public ModelAndView manageVisitingSlots(HttpServletRequest request) {

        ModelMap map = new ModelMap();
        List<Map> visitingSlotList = new ArrayList<Map>();
        try {

            visitingSlotList.addAll(visitingService.getAllVisitingSlotsForVisiting(request.getParameter("visitingId")));
            map.put("visitingSlotList", visitingSlotList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new ModelAndView("manageVisitingSlots", map);



    }
}
