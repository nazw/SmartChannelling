<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<style type="text/css">
    #specializationHeader{
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;
        background-color: #90D3EE;
        padding-bottom:10px;height: 30px;width: 95%;margin-bottom: 8px;

    }
    #specializationHeader a:hover{
        text-decoration: none;
    }
</style>

<div style="display: block;"><h1>Manage Specialization</h1>
    
           <div class="row" id="specializationHeader">       
    <div class="col " style="width:70%;"><p style="font-weight: bold;font-size: 14px;padding-left: 20px;padding-top: 8px;color: #ffffff"></p></div>  
    <div class="col " style="width:30%;"><a style="font-size: 15px;color: #ffffff" href="createSpecialization.htm"> <img src="images/add_ico.png" style="padding-top: 9px;padding-right: 10px;"/><span style="position: relative;top: -9px;">Add Specialization</span></a>
    </div>
</div> 
    
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="dataTable">
        <tr>
            <th width="19%" scope="col">Specialization Name</th>
            <th width="21%" scope="col">Specialization Area</th>
            <th width="53%" scope="col">Specialization Description</th>
            <th width="7%" scope="col">Status</th>
            <th width="7%" scope="col">Edit</th>
        </tr>
        <c:forEach items="${specializationList}" var="specialization">
            <tr>
                <td><c:out value="${specialization.name }"></c:out></td>
                <td><c:out value="${specialization.area }"></c:out></td>
                <td><c:out value="${specialization.description }"></c:out></td>
                <td><c:out value="${specialization.activeStatus }"></c:out></td>
                <td><a href="getUpdateSpecialization.htm?specId=${specialization.specializationId}"><img alt="Edit" src="images/edit-ico.png" style="width:15px;height:20px;"></a></td>
                </tr>
        </c:forEach> 
    </table>
</div>