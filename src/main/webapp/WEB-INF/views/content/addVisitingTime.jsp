<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="style/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css"/>
 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
  
  <script type="text/javascript" >
	  $(document).ready(function() {
	    /*$("#startDate").datepicker({
	    	minDate:new Date(),
	    	onSelect: function( selectedDate ) {
				$( "#endDate" ).datepicker( "option", "minDate", selectedDate );
			}
	    });
		$("#endDate").datepicker({
			minDate:new Date(),
			onSelect: function( selectedDate ) {
				$( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
			}
		});*/
		
		$('#startTime').timepicker({
			timeFormat: 'hh:mm TT',
			stepHour: 1,
			hourGrid: 6,
			minuteGrid: 15,
			ampm: true
		});
		
		$('#endTime').timepicker({
			timeFormat: 'hh:mm TT',
			stepHour: 1,
			hourGrid: 6,
			minuteGrid: 15,
			ampm: true
		});
	  });
	  
	  var compulsoryFields=['hospitalDoctor', 'startTime', 'avgTimePerAppointment', 'charges'];
	  
	  function validateForm(){
		  var isEmpty=false;
		  $.each(compulsoryFields, function(index,id){
			  var val=$.trim($('#' + id).val());
			  if(val==null || val=="" || val==0 || val=='0'){
				  isEmpty=true;
				  $('#' + id).css('border','1px solid #FF0000');
			  }
		  });
		  if(!(isEmpty)){
			  //check if either the end date or end time is empty			  
			  var endTime=$.trim($('#endTime').val());
			  if(endTime==null || endTime==""){
				  var noOfPatients=$.trim($('#noOfPatients').val());
				  if(noOfPatients==null || noOfPatients=="" || noOfPatients==0 || noOfPatients=='0'){
					  alert('Please enter the number of patients');
					  return false;
				  }else{
					  return true;
				  }
				  
			  }else{
				  //having entered the end date, check if the average time is entered
				  var averageTime=$.trim($('#avgTimePerAppointment').val());
				  if(averageTime==null || averageTime==""){
					  alert('Please enter the average time per appointment');
					  return false;
				  }else{
					  return true;
				  }
			  }
		  }else{
			  alert('Please fill all the rquired fields!');
			  return false;
		  }
		  
	  }
	  
	  function clearError(id){
		  $('#' + id).css('border','1px solid #CCCCCC');
	  }
	  
  </script>
  
  
  <div style="display: block;"><h1>Add New Visitng Time</h1>
    <c:if test="${errorMessage != null}" ><h2><span style="padding-left: 100px;"><c:out value="${errorMessage}"></c:out></span></h2></c:if>

                             
                <form:form action="addNewVisiting.htm" method="post" modelAttribute="visiting" onsubmit="return validateForm()">
            
        <div class="row">
            <div class="col1">Doctor Name</div>
            <div class="col2">
                    	<form:select path="hospitalDoctor" id="hospitalDoctor" onfocus="clearError('hospitalDoctor')" name="hospitalDoctor" class="textField">
                    		<c:forEach items="${hospitalDoctors}" var="hospitalDoctor">
                    			<form:option value="${hospitalDoctor.hospitalDoctorId}"><c:out value="${hospitalDoctor.fullName}"></c:out></form:option>
                    		</c:forEach>
                    	</form:select>   
                <span class="mandotary">*</span>
            </div>
            <div class="clear"></div>
        </div>
                
                    
        <div class="row">
            <div class="col1">Day of Week</div>
            <div class="col2">      
	                    	<form:select path="dayOfWeek" name="dayOfWeek" class="textField">
	                    		<form:option path="dayOfWeek" name="dayOfWeek" value="1">Sunday</form:option>
								<form:option path="dayOfWeek" name="dayOfWeek" value="2">Monday</form:option>
								<form:option path="dayOfWeek" name="dayOfWeek" value="3">Tuesday</form:option>
								<form:option path="dayOfWeek" name="dayOfWeek" value="4">Wednesday</form:option>
								<form:option path="dayOfWeek" name="dayOfWeek" value="5">Thursday</form:option>
								<form:option path="dayOfWeek" name="dayOfWeek" value="6">Friday</form:option>
								<form:option path="dayOfWeek" name="dayOfWeek" value="7">Saturday</form:option>	
	                    	</form:select>       	
						<span class="mandotary">*</span>
               </div>
            <div class="clear"></div>
        </div>           
                

<!--                 <div class="row" style="padding-bottom:20px;">        -->
<!--                     <div class="col " style="width:30%;"><p> Start Date</p> </div>   -->
<!--                     <div class="col " style="width:70%;"> -->
<%--                     	<form:input path="startDate" name="startDate" id="startDate" onfocus="clearError('startDate')" class="form_input_box_cel" style="width:120px;" /> --%>
<!-- 							<input id="startDate" name="visStartDate" class="form_input_box_cel" style="width:120px;" /> -->
<!--                     	<span class="mandotary">*</span> -->
<!--                     </div> -->
<!--                 </div>      -->
                
        <div class="row">
            <div class="col1">Start Time</div>
            <div class="col2"> 
<%--                     	<form:input path="startTime" name="startTime" id="startTime" class="form_input_box_cel" style="width:120px;"/> --%>
							<input id="startTime" name="visStartTime" onfocus="clearError('startTime')" class="textField" style="width:120px;" />
                    	<span class="mandotary">*</span>
               </div>
            <div class="clear"></div>
        </div>  
                
<!--                 <div class="row" style="padding-bottom:20px;">        -->
<!--                     <div class="col " style="width:30%;"><p> End Date</p> </div>   -->
<!--                     <div class="col " style="width:70%;"> -->
<%--                     	<form:input path="endDate" name="endDate" id="endDate" class="form_input_box_cel" style="width:120px;" /> --%>
<!-- 							<input id="endDate" name="visEndDate" class="form_input_box_cel" style="width:120px;" /> -->
<!--                     	<span class="mandotary">*</span> -->
<!--                     </div> -->
<!--                 </div>   -->
                
        <div class="row">
            <div class="col1">End Time</div>
            <div class="col2"> 
<%--                     	<form:input path="endTime" name="endTime" id="endTime" class="form_input_box_cel" style="width:120px;"/> --%>
							<input id="endTime" name="visEndTime" class="textField" style="width:120px;" />
               </div>
            <div class="clear"></div>
        </div> 
                
        <div class="row">
            <div class="col1"> Average time per appointment (in minutes)</p> </div>  
                  <div class="col2"> 
                    	<form:input path="avgTimePerAppointment" name="avgTimePerAppointment" id="avgTimePerAppointment" onclick="clearError('avgTimePerAppointment')" onfocus="checkIfNumeric('avgTimePerAppointment')" class="textField" />
                    	<span class="mandotary">*</span>
               </div>
            <div class="clear"></div>
        </div> 
                
                
        <div class="row">
            <div class="col1"> No of patients</div>  
              <div class="col2">
                    	<form:input path="noOfPatients" name="noOfPatients" id="noOfPatients" onfocus="checkIfNumeric('noOfPatients')" class="textField"/>
               </div>
            <div class="clear"></div>
        </div> 
               
        <div class="row">
            <div class="col1">Charges (Rs.)</div>  
              <div class="col2">
                    	<form:input path="charges" name="charges" id="charges" onclick="clearError('charges')" onfocus="checkIfNumeric('charges')" class="textField"/>
                    	<span class="mandotary">*</span>
               </div>
            <div class="clear"></div>
        </div> 
    
                
                    
                        <div class="row" style="margin-top: 20px;">
                <div class="col1">

                </div>
                <div class="col2"><input type="submit" value="Submit" id="submit" class="button" /> &nbsp;&nbsp;&nbsp;<input type="reset" value="Clear" id="submit" class="button" /></div>
                <div class="clear"></div>
            </div>   
                
                                   <div class="clear"></div> 
            <div class="mandotary">* Mandotary Fields	<br />
                    			Please enter either End time OR number of patients</div>  

            
            </form:form>
            
              </div> 