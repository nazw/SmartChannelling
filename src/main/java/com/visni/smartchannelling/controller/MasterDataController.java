package com.visni.smartchannelling.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.visni.smartchannelling.entity.Doctor;
import com.visni.smartchannelling.entity.HospitalRole;
import com.visni.smartchannelling.entity.Specialization;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.service.DoctorService;
import com.visni.smartchannelling.service.HospitalService;
import com.visni.smartchannelling.service.MasterDataService;
import com.visni.smartchannelling.util.ApplicationConstants;
import com.visni.smartchannelling.util.UserRoleType;

@Controller
@SuppressWarnings({"rawtypes"})
public class MasterDataController {

    @Autowired
    private MasterDataService masterDataService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "addSpecialization", method = RequestMethod.POST)
    public ModelAndView addSpecialization(@ModelAttribute("specialization") Specialization specialization, BindingResult result, HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        Specialization specializationAdd = new Specialization();
        String errorMessage = null;
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
            if (systemUser != null) {
                masterDataService.addSpecialization(specialization, systemUser.getUserId());
            } else {
                return new ModelAndView("systemError");
            }
        } catch (ConstraintViolationException cve) {
            cve.printStackTrace();
            errorMessage = " Specialization already exists";
            modelMap.put("errorMessage", errorMessage);
            modelMap.put("specialization", specializationAdd);
            return new ModelAndView("addSpecialization", modelMap);
        } catch (DataIntegrityViolationException dive) {
            dive.printStackTrace();
            errorMessage = " Specialization already exists";
            modelMap.put("errorMessage", errorMessage);
            modelMap.put("specialization", specializationAdd);
            return new ModelAndView("addSpecialization", modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("systemError");
        }
        return getAllSpecializations();
    }
    
    @RequestMapping(value = "getUpdateSpecialization", method = RequestMethod.GET)
    public ModelAndView getUpdateSpecialization(@RequestParam("specId") String specializationId, HttpServletRequest request) {
    	ModelMap map=new ModelMap();
    	try {        	
            Specialization specialization=masterDataService.getSpecializationById(specializationId);
            map.put("specialization", specialization);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("systemError");
        }
        return new ModelAndView("editSpecialization", map);
    }

    @RequestMapping(value = "updateSpecialization", method = RequestMethod.POST)
    public String updateSpecialization(@ModelAttribute("specialization") Specialization specialization, BindingResult result, HttpServletRequest request) {
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
            masterDataService.updateSpecialization(specialization, systemUser.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/systemerror.htm";
        }
        return "redirect:/getAllSpecializations.htm";
    }

    @RequestMapping(value = "deleteSpecialization", method = RequestMethod.GET)
    public String deleteSpecialization(@RequestParam("specializationId") String specializationId, BindingResult result, HttpServletRequest request) {
        try {
            masterDataService.deleteSpecialzation(specializationId);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/systemerror.htm";
        }
        return "redirect:/getAllSpecializations.htm";
    }

    @RequestMapping(value = "getAllSpecializations", method = RequestMethod.GET)
    public ModelAndView getAllSpecializations() {
        List<Specialization> specializationList = null;
        ModelMap map = new ModelMap();
        try {
            specializationList = masterDataService.getAllActiveSpecializations();
            System.out.println("specializationList " + specializationList);
            System.out.println("specializationList size" + specializationList.size());
            map.put("specializationList", specializationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("manageSpecialization", map);
    }

    @RequestMapping(value = "addHospitalRole", method = RequestMethod.POST)
    public ModelAndView addHospitalRole(@ModelAttribute("hospitalRole") HospitalRole hospitalRole, BindingResult result, HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        HospitalRole hospitalRoleAdd = new HospitalRole();
        String errorMessage = null;
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
            if (systemUser != null) {
                masterDataService.addHospitalRole(hospitalRole, systemUser.getUserId());
            } else {
                return new ModelAndView("systemError");
            }
        } catch (ConstraintViolationException cve) {
            cve.printStackTrace();
            errorMessage = " Hospital Role already exists";
            modelMap.put("errorMessage", errorMessage);
            modelMap.put("hospitalRole", hospitalRoleAdd);
            return new ModelAndView("addHospitalRole", modelMap);
        } catch (DataIntegrityViolationException dive) {
            dive.printStackTrace();
            errorMessage = " Hospital Role already exists";
            modelMap.put("errorMessage", errorMessage);
            modelMap.put("hospitalRole", hospitalRoleAdd);
            return new ModelAndView("addHospitalRole", modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("systemError");
        }
        return getAllHospitalRoles();
    }
    
    @RequestMapping(value = "getUpdateHospitalRole", method = RequestMethod.GET)
    public ModelAndView getUpdateHospitalRole(@RequestParam("hid") String hospitalRoleId, HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();       
        try {
           HospitalRole hospitalRole=masterDataService.getHospitalroleById(hospitalRoleId);
           modelMap.put("hospitalRole", hospitalRole);       
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("systemError");
        }
        return new ModelAndView("updateHospitalRole",modelMap);
    }

    @RequestMapping(value = "updateHospitalRole", method = RequestMethod.POST)
    public String updateHospitalRole(@ModelAttribute("hospitalRole") HospitalRole hospitalRole, BindingResult result, HttpServletRequest request) {
    	try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
            if (systemUser != null) {
                masterDataService.updateHospitalRole(hospitalRole, systemUser.getUserId());
            } else {
                return "redirect:/systemerror.htm";
            }        
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/systemerror.htm";
        }
        return "redirect:/getAllHospitalRoles.htm";
    }

    @RequestMapping(value = "deleteHospitalRole", method = RequestMethod.POST)
    public String deleteHospitalRole(@RequestParam("hospitalRoleId") String hospitalRoleId, BindingResult result, HttpServletRequest request) {

        try {

            masterDataService.deleteHospitalRole(hospitalRoleId);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/systemerror.htm";

        }

        return "redirect:/getAllHospitalRoles.htm";
    }

    @RequestMapping(value = "getAllHospitalRoles", method = RequestMethod.GET)
    public ModelAndView getAllHospitalRoles() {
        List<HospitalRole> hospitalRoles = null;
        ModelMap map = new ModelMap();

        try {
            hospitalRoles = masterDataService.getAllActiveHospitalRole();
            System.out.println("hospitalRoles " + hospitalRoles);
            System.out.println("hospitalRoles size " + hospitalRoles.size());
            map.put("hospitalRoles", hospitalRoles);
        } catch (Exception e) {
        }

        return new ModelAndView("manageHospitalRoles", map);
    }

    @RequestMapping(value = "getAllHospitals", method = RequestMethod.GET)
    public ModelAndView getAllHospitals(@ModelAttribute("hospital") Specialization specialization, BindingResult result, HttpServletRequest request) {

        List<Map> hospitals = null;
        ModelMap map = new ModelMap();

        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(ApplicationConstants.SYSTEM_USER);
            boolean isSuperAdmin = false;
            for (UserRole userRole : systemUser.getUserRoles()) {
                if (userRole.getUserRoleType().equals(UserRoleType.ROLE_SUPER_ADMIN)) {
                    isSuperAdmin = true;
                }
            }
            hospitals = hospitalService.getAllActiveHospitalsForUser(systemUser.getUserId(), isSuperAdmin);

            System.out.println("hospitalsize " + hospitals.size());

            map.put("hospitals", hospitals);
        } catch (Exception e) {
        }

        return new ModelAndView("manageHospitals", map);
    }

    @RequestMapping(value = "getAllDoctors", method = RequestMethod.GET)
    public ModelAndView getAllDoctors(@ModelAttribute("doctor") Doctor doctor, BindingResult result) {
        List<Map> doctorsList = null;
        ModelMap map = new ModelMap();

        try {
            doctorsList = doctorService.getAllActiveDoctors();

            System.out.println("doctorsList " + doctorsList.size());

            map.put("doctorsList", doctorsList);
        } catch (Exception e) {
        }

        return new ModelAndView("manageDoctors", map);
    }
}
