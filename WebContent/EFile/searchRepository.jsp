<%@ page language="java"
        session="true"
         import="com.awsd.security.*,
                 com.awsd.efile.*,
                 com.awsd.school.*,
                 java.util.*"%>

<%!
  User usr = null;
  DocumentTypes types = null;
  DocumentType type = null;
  Subjects subjects = null;
  Subject s = null;
  Grades grades = null;
  Courses courses = null;
  Course c = null;
  Grade g = null;

  Iterator iter = null;

  boolean isUnrestricted = false;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.jsp"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.jsp">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
  
  types = new DocumentTypes();
  subjects = new Subjects();
  isUnrestricted = usr.getUserPermissions().containsKey("EFILE-UNRESTRICTED-SEARCH");
  if(isUnrestricted)
  {
    grades = new Grades(true);
    courses = new Courses(true);
  }
  else
  {
    grades = new Grades(usr);
    courses = new Courses(usr);
  }
%>

<html>
<head>
<title>E-File Repository - Member Services/Avalon West School District</title>

<link rel="stylesheet" href="css/e-file.css">

<script language="JavaScript" src="js/common.js"></script>

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
<BR>
<a href="EFileRepositoryChooser.html" class="navigation">home</a>&nbsp;<img src="images/nav_arrow.gif">&nbsp;search repository<BR>
<img src="images/search_title.gif"><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left">
Search Repository is where you can find exams, assignments, and lesson plan resources that have been imported by other teachers around the district.<BR><BR>

<b>You can search by any or all of the following fields.</b><BR><BR>

<form name="searchRepository" action="searchResults.html" method="post">

<table width="80%" cellpadding="10" cellspacing="0" style="border-style: solid; border-width: 2px; border-color:#FEF153;">
<tr>
<td width="80%" valign="top" align="left" colspan="2">
<span class="header2">Search By Date</span><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
<select name="searchByDate"  multiple style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
  <option value="0">Today</option>
  <option value="1">Yesterday</option>
  <option value="7">Last Week</option>
  <option value="31">Last Month</option>
  <option value="365">Last Year</option>
</select>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" colspan="2">
<span class="header2">Search By Keyword(s)</span><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
<input type="text" name="searchByKeyword" size="40" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" colspan="2">
<span class="header2">Search By Document Type</span><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
<select name="documentType"  multiple style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
  <% iter = types.iterator();
     while(iter.hasNext())
     {
        type = (DocumentType) iter.next();
  %>    <option value=<%=type.getDocumentTypeID()%>><%=type.getDocumentTypeName()%></option>
  <% } %>
</select>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" colspan="2">
<span class="header2">Search By Grade Level</span><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
<select name="gradeLevel"  multiple style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
  <%iter = grades.iterator();  
    while(iter.hasNext())
    {   
      g = (Grade) iter.next();
  %>  <option value="<%=g.getGradeID()%>"><%=g.getGradeName()%></option>
  <% } %>
</select>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" colspan="2">
<span class="header2">Search By Document Subject</span><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
<select name="documentSubject" multiple style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
  <% iter = subjects.iterator();
     while(iter.hasNext())
     {
        s = (Subject) iter.next();
  %><option value=<%=s.getSubjectID()%>><%=s.getSubjectName()%></option>
  <% } %>
</select>
</td>
</tr>
<%iter = courses.iterator();
  if(iter.hasNext())
  {
%>  <tr id="crs-header">
      <td width="80%" valign="top" align="left" colspan="2">
        <span class="header2">Search By Document Course</span><BR>
      </td>
    </tr>
    <tr id="crs-footer">
      <td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
        <select name="documentCourse" size="10" multiple style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
          <% while(iter.hasNext())
            {
              c = (Course) iter.next();
          %><option value=<%=c.getCourseID()%>><%=c.getCourseName()%></option>
          <% } %>
        </select>
      </td>
    </tr>
<% } %>
<tr>
<td width="80%" valign="top" align="left" colspan="2">
<img src="images/spacer.gif" width="1" height="5"><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="right" colspan="2" bgcolor="#F5F5EF">
<input type="submit" name="search" value="Search"><BR>
</td>
</tr>
<tr>
<td width="80%" valign="top" align="left" colspan="2">
<img src="images/spacer.gif" width="1" height="5"><BR>
</td>
</tr>
</table>
</form>
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