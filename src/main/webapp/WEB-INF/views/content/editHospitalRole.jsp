
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>


<script language="JavaScript">

    function hospitalRoleform(){   
		
        var validateError='';
        var name = $("#name").val();
                 
        if (name == "")
        {
            validateError="Hospital Role is empty";              
        }        
       	
        if(validateError || ! /^\s*$/.test(validateError)){         
            alert(validateError);            
            return false;
        }
               
        return true;
    }
    
</script> 



<div style="display: block;"><h1>Add Hospital Roles</h1>
    <c:if test="${errorMessage != null}" ><h2><span style="padding-left: 100px;"><c:out value="${errorMessage}"></c:out></span></h2></c:if>
    <form:form id="form2" name="form2" method="post" action="updateHospitalRole.htm" modelAttribute="hospitalRole" onsubmit="return hospitalRoleform()" >
		<form:hidden path="hospitalRoleId"/>
        <div class="row">
            <div class="col1">Designation Name</div>
            <div class="col2">
                <form:input path="name" id="name" class="textField"/>   <span class="mandotary">*</span>
            </div>
            <div class="clear"></div>
        </div>                                                  


        <div class="row">
            <div class="col1">Description</div>
            <div class="col2"><textarea name="description"  class="textArea" id="description" cols="30" rows="6">${hospitalRole.description}</textarea>
             </div>
            <div class="clear"></div>
        </div>



        <div class="row">
            <div class="col1"></div>
            <div class="col2"><input type="submit" value="Submit" id="submit" class="button" /> <input type="reset" value="Clear" id="submit" class="button" /></div>
            <div class="clear"></div>
        </div>                                                           


        <div class="mandotary">* Mandotary Fields</div>  

    </form:form>
</div>
