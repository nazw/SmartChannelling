<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico" />
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/style.css"/>" />
    </head>

    <!--BACKGROUND-->
    <body class="tundra" style="background:url(images/Design.jpg) repeat; margin:0px;" >
        <div id="back">
            <div id="page_wrapper">
                <div id="header_wrapper">
                    <!--HEADER & SEARCH-->
                    <tiles:insertAttribute name="header" />                   
                </div>
                <div id="middle_wrapper">  

                    <!--SEARCH RESULT-->
                    <tiles:insertAttribute name="body" />

                </div>
                <div style="clear:both"></div>
            </div>
            <div id="footer">
                <!--FOOTER-->
                <tiles:insertAttribute name="footer" />
            </div>
        </div>
    </body>
</html>
