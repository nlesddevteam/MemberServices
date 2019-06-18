<%@ page language="java"
        session="true"
         import="com.awsd.security.*,
                 com.awsd.efile.*,
                 com.awsd.efile.equestion.*,
                 com.awsd.school.*,
                 java.util.*,
                 com.awsd.servlet.*,
                 java.io.*,
                 java.text.*,
                 com.awsd.common.*"%>

<%!
  User usr = null;
  QuestionType type = null;
  Question doc = null;
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
  boolean added = false;
  SimpleDateFormat sdf = null;
  QuestionOptions options = null;
  QuestionOption option = null;
  Iterator opt_iter = null;
  boolean first = true;
  HashMap cart = null;
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
  
  cart = (HashMap)session.getAttribute("EQUESTION-CART");
  if(cart == null)
  {
    cart = new HashMap(10);
    session.setAttribute("EQUESTION-CART", cart);
  }
%>
<html>
<head>
<title>E-File Repository - Member Services/Eastern School District</title>

<link rel="stylesheet" href="../css/e-file.css">

<script language="JavaScript" src="../../js/common.js"></script>

</head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">

<jsp:include page="equestion_menu.jsp" flush="true"/>


<table width="100%" cellpadding="10" cellspacing="0" border="0">
<tr>
<td width="100%" valign="top">
<center>
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
<td width="12%" valign="top" align="left" colspan="1">
  <b>Date</b><BR>
</td>
<td width="15%" valign="middle" align="left" colspan="1">
  <b>Question Type</b><BR>
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
<td width="10%" valign="middle" align="left" colspan="1">
  <img src="../images/view_cart_01.gif" border="0" style="cursor:hand;"
       onmouseover="src='../images/view_cart_02.gif';"
       onmouseout="src='../images/view_cart_01.gif';"
       onclick="document.location.href='viewQuestionCart.html?page=<%=page_num%>';"><br>
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
    doc = (Question) iter.next();

    added = cart.containsKey(new Integer(doc.getQuestionID()));
%>
<tr id="<%="R"+doc.getQuestionID()%>" style="background-color:<%=color_off%>;"
    onmouseover="toggleTableRowHighlight('<%="R"+doc.getQuestionID()%>', '<%=color_on%>');"
    onmouseout="toggleTableRowHighlight('<%="R"+doc.getQuestionID()%>', '<%=color_off%>');">
<td valign="middle" align="left" colspan="1">
<%=sdf.format(doc.getSubmitDate())%><BR>
</td>
<td valign="middle" align="left" colspan="1">
<%=doc.getQuestionType().getTypeName()%><BR>
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
  <table width="100%">
    <tr>
      <td width="50%" align="center" valign="middle">
        <%if(!added){%>
        <img src="../images/add_to_cart_off.gif" border="0" style="cursor:hand;"
          onmouseover="src='../images/add_to_cart_on.gif';"
          onmouseout="src='../images/add_to_cart_off.gif';"
          onclick="document.location.href='addToCart.html?qid=<%=doc.getQuestionID()%>&page=<%=page_num%>';"><br>
        <%}else{%>
          <img src="../images/added_to_cart.gif" border="0"><br>
        <%}%>
      </td>
      <td width="*" align="center" valign="middle">
        <%if (canDelete || (doc.getPersonnel().getPersonnelID() == usr.getPersonnel().getPersonnelID())) { %>
          <img src="../images/delete_01.gif" border="0" style="cursor:hand;"
            onmouseover="src='../images/delete_02.gif';"
            onmouseout="src='../images/delete_01.gif';"
            onclick="document.location.href='deleteQuestion.html?id=<%=doc.getQuestionID()%>';">
        <%} else {%>
          &nbsp;
        <%}%>
      </td>
    </tr>
  </table>
</td>
</tr>

<tr>
  <td colspan="6" align="center">
    <table width="50%" cellspacing="0" cellpadding="0" style="border:solid 1px #CCCCCC;">
      <tr>
        <td>
        <table width="100%" cellpadding="2" cellspacing="0">
          <tr>
            <td width="32" height="32px" style="border-right:solid 1px #CCCCCC;background-color:#e4e4e4;color:#00008B;font-size:32px;line-height:32px;" valign="bottom">Q:</td>
            <td width="100%"><%=doc.getStem()%></td>
          </tr>
        </table>
        </td>
      </tr>
      <% if(((doc.getQuestionTypeID()==1)||(doc.getQuestionTypeID()==2))&&(doc.getOptions() != null) && (doc.getOptions().size() > 0)){
          opt_iter = doc.getOptions().iterator();

          first = true;
          
          while(opt_iter.hasNext()){
            option = (QuestionOption) opt_iter.next();%>
          <tr>
            <td width="100%" align="left">
              <table width="100%" cellpadding="2" cellspacing="">
                <tr>
                  <%if(first){%>
                      <td width="32" height="32" style="padding-left:4px;border-right:solid 1px #CCCCCC;background-color:#E4E4E4;color:#8B0000;font-size:32px;line-height:32px;">A:</td>
                  <%}else{%>
                      <td width="32" height="32" style="padding-left:4px;border-right:solid 1px #CCCCCC;background-color:#E4E4E4;color:#8B0000;font-size:32px;line-height:32px;">&nbsp;</td>
                  <%}%>
                  <%if(doc.getQuestionTypeID() == 1){
                    if(option.isCorrect()){%>
                      <td width="22" <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><img src="../images/radio_button_on.gif"><br></td>
                    <%}else{%>
                      <td width="22" <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><img src="../images/radio_button_off.gif"><br></td>
                    <%}%>
                
                  <%}else if(doc.getQuestionTypeID() == 2){
                    if(option.isCorrect()){%>
                      <td width="22" <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><img src="../images/checkbox_on.gif"><br></td>
                    <%}else{%>
                      <td width="22" <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><img src="../images/checkbox_off.gif"><br></td>
                    <%}%>
                  <%}%>
                  <td width="*" align="left" valign="middle"  <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><%=option.getOption().trim()%></td>
                </tr>
              </table>
            </td>
          </tr>
      <%  first = false;
        }
      }else if((doc.getQuestionTypeID()==3)||(doc.getQuestionTypeID()==4)){%>
        <tr>
          <td>
            <table width="100%" cellpadding="2" cellspacing="0">
              <tr>
                <td width="32" height="32px" align="center" style="padding-left:4px;border-right:solid 1px #CCCCCC;background-color:#E4E4E4;color:#8B0000;font-size:32px;line-height:32px;" valign="middle">A:</td>
                <td width="*" style="border-top:solid 1px #CCCCCC;"><%=doc.getCorrectAnswer()%></td>
              </tr>
            </table>
          </td>
        </tr>
    <%}%>
    </table>
  </td>
</tr>
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
</center>

</body>
</html>