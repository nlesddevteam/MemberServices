<%@ page language="java"
         session="true"
         import="com.awsd.ppgp.*,com.awsd.security.*,
                 java.text.*,
                 java.util.*"
        isThreadSafe="false"%>
        
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>
<%
	response.sendRedirect("/MemberServices/memberServices.html");
%>
<esd:SecurityCheck permissions='PPGP-VIEW' />

<%
	User usr = (User) session.getAttribute("usr");
  	HashMap<String, PPGP> ppgps = PPGPDB.getPPGPMap(usr.getPersonnel());
%>

<html>
  <head>

    <title>Professional Learning Plan</title>
   
  </head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b><%= PPGP.getCurrentGrowthPlanYear()%> Learning Plan System</b></div>
      			 	<div class="panel-body">	
  
  Welcome to the <%= PPGP.getCurrentGrowthPlanYear()%> Learning Plan System.
  
  <br/><br/>To begin choose a PLP to view from your Plan Archive above or create a new PLP by clicking the Learning Plan(s) link.
  If you do not have your reflections completed from previous year(s) the option to create a plan will not be available until you complete your self reflection. 
  Check your previous plans under Plan Archive to make sure they are complete. 
  <br/><br/>
  <%if(ppgps.containsKey(PPGP.getCurrentGrowthPlanYear())){
	            					
	            					if(!ppgps.containsKey(PPGP.getNextGrowthPlanYear())){
		            				PPGP ppgp = ppgps.get(PPGP.getCurrentGrowthPlanYear());
		            				if(!ppgp.isSelfReflectionComplete()) { %>	
 									<div class="alert alert-danger" style="text-align:center;"><b>NOTICE:</b><br/> You will be able to create your <%=PPGP.getNextGrowthPlanYear()%> Professional Learning Plan (PLP) after you complete the self reflections in your <%=ppgp.getSchoolYear()%> Professional Learning Plan. Check your Plan Archive above to complete the <%=ppgp.getSchoolYear()%> Plan.</div>
  									<%}}} else {
  									if(ppgps.containsKey(PPGP.getPreviousGrowthPlanYear())){
									PPGP ppgp = ppgps.get(PPGP.getPreviousGrowthPlanYear());
									if(!ppgp.isSelfReflectionComplete()) { %>
	  								<div class="alert alert-danger" style="text-align:center;"><b>NOTICE:</b><br/>You will be able to create your <%=PPGP.getCurrentGrowthPlanYear()%> Professional Learning Plan (PLP) after you complete the self reflections in your <%=ppgp.getSchoolYear()%> Professional Learning Plan. Check your Plan Archive above to complete the <%=ppgp.getSchoolYear()%> Plan.</div>
 <%}}} %>
  
  </div></div></div>  
  </body>
</html>