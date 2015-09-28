<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>

<script language="JavaScript">

    function specializationform(){   
		
        var validateError='';
        var name = $("#name").val();
        var area = $("#area").val();
         
        if (area == "")
        {
            validateError="Specialization Area is empty";              
        } 
        if (name == "")
        {
            validateError="Specialization Name is empty";              
        }        

        
        if(validateError || ! /^\s*$/.test(validateError)){         
            alert(validateError);            
            return false;
        }
               
        return true;
    }
    
</script> 



<div style="display: block;"><h1>Add Specialization</h1>
    <c:if test="${errorMessage != null}" ><h2><span style="padding-left: 100px;"><c:out value="${errorMessage}"></c:out></span></h2></c:if>            
    <form:form id="form2" name="form2" method="post" action="addSpecialization.htm" modelAttribute="specialization" onsubmit="return specializationform()" >

        <div class="row">
            <div class="col1">Specialization Name</div>
            <div class="col2"><form:input path="name" id="name" cssClass="textField" />     <span class="mandotary">*</span></div>
            <div class="clear"></div>
        </div>                                                  

        <div class="row">
            <div class="col1">Specialization Area</div>
            <div class="col2"><form:input path="area" id="area" cssClass="textField" />     <span class="mandotary">*</span></div>
            <div class="clear"></div>
        </div>                                                  


        <div class="row">
            <div class="col1">Description</div>
            <div class="col2"><textarea name="description"  class="textArea" id="description" cols="30" rows="6"></textarea>        
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