<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        

<!--	    <link rel="stylesheet" type="text/css" href="<c:url value="style/client_main_css.css"/>" /> -->
       
        <title>Welcome to Smart Channelling</title>   
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />      
        <link href="css/index_smartchanneling.css" rel="stylesheet" type="text/css" />	     
        <!--[if IE]>
	<link href="css/index_smartchanneling-ie.css" rel="stylesheet" type="text/css" />
        <![endif]-->
        <link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
           <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.2.min.js"></script>  
<!--       <style type="text/css">
       #company_name_area_mid a:HOVER {
               text-decoration: none;;
	
       }

        </style>-->
    </head>
   <body>
        <!-- Start new User Interface -->
   	<div class="mainContainer">
<div class="header">
    <c:url value="/j_spring_security_logout" var="logoutUrl"/>
  <div class="logo"><img src="images/logo.png" width="287" height="42" /></div>

  <div class="signin">
  	
        <div>
        <sec:authorize ifNotGranted="ROLE_USER">
            <div class="login">Members Login: </div>
			<form:form id="form2" name="form2" method="post" action="j_spring_security_check" params="['spring-security-redirect':request.forwardURL]">					
                                        <input type='hidden' name='spring-security-redirect' value='${spring_security_redirect}' />
                                        <table><tr>
                                                <td><input type="text" id="j_username" name="j_username" class="inputUsername"/></td>
                                                 <td><input type="password" name="j_password" id="j_password" class="inputUsername"/></td>
                                                  <td><input type="image" src="images/login.png" class="login-btn" height="31" />  </td>
                                            </tr></table>     
					
				</form:form>
			</sec:authorize>
			<sec:authorize  access="hasRole('ROLE_USER') and fullyAuthenticated">
                            <div class="login"></div>
                             <table><tr><td>
     <p style="color:#00BFFF;font-size:18px;padding-left: 20px;position: relative;top: 5px;"  >Welcome ${sessionScope.systemUser.userName}
			</td><td>	   <a href="${logoutUrl}" style="color: #B46F07;padding-left: 20px; margin-top: 5px;"><input name="" type="image" src="images/logout.png" /></a></p>
        </td> </tr></table>    
     
			</sec:authorize>
            </div>                            
<!--  	<div style="float:left; width:200px;"><input name="" type="text" class="inputUsername" value="User Name" /></div>
  	<div style="float:left; width:200px;"><input name="" type="password"  class="inputUsername" value="password" /></div>
    
  	<div class="logout"><input name="" type="image" src="images/login.png" /></div>-->
  <div class="clear"></div>
  </div>
  
  <div class="clear"></div>
</div>


<div class="main" >
			<!-- navigation -->
			<ul id="nav">

				<li><a href="#" style="width:78px;" class="home" >Home</a></li>
				<li><a href="#" style="width:105px;"  >About us</a></li>
				<li><a href="#" style="width:105px;"  > Hospitals</a></li>
				<li><a href="#" style="width:105px;" > Doctors</a></li>
				<li><a href="#" style="width:150px;"  >Specialization</a></li>
				<li><a href="#"  style="width:150px;" >Hospital Roles</a></li>
				<li><a href="#" style="width:105px;"  > CONTACT US</a></li>
			</ul>
		</div>



<div class="mainBody">
    <div class="flash"><div id="slider" class="nivoSlider">
                <img src="images/flash1.jpg" alt="" />
                <img src="images/flash2.jpg" alt="" title="" />
                <img src="images/flash3.jpg" alt="" />
            </div>
        


    <script type="text/javascript" src="js/jquery.nivo.slider.pack.js"></script>
    <script type="text/javascript">
    $(window).load(function() {
        $('#slider').nivoSlider();
    });
    </script>
</div>
    <div class="welcomDiv">
      <div class="welcome"><h1>Welcome to Smart Channeling</h1>
      <p>"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. </p>

<p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illueum fugiat quo voluptas nulla pariatur?"</p></div>
      <div class="newsDiv">
      	<div class="news">
          <div class="newsHd"><h1>Latest News</h1></div>
          <div class="newsMid">
            <p>architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluur aut odit aut fugit, sed quia consequuntur magni dolores eos quvitae dicta sunt explicabo. <br /><br />Nemo enimgit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. </p>
            <p><a href="#"><img src="images/seeMore.jpg" width="106" height="33" class="seemore" /></a></p>
          </div>
          <div class="newsBot"></div>
      	</div>
      </div>
      <div class="clear"></div>
    </div>
    
    
    
    <div><table width="100%" class="highlights" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th scope="col" width="25%"><img src="images/hospital.png" width="228" height="156" /></th>
    <th scope="col" width="25%"><img src="images/doctor.png" width="228" height="156" /></th>
    <th scope="col" width="25%"><img src="images/specilization.png" width="228" height="156" /></th>
    <th scope="col" width="25%"><img src="images/hospial-roles.png" width="228" height="156" /></th>
  </tr>
  <tr>
    <td><a href="javascript: void(0)">Hospitals</a></td>
    <td><a href="javascript: void(0)">Doctors</a></td>
    <td><a href="javascript: void(0)">Specialization</a></td>
    <td><a href="javascript: void(0)">Hospital Roles</a></td>
  </tr>
</table>
</div>
    
<!--end of mainBody--></div>
<!--end of mainContainer--></div>
<div class="footer">
	<div class="footerContent">
    	<div><table width="100%" border="0" cellspacing="0" cellpadding="0" class="footerTable">
  <tr>
    <th width="36%" height="54" scope="col"><h1>Secure Payment</h1></th>
    <th width="29%" scope="col"><h1>get connect</h1></th>
    <th width="35%" scope="col"><h1>Contact Info</h1></th>
  </tr>
  <tr>
    <td valign="top"><img src="images/payment.png" width="293" height="30" /></td>
    <td valign="top"><img src="images/social_network.png" width="205" height="16" border="0" usemap="#Map" /></td>
    <td valign="top"><p>Smart Channeling PLC, Lorem ipsum dolor sit amet, varius. Donec ulla,faucibus eu magna. Sri Lanka.<br />
    </p>
      <p>&nbsp; </p>

<p><strong>Hotline :</strong> +94 (011)9 999 999<br />
<strong>Fax :</strong> +94 (011)9 999 999<br />
<strong>Email :</strong> <a href="mailto:info@.smartchannelling.com">info@.smartchannelling.com</a><br />
<strong>Web :</strong> <a href="www.smartchannelling.com">www.smartchannelling.com</a></p></td>
  </tr>
</table>
</div>
    	<div class="copyright">Copyright © 2012 Smart Channelling.Com. All Rights Reserved.</div>
    </div>
</div>


<map name="Map" id="Map">
  <area shape="rect" coords="-3,0,16,16" href="www.twitter.com" target="_blank" />
  <area shape="rect" coords="27,0,43,15" href="www.facebook.com" target="_blank" />
  <area shape="rect" coords="53,1,70,15" href="www.linkedin.com" target="_blank" />
  <area shape="rect" coords="81,1,97,16" href="www.picasa.com" target="_blank" />
  <area shape="rect" coords="107,1,124,16" href="www.youtube.com" target="_blank" />
  <area shape="rect" coords="135,1,152,17" href="www.msn.com" target="_blank" />
  <area shape="rect" coords="162,1,178,16" href="www.skype.com" target="_blank" />
</map>
       <!-- End new User Interface -->
       
    
       
       
       
       
       
       
       
       
       
       
       
       
<!--       
       Header Background Part Starts 
<div id="header-bg">
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
	Header Contant Part Starts 
	<div id="header">
		<a href="index.html" style="color:#FCF7CC;font-size:26px;">Smart Channeling</a>
		Login Background Starts 
		<div id="login-bg" >
			Login Area Starts 
			
			<div id="login-area"  class="row">
			<sec:authorize ifNotGranted="ROLE_USER">
			<form:form id="form2" name="form2" method="post" action="j_spring_security_check" params="['spring-security-redirect':request.forwardURL]">
					<label>Members Login:</label>
					<input type='hidden' name='spring-security-redirect' value='${spring_security_redirect}'/>
					<input type="text" id="j_username" name="j_username"/>
					  <input type="password" name="j_password" id="j_password" />
					<input type="image" src="images/login-btn.gif" class="login-btn" alt="Login" title="Login" />
					<br class="spacer" />
				</form:form>
			</sec:authorize>
			<sec:authorize  access="hasRole('ROLE_USER') and fullyAuthenticated">
			<div id="company_name_area_left" class="col">&nbsp;</div>
			<div id="company_name_area_mid" class="col" style="width: 97%">
               <div class="col" style="width: 5%"> <img src="./images/login-icon.jpg" width="18" height="19" style="padding-left:10px; " /></div>
                 <div class="col" style="width: 90%"><p style="color:#FCF7CC;font-size:18px;padding-left: 40px;position: relative;top: -20px;"  >Welcome
               ${sessionScope.systemUser.userName}
				<a href="${logoutUrl}" style="color: #B46F07;padding-left: 30px;text-decoration: underline;">Logout</a></p></div></div>
			</sec:authorize>
			 <div id="company_name_area_right" class="col">&nbsp;</div>
			</div>
			   
			Login Area Ends 
		</div>
		Login Background Ends 
		<br class="spacer" />
	</div>
	Header Contant Part Ends 
</div>
Header Background Part Ends 
Navigation Background Part Starts 
<div id="navigation-bg">
	Navigation Part Starts 
	<div id="navigation">
		<ul class="mainMenu">
			<li><a href="#" class="selectMenu" title="Home">Home</a></li>
			<li><a href="#" title="About">About</a></li>
			<li><a href="#" title="Services">Services</a></li>
			<li><a href="#" title="Support">Support</a></li>
			<li><a href="#" title="Chat">Chat</a></li>
			<li><a href="#" title="History">History</a></li>
			<li class="noBg"><a href="#" title="Contact">Contact</a></li>
		</ul>
		<a href="#" class="signup" title="signup now"></a>
		<br class="spacer" />
		<ul class="subNav">
			<li class="noBg"><a href="#" title="Our Benefits">Our Benefits</a></li>
			<li><a href="#" title="What Our Future Plans">What Our Future Plans</a></li>
			<li><a href="#" title="Our Success">Our Success</a></li>
			<li><a href="#" title="Ratings">Ratings</a></li>
			<li><a href="#" title="Latest Blogs">Latest Blogs</a></li>
			<li><a href="#" title="News">News</a></li>
			<li><a href="#" title="Testimonials">Testimonials</a></li>
			<li><a href="#" title="Comments">Comments</a></li>
		</ul>
		<br class="spacer" />
	</div>
	Navigation Part Ends 
</div>
Navigation Background Part Ends 
Our Company Bacground Part Starts 
<div id="ourCompany-bg">
	Our Company Part Starts 
	<div id="ourCompany-part">
		<h2 class="ourCompany-hdr">Our Companies Main Features</h2>
		Our Company Left Part Starts 
		<div id="ourCompany-leftPart">
			<h2 class="faq-Hdr">Latest F.A.Q’s</h2>
			<ul class="ourCompany-list">
				<li>Nulla congue pretium elit. Integer enim risus, mollis.</li>
				<li>Eget, accumsan id, feugiat eu, velit. Sed molestie.</li>
				<li>lectus id nisi.</li>
			</ul>
			<h2 class="moreIdeas-Hdr">More Ideas About Us</h2>
			<ul class="ourCompany-list noBottomPadding">
				<li>Quisque laoreet, elit at tincidunt porta, massa torr.</li>
				<li>Porttitor magna, at vehicula pede dui id enim.</li>
				<li>Pellentesque rhoncus metus quis nulla. Donecllus.</li>
				<li>Metus, vehicula nec, scelerisque commodo.</li>
				<li>Egestas eget.</li>
			</ul>
		</div>
		Our Company Left Part Ends 
		Our Company Right Part Starts 
		<div id="ourCompany-rightPart">
			<h2 class="moreInfo-Hdr">More Informations</h2>
			<ul class="ourCompany-list noBottomPadding">
				<li><a href="#">Aenean viverra sapien a enim pellentesque</a></li>
			</ul>
			<p class="moreInfo-Text">Pellentesque id nunc at leo<br />
			vestibulum lobortis</p>
			<h2 class="searchUrl-Hdr">Search Our Url's</h2>
			<ul class="ourCompany-list noBottomPadding">
				<li><a href="#">www.,elit at tincidunt/porta.com</a></li>
				<li><a href="#">www.vehicula pede /a/dui id enim.com</a></li>
				<li><a href="#">www. quis nulla.com</a></li>
				<li><a href="#">www.scelerisque commodo.com</a></li>
			</ul>
		</div>
		Our Company Right Part Ends 
		<br class="spacer" />
	</div>
	Our Company Part Ends 
</div>
Our Company Bacground Part Ends 
Future Plans Part Starts 
<div id="futurePlan-bg">
	Future Plans Contant Part Starts 
	<div id="futurePlanContant">
		Projects 2007 Part Starts 
		<div id="projPart">
			<h2 class="proj-hdr">Projects <span>2007</span></h2>
			<ul class="pic">
				<li><a href="#"><img src="images/future-pic-1.jpg" alt="Pic 1" title="Pic 1" width="82" height="74" /></a></li>
				<li><a href="#"><img src="images/future-pic-2.jpg" alt="Pic 2" title="Pic 2" width="82" height="74" /></a></li>
				<li class="noRightMargin"><a href="#"><img src="images/future-pic-3.jpg" alt="Pic 3" title="Pic 3" width="82" height="74" /></a></li>
			</ul>
			<br class="spacer" />
			<h3 class="sub-hdr">We Have For This year:</h3>
			<p>Quisque laoreet, elit at tincidunt porta, massa torr Porttitor magna, at vehicula pede dui id enim. Pellentesque</p>
			<a href="#" class="more-btn" title="READ MORE"></a>
		</div>
		Projects 2007 Part Ends 
		Future Part Starts 
		<div id="futurePart">
			<h2 class="future-hdr">Future Plans</h2>
			<h3 class="future-subHdr">Sed semper, enim id fringilla posuere</h3>
			<p>mauris diam dignissim magna, id ornare libero quam innvallis erat eu lectus. Aenean bibendum facilisis ante.</p>
			<p>Pellentesque id nunc at leo vestibulum lobortis. Integer luctus leo non felis. Proin in justo. Donec sapien enim, porta quis, aliquam sit amet, condimentum nonummy, lorem. Nullam mi metus, cursus in, porta vel, fringilla et, orci. Integer sit amet quam id turpis ultrices</p>
			<img src="images/future-img.gif" alt="Image" title="Image" width="127" height="141" />
			<br class="spacer" />
		</div>
		Future Part Ends 
		<br class="spacer" />
	</div>
	Future Plans Contant Part Ends 
</div>
Footer Part Starts 
<div id="footer-bg">
	Footer Menu Part Starts 
	<div id="footer-menu">
		<ul class="footMenu">
			<li class="noDivider"><a href="#" title="Home">Home</a></li>
			<li><a href="#" title="About">About</a></li>
			<li><a href="#" title="Services">Services</a></li>
			<li><a href="#" title="Support">Support</a></li>
			<li><a href="#" title="Chat">Chat</a></li>
			<li><a href="#" title="History">History</a></li>
			<li><a href="#" title="Contact">Contact</a></li>
		</ul>
		<br class="spacer" />
		<p class="copyright">Copyright &copy;  All Rights Reserved</p>

	</div>
	Footer Menu Part Ends 
</div>
Footer Part Ends -->

   </body>
</html>
