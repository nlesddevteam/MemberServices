<%@ page language="java"
        session="true"
         import="com.awsd.security.*,
                 com.awsd.efile.*,
                 com.awsd.efile.edocument.*,
                 com.awsd.efile.edocument.DocumentType,
                 com.awsd.efile.edocument.DocumentTypes,
                 com.awsd.school.*,
                 java.util.*"%>

<%!
  User usr = null;
  DocumentTypes types = null;
  DocumentType type = null;
  Subjects subjects = null;
  Subject s = null;
  Grades grades = null;
  Grade g = null;
  Courses courses = null;
  Course c = null;

  Iterator iter = null;
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
  
  types = new DocumentTypes();
  subjects = new Subjects();
  grades = new Grades(true);
  courses = new Courses(true);
%>

<html>
<head>
<title>E-File Repository - Member Services/Eastern School District</title>

<link rel="stylesheet" href="../css/e-file.css">

<script language="JavaScript" src="../js/common.js"></script>
</head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
  <form name="frmImport" action="importDocument.html" method="POST" ENCTYPE="multipart/form-data">
    <jsp:include page="edocument_menu.jsp" flush="true"/>
    <table width="100%" cellpadding="10" cellspacing="0" border="0">
      <tr>
        <td width="100%" valign="top">
            <table width="80%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="80%" valign="top" align="left">
                  <BR>
                  <a href="EFileRepositoryChooser.html" class="navigation">home</a>&nbsp;<img src="../images/nav_arrow.gif">&nbsp;import documents<BR>
                  <img src="../images/import_title.gif"><BR>
                </td>
              </tr>
              <tr>
                <td width="80%" valign="top" align="left">
                  Import Documents is where you will <a href="javascript:openWindow('import.jsp','import',150,150)" class="main"><b>import</b></a> your exams, assignments and lesson plans, alternate courses, criteria C domains, and behavior management plans to E-File Repository.<BR><BR>

                  <table width="80%" cellpadding="10" cellspacing="0" style="border-style: solid; border-width: 2px; border-color:#FEF153;">
                    <tr>
                      <td width="80%" valign="top" align="left" colspan="2">
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="50%" align="left">
                              <span class="header2">Document Information</span>
                            </td>
                            <td id="msg" width="50%" align="right">
                              <% if(request.getAttribute("msg")!= null) { %>
                                <font color="#FF0000" style="font-weight:bold;"><%=request.getAttribute("msg")%></font>
                              <% } else { %>
                                <img src="../images/spacer.gif" width="1" height="5">
                              <% } %>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Document Type *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <select name="documentType" onchange="resetMsg();" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
                          <option value="-1"><b>Choose Document Type</b></option>
                          <%iter = types.iterator();
                            while(iter.hasNext())
                            {
                              type = (DocumentType) iter.next();
                          %>  <option value=<%=type.getDocumentTypeID()%>><%=type.getDocumentTypeName()%></option>
                          <% } %>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Document Subject *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <select name="documentSubject" onchange="resetMsg();" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
                          <option value="-1"><b>Choose Subject</B></option>
                          <%iter = subjects.iterator();
                            while(iter.hasNext())
                            {
                              s = (Subject) iter.next();
                          %><option value=<%=s.getSubjectID()%>><%=s.getSubjectName()%></option>
                          <% } %>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Grade Level *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <select name="gradeLevel" onchange="resetMsg(); seniorHighCheck(document.frmImport);" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
                          <option value="-1"><B>Choose Grade Level</B></option>
                          <%iter = grades.iterator();  
                            while(iter.hasNext())
                            {   
                              g = (Grade) iter.next();
                          %>  <option value="<%=g.getGradeID()%>"><%=g.getGradeName()%></option>
                          <% } %>
                        </select>
                      </td>
                    </tr> 
                    <tr id="crs-header" style="display:none;">
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr id="crs-footer" style="display:none;">
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Course *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <select name="documentCourse" onchange="resetMsg();" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
                          <option value="-1"><b>Choose Course</b></option>
                          <% iter = courses.iterator();
                             while(iter.hasNext())
                             {
                                c = (Course) iter.next();
                          %><option value=<%=c.getCourseID()%>><%=c.getCourseName()%></option>
                          <% } %>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Document Keyword(s) *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <input type="text" size="30" name="keyword1" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
                        <input type="text" size="30" name="keyword2" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
                        <input type="text" size="30" name="keyword3" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
                        <input type="text" size="30" name="keyword4" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" colspan="2">
                        <span class="header2">Import Your Document</span><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
                        STEP 1: Click on the "Browse" button.<BR>
                        STEP 2: Find the document on your computer and select it.<BR>
                        STEP 3: Click the "Open" button.<BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
                        <input type="file" size="60" name="uploadfile" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="right" bgcolor="#F5F5EF" colspan="2">
                        <table width="100%">
                          <tr>
                            <td width="50%" align="left">
                              <script language="javascript" src="../js/timerbar.js"></script>
                            </td>
                            <td width="50%" align="right">
                              <input type="submit" value="Import Document" onclick="if(validateDocument(document.frmImport)){progressBarInit(); return true;}else return false;" style="font-family: Arial, Helvetica, sans-serif; font-size: 11px;">&nbsp;<input type="reset" name="reset" value="Reset" style="font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                  </table>

                  <img src="../images/spacer.gif" width="1" height="50"><BR>
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
  </form>
  <script langauge="JavaScript">
    document.frmImport.documentType.selectedIndex=0;
    document.frmImport.documentSubject.selectedIndex=0;
    document.frmImport.gradeLevel.selectedIndex=0;
    document.frmImport.documentCourse.selectedIndex=0;
  </script>
</body>
</html>