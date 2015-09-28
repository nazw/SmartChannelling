
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<style type="text/css">
    #hospitalRoleHeader{
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;
        background-color: #90D3EE;
        padding-bottom:10px;height: 30px;width: 95%;margin-bottom: 8px;

    }
    #hospitalRoleHeader a:hover{
        text-decoration: none;
    }
</style>

   <div style="display: block;"><h1>Manage Hospital Roles</h1>
            

       <div class="row" id="hospitalRoleHeader">       
    <div class="col " style="width:70%;"><p style="font-weight: bold;font-size: 14px;padding-left: 20px;padding-top: 8px;color: #ffffff"></p></div>  
    <div class="col " style="width:30%;"><a style="font-size: 15px;color: #ffffff" href="createHospitalRole.htm"> <img src="images/add_ico.png" style="padding-top: 9px;padding-right: 10px;"/><span style="position: relative;top: -9px;">Add Hospital Role</span></a>
    </div>
</div> 
       
            

     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="dataTable">
  <tr>
    <th width="19%" scope="col">Hospital Roles</th>
    <th width="72%" scope="col">Description</th>
    <th width="9%" scope="col">Status</th>
    <th width="9%" scope="col">Edit</th>
    </tr>
     <c:forEach items="${hospitalRoles}" var="hospitalRoles">
  <tr>
    <td><c:out value="${hospitalRoles.name }"></c:out></td>
    <td><c:out value="${hospitalRoles.description }"></c:out></td>
    <td><c:out value="${hospitalRoles.activeStatus }"></c:out></td>
    <td><a href="getUpdateHospitalRole.htm?hid=${hospitalRoles.hospitalRoleId}"><img alt="Edit" src="images/edit-ico.png" style="width:15px;height:20px;"></a></td>
    </tr>
        </c:forEach>  
</table>

    </div>
 