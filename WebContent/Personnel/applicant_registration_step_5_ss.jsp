<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	ApplicantProfileBean profile = null;
	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");	
	TreeMap<Integer,String> hmapc = ApplicantEducationPostSSManager.getDiplomaCertValues(3);
	TreeMap<Integer,String> hmapd = ApplicantEducationPostSSManager.getDiplomaCertValues(2);
	ApplicantEducationSecSSBean edu = null;
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
    <script type="text/javascript">
	$('document').ready(function(){
		$('#dtype').on('change', function() {
			  if(this.value == "1" || this.value == "-1" ){
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
	<script src="js/applicant_validations.js"></script>
</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">5</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
	SECTION 5: Editing your Support Staff/Management HR Application Profile 
</div>
<br/><br/>
<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>4a. HIGH SCHOOL EDUCATION</b></div>
	               	<div class="alert alert-danger" align="center" id="divmsg" style="display:none;"><span id="spanmsg" name="spanmsg"></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
					
		
                           <form id="ApplicantRegistrationStep1"  method="post" >
                                  <%if(edu != null){%>
                                    	<input type="hidden" name="heducationlevel" id="heducationlevel" value="<%= edu.getEducationLevel()%>" >
                                    	<input type="hidden" name="hgraduated" id="hgraduated" value="<%= edu.getGraduated()%>" >
                                    	<input type="hidden" name="hstateprovince" id="hstateprovince" value="<%= edu.getSchoolProvince()%>" >
                                    	<input type="hidden" name="op" id="op" value="edit" >
                                    	<input type="hidden" name="id" id="id" value="<%= edu.getId()%>">
                                  <%}%>    
                           <input type="hidden" name="utype" id="utype" value="">
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
                                    
                                    
                                     <div align="center"><a class="btn btn-xs btn-danger" href="view_applicant_ss.jsp">Back to Profile</a> <input type="button" value="Save/Update"  class="btn btn-xs btn-success" onclick="checkneweducation()"></div>
                                  
                               
 </div></div></div></div>                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>4b. DEGREES/DIPLOMAS/CERTIFICATES</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 

<ul>	
<li>Fields marked * are required.
<li>Please enter all post secondary Degrees, Diplomas, Certificates and/or Professional Certifications.
<li>If you do not see the Degree, Diploma or Certificate listed below then please contact the NLESD using the following email link <a href="mailto:careers@nlesd.ca?subject=Missing Degree, Diploma or Certificate" title="careers@nlesd.ca">Email HR</a>  to have it added.
</ul>


                           <table class='table table-striped table-condensed' style='font-size:12px;'>
                           <tbody>
                           <tr>
                              <td class="tableTitleL">Name of Institution*:</td>
                              <td class="tableResultL" id="institutionW"><input type="text" name="institution" id="institution" class="form-control"></td>
                              <td class="tableTitleR">Program/Faculty</td> 
                              <td class="tableResultR"><input type="text" name="program" id="program" class="form-control"></td>
                           </tr> 
                           <tr style="display:none;">
                              <td class="tableTitleL">Major:</td>
                              <td class="tableResultL"><job:MajorMinor id="major" cls="form-control" multiple="true" /></td>
                              <td class="tableTitleR">Minor:</td> 
                              <td class="tableResultR"><job:MajorMinor id="minor" cls="form-control" /></td>
                           </tr>    
                           <tr>
                              <td class="tableTitleL">From (mm/yyyy)*:</td>
                              <td class="tableResultL" id="from_dateW"><input type="text" name="from_date" id="from_date" readonly class="form-control" autocomplete="off"></td>
                              <td class="tableTitleR">To (mm/yyyy)*:</td> 
                              <td class="tableResultR" id="to_dateW"> <input type="text" name="to_date" id="to_date" readonly class="form-control" autocomplete="off"></td>
                           </tr>  
                           <tr>
                              <td class="tableTitleL">Type:</td>
                              <td class="tableResultL">
                              			<select id="dtype" name="dtype" class="form-control">                                      	
                                      		<option value="-1" >NOT APPLICABLE</option>
                                      		<option value="1" selected>Degree</option>
                                      		<option value="2">Diploma</option>
                                      		<option value="3">Certificate</option>
                                      	</select>
                              </td>
                              <td class="tableTitleR">Degree/Diploma/Certificate:</td> 
                              <td class="tableResultR">
                              								<div id="divmajor" name="divmajor">
                                      						<!-- listtype for selectbox or checkbox (default). -->
                                      						<job:Degrees id="degree" cls="form-control" listtype="selectbox"/>
                                      						</div>
                                      						<div id="divcert" name="divcert" style="display:none;">
                                      							<select id="dcert" name="dcert" class="form-control">
                                      							<option value="-1" >NOT APPLICABLE</option>
                                      							<%
                                      								for (Map.Entry<Integer, String> entry : hmapc.entrySet()) {
                                      							    	out.println("<option value='" + entry.getKey() + "'>" + entry.getValue() + "</options>");
	                                      							}
	                                      						%>
                                      							</select>
                                      						</div>
                                      						<div id="divdiploma" name="divdiploma"  style="display:none;">
                                      							<select id="ddiploma" name="ddiploma" class="form-control">
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
                              </tbody>
                              </table>
                                       <div align="center"><input type="button" class="btn btn-success btn-xs" value="Add Degree/Diploma/Certificate" onclick="checkneweducationpost()"></div>
                                      
                                        <hr>
                                      
                                        <job:ApplicantEducationPostSS showdelete="edit"/>
                                     
                                        <hr>
                                        
                                     	
                                        <div align="center" style="color:Silver;">Note: Degree/Diploma/Certificate Added Entries save automatically. Press Save/Update for High School Education changes<br/></div>
                        
                           </form>
                           
                           
 </div></div></div></div>                          
 						<%if(request.getAttribute("msg")!=null){%>
							<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
						<%if(request.getAttribute("errmsg")!=null){%>
							<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>                              
                           
  <script>
 $('document').ready(function(){
		$("#from_date,#to_date").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "mm/yy",
	      	yearRange: '-80:+5',
	   });
 });
 </script>                             
</body>
</html>