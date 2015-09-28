package com.visni.smartchannelling.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.visni.smartchannelling.entity.Address;
import com.visni.smartchannelling.entity.Hospital;
import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.service.AddressService;
import com.visni.smartchannelling.service.DoctorService;
import com.visni.smartchannelling.service.HospitalManagerService;
import com.visni.smartchannelling.service.MasterDataService;
import com.visni.smartchannelling.util.AccountStatus;
import com.visni.smartchannelling.util.AddressEditor;
import com.visni.smartchannelling.util.ApplicationConstants;

@Controller
public class AdminController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private MasterDataService masterDataService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private HospitalManagerService hospitalManagerService;
    
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {

	binder.registerCustomEditor(Address.class, new AddressEditor(addressService));
    }

    @RequestMapping(value = "/getCreateHospital", method = RequestMethod.GET)
    public ModelAndView createHospital(HttpServletRequest request) {
	Hospital hospital = new Hospital();

	List<Specialization> specializations = null;
	try {
	    specializations = masterDataService.getAllActiveSpecializations();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	ModelMap modelMap = new ModelMap();
	modelMap.put("hospital", hospital);
	modelMap.put("specializations", specializations);

	return new ModelAndView("addHospital", modelMap);
    }

    @RequestMapping(value = "/createHospitalRole", method = RequestMethod.GET)
    public ModelAndView createHospitalRole(HttpServletRequest request) {

	ModelMap modelMap = new ModelMap();
	HospitalRole hospitalRole = new HospitalRole();
	modelMap.put("hospitalRole", hospitalRole);

	return new ModelAndView("addHospitalRole", modelMap);
    }

    
    @RequestMapping(value = "/adminDashboardView.htm", method = RequestMethod.GET)
    public ModelAndView getAdminDashboad(HttpServletRequest request) {

	String hospitalId = (String) request.getSession().getAttribute(ApplicationConstants.HOSPITAL_ID);
	ModelMap map = new ModelMap();
	Collection<Map> hospitalDoctorsList;
	List<Hospital> hospitals=null;
	try {
		hospitalDoctorsList = doctorService.getAllActiveDoctorsForHospitalWithVistingData(hospitalId);		
		map.put("hospitalDoctorList", hospitalDoctorsList);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		hospitals=hospitalManagerService.getAllAvailableHospitalsByStatus(null);	
		map.put("hospitals", hospitals);
	} catch (Exception e) {
		e.printStackTrace();
	}

	
	
	
	return new ModelAndView("adminDashboad",map);
    }

    // @RequestMapping(value = "/updateHospital", method = RequestMethod.GET)
    // public ModelAndView updateHospital(HttpServletRequest request){
    //
    //
    // return new ModelAndView("editHospital");
    // }

    @RequestMapping(value = "/createSpecialization", method = RequestMethod.GET)
    public ModelAndView createSpecialization(HttpServletRequest request) {

	ModelMap modelMap = new ModelMap();
	Specialization specialization = new Specialization();
	modelMap.put("specialization", specialization);

	return new ModelAndView("addSpecialization", modelMap);
    }

    @RequestMapping(value = "/manageSpecialization", method = RequestMethod.GET)
    public ModelAndView manageSpecialization(HttpServletRequest request) {

	return new ModelAndView("manageSpecialization");
    }

}
