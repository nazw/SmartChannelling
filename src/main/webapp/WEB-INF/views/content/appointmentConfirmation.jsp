<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>

<style type="text/css">
    .right_header {
        padding:0;
        margin-bottom:20px;
        height:40px;
        width: 90%;
        background-color:#766F37;
        box-shadow: 5px 5px 3px #888888;
        color: #ffffff;
    }


    #patientAppointmentBody{
        width: 90%;
        height: auto;
        padding-top: 30px;
        font-size: 13px;
        padding-bottom: 100px;

    }
    #patientAppointmentBody p{

        font-size: 13px;


    }
    #right{
        border: none;
    }
    .receipt_row{

        border-top: 1px solid #333333;
        border-left: 1px solid #333333;
        border-right: 1px solid #333333;
        height: 30px;

    }
    .receipt_row p{
        padding-top: 5px;
        padding-left: 5px;

    }
    .receipt_col{

        height: 100%;
        border-right: 1px solid #333333;


    }
    .row{
        margin: 0px;
        padding: 0px;
    }
</style>

<style>

    @media print
    {
        #header_area,.footer,#left,.right_header,#printer,a,.header,#vtab ul,#patientAppointment h1{display:none;}    
        #body{width: 100%; margin: 0; float: none;}
    }

</style>

<div id="patientAppointment" style="display: block;"><h1>Your Appointment Details</h1>



        <div class="row "><a href="JavaScript:window.print();" id="printer"><img src="images/printer.png"/></a></div>

        <div id="patientAppointmentBody" class="row">

            <div class="row receipt_row" style="height: 50px;">
                <div class="col " style="width:60%;"><p style="font-size: 14px;font-weight: bold;padding-top: 10px;padding-left: 10px;">PAYMENT RECEIPT</p></div>
                <div class="col " style="width:40%;"><p style="padding-left: 10px;">APPOINTMENT NO : <c:out value="${appointment.appointmentId}"></c:out></p><p style="padding-left: 10px;"><%java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");%>DATE : <%= df.format(new java.util.Date())%></p></div>
                </div>
                <div class="row receipt_row" style="height: 100px;">
                    <div class="col " style="width:20%;"><img src="images/<c:out value="${hospital.logo}"></c:out> " width="80" height="80" style="padding-left: 10px;padding-top: 10px;"/></div>
                <div class="col " style="width:80%;"><p style="font-size: 14px;font-weight: bold;"><c:out value="${hospital.hospitalName}"></c:out>  </p>
                    <p style="font-size: 13px;"><c:out value="${address.streetNumber}"></c:out> ,&nbsp;
                        <c:out value="${address.streetName}"></c:out> ,&nbsp;
                        <c:out value="${address.city}"></c:out> ,&nbsp;
                        <c:out value="${address.state}"></c:out>                                    
                        </p>

                        <p style="font-size: 13px;">   <c:forEach var="contactNumbers" items="${contactNumbersList}">
                            <c:out value="${contactNumbers.contactNumberValue}"></c:out> &nbsp;                            
                        </c:forEach> 
                    </p>

                    <p style="font-size: 13px;">   <c:forEach var="emailAddresses" items="${EmailAddressList}">
                            <c:out value="${emailAddresses.emailAddressValue}"></c:out> &nbsp;                            
                        </c:forEach> 
                    </p>
                </div></div>


            <div class="row receipt_row" >
                <div class="col receipt_col" style="width:30%;"><p>PATIENT NAME</p></div>
                <div class="col " style="width:65%;">
                    <p>  <c:out value="${systemUserDetail.title}"></c:out>   &nbsp; 
                        <c:out value="${systemUserDetail.firstName}"></c:out>   &nbsp; 
                        <c:out value="${systemUserDetail.lastName}"></c:out> </p>
                    </div>
                </div>
                <div class="row receipt_row" >
                    <div class="col receipt_col" style="width:30%;"><p>RESERVED DATE</p></div>
                    <div class="col " style="width:65%;"><p>                    <c:set var="string" value="${visitingSolts.startTime}"/>
                    <p> <c:out value="${fn:substring(string,-1,16)}"/>       </p> </p></div>
            </div>   

            <div class="row receipt_row" >
                <div class="col receipt_col" style="width:30%;"><p>DOCTOR NAME</p></div>
                <div class="col " style="width:65%;">
                    <p>  <c:out value="${doctor.title}"></c:out>   &nbsp; 
                        <c:out value="${doctor.firstName}"></c:out>  &nbsp; 
                        <c:out value="${doctor.lastName}"></c:out>     </p>  
                    </div>
                </div> 
                <div class="row receipt_row" >
                    <div class="col receipt_col" style="width:30%;"><p>DOCTOR FEE</p></div>
                    <div class="col " style="width:65%;"><p><c:out value="${doctorCharges}"></c:out></p></div>
                </div> 
                <div class="row receipt_row" >
                    <div class="col receipt_col" style="width:30%;"><p>HOSPITAL FEE</p></div>
                    <div class="col " style="width:65%;"><p> <c:out value="${hospitalCharges}"></c:out>   </p></div>
                </div> 

                <div class="row receipt_row" >
                    <div class="col receipt_col" style="width:30%;"><p>TOTAL AMOUNT</p></div>
                    <div class="col " style="width:65%;"><p><c:out value="${total}"></c:out></p></div>
            </div> 

            <div class="row" style="height: 50px;border: 1px solid #333333;">

                <div class="col " style="width:100%;"><p style="position: relative;left: 204px;top: 16px;font-size: 14px;font-weight: bold;">THANK YOU</p></div>
            </div>



        </div>


    </div>     
