<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>               


  
    <sec:authorize  access="hasRole('ROLE_SUPER_ADMIN') and fullyAuthenticated">
        <li class="tab"><a href="getCreateHospital.htm">Add Hospital</a></li>      
        <li class="tab"><a href="getAllHospitals.htm">View Hospitals</a></li>
     </sec:authorize>
     	
     	 
    <sec:authorize  access="hasRole('ROLE_HOSPITAL_MANAGER') and fullyAuthenticated">   
    	 <li class="tab"><a href="createDoctor.htm">Add New Doctor</a></li> 
          <li class="tab"><a href="getAllDoctors.htm">View Doctors</a></li>    
         <li class="tab"><a href="createVisitingTime.htm">Add New Visiting Time</a></li> 
         <li class="tab"><a href="listAllVisitings.htm">View All Visiting Times</a></li>           
          <li class="tab"><a href="updateHospital.htm?hospitalId=">Update Hospital</a></li>
          <li class="tab" class="tab selected"><a href="createPatient.htm">Channel Doctor</a></li>
     </sec:authorize>
        
        
        <li class="tab"><a href="createSpecialization.htm">Add New Specialization</a></li>
    	<li class="tab"><a href="getAllSpecializations.htm">View Specialization</a></li>    	
    	<li class="tab"><a href="createHospitalRole.htm">Add Hospital Roles</a></li>
    	<li class="tab"><a href="getAllHospitalRoles.htm">View Hospital Roles</a></li>        
                    
