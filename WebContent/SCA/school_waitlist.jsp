<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.school.*,
                  com.esdnl.sca.model.bean.*,
                  com.esdnl.sca.dao.*" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
	User usr = (User) session.getAttribute("usr");
  
  Iterator sch_iter = SchoolDB.getSchools().iterator();
  School sch = SchoolDB.getSchool(Integer.parseInt(request.getParameter("sid")));
  Assessment[] assessments = SCAManager.getAssessmentBeans(sch);
  
  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
  <!--
	<td width="210" background="includes/images/sidemenubkg.gif">
	<table width="95%" cellspacing="2" cellpadding="2" border="0">
<tr><td>
  <br>
  <%School tmp = null;
    while(sch_iter.hasNext()){
      tmp = (School) sch_iter.next();
      
      if(tmp.getSchoolID() > 270)
        continue;
  %>
    <a href="school_waitlist.jsp?sid=<%=tmp.getSchoolID()%>" class="sidemenu">&middot; <%=tmp.getSchoolName().replaceAll(" Region", "")%></a><br><br>
	<%}%>
	</td></tr></table>
	</td>
  -->
	<td width="100%" valign="top">
	<table width="95%" border="0" cellspacing="2" cellpadding="2" class="maintable">
  <tr><td width="100%" valign="top" style="padding:5px;"> 
  <h2 class='body_title'><%=sch.getSchoolName()%></h2>
  <div width="100%" align="left"><a href="addToWaitlist.html?sid=<%=sch.getSchoolID()%>">Add Student To Waitlist</a></div>
	<!-- Mainbody content here -->
	<%if(assessments.length > 0){%>
    <div width="100%" style='padding-top:10px;'>
      <table width="100%" cellpadding="0" cellspacing="0" id="assessment_list" border="0">
        <TR><TH width="25%">Student Name</TH><TH width="25%">MCP#</TH><TH width="25%">Referral Date</TH><TH width="25%">Status</TH></TR>
        <%for(int i=0; i < assessments.length; i++){%>
          <TR style="padding-top:5px;">
            <TD><a href="viewAssessment.html?id=<%=assessments[i].getId()%>"><%=assessments[i].getStudentName()%></a></TD>
            <TD><%=assessments[i].getStudentMCP()%></TD>
            <TD><%=sdf.format(assessments[i].getReferralDate())%></TD>
            <TD><%=assessments[i].getStatus().getDescription()%></TD>
          </TR>
        <%}%>
      </table>
    </div>
  <%}else{%>
    No assessments currently waitlisted.
  <%}%>
	<!--End Mainbody -->
	</td></tr></table>
	</td></tr>
<tr bgcolor="#00407A">
	<td colspan="2"><div align="center" class="copyright">&copy; 2007 Eastern School District. All Rights Reserved.</div></td>
</tr>
</table>



</body>
</html>
