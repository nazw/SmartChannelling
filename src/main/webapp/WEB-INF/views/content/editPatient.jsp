<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js" type="text/javascript"></script>

<script language="JavaScript">

    $(document).ready(function() {
        $('#secondaryEmail').hide();
        $('#secondaryContact').hide();        
        
        $("#showSecondaryEmail").click(function() {
            $('#secondaryEmail').show();
            $('#showSecondaryEmailArea').hide();
        });
				 
        $("#showSecondaryContact").click(function() {
            $('#secondaryContact').show();
            $('#showSecondaryContactArea').hide();
        });
	

                
    }); 
    
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
                    if (emailAddress2 != "")
            {
                if (emailCheckString.test(emailAddress2) == false){
            
                    validateError="Primary Email Address is invalid";              	   
                }
            }
        if (emailAddress1 != "")
        {
            if (emailCheckString.test(emailAddress1) == false){
            
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
    
</script>

<div style="display: block;"><h1>Update Patient Details</h1>
    <c:if test="${errorMessage != null}" ><h2><span style="padding-left: 100px;"><c:out value="${errorMessage}"></c:out></span></h2></c:if>

    <form:form method="post" action="updatePatient.htm" modelAttribute="systemUserDetail" onsubmit="return patientform()">

        <div class="row">
            <div class="col1">Title</div>
            <div class="col2">
                <form:select path="title" name="title" id="title" class="textField" style="width:80px;">
                    <form:option value="Mr">Mr</form:option>
                    <form:option value="Ms">Mrs</form:option>
                    <form:option value="Ms">Ms</form:option>
                </form:select>  
                <span class="mandotary">*</span> 
            </div>
            <div class="clear"></div>
        </div> 


        <div class="row">
            <div class="col1">First Name</div>
            <div class="col2">
                <form:input path="firstName" name="firstName" id="firstName" class="textField" />
                <span class="mandotary">*</span> 
            </div>
            <div class="clear"></div>
        </div> 

        <div class="row">
            <div class="col1">Middle Name</div>
            <div class="col2">
                <form:input path="middleName" name="middleName" id="middleName" class="textField" />
             
            </div>
            <div class="clear"></div>
        </div> 

        <div class="row">
            <div class="col1">Last Name</div>
            <div class="col2">
                <form:input path="lastName" name="lastName" id="lastName" class="textField" />
                <span class="mandotary">*</span> 
            </div>
            <div class="clear"></div>
        </div> 

        <div class="row">
            <div class="col1">Gender</div>
            <div class="col2">
                <form:select path="gender" name="gender" id="gender" class="textField" style="width:120px;">
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
                <form:input path="systemUser.address.zipCode" name="zipCode" id="zipCode" class="textField" />
            </div>
            <div class="clear"></div>
        </div> 


        <div class="row">
            <div class="col1">street Number</div>
            <div class="col2">
                <form:input path="systemUser.address.streetNumber" name="streetNumber" id="streetNumber" class="textField" />
            </div>
            <div class="clear"></div>
        </div> 

        <div class="row">
            <div class="col1">Street Name</div>
            <div class="col2">
                <form:input path="systemUser.address.streetName" name="streetName" id="streetName" class="textField" />
            </div>
            <div class="clear"></div>
        </div> 


        <div class="row">
            <div class="col1">City</div>
            <div class="col2">
                <form:input path="systemUser.address.city" name="city" id="city" class="textField" />
            </div>
            <div class="clear"></div>
        </div> 


        <div class="row">
            <div class="col1">State</div>
            <div class="col2">
                <form:input path="systemUser.address.state" name="state" id="state" class="textField" />
            </div>
            <div class="clear"></div>
        </div> 


        <div class="row">
            <div class="col1">Country</div>
            <div class="col2">
                <form:input path="systemUser.address.country" name="country" id="country" class="textField" />
            </div>
            <div class="clear"></div>
        </div> 



        <div class="row">
            <div class="col1">PostalCode</div>
            <div class="col2">
                <form:input path="systemUser.address.postalCode" name="postalCode" id="postalCode" class="textField" />
            </div>
            <div class="clear"></div>
        </div> 




        <div class="row">
            <div class="col1">Primary Email Address</div>
                <div class="col2">
                    <form:input path="emailAddresses[0].emailAddressValue" name="emailAddress1" id="emailAddress1" class="textField" />
                </div>
                <div class="clear"></div>
            </div> 


            <div class="row" id="showSecondaryEmailArea">
                <div class="col1"></div>
                <div class="col2"> <a id="showSecondaryEmail" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">Add Secondary Email Address</a></div>
                <div class="clear"></div>
            </div>   


                <div class="row" id="secondaryEmail">
                <div class="col1">Secondary Email Address</div>
                <div class="col2">
                    <form:input path="emailAddresses[1].emailAddressValue" name="emailAddress2" id="emailAddress2" class="textField" />
                </div>
                <div class="clear"></div>
            </div> 


            <div class="row" >
                <div class="col1">Primary Contact No</div>
                <div class="col2">
                    <form:input path="contactNumbers[0].contactNumberValue" name="contactNumber1" id="contactNumber1" class="textField" />
                    <span class="mandotary">*</span>
                </div>
                <div class="clear"></div>
            </div> 


            <div class="row" id="showSecondaryContactArea">
                <div class="col1"></div>
                <div class="col2"> <a id="showSecondaryContact" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">Add Secondary Contact No</a></div>
                <div class="clear"></div>
            </div>   


            <div class="row" id="secondaryContact">
                <div class="col1">Secondary Contact No</div>
                <div class="col2"> <form:input path="contactNumbers[1].contactNumberValue" name="contactNumber2" id="contactNumber2" class="textField" /></div>
                <div class="clear"></div>
            </div>    


            <div class="row">
                <div class="col1">
                    <form:input path="systemUserDetailId" name="systemUserDetailId" id="systemUserDetailId" type="hidden"/>
                    <form:input path="emailAddresses[0].emailAddressId" name="emailAddressId1" id="emailAddressId1" type="hidden"/>
                    <form:input path="emailAddresses[1].emailAddressId" name="emailAddressId2" id="emailAddressId2" type="hidden"/>
                    <form:input path="contactNumbers[0].contactNumberId" name="contactNumberId1" id="contactNumberId1" type="hidden"/>
                    <form:input path="contactNumbers[1].contactNumberId" name="contactNumberId2" id="contactNumberId2" type="hidden"/>
                </div>
                <div class="col2"><input type="submit" value="Submit" id="submit" class="button" /> &nbsp;&nbsp;&nbsp;<input type="reset" value="Clear" id="submit" class="button" /></div>
                <div class="clear"></div>
            </div>    


            <div class="clear"></div> 
            <div class="mandotary">* Mandotary Fields</div>  


        </form:form>
    </div>