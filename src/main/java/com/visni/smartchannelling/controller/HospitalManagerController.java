/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.visni.smartchannelling.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.visni.smartchannelling.entity.ContactNumber;
import com.visni.smartchannelling.entity.EmailAddress;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalSpecialization;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.SystemUserDetail;
import com.visni.smartchannelling.service.HospitalService;
import com.visni.smartchannelling.service.MasterDataService;
import com.visni.smartchannelling.service.SystemUserService;
import com.visni.smartchannelling.util.ApplicationConstants;

/**
 * @author visni
 */
@Controller
public class HospitalManagerController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private MasterDataService masterDataService;

    @RequestMapping(value = "/createHospital", method = RequestMethod.POST)
    public ModelAndView createHospital(@ModelAttribute("hospital") Hospital hospital, BindingResult result, HttpServletRequest request) {

        List<Specialization> specializations = null;
        String exmessage = null;
        ModelMap map = new ModelMap();
        // get the list of selected specialization ids
        String[] selectedSpecializationIds = request.getParameterValues("hospitalSpecializations");
        List<HospitalSpecialization> speList = new ArrayList<HospitalSpecialization>();
        if (selectedSpecializationIds.length > 0) {
            for (String specializationId : selectedSpecializationIds) {
                System.out.println("specializationIds :" + specializationId);
                Specialization specialization = new Specialization();
                specialization.setSpecializationId(specializationId);
                HospitalSpecialization hospitalSpecialization = new HospitalSpecialization();
                hospitalSpecialization.setSpecialization(specialization);
                speList.add(hospitalSpecialization);
            }
        }

        hospital.setHospitalSpecializations(speList);

        // only the email address values are set at this point
        for (EmailAddress emailAddress : hospital.getEmailAddresses()) {
            System.out.println("emailAddress :" + emailAddress.getEmailAddressValue());
        }

        // only the contact number values are set at this point
        for (ContactNumber contactNumber : hospital.getContactNumbers()) {
            System.out.println("contactNumber :" + contactNumber.getContactNumberValue());
        }

        Map<String, Object> savedValues = new HashMap<String, Object>();
        try {

            specializations = masterDataService.getAllActiveSpecializations();

            SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
            savedValues = hospitalService.createHospital(hospital, systemUser.getUserId());

        } catch (Exception e) {
            String msgExep = e.getMessage();
            System.out.println("msgExep" + msgExep);

            map.put("hospital", hospital);
            map.put("specializations", specializations);

            if (msgExep.contains("com.visni.smartchannelling.entity.EmailAddress")) {
                exmessage = "Email already exists";
                map.put("errorMessage", exmessage);
            } else if (msgExep.contains("com.visni.smartchannelling.entity.ContactNumber")) {
                exmessage = "Primary Contact Number already exists";
                map.put("errorMessage", exmessage);
            } else if (msgExep.contains("com.visni.smartchannelling.entity.Hospital")) {
                exmessage = "Hospital Registration Number already exists";
                map.put("errorMessage", exmessage);
            }

            return new ModelAndView("addHospital", map);
        }

        map.put("systemUserDetail", savedValues.get("systemUserDetail"));
        map.put("userName", savedValues.get("userName"));
        map.put("password", savedValues.get("password"));
        return new ModelAndView("getHospitalManagerDetail", map);
    }

    @RequestMapping(value = "/updateHospital", method = RequestMethod.GET)
    public ModelAndView getUpdateHospital(HttpServletRequest request) {
        Hospital hospital = new Hospital();
        List<Specialization> specializations = null;
        ModelMap modelMap = new ModelMap();
        
        try {
        	String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
        	if(StringUtils.isBlank(hospitalId)){
        		 hospitalId=request.getParameter("hospitalId");        		
        	}
            hospital = hospitalService.getHospitalById(hospitalId);
            specializations = masterDataService.getAllActiveSpecializations();         
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        modelMap.put("hospitalSpecializationsList", hospital.getHospitalSpecializations());
	modelMap.put("hospital", hospital);
	modelMap.put("specializations", specializations);
        
        return new ModelAndView("editHospital", modelMap);
    }

    @RequestMapping(value = "/deleteHospital", method = RequestMethod.GET)
    public String deleteHospital(@RequestParam("hospitalId") String hospitalId, HttpServletRequest request) {

        try {
            hospitalService.deleteHospital(hospitalId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @RequestMapping(value = "/updateHospital", method = RequestMethod.POST)
    public ModelAndView updateHospital(@ModelAttribute("hospital") Hospital hospital, BindingResult result,HttpServletRequest request) {

        SystemUser currentUser = (SystemUser) request.getSession().getAttribute(ApplicationConstants.SYSTEM_USER);

        try {
                    // get the list of selected specialization ids
        String[] selectedSpecializationIds = request.getParameterValues("hospitalSpecializations");
        List<HospitalSpecialization> speList = new ArrayList<HospitalSpecialization>();
        if ((! (selectedSpecializationIds==null)) && selectedSpecializationIds.length > 0) {
            for (String specializationId : selectedSpecializationIds) {
                System.out.println("specializationIds :" + specializationId);
                Specialization specialization = new Specialization();
                specialization.setSpecializationId(specializationId);
                HospitalSpecialization hospitalSpecialization = new HospitalSpecialization();
                hospitalSpecialization.setSpecialization(specialization);
                speList.add(hospitalSpecialization);
            }
        }
             hospital.setHospitalSpecializations(speList);
             
             
            hospitalService.updateHospital(hospital, currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("adminDashboad");
    }

    @RequestMapping(value = "/updateHospitalManager", method = RequestMethod.POST)
    public String updateHospitalManager(@ModelAttribute("systemUserDetail") SystemUserDetail systemUserDetail, BindingResult result,
            HttpServletRequest request) {
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");

            systemUserService.updateSystemUserDetails(systemUserDetail, systemUser.getUserId());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:adminDashboardView.htm";
    }
}
