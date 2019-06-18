<%@ page language="java"
         import="com.esdnl.personnel.recognition.model.bean.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
  RecognitionCategoryBean[] cats = (RecognitionCategoryBean[]) request.getAttribute("CATEGORIES");
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
                              <td class="displayPageTitle" >Recognition Policy Categories</td>
                            </tr>
                            <tr style="padding-top:8px;">
                              <td style="padding-bottom:10px;">
                                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                  
                                  <%if(request.getAttribute("msg")!=null){%>
                                  	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
                                    	<td align="center">
                                      	<%=(String)request.getAttribute("msg")%>
                                      </td>
                                    </tr>
                                  <%}%>
                                  <%if(cats.length > 0){
                                    for(int i=0; i < cats.length; i++){
                                  %>
                                      <tr style='padding-top:8px; background-color:#<%=(i%2==0)?"FFFFFF":"F9f9f9"%>;'>
                                        <td class="displayHeaderTitle" style='border-bottom:dashed 1px #e0e0e0;'><%=cats[i].getName()%></td>
                                      </tr>
                                      <tr style='background-color:#<%=(i%2==0)?"FFFFFF":"f9f9f9"%>;'>
                                        <td class="displayText"><%=cats[i].getDescription()%></td>
                                      </tr>
                                      <tr style='background-color:#<%=(i%2==0)?"FFFFFF":"f9f9f9"%>;'>
                                        <td class="displayText" style='border-bottom:solid 1px #333333;' align="right">
                                        	<a href="viewRecognitionNominationPeriods.html?id=<%=cats[i].getUID()%>" class="nav">Nomination Periods</a>
                                        	[<%=cats[i].getNominationPeriodCount()%>]
                                        </td>
                                      </tr>
                                 <%}
                                  }else{%>
	                                  <tr class="messageText">
	                                    <td align="center" style="padding-top:8px;padding-bottom:8px;">No recognition categories found.</td>
	                                  </tr>
                                 <%}%>
                                  <tr>
	                                	<td align="center" style="padding-top:8px;padding-bottom:8px;">
	                                		<input type="button" value="Add Category" onclick="document.location.href='addRecognitionCategory.html'" />
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
