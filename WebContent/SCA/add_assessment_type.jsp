<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,
                  com.esdnl.sca.dao.*,
                  com.esdnl.sca.model.bean.*"
         isThreadSafe="false"%>
                  
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
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
  <h2 class='body_title'>Add Assessment Type</h2>
	<!-- Mainbody content here -->
   <%if(request.getAttribute("msg") != null){%>
    <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td class="messageText"><%=(String)request.getAttribute("msg")%></td>
        </tr>
      </table>
    </p>
  <%}%>
	<form id="frmWaitListAdd" action="addAssessmentType.html" method="post">   
    <input type='hidden' name='op' value='ADD'>
    
    <table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding:5px;">
      <tr>
        <td colspan="2" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Assessment Type Name</td>
        <td>
            <input type="text" name="type_name" id="type_name" style="width:175px;" class="requiredInputBox" value="">
        </td>
      </tr>
      
      <tr>
        <td colspan="2">
          <input type="submit" value="Add">
        </td>
      </tr>
    </table>
  </form>
	<!--End Mainbody -->
	</td></tr></table>
	</td></tr>
<tr bgcolor="#00407A">
	<td colspan="2"><div align="center" class="copyright">&copy; 2007 Eastern School District. All Rights Reserved.</div></td>
</tr>
</table>



</body>
</html>
