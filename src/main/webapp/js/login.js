// JavaScript Document
var scnWid=0;
var scnHei=0;
function demen(element,base, width, height){
    

	if (self.innerHeight) // all except Explorer
	{
		scnWid = self.innerWidth;
		scnHei = self.innerHeight;
	}
	else if (document.documentElement && document.documentElement.clientHeight)
		// Explorer 6 Strict Mode
	{
		scnWid = document.documentElement.clientWidth;
		scnHei = document.documentElement.clientHeight;
	}
	else if (document.body) // other Explorers
	{
		scnWid = document.body.clientWidth;
		scnHei = document.body.clientHeight;
	}
	
	document.getElementById(element).style.left = (scnWid- width)/2 +'px';
	document.getElementById(element).style.top = (scnHei- height)/2 +'px';
	document.getElementById(element).style.display='block';
	
	document.getElementById(base).style.width = scnWid+'px';
	document.getElementById(base).style.height = scnHei+'px';
	document.getElementById(base).style.display='block'
}

function close_box(base,elem){
	document.getElementById(base).style.display='none';
	document.getElementById(elem).style.display='none';
}


