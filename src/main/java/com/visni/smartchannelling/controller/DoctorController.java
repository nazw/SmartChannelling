package com.visni.smartchannelling.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.visni.smartchannelling.entity.Doctor;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.service.DoctorService;
import com.visni.smartchannelling.service.MasterDataService;
import com.visni.smartchannelling.util.ApplicationConstants;

@Controller(value="doctorController")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@RequestMapping(value = "/createDoctor", method = RequestMethod.GET)
    public ModelAndView createDoctor(HttpServletRequest request) {
		Doctor doctor=new Doctor();
		ModelMap map=new ModelMap();
		map.put("doctor", doctor);
		try {
			map.put("hospitalRoles", masterDataService.getAllActiveHospitalRole());
			map.put("specializations", masterDataService.getAllActiveSpecializations());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("addDoctor",map);
    }
	
	@RequestMapping(value = "/saveDoctor", method = RequestMethod.POST)
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor, BindingResult result, HttpServletRequest request) {


        try {

        	String[] hospitalRoleIds=request.getParameterValues("doctorHospitalRoles");        	
        	String[] spcailizationIds=request.getParameterValues("doctorSpecializations");
        	
        	SystemUser currentUser = (SystemUser) request.getSession().getAttribute(ApplicationConstants.SYSTEM_USER);
            String hospitalId=(String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
            
            doctorService.createNewDoctor(doctor, currentUser, hospitalRoleIds, spcailizationIds,hospitalId);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:getAllDoctors.htm";

    }

}
