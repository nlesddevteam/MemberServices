<%@ page language="java"
         import="com.esdnl.personnel.recognition.model.bean.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel.tld" prefix="per" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel Package - Employee/Student Recognition Policy Program</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>
<script language="JavaScript" src="js/CalendarPopup.js"></script> 
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-RECOGNITION-ADMIN-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table align="center" width="760" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700;">
    <tr>
      <td>   
        <jsp:include page="admin_top_nav.jsp" flush="true"/>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="admin_side_nav.jsp" flush="true"/>
                  <td width="551" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="391" align="left" valign="top" style="padding-top:8px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle" >Add Recognition Policy Category</td>
                            </tr>
                            <tr style="padding-top:8px;">
                              <td style="padding-bottom:10px;">
                              	<form method="POST" action="addRecognitionCategory.html">
                              		<input type='hidden' name='op' value='ADD'/>
	                                <table width="100%" cellpadding="0" cellspacing="0" border="0">
	                                  <%if(request.getAttribute("msg")!=null){%>
	                                  	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
	                                    	<td colspan="2" align="center">
	                                      	<%=(String)request.getAttribute("msg")%>
	                                      </td>
	                                    </tr>
	                                  <%}%>
	                                  <tr>
	                                    <td class="displayHeaderTitle">Name</td>
	                                    <td><input type="text" name="cat_name" id="cat_name" style="width:300px;" class="requiredInputBox"></td>
	                                  </tr>
	                                  <tr>
	                                    <td class="displayHeaderTitle" valign="top">Description</td>
	                                    <td><textarea name="cat_desc" id="cat_desc" style="width:300px;height:300px;" class="requiredInputBox"></textarea></td>
	                                  </tr>
	                                  <tr>
	                                    <td class="displayHeaderTitle">Secure Only?</td>
	                                    <td><input type="checkbox" name="cat_secure" id="cat_secure" class="requiredInputBox"></td>
	                                  </tr>
	                                  <tr>
	                                    <td class="displayHeaderTitle">Monitor</td>
	                                    <td><per:PersonnelList id="cat_monitor_id" cls="requiredInputBox" style="width:300px;" role="PERSONNEL-RECOGNITION-ADMIN-MONITOR" /></td>
	                                  </tr>
	                                  <tr>
		                                	<td colspan="2" align="center" style="padding-top:8px;padding-bottom:8px;">
		                                		<input type="submit" value="Add" />
		                                	</td>
		                                </tr>
	                                </table>
                                </form>
                              </td>
                            </tr>
                          </table>
                        </td>
                        <td width="160" align="left" valign="top" style="padding:0;">
                          <img src="images/man1.gif"><BR>
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
  <jsp:include page="/Personnel/footer.jsp" flush="true" />
</body>
</html>
