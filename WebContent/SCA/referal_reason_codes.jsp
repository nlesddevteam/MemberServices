<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,
                  com.esdnl.sca.dao.*,
                  com.esdnl.sca.model.bean.*"
         isThreadSafe="false"%>
                  
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
  
  ReferralReason[] reasons = SCAManager.getReferralReasonBeans();
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
	<td width="210" background="includes/images/sidemenubkg.gif">
	<table width="95%" cellspacing="2" cellpadding="2" border="0">
<tr><td height="480" valign="top">
  <br>
  <span class="sidemenu" style="color:#B10022;font-weight:bold;">Administration Options</span><br><br>
  <!--- Menu Items / Sidebar stuff here -->
    <jsp:include page='admin_options_menu.jsp' flush="true" />
	<!-- End Sidebar -->
	</td></tr></table>
	</td>
	<td width="545" valign="top">
	<!-- Mainbody content here -->
	<table width="95%" border="0" cellspacing="2" cellpadding="2" class="maintable">
  <tr><td valign="top" style="padding:5px;"> 
  <h2 class='body_title'>Referral Reason Codes</h2>
  <div width="100%" align="right"><a href="addReferralReasonCode.html">Add Referral Reason Code</a></div>
	<!-- Mainbody content here -->
	<%if(reasons.length > 0){%>
    <table with="100%" cellpadding="0" cellspacing="0">
      <TR><TH width="30">ID</TH><TH width="*">Description</TH></TR>
      <%for(int i=0; i < reasons.length; i++){%>
        <TR>
          <TD><%=reasons[i].getId()%></TD>
          <TD><%=reasons[i].getDescription()%></TD>
        </TR>
      <%}%>
    </table>
  <%}else{%>
    No referral reason codes found.
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
