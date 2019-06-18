<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	ReferenceBean ref = (ReferenceBean) request.getAttribute("REFERENCE_BEAN");
  	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		
		<style type="text/css">@import 'includes/home.css';</style>
		<style type="text/css">@import 'includes/form.css';</style>
		<style>
			.question {font-style: italic;}
			.answer {color:red;}
			.hire {font-weight:bold; color:red;}
		</style>
		
		<script language="JavaScript" src="js/CalendarPopup.js"></script>
		<script language="JavaScript" src="js/common.js"></script>
		<script language="JavaScript" src="js/personnel_ajax_v1.js"></script>
	</head>
	
	<body style='margin:10px;'>
    
<!--
	// Top Nav/Logo Container
	// This will be included
-->
	<table width="800" cellpadding="0" cellspacing="5" border="0" align="center" style='border:solid 1px #333333;background-color:#C0C0C0;'>
		<tr>
			<td style='border:solid 1px #333333;background-color:#FFFFFF;'>
				<table width="760" cellpadding="0" cellspacing="0" border="0" align="center">
			    <tr>
			      <td align="left" style='padding-top:15px;'>
			      	<img src="images/refheader.gif" width='760'><br>
			      </td>
			    </tr>
			  </table>
			  <table width="760" cellpadding="0" cellspacing="0" border="0" align="center">
			    <tr>
			      <td>   
			        <table width="760" cellpadding="0" cellspacing="0" border="0">
			          <tr>
			            <td width="760" align="left" valign="top">
			              <table width="760" cellpadding="0" cellspacing="0" border="0">
			                <tr>
			                  <td width="600" align="left" valign="top">		
			                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
			                      <tr>
			                        <td width="100%" align="left" valign="top" style="padding-top:8px;">
			                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
			                            <tr>
			                              <td class="displayPageTitle"  width="100%">Candidate Reference Check</td>
			                            </tr>
			                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<tr>
				                              <td class="messageText"  width="100%"><%=(String)request.getAttribute("msg")%></td>
				                            </tr>
			                            <%} %>
			                            <tr style="padding-top:8px;">
			                              <td style="padding-bottom:10px;" width="100%">
		                                  
																			<table width="100%" border="0" cellspacing="2" cellpadding="2" class="mainbody">
																				<tr>
																						<td width="200" class='displayHeaderTitle'>Candidates Name:</td>
																						<td width="*"><%= profile.getFullName() %></td>
																				</tr>
																				
																				<tr>
																					<td width="200" class='displayHeaderTitle'>Person providing reference:</td>
																					<td><%= ref.getReferenceProviderName() %></td>
																				</tr>
																				
																				<tr>
																					<td width="200" class='displayHeaderTitle'>Position:</td>
																					<td width="*"><%=ref.getReferenceProviderPosition() %></td>
																				</tr>
																			</table>
																			<br>&nbsp;<br>
		
																			<table border="0" cellspacing="2" cellpadding="2" class="mainbody" width='100%'>
																				<tr>
																					<td colspan="2"><b>The following reference check must be completed and attached to the teacher recommendation form.</b><p></td>
																				</tr>
																				<tr>
																					<td class='question'>Did the candidate ask permission to use your name as a reference?</td>
																					<td class='answer'><%=ref.getQ1() %></td>
																				</tr>
																				<tr>
																					<td class='question'>How long have you known this teacher?</td>
																					<td class='answer'><%=ref.getQ2() %></td>
																				</tr>
																				<tr>
																					<td class='question'>How long has he/she worked in your school?</td>
																					<td class='answer'><%=ref.getQ3() %></td>
																				</tr>
																				<tr valign="top">
																					<td class='question'>What has been his/her teaching assignment this year?</td>
																					<td class='answer'><%=ref.getQ4() %></td>
																				</tr>
																				<tr>
																					<td class='question'>Did this teacher complete a professional growth plan?</td>
																					<td class='answer'><%=ref.getQ5() %></td>
																				</tr>
																				<tr>
																					<td class='question'>Was the growth plan successfully followed?</td>
																					<td class='answer'><%=ref.getQ6() %></td>
																				</tr>
																				<tr>
																					<td class='question'>Has the teacher demonstrated leadership on your staff?</td>
																					<td class='answer'><%=ref.getQ7() %></td>
																				</tr>
																				<%if(ref.getQ7().equalsIgnoreCase("YES")){ %>
																					<tr valign="top">
																						<td colspan='2' class='question'>If yes, please give examples:</td>
																					</tr>
																					<tr>
																						<td colspan='2'><%=(!StringUtils.isEmpty(ref.getQ7Comment())?ref.getQ7Comment():"none") %></td>
																					</tr>
																				<%}%>
																				<tr>
																					<td colspan="2">
																						<br>
																						<b>
																							On a scale of 1 to 5 with 1 being I highly agree and 5 being I highly 
																							disagree please rate the teacher on the following statements:
																						</b>
																					</td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher demonstrates a positive attitude towards students:</td>
																					<td class='answer'><%=ref.getScale1() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher works collaboratively with other teachers:</td>
																					<td class='answer'><%=ref.getScale2() %> </td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher uses assessment to guide instruction:</td>
																					<td class='answer'><%=ref.getScale3() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher takes ownership for student learning:</td>
																					<td class='answer'><%=ref.getScale4() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher uses a variety of instructional strategies to address the needs of diverse learners:</td>
																					<td class='answer'><%=ref.getScale5() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher is a positive role model for students and staff:</td>
																					<td class='answer'><%=ref.getScale6() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher demonstrates a strong understanding of his/her curriculum responsibilities:</td>
																					<td class='answer'><%=ref.getScale7() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher practices good classroom management techniques:</td>
																					<td class='answer'><%=ref.getScale8() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher makes regular home contact with parents:</td>
																					<td class='answer'><%=ref.getScale9() %></td>
																				</tr>
																				<tr>
																					<td class='question'>This teacher understands and adheres to the principals of the ISSP process:</td>
																					<td class='answer'><%=ref.getScale10() %></td>
																				</tr>
																				<tr><td colspan="2"><br>&nbsp;<br></td></tr>
																				<tr>
																					<td colspan="2"  class='question'>Please identify the ways in which this teacher has been involved in building a positive atmosphere in your school:</td>
																				</tr>
																				<tr>
																					<td colspan='2'><%=ref.getQ8() %></td>
																				</tr>
																				<tr><td>&nbsp;</td></tr>
																				<tr valign="top">
																					<td colspan='2'><span class='question'>If given the opportunity would you hire this teacher?</span>&nbsp;&nbsp;<span class='hire'><%=ref.getQ10()%></span></td>
																				</tr>
																				<tr>
																					<td colspan='2' class='question'><br/>Additional Comments:</td>
																				</tr>
																				<tr>
																					<td colspan='2'><%= (!StringUtils.isEmpty(ref.getQ9Comment())?ref.getQ9Comment():"none") %></td>
																				</tr>
																			</table>
																			<p>
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
  		</td>
  	</tr>
  	<tr>
  		<td style='border:solid 1px #333333;'>
		  	<jsp:include page="footer.jsp" flush="true">
		  		<jsp:param name="width" value="800"/>
		  	</jsp:include>
	  	</td>
  	</tr>
  </table>
</body>
</html>
