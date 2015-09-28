<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Error page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        
	     <link rel="stylesheet" type="text/css" href="<c:url value="style/client_main_css.css"/>" />   
	     
  <style type="text/css">
#company_name_area_mid a:HOVER {
	text-decoration: none;;
	
}
 
</style>	      
    </head>
   <body style="background-color: #E7DFA0;">
	<c:url value="/j_spring_security_logout" var="logoutUrl"/>
        <!--Header Background Part Starts -->
        <div id="header-bg">
            <!--Header Contant Part Starts -->
            <div id="header">
                <a href="index.html" style="color:#FCF7CC;font-size:26px;">Smart Channeling</a>
                <!--Login Background Starts -->
                <div id="login-bg">
                
                    <!--Login Area Starts -->
                    <div id="login-area">
                    <sec:authorize ifNotGranted="ROLE_USER">
                        <form:form id="form2" name="form2" method="post" action="j_spring_security_check" params="['spring-security-redirect':request.forwardURL]">
					<label>Members Login:</label>
					<input type='hidden' name='spring-security-redirect' value='${spring_security_redirect}'/>
					<input type="text" id="j_username" name="j_username"/>
					  <input type="password" name="j_password" id="j_password" />
					<input type="image" src="images/login-btn.gif" class="login-btn" alt="Login" title="Login" />
					<br class="spacer" />
				</form:form>
                        </sec:authorize>
			<sec:authorize  access="hasRole('ROLE_USER') and fullyAuthenticated">
			<div id="company_name_area_mid" class="col">
                <img src="/images/login-icon.jpg" width="18" height="19" style="padding-left:9px;padding-top:20px;" class="col"/>
                <p style="color:#FCF7CC;font-size:18px;padding-top:20px;padding-left:8px;" class="col">Welcome
               ${sessionScope.systemUser.userName}
				<a href="${logoutUrl}" style="padding-left: 20px;color: #B46F07;">Logout</a></p></div>
			</sec:authorize>
                    </div>
                    <!--Login Area Ends -->
                </div>
                <!--Login Background Ends -->
                <br class="spacer" />
            </div>
            <!--Header Contant Part Ends -->
        </div>
        <!--Header Background Part Ends -->
        <!--Navigation Background Part Starts -->
        <div id="navigation-bg">
            <!--Navigation Part Starts -->
            <div id="navigation">
                <ul class="mainMenu">
                    <li><a href="#" class="selectMenu" title="Home">Home</a></li>
                    <li><a href="#" title="About">About</a></li>
                    <li><a href="#" title="Services">Services</a></li>
                    <li><a href="#" title="Support">Support</a></li>
                    <li><a href="#" title="Chat">Chat</a></li>
                    <li><a href="#" title="History">History</a></li>
                    <li class="noBg"><a href="#" title="Contact">Contact</a></li>
                </ul>
                <a href="#" class="signup" title="signup now"></a>
                <br class="spacer" />
                <ul class="subNav">
                    <li class="noBg"><a href="#" title="Our Benefits">Our Benefits</a></li>
                    <li><a href="#" title="What Our Future Plans">What Our Future Plans</a></li>
                    <li><a href="#" title="Our Success">Our Success</a></li>
                    <li><a href="#" title="Ratings">Ratings</a></li>
                    <li><a href="#" title="Latest Blogs">Latest Blogs</a></li>
                    <li><a href="#" title="News">News</a></li>
                    <li><a href="#" title="Testimonials">Testimonials</a></li>
                    <li><a href="#" title="Comments">Comments</a></li>
                </ul>
                <br class="spacer" />
            </div>
            <!--Navigation Part Ends -->
        </div>
        <!--Navigation Background Part Ends -->
        <!--Our Company Bacground Part Starts -->
        <div id="ourCompany-bg">
        
      
            <div id="ourCompany-part" style="height:300px;">
                <h2 class="ourCompany-hdr">Error Page</h2>
                <p style="padding-top:30px;color:#2B0D0A;font-size:20px;font-family:'Times New Roman', Times, serif">The username or password you entered is incorrect.</p>
                <br class="spacer" />
            </div>
            

        </div>
        <!--Our Company Bacground Part Ends -->

        <!--Footer Part Starts -->
        <div id="footer-bg">
            <!--Footer Menu Part Starts -->
            <div id="footer-menu">
                <ul class="footMenu">
                    <li class="noDivider"><a href="#" title="Home">Home</a></li>
                    <li><a href="#" title="About">About</a></li>
                    <li><a href="#" title="Services">Services</a></li>
                    <li><a href="#" title="Support">Support</a></li>
                    <li><a href="#" title="Chat">Chat</a></li>
                    <li><a href="#" title="History">History</a></li>
                    <li><a href="#" title="Contact">Contact</a></li>
                </ul>
                <br class="spacer" />
                <p class="copyright">Copyright &copy;  All Rights Reserved</p>

            </div>
            <!--Footer Menu Part Ends -->
        </div>
        <!--Footer Part Ends -->

   </body>
</html>
