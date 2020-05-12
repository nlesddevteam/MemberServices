<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.awsd.school.bean.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.database.sds.*"  
         isThreadSafe="false"%>



		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		
<job:ApplicantLoggedOn/>

<%
  ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	JobOpportunityBean[] jobs = null;
	
  SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
  SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
  
  ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
  ApplicantEducationBean[] edu = ApplicantEducationManager.getApplicantEducationBeans(profile.getSIN());
  ApplicantEducationOtherBean edu_oth = ApplicantEducationOtherManager.getApplicantEducationOtherBean(profile.getSIN());
  ApplicantEsdReplacementExperienceBean[] rpl = ApplicantEsdReplExpManager.getApplicantEsdReplacementExperienceBeans(profile.getSIN());
  ApplicantSubstituteTeachingExpBean[] sub = ApplicantSubExpManager.getApplicantSubstituteTeachingExpBeans(profile.getSIN());
  ApplicantExperienceOtherBean[] exp_other = ApplicantExpOtherManager.getApplicantExperienceOtherBeans(profile.getSIN());
  ApplicantOtherInformationBean other_info = ApplicantOtherInfoManager.getApplicantOtherInformationBean(profile.getSIN());
  ApplicantSupervisorBean[] refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
  RegionBean[] regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(profile).values().toArray(new RegionBean[0]);
  Collection<ApplicantDocumentBean> docs = ApplicantDocumentManager.getApplicantDocumentBean(profile);
  Collection<ApplicantCriminalOffenceDeclarationBean> cods = ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans(profile);
  ApplicantNLESDPermanentExperienceBean[] per = ApplicantNLESDPermExpManager.getApplicantNLESDPermanentExperienceBeans(profile.getSIN());
  Map<String, JobOpportunityBean> highlyRecommendedPools = JobOpportunityManager.getApplicantHighlyRecommendedPoolCompetitionsMap(profile.getSIN());
  
  ApplicantPositionOfferBean[] current_offers = null;
  Collection<ApplicantPositionOfferBean> emp_letters = null;
  
  jobs = JobOpportunityManager.getApplicantOpenJobOpportunityBeans(profile.getSIN());
  HashMap<Integer, ApplicantSubListInfoBean> sublists = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
  
  current_offers = ApplicantPositionOfferManager.getApplicantPositionOfferBeans(profile);
  emp_letters = ApplicantPositionOfferManager.getApplicantEmploymentLetters(profile);
  
  EmployeeBean empbean = null;
  if(!StringUtils.isEmpty(profile.getSIN2())){
  	empbean = EmployeeManager.getEmployeeBeanBySIN(profile.getSIN2Unformatted());
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

.tableTitleL {font-weight:bold;width:30%;}
.tableResultL {font-weight:normal;width:20%;}
.tableTitleR {font-weight:bold;width:30%;}
.tableResultR {font-weight:normal;width:20%;}
input {    
    border:1px solid silver;
}

</style>

<script>
 $('document').ready(function(){
	  $("#regPrefs").DataTable(
		{
			"order": [[ 0, "asc" ]],
			"lengthMenu": [[-1], ["All"]],
			"lengthChange": false,
			"searching": false
		}	  
	  );
 });
    </script>



</head>
<body>



<!-- DEMOGRAPHICS --------------------------------------------------------------->  
<div style="font-size:30px;padding-top:10px;color:rgb(0, 128, 0,0.3);font-weight:bold;text-align:left;"><%=profile.getFullNameReverse()%></div>
Your current Teaching/Educational profile information can be found below. If any changes are required, please select the proper menu item above and/or edit link found in each section below. 
There are no registration steps, and instead you can just edit any section of your profile in any order. Please complete your profile as much as possible and ALWAYS keep it updated. <b>Never create a second profile</b> if you forget your login to your previous profile. Having more than one profile may result in missed communications regarding any employment positions and/or applications.
Sections with no information will display a red header. Those completed and/or with entries will display green.
<div class="panel-group" style="padding-top:10px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>1. PROFILE INFORMATION</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_1.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
      			 	       
      			 	       <table class="table table-striped table-condensed" style="font-size:12px;">
							   
							    <tbody>
							    <tr>
							    <td class="tableTitle">NAME:</td>  
							    <td class="tableResult"><%=profile.getFirstname() + " " + ((profile.getMiddlename() != null)?profile.getMiddlename() + " ":"") + profile.getSurname() + ((profile.getMaidenname() != null)?" ("+profile.getMaidenname() + ") ":"")%></td>
								</tr>								
								
								<c:if test="${APPLICANT.modifiedDate ne null}">                               
                                <tr>
							    <td class="tableTitle">LAST MODIFIED:</td>
							    <td class="tableResult"><fmt:formatDate pattern='MMMM dd, yyyy' value='${APPLICANT.modifiedDate}'/></td>
								</tr> 
								</c:if>
								<!-- 
								<tr>
							    <td class="tableTitle">Verification Status:</td>
							    <td colspan=3>							    
							    <c:choose>
					    			<c:when test="${ APPLICANT.profileVerified }">					    				
					    				<c:if test="${APPLICANT.verificationBean ne null}">
					    					<span style="color:Green;"><span class="glyphicon glyphicon-ok"></span> Profile verified by ${APPLICANT.verificationBean.verifiedByName} on ${APPLICANT.verificationBean.getDateVerifiedFormatted()}</span>
					    				</c:if>
										</c:when>
					    			<c:otherwise>
					    				<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> Your Profile has not been verified by HR. Please check again later.</span>
					    			</c:otherwise>
					    		</c:choose>
					    		</td>
							    </tr>
								 -->
							<tr>
								<td class="tableTitle">ADDRESS:</td>
								<td class="tableResult"><%=profile.getAddress1() + " &middot; " + profile.getAddress2() + ", " + profile.getProvince() + " &middot; "
		+ profile.getCountry() + " &middot; " + profile.getPostalcode()%>
								</td>
							</tr>

							<tr>
								<td class="tableTitle">TELEPHONE:</td>
								<td class="tableResult"><%=profile.getHomephone()%></td>
							</tr>

							<%
								if (!StringUtils.isEmpty(profile.getWorkphone())) {
							%>
							<tr>
								<td class="tableTitle">WORK PHONE:</td>
								<td class="tableResult"><%=profile.getWorkphone()%></td>
							</tr>
							<%
								}
							%>

							<%
								if (!StringUtils.isEmpty(profile.getCellphone())) {
							%>
							<tr>
								<td class="tableTitle">CELL PHONE:</td>
								<td class="tableResult"><%=profile.getCellphone()%></td>
							</tr>
							<%
								}
							%>

							<tr>
							    <td class="tableTitle">EMAIL</td>
							    
							    
							    <!-- Check to see if email is old esdnl, wsdnl, ncsd, or lsb. -->
	                                      <c:set var="emailCheck" value ="<%=profile.getEmail()%>"/>
		                                      <c:choose>
			                                      <c:when test="${fn:endsWith(emailCheck,'@esdnl.ca') or fn:endsWith(emailCheck,'@wnlsd.ca') or fn:endsWith(emailCheck,'@lsb.ca') or fn:endsWith(emailCheck,'@ncsd.ca')}">
			                                       <td class="tableResult" title="Email needs updating. Invalid address." style="border:1px solid red;background-color:#fde8ec;"><span><%=profile.getEmail()%> (Email is outdated, please update to valid email.)</span></td>   
			                                      </c:when>
		                                      <c:otherwise>
		                                      		 <td class="tableResult"><a href="mailto:<%=profile.getEmail()%>?subject=Applicant Profile"><%=profile.getEmail()%></a></td>  
		                                      </c:otherwise>
	                                      </c:choose>	
							    
							    </tr> 
                                 
                                <%if((profile!=null)&&(profile.getDOB()!=null)){%>    
								<tr>
							    <td class="tableTitle">DATE OF BIRTH:</td>
							    <td class="tableResult"><%=profile.getDOBFormatted()%></td>
								</tr>
                                <%}%>
  							</tbody>
  							</table>
  							</div>
  </div></div></div>                             
                                
  
                             
   
  <!-- 2A. NLESD EXPERIENCE ------------------------------------------------------------------------------------>
   
   
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section2a">   
	               	<div class="panel-heading"><b>2a. NLESD  EXPERIENCE</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_2.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">  
   								
   						  	
                               <%if((esd_exp != null)&&(esd_exp.getPermanentContractSchool() != 0)&&(esd_exp.getPermanentContractSchool() != -1)){%>
                               <table class="table table-striped table-condensed" style="font-size:11px;">
   						  		<tbody>	
                                <tr>
							    <td class="tableTitle">PERMANENT CONTRACT SCHOOL:</td>
							    <td class="tableResult"><%=esd_exp.getPermanentContractLocationText()%></td>
								</tr>
                                <tr>
							    <td class="tableTitle">PERMANENT CONTRACT POSITION:</td>
							    <td class="tableResult"><%=esd_exp.getPermanentContractPosition()%></td>
								</tr>  
								 </tbody>
                                </table>     
                               <%} else if((esd_exp != null)&&(esd_exp.getContractSchool() != 0)&&(esd_exp.getContractSchool() != -1)){%>
                               <table class="table table-striped table-condensed" style="font-size:11px;">
   						  		<tbody>	
                                <tr>
							    	<td class="tableTitle">REPLACEMENT CONTRACT SCHOOL:</td>
							    	<td class="tableResult"><%=esd_exp.getReplacementContractLocationText()%></td>
								</tr>
                                <tr>
							    	<td class="tableTitle">REPLACEMENT CONTRACT END DATE:</td>
							    	<td class="tableResult"><%=esd_exp.getFormattedContractEndDate()%></td>
								</tr>
								 </tbody>
                                </table>   
                               <%} else {%>                                                     	
                                                                                             
                                   <span style="color:Grey;">No Newfoundland &amp; Labrador English School District Experience currently on file.</span>
                                   <script>$("#section2a").removeClass("panel-success").addClass("panel-danger");</script>
                                <%}%>
                                <%if(empbean != null){%>
                               <table class="table table-striped table-condensed" style="font-size:11px;">
   						  		<tbody>	
                                <tr>
							    <td class="tableTitle">SDS Employee ID:</td>
							    <td class="tableResult"><%=empbean.getEmpId() %></td>
								</tr>
                                <tr>
							    <td class="tableTitle">Years of Service:</td>
							    <td class="tableResult">
							    	<% 
							    		if((empbean != null ) && (empbean.getSeniority(EmployeeSeniorityBean.Union.NLTA) != null)) {
							    			NumberFormat nf = new DecimalFormat("0.00");
							    			EmployeeSeniorityBean esb = empbean.getSeniority(EmployeeSeniorityBean.Union.NLTA);
							    			out.println("PROVINCIAL: " + nf.format(esb.getSeniorityValue1()) + " yrs<br />");
							    			out.println("OUT OF PROVINCE: " + nf.format(esb.getSeniorityValue2()) + " yrs");
							    		} 
							    		else {
							    			out.println("N/A");
							    	 	} 
							    	%>
							  	</td>
								</tr>  
								 </tbody>
                                </table> 
                                <%}%>
  
  </div></div></div></div> 
   
   
   
   
  <!-- 2B. PERMANENT EXPERIENCE ------------------------------------------------------------------------------------>
   
   
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section2b">   
	               	<div class="panel-heading"><b>2b. NLESD PERMANENT EXPERIENCE</b> (Total Months: <%=((esd_exp != null)?Integer.toString(esd_exp.getPermanentLTime()):"UNKNOWN")%>) <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_2_perm_exp.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">  
   								
   								
                                <% if((per != null) && (per.length > 0)) { %>
                                
                                <table class="table table-striped table-condensed" style="font-size:11px;">
							    <thead>
							    <tr>
                                 <th width="10%">FROM</th>
                                 <th width="10%">TO</th>
                                 <th width="30%">SCHOOL</th>
                                 <th width="50%">GRADES/SUBJECTS TAUGHT</th>
                                </tr>
                                </thead>
                                <tbody>	                               	
                                 <% for(int i=0; i < per.length; i ++){ %>
                                     <tr>
                                      <td><%=sdf.format(per[i].getFrom())%></td>
                                      <td><%=sdf.format(per[i].getTo())%></td>
                                      <td><%=SchoolDB.getSchool(per[i].getSchoolId()).getSchoolName()%></td>
                                      <td><%=per[i].getGradesSubjects()%></td>
                                     </tr>
                                  <% } %>
                                  </tbody>
                                  </table>
                                  <% } else {%>                                  
                                   <span style="color:Grey;">No Newfoundland &amp; Labrador English School District Permanent Experience currently on file.</span>
                                   <script>$("#section2b").removeClass("panel-success").addClass("panel-danger");</script>
                                <% } %>
  
  </div></div></div></div>
                                
 <!-- 2C. REPLACEMENT CONTRACT EXPERIENCE ------------------------------------------------------------------------------------>                               
                
                                 
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section2c">   
	               	<div class="panel-heading"><b>2c. NLESD REPLACEMENT CONTRACT EXPERIENCE</b> (Total Months: <%=((esd_exp != null)?Integer.toString(esd_exp.getReplacementTime()):"UNKNOWN")%>) <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_2_repl_exp.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
								<%  if((rpl != null) && (rpl.length > 0)) { %>
                                <table class="table table-striped table-condensed" style="font-size:11px;">
							    <thead>
							    <tr>
                                 <th width="10%">FROM</th>
                                 <th width="10%">TO</th>
                                 <th width="30%">SCHOOL</th>
                                 <th width="50%">GRADES/SUBJECTS TAUGHT</th>
                                </tr>
                                </thead>
                                <tbody>
                                  <% for(int i=0; i < rpl.length; i ++) { %>
                                      <tr>
                                      <td><%=sdf.format(rpl[i].getFrom())%></td>
                                      <td><%=sdf.format(rpl[i].getTo())%></td>
                                      <td><%=SchoolDB.getSchool(rpl[i].getSchoolId()).getSchoolName()%></td>
                                      <td><%=rpl[i].getGradesSubjects()%></td>
                                      </tr>
                                  <% } %>
                                  </tbody>
                                  </table>
                                  <% } else {%>                                  
                                   <span style="color:Grey;">No Newfoundland &amp; Labrador English School District Replacement Contract Experience currently on file.</span>
                                   <script>$("#section2c").removeClass("panel-success").addClass("panel-danger");</script>
                                <% } %>
  </div></div></div></div>             
 
 <!-- 3. EXPERIENCE WITH OTHER BOARDS ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section3">   
	               	<div class="panel-heading"><b>3. EXPERIENCE WITH OTHER BOARDS</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_3.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">   
                                
                                <% if((exp_other != null) && (exp_other.length > 0)) { %>                                   
                                	 <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="10%">FROM</th>
                                       <th width="10%">TO</th>                                       
                                       <th width="30%">SCHOOL &amp; BOARD</th>
                                       <th width="50%">GRADES/SUBJECTS TAUGHT</th>
                                      </tr>
                                      </thead>
                                      <tbody>
                                      <%for(int i=0; i < exp_other.length; i ++) { %>
                                      <tr>
                                      <td><%=sdf.format(exp_other[i].getFrom())%></td>
                                      <td><%=sdf.format(exp_other[i].getTo())%></td>
                                      <td><%=exp_other[i].getSchoolAndBoard()%></td>
                                      <td><%=exp_other[i].getGradesSubjects()%></td>
                                      </tr>
                                      <% } %>
                                      </tbody>
                                      </table>
                                      <% } else {%>                                  
                                       <span style="color:Grey;">No Experience with other Boards currently on file.</span>
                                       <script>$("#section3").removeClass("panel-success").addClass("panel-danger");</script>
                                    <% } %>
 </div></div></div></div>                                
 
 <!-- 4. SUBSTITUTE TEACHING EXPERIENCE ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section4">   
	               	<div class="panel-heading"><b>4. SUBSTITUTE TEACHING EXPERIENCE</b> (Total Sub Days: <%=((esd_exp != null)?Integer.toString(esd_exp.getSubstituteTime()):"UNKNOWN")%>) <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_4.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">                
                                
                                <% if((sub != null) && (sub.length > 0)) { %>
                                	  
                                	<table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="25%">FROM</th>
                                       <th width="25%">TO</th>                                       
                                       <th width="50%"># DAYS PER YEAR</th>
                                      </tr>
                                      </thead>
                                      <tbody>
                                	  <% for(int i=0; i < sub.length; i ++) { %>
                                      <tr>
                                      <td><%=sdf.format(sub[i].getFrom())%></td>
                                      <td><%=sdf.format(sub[i].getTo())%></td>                                      
                                      <td><%=sub[i].getNumDays()%></td>
                                      </tr>
                                      
                                      <% } %>
                                      </tbody>
                                      </table>
                                      <% } else {%>                                  
                                       <span style="color:Grey;">No Newfoundland &amp; Labrador English School District Substitute Teaching Experience currently on file.</span>
                                       <script>$("#section4").removeClass("panel-success").addClass("panel-danger");</script>
                                    <% } %>
  </div></div></div></div>
  
  <!-- 5. UNIVERSITY/COLLEGE EDUCATION ------------------------------------------------------------------->
  
  
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section5">   
	               	<div class="panel-heading"><b>5. UNIVERSITY/COLLEGE EDUCATION</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_5.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
      			 	       
      			 				<% if((edu != null) && (edu.length > 0)) { %>
      			 				<table class="table table-striped table-condensed" style="font-size:11px;">
  								<thead>
                                  <tr>
                                    <th width="24%">Institution</th>
                                    <th width="8%">From</th>
                                    <th width="8%">To</th>
                                    <th width="20%">Program &amp; Faculty</th>
                                    <th width="25%">Major(s)(#crs)/Minor(#crs)</th>
                                    <th width="15%">Degree Conferred</th>
                                  </tr>
                                 </thead>
                                  <tbody>
                                  <% for(int i=0; i < edu.length; i ++) { %>
                                       <tr>
                                       <td><%=edu[i].getInstitutionName()%></td>
                                       <td><%=sdf.format(edu[i].getFrom())%></td>
                                       <td><%=sdf.format(edu[i].getTo())%></td>
                                       <td><%=edu[i].getProgramFacultyName()%></td>
                                       <td><span style="color:Navy;">Major(s):</span>                                      
                                        <% if(edu[i].getMajor_other() > 0) { %>
                                        <%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName() + (edu[i].getMajor_other() > 0 ? ", " + SubjectDB.getSubject(edu[i].getMajor_other()).getSubjectName():"") +	" (" + edu[i].getNumberMajorCourses()+ ")<br/>"%> 
                                      	<% } else if(edu[i].getMajor() != -1) { %>
                                         <%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName() + " (" + edu[i].getNumberMajorCourses()+ ")<br/>"%>        
                                        <%} else {%>
                                       		N/A<br/>
										<%} %>
                                        
                                        
                                       <span style="color:Green;">Minor(s):</span>
                                        <%                                        
                                        if(edu[i].getMinor() != -1) {%>
                                         <%=SubjectDB.getSubject(edu[i].getMinor()).getSubjectName() + " (" + edu[i].getNumberMinorCourses()+ ")"%>
                                       <% } else { %>
                                          N/A
                                         <%} %>  
                                        </td>                                          
                                        <td><%=((!StringUtils.isEmpty(edu[i].getDegreeConferred()))?DegreeManager.getDegreeBeans(edu[i].getDegreeConferred()).getAbbreviation():"&nbsp;")%></td>
                                        </tr>
                                     <% } %>
                                
                                 </tbody>
                                 </table>
                                  
                                    <%} else { %>
                                     <span style="color:Grey;">No University/College education currently on file.</span>
                                     <script>$("#section5").removeClass("panel-success").addClass("panel-danger");</script>
                                   <% }%>
</div></div></div></div>                                


<!-- 6. EDUCATION OTHER ------------------------------------------------------------------------------------------------->

                                
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section6">   
	               	<div class="panel-heading"><b>6. EDUCATION (OTHER)</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_6.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
      			 	       
      			 	            <%if(edu_oth != null){%>
                                    
                                <table class="table table-striped table-condensed" style="font-size:12px;">
							   
							    <tbody>
							    <tr>
							    <td class="tableTitleL">TRAINING METHOD:</td>
							    <td class="tableResultL"><%=edu_oth.getProfessionalTrainingLevel().getDescription()%></td>								
							    <td class="tableTitleR"># SPECIAL ED. COURSES:</td>
							    <td class="tableResultR"><%=edu_oth.getNumberSpecialEducationCourses()%></td>
								</tr>
								   
                                <tr>
							    <td class="tableTitleL"># FRENCH LANGUAGE COURSES:</td>
							    <td class="tableResultL"><%=edu_oth.getNumberFrenchCourses()%></td>
								<td class="tableTitleR"># MATH COURSES:</td>
							    <td class="tableResultR"><%=edu_oth.getNumberMathCourses()%></td>
								</tr>
								 <tr>
							    <td class="tableTitleL"># ENGLISH COURSES:</td>
							    <td class="tableResultL"><%=edu_oth.getNumberEnglishCourses()%></td>								
							    <td class="tableTitleR"># MUSIC COURSES:</td>
							    <td class="tableResultR"><%=edu_oth.getNumberMusicCourses()%></td>
								</tr>      
                                 <tr>
							    <td class="tableTitleL"># TECHNOLOGY COURSES:</td>
							    <td class="tableResultL"><%=edu_oth.getNumberTechnologyCourses()%></td>								
							    <td class="tableTitleR"># SCIENCE COURSES:</td>
							    <td class="tableResultR"><%=edu_oth.getNumberScienceCourses()%></td>
								</tr>
								<tr>
							    <td class="tableTitleL"># SOCIAL STUDIES COURSES:</td>
							    <td class="tableResultL"><%=edu_oth.getNumberSocialStudiesCourses()%></td>								
							    <td class="tableTitleR"># ART COURSES:</td>
							    <td class="tableResultR"><%=edu_oth.getNumberArtCourses()%></td>
								</tr>
								<tr>
							    <td class="tableTitleL">(TLA) TOTAL # COURSES COMPLETE:</td>
							    <td class="tableResultL"><%=edu_oth.getTotalCoursesCompleted()%></td>	
							    <td class="tableTitleR"></td>
							    <td class="tableResultR"></td>						 
							    </tr>
							    
							    <tr>							
							    <td class="tableTitleL">CERTIFICATION LEVEL:</td>
							    <td class="tableResultL"><%=!StringUtils.isEmpty(edu_oth.getTeachingCertificateLevel()) ? edu_oth.getTeachingCertificateLevel() : "N/A"%></td>
								<td class="tableTitleR">CERTIFICATION ISSUE DATE:</td>
							    <td class="tableResultR"><%=((edu_oth.getTeachingCertificateIssuedDate() != null) ? sdf.format(edu_oth.getTeachingCertificateIssuedDate()) : "N/A")%></td>
								</tr>        
                                      
                          
                                   </tbody>
                                   </table>
                                   
                                  <%}else{%>                                    
                                     <span style="color:Grey;">No other education currently on file.</span>
                                     <script>$("#section6").removeClass("panel-success").addClass("panel-danger");</script>
                                  <%}%>
                                
  </div></div></div></div> 
               
 
  <!--7. OTHER INFORMATION ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section7">   
	               	<div class="panel-heading"><b>7. OTHER INFORMATION</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_7.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive" style="font-size:11px;">
 							  <%if((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())) { %>
                                   
                                  <%=other_info.getOtherInformation().replaceAll(new String( new char[]{((char)10)} ),"<br>")%>
                                  
                              <%}else{%>
                                   <span style="color:Grey;">No Other Information currently on file.</span>
                                   <script>$("#section7").removeClass("panel-success").addClass("panel-danger");</script>
                              <%}%>
  </div></div></div></div>
  
 <!-- 8. REFERENCES ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section8">   
	               	<div class="panel-heading"><b>8. REFERENCES</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_8.jsp">EDIT/VIEW</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">                 
               
               						<% if((refs != null) && (refs.length > 0)){ %>
                                  
                                  <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="15%">NAME</th>
                                       <th width="25%">TITLE</th>                                       
                                       <th width="25%">PRESENT ADDRESS</th>
                                       <th width="15%">TELEPHONE</th>
                                       <th width="10%">STATUS</th>       
                                       <th width="10%">OPTIONS</th>                                
                                      </tr>
                                      </thead>
                                      <tbody>
                                  		<%for(int i=0; i < refs.length; i ++) { %>
                                  
                                  
                                      <tr>
                                      <td><%=refs[i].getName()%></td>
                                      <td><%=refs[i].getTitle()%></td>
                                      <td><%=refs[i].getAddress()%></td>
                                      <td><%=refs[i].getTelephone()%></td> 
                                      <td>
                                      <% if(refs[i].getApplicantRefRequestBean() == null){ %>
                                      	<span style='color:DimGrey;'>NOT SENT</span>
                                      <%} else{ %>
                                      	<%if (refs[i].getApplicantRefRequestBean().getRequestStatus() ==  null) { %>
                                      		<span style='color:DimGrey;'>NOT SENT</span>
                                      	<%}else{ %>
                                      		<%if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST SENT")){ %>
                                      			<span style='color:Navy;'>PENDING</span>
                                      		<%} else if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")) {%>
                                      			<span style='color:Green;'>COMPLETED</span>
                                      		<%} else if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST DECLINED")) {%>
												<span style='color:Red;'>DECLINED</span>
									   		<% } else {%>
									   			<span style='color:DimGrey;'>NOT SENT</span>
									   		<%} %>
									   	<%} %>
                                      <% } %>
                                      
										</td>  
										<td>
										<%if ((!(refs[i].getApplicantRefRequestBean() == null)) && (!(refs[i].getApplicantRefRequestBean().getRequestStatus() ==  null)) && (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED"))) { %>
										<a class='btn btn-xs btn-success' href='viewNLESDReferenceApp.html?id=<%=refs[i].getApplicantRefRequestBean().getFkReference()%>&reftype=<%=refs[i].getApplicantRefRequestBean().getReferenceType()%>'>VIEW</a></td>
										<%}%>                                   
                                      </tr>
                                  	<% } %>
                                      </tbody>
                                      </table>
                                   <% } else {%>                                  
                                       <span style="color:Grey;">No References currently on file.</span>
                                       <script>$("#section8").removeClass("panel-success").addClass("panel-danger");</script>
                                    <% } %>
                                
 
 
 </div></div></div></div>
                                
  <!--9.  REGIONAL PREFERENCES ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section9">   
	               	<div class="panel-heading"><b>9. REGIONAL PREFERENCES</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_9.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">                               
               
               <%if((regionPrefs != null) && (regionPrefs.length > 0)) { 
               int cntr=0;
               %>
						<table class="table table-striped table-condensed" id="regPrefs" style="font-size:11px;">
      							    <thead>
      							    <tr>                                       
                                       <th width="45%">REGION</th>                                                                              
                                       <th width="45%">REGIONAL ZONE</th>                                  
                                      </tr>
                                      </thead>
                                      <tbody>						
							
						<% for(int i=0; i < regionPrefs.length; i ++){ %>	
						
                        <tr>                        
                        <td>
                        <c:set var="whatRegion" value="<%=regionPrefs[i].getZone()%>"/>
                        <c:set var="whatZone" value="<%=regionPrefs[i].getName()%>"/>
						<c:choose>
						<c:when test="${whatRegion eq 'avalon' or whatRegion eq 'eastern' }">Avalon Region</c:when>
						<c:when test="${whatRegion eq 'central'}">Central Region</c:when>
						<c:when test="${whatRegion eq 'western'}">Western Region</c:when>
						<c:when test="${whatRegion eq 'labrador'}">Labrador Region</c:when>
						<c:when test="${whatRegion eq 'provincial'}">Provincial</c:when>
						<c:otherwise><span style="color:Red;">ERROR: Unknown Region</span></c:otherwise>
						</c:choose>
                        </td>
                        <td>                        
                        <c:choose>
						<c:when test="${whatZone eq 'nlesd - provincal' or whatZone eq 'nlesd - provincial'}">All Province</c:when>
						<c:when test="${whatZone eq 'all labrador region'}">All Labrador</c:when>	
						<c:when test="${whatZone eq 'all western region'}">All Western Zone</c:when>
						<c:when test="${whatZone eq 'all central region'}">All Central Zone</c:when>
						<c:when test="${whatZone eq 'all eastern region' or whatZone eq 'all avalon region'}">All Avalon Zone</c:when>							
						<c:otherwise><span style="text-transform:Capitalize;"><%=regionPrefs[i].getName()%> Regional Zone</span></c:otherwise>
						</c:choose>
                        
                        </td>
                        </tr>
                       <% } %>
						</tbody>
                         </table>
                    	
                    	<%} else { %>
                    		 <span style="color:Grey;">No regional preferences have been selected.</span>
                    		 <script>$("#section9").removeClass("panel-success").addClass("panel-danger");</script>
                    	<%} %>
               
               
       </div></div></div></div>        
               
               

 
 <!--10.  DOCUMENTS ----------------------------------------------------------------------------------------->


	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section10">
			<div class="panel-heading">
				<b>10. DOCUMENTS</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_10.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						if ((docs != null) && (docs.size() > 0)) {
						int i = 0;
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="25%">TYPE</th>
								<th width="30%">UPLOAD DATE</th>
								<th width="10%">OPTIONS</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (ApplicantDocumentBean doc : docs) {
							%>
							<tr>
								<td><%=doc.getType().getDescription()%></td>
								<td><%=sdf_long.format(doc.getCreatedDate())%></td>
								<td><a class='btn btn-xs btn-primary'
									href='viewDocument.html?id=<%=doc.getDocumentId()%>'
									target='_blank'>View</a></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No Documents currently on file.</span>
					<script>
						$("#section10").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>


	<!-- CRIMINAL OFFENCE DECLARATIONS ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section11">   
	               	<div class="panel-heading"><b>CRIMINAL OFFENCE DECLARATIONS</b><span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_10_CODF.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">
									<%
										if ((cods != null) && (cods.size() > 0)) {
										int i = 0;
									%>	                                  	
	                                  	<table class="table table-striped table-condensed" style="font-size:11px;">
      							    	<thead>
      							    	<tr>
                                       	<th width="90%">DECLARATION DATE</th>                                                                           
                                       	<th width="10%">OPTIONS</th>                                                                           
                                      	</tr>
                                      	</thead>
                                      	<tbody>
	                                  	<%for(ApplicantCriminalOffenceDeclarationBean cod : cods) { %>
	                                      <tr>
	                                      <td><%=sdf_long.format(cod.getDeclarationDate())%></td>
	                                      <td><a class='btn btn-xs btn-primary' href='viewCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>' target='_blank'>VIEW</a></td>
	                                     </tr>
	                                      <%} %>  
	                                   	</tbody>
	                                   	</table>	                                      
	                                      <% } else {%>                                  
	                                       <span style="color:Grey;">No Documents currently on file.</span>
	                                       <script>$("#section10").removeClass("panel-success").addClass("panel-danger");</script>
	                                    <% } %>
  </div></div></div></div>
  
  <!--10.  SUBSTITUTE PREFERENCES ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section16">   
	               	<div class="panel-heading"><b>SUBSTITUTE PREFERENCES</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_substitute_preferences.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 







</div></div></div></div>

<!-- HIGHLY RECOMMENDED POOL COMPETITIONS ----------------------------------------------------------------------------------------->
<% if(highlyRecommendedPools.size() > 0) { %>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section12">
			<div class="panel-heading">
				<b>Pool Competitions with HIGHLY RECOMMENDED status</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="20%">COMP #</th>
								<th width="50%">TITLE</th>
								<th width="20%">LOCATION</th>
								<th width="10%">OPTIONS</th>
							</tr>
						</thead>
						<tbody>
							<% for (JobOpportunityBean j : highlyRecommendedPools.values()) { %>
								<tr>
									<td><%= j.getCompetitionNumber() %></td>
									<td><%= j.getPositionTitle( )%></td>
									<td><%= j.getJobLocation() %></td>
									<td><a href="" class="btn btn-xs btn-primary showModal"
										data-href="/employment/view_job_post.jsp?comp_num=<%=j.getCompetitionNumber()%>">VIEW</a></td>
								</tr>
							<% } %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
<% } %>

	<!-- CURRENT JOB COMPETITION APPLICATION(S) ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section12">   
	               	<div class="panel-heading"><b>CURRENT JOB APPLICATION(S)</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">  
					
					<%
  											if (jobs.length > 0) {
  										%>        
					<table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="20%">COMP #</th>
                                       <th width="50%">TITLE</th>                                       
                                       <th width="20%">CLOSING DATE</th>
                                       <th width="10%">OPTIONS</th>
                                      </tr>
                                      </thead>
                                      <tbody>
                     						<%for(int i=0; i < jobs.length; i++){%>
			                                    <tr>
			                                      <td><%=jobs[i].getCompetitionNumber()%></td>
			                                      <td><%=jobs[i].getPositionTitle()%></td>
			                                      <td><%=jobs[i].getFormatedCompetitionEndDate()%></td>
			                                      <td><a href="" class="btn btn-xs btn-primary showModal" data-href="/employment/view_job_post.jsp?comp_num=<%=jobs[i].getCompetitionNumber()%>">VIEW</a></td>
			                                    </tr>
			                                 <% } %>
                                      </tbody>
									</table>
                          
                          					<%}else{%>
                          					<span style="color:Grey;"> No open job applications on file.</span>
                                       		<script>$("#section12").removeClass("panel-success").addClass("panel-danger");</script>
                                            
											<%} %>
									
</div></div></div></div>


 <!-- CURRENT SUB LIST APPLICATION(S) ----------------------------------------------------------------------------------------->               
                
                
 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section13">   
	               	<div class="panel-heading"><b>CURRENT SUBLIST APPLICATION(S)</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">          
                                       
                                            <%
                                            @SuppressWarnings("unchecked")
                                            Map.Entry<Integer, ApplicantSubListInfoBean>[] entries = (Map.Entry<Integer, ApplicantSubListInfoBean>[])sublists.entrySet().toArray(new Map.Entry[0]);
			                                  	ApplicantSubListInfoBean info = null;
			                                  	if(entries.length > 0){ %>
			                                  	<table class="table table-striped table-condensed" style="font-size:11px;">
				      							    <thead>
				      							    <tr>
				                                       <th width="60%">LIST (YEAR)/REGION</th>
				                                       <th width="20%">DATE APPLIED</th>                                       
				                                       <th width="20%">STATUS</th>
				                                      </tr>
				                                      </thead>
				                                      <tbody>
			                                 <% for(int i=0; i < entries.length; i++){
			                                    	info = (ApplicantSubListInfoBean) entries[i].getValue();
			                                  %>
			                                  <tr>
                                                  <td style="text-transform:Capitalize;"><%=info.getSubList().getTitle()%> (<%=info.getSubList().getSchoolYear()%>) - <%=info.getSubList().getRegion().getName()%> </td>
			                                      <td><%=info.getAppliedDateFormatted()%></td>
			                                      <td><%=(info.isNewApplicant()?"<span style='background-color:Blue;color:white;'>&nbsp;NEW&nbsp;</span>":(info.isShortlisted()?"<span style='background-color:Green;color:white;'>&nbsp;APPROVED&nbsp;</span>":(info.isNotApproved()?"<span style='background-color:Red;color:white;'>&nbsp;NOT APPROVED&nbsp;</span>":"<span style='background-color:Yellow;color:black;'>&nbsp;IN POSITION / REMOVED&nbsp;</span>")))%></td>
			                                   </tr>
			                                   <% } %>
                                      				</tbody>
													</table>
                          
                          					<%}else{%>
                          					<span style="color:Grey;"> No open job applications on file.</span>
                                       		<script>$("#section13").removeClass("panel-success").addClass("panel-danger");</script>
                                            
											<%} %>

</div></div></div></div>	

 <!-- CURRENT OFFERS(S) ----------------------------------------------------------------------------------------->               
                
 
                
 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section14">   
	               	<div class="panel-heading"><b>CURRENT OFFERS(S)</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">          
                                       <%if(current_offers != null && current_offers.length > 0){%>
                                            <ul>
		                                 		<%for(int i=0; i < current_offers.length; i++){ 
		                                 				JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(current_offers[i].getJob());	%>
		                                    	<li><a class="menu" href="/MemberServices/Personnel/applicantPositionOfferController.html?id=<%=current_offers[i].getRecommendation().getRecommendationId()%>"><%=ass[0].getLocationText() + " (" + current_offers[i].getJob().getPositionTitle() + ")"%></a></li>
		                                     	<%}%>
		                                    </ul>
										 <%} else {%>										 
										 No current offer(s) currently posted.
										 <script>$("#section14").removeClass("panel-success").addClass("panel-danger");</script>
										 <%}%>
										 
</div></div></div></div>	



<!-- LETTERS OF EMPLOYMENT ----------------------------------------------------------------------------------------->               
                

                
 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section15">   
	               	<div class="panel-heading"><b>LETTER(S) OF EMPLOYMENT</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">          
                                        <%if(emp_letters != null && emp_letters.size()> 0) {%>
                                            <ul>
	                                 			<%for(ApplicantPositionOfferBean letter : emp_letters){ 
	                                 				JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(letter.getJob());	%>
	                                     		<li><a class="menu" href="/MemberServices/Personnel/viewLetterOfEmployment.html?id=<%=letter.getRecommendation().getRecommendationId()%>"><%=ass[0].getLocationText() + " (" + letter.getJob().getPositionTitle() + ")"%></a></li>
	                                     		<%}%>
	                                   		</ul>
                                            <%} else {%>										 
										 No letter(s) of employment currently listed.
										 <script>$("#section15").removeClass("panel-success").addClass("panel-danger");</script>
										 <%}%>
</div></div></div></div>	


		                                 	
                                    
                                  
                                  


						
<div align="center" class="no-print"><a href="/employment/index.jsp?finished=true" class="btn btn-sm btn-danger">Back to Employment</a></div>
<br/><br/>


<!-- View Job -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">View Competition Application</h4>
      </div>
      <div class="modal-body">
       <iframe src="" width="500" height="400"></iframe>    
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>


<script>
$(document).ready(function(){
	 $(".showModal").click(function(e){
	   e.preventDefault();
	   var url = $(this).attr("data-href");
	   $("#myModal iframe").attr("src", url);
	   $("#myModal").modal("show");
	 });
	});

</script>                            
                     
</body>
</html>
