<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.awsd.school.bean.*,
                 com.esdnl.util.*,
                 java.text.SimpleDateFormat,  
				 java.util.Date,
				 java.util.concurrent.TimeUnit,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.database.sds.*,
                 java.util.stream.*"
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		
<job:ApplicantLoggedOn/>

<%
	ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
	SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	ApplicantNLESDExperienceSSBean esd_exp = null;
	ApplicantEducationSecSSBean edu = null;
	ApplicantEducationOtherSSBean other_info = null;
	RegionBean[] regionPrefs = null;
	ApplicantSupervisorBean[] refs = null;
	//HashMap<Integer, ApplicantRegionalJobPoolSSBean> hmap = new HashMap<Integer, ApplicantRegionalJobPoolSSBean>();
	Collection<ApplicantDocumentBean> docs = null;
	HashMap<Integer, ApplicantCurrentPositionBean> hmapcur = new HashMap<Integer, ApplicantCurrentPositionBean>();

	esd_exp = ApplicantNLESDExperienceSSManager.getApplicantNLESDExperienceSSBeanBySin(profile.getSIN());
	edu = ApplicantEducationSecSSManager.getApplicantEducationSecSSBeanBySin(profile.getSIN());
	other_info = ApplicantEducationOtherSSManager.getApplicantEducationOtherSSBean(profile.getSIN());
	regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(profile).values().toArray(
			new RegionBean[0]);
	//hmap = ApplicantRegionalJobPoolSSManager.getApplicantRegionalJobPoolPreferencesMap(profile.getSIN());
	refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
	docs = ApplicantDocumentManager.getApplicantDocumentBean(profile).stream().filter(dd -> dd.getTypeSS() != null).collect(Collectors.toList());
	boolean hasCOE = false;
	
	hmapcur = ApplicantCurrentPositionManager.getApplicantCurrentPositionBeanMap(profile.getSIN());
	EmployeeBean empbean = null;
	if (!StringUtils.isEmpty(profile.getSIN2())) {
		empbean = EmployeeManager.getEmployeeBeanBySIN(profile.getSIN2Unformatted());
	}
	Collection<ApplicantCriminalOffenceDeclarationBean> cods = ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans(profile);
	ApplicantSecurityBean security_question = null;
    
  	if(profile != null)
  	{
    	//create function to get values
	  security_question = ApplicantSecurityManager.getApplicantSecurityBean(profile.getSIN());
  	}  
%>
<c:set var="SDSID" value="<%=empbean != null?empbean.getEmpId():\"N/A\" %>"/>
<c:set var="seniorityTotal" value="<%=empbean != null && empbean.getSeniority() != null ? empbean.getSeniority().getSeniorityTotal() :\"\"  %>"/>
<c:set var="SDSBEAN" value="<%=empbean == null ? null : empbean.getSenioritySupport() ==  null ? null: empbean.getSenioritySupport()%>"/>
<c:set var="SBEAN" value="<%=empbean != null?empbean:null %>"/> 
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
.tableResultL {font-weight:normal;width:40%;}
.tableTitleR {font-weight:bold;width:20%;}
.tableResultR {font-weight:normal;width:40%;}
input {    
    border:1px solid silver;
}

</style>

</head>
<body>

<!-- DEMOGRAPHICS --------------------------------------------------------------->  
<div style="font-size:30px;padding-top:10px;color:rgb(0, 128, 0,0.3);font-weight:bold;text-align:left;"><%=profile.getFullNameReverse()%></div>
<span style="color:Grey;font-weight:bold;">SUPPORT STAFF/STUDENT ASSISTANT/MANAGEMENT PROFILE</span><br/><br/>


<div id="COENotice" class="alert alert-warning" style="display:none;text-align:center;">*** <b style="font-size:16px;">NOTICE: Missing Code of Ethics and Conduct Declaration Certificate</b> ***<br/><br/>
	Your profile is currently missing the <b>Code of Ethics and Conduct Training Declaration Certificate</b>. 
	
	This training is <b>mandatory for all staff</b> and new hires as communicated in a memo on February 8, 2022. 

Please upload this document as soon as possible to the <b>Documents Section</b> of your Profile (Section 10 for a Teaching Profile/Section 7 for a Support Profile).  
Please ensure that the document is uploaded by selecting <i>Code of Ethics and Conduct Declaration</i> as the Document Type.  

Profiles are considered <b>incomplete</b> for existing staff if this certificate is not uploaded properly and may affect job opportunities.  New hires will need to complete and upload prior to commencement of position.  

Link to the Training and Reference Materials can be <a href="/MemberServices/" target="_blank">found here</a> under Staff Training Modules icon.

	</div>
<%if(profile != null){%>

<%if(security_question == null){ %>

<div class="alert alert-danger" style="text-align:center;"><span style="font-size:16px;font-weight:bold;">*** IMPORTANT NOTICE ***</span><br/>You have NOT completed your secure question/answer for password recovery. Please enter a secure question and answer that you will remember and ONLY you would know in case you need to reset your password. 
If you fail to complete this, you will not be able to reset your password and will need to contact support if your login fails. Click link below to set this up now.<br/><br/>

																									  
<div align="center"><a href="/MemberServices/Personnel/applicant_security.jsp" class="btn ntn-sm btn-danger">PASSWORD RECOVERY SETUP</a></div>
</div>

<%}%>
<%}%>

Your current <b>Support Staff/SA/Management profile</b> information can be found below. If any changes are required, please select the proper menu item above and/or edit link found in each section below. 
There are no registration steps, and instead you can just edit any section of your profile in any order. Please complete your profile as much as possible and ALWAYS keep it updated. 

<br/><br/><span style="color:Red;font-weight:bold;">Never create a second Support Staff/Management profile</span> if you forget your login to your previous profile. Having more than one profile may result in missed communications regarding any 
employment positions and/or applications. 
<br/>
<br/>Sections with no information will display a red header. Those completed and/or with entries will display green.
<br/>
<br />

<div class="alert alert-info"  style="font-size:14px;">
	Resume and Cover Letter can now be uploaded using Edit/Upload button on Section 10 below
</div>
<br /><br />
<div class="panel-group" style="padding-top:10px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>1. PROFILE INFORMATION</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_1_ss.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
      			 	       
      			 	       <table class="table table-striped table-condensed" style="font-size:12px;">
							   
							    <tbody>
							    <tr>
							    <td class="tableTitle">NAME:</td>  
							    <td class="tableResult"><%=profile.getFirstname() + " " + ((profile.getMiddlename() != null)?profile.getMiddlename() + " ":"") + profile.getSurname() + 
							    (((profile.getMaidenname() != null)&&(profile.getMaidenname() != ""))?(" ("+ profile.getMaidenname()+")"):"")%></td>
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
					    				<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> 
					    				Your Profile has not been verified by HR. 
					    				There is nothing you need to do. 
					    				You can still apply for positions and edit your profile.</span>
					    			</c:otherwise>
					    		</c:choose>
					    		</td>
							  </tr>
							  -->
                <tr>
							    	<td class="tableTitle">ADDRESS:</td>
							    	<td class="tableResult">
							    		<%=profile.getAddress1() + " &middot; " +profile.getAddress2() + ", " + profile.getProvince() + " &middot; " + profile.getCountry() + " &middot; " + profile.getPostalcode()%>
							    </td>
								</tr>  
                                 
                                <tr>
							    <td class="tableTitle">TELEPHONE:</td>
							    <td class="tableResult"><%=profile.getHomephone()%></td>
								</tr>         
                                  
                                <%if(!StringUtils.isEmpty(profile.getWorkphone())){%>   
	                                <tr>
								    <td class="tableTitle">WORK PHONE:</td>
								    <td class="tableResult"><%=profile.getWorkphone()%></td>
									</tr>  
                                <%}%>
                                
                                <%if(!StringUtils.isEmpty(profile.getCellphone())){%>
	                                <tr>
								    <td class="tableTitle">CELL PHONE:</td>
								    <td class="tableResult"><%=profile.getCellphone()%></td>
									</tr> 
                                 <%}%>  
                                                                       
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
                                
                                
  
  							</tr>
  							</tbody>
  							</table>
  							</div>
  </div></div></div>                  



  <!-- 2. NLESD EXPERIENCE ------------------------------------------------------------------------------------>
   
   
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section2">   
	               	<div class="panel-heading"><b>2. NLESD EXPERIENCE</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_2_ss.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">  
   								<%if(esd_exp!=null){%>
                                    		<%if(esd_exp.getCurrentlyEmployed().equals("Y")){%>
                                    			<span style="color:Green;"><b>STATUS:</b> Currently Employed with the Newfoundland &amp; Labrador English School District.</span>
                                    			<br/><br/>                                    			
                                    			</table>
                                  				<hr>
                                				<div style="color:Green;font-size:14px;font-weight:bold;">Current Position(s):</div>				
												<br/>        						
         										<job:ApplicantCurrentPositionsSS showdelete="view"/>
         										  				
                                				<%}else{%>
                                    			<span style="color:Grey;">No Experience currently on file.</span>
                                    			<script>$("#section2").removeClass("panel-success").addClass("panel-danger");</script>
                                    			<%} %>
                                <%}else{%>
                                <span style="color:Grey;">No Newfoundland &amp; Labrador English School District Experience currently on file.</span>
                                <script>$("#section2").removeClass("panel-success").addClass("panel-danger");</script>
                               	<%} %>
                                <%if(empbean != null){%>
                               <table class="table table-striped table-condensed" style="font-size:11px;">
   						  		<tbody>	
                                <tr>
							    <td class="tableTitle">SDS Employee ID:</td>
							    <td class="tableResult">${SDSID}</td>
								</tr>
                                <tr>
								<c:choose>
							    	<c:when test="${SBEAN eq null}">
							    		<td class="tableTitleR">Service Time:</td>
							    		<td class="tableResultR">N/A</td>
							    	</c:when>
							    	<c:otherwise>
							    		<td class="tableTitleR">Service Time:</td>
							    		<td class="tableResultR">
						    				${SBEAN.viewAllSenioritySupport()}
						    			</td>
						    		</c:otherwise>
						    	</c:choose>
							    </tr>
							    
								
								 </tbody>
                                </table> 
                                <%}%>
   								
                               
  </div></div></div></div> 

<!-- 3. EMPLOYMENT  ------------------------------------------------------------------------------------>
   
   
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section3">   
	               	<div class="panel-heading"><b>3. EMPLOYMENT</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_3_ss.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">  
                    <job:ApplicantEmploymentSS showdelete="view"/>

  </div></div></div></div>                              
                                  
   
   
   




<!-- 5. DEGREES/DIPLOMAS/CERTIFICATES ------------------------------------------------------------------------------------------------->

                                
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section5">   
	               	<div class="panel-heading"><b>4. EDUCATION/DEGREES/DIPLOMAS/CERTIFICATES</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_5_ss.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">
					                       <% if(edu != null){%>   
                       <table class="table table-striped table-condensed" style="font-size:11px;">
                       <thead>
                       <tr>
                       
                       <th width="20%">SCHOOL NAME</th>
                       <th width="30%">ADDRESS</th>                                     
                        <th width="15%">HIGHEST GRADE</th>
                        <th width="35%">STATUS</th>
                       </tr>
                       </thead>
                       <tbody>	
                 			<tr>                 			                                 			
                 			<td><%=edu.getSchoolName() != null ? edu.getSchoolName() : "N/A"%></td>
                 			<td><%=edu.getSchoolCity() != null ? edu.getSchoolCity()+", " : "N/A, "%>                              			
                 			<%=edu.getSchoolProvince() != null ? edu.getSchoolProvince() + " &middot; ": ""%>
                 			<%=edu.getSchoolCountry() != null ? edu.getSchoolCountry() : ", N/A"%></td>
                 			<td><%=edu.getEducationLevel() != null ? edu.getEducationLevel() : "N/A"%></td>   
                 			<td>
                 			<%if (edu.getGraduatedText() != null) { %>
                 			<c:set var="graduatedQ" value="<%=edu.getGraduatedText()%>"/>          				
	          				<c:choose>
	          				<c:when test="${graduatedQ eq 'Yes' }">
	          					<span style="color:Green;">Successfully Graduated <%=edu.getYearGraduated()!=null? " in " +edu.getYearGraduated():"" %></span>
	          				</c:when>
	          				<c:when test="${graduatedQ eq 'No' }">
	          					<span style="color:Red;">Did Not Graduate</span>
	          				</c:when>
	          				<c:when test="${graduatedQ eq 'GED' }">
	          					<span style="color:Blue;">High School Equivalency (GED) <%=edu.getYearGraduated()!=null? " completed " +edu.getYearGraduated():"" %></span>
	          				</c:when>
	          				<c:otherwise>
	          					N/A
	          				</c:otherwise>
	          				</c:choose>
          				<%} else { %>
          					N/A
          				<%} %>
                 			
                 			</td>  
                 			</tr>
                 		</tbody>
          				</table>          				
          			
          				
          				
          				<%} else { %>
                                  		
                                  		<span style="color:Grey;">No Education information currently on file.</span>
                                		<script>$("#section4").removeClass("panel-success").addClass("panel-danger");</script>
                        <%} %>
                        <br />      
					<job:ApplicantEducationPostSS showdelete="view"/>
										
</div></div></div></div>
                                  	
                                  	
<!-- 6. OTHER INFORMATION ------------------------------------------------------------------------------------------------->

                                
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section6">   
	               	<div class="panel-heading"><b>5. OTHER INFORMATION</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_6_ss.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">      
					
						<% if((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())){
            				out.println(other_info.getOtherInformation().replaceAll(new String( new char[]{((char)10)} ), "<br>" ));
          				}else{
          				%>
            			<span style="color:Grey;">No other information currently on file.</span>
                        <script>$("#section6").removeClass("panel-success").addClass("panel-danger");</script>
          				<%} %>
								
</div></div></div></div>     


<!-- 7. REFERENCES ------------------------------------------------------------------------------------------------->

                                
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section7">   
	               	<div class="panel-heading"><b>6. REFERENCES</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_8_ss.jsp">EDIT/VIEW</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 

               						<% if((refs != null) && (refs.length > 0)){ %>
                                  
                                  <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="25%">NAME (TITLE)</th>
                                                                         
                                       <th width="30%">ADDRESS</th>
                                       <th width="15%">TELEPHONE</th>
                                       <th width="15%">EMAIL</th>
                                       <th width="20%">STATUS</th>                                       
                                      </tr>
                                      </thead>
                                      <tbody>
                                  		<%for(int i=0; i < refs.length; i ++) { %>                                 
                                  
                                      <tr>
                                      <td width="25%"><%=refs[i].getName()%> (<%=refs[i].getTitle()%>)</td>                                     
                                      <td width="30%"><%=refs[i].getAddress()%></td>
                                      <td width="15%"><%=refs[i].getTelephone()%></td>  
                                         <%out.println("<td width='20%'>" + ((refs[i].getApplicantRefRequestBean()!= null)?refs[i].getApplicantRefRequestBean().getEmailAddress():"N/A" ) +"</td>");%>	                                  
                                    
                                    <!-- Get current date and time to see if expired. -->
								<%
								//I hate Long variables
								Date date = new Date();		//Get today			
			int refResendTimeLeft=0;	
			int refDiff,refDiffResend,refDateRequested,refCurrentDate,refTimeLeft;; //declare ints 			
			int refExpiredTimeY = 8760; //# hours in a year exact. References expire after a year.
		
			
			if  (refs[i].getApplicantRefRequestBean()!=null && refs[i].getApplicantRefRequestBean().getDateStatus()!=null) {								
			 
				refDateRequested = (int) TimeUnit.MILLISECONDS.toHours(refs[i].getApplicantRefRequestBean().getDateStatus().getTime());
			 	refCurrentDate = (int) TimeUnit.MILLISECONDS.toHours(date.getTime());			 
			 	refDiff = (refCurrentDate - refDateRequested)-12;	//do the math hours
			 	
			} else {
				refDateRequested = 0; //if null 0 all variables
			 	refCurrentDate = 0;									
			 	refDiff = 0;				 
			}									
								
			
			if(refDiff >refExpiredTimeY) {  %>
													<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> EXPIRED</td>								
								<%	} else { 									
										if (refs[i].getApplicantRefRequestBean() == null) {	%> 
													<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>
								<%	} else {
									 	if (refs[i].getApplicantRefRequestBean().getRequestStatus() == null) {%>
													<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>
								 <%} else {									 
									 	if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST SENT")) { %>
													<td width='10%' class='info' style='text-align:center;'><i class='far fa-clock'></i> SENT/PENDING</td>
								<%	} else if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")) {%>
													<td width='10%' class='success' style='text-align:center;'><i class='fas fa-check'></i> COMPLETED</td>
								<%   } else if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST DECLINED")) { %>
													<td width='10%' class=danger' style='text-align:center;'><i class='fas fa-times'></i> DECLINED</td>
								 <%	} else { %> 			
								 					<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>> 
								 <% } %> 
								 <% } %> 
								 <% }} %>
								                                  
                                      </tr>
                                  	<% } %>
                                      </tbody>
                                      </table>
                                   <% } else {%>                                  
                                       <span style="color:Grey;">No References currently on file.</span>
                                       <script>$("#section8").removeClass("panel-success").addClass("panel-danger");</script>
                                    <% } %>
               
  </div></div></div></div>                              
                                
<!-- 8. DOCUMENTS ------------------------------------------------------------------------------------------------->

                                
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section8">   
	               	<div class="panel-heading"><b>7. DOCUMENTS</b> (Upload documents here. They will display in proper sections once uploaded). <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_9_ss.jsp">EDIT/UPLOAD</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">                                
  								<% if((docs != null) && (docs.size() > 0)) {
	                                  	int i=0; %>
	                            <table class="table table-striped table-condensed" style="font-size:12px;">
	                                <thead>
	                                <tr style="font-weight:bold;">
		                                <th width="40%">TYPE</th>
		                                <th width="40%">UPLOAD DATE</th>
		                                <th width="20%">OPTIONS</th>	                                
	                                </tr>
	                                </thead>
                                <tbody>
                                <c:set var="emailCheck" value="<%=profile.getEmail()%>" />
                                <% for(ApplicantDocumentBean doc : docs) { 
                                	//if(!doc.getTypeSS().equals(DocumentTypeSS.LETTER) || !doc.getTypeSS().equals(DocumentTypeSS.COVID19_VAX)){ 
                                		if(doc.getTypeSS() == DocumentTypeSS.COVER_LETTER || doc.getTypeSS() == DocumentTypeSS.RESUME ){
                                		continue;
                                	}
                                			
                                	%>
	                             	<tr>		                            
		                           		<td><%=doc.getTypeSS().getDescription() %>	                          
		                         	<!-- if user has nlesd email, check if code of conduct, and if so, hide warning. If user has NON nlesd email, hide it anyways.-->
								<c:choose>
								<c:when test="${fn:endsWith(emailCheck,'@nlesd.ca')}">
		                          
		                        <%
								//Has COE Training uploaded
								if(doc.getTypeSS().equals(DocumentTypeSS.CODE_OF_ETHICS_CONDUCT)) {%>
								
								<script>
								$("#COENotice").css("display","none");
								</script>
								
								<% } %>
								</c:when>
								<c:otherwise>
								<script>
								$("#COENotice").css("display","none");
								</script>
								
								</c:otherwise>
								</c:choose>
								
								                  		
		                           		
		                           		
		                           		</td>
		                            	<td><%=sdf_long.format(doc.getCreatedDate()) %></td>
		                             	<td><a class='viewdoc btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                             	</tr>
	                             <%//} %>
	                              <%  } %>
	                              </tbody>
	                              </table>
	                              <% } else { %>
	                              	<span style="color:Grey;">No Documentation currently on file.</span>
	                        		<script>$("#section8").removeClass("panel-success").addClass("panel-danger");</script>	
	                              <%} %>
                                
 </div></div></div></div>
  <!--10.  COVID19 ----------------------------------------------------------------------------------------->               
                
                <!-- HIDDEN NOW. Keep in case we need it again -->
  <div class="panel-group" style="padding-top:5px;display:none;">                               
	               	<div class="panel panel-info" id="section10b">   
	               	<div class="panel-heading"><b>COVID-19 Proof of Vaccination</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_9_ss.jsp">EDIT/UPLOAD</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
 										<% if((docs != null) && (docs.size() > 0)) {
	                                  	int i=0; %>	                                  	
	                                   <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="25%">TITLE</th>
                                       <th width="30%">UPLOAD DATE</th>
                                       <th width="35%">STATUS</th>
                                       <th width="10%">OPTIONS</th>                                    
                                      </tr>
                                      </thead>
                                      <tbody>
	                                   <% for(ApplicantDocumentBean doc : docs){ 
	                                   		if(doc.getTypeSS().equals(DocumentTypeSS.COVID19_VAX)){ %>
	                                   		<tr>
	                                      		<td width="25%"><%=doc.getTypeSS().toString()%></td>
	                                      		<td width="30%"><%=sdf_long.format(doc.getCreatedDate())%></td>
	                                      			<td width="35%">
	                                      			<%if(doc.getClBean() != null){ %>
	                                      				<%if(doc.getClBean().getDateVerified() != null) {%>
	                                      					<%if((doc.getClBean().isExcemptionDoc())){ %>
	                                      						<span style="color:Green;"><i class="fas fa-check"></i> Exemption verified on <%=doc.getClBean().getDateVerifiedFormatted() %></span>	
	                                      					<%}else{ %>
	                                      						<span style="color:Green;"><i class="fas fa-check"></i> Verified on <%=doc.getClBean().getDateVerifiedFormatted() %></span>	
	                                      					<%} %>
	                                      					                                      				
	                                      				<%}else if(doc.getClBean().getRejectedDate() != null){ %>
	                                      					<span style="color:Red;"><i class="fas fa-ban"></i> Rejected on <%=doc.getClBean().getRejectedDateFormatted() %><br/>
	                                      					 Notes: <%=doc.getClBean().getRejectedNotes() %> 
	                                      					</span>
	                                      				<%} %>	
	                                      			<%} %>
	                                      			</td>
	                                      		<%if(doc.getClBean() != null){ %>
	                                      			<%if(!(doc.getClBean().isExcemptionDoc())){ %>
	                                      			<td  width="10%"><a class='btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                                      			<%}else{ %>
	                                      			<td  width="10%"></td>
	                                      			<%} %>
	                                      		<%}else{ %>
	                                      		
	                                      		
	                                      		<%} %>                                      
	                                      	</tr>
	                                      <% } %>
	                                    <%} %>  
	                                      </tbody>
	                                      </table>	                                      
	                                      <% } else {%>                                  
	                                       <span style="color:Grey;">No Vaccination(s) currently on file.</span>
	                                       <script>$("#section10b").removeClass("panel-success").addClass("panel-danger");</script>
	                                    <% } %>
                              
</div></div></div></div>


   <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section11">   
	               	<div class="panel-heading"><b>8. CRIMINAL OFFENCE DECLARATIONS</b><span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_10_CODF.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">
									<%if((cods != null) && (cods.size() > 0))  {
	                                  int i=0; %>	                                  	
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
	                                      <td><a class='btn btn-xs btn-info' href='viewCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>'>VIEW</a></td>
	                                     </tr>
	                                      <%} %>  
	                                   	</tbody>
	                                   	</table>	                                      
	                                      <% } else {%>                                  
	                                       <span style="color:Grey;">No Documents currently on file.</span>
	                                       <script>$("#section10").removeClass("panel-success").addClass("panel-danger");</script>
	                                    <% } %>
  </div></div></div></div>                             
         
    <!--10.  Letters ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section10b">   
	               	<div class="panel-heading"><b>9. DISTRICT LETTERS</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
 										<% if((docs != null) && (docs.size() > 0)) {
	                                  	int i=0; %>
	                                   <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="25%">TITLE</th>
                                       <th width="30%">UPLOAD DATE</th>
                                       <th width="10%">OPTIONS</th>                                    
                                      </tr>
                                      </thead>
                                      <tbody>
	                                   <% for(ApplicantDocumentBean doc : docs){ 
	                                   		if(doc.getTypeSS().equals(DocumentTypeSS.LETTER)){ %>
	                                   		<tr>
	                                      		<td><%=doc.getDescription()%></td>
	                                      		<td><%=sdf_long.format(doc.getCreatedDate())%></td>
	                                      			<td><a class='btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                                      				                                      
	                                      	</tr>
	                                      <% } %>
	                                    <%} %>  
	                                      </tbody>
	                                      </table>	                                      
	                                      <% } else {%>                                  
	                                       <span style="color:Grey;">No Documents currently on file.</span>
	                                       <script>$("#section10b").removeClass("panel-success").addClass("panel-danger");</script>
	                                    <% } %>
                              
</div></div></div></div> 
  
    <!--10.  resume/coverletter ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section10b">   
	               	<div class="panel-heading"><b>10. RESUME/COVER LETTER</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_9_ss.jsp">EDIT/UPLOAD</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
 										<% if((docs != null) && (docs.size() > 0)) {
	                                  	int i=0; %>
	                                   <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="25%">TITLE</th>
                                       <th width="30%">UPLOAD DATE</th>
                                       <th width="10%">OPTIONS</th>                                    
                                      </tr>
                                      </thead>
                                      <tbody>
	                                   <% for(ApplicantDocumentBean doc : docs){ 
	                                   		if(doc.getTypeSS().equals(DocumentTypeSS.COVER_LETTER) || doc.getTypeSS().equals(DocumentTypeSS.RESUME)){ %>
	                                   		<tr>
	                                      		<td><%=doc.getTypeSS().getDescription()%></td>
	                                      		<td><%=sdf_long.format(doc.getCreatedDate())%></td>
	                                      			<td><a class='btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                                      				                                      
	                                      	</tr>
	                                      <% } %>
	                                    <%} %>  
	                                      </tbody>
	                                      </table>	                                      
	                                      <% } else {%>                                  
	                                       <span style="color:Grey;">No Resume/Cover Letter currently on file.</span>
	                                       <script>$("#section10b").removeClass("panel-success").addClass("panel-danger");</script>
	                                    <% } %>
                              
</div></div></div></div> 
         
         <div align="center" class="no-print" style="padding-top:3px;padding-bottom:10px;"><a href="/employment/index.jsp?finished=true" class="btn btn-sm btn-danger">Back to Employment</a></div>
                                
</body>
</html>