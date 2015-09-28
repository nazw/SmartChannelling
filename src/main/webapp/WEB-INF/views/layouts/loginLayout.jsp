<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv=”X-UA-Compatible” content=”ie=edge,chrome=1″ />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico" />
        <link rel="icon" type="image/png" href="images/favicon.ico" />

        <link rel="stylesheet" type="text/css" href="<c:url value="style/style.css"/>" />         
        <link  rel="stylesheet" type="text/css" href="style/light.css" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/custom-theme/jquery-ui-1.8.13.custom.css"/>" />

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>

                <!--<script type="text/javascript" src="<c:url value="js/jquery.ui.core.js"/>"></script>-->
        <script type="text/javascript" src="<c:url value="js/jquery.ui.widget.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/jquery.ui.position.js"/>"></script>     
        <script type="text/javascript" src="<c:url value="js/json.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/jquery.autocomplete.js"/>"></script>
        <script type="text/javascript" src="js/login.js"></script>
        <script type="text/javascript" src="js/jquery.placeholder.js"></script>
    </head>
    <!--BACKGROUND-->
    <body class="tundra" style="background:url(images/Design.jpg) repeat; margin:0px;" >
        <div id="back">
            <div id="page_wrapper_login">
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

        </div>
    </body>
</html>
