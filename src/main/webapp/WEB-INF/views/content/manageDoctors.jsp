
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style type="text/css">
    #doctorHeader{
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;
        background-color: #90D3EE;
        padding-bottom:10px;height: 30px;width: 95%;margin-bottom: 8px;

    }
    #doctorHeader a:hover{
        text-decoration: none;
    }
</style>

<div class="row" style="display: block;"><h1>Manage Doctors</h1> </div>          


<div class="row" id="doctorHeader">       
    <div class="col " style="width:70%;"><p style="font-weight: bold;font-size: 14px;padding-left: 20px;padding-top: 8px;color: #ffffff"></p></div>  
    <div class="col " style="width:30%;"><a style="font-size: 15px;color: #ffffff" href="createDoctor.htm"> <img src="images/add_ico.png" style="padding-top: 9px;padding-right: 10px;"/><span style="position: relative;top: -9px;">Add Doctor</span></a>
    </div>
</div> 

<div style="height:5px;min-height:5px;line-height:5px;" class="template_row">&nbsp;</div>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="dataTable">
    <tr>
        <th width="65%" scope="col">Doctor Name</th>
        <th width="15%" scope="col">Status</th>
        <th width="10%" scope="col">Update</th>
        <th width="10%" scope="col">Delete</th>
    </tr>
    <c:forEach items="${doctorsList}" var="doctorsList">
        <tr>
            <td><c:out value="${doctorsList.title }"></c:out> &nbsp;<c:out value="${doctorsList.first_name }"></c:out> &nbsp;&nbsp;<c:out value="${doctorsList.last_name }"></c:out></td>
            <td><c:out value="${doctorsList.account_status }"></c:out></td>
            <td><img src="images/edit-ico.png"/></td>
            <td> <img src="images/cancel-ico.png"/></td>
        </tr>
    </c:forEach> 
</table>



