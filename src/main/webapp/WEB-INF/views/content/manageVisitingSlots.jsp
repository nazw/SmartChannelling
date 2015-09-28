<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>

<style type="text/css">


    .slot_grey{
        width: 90px;
        height: 60px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CFCFCF;
        float: left;
    }
    .slot_green{
        width: 90px;
        height: 60px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #58A222;
        float: left;
    }
    .slot_red{
        width: 90px;
        height: 60px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CC6600;
        float: left;
    }
    .slot_grey_dummy{
        width: 40px;
        height: 20px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CFCFCF;
    }
    .slot_green_dummy{
        width: 40px;
        height: 20px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #58A222;
    }
    .slot_red_dummy{
        width: 40px;
        height: 20px;
        padding: 0px;
        margin: 0px;
        border: 1px solid #ffffff;
        background-color: #CC6600;
    }

    .time_slot{
        padding-top: 23px;padding-left: 18px;
    }

    #timeSlotAllocateArea{
        padding: 0px;
        margin: 0px;
        height: auto;

    }

</style>

<script language="JavaScript">

    $(document).ready(function() {


    });  
    
    

     
    function allocateTimeSlot(timeSlotId) {  

        var slotValue=$('#'+timeSlotId).attr('class');

        if(slotValue.indexOf("slot_red") >= 0){
            $('#'+timeSlotId).removeClass('slot_red');
            $('#'+timeSlotId).addClass('slot_grey'); 
    
        }
        if(slotValue.indexOf("slot_green") >= 0){
            $('#'+timeSlotId).removeClass('slot_green'); 
            $('#'+timeSlotId).addClass('slot_grey');   
        }
  
        if(slotValue.indexOf("slot_grey") >= 0){            
            $('#'+timeSlotId).addClass('slot_grey');   
        }


    }
  

    

     
</script>





   <div id="timeSlotAllocateArea" class="template_row">


        <div class="row" style="padding-bottom:10px;background-color: #C1B664;height: 30px;width: 90%" id="timeSlotBookingHeader">       
            <div class="col " ><p style="font-weight: bold;font-size: 13px;padding-top: 8px;padding-left: 20px;">Manage Visiting Times</p></div>  
        </div>

        <div style="height:35px;min-height:35px;line-height:35px;" class="template_row">&nbsp;</div>

        <div class="row">
        <div class="slot_white" onclick="allocateTimeSlot(visitingSlotsId )" style="cursor: pointer;" id="this.visitingSlotsId"><p class="time_slot">hours : minutes</p></div>

        </div>


        <div style="height:35px;min-height:35px;line-height:35px;" class="template_row">&nbsp;</div>

        <div class="row" style="width: 75%">
            <div class="col " style="width: 25%" ><span class="col slot_grey_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Cancel</p></div>
            <div class="col " style="width: 25%"><span class="col slot_green_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Available</p></div>
            <div class="col " style="width: 25%"><span class="col slot_red_dummy">&nbsp;</span><p style="padding-left: 50px;padding-top: 5px;">Reserved</p></div>
        </div>
    </div>