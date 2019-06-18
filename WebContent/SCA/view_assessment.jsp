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
  
  Assessment assessment = (Assessment) request.getAttribute("ASSESSMENTBEAN");
  
  School sch = assessment.getSchool();
  
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
	<td width="100%" valign="top">
  <h2 class='body_title'><%=sch.getSchoolName()%></h2>
	<table align="center" width="100%" border="0" cellspacing="2" cellpadding="2" class="maintable">
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
  
	<form id="frmWaitListEdit" action="updateAssessment.html" method="post">   
    <input type='hidden' name='op' value='UPDATE'>
    <input type='hidden' name='id' value='<%=assessment.getId()%>'>
                                   
    <table align="center" width="60%" cellpadding="0" cellspacing="0" border="0" style="border:solid 1px #f0f0f0; padding:5px;">
      <tr>
        <td colspan="2" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Student Name</td>
        <td>
            <%=assessment.getStudentName()%>
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Student MCP</td>
        <td>
          <%=assessment.getStudentMCP()%>
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Current Grade</td>
        <td>
          <%=assessment.getGrade().getGradeName()%>
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Reason for Referral</td>
        <td>
          <sca:ReferralReasons id="referral_reason"  displayOnly="true"  value='<%=assessment.getReasons()%>' />
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Current Programming</td>
        <td>
          <%=assessment.getCurrentPathway().getDescription()%>
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar" style="color:#FFFFFF;">*&nbsp;</span>Previous Assessment<br><span style='font-size:9px'>(if Yes, specify month/year)</span></td>
        <td>
          <%
            if(assessment.getPreviousAssessmentDate() != null)
              out.println(assessment.getPreviousAssessmentDateFormatted());
            else
              out.println("NO");
          %>
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Assessment Types</td>
        <td>
          <sca:AssessmentTypes id="assessment_types" displayOnly="true" value='<%=assessment.getTests()%>' />
        </td>
      </tr>
      
      <tr>
        <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Assessment Status</td>
        <td>
          <sca:AssessmentStatus id="assessment_status" style="width:175px;" cls="requiredInputBox" value='<%=assessment.getStatus()%>' />
        </td>
      </tr>
      
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
