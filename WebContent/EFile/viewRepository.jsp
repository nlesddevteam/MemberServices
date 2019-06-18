<%@ page language="java"
        session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}%>

<html>
<head>
<title>E-File Repository - Member Services/Avalon West School District</title>

<link rel="stylesheet" href="css/e-file.css">

</head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
<center>
<jsp:include page="efile_menu.jsp" flush="true"/>
<table width="100%" cellpadding="10" cellspacing="0" border="0">
<tr>
<td width="100%" valign="top">

<table width="80%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="80%" valign="top" align="left">
<img src="images/welcome_title.gif"><BR><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left">
E-File Repository is an online storage and retrieval application that allows teachers to submit a digital copy of exams, assignments, and lesson plans.<BR><BR>

<table width="80%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="40%" align="center" valign="middle">
<a href="importDocument.html"><img src="images/step_one_01.gif" onMouseOver="src='images/step_one_02.gif'" onMouseOut="src='images/step_one_01.gif'" alt="Import Documents" border="0"></a><BR>
</td>
<td width="40%" align="center" valign="middle">
<a href="searchEFileResources.html"><img src="images/step_two_01.gif" onMouseOver="src='images/step_two_02.gif'" onMouseOut="src='images/step_two_01.gif'" alt="Search Repository" border="0"></a><BR>
</td>
</tr>
</table>

<img src="images/spacer.gif" width="1" height="100"><BR>
</td>
</tr>
</table>


</td>
</tr>
</table>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="100%" height="28" align="left" valign="bottom" background="images/footer_bg.gif">
<img src="images/footer.gif"><BR>
</td>
</tr>
</table>
</center>


</body>
</html>