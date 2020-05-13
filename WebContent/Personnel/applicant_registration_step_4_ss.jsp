<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	ApplicantProfileBean profile = null;
	ApplicantEducationSecSSBean edu = null;
	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	if(profile != null){
	 edu = ApplicantEducationSecSSManager.getApplicantEducationSecSSBeanBySin(profile.getSIN());
	}
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
<style>
	.tableTitle {font-weight:bold;width:20%;}
	.tableResult {font-weight:normal;width:80%;}
	.tableTitleL {font-weight:bold;width:20%;}
	.tableResultL {font-weight:normal;width:30%;}
	.tableTitleR {font-weight:bold;width:20%;}
	.tableResultR {font-weight:normal;width:30%;}
	input { border:1px solid silver;}
</style>

<script>
			$('document').ready(function(){
				//now we check to see if this is an edit
			    if($( "#heducationlevel" ).length){
			    	$("#educationlevel").val($( "#heducationlevel" ).val());
			    }
			    if($( "#hgraduated" ).length){
			    	$("#graduated").val($( "#hgraduated" ).val());
			    }
			    if($( "#hstateprovince" ).length){
			    	$("#state_province").val($( "#hstateprovince" ).val());
			    }
			});	
		</script>
</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">4</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
	SECTION 4: Editing your Support Staff/Management HR Application Profile 
</div>

<br/>Please complete/update your profile below. Fields marked * are required. 

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>4. HIGH SCHOOL EDUCATION</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
					
		
                           <form id="ApplicantRegistrationStep1" action="applicantRegistrationSS.html?step=4" method="post" onsubmit="return checkneweducation()">
                                  <%if(edu != null){%>
                                    	<input type="hidden" name="heducationlevel" id="heducationlevel" value="<%= edu.getEducationLevel()%>" >
                                    	<input type="hidden" name="hgraduated" id="hgraduated" value="<%= edu.getGraduated()%>" >
                                    	<input type="hidden" name="hstateprovince" id="hstateprovince" value="<%= edu.getSchoolProvince()%>" >
                                    	<input type="hidden" name="op" id="op" value="edit" >
                                    	<input type="hidden" name="id" id="id" value="<%= edu.getId()%>">
                                  <%}%>    
                           
                           <table class='table table-striped table-condensed' style='font-size:12px;'>
                           <tbody>
                           <tr>
                           <td class="tableTitleL">Education Level<br/>(Highest Grade)*:</td>
                           <td class="tableResultL" id="educationlevelW">
                           			<select id="educationlevel" name="educationlevel" class="form-control">
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
                             <td class="tableTitleR">School Name*:</td>
                             <td class="tableResultR" id="schoolnameW"><input type="text" name="schoolname" id="schoolname" class="form-control" value='<%= edu != null ? edu.getSchoolName():""%>'></td>
                             </tr>
                             <tr>
                             <td class="tableTitleL">School Town/City*:</td>
                             <td class="tableResultL" id="schoolcityW"><input type="text" name="schoolcity" id="schoolcity" class="form-control" value='<%= edu != null ? edu.getSchoolCity():""%>'></td>
                             <td class="tableTitleR">School Province/State*:</td>
                             <td class="tableResultR" id="state_provinceW"><job:StateProvince id="state_province" cls="form-control" /></td>
                             </tr>         
                             <td class="tableTitleL">Did you graduate?</td>
                             <td class="tableResultL" id="graduatedW"><select id="graduated" name="graduated" class="form-control">
                                        	<option value="">Please select</option>
                                        	<option value="Y">Yes</option>
                                        	<option value="N">No</option>
                                        	<option value="G">GED</option>
                                        </select>
                             </td>
                             <td></td>
                             <td></td>
                             </tr>
                               </tbody>  
                               </table>     
                                    
                                    <div align="center"><a class="btn btn-xs btn-danger" href="view_applicant_ss.jsp">Back to Profile</a> <input type="submit" class="btn btn-xs btn-success" value="Save/Update" /></div>
                                     
                                  
                                </form>
 </div></div></div></div>
 
 						<%if(request.getAttribute("msg")!=null){%>
							<script>$(".msgok").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
						<%if(request.getAttribute("errmsg")!=null){%>
							<script>$(".msgerr").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>                       
</body>
</html>
