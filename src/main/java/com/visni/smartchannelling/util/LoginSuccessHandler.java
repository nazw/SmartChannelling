package com.visni.smartchannelling.util;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import com.visni.smartchannelling.entity.HospitalManager;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.UserRole;
import com.visni.smartchannelling.service.SystemUserService;


@Component(value = "loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private SystemUserService systemUserService;
    
    
    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
    	
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) (principal instanceof UserDetails ? principal : null);
            //check if the user is logged in
            if (userDetails != null) {
                try {
                    SystemUser systemUserNew = systemUserService.getSystemUserByUserName(userDetails.getUsername());
                    Set<UserRole> userRoles = systemUserNew.getUserRoles();
                    
                    HttpSession session = request.getSession();
                    
                    session.setMaxInactiveInterval(10000000);
                    session.setAttribute(ApplicationConstants.SYSTEM_USER, systemUserNew);
                    
                    //setting the hospital id to the session
                    Set<HospitalManager> hospitalManagers=systemUserNew.getHopspitalManagers();
                    if(!((hospitalManagers==null)|| (hospitalManagers.isEmpty()))){                    	 
                    	for (HospitalManager hospitalManager : hospitalManagers) {
                    		session.setAttribute(ApplicationConstants.HOSPITAL_ID, hospitalManager.getHospital().getHospitalId());
						}
                    }
                  
                    //temporarily had code this
                    //session.setAttribute(ApplicationConstants.HOSPITAL_ID, "HP0000000001");
                    
                    DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
                
                    if( defaultSavedRequest != null) {
                    	
                    	target.onAuthenticationSuccess(request, response, authentication);
                    } else {
                    	
                    	if (!(userRoles.isEmpty())) {
                            for (UserRole userRole : userRoles) {
                            	if(userRole.getUserRoleType() == UserRoleType.ROLE_SUPER_ADMIN){
                            		response.sendRedirect("adminDashboardView.htm"); 
                            	}else if (userRole.getUserRoleType() == UserRoleType.ROLE_HOSPITAL_MANAGER) {
                            		response.sendRedirect("adminDashboardView.htm");
                                } 
                            }

                    	}
                    	
                    }
                    

                } catch (Exception e) {
                   e.printStackTrace();
                }

           }
        }



    }
}
        
