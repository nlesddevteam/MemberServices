<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>
         
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	ApplicantProfileBean profile = null;
	ApplicantEducationSecSSBean edu = null;
	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	
	TreeMap<Integer,String> hmapc = ApplicantEducationPostSSManager.getDiplomaCertValues(3);
	TreeMap<Integer,String> hmapd = ApplicantEducationPostSSManager.getDiplomaCertValues(2);
	
	if(profile != null){
		 edu = ApplicantEducationSecSSManager.getApplicantEducationSecSSBeanBySin(profile.getSIN());
		}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Applicant Registration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>
<style type="text/css">@import 'includes/jquery-ui-timepicker-addon.css';</style>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/applicant_validations.js"></script>

</head>
<body>

<table cellpadding="2" cellspacing="2" width="920" align="left" border='0'>
<tr>
	<td colspan="2" width='100%'>
		<span class="applicantTitle">Applicant Registration <br/> Step 4: Education </span>
	</td>
</tr>

<tr valign="top">
	<td width="200">
		<jsp:include page="includes/jsp/applicant_registration_menu_ss.jsp" flush="true" />
	</td>
	<td width="*" align='left'>
                           <form id="ApplicantRegistrationStep1" action="applicantRegistrationSS.html?step=5" method="post" onsubmit="return checkneweducationpost()">
                           <%if(edu != null){%>
                                    	<input type="hidden" name="heducationlevel" id="heducationlevel" value="<%= edu.getEducationLevel()%>" >
                                    	<input type="hidden" name="hgraduated" id="hgraduated" value="<%= edu.getGraduated()%>" >
                                    	<input type="hidden" name="hstateprovince" id="hstateprovince" value="<%= edu.getSchoolProvince()%>" >
                                    	<input type="hidden" name="op" id="op" value="edit" >
                                    	<input type="hidden" name="id" id="id" value="<%= edu.getId()%>">
                                  <%}%>
                           <table width="100%" cellpadding="2" cellspacing="2" border="0" style="display:inline;">
                                   <tr bgcolor="#E5EAF1"><td colspan="2"><b>High School Education</b></td></tr>
                                    <tr>
                                    <td class="displayHeaderTitle" valign="middle" align="left" width='150px'><span class="requiredStar">*&nbsp;</span>Education Level(Highest Grade)</td>
                                      <td>
                                        <select id="educationlevel" name="educationlevel" style="width:175px;" class="requiredInputBox">
                                        	<option value="">Please select</option>
                                        	<option value="K">K</option>
                                        	<option value="1">1</option>
                                        	<option value="2">2</option>
                                        	<option value="3">3</option>
                                        	<option value="4">4</option>
                                        	<option value="5">5</option>
                                        	<option value="6">6</option>
                                        	<option value="7">7</option>
                                        	<option value="8">8</option>
                                        	<option value="9">9</option>
                                        	<option value="10">10</option>
                                        	<option value="11">11</option>
                                        	<option value="12">12</option>
                                        </select>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left"><span class="requiredStar">*&nbsp;</span>School Name</td>
                                      <td>
                                        <input type="text" name="schoolname" id="schoolname" style="width:175px;" class="requiredInputBox" 
                                        value='<%= edu != null ? edu.getSchoolName():""%>'>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left"><span class="requiredStar">*&nbsp;</span>School Town/City</td>
                                      <td>
                                        <input type="text" name="schoolcity" id="schoolcity" style="width:175px;" class="requiredInputBox"
                                        value='<%= edu != null ? edu.getSchoolCity():""%>'>
                                      </td>
                                    </tr>                                    
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left"><span class="requiredStar">*&nbsp;</span>School Province/State</td>
                                      <td>
                                        <job:StateProvince id="state_province" style="width:175px;" cls="requiredInputBox" />
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left"><span class="requiredStar">*&nbsp;</span>Did you graduate?</td>
                                      <td>
                                        <select id="graduated" name="graduated" style="width:175px;" class="requiredInputBox">
                                        	<option value="">Please select</option>
                                        	<option value="Y">Yes</option>
                                        	<option value="N">No</option>
                                        	<option value="G">GED</option>
                                        </select></td>
                                    </tr>
                                    <tr>
                                    <td colspan='2'>
                                    <br />
                                    <br />
                                    </td>
                                    </tr> 

                                   
                                   
                                   
                                   
                                   
                                   	<tr bgcolor="#E5EAF1"><td colspan="2"><b>Degrees\Diplomas\Certificates</b></td></tr> 
                                   	<tr>
                                   		<td colspan="2">
                                   		<table>
                                   			<tr valign="top"><td>&nbsp;</td><td align='center'>Fields marked <span class="requiredStar">*</span> are required.</td></tr>
											<tr valign="top"><td>&nbsp;</td><td align='center'>Please enter all post secondary Degrees, Diplomas, Certificates and/or Professional Certifications</td></tr>
											<tr valign="top"><td>&nbsp;</td><td align='center'>If you do not see the Degree, Diploma or Certificate listed below then please contact the NLESD  </td></tr>
											<tr valign="top"><td>&nbsp;</td><td align='center'>using the following email link <a href="mailto:careers@nlesd.ca?subject=Missing Degree, Diploma or Certificate" title="careers@nlesd.ca">Email HR</a>  to have it added</td></tr>
                                   		</table>
                                   		</td>
                                   	</tr>
                                   <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left" ><span class="requiredStar">*&nbsp;</span>Name of Institution</td>
                                      <td><input type="text" name="institution" id="institution" style="width:175px;" class="requiredInputBox"></td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left" ><span class="requiredStar">*&nbsp;</span>From (mm/yyyy)</td>
                                      <td><input type="text" name="from_date" id="from_date" style="width:175px;" class="requiredInputBox"></td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left" ><span class="requiredStar">*&nbsp;</span>To (mm/yyyy)</td>
                                      <td><input type="text" name="to_date" id="to_date" style="width:175px;" class="requiredInputBox"></td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left" >&nbsp;&nbsp;Program/Faculty</td>
                                      <td><input type="text" name="program" id="program" style="width:175px;"></td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left" >&nbsp;&nbsp;Type</td>
                                      <td>
                                      	<select id="dtype" name="dtype" style="width:175px;">
                                      		<option value="-1" >NOT APPLICABLE</option>
                                      		<option value="1" selected>Degree</option>
                                      		<option value="2">Diploma</option>
                                      		<option value="3">Certificate</option>
                                      	</select>
                                      </td>
                                    </tr>
                                    <tr>
                                    	<td colspan="2">
                                    		<div style="display:none;">
                                    				<table>
                                    				<tr>
                                      					<td class="displayHeaderTitle" align="left" valign="top">Major</td>
                                      					<td>
                                      						<job:MajorMinor id="major" cls="requiredInputBox"  style="height:150px;width:175px;" multiple="true" />
                                      					</td>
                                      				</tr>
                                    				</table>
                                    		</div>
                                    	</td>
                                    </tr>
                                    <tr>
                                    	<td colspan="2">
                                    		<div style="display:none;">
                                    				<table>
                                    				<tr>
                                    				<td class="displayHeaderTitle" valign="middle" align="left" ><span class="requiredStar">*&nbsp;</span>Minor</td>
                                      				<td><job:MajorMinor id="minor" cls="requiredInputBox"  style="width:175px;" /></td>
                                    				</tr>
                                    				</table>
                                    		</div>
                                    		</td>
                                      
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="left" >&nbsp;&nbsp;&nbsp;&nbsp;Degree\Diploma\Certificate</td>
                                      <td>
                                      						<div id="divmajor" name="divmajor">
                                      						<job:Degrees id="degree" cls="inputBox"  style="width:175px;" />
                                      						</div>
                                      						<div id="divcert" name="divcert" style="display:none;">
                                      							<select id="dcert" name="dcert" style="width:175px;">
                                      							<option value="-1" >NOT APPLICABLE</option>
                                      							<%
                                      								for (Map.Entry<Integer, String> entry : hmapc.entrySet()) {
                                      							    	out.println("<option value='" + entry.getKey() + "'>" + entry.getValue() + "</options>");
	                                      							}
	                                      						%>
                                      							</select>
                                      						</div>
                                      						<div id="divdiploma" name="divdiploma"  style="display:none;">
                                      							<select id="ddiploma" name="ddiploma" style="width:175px;">
                                      							<option value="-1" >NOT APPLICABLE</option>
                                      							<%
                                      								for (Map.Entry<Integer, String> entry : hmapd.entrySet()) {
                                      							    	out.println("<option value='" + entry.getKey() + "'>" + entry.getValue() + "</options>");
	                                      							}
	                                      						%>
                                      							</select>
                                      						</div>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="right">
                                        <input type="submit" value="Add">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="right">
                                        <hr width="100%">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="left">
                                        <job:ApplicantEducationPostSS showdelete="edit"/>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="right">
                                        <hr width="100%">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="right">
                                        <input type="button" value="Save and Continue" onclick="document.location.href='applicant_registration_step_6_ss.jsp'">
                                      </td>
                                    </tr>
                                    <%if(request.getAttribute("msg")!=null){%>
                                      <tr class="messageText2">
                                        <td colspan="2" align="center">
                                          <%=(String)request.getAttribute("msg")%>
                                        </td>
                                      </tr>
                                    <%}%>
                                  </table>     
                                
                        	 
                                  
                           </form>
        </td>
        </tr>
        </table> 
        	<script type="text/javascript">
	$('document').ready(function(){
		$('#dtype').on('change', function() {
			  if(this.value == "1" || this.value == "-1"){
				  $('#divmajor').show();
				  $('#divcert').hide();
				  $('#divdiploma').hide();
				  
				  
			  }
			  if(this.value == "2"){
				  $('#divmajor').hide();
				  $('#divcert').hide();
				  $('#divdiploma').show();
			  }
			  if(this.value == "3"){
				  $('#divmajor').hide();
				  $('#divcert').show();
				  $('#divdiploma').hide();
			  }

			  
		});
		//now we check to see if this is an edit
	    if($( "#heducationlevel" ).length){
	    	alert($( "#heducationlevel" ).val());
	    	$("#educationlevel").val($( "#heducationlevel" ).val());
	    }
	    if($( "#hgraduated" ).length){
	    	alert($( "#hgraduated" ).val());
	    	$("#graduated").val($( "#hgraduated" ).val());
	    }
	    if($( "#hstateprovince" ).length){
	    	alert($( "##hstateprovince" ).val());
	    	$("#state_province").val($( "#hstateprovince" ).val());
	    }
	});
	</script>                    
</body>
</html>