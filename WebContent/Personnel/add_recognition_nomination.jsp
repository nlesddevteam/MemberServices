<%@ page language="java"
         import="com.awsd.security.*,
         				 java.text.*,
         				 java.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/recognition.tld" prefix="rec" %>

<%
	User usr = (User) session.getAttribute("usr");
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
                              <td class="displayPageTitle" >Recognition of Excellence - Nomination Form</td>
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
		                                      	<%=(String)request.getAttribute("msg")%>
		                                      </td>
		                                    </tr>
	                                    </table>
	                                  <%}%>
	                                  
	                                  <fieldset>
                        						<legend>NOMINEE</legend>
	                                  <table width="100%" cellpadding="0" cellspacing="3" border="0">
		                                  <tr>
		                                    <td class="displayHeaderTitle">First Name</td>
		                                    <td><input type="text" name="nominee_first_name" id="nominee_first_name" style="width:200px;" class="requiredInputBox"></td>
		                                  </tr>
		                                  <tr>
		                                    <td class="displayHeaderTitle">Surname</td>
		                                    <td><input type="text" name="nominee_last_name" id="nominee_last_name" style="width:200px;" class="requiredInputBox"></td>
		                                  </tr>
		                                  <tr>
		                                    <td class="displayHeaderTitle">Location</td>
		                                    <td><rec:Schools id="nominee_location_id" style="width:200px;" cls="requiredInputBox" /></td>
		                                  </tr>
	                                  </table>
	                                  </fieldset>
	                                  
	                                  <br/>
	                                  
	                                  <fieldset>
                        						<legend>AWARD</legend>
	                                  <table width="100%" cellpadding="0" cellspacing="3" border="0">
		                                  <tr>
		                                  	<td class="displayText" colspan="2" style="display:block;padding-top:5px;padding-bottom:5px">
			                                  	Please attach a <b><i>maximum of two pages</i></b> describing why the nominee should be considered for 
			                                  	a Recognition of Excellence Award.  Be sure to sign your nomination statement and please keep the 
			                                  	nomination confidential.
		                                  	</td>
		                                  </tr>
		                                  <tr>
		                                    <td class="displayHeaderTitle" width="100">Award Category</td>
		                                    <td><rec:RecognitionCategories id="period_id" style="width:200px;" cls="requiredInputBox" /></td>
		                                  </tr>
		                                  <tr>
						                            <td class="displayHeaderTitle">Rationale File<br><span style="font-size:9px;">(*.pdf, *.doc, *.txt)</span></td>
						                            <td><input type="file" name="rationale_file" style="width:200px;" class="requiredInputBox" /></td>
						                          </tr>
					                          </table>
					                          </fieldset>
					                          
					                          <br/>
					                          
					                          <fieldset>
                        						<legend>NOMINATOR</legend>
					                          <table width="100%" cellpadding="0" cellspacing="3" border="0">
		                                  <%if(usr != null){%>
			                                  <tr>
			                                    <td class="displayHeaderTitle">Name</td>
			                                    <td class="displayText"><%=usr.getPersonnel().getFullNameReverse()%></td>
			                                  </tr>
		                                  	<%if(usr.getPersonnel().getSchool() != null){%>
				                                  <tr>
				                                    <td class="displayHeaderTitle">School</td>
				                                    <td class="displayText"><%=usr.getPersonnel().getSchool().getSchoolName()%></td>
				                                  </tr>
				                                <%}%>
				                              <%}else{%>
					                              <tr>
			                                    <td class="displayHeaderTitle">First Name</td>
			                                    <td><input type="text" name="nominator_first_name" id="nominator_first_name" style="width:200px;" class="requiredInputBox"></td>
			                                  </tr>
			                                  <tr>
			                                    <td class="displayHeaderTitle">Surname</td>
			                                    <td><input type="text" name="nominator_last_name" id="nominator_last_name" style="width:200px;" class="requiredInputBox"></td>
			                                  </tr>
			                                  <tr>
			                                    <td class="displayHeaderTitle">Address/School</td>
			                                    <td><input type="text" name="nominator_location" id="nominator_location" style="width:200px;" class="requiredInputBox"></td>
			                                  </tr>
				                              <%}%>
				                              <tr>
			                                	<td class="displayHeaderTitle">Date</td>
			                                  <td class="displayText"><%=new SimpleDateFormat("MMMM dd, yyyy hh:mm a").format(Calendar.getInstance().getTime())%></td>
			                                </tr>
		                                </table>
		                                </fieldset>
		                                
	                                  <p class="displayText">
		                                  	The Selection Committee will consist of one representative or designate of the 
		                                  	programs, human resources, finance and administration and rural education/corporate 
		                                  	services divisions, two SEOs and two programs specialists, and the manager of 
		                                  	communications. 
	                                  </p>
	                                  
	                                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
		                                  <tr>
			                                	<td  align="center" style="padding-bottom:8px;">
			                                		<input type="submit" value="Nominate" />
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
