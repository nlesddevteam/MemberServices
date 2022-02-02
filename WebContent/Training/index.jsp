<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*"%>

<esd:SecurityCheck />
<%
  User usr = null;
  usr = (User) session.getAttribute("usr");  
  if(usr == null)
  {%>
  	 <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="This is a Secure Resource!<br>Please Login."/>
    </jsp:forward>
  <%} %> 


<html>
  <head>
    <title>Training Modules</title>    
 
  </head>
  <body>
  
<!-- TEACHER NAME UPDATE -->

<div class="container-fluid">		

<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;padding-top:5px;">	
				   
				  Below are a list of currently required Staff Training Modules for all district staff. 
				  Please take sometime to review and participate in these modules and follow all instructions to certify and complete your training.
				  </div>
</div>				  
<br/>
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4" style="font-size:12px;">   
            
            <div align="center" style="text-align:center;border:2px solid silver;padding:5px;border-radius: 25px;">
            <span style="font-size:14px;font-weight:bold;">CODE OF ETHICS &amp;<br/>CONDUCT TRAINING</span><br/>
            
            <img src="includes/img/codeethicsthumb.jpg" style="width:100%;max-width:300px;"><br/>
            
            <a href="codeofethics.jsp" class="btn btn-sm btn-primary">OPEN MODULE</a>
            
            </div>
            

    
   
   </div>
  				</div>
 </div>   
 <br/><br/>
<div align="center" style="padding-top:5px;padding-bottom:10px;"><a href="/MemberServices/navigate.jsp" class="btn btn-danger btn-sm">Exit to Member Services</a></div>
   
  </body>
</html>
