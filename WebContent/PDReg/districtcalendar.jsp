<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"
         isThreadSafe="false"%>
         
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-VIEW" />

<html>
  <head>
  	<title>Members Admin - Members Services/Newfoundland &amp; Labrador English School District</title>
  	<script type="text/javascript" src="js/jquery-1.6.1.min.js"></script>
  	<script type="text/javascript">
  		$('document').ready(function() {
  			$('#cal_main').attr('src', 'viewMonthlyCalendar.html');
  		});
  	</script>
  </head>
  <frameset framespacing="0" border="0" rows="100%,*" frameborder="no">
      <frame id='cal_main' name="cal_main" src="calendar_content.jsp" scrolling="auto" marginwidth="0" marginheight="0" noresize>
      <frame id='cal_hidden' name="cal_hidden" src=""  marginwidth="0" marginheight="0" noresize>
  </frameset>
  <noframes>
  	<body>
    	<p>This page uses frames, but your browser doesn't support them.</p>
    </body>
  </noframes>
</html>