<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.school.bean.*,
                  com.awsd.school.dao.*,
                  com.esdnl.school.bean.*,
                  com.esdnl.school.database.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.nlesd.school.bean.*,
                  com.nlesd.school.service.*,
                  com.esdnl.personnel.jobs.constants.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<c:set var='szones' value='<%= SchoolZoneService.getSchoolZoneBeans() %>' />
<c:set var='regions' value='<%= RegionManager.getRegionBeans() %>' />
<c:set var='sgroups' value='<%= SubjectGroupManager.getSubjectGroupBeans() %>' />

<%
  	JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
	TreeMap<Integer,String> hmapc = ApplicantEducationPostSSManager.getDiplomaCertValues(3);
	TreeMap<Integer,String> hmapd = ApplicantEducationPostSSManager.getDiplomaCertValues(2);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import 'includes/home.css';</style>
		<link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/smoothness/jquery-ui.css"
		        type="text/css" rel="Stylesheet" />
		<style type='text/css'>
			optgroup { text-transform: uppercase; color: red; }
			optgroup option { text-transform: capitalize; color: black;} 
		</style>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
		<script language="JavaScript" src="js/CalendarPopup.js"></script> 
		
		<script type='text/javascript'>
			$(function(){
				var pickerOpts={dateFormat:"mm/dd/yy",changeMonth:true,changeYear:true,yearRange: "-75:+0"};
			    $( "#sdate" ).datepicker(pickerOpts);
			});
		</script>
	</head>
	
	<body>
  
	<!--
		// Top Nav/Logo Container
		// This will be included
	-->
	  <table width="760" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700;" align="center">
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
	                          <form action="filterJobApplicantsSS.html" method="post">
	                            <table width="100%" cellpadding="0" cellspacing="0" border="0">
	                              <tr>
	                                <td class="displayPageTitle" >Competition # <%=job.getCompetitionNumber()%></td>
	                              </tr>
	                              <tr style="padding-top:8px;">
	                                <td width="100%" style="padding-bottom:10px;">
	                                  <table width="100%" cellpadding="2" cellspacing="3" border="0">
	                                    <tr>
	                                      <td class="displayHeaderTitle">Currently Employed By NLESD</td>
	                                      <td class="displayText" valign="top">
	                                        Yes
	                                        <input type="radio" name="perm" id="perm-yes"  value="Y">
	                                        &nbsp;&nbsp;
	                                        No
	                                        <input type="radio" name="perm" id="perm-no"   value="N">
	                                        &nbsp;&nbsp;
	                                        Any
	                                        <input type="radio" name="perm" id="perm-any"   value="A" checked="checked">
	                                      </td>
	                                    </tr>
										<tr>
	                                      <td class="displayHeaderTitle">Senority Date </td>
	                                      <td>
	                                      	<select id="sfilter" name="sfilter" style="width:95px;" class="requiredInputBox">
	                                      		<option value=""></option>
	                                      		<option value="before">Before</option>
	                                      		<option value="after">After</option>
	                                      	</select>
	                                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                        <input type="text" name="sdate" id="sdate" style="width:75px;" class="requiredInputBox" value="" >
	                                      </td>
	                                    </tr>
	                                    
										<tr>
	                                      <td class="displayHeaderTitle">Current Position</td>
	                                      <td>
	                                        <select id="perm_position" name="perm_position" style="width:175px;" class="requiredInputBox">
	                                        				<option value=""></option>									                    	
									                    	<%for(ApplicantPoolJobSSConstant ap: ApplicantPoolJobSSConstant.ALL){ %>
									                    		<option value="<%=ap.getValue()%>"><%=ap.getDescription()%></option>
									                    	<%} %>
									        </select>
	                                      </td>
	                                    </tr>
										<tr>
	                                      <td class="displayHeaderTitle">Current Position Type</td>
	                                      <td>
	                                        <select id="positiontype" name="positiontype" style="width:175px;" cls='requiredInputBox' >
	                                        					<option value=""></option>
								              	  				<option value="C">Casual</option>
								              	  				<option value="P">Permanent</option>
								              	  				<option value="S">Substitute</option>
								              	  				<option value="T">Temporary</option>
								            </select>
	                                      </td>
	                                    </tr>	                                    
										<tr>
	                                      <td class="displayHeaderTitle">Degrees</td>
	                                      <td>
	                                         <job:Degrees id="degrees" cls="InputBox"  style="height:150px;width:250px;" multiple="true" />
	                                      </td>
	                                    </tr>
	                                    <tr>	                                    	                                    
	                                      <td class="displayHeaderTitle">Diplomas</td>
	                                      <td>
											<SELECT name="ddiploma" id="ddiploma" class="inputBox" style="height:150px;width:250px;" MULTIPLE>
											
                                      			<option value="-1" >NOT APPLICABLE</option>
                                      				<%
                                      					for (Map.Entry<Integer, String> entry : hmapd.entrySet()) {
                                      						out.println("<option value='" + entry.getKey() + "'>" + entry.getValue() + "</options>");
	                                      				}
	                                      			%>
                                      		</select>
	                                      </td>
	                                    </tr>
	                                    <tr>
	                                    <td class="displayHeaderTitle">Certificates</td>
	                                      <td>
	                                         <SELECT name="dcert" id="dcert" class="inputBox" style="height:150px;width:250px;" MULTIPLE>
                                 				<option value="-1" >NOT APPLICABLE</option>
                                 					<%
                                 						for (Map.Entry<Integer, String> entry : hmapc.entrySet()) {
                                 							out.println("<option value='" + entry.getKey() + "'>" + entry.getValue() + "</options>");
                                  						}
                                  					%>
                                 			</select>
	                                      </td>
	                                    </tr>	                                    
	                                    <tr>
	                                    <td class="displayHeaderTitle">Documents</td>
	                                      <td>
	                                      		<table>
	                                      			<tr>
	                                      			<td><label for="da">Drivers Abstract</label></td>
	                                      			<td><input type="checkbox" name="da" id="da" /></td>
	                                      			</tr>
	                                      			<tr>
	                                      			<td><label for="ohs">OHS Training</label></td>
	                                      			<td><input type="checkbox" name="ohs" id="ohs" /></td>
	                                      			</tr>
	                                      			<tr>
	                                      			<td><label for="cc">Code of Conduct</label></td>
	                                      			<td><input type="checkbox" name="cc" id="cc" /></td>
	                                      			</tr>
	                                      			<tr>
	                                      			<td><label for="fa">First Aid</label></td>
	                                      			<td><input type="checkbox" name="fa" id="fa" /></td>
	                                      			</tr>
	                                      			<tr>
	                                      			<td><label for="whmis">WHMIS</label></td>
	                                      			<td><input type="checkbox" name="whmis" id="whmis" /></td>
	                                      			</tr>
	                                      			<tr>
	                                      			<td><label for="dl">Drivers License</label></td>
	                                      			<td><input type="checkbox" name="dl" id="dl" /></td>
	                                      			</tr>
	                                      		</table>
	                                         	
	                                        	
	                                        	
	                                      </td>
	                                    </tr>
	                                    <tr style="padding-top:8px;">
	                                      <td colspan="2" align="center">
	                                        <input type="submit" value="Submit Filter">
	                                      </td>
	                                    </tr>
	                                  </table>
	                                </td>
	                              </tr>
	                            </table>
	                          </form>
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
	  <jsp:include page="footer.jsp" flush="true" />
	</body>
</html>
