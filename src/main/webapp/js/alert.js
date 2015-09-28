$(function() {
    var button = $('#loginButton');
    var box = $('#loginBox');
    var form = $('#loginForm');
    button.removeAttr('href');
    button.mouseup(function(login) {
    	var x=$('#alertCount').val();
    	if(x>0){
	        box.toggle();
	        button.toggleClass('active');
    	}else if(x==0){
    		box.toggle();
    	}else{
    		box.toggle();
    	}
    });
    form.mouseup(function() { 
        return false;
    });
    $(this).mouseup(function(login) {
        if(!($(login.target).parent('#loginButton').length > 0)) {
        	var x=$('#alertCount').val();
        	if(x>0){
        		button.removeClass('active');
        	}
            box.hide();
        }
    });
});


