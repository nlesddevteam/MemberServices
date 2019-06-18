<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.school.*,
                  com.esdnl.servlet.*,com.esdnl.util.*,
                  com.esdnl.sca.model.bean.*,
                  com.esdnl.sca.dao.*" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/sca.tld" prefix="sca" %>

<%
	User usr = (User) session.getAttribute("usr");
  Form form = (Form) request.getAttribute("FORM");
  
  Iterator sch_iter = SchoolDB.getSchools().iterator();
  School sch = (School) request.getAttribute("SCHOOLBEAN");
  Assessment assessment = null;
  
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
<tr>

  <td>
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
  <h2 class='body_title'><%=sch.getSchoolName()%></h2>
	<table align="center" width="95%" border="0" cellspacing="2" cellpadding="2" class="maintable">
  <tr><td valign="top" style="padding:5px;"> 
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
  
	<form id="frmWaitListAdd" action="addToWaitlist.html" method="post">   
    <input type='hidden' name='op' value='ADD'>
    <input type='hidden' name='sid' value='<%=sch.getSchoolID()%>'>
                                   
    <table align="center" width="60%" cellpadding="0" cellspacing="0" border="0" style="border:solid 1px #f0f0f0; padding:5px;">
      <tr>
        <td colspan="2" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Student Name</td>
        <td>
            <input type="text" name="student_name" id="student_name" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("student_name"):(assessment != null)?assessment.getStudentName():""%>">
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Student MCP</td>
        <td>
          <input type="text" name="student_mcp" id="student_mcp" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("student_mcp"):(assessment != null)?assessment.getStudentMCP():""%>"><br>
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Current Grade</td>
        <td>
          <sca:Grades id="student_grade" style="width:175px;" cls="requiredInputBox" value='<%=(form!=null)?form.get("student_grade"):(assessment != null)?Integer.toString(assessment.getGrade().getGradeID()):""%>' />
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Reason for Referral</td>
        <td>
          <sca:ReferralReasons id="referral_reason" style="width:175px;" multiple="true" cls="requiredInputBox" />
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Current Programming</td>
        <td>
          <sca:Pathways id="current_pathway" style="width:175px;" cls="requiredInputBox" value='<%=(form!=null)?Pathway.get(Integer.parseInt(form.get("referral_reason"))):(assessment != null)?assessment.getCurrentPathway():null%>' />
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar" style="color:#FFFFFF;">*&nbsp;</span>Previous Assessment<br><span style='font-size:9px'>(if Yes, specify month/year)</span></td>
        <td>
          <input type="text" name="prev_assessment" id="prev_assessment" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("prev_assessment"):((assessment != null)&&(assessment.getPreviousAssessmentDate() != null))?assessment.getPreviousAssessmentDateFormatted():""%>"><br>
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Assessment Types Required</td>
        <td>
          <sca:AssessmentTypes id="assessment_types" style="width:175px;" multiple="true" cls="requiredInputBox" />
        </td>
      </tr>
      <!--
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Assessment Status</td>
        <td>
          <sca:AssessmentStatus id="assessment_status" style="width:175px;" cls="requiredInputBox" />
        </td>
      </tr>
      -->                            
      <tr>
        <td colspan="2">
          <input type="submit" value="<%=(assessment == null)?"Add":"Update"%>">
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
