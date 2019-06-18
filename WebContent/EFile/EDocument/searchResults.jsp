<%@ page language="java"
        session="true"
         import="com.awsd.security.*,
                 com.awsd.efile.*,
                 com.awsd.efile.edocument.*,
                 com.awsd.efile.edocument.Document,
                 com.awsd.efile.edocument.DocumentType,
                 com.awsd.school.*,
                 java.util.*,
                 com.awsd.servlet.*,
                 java.io.*,
                 java.text.*"%>

<%!
  User usr = null;
  DocumentType type = null;
  Document doc = null;
  Subject s = null;
  Grade g = null;
  Course c = null;
  Vector page = null;
  SearchResults results = null;
  Iterator iter = null;
  String color_on;
  String color_off;
  int page_num;
  boolean canDelete = false;
  SimpleDateFormat sdf = null;
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
<%}
  results = (SearchResults) session.getAttribute("results");
  page_num = Integer.parseInt(request.getParameter("page"));

  canDelete = usr.getUserPermissions().containsKey("EFILE-DELETE-DOCUMENT");
  color_off = "#F5F5EF";
  color_on = "#FEF153";

  sdf = new SimpleDateFormat("dd-MMM-yyyy");
%>
<html>
<head>
<title>E-File Repository - Member Services/Eastern School District</title>

<link rel="stylesheet" href="../css/e-file.css">

<script language="JavaScript" src="../../js/common.js"></script>

</head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">

<jsp:include page="edocument_menu.jsp" flush="true"/>


<table width="100%" cellpadding="10" cellspacing="0" border="0">
<tr>
<td width="100%" valign="top">

<table width="80%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="100%" valign="top" align="left">
<BR>
<a href="../EFileRepositoryChooser.html" class="navigation">home</a>&nbsp;<img src="../images/nav_arrow.gif">&nbsp;search repository<BR>
<img src="../images/search_results_title.gif"><BR>
</td>
</tr>
<tr>
<td width="100%" valign="top" align="left">
Click on the <img src="../images/acrobat.gif" valign="middle" border="0"> icon to the right of the document you want to view, download or print.<BR><BR>
<table width="100%">
<tr>
<td width="50%" align="left">
Results: <b><%=results.getDocumentCount()%></b> resources found.
</td>
<td width="50%" align="right">
<%if(!results.isEmpty() && (results.getPageCount() > 1)) {%>
<font size="1" style="font-weight:bold;">[Page: 
<%
  if(page_num > 1)
  {
%>  <Font color="#000000" style="font-weight:bold;">
	<a href='searchResults.jsp?page=<%=page_num-1%>' class="navigation_small">Prev</a>
    </font>&nbsp;
<%} 

  for(int i = 1; i <= results.getPageCount(); i++) 
  { 
    if(i == page_num)
    {
%>    <Font color="#FF0000" style="font-weight:bold;"><%=i%></font>
<%  }
    else
    {
%>    <Font color="#000000" style="font-weight:bold;">
	<a href='searchResults.jsp?page=<%=i%>' class="navigation_small"><%=i%></a>
      </font>
<%  }
%>  &nbsp;
<%
  }

  if(page_num < results.getPageCount())
  {
%>  <Font color="#000000" style="font-weight:bold;">
	<a href='searchResults.jsp?page=<%=page_num+1%>' class="navigation_small">Next</a>
    </font>
<%}%>
]</font>
<%}%>
</td>
</tr>
</table>
<br>
<table width="100%" cellpadding="5" cellspacing="1" style="border-style: solid; border-width: 2px; border-color:#FEF153;">
<tr>
<td width="5%" valign="top" align="left" colspan="1">
  &nbsp
</td>
<td width="12%" valign="top" align="left" colspan="1">
  <b>Date</b><BR>
</td>
<td width="15%" valign="middle" align="left" colspan="1">
  <b>Document Type</b><BR>
</td>
<td width="15%" valign="middle" align="left" colspan="1">
  <b>Grade Level</b><BR>
</td>
<td width="23%" valign="middle" align="left" colspan="1">
  <b>Subject</b><BR>
</td>
<td width="25%" valign="middle" align="left" colspan="1">
  <b>Course</b><BR>
</td>
<td width="5%" valign="middle" align="left" colspan="1">
  &nbsp
</td>
</tr>
<% if(results.isEmpty()) { %>
<tr>
  <td width="5%" valign="top" align="left" colspan="1" bgcolor="#F5F5EF">
    &nbsp
  </td>
  <td colspan="5" valign="top" align="left" bgcolor="#F5F5EF">
    <font color="#FF0000" style="font-weight:bold;">No Resources found.</font>
  </td>
</tr>
<% } else {
  iter = results.getPage(page_num);
  while(iter.hasNext()) 
  {
    doc = (Document) iter.next();
%>
<tr id="<%="R"+doc.getDocumentID()%>" style="background-color:<%=color_off%>;"
    onmouseover="toggleTableRowHighlight('<%="R"+doc.getDocumentID()%>', '<%=color_on%>');"
    onmouseout="toggleTableRowHighlight('<%="R"+doc.getDocumentID()%>', '<%=color_off%>');">
<td valign="middle" align="middle" colspan="1">
  <table width="100%">
    <tr>
      <td>
        <img src="../images/acrobat.gif" border="0" style="cursor:hand;"
          onmouseover="src='../images/acrobat_on.gif';"
          onmouseout="src='../images/acrobat.gif';"
          onclick="openWindow('ResultDocument','<%="docs/" + doc.getDocumentID() + ".pdf"%>', screen.width, screen.height-55, 0);"><BR>
      </td>
      <% if(new File(ControllerServlet.EFILE_CONVERT_OUTPUT_DIR + "/" + doc.getDocumentID() + ".doc").exists()) {%>
      <td>
        <img src="../images/word.gif" border="0" style="cursor:hand;"
          onmouseover="src='../images/word_on.gif';"
          onmouseout="src='../images/word.gif';"
          onclick="openWindow('ResultDocument','<%="docs/" + doc.getDocumentID() + ".doc"%>', screen.width, screen.height-55, 0);"><BR>
      </td>
      <%}%>
      <% if(new File(ControllerServlet.EFILE_CONVERT_OUTPUT_DIR + "/" + doc.getDocumentID() + ".wpd").exists()) {%>
      <td>
        <img src="../images/wordperfect.gif" border="0" style="cursor:hand;"
          onmouseover="src='../images/wordperfect_on.gif';"
          onmouseout="src='../images/wordperfect.gif';"
          onclick="openWindow('ResultDocument','<%="docs/" + doc.getDocumentID() + ".wpd"%>', screen.width, screen.height-55, 0);"><BR>
      </td>
      <%}%>
      <% if(new File(ControllerServlet.EFILE_CONVERT_OUTPUT_DIR + "/" + doc.getDocumentID() + ".ppt").exists()) {%>
      <td>
        <img src="../images/powerpoint.gif" border="0" style="cursor:hand;"
          onmouseover="src='../images/powerpoint_on.gif';"
          onmouseout="src='../images/powerpoint.gif';"
          onclick="openWindow('ResultDocument','<%="docs/" + doc.getDocumentID() + ".ppt"%>', screen.width, screen.height-55, 0);"><BR>
      </td>
      <%}%>
    </tr>
  </table>
</td>
<td valign="middle" align="left" colspan="1">
<%=sdf.format(doc.getUploadDate())%><BR>
</td>
<td valign="middle" align="left" colspan="1">
<%=doc.getDocumentType().getDocumentTypeName()%><BR>
</td>
<td valign="middle" align="left" colspan="1">
<%=doc.getGrade().getGradeName()%><BR>
</td>
<td  valign="middle" align="left" colspan="1">
<%=doc.getSubject().getSubjectName()%><BR>
</td>
<td valign="middle" align="left" colspan="1">
<%if (doc.getCourse() != null) { %>
  <%= doc.getCourse().getCourseName()%><BR>
<%} else {%>
  &nbsp;<BR>
<%}%>
</td>
<td valign="middle" align="left" colspan="1">
<%if (canDelete || (doc.getPersonnel().getPersonnelID() == usr.getPersonnel().getPersonnelID())) { %>
  <img src="../images/delete_01.gif" border="0" style="cursor:hand;"
    onmouseover="src='../images/delete_02.gif';"
    onmouseout="src='../images/delete_01.gif';"
    onclick="document.location.href='deleteDocument.html?id=<%=doc.getDocumentID()%>';">
<%} else {%>
  &nbsp;
<%}%>
</td>
</tr>
<!--
<tr>
  <td>&nbsp;</td>
  <td colspan="">Keywords:
  <% 
    String kws[] = doc.getKeywords();
    for(int i=0; i < kws.length; i++)
    {
  %>  <%=kws[i]%>
  <%}%>
  </td>
</tr>
-->
<%} 
  if(!results.isEmpty() && (results.getPageCount() > 1)) {
%>
<tr>
<td colspan="6" align="center" valign="middle">
  [Page: 
<%
  if(page_num > 1)
  {
%>  <Font color="#000000" style="font-weight:bold;">
	<a href='searchResults.jsp?page=<%=page_num-1%>' class="navigation">Prev</a>
    </font>&nbsp;
<%} 

  for(int i = 1; i <= results.getPageCount(); i++) 
  { 
    if(i == page_num)
    {
%>    <Font color="#FF0000" style="font-weight:bold;"><%=i%></font>
<%  }
    else
    {
%>    <Font color="#000000" style="font-weight:bold;">
	<a href='searchResults.jsp?page=<%=i%>' class="navigation"><%=i%></a>
      </font>
<%  }
%>  &nbsp;
<%
  }

  if(page_num < results.getPageCount())
  {
%>  <Font color="#000000" style="font-weight:bold;">
      <a href='searchResults.jsp?page=<%=page_num+1%>' class="navigation">Next</a>
    </font>
<%}%>
]
</td>
</tr>
<%}}%>
</table>
<img src="../images/spacer.gif" width="1" height="100"><BR>
</td>
</tr>
</table>

</td>
</tr>
</table>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="100%" height="28" align="left" valign="bottom" background="../images/footer_bg.gif">
<img src="../images/footer.gif"><BR>
</td>
</tr>
</table>

</body>
</html>