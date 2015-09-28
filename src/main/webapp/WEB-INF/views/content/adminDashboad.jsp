<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   

<style>
#mytable {
	font: normal 11px auto "Trebuchet MS", Verdana, Arial, Helvetica,
		sans-serif;
	color: #4f6b72;
	padding: 0;
	margin: 0;
        width: 100%;
}

th {
	font: bold 08px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #ffffff;
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	border-top: 1px solid #C1DAD7;
	letter-spacing: 2px;
	text-transform: uppercase;
	text-align: left;
	padding: 6px 6px 6px 12px;
	background: #0CA4D2 url(images/bg_header.jpg) no-repeat;
	min-width: 5px;
}

th.nobg {
	border-top: 0;
	border-left: 0;
	border-right: 1px solid #C1DAD7;
	background: none;
	min-width: 5px;
}

td {
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	background: #fff;
	 padding: 2px 2px 2px 4px;
	font: bold 12px "Trebuchet MS",Verdana,Arial,Helvetica,sans-serif;
}

td.alt {
	background: #F5FAFA;
	color: #797268;
}

th.spec {
	border-left: 1px solid #C1DAD7;
	border-top: 0;
	background: #fff;
	font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
        background-color:#0CA4D2 ;
}

th.specalt {
	border-left: 1px solid #C1DAD7;
	border-top: 0;
	background: #f5fafa;
	font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #797268;
}

.spec div,.specalt div {
	font-weight: bold;
	text-transform: capitalize;
}

.notavailable {
	font-size: 14px;
	text-align: center;
}

table.doctorschedule td {
	border-right: none;
	border-bottom: none;
	color: #000;
}
</style>

<sec:authorize  access="hasRole('ROLE_HOSPITAL_MANAGER') and fullyAuthenticated">      
<table id="mytable" cellspacing="0">
	<!-- Table Headers Start -->
	<tr>
		<th scope="col" abbr="Doctor name" class="nobg" style="color: #333333;font-size: 12px;">Doctor</th>
		<th scope="col" abbr="Sun">Sunday</th>
		<th scope="col" abbr="Mon">Monday</th>
		<th scope="col" abbr="Tue">Tuesday</th>
		<th scope="col" abbr="Wed">Wednesday</th>
		<th scope="col" abbr="Thu">Thursday</th>
		<th scope="col" abbr="Fri">Friday</th>
		<th scope="col" abbr="Sat">Saturday</th>
	</tr>

	<!-- Table Headers End -->

	<c:forEach items="${hospitalDoctorList}" var="doctor">		
		<tr>
                    <th scope="row" class="spec" ><c:out value="${doctor.fullName}"></c:out>
				<div>
					-
					<c:forEach var="entry" items="${doctor.doctorSpecializations}" varStatus="status">
					    <c:out value="${entry.value}"/>
					    <c:if
							test="${(fn:length(doctor.doctorSpecializations)) != status.count}">
							,
						</c:if>
					</c:forEach>
					
				</div>
			</th>

			<c:set var="visitingMap" value="${doctor.visitings}"></c:set>

			<c:forEach begin="1" end="7" varStatus="status">
				<c:set var="isValueExist" value="false" />
				<c:set var="count" value="${status.count-1}" />
				<c:forEach items="${visitingMap}" var="visiting">
					<c:if test="${visiting.dayOfWeek == status.count }">
						<c:set var="isValueExist" value="true" />
						<td>
							<table class="doctorschedule">
								<tr>
									<td colspan="2"><fmt:formatDate pattern="hh:mm aaa" value="${visiting.startTime}" /> -
										<fmt:formatDate pattern="hh:mm aaa" value="${visiting.endTime}" /></td>
								</tr>
								<tr>
									<td>Patients</td>
									<td><c:out value="${visiting.allSlots}"></c:out></td>
								</tr>
								<tr>
									<td>Booked</td>
									<td><c:out value="${visiting.bookedSlots}"></c:out></td>
								</tr>
								<tr>
									<td>Free</td>
									<td><c:out value="${visiting.vacantSlots}"></c:out></td>
								</tr>
							</table>
						</td>
					</c:if>
				</c:forEach>
				<c:if test="${isValueExist=='false' }">
					<td class="notavailable">N/A</td>
				</c:if>
			</c:forEach>

		</tr>

	</c:forEach>

</table>
</sec:authorize>

<sec:authorize  access="hasRole('ROLE_SUPER_ADMIN') and fullyAuthenticated"> 
    <div class="row" style="width: 97%;overflow: auto">
	<table id="mytable" cellspacing="0">
	<!-- Table Headers Start -->
	<tr>
		<th scope="col" abbr="Doctor name">Hospital</th>
		<th scope="col" abbr="Sun">Manager</th>
		<th scope="col" abbr="Mon">Doctors</th>
		<th scope="col" abbr="Tue">Address</th>
		<th scope="col" abbr="Wed">Contact</th>	
		<th scope="col" abbr="Wed">Email</th>						
	</tr>

	<!-- Table Headers End -->

	<c:forEach items="${hospitals}" var="hospital">		
		<tr>
			<th scope="row" class="spec"><c:out value="${hospital.hospitalName}"></c:out>
				<div>
					<c:out value="${hospital.registrationNumber}"></c:out>
				</div>
				<div>
					-
					<c:forEach var="hospitalSpecialization" items="${hospital.hospitalSpecializations}" varStatus="status">
					    <c:out value="${hospitalSpecialization.specialization.name}"/>
					    <c:if
							test="${(fn:length(hospital.hospitalSpecializations)) != status.count}">
							,
						</c:if>
					</c:forEach>
					
				</div>
			</th>
			<td>
				<c:forEach var="hopspitalManager" items="${hospital.hopspitalManagers}">
					<c:choose>
						<c:when test="${! empty  hopspitalManager.systemUser.systemUserDetail.firstName}">
							<c:out value="${hopspitalManager.systemUser.systemUserDetail.title}"></c:out>
							<c:out value="${hopspitalManager.systemUser.systemUserDetail.firstName}"></c:out>
							<c:out value="${hopspitalManager.systemUser.systemUserDetail.middleName}"></c:out>
							<c:out value="${hopspitalManager.systemUser.systemUserDetail.lastName}"></c:out>
						</c:when>
						<c:otherwise>
							<c:out value="${hopspitalManager.systemUser.userName}"></c:out>
						</c:otherwise>
					</c:choose>					
				</c:forEach>
			</td>
			<td>				
				<c:forEach var="hospitalDoctor" items="${hospital.hospitalDoctors}">	
					<ol style="list-style-type: none;">
						<li>										
						<c:out value="${hospitalDoctor.doctor.systemUserDetail.title}"></c:out>
						<c:out value="${hospitalDoctor.doctor.systemUserDetail.firstName}"></c:out>
						<c:out value="${hospitalDoctor.doctor.systemUserDetail.middleName}"></c:out>
						<c:out value="${hospitalDoctor.doctor.systemUserDetail.lastName}"></c:out>
							<ul style="list-style-type: none;padding-top: 5px;margin-left: 2px;">
								<li>
									<c:forEach var="doctorSpecialization" items="${hospitalDoctor.doctor.doctorSpecializations}">
										<c:out value="${doctorSpecialization.specialization.name}"></c:out>
									</c:forEach>
								</li>
							</ul>
						</li>
					</ol>
			    </c:forEach>
								
			</td>
			<td>
				<c:out value="${hospital.address.addressValue}"></c:out>
			</td>
			<td>
				<c:forEach var="contactNumber" items="${hospital.contactNumbers}">
					<c:out value="${contactNumber.contactNumberValue}"></c:out>
				</c:forEach>								
			</td>
			<td>					
				<c:forEach var="emailAddress" items="${hospital.emailAddresses}">
                                    <span><c:out value="${emailAddress.emailAddressValue}"></c:out>
				</c:forEach>
			</td>
		</tr>			
		</c:forEach>
	</table>
    </div>
</sec:authorize>
