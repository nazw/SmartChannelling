<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   



<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<div class="logo"> <a href="adminDashboardView.htm" ><img src="images/logo.png" width="287" height="42" /></a></div>

        <sec:authorize  access="hasRole('ROLE_USER') and fullyAuthenticated">
            <div class="signin"><div class="username">Welcome  <span>${sessionScope.systemUser.userName}</span></div>
        <div class="logout"><a href="${logoutUrl}" ><img src="images/logout.png" width="85" height="30" /></a> </div></div>
            </sec:authorize>

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

<div class="clear"></div>