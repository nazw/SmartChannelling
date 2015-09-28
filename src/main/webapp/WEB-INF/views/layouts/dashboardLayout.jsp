<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/smartchanneling.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/vertical-tabs.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/admin_main_css.css"/>" />
        
        <!--[if IE]>
                <link href="css/smartchanneling-ie.css" rel="stylesheet" type="text/css" />
        <![endif]-->

        <script type="text/javascript" src="<c:url value="js/pngfix.js"/>"></script>  
    

        <script type="text/javascript">
            function checkIfNumeric(inputId){ 				
                $("#" + inputId).keydown(function(event) {                	
                    // Allow only backspace and delete
                    // Allow only backspace and delete
                    var valInInput=$("#" + inputId).val();                        	
                    if(isNaN(valInInput)){ 
                        $("#" + inputId).val('0');	                        
                    } 
                });
            }
        </script>

    </head>
    <body>

        <div class="mainContainer">
            <div class="header">

                <tiles:insertAttribute name="header" />	
            </div>

            <div class="mainBody">
                <div class="mainBodyTop"></div>
                <div class="mainBodyMid">

                    <div id="vtab">

                        <ul>
                            <tiles:insertAttribute name="menu" />	
                        </ul> 

                        <div style="display: block;height: auto;" id="content">
                            <tiles:insertAttribute name="body" />	
                        </div>

                    </div>


                    <!--end of main body middle contents--></div>
                <div class="mainBodyBop"></div>
            </div>


            <!--end of mainContainer--></div>
        <div class="footer"> <tiles:insertAttribute name="footer" /> </div>



    </body>
</html>
