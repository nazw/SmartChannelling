<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link rel="shortcut icon" type="image/x-icon" href="../images/favicon.ico" />
        <link rel="icon" type="image/png" href="../images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="<c:url value="style/client_main_css.css"/>" />   

        <title>Smart Channeling</title>     


    </head>
    <body>

        <!--Header Background Part Starts -->
        <div id="header-bg">
            <!--Header Contant Part Starts -->
            <div id="header">
                <a href="index.html" style="color:#FCF7CC;font-size:26px;">Smart Channeling</a>
                <!--Login Background Starts -->
                <div id="login-bg">
                    <!--Login Area Starts -->
                    <div id="login-area">
                        <form action="" method="post" name="Login" id="Login">
                            <label>Members Login:</label>
                            <input type="text" name="username" id="username" />
                            <input type="password" name="pass" id="pass" />
                            <input type="image" src="images/login-btn.gif" class="login-btn" alt="Login" title="Login" />
                            <br class="spacer" />
                        </form>
                    </div>
                    <!--Login Area Ends -->
                </div>
                <!--Login Background Ends -->
                <br class="spacer" />
            </div>
            <!--Header Contant Part Ends -->
        </div>
        <!--Header Background Part Ends -->
        <!--Navigation Background Part Starts -->
        <div id="navigation-bg">
            <!--Navigation Part Starts -->
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
            <!--Navigation Part Ends -->
        </div>
        <!--Navigation Background Part Ends -->
        <!--Our Company Bacground Part Starts -->
        <div id="ourCompany-bg">
            <!--Our Company Part Starts -->
            <div id="ourCompany-part" style="height:200px;">
                <h2 class="ourCompany-hdr">Error Page</h2>
                <p style="padding-top:30px;color:#2B0D0A;font-size:20px;font-family:'Times New Roman', Times, serif">The username or password you entered is incorrect.</p>
                <br class="spacer" />
            </div>
            <!--Our Company Part Ends -->
        </div>
        <!--Our Company Bacground Part Ends -->

        <!--Footer Part Starts -->
        <div id="footer-bg">
            <!--Footer Menu Part Starts -->
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
            <!--Footer Menu Part Ends -->
        </div>
        <!--Footer Part Ends -->

    </body>
</html>
