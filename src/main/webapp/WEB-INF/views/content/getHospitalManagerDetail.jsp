<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>

      <script language="JavaScript">

            $(document).ready(function() {
				 $('#secondaryEmail').hide();
				 $('#secondaryContact').hide();
				  $('#adminSecondaryEmail').hide();
				 $('#adminSecondaryContact').hide();
				 
				 $("#showSecondaryEmail").click(function() {
  					$('#secondaryEmail').show();
					$('#showSecondaryEmail').hide();
				});
				 
				$("#showSecondaryContact").click(function() {
  					$('#secondaryContact').show();
					$('#showSecondaryContact').hide();
				});
					
				$("#showAdminSecondaryEmail").click(function() {
  					$('#adminSecondaryEmail').show();
					$('#showAdminSecondaryEmail').hide();
				});
				 
				$("#showAdminSecondaryContact").click(function() {
  					$('#adminSecondaryContact').show();
					$('#showAdminSecondaryContact').hide();
				});
				
				 
           });  
        </script>
              
    <div style="display: block;"><h1>Add Hospital</h1>
            <h2>STEP 2 &nbsp;&nbsp;&nbsp;<span style="padding-left: 100px;">You have successfully created a new hospital account!</span></h2>
     
                     

                
            <form:form action="updateHospitalManager.htm" method="post" modelAttribute="systemUserDetail">
            
                <div class="row" style="padding-bottom:10px;padding-top:20px;">       
                    <div class="col " style="width:30%;"><p> User Name  </p> </div>  
                    <div class="col " style="width:70%;"> 
                    	<div class="textField" style="background-color: #0CA4D2;text-align: center;padding-top: 4px;padding-bottom: 3px;height: 15px;color: #ffffff;"><c:out value="${userName}"></c:out> </div>                    	
                    </div>
                </div>  
            
                <div class="row" style="padding-bottom:20px;padding-top:10px;">       
                    <div class="col " style="width:30%;"><p>Password  </p> </div>  
                    <div class="col " style="width:70%;">
                    	<div style="background-color: #0CA4D2;text-align: center;padding-top: 4px;padding-bottom: 3px;height: 15px;color: #ffffff;" class="textField"><c:out value="${password}"></c:out> </div>                     	 
                   </div>
                </div> 
                

                <div class="row" style="padding-bottom:10px;">       
                    <div class="col " style="width:30%;"><p>Primary Email Address</p> </div>  
                    <div class="col " style="width:70%;">
                    	<form:hidden path="systemUserDetailId" name="systemUserDetailId" id="systemUserDetailId" class="form_input_box_cel" />
                    	<form:input path="emailAddresses[0].emailAddressValue" name="emailAddress1" id="emailAddress1" class="textField" />
                   </div>
                </div>  
                
               <div class="row" style="padding-bottom:10px;" > <div class="col " style="width:30%;">&nbsp;</div>  
                <div class="col " style="width:70%;"><a id="showAdminSecondaryEmail" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">Add Secondary Email Address</a> </div>    </div>

                
                <div class="row" style="padding-bottom:10px;" id="adminSecondaryEmail">       
                    <div class="col " style="width:30%;"><p>Secondary Email Address</p> </div>  
                    <div class="col " style="width:70%;">
                    	<form:input path="emailAddresses[1].emailAddressValue" name="emailAddress2" id="emailAddress2" class="textField" />
                    </div>
                </div>  
                
               <div class="row" style="padding-bottom:10px;">       
                    <div class="col " style="width:30%;"><p>Primary Contact No</p> </div>  
                    <div class="col " style="width:70%;">
                    	<form:input path="contactNumbers[0].contactNumberValue" name="contactNumber1" id="contactNumber1" class="textField" />
                   
                   	</div>
                </div> 
                
                <div class="row" style="padding-bottom:10px;" > <div class="col " style="width:30%;">&nbsp;</div>  
                <div class="col " style="width:70%;"><a id="showAdminSecondaryContact" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">Add Secondary Contact No</a> </div>    </div>
                
               <div class="row" style="padding-bottom:10px;" id="adminSecondaryContact">       
                    <div class="col " style="width:30%;"><p>Secondary Contact No</p> </div>  
                    <div class="col " style="width:70%;">
                    	<form:input path="contactNumbers[1].contactNumberValue" name="contactNumber1" id="contactNumber1" class="textField" />
                    </div>
                </div> 

                
                  <div class="row" style="margin-top: 20px;">
            <div class="col1"></div>
            <div class="col2"><input type="submit" value="Submit" id="submit" class="button" /> <input type="reset" value="Clear" id="submit" class="button" /></div>
            <div class="clear"></div>
        </div>                                                           

               
                
            </form:form>
            
              </div> 