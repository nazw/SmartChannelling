<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><tiles:insertAttribute name="title" ignore="true" /></title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv=”X-UA-Compatible” content=”ie=edge,chrome=1″ />

        <link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico" />
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/jquery.xtip.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/alert.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/style.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/light.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/serch_result.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/confirm_your_bid_request.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/custom-theme/jquery-ui-1.8.13.custom.css"/>" />

        <style type="text/css" media="screen">

            .ui-datepicker-trigger{
                margin-top:0px !important;
                vertical-align: middle;
                margin-left: 0px;

            }

        </style>
        <!-- This is the loaded throught the CDN  -->	
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
        <!--<script type="text/javascript" src="js/jquery-1.7.1.min.js" ></script >-->
        <!--        <script type="text/javascript" src="<c:url value="js/homelayout.required.core.min.js"/>" ></script>-->

<!--		<script type="text/javascript" src="<c:url value="js/jquery-1.6.2.min.js"/>"></script>        -->
        <script type="text/javascript" src="<c:url value="js/json.min.js"/>"></script>
<!--        <script type="text/javascript" src="<c:url value="js/jquery.ui.core.js"/>"></script>-->
        <script type="text/javascript" src="<c:url value="js/jquery.ui.widget.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/jquery.ui.position.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/ui.datepicker.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/ajaxupload.3.6.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/jquery.ui.autocomplete.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/alert.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/jquery.raty.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="js/jquery.xtip.js"/>"></script>

        <script type="text/javascript">
            <!--
            //Do not remove Util to add hours to a date object used to validate current date
            Date.prototype.removeHours= function(){
                this.setHours(0);
                this.setMinutes(0, 0, 0);
                return this;
            };

            function disableEnterKey(e)
            {
                var key;
                if(window.event)
                    key = window.event.keyCode; //IE
                else
                    key = e.which; //firefox

                return (key != 13);
            }

            $(document).ready(function() {
                refreshLoggedInAsName()
                refreshCart();
                //refreshGenericProductCart();
            });
	        
            function refreshLoggedInAsName(){
                $('#loggedInUserNameSpan').html('');	 
                $.getJSON("getLoggedInAsDetails.htm", {}, function(data) {  				
                    $('#loggedInUserNameSpan').html(data.name);	 				
                });
            }
        
            function checkIfNumeric(inputId){                        	                      	
                $("#" + inputId).keydown(function(event) {
                    // Allow only backspace and delete
                    // Allow only backspace and delete
                    if ( event.keyCode == 46 || event.keyCode == 8 ) { 
                    } 
                    else { 
                        if (event.keyCode < 95) { 
                            if (event.keyCode < 48 || event.keyCode > 57 ) { 
                                event.preventDefault();	
                            } 
                        } else { 
                            if (event.keyCode < 96 || event.keyCode > 105 ) { 
                                event.preventDefault();	
                            } 
                        } 
                    } 
                });
                var valInInput=$("#" + inputId).val();                        	
                if(isNaN(valInInput) == true){                        		
                    $("#" + inputId).val('1');
                } 
            }
            

          

            function addToCart(supplierProductId) {
                var combinedKeys=new Array();
                var productQty=$('#QtyInputs' + supplierProductId).val();
                var className='selectedValues' + supplierProductId;
                $.each($("input." + className + ":hidden"), function(){
                    var value=$(this).val();
                    combinedKeys.push(value);            		
                });       	
            	
                $('#loading'+supplierProductId).show('slow');
                var supplierProductId = supplierProductId;
                $.ajaxSetup({ cache: false });
                jQuery.ajaxSettings.traditional = true;
                $("#checkIfCartIsEmpty").val('');
                $.getJSON("addToCart.htm", { supplierProductId: supplierProductId, productQty:productQty, combinedKeys: combinedKeys }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#cartTable').html('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {
                    	
                        var id='divID' + value.uniqueItemId;
                        var mainText = value.supplierProductName;
                        var text = value.supplierProductName;
                        var qtyValue=value.productQuantity;
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }
                        $('#loading'+supplierProductId).hide();                            
                        $('#cartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + id + '" onfocus="checkIfNumeric(\'input' + id + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                        $("#checkIfCartIsEmpty").val(id);

                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                       
                    });
                    }
                    getHide();
                    $('#QtyInputs' + supplierProductId).val("1");
                });
                

            }

            function removeFromCart(uniqueItemId) {         	
                $.ajaxSetup({ cache: false });
                $.getJSON("removeFromCart.htm", { uniqueItemId : uniqueItemId }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#cartTable').html('');
                    $("span#clearArea").html("");
                    //var divToAdd="<div class='text_node' id='text_node'></div>";
                    //$("span#clearArea").html(divToAdd);
                    $("#checkIfCartIsEmpty").val('');                    
                    if(!(data==null)){
                    $.each(data, function(key, value) {
                        var hcpcCode="";                        
                        
                        var id='divID' + value.uniqueItemId;
                        var mainText = value.supplierProductName;
                        var text = value.supplierProductName;
                        var textDesc = value.supplierProductDescription;
                        var qtyValue=value.productQuantity;
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }       	
                        var hcpcCodeList = value.hcpcCode;                            
                        $.each(hcpcCodeList, function(index,value) {                                
                            hcpcCode=hcpcCode + value;                           
								
                        });
                           
                        var spanId='text_node'+value.uniqueItemId;
                            
                        if(textDesc.length > 80) {
                            textDesc = textDesc.substring(0, 80);
                            textDesc=textDesc + "...";
                        }
                            
                        $('#cartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + id + '" onfocus="checkIfNumeric(\'input' + id + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                                                                                
                                                     
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                            
                        var appendText=''; 
                                
                               
                        appendText=appendText +  '<div class="text_node" id="text_node' + spanId + '">' +  
                            '<span style="float: left;width:550px;">' + mainText + '</span>' +  
                            '<span style="float: right;padding-right: 10px">QTY: ' + 
                            '<input type="text" class="bid_confirm_qty_input" onblur="changeQuantity(\'' + value.uniqueItemId  + '\',this.value)" id="bid_confirm_qty_input' + value.uniqueItemId  + '" onfocus="checkIfNumeric(\'bid_confirm_qty_input' + value.uniqueItemId  + '\')" value="' + qtyValue + '"/></span>' + 
                            '</div><div style="clear: both"></div><div class="image_node" id="image_node' + value.uniqueItemId  + '">' + 
                            '<a href="#" onclick="revealDescription(\'' + value.uniqueItemId  + '\')">' + 
                            '<img src="<c:url value="images/plus_sign.png"/>" alt="">' +
                            '</a>&nbsp;&nbsp;' + textDesc + '</div><div style="padding-left: 10px">' + 
                            '<div style="background-color: #DFDFDF;width: 98%;display: none" id="image_description' + value.uniqueItemId  + '">' + 
                            '<table class="image_Desc" style="vertical-align: text-top">' + 
                            '<tr><td style="width: 20px;text-align: left;padding-bottom: 80px" rowspan="2" valign="top">' + 
                            '<div style="margin-top: 5px;"><a href="#" onclick="hideDescription(\'' + value.uniqueItemId  + '\')" id="hide_btn' + value.uniqueItemId  + '">' + 
                            '<img src="<c:url value="images/minus_sign.png"/>" alt=""></a></div></td><td rowspan="2" valign="top"><div style="margin-top: 5px;">' + 
                            '<img src="'+ value.supplierProductImageURL  +'" style="height: 90px" alt=""></div>' + 
                            '</td><td style="text-align: left;padding-bottom:5px;padding-left:10px;width:525px "></div>'+ value.supplierProductDescription +'</td></tr><tr>' +
                            '<td style="text-align: left;padding-bottom:5px;width:525px; "><div style="float:left;"><table width="100%" cellspacing="10px" ><tr>';
		 						
                        var myProductId= value.supplierProductId;
                        var uniqueItemId=value.uniqueItemId;
                        var appendSubText='';
                        var isSelected='';
                        $.each(value.productOptionCategories, function(index,valueNewMain) { 
                            var categoryId =valueNewMain.productOptionCategoryId;
                            var categoryDivId= uniqueItemId + '_' + myProductId + '_' + categoryId; 
                            var categoryOptDivMyId=categoryDivId;
                            appendSubText=appendSubText + '<td><div class="category-div_bid_confirm" id="' + categoryDivId + '">' + 
                                '<div class="category_name">' + valueNewMain.productOptionCategoryName + ' : </div>' + 
                                '<table id="mytable' + categoryDivId + '"><tr>';
                                				  
                            $.each(valueNewMain.productOptionCategoryValues, function(index,valueNewSub) { 
                                var categoryOptionId =valueNewSub.productOptionCategoryValueId;                                					 
                                $.each(value.categoryAndOptionMap, function(key, values) {
                                    if(key==categoryId){
                                        isSelected='NO';
                                        if(values==categoryOptionId){
                                            isSelected='YES';
                                            appendSubText=appendSubText +'<input type="hidden" id="oldCombination' + categoryId+ '_' + uniqueItemId + '" value="' + myProductId + '_' + categoryId + '_' + categoryOptionId + '" />';
                                        }
                                    }                                    						   
                                });
                                						  
                                var categoryOptDivId =categoryDivId + '_' + categoryOptionId;
                                if(isSelected == 'YES'){
                                    appendSubText=appendSubText +' <td><span id=' + categoryOptDivId + '" onclick="" class="selected_opt">' + valueNewSub.productOptionCategoryValueName + '</span></td>';
                                }else if(isSelected == 'NO'){
                                    appendSubText=appendSubText +'<td><span id="' + categoryOptDivId + '" onclick="toggleClass(\'' + categoryDivId + '\',\'' + categoryOptDivId + '\',\'' + myProductId+ '\',\'' + categoryId+ '\',\'' + categoryOptionId+ '\',\'' + uniqueItemId+ '\')"' +  
                                        'class="non_selected_opt">' + valueNewSub.productOptionCategoryValueName + '</span></td>';
                                }
                                					                                					  
                            });
                                				  
                            appendSubText=appendSubText +'</tr></table></div></td>';		
    								
                        });
                                
                               
                        appendText=appendText + appendSubText + '</tr></table></div></td></tr></table></div></div>';		 		
                        $("#checkIfCartIsEmpty").val(appendText); 					 		
                        $("#clearArea").append(appendText);                                               
                    });
                    }
                    getHide();
                });

            }

            function refreshCart() {
                $.ajaxSetup({ cache: false });
                
                $.getJSON("refreshCart.htm", {}, function(data) {                	
                    $('#cartTable').html('');
                    $("#checkIfCartIsEmpty").val('');  
                    if(!(data==null)){
                    $.each(data, function(key, value) {
                        var supplierProductId=value.supplierProductId;
                        var id='divID' + value.uniqueItemId;
                        var mainText = value.supplierProductName;
                        var text = value.supplierProductName;
                        var qtyValue=value.productQuantity;
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }
                        $('#loading'+supplierProductId).hide();                            
                        $('#cartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + id + '" onfocus="checkIfNumeric(\'input' + id + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                        $("#checkIfCartIsEmpty").val(id);

                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                   
                    });                   
                    }
                    refreshGenericProductCart();
                    
                });

            }
            
            function changeQuantity(uniqueProductId, qty){
                var showStatus =  $('#image_description'+uniqueProductId+'').is(":visible");
                $.ajaxSetup({ cache: false });     
                $.getJSON("changeQuantity.htm", { 
                    uniqueproductId: uniqueProductId, 
                    qty: qty   	  
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#cartTable').html('');
                    $("span#clearArea").html("");
                    //var divToAdd="<div class='text_node' id='text_node'></div>";
                    //$("span#clearArea").html(divToAdd);
                    $("#checkIfCartIsEmpty").val('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {
                        var hcpcCode="";                        
                        
                        var id='divID' + value.uniqueItemId;
                        var mainText = value.supplierProductName;
                        var text = value.supplierProductName;
                        var textDesc = value.supplierProductDescription;
                        var qtyValue=value.productQuantity;
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }                                              
                        $('#cartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + id + '" onfocus="checkIfNumeric(\'input' + id + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                        $("#checkIfCartIsEmpty").val(id);
                          
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                      
                        var spanId='text_node'+value.uniqueItemId;
                          
                        if(textDesc.length > 80) {
                            textDesc = textDesc.substring(0, 80);
                            textDesc=textDesc + "...";
                        }
                          
                        var appendText=''; 
                          
                        
                        appendText=appendText +  '<div class="text_node" id="text_node' + spanId + '">' +  
                            '<span style="float: left;width:550px;">' + mainText + '</span>' +  
                            '<span style="float: right;padding-right: 10px">QTY: ' + 
                            '<input type="text" class="bid_confirm_qty_input" onblur="changeQuantity(\'' + value.uniqueItemId  + '\',this.value)" id="bid_confirm_qty_input' + value.uniqueItemId  + '" onfocus="checkIfNumeric(\'bid_confirm_qty_input' + value.uniqueItemId  + '\')" value="' + qtyValue + '"/></span>' + 
                            '</div><div style="clear: both"></div><div class="image_node" id="image_node' + value.uniqueItemId  + '">' + 
                            '<a href="#" onclick="revealDescription(\'' + value.uniqueItemId  + '\')">' + 
                            '<img src="<c:url value="images/plus_sign.png"/>">' +
                            '</a>&nbsp;&nbsp;' + textDesc + '</div><div style="padding-left: 10px">' + 
                            '<div style="background-color: #DFDFDF;width: 98%;display: none" id="image_description' + value.uniqueItemId  + '">' + 
                            '<table class="image_Desc" style="vertical-align: text-top">' + 
                            '<tr><td style="width: 20px;text-align: left;padding-bottom: 80px" rowspan="2" valign="top">' + 
                            '<div style="margin-top: 5px;"><a href="#" onclick="hideDescription(\'' + value.uniqueItemId  + '\')" id="hide_btn' + value.uniqueItemId  + '">' + 
                            '<img src="<c:url value="images/minus_sign.png"/>" ></a></div></td><td rowspan="2" valign="top"><div style="margin-top: 5px;">' + 
                            '<img src="'+ value.supplierProductImageURL  +'" style="height: 90px"></div>' + 
                            '</td><td style="text-align: left;padding-bottom:5px;padding-left:10px;width:525px "></div>'+ value.supplierProductDescription +'</td></tr><tr>' +
                            '<td style="text-align: left;padding-bottom:5px;width:525px; "><div style="float:left;"><table width="100%" cellspacing="10px" ><tr>';
           					
                        var myProductId= value.supplierProductId;
                        var uniqueItemId=value.uniqueItemId;
                        var appendSubText='';
                        var isSelected='';
                        $.each(value.productOptionCategories, function(index,valueNewMain) { 
                            var categoryId =valueNewMain.productOptionCategoryId;
                            var categoryDivId= uniqueItemId + '_' + myProductId + '_' + categoryId; 
                            var categoryOptDivMyId=categoryDivId;
                            appendSubText=appendSubText + '<td><div class="category-div_bid_confirm" id="' + categoryDivId + '">' + 
                                '<div class="category_name">' + valueNewMain.productOptionCategoryName + ' : </div>' + 
                                '<table id="mytable' + categoryDivId + '"><tr>';
                           				  
                            $.each(valueNewMain.productOptionCategoryValues, function(index,valueNewSub) { 
                                var categoryOptionId =valueNewSub.productOptionCategoryValueId;                                					 
                                $.each(value.categoryAndOptionMap, function(key, values) {
                                    if(key==categoryId){
                                        isSelected='NO';
                                        if(values==categoryOptionId){
                                            isSelected='YES';
                                            appendSubText=appendSubText +'<input type="hidden" id="oldCombination' + categoryId+ '_' + uniqueItemId + '" value="' + myProductId + '_' + categoryId + '_' + categoryOptionId + '" />';
                                        }
                                    }                                    						   
                                });
                           						  
                                var categoryOptDivId =categoryDivId + '_' + categoryOptionId;
                                if(isSelected == 'YES'){
                                    appendSubText=appendSubText +' <td><span id=' + categoryOptDivId + '" onclick="" class="selected_opt">' + valueNewSub.productOptionCategoryValueName + '</span></td>';
                                }else if(isSelected == 'NO'){
                                    appendSubText=appendSubText +'<td><span id="' + categoryOptDivId + '" onclick="toggleClass(\'' + categoryDivId + '\',\'' + categoryOptDivId + '\',\'' + myProductId+ '\',\'' + categoryId+ '\',\'' + categoryOptionId+ '\',\'' + uniqueItemId+ '\')"' +  
                                        'class="non_selected_opt">' + valueNewSub.productOptionCategoryValueName + '</span></td>';
                                }
                           					                                					  
                            });
                           				  
                            appendSubText=appendSubText +'</tr></table></div></td>';		
           						
                        });
                           
                          
                        appendText=appendText + appendSubText + '</tr></table></div></td></tr></table></div></div>';		 		
           									 		
                        $("span#clearArea").append(appendText);

                     
                       
                    });
                    }
                    getHide();
                    if(showStatus == true){
                        revealDescriptionQuickly(uniqueProductId);
                    }
                });
            }
            
            //For the generic products
            function addToCartGenericProduct(uniqueItemId){
        	  
                $('#loading' + uniqueItemId).show();
        	  
                var hcpcId=$('#hcpcCodeIdGen').val();
                var searchTerm=$('#searchTerm').val();
                var additionalNote=$('#comment').val();
                var quantity=$('#qtyInputsGen').val();
        		 
        	  
                $.ajaxSetup({ cache: false});              
                $.getJSON("addToCartGenericProduct.htm", { 
                    hcpcId: hcpcId, 
                    searchTerm: searchTerm, 
                    additionalNote: additionalNote,
                    quantity: quantity
            	  
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#genericCartTable').html('');
                    $("#checkIfGenericCartIsEmpty").val('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {                 	
                     
                        var id='divID' + value.uniqueItemId;
                	  
                        var text = value.hcpcCode + " - " + value.serchTerm;
                        var mainText = text;
                        var qtyValue=value.productQuantity;
                      
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }
                      
                        $('#loading' + uniqueItemId).hide();
                      
                        $('#genericCartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeGenericProductQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + uniqueItemId + '" onfocus="checkIfNumeric(\'input' + uniqueItemId + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeGenericProductFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                      
                        $('#qtyInputsGen').val("1");
                        $("#checkIfGenericCartIsEmpty").val(id);
                  
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                
                    });
                    }
                    getHide();
                }); 
               
            }
          
            function removeGenericProductFromCart(uniqueItemId){
                $("#checkIfGenericCartIsEmpty").val('');
                $.ajaxSetup({ cache: false });              
                $.getJSON("removeGenericProductFromCart.htm", { 
            	  
                    uniqueItemId: uniqueItemId           	  
            
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#genericCartTable').html('');
                    $('#clearGenericArea').html('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {                 	
                     
                        var id='divID' + value.uniqueItemId;
                	  
                        var text = value.hcpcCode + " - " + value.serchTerm;
                        var mainText = text;
                        var qtyValue=value.productQuantity;
                      
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }
                      
                        $('#loading' + uniqueItemId).hide();
                      
                        $('#genericCartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeGenericProductQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + uniqueItemId + '" onfocus="checkIfNumeric(\'input' + uniqueItemId + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeGenericProductFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                      
                    
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                    
                        var uniqueItemId=value.uniqueItemId;
                        var desText=value.codeDescription;
                    
                        if(desText.length > 15) {
                            desText = desText.substring(0, 80);
                            desText=desText + "...";
                        }
                    
                        var codeDescription=value.codeDescription;
                               				
                        $('#clearGenericArea').append('<div class="text_node" id="text_node">'
                            + '<span style="float: left;width:550px;">'
                            + '"' + value.serchTerm + '" - SUPPLIER SELECTS PRODUCT FOR HCPC: ' + value.hcpcCode + ''
                            + '</span>' 
                            + '<span style="float: right;padding-right: 10px">QTY:' 
                            + '<input type="text" class="bid_confirm_qty_input" onblur="changeGenericProductQuantity(\'' + uniqueItemId + '\',this.value)" value="' + value.productQuantity + '" id="bid_confirm_qty_input' + uniqueItemId + '" onfocus="checkIfNumeric(\'bid_confirm_qty_input' + uniqueItemId + '\')"/></span>'
                            + '</div>'	                         
                            + '<div style="clear: both"></div>'
                            + '<div class="image_node" id="image_node' + uniqueItemId + '">'
                            + '<a href="#" onclick="revealDescription(\'' + uniqueItemId + '\')">'
                            + '<img src="<c:url value="images/plus_sign.png"/>">'
                            + '</a>&nbsp;&nbsp;' + desText + ''	
                            + '</div>'	                         
                            + '<div style="padding-left: 10px">'
                            + '<div style="background-color: #DFDFDF;width: 98%;display: none" id="image_description' + uniqueItemId + '">'
                            + '<table class="image_Desc" style="vertical-align: text-top">'
                            + '<tr>'
                            + '<td style="width: 20px;text-align: left;padding-bottom: 80px" rowspan="2" valign="top">'
                            + '<div style="margin-top: 5px;"><a href="#" onclick="hideDescription(\'' + uniqueItemId + '\')" id="hide_btn' + uniqueItemId + '"><img src="<c:url value="images/minus_sign.png"/>" ></a></div>'
                            + '</td>'	                                                
                            + '<td valign="top" style="text-align: left;padding-bottom:5px;padding-left:10px;width:525px;padding-top: 10px; "></div>'
                            + '' + codeDescription + '' 
                            + '</td>'
                            + '</tr>'	                                            
                            + '</table>'
                            + '</div>'
                            + '</div>');
                           		
                      
                        $('#qtyInputsGen').val("1");
                        $("#checkIfGenericCartIsEmpty").val(id);
                  
                    });
                    }
                    getHide();
                }); 
        	  
        	  
        	  
            }
          
            function changeGenericProductQuantity(uniqueItemId, quantity){
                $.ajaxSetup({ cache: false });              
                $.getJSON("changeGenericProductQuantity.htm", { 
            	  
                    uniqueItemId: uniqueItemId,
                    quantity : quantity
            
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#genericCartTable').html('');
                    $('#clearGenericArea').html('');
                    $("#checkIfGenericCartIsEmpty").val('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {                 	
                     
                        var id='divID' + value.uniqueItemId;
                	  
                        var text = value.hcpcCode + " - " + value.serchTerm;
                        var mainText = text;
                        var qtyValue=value.productQuantity;
                      
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }
                      
                        $('#loading' + uniqueItemId).hide();
                      
                        $('#genericCartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeGenericProductQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + uniqueItemId + '" onfocus="checkIfNumeric(\'input' + uniqueItemId + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeGenericProductFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                      
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                  
                        var uniqueItemId=value.uniqueItemId;
                        var desText=value.codeDescription;
                  
                        if(desText.length > 15) {
                            desText = desText.substring(0, 80);
                            desText=desText + "...";
                        }
                  
                        var codeDescription=value.codeDescription;
                             				
                        $('#clearGenericArea').append('<div class="text_node" id="text_node">'
                            + '<span style="float: left;width:550px;">'
                            + '"' + value.serchTerm + '" - SUPPLIER SELECTS PRODUCT FOR HCPC: ' + value.hcpcCode + ''
                            + '</span>' 
                            + '<span style="float: right;padding-right: 10px">QTY:' 
                            + '<input type="text" class="bid_confirm_qty_input" onblur="changeGenericProductQuantity(\'' + uniqueItemId + '\',this.value)" value="' + value.productQuantity + '" id="bid_confirm_qty_input' + uniqueItemId + '" onfocus="checkIfNumeric(\'bid_confirm_qty_input' + uniqueItemId + '\')"/></span>'
                            + '</div>'	                         
                            + '<div style="clear: both"></div>'
                            + '<div class="image_node" id="image_node' + uniqueItemId + '">'
                            + '<a href="#" onclick="revealDescription(\'' + uniqueItemId + '\')">'
                            + '<img src="<c:url value="images/plus_sign.png"/>">'
                            + '</a>&nbsp;&nbsp;' + desText + ''	
                            + '</div>'	                         
                            + '<div style="padding-left: 10px">'
                            + '<div style="background-color: #DFDFDF;width: 98%;display: none" id="image_description' + uniqueItemId + '">'
                            + '<table class="image_Desc" style="vertical-align: text-top">'
                            + '<tr>'
                            + '<td style="width: 20px;text-align: left;padding-bottom: 80px" rowspan="2" valign="top">'
                            + '<div style="margin-top: 5px;"><a href="#" onclick="hideDescription(\'' + uniqueItemId + '\')" id="hide_btn' + uniqueItemId + '"><img src="<c:url value="images/minus_sign.png"/>" ></a></div>'
                            + '</td>'	                                                
                            + '<td valign="top" style="text-align: left;padding-bottom:5px;padding-left:10px;width:525px;padding-top: 10px; "></div>'
                            + '' + codeDescription + '' 
                            + '</td>'
                            + '</tr>'	                                            
                            + '</table>'
                            + '</div>'
                            + '</div>');
                  
                        $('#qtyInputsGen').val("1");
                        $("#checkIfGenericCartIsEmpty").val(id);                  
                    });
                    }
                    getHide();
                }); 
            }
          
            function refreshGenericProductCart(){			  
                $.ajaxSetup({ cache: false });              
                $.getJSON("refreshGenericProductCart.htm", { 
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#genericCartTable').html('');
                    $("#checkIfGenericCartIsEmpty").val('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {                 	
                     
                        var id='divID' + value.uniqueItemId;
                	  
                        var text = value.hcpcCode + " - " + value.serchTerm;
                        var mainText = text;
                        var qtyValue=value.productQuantity;
                      
                        if(text.length > 15) {
                            text = text.substring(0, 15);
                            text=text + "...";
                        }
                      
                        $('#loading' + value.uniqueItemId).hide();
                      
                        $('#genericCartTable').append('<tr>' 
                            +  ' <td width="30px"><input onblur="changeGenericProductQuantity(\'' + value.uniqueItemId  + '\',this.value)" class="cart_product_qty" id="input' + value.uniqueItemId + '" onfocus="checkIfNumeric(\'input' + value.uniqueItemId + '\')" value="' + qtyValue + '" /></td>'
                            +  ' <td width="145px">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeGenericProductFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                      
                        $('#qtyInputsGen').val("1");
                        $("#checkIfGenericCartIsEmpty").val(id);     
                  
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                  
                    });
                    }
                    refreshServiceCart();
                });            
            }
		
            function getHide(){			
                var inputVal=$("#checkIfCartIsEmpty").val(); 
                var inputGenericVal=$('#checkIfGenericCartIsEmpty').val();
                var inputServiceVal=$('#checkIfServiceCartIsEmpty').val();
                //alert("inputVal :"  + inputVal + " : inputGenericVal :" + inputGenericVal);
                if(inputVal.length > 0 || inputGenericVal.length > 0 || inputServiceVal.length > 0){
                    $("#cartButton").show();
                    $("#submitBid").show();
                    $("#cartItemDiv").show();
                    $("#cartEmptyDiv").hide();
                }else{
                    $("#cartButton").hide();
                    $("#submitBid").hide();
                    $("#cartItemDiv").hide();
                    $("#cartEmptyDiv").show();
                    var divToAdd="<span class='errorMsg'>Your Cart is Empty.Checkout not allowed </span>";
                    $("span#bidRequestItemWrapper").html(divToAdd);
                }
            }
		
            //for the service-related functionalities
            function addToCartService(serviceId){
			
                var modifiedId='SER' + serviceId;
                var comment=$('#comment' + modifiedId).val();
			
                $.ajaxSetup({ cache: false });              
                $.getJSON("addToCartService.htm", { 
                    serviceId: modifiedId, 
                    comment: comment          	  
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#servicecartTable').html('');
                    $("#checkIfServiceCartIsEmpty").val('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {                 	
                    
                        var id='divID' + value.uniqueItemId;
               	  
                        var text = value.supplierServiceName; 
                        var mainText = text;
                        if(text.length > 20) {
                            text = text.substring(0, 20);
                            text=text + "...";
                        }
                     
                        $('#loading' + value.uniqueItemId).hide();
                     
                        $('#servicecartTable').append('<tr>' 
                            +  ' <td width="120px" colspan="2">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeServiceFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
                     
                        $("#checkIfServiceCartIsEmpty").val(id);
                 
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                 
                    });
                    }
                    getHide();
                }); 
			
            }
		
            function removeServiceFromCart(serviceId){
		
                $("#checkIfServiceCartIsEmpty").val('');
                $.ajaxSetup({ cache: false });              
                $.getJSON("removeServiceFromCart.htm", { 
          	  
                    uniqueItemId: serviceId           	  
          
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#servicecartTable').html('');
                    $('#clearServiceArea').html('');
                    if(!(data==null)){
                    $.each(data, function(key, value) {                 	
                   
                        var id='divID' + value.uniqueItemId;
              	  
                        var text = value.supplierServiceName; 
                        var mainText = text;
                        if(text.length > 20) {
                            text = text.substring(0, 20);
                            text=text + "...";
                        }
                    
                        $('#loading' + value.uniqueItemId).hide();
                    
                        $('#servicecartTable').append('<tr>' 
                            +  ' <td width="120px" colspan="2">'
                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
                            +  ' </td>'
                            +  ' <td><a href="#" style="color:#195771" onclick="removeServiceFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
                            +  ' </tr>'
                            +  ' <tr>'
                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;"  size="1px" color="#969696"/></td>'
                            +  ' </tr>');
     
                        //generate the tool tip here
                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
                  
                        var uniqueItemId=value.uniqueItemId;
                        var desText=value.supplierServiceDescription;
                  
                        if(desText.length > 80) {
                            desText = desText.substring(0, 80);
                            desText=desText + "...";
                        }
                  
                  
                        var appendServiceText='';
                  
                        appendServiceText=appendServiceText + '<div class="text_node" id="text_node">'
                            + '<span style="float: left;width:550px;">'
                            + '"' + value.supplierServiceName + '"'
                            + '</span>' 
                            + '<span style="float: right;padding-right: 10px">';                         		   
                         		 
                        var hcpcCodeList=value.hcpcCode;
                        if(!(hcpcCodeList==null || hcpcCodeList.length <= 0)){
                            appendServiceText=appendServiceText + 'HCPC:';
                            $.each(hcpcCodeList, function(index, hcpc){
                                appendServiceText=appendServiceText + hcpc;
                            });
                        }
                         		   
                         		   
                        appendServiceText=appendServiceText +  '</span>'
                            + '</div>'	                         
                            + '<div style="clear: both"></div>'
                            + '<div class="image_node" id="image_node' + uniqueItemId + '">'
                            + '<a href="#" onclick="revealDescription(\'' + uniqueItemId + '\')">'
                            + '<img src="<c:url value="images/plus_sign.png"/>">'
                            + '</a>&nbsp;&nbsp;' + desText + ''	
                            + '</div>'	                         
                            + '<div style="padding-left: 10px">'
                            + '<div style="background-color: #DFDFDF;width: 98%;display: none" id="image_description' + uniqueItemId + '">'
                            + '<table class="image_Desc" style="vertical-align: text-top">'
                            + '<tr>'
                            + '<td style="width: 20px;text-align: left;padding-bottom: 80px" rowspan="2" valign="top">'
                            + '<div style="margin-top: 5px;"><a href="#" onclick="hideDescription(\'' + uniqueItemId + '\')" id="hide_btn' + uniqueItemId + '"><img src="<c:url value="images/minus_sign.png"/>" ></a></div>'
                            + '</td>'	                                                
                            + '<td valign="top" style="text-align: left;padding-bottom:5px;padding-left:10px;width:525px;padding-top: 10px; "></div>'
                            + '' + value.supplierServiceDescription + '' 
                            + '</td>'
                            + '</tr>'	                                            
                            + '</table>'
                            + '</div>'
                            + '</div>';
                         		
                    
                        $('#clearServiceArea').append(appendServiceText);
                        $("#checkIfServiceCartIsEmpty").val(id);
                
                    });
                    }
                    getHide();
                }); 
			
            }
		
		
            function refreshServiceCart(){
                $.ajaxSetup({ cache: false });              
                $.getJSON("refreshServiceCart.htm", { 
                }, function(data) {
                    // create a new Tooltip and connect it to bar1 and bar4
                    $('#servicecartTable').html('');
                    $("#checkIfServiceCartIsEmpty").val('');
                 
                    if(!(data==null)){
	                    $.each(data, function(key, value) {                 	
	                    
	                        var id='divID' + value.uniqueItemId;
	               	  
	                        var text = value.supplierServiceName; 
	                        var mainText = text;
	                        if(text.length > 20) {
	                            text = text.substring(0, 20);
	                            text=text + "...";
	                        }
	                     
	                        $('#loading' + value.uniqueItemId).hide();
	                     
	                        $('#servicecartTable').append('<tr>'                             
	                            +  ' <td width="120px" colspan="2">'
	                            +  ' <a href="javascript:void(0)" title="' + mainText  + '" id="tooltip' + id + '"><div id=' + id + ' class="blue_hyperlink_cart">' + text + '</div>'                                    			
	                            +  ' </td>'
	                            +  ' <td><a href="#" style="color:#195771" onclick="removeServiceFromCart(\''  + value.uniqueItemId + '\' )"><img src="images/close_btn.png" alt="Delete"/></a></td>'
	                            +  ' </tr>'
	                            +  ' <tr>'
	                            +  ' <td colspan="3" align="center" valign="middle"><hr style="width:197px;" size="1px" color="#969696"/></td>'
	                            +  ' </tr>');
	                     
	                        $("#checkIfServiceCartIsEmpty").val(id);            
	                        //generate the tool tip here
	                        $('#tooltip' + id ).showTip({bindEvent: 'over',position: 'right'});
	
	                    });
                    }
                    getHide();
                });  
			
            }
          
			
			
            //--> 
        </script>

    </head>

    <!--BACKGROUND-->
    <body  style="background:url(images/Design.jpg) repeat;"  >
        <div id="back">
            <div id="page_wrapper">
                <div id="header_wrapper">

                    <!--HEADER & SEARCH-->
                    <tiles:insertAttribute name="header" />
                    <tiles:insertAttribute name="search" />
                </div>
                <div id="middle_wrapper">
                    <div id="left_wrapper">
                        <!--BID BLOCK & ACCOUNT-->

                        <tiles:insertAttribute name="bidblock" />
                        <div id="cartDiv">
                            <tiles:insertAttribute name="cart"/>
                        </div>
                        <div id="acctDiv">
                            <%--                             <tiles:insertAttribute name="account" /> --%>
                        </div>

                    </div>
                    <div id="right_wrapper">

                        <!--SEARCH RESULT-->
                        <tiles:insertAttribute name="body" />
                    </div>
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
