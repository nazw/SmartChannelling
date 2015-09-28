<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>

<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
<script type="text/javascript" src="js/gmap3.min.js"></script>

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
           
    function loadMapForAddBranch() {

        $('#googleMap')
        .gmap3(
        {
            action : 'addMarker',
            latLng : [  6.878652801196894, 79.8798788189888 ],
            map : {
                center : true,
                zoom : 16,
                streetViewControl : false,
                mapTypeId : google.maps.MapTypeId.ROADMAP
            },
            marker : {
                options : {
                    draggable : true,
                    icon : 'http://code.google.com/intl/fr/apis/maps/documentation/javascript/examples/images/beachflag.png'
                },
                events : {
                    dragend : function(marker) {
                        $(this)
                        .gmap3(
                        {
                            action : 'getAddress',
                            latLng : marker
                            .getPosition(),
                            callback : function(
                            results) {
                                var map = $(
                                this)
                                .gmap3(
                                'get'), infowindow = $(
                                this)
                                .gmap3(
                                {
                                    action : 'get',
                                    name : 'infowindow'
                                }), content = results
                                    && results[1] ? results
                                    && results[1].formatted_address
                                : 'no address';
                                $(
                                '#googleMapPoint')
                                .val(
                                marker
                                .getPosition());
                                $(
                                '#addressValue')
                                .val(
                                content);

                                if (infowindow) {
                                    infowindow
                                    .open(
                                    map,
                                    marker);
                                    infowindow
                                    .setContent(content);
                                    $(
                                    '#googleMapPoint')
                                    .val(
                                    marker
                                    .getPosition());
                                    $(
                                    '#addressValue')
                                    .val(
                                    content);

                                } else {

                                    $(
                                    '#addressValue')
                                    .val(
                                    content);
                                    $(
                                    '#googleMapPoint')
                                    .val(
                                    marker
                                    .getPosition());
                                    $(this)
                                    .gmap3(
                                    {
                                        action : 'addinfowindow',
                                        anchor : marker,
                                        options : {
                                            content : content
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });

    }
        
   
    function hospitalform(){   
		
        var validateError='';
        var  registrationNumber = $("#registrationNumber").val();
        var  hospitalName = $("#hospitalName").val();
        var  emailAddressValue1 = $("#emailAddressValue0").val();
        var  contactNumberValue1 = $("#contactNumberValue0").val();
        var  addressValue = $("#addressValue").val(); 
                    
        var emailCheckString = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            
        if (!validate())
        {
            validateError="Please select at least one hospital Specializations";                      
        }
           
        if (contactNumberValue1 == "")
        {
            validateError="Primary contact Number is empty";              
        }
      
        if (emailAddressValue1 == "")
        {
            validateError="Primary Email Address is empty";              
        }
        if (addressValue == "")
        {
            validateError="Address is empty";              
        }

        if (registrationNumber == "")
        {
            validateError="Registration Number is empty";              
        }
        if (hospitalName == "")
        {
            validateError="Hospital Name is empty";              
        }
           
        if(validateError || ! /^\s*$/.test(validateError)){         
            alert(validateError);            
            return false;
        }
               
        return true;
    }
        
    function validate()
    {
        var chks = document.getElementsByName('hospitalSpecializations');
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


<div style="display: block;"><h1>Update Hospital</h1>
    <c:if test="${errorMessage != null}" ><h2><span style="padding-left: 100px;"><c:out value="${errorMessage}"></c:out></span></h2></c:if>
</div>

<form:form action="updateHospital.htm" method="post" id="form2"
           modelAttribute="hospital" onsubmit="return hospitalform()" >

    <div class="row">
        <div class="col1">Hospital Name</div>
        <div class="col2">
            <form:input path="hospitalName" id="hospitalName"
                        cssClass="textField" />
            <span class="mandotary">*</span>
        </div>
        <div class="clear"></div>
    </div> 

    <div class="row">
        <div class="col1">Registration Number</div>
        <div class="col2">
            <form:input path="registrationNumber" id="registrationNumber"
                        cssClass="textField" />
            <span class="mandotary">*</span>
        </div>
        <div class="clear"></div>
    </div> 

    <div class="row" style="padding-bottom: 10px;">
        <div class="col " style="width: 30%;">
            <p style="padding-bottom: 8px;">Address</p>

            <div id="googleMap" class="gmap3 row"
                 style="overflow: visible; width: 185px; height: 170px; border: 1px solid black;"></div>
        </div>

        <div class="col " style="width: 70%;">
            <div class="row" style="padding-bottom: 10px;">
                <div class="col" style="width: 50%">
                    <p>&nbsp;</p>
                    <form:input path="address.addressValue" id="addressValue"
                                cssClass="textField" />
                    <span class="mandotary">*</span>
                </div>

                <div class="col" style="width: 50%">
                    <p style="padding-bottom: 5px;">StreetNumber</p>

                    <form:input path="address.streetNumber" id="streetNumber"
                                cssClass="textField" />

                </div>
            </div>


            <div class="row" style="padding-bottom: 10px;">
                <div class="col" style="width: 50%">
                    <p style="padding-bottom: 5px;">StreetName</p>
                    <form:input path="address.streetName" id="streetName"
                                cssClass="textField" />

                </div>
                <div class="col" style="width: 50%">
                    <p style="padding-bottom: 5px;">City</p>
                    <form:input path="address.city" id="city"
                                cssClass="textField" />

                </div>
            </div>




            <div class="row" style="padding-bottom: 10px;">
                <div class="col" style="width: 50%">
                    <p style="padding-bottom: 5px;">State</p>
                    <form:input path="address.state" id="state"
                                cssClass="textField" />

                </div>
                <div class="col" style="width: 50%">
                    <p style="padding-bottom: 5px;">Country</p>

                    <form:input path="address.country" id="country"
                                cssClass="textField" />

                </div>
            </div>



            <div class="row" style="padding-bottom: 30px;">
                <div class="col" style="width: 50%">
                    <p style="padding-bottom: 5px;">PostalCode</p>
                    <form:input path="address.postalCode" id="postalCode"
                                cssClass="textField" />

                </div>
                <div class="col" style="width: 50%">
                    <p style="padding-bottom: 5px;">ZipCode</p>
                    <form:input path="address.zipCode" id="zipCode"
                                cssClass="textField" />

                </div>
            </div>

        </div>






        <form:hidden path="address.googleMapPoint" id="googleMapPoint" />

        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">
                <p>Primary Email Address</p>
            </div>
            <div class="col " style="width: 70%;">		
                <form:input path="emailAddresses[0].emailAddressValue" name="emailAddressValue0" id="emailAddressValue0" class="textField" />
                <span class="mandotary">*</span>
            </div>
        </div>

        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">&nbsp;</div>
            <div class="col " style="width: 70%;">
                <a id="showSecondaryEmail"
                   style="color: #B46F07; font-size: 10px; cursor: hand;"
                   href="javascript:void(0)">Add Secondary Email Address</a>
            </div>
        </div>



        <div class="row" style="padding-bottom: 10px;" id="secondaryEmail">
            <div class="col " style="width: 30%;">
                <p>Secondary Email Address</p>
            </div>
            <div class="col " style="width: 70%;">
                <form:input path="emailAddresses[1].emailAddressValue" name="emailAddressValue1" id="emailAddressValue1" class="textField" />
            </div>
        </div>

        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">
                <p>Primary Contact No</p>
            </div>
            <div class="col " style="width: 70%;">
                <form:input path="contactNumbers[0].contactNumberValue" name="contactNumberValue0" id="contactNumberValue0" class="textField" />
                <span class="mandotary">*</span>
            </div>
        </div>

        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">&nbsp;</div>
            <div class="col " style="width: 70%;">
                <a id="showSecondaryContact"
                   style="color: #B46F07; font-size: 10px; cursor: hand;"
                   href="javascript:void(0)">Add Secondary Contact No</a>
            </div>
        </div>

        <div class="row" style="padding-bottom: 10px;" id="secondaryContact">
            <div class="col " style="width: 30%;">
                <p>Secondary Contact No</p>
            </div>
            <div class="col " style="width: 70%;">
                <form:input path="contactNumbers[1].contactNumberValue" name="contactNumberValue1" id="contactNumberValue1" class="textField" />

            </div>
        </div>

        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">
                <p>Mother Company</p>
            </div>
            <div class="col " style="width: 70%;">
                <form:input path="parentCompanyName" id="parentCompanyName" cssClass="textField" />
            </div>
        </div>

        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">
                <p>Description</p>
            </div>
            <div class="col " style="width: 70%;">
                <form:textarea path="description" name="description" class="textArea" id="description" cols="30" rows="6" />
            </div>
        </div>

        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">
                <p>Facilities</p>
            </div>
            <div class="col " style="width: 70%;">
                <p style="color: #B46F07; font-size: 11px; padding-bottom: 10px;">Please
			enter a comma-separated list of facilities</p>
                    <form:textarea path="facilities" name="facilities" class="textArea" id="facilities" cols="30" rows="6"  />
            </div>
        </div>

        <div class="row" style="padding-bottom: 20px; padding-top: 20px;">
            <div class="col " style="width: 30%;">
                <p>Hospital Specialization</p>
            </div>
            <div class="col " style="width: 70%;">
                <div class="row" style="padding-bottom: 10px;">
                    <c:forEach items="${specializations}" var="specialization" >                   
                        <c:set var="doesValueMatch" value="false" />
                        <c:forEach items="${hospitalSpecializationsList}" var="sp" >              
                            <c:if test="${specialization.specializationId == sp.specialization.specializationId}">
                                <c:set var="doesValueMatch" value="true" />
                                <input type="checkbox" value="${specialization.specializationId}" id="hospitalSpecializations" name="hospitalSpecializations" checked="checked"/>
                                <span style="padding-right:8px;"><c:out value="${specialization.name}"></c:out></span>
                            </c:if>     
                        </c:forEach>
                        <c:if test="${doesValueMatch=='false'}" >
                            <input type="checkbox" value="${specialization.specializationId}" id="hospitalSpecializations" name="hospitalSpecializations"/>
                            <span style="padding-right:8px;"><c:out value="${specialization.name}"></c:out></span>
                        </c:if>
                    </c:forEach>  <span class="mandotary">*</span>			
                </div>
            </div>
        </div>


        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">
                <p>Hospital Charges</p>
            </div>
            <div class="col " style="width: 70%;">
                <form:input path="charges" id="charges" cssClass="textField" />

            </div>
        </div>


        <div class="row" style="padding-bottom: 10px;">
            <div class="col " style="width: 30%;">
                <p>Web Url</p>
            </div>
            <div class="col " style="width: 70%;">
                <form:input path="webUrl" id="webUrl" cssClass="textField" />
            </div>
        </div>


        <div class="row" style="padding-top: 20px;">

            <form:input path="hospitalId" name="hospitalId" id="hospitalId" type="hidden"/>
            <form:input path="emailAddresses[0].emailAddressId" name="emailAddressId0" id="emailAddressId0" type="hidden"/>
            <form:input path="emailAddresses[1].emailAddressId" name="emailAddressId1" id="emailAddressId1" type="hidden"/>
            <form:input path="contactNumbers[0].contactNumberId" name="contactNumberId0" id="contactNumberId0" type="hidden"/>
            <form:input path="contactNumbers[1].contactNumberId" name="contactNumberId1" id="contactNumberId1" type="hidden"/>
        </div>



        <div class="row">
            <div class="col1"></div>
            <div class="col2"><input type="submit" value="Submit" id="submit" class="button" /> <input type="reset" value="Clear" id="submit" class="button" /></div>
            <div class="clear"></div>
        </div>                                                           

        <div class="mandotary">* Mandotary Fields</div>  
    </form:form>
</div>

