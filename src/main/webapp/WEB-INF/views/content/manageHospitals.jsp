

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style type="text/css">
    #hospitalsHeader{
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;
        background-color: #90D3EE;
        padding-bottom:10px;height: 30px;width: 95%;margin-bottom: 8px;

    }
    #hospitalsHeader a:hover{
        text-decoration: none;
    }
</style>

<div class="row" style="display: block;"><h1>Manage Hospitals</h1> </div>  

<div class="row" id="hospitalsHeader">       
    <div class="col " style="width:70%;"><p style="font-weight: bold;font-size: 14px;padding-left: 20px;padding-top: 8px;color: #ffffff"></p></div>  
    <div class="col " style="width:30%;"><a style="font-size: 15px;color: #ffffff" href="getCreateHospital.htm"> <img src="images/add_ico.png" style="padding-top: 9px;padding-right: 10px;"/><span style="position: relative;top: -9px;">Add Hospital</span></a>
    </div>
</div> 
 
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="dataTable">
        <tr>
            <th width="70%" scope="col">Hospital Name</th>
            <th width="20%" scope="col">Registration No</th> 
            <th width="10%" scope="col">Status</th>     
        </tr>
   <c:forEach var="hospital" items="${hospitals}">
            <tr>
                <td><c:out value="${hospital.hospitalName }"></c:out></td>
                <td><c:out value="${hospital.registrationNumber }"></c:out></td>
                <td><c:out value="${hospital.accountStatus }"></c:out></td>
         </tr>
        </c:forEach> 
    </table>