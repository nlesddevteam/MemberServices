<%@ page language="java"
        session="true"
         import="com.awsd.security.*,
                 com.awsd.efile.*,
                 com.awsd.efile.equestion.*,
                 com.awsd.school.*,
                 java.util.*"%>

<%!
  User usr = null;
  QuestionTypes types = null;
  QuestionType type = null;
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
  
  types = new QuestionTypes();
  subjects = new Subjects();
  grades = new Grades(true);
  courses = new Courses(true);
%>

<html>
<head>
<title>E-Question Repository - Member Services/Eastern School District</title>

<link rel="stylesheet" href="../css/e-file.css">

<script language="JavaScript" src="../js/common.js"></script>
</head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
  <form name="frmImport" action="importExamQuestion.html" method="POST" ENCTYPE="multipart/form-data">
    <jsp:include page="equestion_menu.jsp" flush="true"/>
    <table width="100%" cellpadding="10" cellspacing="0" border="0">
      <tr>
        <td width="100%" valign="top">
          <center>
            <table width="80%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="80%" valign="top" align="left">
                  <BR>
                  <a href="../EFileRepositoryChooser.html" class="navigation">home</a>&nbsp;<img src="../images/nav_arrow.gif">&nbsp;import questions<BR>
                  <img src="../images/import_question_title.gif"><BR>
                </td>
              </tr>
              <tr>
                <td width="80%" valign="top" align="left">
                  Import Questions is where you will <a href="javascript:openWindow('import.jsp','import',150,150)" class="main"><b>import</b></a> your exams, quiz, or unit test questions into the E-File Repository.<BR><BR>
                  <table width="80%" cellpadding="10" cellspacing="0" style="border-style: solid; border-width: 2px; border-color:#FEF153;">
                    <tr>
                      <td width="80%" valign="top" align="left" colspan="2">
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="50%" align="left">
                              <span class="header2">Question Information</span>
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
                      <td width="15%" valign="top" align="left" bgcolor="#F5F5EF">
                        Question Type *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <select name="questionType" onchange="resetMsg(); questionTypeCheck(document.frmImport);" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
                          <option value="-1"><b>Choose Question Type</b></option>
                          <%iter = types.iterator();
                            while(iter.hasNext())
                            {
                              type = (QuestionType) iter.next();
                          %>  <option value=<%=type.getTypeID()%>><%=type.getTypeName()%></option>
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
                        Question Subject *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <select name="questionSubject" onchange="resetMsg();" style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 11px;">
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
                        Unit Number *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <input type="text" name="questionUnitNumber" cols="40">
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Question Stem *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <textarea name="questionStem" cols="40" rows="5"></textarea>
                      </td>
                    </tr>
                    <tr id="ans_header" style="display:none;">
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr id="ans_footer" style="display:none;">
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Correct Answer *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <input type="text" name="fillin_correct_answer" style="width:300px;">
                      </td>
                    </tr>
                    <tr id="tf_header" style="display:none;">
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr id="tf_footer" style="display:none;">
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Correct Answer *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="75" valign="middle">
                              <input type="radio" name="correct_answer" value="TRUE">TRUE
                            </td>
                            <td width="*" valign="middle">
                              <input type="radio" name="correct_answer" value="FALSE">FALSE
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr id="mcsa_header" style="display:none;">
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr id="mcsa_footer" style="display:none;">
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Options *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <table width="100%" cellpadding="0" cellspacing="4">
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    A. <input type="radio" name="correct_option" value="6">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_6" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    B. <input type="radio" name="correct_option" value="7">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_7" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    C. <input type="radio" name="correct_option" value="8">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_8" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    D. <input type="radio" name="correct_option" value="9">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_9" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>

                    <tr id="mcma_header" style="display:none;">
                      <td width="80%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="../images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    <tr id="mcma_footer" style="display:none;">
                      <td width="25%" valign="top" align="left" bgcolor="#F5F5EF">
                        Options *<BR>
                      </td>
                      <td width="55%" valign="top" align="left" bgcolor="#F5F5EF">
                        <table width='100%' cellpadding="0" cellspacing="4">
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    A. <input type="checkbox" name="correct_option" value="1">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_1" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    B. <input type="checkbox" name="correct_option" value="2">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_2" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    C. <input type="checkbox" name="correct_option" value="3">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_3" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    D. <input type="checkbox" name="correct_option" value="4">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_4" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%">
                              <table width="100%" cellspacing="0" cellpadding="3">
                                <tr>
                                  <td width="12%" valign="middle">
                                    E. <input type="checkbox" name="correct_option" value="5">
                                  </td>
                                  <td width="*">
                                    <textarea name="option_text_5" cols="30" rows="3"></textarea>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="80%" valign="top" align="left" colspan="2">
                        <span class="header2">Import Stem Image</span><BR>
                      </td>
                    </tr>
                    <tr>
                      <td width="80%" valign="top" align="left" bgcolor="#F5F5EF" colspan="2">
                        STEP 1: Click on the "Browse" button.<BR>
                        STEP 2: Find the image on your computer and select it.<BR>
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
                              <input type="submit" value="Submit Question" onclick="if(validateQuestion(document.frmImport)){progressBarInit(); return true;}else return false;" style="font-family: Arial, Helvetica, sans-serif; font-size: 11px;">&nbsp;<input type="reset" name="reset" value="Reset" style="font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
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
    </center>
  </form>
  <script langauge="JavaScript">
    document.frmImport.questionType.selectedIndex=0;
    document.frmImport.questionSubject.selectedIndex=0;
    document.frmImport.gradeLevel.selectedIndex=0;
    document.frmImport.documentCourse.selectedIndex=0;
  </script>
</body>
</html>