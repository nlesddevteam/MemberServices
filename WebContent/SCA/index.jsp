<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.school.*" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
	User usr = (User) session.getAttribute("usr");
  
  Iterator sch_iter = SchoolDB.getSchools().iterator();
  School sch = null;
%>



<html>
<head>
<title>Eastern School Disctrict - Student Comprehensive Assessment</title>
<link href="includes/css/sca.css" rel="stylesheet" type="text/css">
</head>
<body>
<esd:SecurityCheck permissions="SCA-ADMIN-VIEW" />
<table width="755" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#FFFFFF" style="border: thin solid #00407A;">
<tr bgcolor="#00407A">
	<td colspan="2">
	<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0">
<tr>
	<td><div align="left" class="toptext">Welcome <%=usr.getPersonnel().getFirstName()%></div></td>
	<td><div align="right" class="toptext"><a href="index.jsp" class="topmenu">Home</a> | <!-- <a href="index.jsp" class="topmenu">Logout</a> |--> <a href="index.jsp" class="topmenu">Help</a>&nbsp;</div></td>
</tr>
</table>
	</td>
</tr>
<tr>
	<td colspan="2"><img src="includes/images/header.gif" alt="Eastern School District Student Comprehensive Assessment" width="755" height="113" border="0"></td>
</tr>
<tr>
	<td width="175" background="includes/images/sidemenubkg.gif" valign="top">
	<table width="95%" cellspacing="2" cellpadding="2" border="0">
<tr><td valign="top">
  <br>
  <a href="sca_admin.jsp" class="sidemenu" style="color:#B10022;font-weight:bold;">&middot; Administration Options</a><br><br>
  
	</td></tr></table>
	</td>
	<td width="*">
	<table width="100%" border="0" cellspacing="2" cellpadding="2" class="maintable">
<tr><td>
	<!-- Mainbody content here -->
	
  <table width="100%" cellpadding="0" cellspacing="0" id="assessment_list" border="0">
  <TR>
	<!--- Menu Items / Sidebar stuff here -->
  <% 
     int num_columns = 3;
     int cnt = -1;
     
     while(sch_iter.hasNext()){
      sch = (School) sch_iter.next();
      
      if(sch.getSchoolID() > 270)
        continue;
      
      if((++cnt % num_columns) == 0)
        out.println("</TR><TR>");
      
      out.println("<TD><a href='school_waitlist.jsp?sid=" + sch.getSchoolID() + "' class='sidemenu'>&middot; " 
        + sch.getSchoolName().replaceAll(" Region", "") + "</a></TD>");
    }
  %>
  </TR>
	<!-- End Sidebar -->
	
	
	
	
	
	
	
	
	
	<!--End Mainbody -->
	</td></tr></table>
	</td></tr>
<tr bgcolor="#00407A">
	<td colspan="2"><div align="center" class="copyright">&copy; 2007 Eastern School District. All Rights Reserved.</div></td>
</tr>
</table>



</body>
</html>
