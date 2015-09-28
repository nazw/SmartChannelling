<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>

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
    
    function doctorForm(){   
		
        var validateError='';
        var  firstName = $("#firstName").val();
        var  lastName = $("#lastName").val();
        var  docRegistrationNumber = $("#docRegistrationNumber").val();  
        var  contactNumberValue1 = $("#contactNumber1").val();
        
                    
        var emailCheckString = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            
        if (!validate())
        {
            validateError="Please select at least one Specialization";                      
        }
           
        if (contactNumberValue1 == "")
        {
            validateError="Primary contact Number is empty";              
        }
      

        if (docRegistrationNumber == "")
        {
            validateError="Registration Number is empty";              
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
            alert(validateError);            
            return false;
        }
               
        return true;
    }
        
    function validate()
    {
        var chks = document.getElementsByName('doctorSpecializations');
        var hasChecked = false;
        for (var i = 0; i < chks.length; i++)
        {
            if (chks[i].checked)
            {
                hasChecked = true;
                break;
            }
        }
        if (hasChecked == false)
        {

            return false;
        }
        return true;
    }
    
    
</script>


<div class="row" style="display: block;"><h1>Add Doctor</h1>
    <c:if test="${errorMessage != null}" ><h2><span style="padding-left: 100px;"><c:out value="${errorMessage}"></c:out></span></h2></c:if>

    <div style="height:15px;min-height:15px;line-height:15px;" class="template_row">&nbsp;</div>


    <form:form method="post" action="saveDoctor.htm" modelAttribute="doctor" onsubmit="return doctorForm()" >

        <div class="row">
            <div class="col1">Title</div>
            <div class="col2">
                <form:select path="systemUserDetail.title" name="title" id="title" class="textField" style="width:80px;">
                    <form:option value="Mr">Mr</form:option>
                    <form:option value="Ms">Ms</form:option>
                    <form:option value="Miss">Miss</form:option>
                </form:select>            	
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
            <div class="col1">Registration Number</div>
            <div class="col2">
                <form:input path="docRegistrationNumber" name="docRegistrationNumber" id="docRegistrationNumber" class="textField" />
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




        <div class="row">
            <div class="col1">Primary Email Address</div>
            <div class="col2">
                <form:input path="systemUserDetail.emailAddresses[0].emailAddressValue" name="emailAddress1" id="emailAddress1" class="textField" />
            </div>
            <div class="clear"></div>
        </div>

        <div class="row" id="showSecondaryEmailArea">
            <div class="col1"></div>
            <div class="col2">     
                <a id="showSecondaryEmail" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">
            	Add Secondary Email Address</a></div> 
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
            <div class="col1">Primary Contact Number</div>
            <div class="col2">
                <form:input path="systemUserDetail.contactNumbers[1].contactNumberValue" name="contactNumber1" id="contactNumber1" class="textField" />
                <span class="mandotary">*</span>
            </div>
            <div class="clear"></div>
        </div> 

                <div class="row" id="showSecondaryContactArea">
            <div class="col1"></div>
            <div class="col2">
                <a id="showSecondaryContact" style="color:#B46F07;font-size:10px;cursor: hand;" href="javascript:void(0)">
            		Add Secondary Contact No
                </a></div>
            <div class="clear"></div>
        </div> 

        <div class="row" id="secondaryContact">
            <div class="col1">Secondary Contact No</div>
            <div class="col2"> 
                <form:input path="systemUserDetail.contactNumbers[2].contactNumberValue" name="contactNumber2" id="contactNumber2" class="textField" /></div>
            <div class="clear"></div>
        </div>


        <div class="row">
            <div class="col1">Designation</div>
            <div class="col2">
                <c:forEach items="${hospitalRoles}" var="hospitalRole" varStatus="count">
                    <p style="margin-bottom: 5px;">   <input type="checkbox" value="${hospitalRole.hospitalRoleId}" id="doctorHospitalRoles" name="doctorHospitalRoles"/>
                    <c:out value="${hospitalRole.name}"></c:out> </p>
                </c:forEach>  
            </div>
            <div class="clear"></div>
        </div> 

        <div class="row">
            <div class="col1">Description</div>
            <div class="col2">
                <form:textarea path="description" name="description" class="textArea" id="description" cols="30" rows="6"/>            		
            </div>
            <div class="clear"></div>
        </div>

        <div class="row">
            <div class="col1">Specialization <span class="mandotary">*</span></div>
            <div class="col2">
                <c:forEach items="${specializations}" var="specialization" varStatus="count">
                    <p style="margin-bottom: 5px;">   <input type="checkbox" value="${specialization.specializationId}" id="doctorSpecializations" name="doctorSpecializations"/>
                     <c:out value="${specialization.name}"></c:out></p> 
                </c:forEach>   
               
            </div>
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

    </form:form>

</div> 