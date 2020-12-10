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
    <title>Member Services - OHS</title>    
 
  </head>
  <body>
  
<!-- TEACHER NAME UPDATE -->

<div class="container-fluid">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;">   
            

Occupational Health and Safety Information Shared with Schools and District Staff can be found below:
<br/><br/>
   <ul>  
   
  		<li><a href="https://sites.google.com/nlesd.ca/ohstraining2021/safety-orientation" target="_blank">Orientation Training 2020-2021</a>
		<li> <a href="https://sites.google.com/nlesd.ca/ohsorientation/home" target="_blank">NLESD OHS Website</a>   
   		
   </ul>
    
   
   </div>
  				</div>
 </div>   
 <br/><br/>
<div align="center" style="padding-top:5px;padding-bottom:10px;"><a href="/MemberServices/navigate.jsp" class="btn btn-danger btn-sm">Exit to Member Services</a></div>
   
  </body>
</html>
