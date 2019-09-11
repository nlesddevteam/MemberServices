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
	if(profile != null){
	   	 esd_exp = ApplicantNLESDExperienceSSManager.getApplicantNLESDExperienceSSBeanBySin(profile.getSIN());
	   	 edu = ApplicantEducationSecSSManager.getApplicantEducationSecSSBeanBySin(profile.getSIN());
	   	other_info = ApplicantEducationOtherSSManager.getApplicantEducationOtherSSBean(profile.getSIN());
	   	regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(profile).values().toArray(new RegionBean[0]);
	   	//hmap = ApplicantRegionalJobPoolSSManager.getApplicantRegionalJobPoolPreferencesMap(profile.getSIN());
	   	refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
	   	docs = ApplicantDocumentManager.getApplicantDocumentBean(profile);
	   	hmapcur = ApplicantCurrentPositionManager.getApplicantCurrentPositionBeanMap(profile.getSIN());
	}
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
Your current Support Staff/Management profile information can be found below. If any changes are required, please select the proper menu item above and/or edit link found in each section below. 
There are no registration steps, and instead you can just edit any section of your profile in any order. Please complete your profile as much as possible and ALWAYS keep it updated. 
<b>Never create a second profile</b> if you forget your login to your previous profile. Having more than one profile may result in missed communications regarding any 
employment positions and/or applications. Sections with no information will display a red header. Those completed and/or with entries will display green.
<div class="panel-group" style="padding-top:10px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>1. PROFILE INFORMATION</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_1_ss.jsp">EDIT</a></span></div>
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
							    <td class="tableResult"><%=empbean.getEmpId() %></td>
								</tr>
                                <tr>
							    <td class="tableTitle">Years of Service:</td>
							    <td class="tableResult"><fmt:formatNumber pattern='0.00' value='<%= empbean.getSeniority().getSeniorityTotal() %>'/></td>
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
                       <th width="25%">HIGHEST GRADE LEVEL</th>
                       <th width="25%">SCHOOL NAME</th>
                       <th width="25%">TOWN/CITY</th>
                       <th width="25%">PROVINCE/STATE</th>
                       </tr>
                       </thead>
                       <tbody>	
                 			<tr>
                 			<td><%=edu.getEducationLevel() != null ? edu.getEducationLevel() : "N/A"%></td>                                    			
                 			<td><%=edu.getSchoolName() != null ? edu.getSchoolName() : "N/A"%></td>
                 			<td><%=edu.getSchoolCity() != null ? edu.getSchoolCity() : "N/A"%></td>                                    			
                 			<td><%=edu.getSchoolProvince() != null ? edu.getSchoolProvince() : "N/A"%></td>
                 			</tr>
                 		</tbody>
          				</table>
          				
          				<%if (edu.getGraduatedText() != null) { %>
	          				<c:set var="graduatedQ" value="<%=edu.getGraduatedText()%>"/>          				
	          				<c:choose>
	          				<c:when test="${graduatedQ eq 'Yes' }">
	          					<div class="alert alert-success">Successfully Graduated from <%=edu.getSchoolName() != null ? edu.getSchoolName() : "N/A"%>.</div>
	          				</c:when>
	          				<c:when test="${graduatedQ eq 'No' }">
	          					<div class="alert alert-danger">Did not graduate from <%=edu.getSchoolName() != null ? edu.getSchoolName() : "N/A"%>.</div>
	          				</c:when>
	          				<c:when test="${graduatedQ eq 'GED' }">
	          					<div class="alert alert-warning">Applicant has completed a High School Equivalency (GED) test.</div>
	          				</c:when>
	          				<c:otherwise>
	          					<div class="alert alert-info">No graduation information currently on file.</div>
	          				</c:otherwise>
	          				</c:choose>
          				<%} else { %>
          					<div class="alert alert-info">No graduation information currently on file.</div>
          				<%} %>
          				
          				
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
	               	<div class="panel-heading"><b>6. REFERENCES</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_8_ss.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 

               						<% if((refs != null) && (refs.length > 0)){ %>
                                  
                                  <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="20%">NAME</th>
                                       <th width="25%">TITLE</th>                                       
                                       <th width="25%">PRESENT ADDRESS</th>
                                       <th width="15%">TELEPHONE</th>
                                       <th width="20%">STATUS</th>                                       
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
	               	<div class="panel-heading"><b>7. DOCUMENTS</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_9_ss.jsp">EDIT</a></span></div>
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
                                <% for(ApplicantDocumentBean doc : docs) { %>
	                             <tr>
		                             <td><%=sdf_long.format(doc.getCreatedDate()) %></td>
		                             <td><%=doc.getTypeSS().getDescription() %></td> 
		                             <td><a class='viewdoc btn btn-xs btn-primary' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                             </tr>
	                              <%  } %>
	                              </tbody>
	                              </table>
	                              <% } else { %>
	                              	<span style="color:Grey;">No Documentation currently on file.</span>
	                        		<script>$("#section8").removeClass("panel-success").addClass("panel-danger");</script>	
	                              <%} %>
                                
 </div></div></div></div>                             
         
         
         <div align="center" class="no-print" style="padding-top:3px;padding-bottom:10px;"><a href="/employment/index.jsp?finished=true" class="btn btn-sm btn-danger">Back to Employment</a></div>
                                
</body>
</html>