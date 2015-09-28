<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="style/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css"/>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>

<style type="text/css">

    .slot_white{
        width: 90px;
        height: 60px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #ffffff;
        float: left;
    }
    .slot_grey{
        width: 90px;
        height: 60px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CFCFCF;
        float: left;
    }
    .slot_green{
        width: 90px;
        height: 60px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #58A222;
        float: left;
    }
    .slot_red{
        width: 90px;
        height: 60px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CC6600;
        float: left;
    }

    .slot_grey_dummy{
        width: 40px;
        height: 20px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CFCFCF;
    }
    .slot_green_dummy{
        width: 40px;
        height: 20px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #58A222;
    }
    .slot_red_dummy{
        width: 40px;
        height: 20px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CC6600;
    }

    .time_slot{
        padding-top: 23px;padding-left: 18px;
    }

    #timeSlotAllocateArea{
        padding: 0px;
        margin: 0px;
        height: auto;

    }

    .mng_rows {
        background-color: #FAF3BB;
        height: auto;

        padding: 0px;
        width: 100%;
    }
    .right_header {
        padding:0;
        margin-bottom:20px;
        height:40px;
        width: 90%;
        background-color:#766F37;
        box-shadow: 5px 5px 3px #888888;
        color: #ffffff;
    }
    #allVisitingsHeader {

        background-color: #0CA4D2;

        color: #FFFFFF;
        height: 20px;
    
        width: 100%;
     
    }
    #allVisitingsHeader p{
        font-weight: bold;font-size: 13px;
        padding-top: 5px;
    }

    #timeSlotBookingHeader{
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;
        background-color: #A09B9B;
        padding-bottom:10px;height: 30px;width: 82%;margin-bottom: 8px;

    }
    #timeSlotBookingBody{
        background-color: #ffffff;
        box-shadow: 5px 5px 3px #888888;
        height: auto;
        width: 830px;
        padding: 10px 10px 10px 10px;
    }

    .search_header {
        background-color: #0CA4D2;
        margin-bottom: 40px;
        position: relative;
        top: 20px;
        padding-top: 15px;
        height: 55px;
        width: 94%;
    }

    #visitingHeader{
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;
        background-color: #0CA4D2;
        padding-bottom:10px;height: 30px;width: 95%;margin-bottom: 8px;

    }
    #visitingHeader a:hover{
        text-decoration: none;
    }
</style>


<script type="text/javascript">
    $(document).ready(function() {

        $('#timeSlotAllocateArea').hide();	

                
    }); 
    

  
    function cancelVisitings(visitingId) {  

        var appendString = '';

        $.ajaxSetup({ cache: false });
        $.getJSON("cancelVisitings.htm", { visitingId : visitingId }, function(data) {                    
                                        
            if(data !=0 &&  data.length != []){                
          
                $.each(data, function (){ 

                    appendString =                       
                        ' <tr id="'+this.visitingId+'">'
                        +'<td>'+this.fullName+'</td>'
                        +'<td>'+this.startDate+' @ '+ this.startTime+'</td>'
                        +'<td>'+this.endDate+' @ '+ this.endTime+'</td>'               
                        +'<td><img src="images/edit-ico.png" onclick="loadTimeslots(\''+this.visitingId + '\')" style="cursor: pointer;"/></td>'
                        +'<td><img src="images/cancel-ico.png" onclick="cancelVisitings(\''+this.visitingId + '\')" style="cursor: pointer;"/></td>'
                        +'</tr>'
                    $("#visitingsBody").append(appendString); 
                   
                   
                }); 
            }
			
        });     
        $('#firstVisitingsBody').hide();
        $('#visitingsBody').show();            
        
    }
    
    function loadTimeslots(visitingId) {  

        $('#visitingHeader').hide();  
        $('#timeSlotAllocateArea').show();
        var appendString = '';
        var hours='';
        var minutes='';
        $.ajaxSetup({ cache: false });
        $.getJSON("getAllVisitingSlotsForVisiting.htm", { visitingId : visitingId }, function(data) {                    
                                        
            if(data !=0 &&  data.length != []){                
          
                $.each(data, function (){ 
                    var date = new Date(this.startTime);
                    if(date.getHours()==0){hours='00';}else{hours=date.getHours();}
                    if(date.getMinutes()==0){minutes='00';}else{minutes=date.getMinutes();}
                    appendString = '<div class="slot_white" onclick="allocateTimeSlot(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;" id="'+this.visitingSlotsId+'"><p class="time_slot">'+hours+' : '+minutes+'</p></div>'
                    $("#timeSlotBookingBody").append(appendString); 
                    addTimeSlot(this.visitingSlotsId,this.bookingStatus);
                   
                }); 
            }
			
        });
        
    }

    function allocateTimeSlot(timeSlotId) {  

        var a='';
        var slotValue=$('#'+timeSlotId).attr('class');

        if(slotValue.indexOf("slot_grey") >= 0){
            if(confirm('Are you sure You want to activate the time slot?')){
                
                $.ajaxSetup({ cache: false });
                $.getJSON("activateVisitingSlot.htm", { visitingSlotsId : timeSlotId }, function(data) {  
                   
                    if(data !=0 &&  data.length != []){           
                        $('#'+timeSlotId).removeClass('slot_grey'); 
                        $('#'+timeSlotId).addClass('slot_green'); 
                    } 	
                });                     
            }
        }else if(slotValue.indexOf("slot_green") >= 0){
            if(confirm('Are you sure You want to cancel the time slot?')){                
                $.ajaxSetup({ cache: false });
                $.getJSON("cancelVisitingSlot.htm", { visitingSlotsId : timeSlotId }, function(dataValue) {  
                    if(dataValue !=0 &&  dataValue.length != []){ 
                        $('#'+timeSlotId).removeClass('slot_green'); 
                        $('#'+timeSlotId).addClass('slot_grey');   
                    }                  	
                });                     
            }
        }else if(slotValue.indexOf("slot_red") >= 0){
            alert('Please cancel the appontment before cancel the time slot!');
        }
        
    }
    
    
    function addTimeSlot(visitingSlotsId,visitingStatus) {  

        if(visitingStatus=='CANCELLED'){$('#'+visitingSlotsId).addClass('slot_grey'); }
        if(visitingStatus=='VACANT'){$('#'+visitingSlotsId).addClass('slot_green'); }
        if(visitingStatus=='BOOKED'){$('#'+visitingSlotsId).addClass('slot_red'); }                             


    }

</script>


<div class="template_row" id="main_page_area">


    <div class="row" style="display: block;"><h1>Manage Visiting</h1>         


        <div class="row" id="visitingHeader">       
            <div class="col " style="width:70%;"><p style="font-weight: bold;font-size: 14px;padding-left: 20px;padding-top: 8px;color: #ffffff"></p></div>  
            <div class="col " style="width:30%;"><a style="font-size: 15px;color: #ffffff" href="createVisitingTime.htm"> <img src="images/add_ico.png" style="padding-top: 9px;padding-right: 10px;"/><span style="position: relative;top: -9px;">Add Visiting</span></a>
            </div>
        </div> 



        <div class="search_header row" >
            <form action="filterAllVisitings.htm" method="post">
                <table width="100%">
                    <tr>
                        <td style="width:5%"><input type="checkbox" name="criteriaName" value="DOCTOR" /></td>
                        <td style="width:5%">Doctor:</td>
                        <td style="width:20%">
                            <select id="doctorId" name="doctorId" class="textField" style="margin-right: 30px;">								
                                <c:forEach items="${doctorList}" var="entry">
                                    <c:choose>
                                        <c:when test="${doctorId==entry.key}"><option selected="selected" value="${entry.key}"><c:out value="${entry.value}"></c:out></c:when>
                                        <c:otherwise><option value="${entry.key}"><c:out value="${entry.value}"></c:out></c:otherwise>
                                        </c:choose>									
                                    </c:forEach>
                            </select>
                        </td>

                        <td style="width:5%"><input type="checkbox" name="criteriaName" value="MONTH" /></td>
                        <td style="width:5%">Month:</td>
                        <td style="width:20%">
                            <select id="month" name="month" class="textField" style="width: 100px;">
                                <c:forEach items="${dateList}" var="entry">	
                                    <c:choose>
                                        <c:when test="${date==entry}"><option value="${entry}" selected="selected"><fmt:formatDate pattern="MMMM" value="${entry}" /></c:when>
                                        <c:otherwise><option value="${entry}"><fmt:formatDate pattern="MMMM" value="${entry}" /></c:otherwise>
                                        </c:choose>				
                                    </c:forEach>
                            </select>
                        </td>
               
                        <td style="width:40%"><input type="submit" value="Apply Filter" class="button"></td>
                    </tr>
                </table>			
            </form>
        </div>




        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="dataTable" >
            <tr>
                <th width="30%" scope="col">Doctor Name</th>
                <th width="25%" scope="col">Starting At</th>
                <th width="25%" scope="col">Finishing At</th>
                <th width="10%" scope="col">Manage</th>
                <th width="10%" scope="col">Cancel</th>
            </tr>
            <span id="firstVisitingsBody">
                <c:forEach items="${visitings}" var="visiting">
                    <tr>
                        <td><c:out value="${visiting.fullName}"></c:out></td>
                            <td>
                            <fmt:formatDate pattern="yyyy-MMM-dd" value="${visiting.startDate}" /> @
                            <fmt:formatDate pattern="hh:mm aaa" value="${visiting.startTime}" />
                        </td>
                        <td>
                            <fmt:formatDate pattern="yyyy-MMM-dd" value="${visiting.endDate}" /> @
                            <fmt:formatDate pattern="hh:mm aaa" value="${visiting.endTime}" />
                        </td>
                        <td><img src="images/edit-ico.png" onclick="loadTimeslots('${visiting.visitingId }')" style="cursor: pointer;"/></td>
                        <td><img src="images/cancel-ico.png" onclick="cancelVisitings('${visiting.visitingId }')" style="cursor: pointer;"/></td>               		
                    </tr>
                </c:forEach>  
            </span>
      

    <div id="visitingsBody"></div>
      </table>
</div> 







<div id="timeSlotAllocateArea" class="template_row">


    <div class="row" id="timeSlotBookingHeader">       
        <p style="font-weight: bold;font-size: 13px;padding-top: 8px;padding-left: 20px;color: #ffffff;">Manage Time Slots</p>
    </div>

    <div style="height:35px;min-height:35px;line-height:35px;" class="template_row">&nbsp;</div>

    <div class="row" id="timeSlotBookingBody" ></div>


    <div style="height:35px;min-height:35px;line-height:35px;" class="template_row">&nbsp;</div>

    <div class="row" style="width: 45%;background-color: #ffffff;height: 50px;box-shadow: 5px 5px 3px #888888;">
        <div class="col " style="width: 25%;padding-left: 35px;padding-top: 14px;" ><span class="col slot_grey_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Cancel</p></div>
        <div class="col " style="width: 25%;padding-top: 14px;"><span class="col slot_green_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Available</p></div>
        <div class="col " style="width: 25%;padding-top: 14px;padding-left: 14px;"><span class="col slot_red_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Reserved</p></div>

    </div>
</div>





</div>


