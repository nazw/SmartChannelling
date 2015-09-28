<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link href="style/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css"/>
<link href="style/Tooltip.css" rel="stylesheet" type="text/css"/>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/Tooltip.js"></script>

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
        padding-top: 14px;padding-left: 18px;color: #ffffff;
    }

    #timeSlotAllocateArea{
        padding: 0px;
        margin: 0px;
        height: auto;

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
    #doctorNameHeader{
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;
        background-color: #90D3EE;
        padding-bottom:10px;height: 30px;width: 95%;margin-bottom: 8px;

    }
    #search_pane{
        padding-bottom:10px;background-color: #0CA4D2;width: 70%;height: 160px;   
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;

    }
    #search_pane_advance{
        padding-bottom:10px;background-color: #0CA4D2;width: 80%;height: 285px;   
        -moz-user-select: none;
        border-bottom-left-radius: 22px;
        border-bottom-right-radius: 22px;
        border-top-left-radius: 22px;
        border-top-right-radius: 22px;

    }


    #doctorAppointmentHeader{
       -moz-user-select: none;
    background-color: #90D3EE;
    border-radius: 22px 22px 22px 22px;
    height: 30px;
    margin-bottom: 8px;
    padding-bottom: 10px;
    width: 95%;
    }

    #timeSlotBookingHeader{

    }

    #timeSlotBookingBody{
        background-color: #E3F5FC;
        box-shadow: 5px 5px 3px #C5D5DB;
        height: auto;
        width: 552px;
        padding: 10px 10px 10px 10px;
    }


    #patientpopupinfo{
        height:30%;width:98%;
        border: none;
        color: #ffffff;
        font-size: 17px;
        background-color: #B3D8E5;
        padding-left: 16px;
        font-style: italic;
        background: transparent;
    }
    #patientpopupinfo:hover{
        color: #333333;
        text-decoration: none;
        font-style: normal;
    }

    .search_header {
        background-color: #0CA4D2;
        margin-bottom: 20px;
        
       padding-top: 15px;
        height: 55px;
        width: 94%;
    }
    #showAdvanceSearch:hover{
        text-decoration: none;
    }
    #mcTooltip {float:left;position:relative; border-style:solid;top: 200px;left: 420px;}
#mcttCloseButton{margin-top: 205px;margin-right: -414px;}
    #mcttCo em, #mcttCo b {display: none;}
</style>

<script language="JavaScript">

    $(document).ready(function() {
        $('#secondaryEmail').hide();
        $('#secondaryContact').hide();      
        $('#availableDoctorsForAppointment').hide();
        $('#doctorAppointment').hide();
        $('#timeSlotAllocateArea').hide();
        $('#patientAppointment').hide();
        $('#findDoctorsForAppointmentAdvance').hide();
         

        $("#showSecondaryEmail").click(function() {
            $('#secondaryEmail').show();
            $('#showSecondaryEmailArea').hide();
        });
				 
        $("#showSecondaryContact").click(function() {
            $('#secondaryContact').show();
            $('#showSecondaryContactArea').hide();
        });
	
        $("#showAdvanceSearch").click(function() {
            $('#findDoctorsForAppointmentAdvance').show();
                 $('#availableDoctorsForAppointment').hide();
            $('#search_pane').hide();
        });
        
        $("#startDate").datepicker({
            minDate:new Date(),
            onSelect: function( selectedDate ) {
                $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
            }
        });

                
    });  
    
    
    function getAllDoctors(specializationId) {  
        var i=0;
        var appendString = '';
        $("#doctorNameBody").html('');       
        $.ajaxSetup({ cache: false });
        $.getJSON("getAllDoctorsforAppointment.htm", { specializationId : specializationId }, function(data) {                    
                                        
            if(data !=0 &&  data.length != []){                
                $('#doctorNameHeader').show();
                $.each(data, function (){                    
                    appendString = '<div class="row" style="height:13px;"><div class="col " style="width:65%;padding-left: 20px;font-size: 14px;"><p>'+this.title+' '+this.firstName+' '+this.lastName+'</p></div> <div class="col " style="width:10%;padding-left: 30px;"><img src="images/doctor-ico.png"  onclick="loadDoctorDetails(\'' + this.doctorId + '\')" style="cursor: pointer;" /></div></div></div>'
                    $("#doctorNameBody").append(appendString);                    
                }); 
            }else{
                appendString = '<div class="row" ><div class="col " style="width:60%;padding-top: 5px;padding-left: 20px;font-size: 14px;padding-bottom: 5px;"><p>No Data Available</p></div></div>'
                $("#doctorNameBody").append(appendString); 
            }			
        });
       
        $('#availableDoctorsForAppointment').show();
    }
    
    function loadDoctorDetails(doctorId) {  
        $('#findDoctorsForAppointment').hide();
        $('#availableDoctorsForAppointment').hide();  
        $('#doctorAppointment').show();
        
        $('#doctorIdHidden').val('');
        $('#doctorIdHidden').val(doctorId);
        
        var visitingStatus='';
        var appendString = '';
         
        $.ajaxSetup({ cache: false });
        $.getJSON("getDoctorforAppointment.htm", { doctorId : doctorId }, function(data) {                    
                                       
            if(data.doctorList !=0 &&  data.doctorList.length != []){                
                $.each(data.doctorList, function (){
                    if(this.visitingStatus=='ACTIVE'){visitingStatus='Available';}else{visitingStatus='Not Available';}
                    appendString = '<div class="col " style="width:30%;"><p style="font-size: 13px;padding-top: 8px;padding-left: 20px;">'+this.startDate+'</p></div> '
                        +'<div class="col " style="width:20%;"><p style="font-size: 13px;padding-top: 8px;padding-left: 5px;">'+visitingStatus+'</p></div> '
                        +' <div class="col " style="width:25%;"><p style="font-size: 13px;padding-top: 8px;">'+ this.startTime+' - '+ this.endTime+'</p></div> '
                        +' <div class="col " style="width:25%;"><span style="font-size: 13px;padding-top: 8px;padding-left: 40px;"> <img src="images/app-ico.png"  onclick="loadTimeslots(\'' + this.visitingId + '\')" style="cursor: pointer;" /></span></div> '
                    $("#doctorAppointmentBody").append(appendString);                      
                     
                }); 

            }
            
            
            if(data.dateList !=0 &&  data.dateList.length != []){  
                
                appendString ='<div class="search_header" style="margin-bottom:5px;">'				
                    +'<table width="100%"><tr><td style="width: 32%"><p style="font-size:17px;color:#ffffff;padding-left:110px;">Month:</p></td><td style="width: 38%">'
                    +'<select id="monthFil" name="monthFil" class="textField">'			
                    +'</select></td><td style="width: 24%"><input type="button" value="Apply Filter" onclick="loadFilterAppointment()" class="button"></td></tr></table></div>'
                $("#filterAppointmentDate").append(appendString); 
      
                $.each(data.dateList[0], function (key,value){                                  
                    appendString =appendString+'<option value="'+value+'">'+key+'</option>';
                }); 
                                                          
                $("#monthFil").append(appendString);   
            }			
        });
        
    } 
    
        
    function loadTimeslots(visitingId) {  

        $('#doctorAppointment').hide();  
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
                    if(this.bookingStatus=='BOOKED'){
                        appendString = '<div class="slot_white"  id="'+this.visitingSlotsId+'"><div class="time_slot" onclick="allocateTimeSlot(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;height:37%;width:80%;">'+hours+' : '+minutes+'</div>'
                            +'<div id="moreArea'+this.visitingSlotsId+'"><div onclick="getPatientByVisitingSlotId(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;height:30%;width:100%;background-color: #0CA4D2;" ><a href="javascript:void(0)" id="patientpopupinfo" >More..</a></div></div></div>'
                    }else{
                        appendString = '<div class="slot_white"  id="'+this.visitingSlotsId+'"><div class="time_slot" onclick="allocateTimeSlot(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;height:80%;width:80%;">'+hours+' : '+minutes+'</div></div>'
                     
                    }
                    $("#timeSlotBookingBody").append(appendString); 
                    addTimeSlot(this.visitingSlotsId,this.bookingStatus);
                   
                }); 
            }
			
        });
        
    }
    
    
    function allocateTimeSlot(timeSlotId) {  

        var a='';
        var slotValue=$('#'+timeSlotId).attr('class');
        $('#visitingSlotsIdHidden').val('');

        if(slotValue.indexOf("slot_red") >= 0){
            if(confirm('Are you sure You want to cancel the reserved time?')){
                
                $.ajaxSetup({ cache: false });
                $.getJSON("cancelAppointment.htm", { visitingSlotsId : timeSlotId }, function(data) {  
                   
                    if(data !=0 &&  data.length != []){                          
                        $('#'+timeSlotId).removeClass('slot_red');
                        $('#'+timeSlotId).addClass('slot_green');
                        $('#moreArea'+timeSlotId+'').hide();
                        
                    }  
                    		
                });               
                     
            }           
    
        }else if(slotValue.indexOf("slot_green") >= 0){
            $('#'+timeSlotId).removeClass('slot_green'); 
            $('#'+timeSlotId).addClass('slot_red'); 
            $('#timeSlotAllocateArea').hide();
            $('#patientAppointment').show();
            $('#visitingSlotsIdHidden').val(timeSlotId);              
              
        }else if(slotValue.indexOf("slot_grey") >= 0){            
            $('#'+timeSlotId).addClass('slot_grey');   
        }

    }
    
    function addTimeSlot(visitingSlotsId,visitingStatus) {  

        if(visitingStatus=='CANCELLED'){$('#'+visitingSlotsId).addClass('slot_grey'); }
        if(visitingStatus=='VACANT'){$('#'+visitingSlotsId).addClass('slot_green'); }
        if(visitingStatus=='BOOKED'){$('#'+visitingSlotsId).addClass('slot_red'); }
         
    }
   
    
    function loadFilterAppointment(){
        var monthFil=$("#monthFil").val();
        var doctorId=$("#doctorIdHidden").val();
        $("#doctorAppointmentBody").html('');
            
        var visitingStatus='';
        var appendString = '';
         
        $.ajaxSetup({ cache: false });
        $.getJSON("getFilteredDoctorAppointment.htm", { doctorId : doctorId, monthFil : monthFil }, function(data) {                    
                                   
            if(data.doctorList !=0 &&  data.doctorList.length != []){                
                $.each(data.doctorList, function (){
                  
                    if(this.visitingStatus=='ACTIVE'){visitingStatus='Available';}else{visitingStatus='Not Available';}
                    appendString = '<div class="col " style="width:30%;"><p style="font-size: 13px;padding-top: 8px;padding-left: 20px;">'+this.startDate+'</p></div> '
                        +'<div class="col " style="width:20%;"><p style="font-size: 13px;padding-top: 8px;padding-left: 5px;">'+visitingStatus+'</p></div> '
                        +' <div class="col " style="width:25%;"><p style="font-size: 13px;padding-top: 8px;">'+ this.startTime+' - '+ this.endTime+'</p></div> '
                        +' <div class="col " style="width:25%;"><span style="font-size: 13px;padding-top: 8px;padding-left: 40px;"> <img src="images/app-ico.png"  onclick="loadTimeslots(\'' + this.visitingId + '\')" style="cursor: pointer;" /></span></div> '
                    $("#doctorAppointmentBody").append(appendString);                     
                     
                }); 
            }else{
                appendString = '<div class="row" ><div class="col " style="width:60%;padding-top: 5px;padding-left: 20px;font-size: 14px;padding-bottom: 5px;"><p>No Data Available</p></div></div>'
                $("#doctorAppointmentBody").append(appendString); 
            }           
        });        
    }


    function clearError(id){
        $('#' + id).css('border','1px solid #CCCCCC');
    }
   
   
    function advanceSearch(){
    
        if(!advanceSearchform()){
        
        }else{
            var  firstName = $("#txtDoctorFirstName").val();
            var  lastName = $("#txtDoctorLastName").val();
            var  specializationId = $("#docSpecializations").val();
            var  startDate = $("#startDate").val();
            $("#timeSlotBookingBody").html('');
            $("#availableDoctorsForAppointment").html('');
            
            var cDoctorId='';
            var timeslotId='';
            $("#doctorIdHdn").val('');
            $("#visitingSlotsIdHidden").val('');
        
            $.ajaxSetup({ cache: false });
            $.getJSON("findTimeSlotsForDoctor.htm", { firstName : firstName,lastName : lastName,specializationId : specializationId,startDate : startDate }, function(data) {                    
                                        
                if(data !=0 &&  data.length != []){                
          
                    $.each(data, function (){ 
                        var date = new Date(this.startTime);
                        if(date.getHours()==0){hours='00';}else{hours=date.getHours();}
                        if(date.getMinutes()==0){minutes='00';}else{minutes=date.getMinutes();}
                        if(this.bookingStatus=='BOOKED'){
                            appendString = '<div class="slot_white"  id="'+this.visitingSlotsId+'"><div class="time_slot" onclick="allocateTimeSlot(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;height:20%;width:80%;">'+hours+' : '+minutes+'</div>'
                                +'<div id="moreArea'+this.visitingSlotsId+'"><div onclick="getPatientByVisitingSlotId(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;height:30%;width:100%;background-color: #E7DFA0;" ><a href="javascript:void(0)" id="patientpopupinfo" >More..</a></div></div></div>'
                        }else{
                            appendString = '<div class="slot_white"  id="'+this.visitingSlotsId+'"><div class="time_slot" onclick="allocateTimeSlot(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;height:80%;width:80%;">'+hours+' : '+minutes+'</div></div>'
                     
                        }
                        $("#timeSlotBookingBody").append(appendString); 
                        addTimeSlot(this.visitingSlotsId,this.bookingStatus);
                    
       
                        $("#doctorIdHidden").val(this.doctorId);
                        $("#visitingSlotsIdHidden").val(this.visitingSlotsId);
                    }); 
                }else{
                
                    appendString='<p>Time slots are not available</p>';
                    $("#timeSlotBookingBody").append(appendString);
                    $('#slot_dummy').hide(); 
                
                }
			
            });
        
        
            $('#findDoctorsForAppointment').hide(); 
            $('#findDoctorsForAppointmentAdvance').hide(); 
            $('#timeSlotAllocateArea').show();
    
        }
    }
    
    function patientform(){   
		
        var validateError='';
        var  firstName = $("#firstName").val();
        var  lastName = $("#lastName").val();
        var  city = $("#city").val();
        var  contactNumber1 = $("#contactNumber1").val();
        var  emailAddress1 = $("#emailAddress1").val(); 
        var  emailAddress2 = $("#emailAddress2").val();      
        var emailCheckString = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
           

    
        if (contactNumber1 == "")
        {
            validateError="Primary contact Number is empty";              
        }
        
        if (emailAddress1 != "")
        {
            if (emailCheckString.test(emailAddress1) == false){
            
                validateError="Primary Email Address is invalid";              	   
            }
        }
        if (emailAddress2 != "")
        {
            if (emailCheckString.test(emailAddress2) == false){
            
                validateError="Primary Email Address is invalid";              	   
            }
        }
        

        if (lastName == "")
        {
            validateError="Enter Last Name";              
        }
        if (firstName == "")
        {
            validateError="Enter First Name";              
        }	
        if(validateError || ! /^\s*$/.test(validateError)){ 
        
            validateError =validateError.replace("," ," ");
            alert(validateError);
            
            return false;
        }
               
        return true;
    }
    

    function advanceSearchform(){   
		
        var validateError='';
        var  firstName = $("#txtDoctorFirstName").val();
        var  lastName = $("#txtDoctorLastName").val();
        var  docSpecializations = $("#docSpecializations").val();
        var  startDate = $("#startDate").val();

    
        if (startDate == "")
        {
            validateError="startDate is empty";              
        }
        if (docSpecializations == "")
        {
            validateError="Specializations is empty";              
        }
        if (lastName == "")
        {
            validateError="Last Name is empty";              
        }
        if (firstName == "")
        {
            validateError="First Name is empty";              
        }
   	
        if(validateError || ! /^\s*$/.test(validateError)){ 
        
            validateError =validateError.replace("," ," ");
            alert(validateError);
            
            return false;
        }
               
        return true;
    }
</script>


    <div id="findDoctorsForAppointment" style="display: block;">
        <h1>Search For Your Doctor</h1>
        <div style="height:15px;min-height:15px;line-height:15px;" class="row">&nbsp;</div>
        <div class="row" id="search_pane"> 
            <div class="row " ><a id="showAdvanceSearch" style="color:#ffffff;font-size:15px;cursor: hand;font-weight: bold;position: relative;top: 15px;left: 288px;" href="javascript:void(0)">Advance Search</a></div>
            <div class="row " > <div class="col " style="width:33%;position: relative;top: 45px; "><p style="font-size: 13px;font-weight: bold;padding-left: 20px;color: #ffffff;">Specialization</p></div>  
                <div class="col " style="width:50%;position: relative;top: 40px;">
                    <select name="doctorSpecializations" id="doctorSpecializations" onchange="getAllDoctors(this.value)" style="padding-top: 5px;padding-bottom: 5px;padding-right: 5px;width: 250px;">                   
                        <option >Select Specialization</option> 
                        <c:forEach items="${specializationList}" var="specialization" varStatus="count">                       
                            <option value="${specialization.specializationId}"><c:out value="${specialization.name}"></c:out></option> 
                        </c:forEach> 
                    </select> </div>
            </div>
        </div>  

    </div>


    <div id="findDoctorsForAppointmentAdvance" class="template_row">

        <div class="row" id="search_pane_advance">
            <div class="row" style="margin-top: 20px;">       
                <div class="col " style="width:40%; "><p style="font-size: 13px;font-weight: bold;padding-left: 20px;color: #ffffff;">Doctor's First Name</p></div>  
                <div class="col " style="width:50%;">
                    <input type="text" name="txtDoctorFirstName" id="txtDoctorFirstName" class="textField"/>
                </div>
            </div> 
            <div class="row" style="margin-top: 5px;">       
                <div class="col " style="width:40%; "><p style="font-size: 13px;font-weight: bold;padding-left: 20px;color: #ffffff;">Doctor's Last Name</p></div>  
                <div class="col " style="width:50%;">
                    <input type="text" name="txtDoctorLastName" id="txtDoctorLastName" class="textField"/>
                </div>
            </div>  

            <div class="row" style="margin-top: 5px;">       
                <div class="col " style="width:40%; "><p style="font-size: 13px;font-weight: bold;padding-left: 20px;color: #ffffff;">Specialization</p></div>  
                <div class="col " style="width:50%;">
                    <select name="docSpecializations" id="docSpecializations" class="textField">                   
                        <option value="">Select Specialization</option> 
                        <c:forEach items="${specializationList}" var="specialization" varStatus="count">                       
                            <option value="${specialization.specializationId}"><c:out value="${specialization.name}"></c:out></option> 
                        </c:forEach> 
                    </select>
                </div>
            </div>  
            <div class="row" style="margin-top: 5px;">       
                <div class="col " style="width:40%; "><p style="font-size: 13px;font-weight: bold;padding-left: 20px;color: #ffffff;">Appointment Date</p></div>  
                <div class="col " style="width:50%;">
                    <input name="startDate" id="startDate" onfocus="clearError('startDate')" class="textField" style="width: 80px;" />
                </div>
            </div> 
            <div class="row" style="margin-top: 10px;">                  
                <div class="col " style="width:100%;">

                    <img src="images/home_search_search_button.png"  onclick="advanceSearch()" style="cursor: pointer;position: relative;left: 333px;" />
                </div>
            </div> 


        </div> 
        <div style="height:35px;min-height:35px;line-height:35px;" class="template_row">&nbsp;</div>
    </div>


    <div id="availableDoctorsForAppointment" class="template_row">
        <div style="height:55px;min-height:55px;line-height:55px;" class="template_row">&nbsp;</div>
        <div class="row" id="doctorNameHeader">       
            <div class="col " style="width:60%;"><p style="font-weight: bold;font-size: 14px;padding-left: 20px;padding-top: 8px;color: #ffffff">Doctor Name</p></div>  
            <div class="col " style="width:30%;"><p style="font-weight: bold;font-size: 14px;padding-left: 20px;padding-top: 8px;color: #ffffff"> Click here to channel</p>
            </div>
        </div> 
        <div class="row" style="padding-bottom:10px;width: 95%" id="doctorNameBody"></div>   
    </div> 



    <div id="doctorAppointment" style="display: block;"><h1>Appointment Dates</h1>

        <div id="filterAppointmentDate" class="row"> 
        </div> 

        <div class="row" id="doctorAppointmentHeader">       
            <div class="col " style="width:30%;"><p style="font-weight: bold;font-size: 13px;padding-top: 8px;padding-left: 10px;">Appointment Date</p></div>  
            <div class="col " style="width:20%;"><p style="font-weight: bold;font-size: 13x;padding-top: 8px;"> Status</p></div> 
            <div class="col " style="width:25%;"><p style="font-weight: bold;font-size: 13px;padding-top: 8px;"> Available Time</p></div> 
            <div class="col " style="width:25%;"><p style="font-weight: bold;font-size: 13px;padding-top: 8px;"> Make an appointment</p></div> 
        </div>

        <div class="row" style="padding-bottom:10px;width: 90%" id="doctorAppointmentBody">   

        </div> 

    </div> 


    <div id="timeSlotAllocateArea" class="template_row">


        <div class="row" id="timeSlotBookingHeader">       
            <h1>Time Slots Booking</h1>  
        </div>

        <div style="height:5px;min-height:5px;line-height:5px;" class="template_row">&nbsp;</div>

        <div class="row" id="timeSlotBookingBody" ></div>


        <div style="height:35px;min-height:35px;line-height:35px;" class="template_row">&nbsp;</div>

        <div class="row" style="width: 84%;box-shadow: 5px 5px 3px #C5D5DB;background-color: #E3F5FC;height: 50px;" id="slot_dummy">
            <div class="col " style="width: 25%;padding-left: 35px;padding-top: 14px;" ><span class="col slot_grey_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Cancel</p></div>
            <div class="col " style="width: 25%;padding-top: 14px;"><span class="col slot_green_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Available</p></div>
            <div class="col " style="width: 25%;padding-top: 14px;padding-left: 14px;"><span class="col slot_red_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Reserved</p></div>

        </div>



    </div>




    <div style="display: block;" id="patientAppointment"><h1>Patient Details</h1>
        <c:if test="${errorMessage != null}" ><h2><span style="padding-left: 100px;"><c:out value="${errorMessage}"></c:out></span></h2></c:if>

  <div style="height:15px;min-height:15px;line-height:15px;" class="template_row">&nbsp;</div>
        <form method="post" action="savePatient.htm" modelAttribute="systemUserDetail" onsubmit="return patientform()">


            <div class="row">
                <div class="col1">Title</div>
                <div class="col2">
                    <form:select path="systemUserDetail.title" name="title" id="title" class="textField" style="width:80px;">
                        <form:option value="Mr">Mr</form:option>
                        <form:option value="Ms">Ms</form:option>
                    </form:select>   
                    <input type="hidden" name="doctorIdHidden" id="doctorIdHidden" />                        
                    <span class="mandotary">*</span> 
                </div>
                <div class="clear"></div>
            </div>  


            <div class="row">
                <div class="col1">First Name</div>
                <div class="col2">
                    <form:input path="systemUserDetail.firstName" name="firstName" id="firstName" class="textField" />            	
                    <span class="mandotary">*</span> 
                </div>
                <div class="clear"></div>
            </div> 

            <div class="row">
                <div class="col1">Middle Name</div>
                <div class="col2">
                    <form:input path="systemUserDetail.middleName" name="middleName" id="middleName" class="textField" /> 
                </div>
                <div class="clear"></div>
            </div> 

            <div class="row">
                <div class="col1">Last Name</div>
                <div class="col2">
                    <form:input path="systemUserDetail.lastName" name="lastName" id="lastName" class="textField" />
                    <span class="mandotary">*</span> 
                </div>
                <div class="clear"></div>
            </div> 

            <div class="row">
                <div class="col1">Gender</div>
                <div class="col2">
                    <form:select path="systemUserDetail.gender" name="gender" id="gender" class="textField" style="width:120px;">
                        <form:option value="Male">Male</form:option>
                        <form:option value="Female">Female</form:option>
                    </form:select>            	
                    <span class="mandotary">*</span> 
                </div>
                <div class="clear"></div>
            </div> 


            <div class="row">
                <div class="col1">zipCode</div>
                <div class="col2">                  
                    <form:input path="systemUserDetail.systemUser.address.zipCode" name="zipCode" id="zipCode" class="textField" />
                </div>
                <div class="clear"></div>
            </div> 

            <div class="row">
                <div class="col1">street Number</div>
                <div class="col2">
                    <form:input path="systemUserDetail.systemUser.address.streetNumber" name="streetNumber" id="streetNumber" class="textField" />
                </div>
                <div class="clear"></div>
            </div> 


            <div class="row">
                <div class="col1">Street Name</div>
                <div class="col2"> 
                    <form:input path="systemUserDetail.systemUser.address.streetName" name="streetName" id="streetName" class="textField" />
                </div>
                <div class="clear"></div>
            </div> 

            <div class="row">
                <div class="col1">City</div>
                <div class="col2">
                    <form:input path="systemUserDetail.systemUser.address.city" name="city" id="city" class="textField" />
                </div>
                <div class="clear"></div>
            </div>


            <div class="row">
                <div class="col1">State</div>
                <div class="col2">                     
                        <form:input path="systemUserDetail.systemUser.address.state" name="state" id="state" class="textField" />
                    </div>
                    <div class="clear"></div>
                </div> 


                <div class="row">
                    <div class="col1">Country</div>
                    <div class="col2">
                        <form:input path="systemUserDetail.systemUser.address.country" name="country" id="country" class="textField" />
                    </div>
                    <div class="clear"></div>
                </div> 

                <div class="row">
                    <div class="col1">PostalCode</div>
                    <div class="col2"> 
                        <form:input path="systemUserDetail.systemUser.address.postalCode" name="postalCode" id="postalCode" class="textField" />
                    </div>
                    <div class="clear"></div>
                </div> 


                    <div class="row" >
            <div class="col1">Primary Email Address</div>
                <div class="col2">
                        <form:input path="systemUserDetail.emailAddresses[0].emailAddressValue" name="emailAddress1" id="emailAddress1" class="textField" />
                </div>
                <div class="clear"></div>
            </div> 
  

                <div class="row" id="showSecondaryEmailArea">
                <div class="col1"></div>
                <div class="col2">         <a id="showSecondaryEmail" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">
                            Add Secondary Email Address</a> </div> 
                <div class="clear"></div>
            </div> 

                <div class="row" id="secondaryEmail">
                <div class="col1">Secondary Email Address</div>
                <div class="col2">
                        <form:input path="systemUserDetail.emailAddresses[1].emailAddressValue" name="emailAddress2" id="emailAddress2" class="textField" />
                </div>
                <div class="clear"></div>
            </div>  

            <div class="row" >
                <div class="col1">Primary Contact No</div>
                <div class="col2">
                        <form:input path="systemUserDetail.contactNumbers[1].contactNumberValue" name="contactNumber1" id="contactNumber1" class="textField" />
                    <span class="mandotary">*</span>
                </div>
                <div class="clear"></div>
            </div> 
                    

                    <div class="row" id="showSecondaryContactArea">
                <div class="col1"></div>
                     <div class="col2">    <a id="showSecondaryContact" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">
                             Add Secondary Contact No </a></div>
                <div class="clear"></div>
            </div>   

            <div class="row" id="secondaryContact">
                <div class="col1">Secondary Contact No</div>
                <div class="col2">        <form:input path="systemUserDetail.contactNumbers[2].contactNumberValue" name="contactNumber2" id="contactNumber2" class="textField" /> </div>
                <div class="clear"></div>
            </div>  

                <div class="row" style="padding-top: 20px;">
                <div class="col1">      
                    <input type="hidden" name="visitingSlotsIdHidden" id="visitingSlotsIdHidden" />
                      </div>
                <div class="col2"><input type="submit" value="Submit" id="submit" class="button" /> &nbsp;&nbsp;&nbsp; <input type="reset" value="Clear" id="submit" class="button" /></div>
                <div class="clear"></div>
            </div>

                       <div class="clear"></div> 
            <div class="mandotary">* Mandotary Fields</div>  


        </form>




</div>

<div style="height:25px;min-height:25px;line-height:25px;" class="template_row">&nbsp;
    <input type="hidden" name="visitingSlotsIdAppHidden" id="visitingSlotsIdAppHidden" />
    <input type="hidden" name="changeAppointmentHidden" id="changeAppointmentHidden" />

</div>          

<script type="text/javascript">
    
    function changeAppointmentDate(visitingSlotsId) { 
        $("#timeSlotBookingBody").html('');
        $("#doctorAppointmentBody").html('');  
        $('#timeSlotAllocateArea').hide();    
        $('#filterAppointmentDate').hide(); 
      
        var doctorId=$('#doctorIdHidden').val();
        $('#visitingSlotsIdAppHidden').val(visitingSlotsId);
        var visitingStatus='';
        var appendString = '';
          
        $.ajaxSetup({ cache: false });
        $.getJSON("getDoctorforAppointment.htm", { doctorId : doctorId }, function(data) {                    
                                       
            if(data.doctorList !=0 &&  data.doctorList.length != []){                
                $.each(data.doctorList, function (){
                    if(this.visitingStatus=='ACTIVE'){visitingStatus='Available';}else{visitingStatus='Not Available';}
                    appendString = '<div class="col " style="width:30%;"><p style="font-size: 13px;padding-top: 8px;padding-left: 20px;">'+this.startDate+'</p></div> '
                        +'<div class="col " style="width:20%;"><p style="font-size: 13px;padding-top: 8px;padding-left: 5px;">'+visitingStatus+'</p></div> '
                        +' <div class="col " style="width:25%;"><p style="font-size: 13px;padding-top: 8px;">'+ this.startTime+' - '+ this.endTime+'</p></div> '
                        +' <div class="col " style="width:25%;"><span style="font-size: 13px;padding-top: 8px;padding-left: 40px;"> <img src="images/app-ico.png"  onclick="changeAppointmentTimeslot(\'' + this.visitingId + '\')" style="cursor: pointer;" /></span></div> '
                    $("#doctorAppointmentBody").append(appendString);  
                }); 
            }
			
        });
        
        $('#doctorAppointment').show();
    } 
    
    function changeAppointmentTimeslot(visitingId) {  
        
        var type='1';
        $("#timeSlotBookingBody").html('');
        $('#doctorAppointment').hide();  
        $('#timeSlotAllocateArea').show();
        $('#changeAppointmentHidden').val(type);
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
          
                    appendString = '<div id="moreArea'+this.visitingSlotsId+'"><div class="slot_white"  id="'+this.visitingSlotsId+'"><div class="time_slot" onclick="allocateTimeSlotForChangeAppointment(\'' + this.visitingSlotsId + '\')" style="cursor: pointer;height:80%;width:80%;">'+hours+' : '+minutes+'</div></div></div>'
                
                    $("#timeSlotBookingBody").append(appendString); 
                    addTimeSlot(this.visitingSlotsId,this.bookingStatus);
                   
                }); 
            }
			
        });
        
    }
    
    function allocateTimeSlotForChangeAppointment(timeSlotId) {  

        var type='2';
        var changeAppointmentHidden=$('#changeAppointmentHidden').val();
        var a='';
        var slotValue=$('#'+timeSlotId).attr('class');
        var currentvVsitingSlotsId=$('#visitingSlotsIdAppHidden').val(); 
        if(slotValue.indexOf("slot_red") >= 0){
            $('#'+timeSlotId).addClass('slot_red'); 
    
        }else if(slotValue.indexOf("slot_green") >= 0){
 
            if(changeAppointmentHidden=='1'){
                $.ajaxSetup({ cache: false });
                $.getJSON("updateAppointment.htm", { newTimeSlotId : timeSlotId,currentvVsitingSlotsId : currentvVsitingSlotsId }, function(data) {                    
                                        
                    // if(data !=0 &&  data.length != []){  
                    $('#'+currentvVsitingSlotsId).removeClass('slot_red');
                    $('#'+currentvVsitingSlotsId).addClass('slot_green');
                    $('#'+timeSlotId).removeClass('slot_green');
                    $('#'+timeSlotId).addClass('slot_red'); 
                    // }			
                });
                $('#changeAppointmentHidden').val(type);
            }
            $('#visitingSlotsIdAppHidden').val('');              
              
        }else if(slotValue.indexOf("slot_grey") >= 0){            
            $('#'+timeSlotId).addClass('slot_grey');   
        }

    }
    
    
    function getPatientByVisitingSlotId(visitingSlotsId) {  

        var appendString = '';
        var title='';
        var name='';
        var a=visitingSlotsId;
        $.ajaxSetup({ cache: false });
        $.getJSON("getPatientByVisitingSlotId.htm", { visitingSlotsId : visitingSlotsId }, function(data) {     
                    
            // if(data !=0 &&  data.length != []){        
            $.each(data, function (){ 
                name=this.title+' '+ this.firstName+' '+ this.lastName;                        
            }); 
            appendString = '<div>'
                +'<div style="width: 100%; float: left;padding-top: 7px;"> <p style="font-weight: bold;font-size: 14px;padding-left:7px;">Patient Name : '+name+'</p></div>'
                +'<div style="width: 100%; float: left;padding-top: 14px;"> <a href="editPatient.htm?visitingSlotsId='+visitingSlotsId+'"><img src="images/sm_update_btn.png"></a></div>'
                +'<div style="width: 100%; float: left;padding-top: 7px;"><img src="images/sm_change_btn.png"  onclick="changeAppointmentDate(\'' + visitingSlotsId + '\')" style="cursor: pointer;" /> </div>'
                +'</div>'
            //  $("#aaa").append(appendString); 
            //  }
            var a='<div ><div style="width: 100%; float: left"> <p>Patient Name : '+name+'</p></div>';
                 
            this.overlay=true; 
            tooltip.pop(this, appendString); 
            document.getElementById('mcttCloseButton').style.display='inline'; 
            document.getElementById('mcOverlay').onclick = tooltip.hide;	
        });
             
    }
</script>


