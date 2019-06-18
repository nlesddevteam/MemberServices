<%@ page language="java"
         import="com.esdnl.personnel.recognition.model.bean.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	NominationPeriodBean period = (NominationPeriodBean) request.getAttribute("NOMINATIONPERIODBEAN");
  NominationBean[] nominations = (NominationBean[]) request.getAttribute("NOMINATIONBEANS");
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
                              <td class="displayPageTitle" ><%=period.getCategory().getName()%> - <%=period.getFormattedStartDate()%> to <%=period.getFormattedEndDate()%>- Nominations</td>
                            </tr>
                            <tr style="padding-top:8px;">
                              <td style="padding-bottom:10px;">
                                <table width="100%" cellpadding="1" cellspacing="0" border="0" style="border-bottom:solid 1px #333333;">
                                  <%if(request.getAttribute("msg")!=null){%>
                                  	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
                                    	<td colspan="4" align="center">
                                      	<%=(String)request.getAttribute("msg")%>
                                      </td>
                                    </tr>
                                  <%}%>
                                  <tr>
                                  	<td class="displayHeaderTitle" width="30%" valign="top">Nominee</td>
                                  	<td class="displayHeaderTitle" width="30%" valign="top">Nominated By</td>
                                  	<td class="displayHeaderTitle" width="15%" valign="top" align="center">Date</td>
                                  	<td class="displayHeaderTitle" width="25%" valign="top" align="center">Rationale</td>
                                  </tr>
                                  <%if(nominations.length > 0){
                                    for(int i=0; i < nominations.length; i++){
                                  %>
                                      <tr style='padding-top:8px; background-color:#<%=(i%2==0)?"FFFFFF":"F9f9f9"%>;'>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;" valign="top">
                                        	<%
                                        		out.println(nominations[i].getNomineeFirstname() 
                                        				+ " " + nominations[i].getNomineeLastname() 
                                        				+ "<br>" + nominations[i].getNomineeLocation().getSchoolName());
                                        	%>
                                        </td>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;" valign="top">
                                        	<%
                                        		if(nominations[i].getNominator() != null){
                                        			out.println(nominations[i].getNominator().getFullNameReverse());
                                        			if(nominations[i].getNominator().getSchool() != null)
                                        				out.println("<br>" + nominations[i].getNominator().getSchool().getSchoolName());
                                        		}else{
                                        			out.println(nominations[i].getNominatorFirstName() 
                                        					+ " " + nominations[i].getNominatorLastName() 
                                        					+ "<br>" + nominations[i].getNominatorLocation());
                                        		}
                                        	%>
                                        </td>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;" valign="top"><%=nominations[i].getFormattedNominationDate()%></td>
                                        <td class="displayText" style="border-bottom:solid 1px #e0e0e0;" valign="top" align="center">
                                        	<a href="/MemberServices/recognition/nominations/rationales/<%=nominations[i].getRationaleFilename()%>" class="nav" target="_blank">view</a>&nbsp;|
                                        	<a href="deleteRecognitionAwardNomination.html?np_id=<%=period.getId()%>&id=<%=nominations[i].getUID()%>" class="nav">delete</a>
                                        </td>
                                        <!-- 
                                        <td class='displayText' align="right" style="border-bottom:solid 1px #e0e0e0;">
                                        	&nbsp;
                                        </td>
                                        -->
                                      </tr>
                                 <%}
                                  }else{%>
	                                  <tr class="messageText">
	                                    <td colspan="5" align="center" style="padding-top:8px;padding-bottom:8px;">No nominations found.</td>
	                                  </tr>
                                 <%}%>
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
