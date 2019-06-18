<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,
                  com.esdnl.servlet.*,
                  com.esdnl.nicep.beans.*,com.esdnl.util.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/nisep.tld" prefix="nisep" %>

<%
  User usr = (User) session.getAttribute("usr");
  Form form = (Form) request.getAttribute("FORM");
  AgencyDemographicsBean agency = (AgencyDemographicsBean) request.getAttribute("AGENCYBEAN");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Eastern School District - Member Services - NISEP Administration</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'css/home.css';</style>
  
</head>
<body style="margin-top:15px;">

  <esd:SecurityCheck permissions="NISEP-ADMIN-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <jsp:include page="header.jsp" flush="true" />
  <table width="760" cellpadding="0" cellspacing="0" border="0"  align="center" style="border: solid 1px #FFB700;">
    <tr>
      <td>   
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="178" align="left" valign="top" style="padding-left:5px;border-right:solid 1px #FFB700;">
                    <img src="images/spacer.gif" width="1" height="5"><BR>
                    
                    <jsp:include page="side_nav.jsp" flush="true"/>
                    
                    <img src="images/spacer.gif" width="1" height="10"><BR>
                  </td>
                  <td width="15" align="left" valign="top">
                    <img src="images/spacer.gif" width="15" height="1"><BR>
                  </td>
                  <td width="*" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%" align="left" valign="top" style="padding-top:5px;padding-right:10px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"><%=agency.getName()%> - Add Student</td>
                            </tr>
                            
                            <tr style="padding-top:8px;">
                              <td>
                                <form id="frmAddAgencyContact" action="addAgencyStudent.html" method="post">
                                  <input type='hidden' name='op' value='ADD'>
                                  <input type='hidden' name='id' value='<%=agency.getAgencyId()%>'>
                                  
                                  <table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding:5px;">
                                    <tr>
                                      <td colspan="2" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Student(s)</td>
                                      <td>
                                        <nisep:StudentsNoAgencyAssociation id="student_id" style="width:175px;" cls="requiredInputBox" />
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">
                                        <input type="submit" value="Add">
                                      </td>
                                    </tr>
                                  </table>
                                </form>
                                
                                <%if(request.getAttribute("msg") != null){%>
                                  <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td class="messageText"><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    </table>
                                  </p>
                                <%}%>
                              </td>
                            </tr>  
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>						
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <jsp:include page="footer.jsp" flush="true" />
</body>
</html>
