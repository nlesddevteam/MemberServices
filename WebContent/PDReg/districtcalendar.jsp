<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"
         isThreadSafe="false"%>
         
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-VIEW" />

<html>
  <head>
  	<title>NLESD PD Calendar</title>
  	<meta http-equiv="refresh" content="0;url=viewMonthlyCalendar.html" />
  
  </head>
 <body>
 <div id="loadingSpinner" style="z-index:9999;" ><div id="spinner"><img src="/MemberServices/PDReg/includes/img/loading.gif" title="Loading Events...please wait." class="spinnerSize" border=0><br/><span id="spinnerText">Loading events, please wait...</span></div></div>


 </body>
</html>