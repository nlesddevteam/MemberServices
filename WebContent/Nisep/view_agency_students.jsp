<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,
                  com.esdnl.servlet.*,
                  com.esdnl.nicep.beans.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/nisep.tld" prefix="nisep" %>

<%
  User usr = (User) session.getAttribute("usr");
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
                      <tr></tr>
                        <td width="100%" align="left" valign="top" style="padding-top:5px;padding-right:10px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"><nisep:AgencyDemographics field="name" /></td>
                            </tr>
                            
                            <tr style="padding-top:8px;" align="center">
                              <td>
                                <%if(request.getAttribute("msg") != null){%>
                                  <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;' align="left">
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td class="messageText" align="left"><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    </table>
                                  </p>
                                <%}%>
                                
                                <form id="frmViewAgency" name="frmViewAgency" action="addAgencyStudent.html" method="post">
                                  <input type='hidden' id="id" name="id" value="<%=agency.getAgencyId()%>">
                                  
                                  <fieldset>
                                    <legend>Student Roster</legend>
                                  
                                    <table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding:5px;">
                                      <tr>
                                        <td align="center">
                                          <nisep:AgencyStudents />
                                        </td>
                                      </tr>
                                      <tr>
                                        <td align="left" bgcolor="#B8DB7C">
                                          <input style="font-size:10px;font-weight:bold;color: #EF9A48;" type="button" value="Add" onclick="document.forms[0].action='addAgencyStudent.html';document.forms[0].submit();">
                                        </td>
                                      </tr>
                                    </table>
                                  </fieldset>
                                </form>
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
