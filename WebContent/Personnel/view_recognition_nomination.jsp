<%@ page language="java"
         import="com.awsd.security.*,
         				 java.text.*,
         				 java.util.*,
         				 com.esdnl.personnel.recognition.model.bean.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/recognition.tld" prefix="rec" %>

<%
User usr = (User) session.getAttribute("usr");
NominationBean n = (NominationBean) request.getAttribute("NOMINATIONBEAN");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel Package - Employee/Student Recognition Policy Program</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>
<style>
	fieldset{
		width:95%;
	}
	legend{
		font-weight: bold;
		color="#333333";
		text-decoration:underline;
	}
</style>
<script language="JavaScript" src="js/CalendarPopup.js"></script> 
</head>
<body>
  
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
                              <td class="displayPageTitle" >Recognition of Excellence - <%=n.getNominationPeriod().getCategory().getName()%> Nomination</td>
                            </tr>
                            <tr style="padding-top:8px;">
                              <td style="padding-bottom:10px;">
                              	<form method="POST" action="addRecognitionNomination.html" ENCTYPE="multipart/form-data">
                              		<%if(usr != null){ %>
                              			<input type="hidden" name='nominator_id' value="<%=usr.getPersonnel().getPersonnelID()%>" />
                              		<%}%>
                              		<input type='hidden' name='op' value='ADD'/>
	                                
	                                  <%if(request.getAttribute("msg")!=null){%>
	                                  	<table width="100%" cellpadding="0" cellspacing="0" border="0">
		                                  	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
		                                    	<td  align="center">
		                                      	Thank you for submitting your nomination!
		                                      </td>
		                                    </tr>
	                                    </table>
	                                  <%}%>
	                                  
	                                  <fieldset>
	   						<legend>Nomination Information</legend>
	              <table width="100%" cellpadding="0" cellspacing="3" border="0">
	              	<tr>
	                	<td class="displayHeaderTitle" width="75">Nominee</td>
	                 	<td class="displayText" width="*"><%=n.getNomineeFullName()%></td>
	               	</tr>
	               	<tr>
	                 	<td class="displayHeaderTitle">Nominee Location</td>
	                 	<td class="displayText" width="*"><%=n.getNomineeLocation().getSchoolName()%></td>
	               	</tr>
	              	<tr>
	                 	<td class="displayHeaderTitle" width="100">Award Category</td>
	                 	<td class="displayText" width="*"><%=n.getNominationPeriod().getCategory().getName() %></td>
	               	</tr>
	               	<tr>
			             	<td class="displayHeaderTitle">Rationale</td>
			             	<td class="displayText" width="*">
			             		<a href="/MemberServices/recognition/nominations/rationales/<%=n.getRationaleFilename()%>" class="nav" target="_blank">view</a>
			             	</td>
			           	</tr>
			           	<tr>
	                	<td class="displayHeaderTitle" width="75">Nominator</td>
	                 	<td class="displayText" width="*"><%=n.getNominatorFullName()%></td>
	               	</tr>
	               	<tr>
	                 	<td class="displayHeaderTitle">Nominator Location</td>
	                 	<td class="displayText" width="*"><%=n.getNominatorLocation()%></td>
	               	</tr>
	               	<tr>
	                 	<td class="displayHeaderTitle">Nomination Date</td>
	                 	<td class="displayText" width="*"><%=n.getFormattedNominationDate()%></td>
	               	</tr>
	              </table>
              </fieldset>
		                                
	                                  <p class="displayText">
		                                  	The Selection Committee will consist of one representative or designate of the 
		                                  	programs, human resources, finance and administration and rural education/corporate 
		                                  	services divisions, two SEOs and two programs specialists, and the manager of 
		                                  	communications. 
	                                  </p>
	                                  
	                                  
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
