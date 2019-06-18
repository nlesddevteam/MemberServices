<%@ page language="java"
         import="com.esdnl.personnel.recognition.model.bean.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	RecognitionCategoryBean cat = (RecognitionCategoryBean) request.getAttribute("CATEGORYBEAN");
  NominationPeriodBean[] nps = (NominationPeriodBean[]) request.getAttribute("NOMINATIONPERIODS");
%>

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
                              <td class="displayPageTitle" >Recognition Policy Category - Nomination Periods</td>
                            </tr>
                            <tr style="padding-top:8px;">
                              <td style="padding-bottom:10px;">
                                <table width="100%" cellpadding="0" cellspacing="0" border="0" style="border-bottom:solid 1px #333333;">
                                  <tr class="displayCategoryTitle" style="padding-top:8px;padding-bottom:8px;">
                                    	<td colspan="5">
                                      	<%=cat.getName()%>
                                      </td>
                                  </tr>
                                  <%if(request.getAttribute("msg")!=null){%>
                                  	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
                                    	<td colspan="5" align="center">
                                      	<%=(String)request.getAttribute("msg")%>
                                      </td>
                                    </tr>
                                  <%}%>
                                  <tr>
                                  	<td class="displayHeaderTitle" width="25" valign="top">Id</td>
                                  	<td class="displayHeaderTitle" width="75" valign="top">Start Date</td>
                                  	<td class="displayHeaderTitle" width="75" valign="top">End Date</td>
                                  	<td class="displayHeaderTitle" width="75" valign="top">Nomination<br>Count</td>
                                  	<td width="*">&nbsp;</td>
                                  </tr>
                                  <%if(nps.length > 0){
                                    for(int i=0; i < nps.length; i++){
                                  %>
                                      <tr style='padding-top:8px; background-color:#<%=(i%2==0)?"FFFFFF":"F9f9f9"%>;'>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;"><%=nps[i].getId()%></td>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;"><%=nps[i].getFormattedStartDate()%></td>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;"><%=nps[i].getFormattedEndDate()%></td>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;" align="center"><%=nps[i].getNominationCount()%></td>
                                        <td class='displayText' align="right" style="border-bottom:solid 1px #e0e0e0;">
                                        	<a href="viewRecognitionAwardPeriodNominations.html?id=<%=nps[i].getId()%>" class="nav">Nominations</a>&nbsp;|&nbsp;
                                        	<a href="deleteRecognitionAwardPeriod.html?cat_id=<%=cat.getUID()%>&id=<%=nps[i].getId()%>" class="nav">delete</a>
                                        </td>
                                      </tr>
                                 <%}
                                  }else{%>
	                                  <tr class="messageText">
	                                    <td colspan="5" align="center" style="padding-top:8px;padding-bottom:8px;">No nomination periods found for "<%=cat.getName()%>".</td>
	                                  </tr>
                                 <%}%>
                                  <tr>
	                                	<td colspan="5" align="center" style="padding-top:15px;" style="border-top:solid 1px #949494;">
	                                		<form method="POST" action="addRecognitionCategoryNominationPeriod.html" >
	                                			<input type="hidden" name="id" value="<%=cat.getUID() %>" />
	                                			<input type="hidden" name="op" value="ADD" />
	                                			<table width="75%" cellpadding="3" cellspacing="0" border="0" align="center" style="background-color:#f4f4f4;border: solid 1px #c0c0c0;">
	                                				<tr>
	                                					<td colspan="2" style="background-color:#FFFFFF;" class="displayBoxTitle">Add Nomination Period</td>
	                                				</tr>
	                                				<tr>
	                                					<td class="displayHeaderTitle" width="75">Start Date:<br>(dd/mm/yyyy)</td>
	                                					<td width="*"><input type='text' name="start_date" style="width:100px;" class="requiredInputBox" /></td>
	                                				</tr>
	                                				<tr>
	                                					<td class="displayHeaderTitle" width="75">End Date:<br>(dd/mm/yyyy)</td>
	                                					<td width="*"><input type='text' name="end_date" style="width:100px;" class="requiredInputBox" /></td>
	                                				</tr>
	                                				<tr>
	                                					<td colspan="2"><input type="submit" value="Add"/></td>
	                                				</tr>
	                                			</table>
	                                		</form>
	                                	</td>
	                                </tr>
                                </table>
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
