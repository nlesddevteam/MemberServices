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
	docs = ApplicantDocumentManager.getApplicantDocumentBean(profile);
	hmapcur = ApplicantCurrentPositionManager.getApplicantCurrentPositionBeanMap(profile.getSIN());
	EmployeeBean empbean = null;
	if (!StringUtils.isEmpty(profile.getSIN2())) {
		empbean = EmployeeManager.getEmployeeBeanBySIN(profile.getSIN2Unformatted());
	}
	Collection<ApplicantCriminalOffenceDeclarationBean> cods = ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans(profile);
	  
%>
<c:set var="SDSID" value="<%=empbean != null?empbean.getEmpId():\"N/A\" %>"/>
<c:set var="seniorityTotal" value="<%=empbean != null && empbean.getSeniority() != null ? empbean.getSeniority().getSeniorityTotal() :\"\"  %>"/>
<c:set var="SDSBEAN" value="<%=empbean == null ? null : empbean.getSenioritySupport() ==  null ? null: empbean.getSenioritySupport()%>"/> 
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

Your current <b>Support Staff/SA/Management profile</b> information can be found below. If any changes are required, please select the proper menu item above and/or edit link found in each section below. 
There are no registration steps, and instead you can just edit any section of your profile in any order. Please complete your profile as much as possible and ALWAYS keep it updated. 

<br/><br/><span style="color:Red;font-weight:bold;">Never create a second Support Staff/Management profile</span> if you forget your login to your previous profile. Having more than one profile may result in missed communications regarding any 
employment positions and/or applications. 

<br/><br/>Sections with no information will display a red header. Those completed and/or with entries will display green.
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
							    	<c:when test="${SDSBEAN eq null}">
							    		<td class="tableTitle">Years of Service:</td>
							    		<td class="tableResult">N/A</td>
							    	</c:when>
							    	<c:otherwise>
						    		<c:choose>
						    			<c:when test="${ SDSBEAN.unionCode eq '01' || SDSBEAN.unionCode eq '03' || SDSBEAN.unionCode eq '04' || SDSBEAN.unionCode eq '10' || SDSBEAN.unionCode eq '11' }">
						    				<td class="tableTitle">Seniority Date:</td>
						    				<c:choose>
					    						<c:when test="${SDSBEAN ne null}">
					    							<c:choose>
					    								<c:when test="${ SDSBEAN.seniorityDate1 ne null}">
					    									<td class="tableResult">${ SDSBEAN.seniorityDate1Formatted}</td>
					    								</c:when>
					    								<c:when test="${SDSBEAN.seniorityDate2 ne null}">
					    									<td class="tableResult">${ SDSBEAN.seniorityDate2Formatted}</td>
					    								</c:when>
					    								<c:otherwise>
					    									<td class="tableResult">N/A</td>
					    								</c:otherwise>
					    							</c:choose>
					    						</c:when>
						    					<c:otherwise>N/A </c:otherwise>
						    				</c:choose>
						    			</c:when>
						    			<c:when test="${ SDSBEAN.unionCode eq '02' || SDSBEAN.unionCode eq '05' || SDSBEAN.unionCode eq '06' || SDSBEAN.unionCode eq '07' || SDSBEAN.unionCode eq '08' || SDSBEAN.unionCode eq '09' }">
						    					<td class="tableTitle">Days Worked:</td>
						    					<c:choose>
						    						<c:when test="${seniorityTotal ne ''}"><td class="tableResult">${seniorityTotal}</td></c:when>
						    						<c:otherwise>N/A</c:otherwise>
						    					</c:choose>
						    			</c:when>
						    			<c:otherwise>
						    				<td class="tableTitle">Years of Service:</td>
						    				<td class="tableResult">N/A</td>
						    			</c:otherwise>
						    		</c:choose>
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
                                <% for(ApplicantDocumentBean doc : docs) { 
                                	//if(!doc.getTypeSS().equals(DocumentTypeSS.LETTER) || !doc.getTypeSS().equals(DocumentTypeSS.COVID19_VAX)){ %>
	                             	<tr>		                            
		                           		<td><%=doc.getTypeSS().getDescription() %></td>
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
                
                
  <div class="panel-group" style="padding-top:5px;">                               
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
                                       <th width="10%">OPTIONS</th>                                    
                                      </tr>
                                      </thead>
                                      <tbody>
	                                   <% for(ApplicantDocumentBean doc : docs){ 
	                                   		if(doc.getTypeSS().equals(DocumentTypeSS.COVID19_VAX)){ %>
	                                   		<tr>
	                                      		<td><%=doc.getTypeSS().toString()%></td>
	                                      		<td><%=sdf_long.format(doc.getCreatedDate())%></td>
	                                      			<td><a class='btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                                      				                                      
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
  
         
         <div align="center" class="no-print" style="padding-top:3px;padding-bottom:10px;"><a href="/employment/index.jsp?finished=true" class="btn btn-sm btn-danger">Back to Employment</a></div>
                                
</body>
</html>