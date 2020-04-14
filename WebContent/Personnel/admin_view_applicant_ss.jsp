<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.awsd.school.*,
                  com.awsd.school.bean.*,
                  com.awsd.mail.bean.AlertBean,
                  com.esdnl.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.awsd.security.crypto.*,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.database.sds.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<%
    ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("APPLICANT");
	SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
    ApplicantNLESDExperienceSSBean esd_exp = ApplicantNLESDExperienceSSManager.getApplicantNLESDExperienceSSBeanBySin(profile.getSIN());
    ApplicantEducationSecSSBean edu = ApplicantEducationSecSSManager.getApplicantEducationSecSSBeanBySin(profile.getSIN());
    ApplicantEducationOtherSSBean other_info = ApplicantEducationOtherSSManager.getApplicantEducationOtherSSBean(profile.getSIN());
    RegionBean[] regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(profile).values().toArray(new RegionBean[0]);
    //HashMap<Integer, ApplicantRegionalJobPoolSSBean> hmap = ApplicantRegionalJobPoolSSManager.getApplicantRegionalJobPoolPreferencesMap(profile.getSIN());
    ApplicantSupervisorBean[] refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
    NLESDReferenceListBean[] chks = NLESDReferenceListManager.getReferenceBeansByApplicant(profile.getSIN());
    boolean validReference = true;
    if(chks.length == 0 || refs == null){
    	validReference = false;
    }
    Collection<ApplicantDocumentBean> docs = ApplicantDocumentManager.getApplicantDocumentBean(profile);
    User usr = (User)session.getAttribute("usr");
    if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW-PWD"))
      session.setAttribute("APPLICANT", profile); 
     
    if(!usr.checkPermission("PERSONNEL-ADMIN-VIEW") && (session.getAttribute("SUBLIST") == null) && (session.getAttribute("JOB") == null)) {
    	new AlertBean(new com.awsd.security.SecurityException("Applicant Profile Illegal Access Attempted By " + usr.getPersonnel().getFullNameReverse()));
    	
    	throw new com.awsd.security.SecurityException("Illegal Access Attempted By " + usr.getPersonnel().getFullNameReverse());
    }
    
    EmployeeBean empbean = null;
    if(!StringUtils.isEmpty(profile.getSIN2())){
    	empbean = EmployeeManager.getEmployeeBeanBySIN(profile.getSIN2Unformatted());
    }
    
    Calendar cal = Calendar.getInstance();
    cal.clear(Calendar.HOUR);
    cal.clear(Calendar.MINUTE);
    cal.clear(Calendar.SECOND);
    cal.clear(Calendar.MILLISECOND);
    cal.add(Calendar.MONTH, -6);
    
    Date six_months = cal.getTime();
    Collection<ApplicantCriminalOffenceDeclarationBean> cods = ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans(profile);
    
    
%>

<!-- Clean up the code and use jstl vars. this should be moved after. -->
<c:set var="nameDisplay" value="<%=profile.getSurname() + \", \" + profile.getFirstname()%>"/>
<c:set var="fullName" value="<%=profile.getFirstname() + \" \" + ((profile.getMiddlename() != null)?profile.getMiddlename() + \" \":\"\") + profile.getSurname() + ((profile.getMaidenname()!= null)?\" (\"+profile.getMaidenname()+\")\":\"\")%>"/>
<c:set var="UUID" value="<%=profile.getSIN()%>"/>
<c:set var="primaryAddress" value="<%=profile.getAddress1()!=null?profile.getAddress1() : \"N/A\" %>"/>
<c:set var="secondaryAddress" value="<%=profile.getAddress2()!=null?\", \"+ profile.getAddress2() : \"\" %>"/>
<c:set var="prov" value="<%=profile.getProvince()%>"/>
<c:set var="country" value="<%=profile.getCountry()%>"/>
<c:set var="postalCode" value="<%=profile.getPostalcode()%>"/>
<c:set var="homePhone" value="<%=profile.getHomephone()!=null?profile.getHomephone() : \"N/A\" %>"/>
<c:set var="cellPhone" value="<%=profile.getCellphone()!=null?profile.getCellphone() : \"N/A\" %>"/>
<c:set var="workPhone" value="<%=profile.getWorkphone()!=null?profile.getWorkphone() : \"N/A\" %>"/>
<c:set var="emailAddress" value="<%=profile.getEmail()%>"/>
<c:set var="password" value="<%=profile.getPassword()%>"/>
<c:set var="userID" value="<%=profile.getUID()%>"/>
<c:set var="SDSID" value="<%=empbean != null?empbean.getEmpId():\"N/A\" %>"/>
<c:set var="seniorityTotal" value="<%=empbean != null && empbean.getSeniority() != null ? empbean.getSeniority().getSeniorityTotal() :\"\"  %>"/> 

<html>
<head>
<title>MyHRP Applicant Profiling System</title>

<script>
$("#loadingSpinner").css("display","none");
</script>

 
<script type="text/javascript">


	var reload_applicant = false;
	
  $('document').ready(function(){
	  $("#jobsapp").dataTable(
		{
			"order": [[ 0, "desc" ]],
			"lengthMenu": [[25, 50, 100, 200, -1], [25, 50, 100, 200, "All"]]
		}	  
	  );
		$('#btn_delete_applicant').click(function(){
  		if(confirm('Are you sure you want to delete this applicant profile?'))
  			return true;
  		else
  			return false;
  	});
  	
  	$('.delete-cod').click(function(){
  		if(confirm('Are you sure you want to delete this Criminal Offence Declaration?')){
  			$(this).css({'color': "blue"}).html('DELETING...');
  			return true;
  		}
  		else
  			return false;
  	});
  	
  	$('.delete-doc').click(function(){
  		if(confirm('Are you sure you want to delete this Document?')){
  			$(this).css({'color': "blue"}).html('DELETING...');
  			return true;
  		}
  		else
  			return false;
  	});
  	


  	$('#add_applicant_dialog').dialog({
			autoOpen: false,
			bgiframe: true,
			width:500,
			height: 400,
			modal: true,
			hide: 'explode',
			buttons: 
			{'close': 
				function(){
					$('#add_applicant_dialog').dialog('close');
					if(reload_applicant)
						document.location.reload();
				}
			}
		});

  	$('#btn_show_add_applicant_dialog').click(function(){
			$("#add_applicant_dialog").dialog('open');
			return false;
		});

  	$('#btn_add_applicant').hover(
			function() { $(this).addClass('ui-state-hover'); }, 
			function() { $(this).removeClass('ui-state-hover'); }
		);

  	$('#btn_add_applicant').click(function(){

			$.post("addSublistApplicant.html", 
					{ 
						sin: '<%=profile.getUID()%>', 
						list_id: $('#sublist_id').val(),
						ajax: true 
					}, 
					function(data){
						parseAddApplicantResponse(data);
					}, 
					"xml"
			);
			
		});
  	
	$('#butVerify').click(function(){
  		var url="applicantVerification.html";
  		$('#frmverify').attr('action',url);
		$('#frmverify').submit();
			
	});  	
  	
  });

  	

  function parseAddApplicantResponse(data){
		var xmlDoc=data.documentElement;
		var msg = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
		
		$('#response_msg > td').html(msg);
		$('#response_msg').show();

		reload_applicant = true;
	}
</script>
<style>
.tableTitleL {font-weight:bold;width:20%;}
.tableResultL {font-weight:normal;width:30%;}
.tableTitle {font-weight:bold;width:40%;}
.tableResult {font-weight:normal;width:60%;}
.tableTitleR {font-weight:bold;width:20%;}
.tableResultR {font-weight:normal;width:30%;}
input {    
    border:1px solid silver;
}

</style>
</head>
<body>


<!-- 1. DEMOGRAPHICS --------------------------------------------------------------->  

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>DEMOGRAPHICS</b></div>
      			 	<div class="panel-body">       			 	
      			 				<span style="font-size:20px;padding-top:10px;color:#007d01;font-weight:bold;">${nameDisplay}</span><br/>
      			 				<input type="hidden" id="hidshowsl" value="<%=session.getAttribute("sfilterparams") == null ? 'Y':'N'%>">
      			 				<input type="hidden" id="id" value="<%=profile.getSIN() %>">
      			 	 			<c:if test="${APPLICANT.modifiedDate ne null}">
                       				<span style="color:Silver;text-align:right;">Last Modified: <fmt:formatDate pattern='MMMM dd, yyyy' value='${APPLICANT.modifiedDate}'/></span>
                     			</c:if>
      			 			<br/>                  			    			 			
      			 			
      			 		<div class="table-responsive">       			 	       
      			 	       <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr>
							    <td class="tableTitleL">Verification Status:</td>
							    <td colspan=3>							    
							    <c:choose>
					    			<c:when test="${ APPLICANT.profileVerified }">					    				
					    					<c:if test="${APPLICANT.verificationBean ne null}">
					    					<span style="color:Green;"><span class="glyphicon glyphicon-ok"></span> Profile verified by ${APPLICANT.verificationBean.verifiedByName} on ${APPLICANT.verificationBean.getDateVerifiedFormatted()}</span>
					    					</c:if>
									</c:when>
					    			<c:otherwise>
					    				<esd:SecurityAccessRequired roles="ADMINISTRATOR,MANAGER OF HR,MANAGER OF HR - PERSONNEL,SEO - PERSONNEL,SENIOR EDUCATION OFFICIER">
					    				<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> This Profile has not been verified.</span><button type="button" class="btn btn-success btn-xs" id="butVerify" style="margin-left:10px;"><span class="glyphicon glyphicon-thumbs-up"></span> Verify</button>
					    				</esd:SecurityAccessRequired> 
					    			</c:otherwise>
					    		</c:choose>
					    		</td>
							    </tr>							    
							    <tr>
							    <td class="tableTitleL">NAME:</td>
							    <td class="tableResultL">${fullName}</td>
							    <%if(usr.checkRole("ADMINISTRATOR")){%>
	                            <td class="tableTitleR">UUID:</td>
							    <td class="tableResultR">${UUID}</td>
							     <%} else {%>
							    <td colspan=2></td>
							    <%}%>
							    							    
							    </tr>
							     <tr>
							    <td class="tableTitleL">ADDRESS:</td>
							    <td colspan=3>${primaryAddress}${secondaryAddress}, ${prov }, ${country } &nbsp; ${postalCode }</td>
							    </tr>
							    <tr>
							    <td class="tableTitleL">TEL #:</td>
							    <td class="tableResultL">${homePhone}</td>
							    <td class="tableTitleR">CELL #:</td>
							    <td class="tableResultR">${cellPhone}</td>
							    
							    </tr>
							    <tr>
							    <td class="tableTitleL">WORK #:</td>
							    <td class="tableResultL">${workPhone}</td>
							    <td class="tableTitleR">EMAIL:</td>
							    <!-- Check to see if email is old esdnl, wsdnl, ncsd, or lsb. -->
	                                      <c:set var="emailCheck" value ="${emailAddress}"/>
		                                      <c:choose>
			                                      <c:when test="${fn:endsWith(emailCheck,'@esdnl.ca') or fn:endsWith(emailCheck,'@wnlsd.ca') or fn:endsWith(emailCheck,'@lsb.ca') or fn:endsWith(emailCheck,'@ncsd.ca')}">
			                                       <td class="tableResultR" title="Email needs updating. Invalid address." style="background-color:#fde8ec;"><span>${emailAddress} (Need update)</span></td>   
			                                      </c:when>
		                                      <c:otherwise>
		                                      		 <td class="tableResultR"><a href="mailto:${emailAddress}">${emailAddress}</a></td>   
		                                      </c:otherwise>
	                                      </c:choose>							    
							    
							    
							   
							    </tr>
							   
						<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-PWD"> 
								    <tr class="no-print">
								    <td class="tableTitleL">Password:</td>
								    <td class="tableResultL">${password}</td>
								    <td class="tableTitleR"><a href="#" class="btn btn-info btn-xs" onclick="onSendApplicantLoginInfoEmail(<%=profile.getUID()%>);"><span class="glyphicon glyphicon-envelope"></span> Email Credentials</a></td>
								    <td class="tableResultR"><a href="/employment/index.jsp?uid=<%=PasswordEncryption.encrypt(profile.getSIN())%>" class="btn btn-warning btn-xs" target="_blank"><span class="glyphicon glyphicon-user"></span> Sign In</a></td>
								    </tr>
						</esd:SecurityAccessRequired>
							    
							    <tr>
							    	<td class="tableTitleL">SDS Employee ID:</td>
								    <td class="tableResultL">${SDSID}</td>
							    	<td class="tableTitleR">Years of Service:</td>
							    	<td class="tableResultR">
							    	<c:choose>
							    	<c:when test="${seniorityTotal ne ''}">${seniorityTotal} yrs</c:when>
							    	<c:otherwise>N/A</c:otherwise>
							    	</c:choose>
							    	</td>
							    </tr>
							    </tbody>
							    </table>
      			 	       
						</div>
	                 </div> 
	              </div>
  </div>


<!-- 2. NLESD EXPERIENCE --------------------------------------------------------------->    
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section2">   
	               	<div class="panel-heading"><b>NLESD Experience</b></div>
      			 	<div class="panel-body"> 	
					
					<div class="table-responsive"> 
					<%if(esd_exp != null){%>
      			 	      <table class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;margin-top:10px;">
      			 	       <tbody>
      			 	        
      			 	       <tr>
      			 	      		<td class="tableTitle" colspan=2>Currently Employed with NLESD?</td>
								<td class="tableResult" colspan=2><%=esd_exp.getCurrentlyEmployed().equals("Y") ? "Yes" : "No"%></td>						    
      			 	       </tr>
      			 	       <tr>
      			 	      		<td colspan=4><b>Current NLESD Position(s):</b><br/>
      			 	      		<job:ApplicantCurrentPositionsSS showdelete="view"/>
      			 	      		</td>
														    
      			 	       </tr>
      			 	      
      			 	       </tbody>
      			 	       
      			 	       </table>
      			 	        <%}else{%>
      			 	         	<span style="color:Grey;">No experience currently on file.</span>
                    			<script>$("#section2").removeClass("panel-success").addClass("panel-danger");</script>	      			 	        
	                      	<%}%>
      			 	      
					</div>		    
					</div>
					</div>
</div>



<!-- 3. EMPLOYMENT HISTORY --------------------------------------------------------------->    
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section3">   
	               	<div class="panel-heading"><b>Employment History</b></div>
      			 	<div class="panel-body"> 	
					
					<div class="table-responsive">                                	                              
	                <job:ApplicantEmploymentSS showdelete="view"/>            
	                              
	                              
					</div>		    
					</div>
					</div>
</div>	                              
	                              
	                              
<!-- 4. SCHOOL EDUCATION --------------------------------------------------------------->    
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section4">   
	               	<div class="panel-heading"><b>School Education</b></div>
      			 	<div class="panel-body"> 	
					
					<div class="table-responsive">  
					    <%if(edu != null){%>                           	                              
	                    <table class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;margin-top:10px;">
      			 	       <tbody>
      			 	        
      			 	       <tr>
      			 	      		<td class="tableTitle" colspan=2>Highest Level of School Education (Grade/Level):</td>
								<td class="tableResult" colspan=2><%=edu.getEducationLevel() != null ? edu.getEducationLevel() : "N/A"%></td>						    
      			 	       </tr>    
	                       <tr>
      			 	      		<td class="tableTitleL">School Name:</td>
								<td class="tableResultL"><%=edu.getSchoolName() != null ? edu.getSchoolName() : "N/A"%></td>
							    <td class="tableTitleR">Address:</td>
							    <td class="tableResultR"><%=edu.getSchoolCity() != null ? edu.getSchoolCity() : "N/A"%>, <%=edu.getSchoolProvince() != null ? edu.getSchoolProvince() : "N/A"%></td>
      			 	        </tr>  
      			 	        <tr>
      			 	      		<td class="tableTitle" colspan=2>Successfully Graduated?</td>
								<td class="tableResult" colspan=2> <%=edu.getGraduatedText() != null ? edu.getGraduatedText() : "N/A"%></td>						    
      			 	       </tr>     
	                       </tbody>
	                       </table> 
	                        <%}else{%>
	                        <span style="color:Grey;">No education currently on file.</span>
                    		<script>$("#section4").removeClass("panel-success").addClass("panel-danger");</script>	                        
	                        <%}%>
	                              
					</div>		    
					</div>
					</div>
</div>	                                      
                           
<!-- 5. DEGREES/DIPLOMAS/CERTIFICATES --------------------------------------------------------------->    

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section5">   
	               	<div class="panel-heading"><b>DEGREES/DIPLOMAS/CERTIFICATES</b></div>
      			 	<div class="panel-body">
					<div class="table-responsive">                           
	                <job:ApplicantEducationPostSS showdelete="view"/>
	 				</div>		    
					</div>
					</div>
</div>	  	                                                            
                               
<!-- 6. OTHER INFORMATION --------------------------------------------------------------->
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section6">   
	               	<div class="panel-heading"><b>Other Information</b></div>
      			 	<div class="panel-body"> 	
					<%if((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())) { %>                    
                    <span style="font-size:11px;"><%=other_info %></span>                    
                    <%} else { %>
                    <span style="color:Grey;">No Other Information currently on file.</span>
                    <script>$("#section6").removeClass("panel-success").addClass("panel-danger");</script>
                    <%}%>                    
					</div>
					</div>
</div>		                                		
		                                	

<!-- 7. REFERENCE CHECKS --------------------------------------------------------------->		
                                	
<%if((!usr.checkPermission("PERSONNEL-ADMIN-VIEW") && session.getAttribute("JOB") != null) || usr.checkPermission("PERSONNEL-ADMIN-VIEW")) {%>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section7">   
	               	<div class="panel-heading"><b>Reference Checks</b></div>
      			 	<div class="panel-body"> 	
					
					<div class="table-responsive"> 
      			 	       
      			 	       
							  <% if((chks != null) && (chks.length > 0)) { %>
                               <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>
									    
									      <tr style="border-top:1px solid black;">
									        <th width='15%'>REFERENCE DATE</th>
									        <th width='20%'>PROVIDED BY</th>	
									        <th width='50%'>POSITION</th>								       								        
									        <th class="no-print" width='15%'>OPTIONS</th>		
									      </tr>
									    </thead>
							    
							    <tbody>
                               
                               <% for(int i=0; i < chks.length; i ++)
                                  {
                               %>
							    <tr>
							    <td><%=chks[i].getProvidedDateFormatted() %></td>
							    <td><%=chks[i].getProvidedBy() %></td>							    
							    <td><%=chks[i].getProviderPosition() %></td>
							    <td class="no-print"><a class='btn btn-info btn-xs' href='<%=chks[i].getViewUrl()%>'>VIEW</a></td>
							    </tr>
							    <%}%>
							    </tbody>
							    </table>
							    <%} else { %>							   
                                    <span style="color:Grey;">No Reference Checks currently on file.</span>
                                    <script>$("#section7").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
							    
							    
						
					</div>
					</div>
					</div>
</div>
 <%}%>

 <!-- 8. REFERENCES --------------------------------------------------------------->
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section8">   
	               	<div class="panel-heading"><b>References</b></div>
      			 	<div class="panel-body"> 	
	
					<div class="table-responsive"> 
      			 	       
      			 	       
							  <% if((refs != null) && (refs.length > 0))
                              		{ %>
                              <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>
									    
									      <tr style="border-top:1px solid black;">
									        <th width='20%'>FULL NAME</th>
									        <th width='20%'>TITLE</th>	
									        <th width='45%'>PRESENT ADDRESS</th>								       								        
									        <th width='15%'>TELEPHONE</th>		
									      </tr>
									    </thead>
							    
							    <tbody>
                              
                              
                              <% for(int i=0; i < refs.length; i ++)
                                  	{
                               %>
							    <tr>
							    <td><%=refs[i].getName()%></td>
							    <td><%=refs[i].getTitle()%></td>							    
							    <td><%=refs[i].getAddress()%></td>
							    <td><%=refs[i].getTelephone()%></td>
							    </tr>
							    <%}%>
							    </tbody>
							    </table>
							    <%} else { %>							   
                                    <span style="color:Grey;">No References currently on file.</span>
                                    <script>$("#section8").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
					</div>
	
					
	
	
	
	
					</div>
					</div>
</div>                                 	
                                  	
<!-- 9. DOCUMENTATION --------------------------------------------------------------->
 <% if(usr.checkPermission("PERSONNEL-ADMIN-VIEW-DOCS")) { %>
	 
 
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section9">   
	               	<div class="panel-heading"><b>Documents</b></div>
      			 	<div class="panel-body"> 	
	
					<div class="table-responsive"> 
      			 	       
      			 	       
							  <% if((docs != null) && (docs.size() > 0))
                              { %>
                              
                              <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>
									    
									      <tr style="border-top:1px solid black;">
									      	<th width='50%'>TYPE</th>
									        <th width='20%'>UPLOADED</th>
									        <th width='15%'>STATUS</th>								       								        
									        <th class="no-print" width='15%'>OPTIONS</th>		
									      </tr>
									    </thead>
							    
							    <tbody>
                              
                              
                              <%
                                	int i=0;
                                  for(ApplicantDocumentBean doc : docs)
                                  {                                  
                                  	//only select roles get docs other then transcripts.
	                                    	if(doc.getApplicant().getProfileType().equals("T")){
	                                    		if(!doc.getType().equal(DocumentType.UNIVERSITY_TRANSSCRIPT) 
		                                    			&& !usr.checkPermission("PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL"))
		                                    		continue;
	                                    	}
                               %>
							    <tr>							   
							    <td>
							    <%if(doc.getApplicant().getProfileType().equals("T")){ %>
	                            <%=doc.getType().getDescription()%>
	                                     <% }else{%>
	                            <%=doc.getTypeSS().getDescription()%>
	                                    <% }%>
							    </td>	
							    <td><%=sdf_long.format(doc.getCreatedDate())%></td>						    
							    <td>
							    <% if(doc.getApplicant().getProfileType().equals("T")){  %>
							    <%=((doc.getType().equal(DocumentType.CODE_OF_CONDUCT) && doc.getCreatedDate().before(six_months))? "<span style='color:Red;'>** OUTDATED **</span>" : "<span style='color:Green;'>OK</span>")%>
							    <%}%>
							    </td>
							    <td class="no-print">
							    <a class='viewdoc btn btn-xs btn-info' href='viewApplicantDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a> &nbsp;
							    <a class='viewdoc delete-doc btn btn-xs btn-danger' href='deleteApplicantDocument.html?id=<%=doc.getDocumentId()%>'>DELETE</a>
							    </td>
							    </tr>
							    <%  }%>
							    </tbody>
							    </table>
							    <%}	else { %>							   
                                    <span style="color:Grey;">No Documentation currently on file.</span>
                                    <script>$("#section9").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
							    
							    
						
					</div>
					</div>
					</div>
</div>                                  	
                                  	
  <%} %>                            

<!-- CRIMINAL OFFENCE DECLARATIONS --------------------------------------------------------------->
<% if(usr.checkPermission("PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL")) { %>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section13">   
	               	<div class="panel-heading"><b>Criminal Offence Declarations</b></div>
      			 	<div class="panel-body"> 	
					
					<div class="table-responsive"> 
      			 	       
      			 	      
							  <% if((cods != null) && (cods.size() > 0))
                              { %>
                               
                                <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>
									      <tr style="border-top:1px solid black;">
									        <th width='25%'>DECLARATION DATE</th>
									        <th width='60%'>OFFENCES PRESENT</th>	
									        <th width='15%' class="no-print">OPTIONS</th>					       								        
									      </tr>
									    </thead>
							    
							    <tbody>
                               
                               <%
                               int i=0;
                                  for(ApplicantCriminalOffenceDeclarationBean cod : cods)
                                  {
                               %>
							    <tr>
							    <td><%=sdf_long.format(cod.getDeclarationDate())%></td>
							    <td><%=((cod.getOffences() != null)?cod.getOffences().size():0)%></td>							    
							    <td class="no-print">
							    <a class='viewdoc btn btn-xs btn-info' href='viewApplicantCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>' target='_blank'>VIEW</a>
							    <a class='viewdoc delete-cod btn btn-xs btn-danger' href='deleteApplicantCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>'>DELETE</a>
							    </td>
							    
							    </tr>
							    <%  }%>
							    </tbody>
							    </table>
							    
							    <%}  else { %>							   
                                    <span style="color:Grey;">None CODs currently on file.</span>
                                    <script>$("#section13").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
					</div>
	
	
					</div>
					</div>
</div>
 <% } %> 
                                   
<!-- 10. POSITIONS APPLIED FOR --------------------------------------------------------------->
	
<div class="panel-group no-print" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section10">   
	               	<div class="panel-heading"><b>Positions Applied</b></div>
      			 	<div class="panel-body"> 	
	                     
											<c:choose>
			                                  	<c:when test='${fn:length(jobs) gt 0}'>			                                  	
			                                  	<table class="table table-condensed table-striped" id="jobsapp" style="font-size:11px;">
				                                <thead>
			                                 	<tr>
			                                 		<th>COMPETITION NUMBER</th>
			                                 		<th>TITLE</th>
			                                 		<th>DATE APPLIED</th>
			                                 		<th>LOCATION</th>
			                                 		<th class="no-print">OPTIONS</th>
			                                 	</tr>
					                             </thead>
			                                 	<tbody>
			                                  	<c:forEach items='${jobs}' var='g'>
		                                  			<tr>
		                                  			<td>${g.compNum}</td>
		                                  			<td>${g.posTitle}</td>
		                                  			<td>${g.formattedAppliedDate}</td>
		                                  			<td>${g.schoolName}</td>
		                                  			<td class="no-print"><a class='btn btn-xs btn-info' href='view_job_post.jsp?comp_num=${g.compNum}'>View</a></td>
				                					</tr>
				                				</c:forEach>
				                				</tbody>
			                					</table>
				                				
		                                  		</c:when>
												<c:otherwise>
													<span style="color:Grey;">No Applications on file.</span>
													<script>$("#section10").removeClass("panel-success").addClass("panel-danger");</script>
												</c:otherwise>
											</c:choose>
											
					</div>
					</div>
</div>                                   
                                   

<!-- OPTIONS --------------------------------------------------------------->    
<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">                        
	
					<div align="center" class="no-print" style="padding-bottom:10px;">
                                    <a href='#' title='Print this page (pre-formatted)' class='btn btn-xs btn-info' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span> Print Profile</a>
                    <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE">
                                  	<a id='btn_delete_applicant' class="btn btn-xs btn-danger" href='deleteApplicant.html?uid=<%=profile.getUID()%>'><span class="glyphicon glyphicon-remove"></span> Delete Applicant</a>
                    </esd:SecurityAccessRequired>
                    			<%if(session.getAttribute("JOB") != null){
                                  		JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
                                  		InterviewGuideBean guide = null;
                                  		
                                  		if(job != null) {
                                  			guide = InterviewGuideManager.getInterviewGuideBean(job);
                                  		}
                                  %>
                                  <a href='admin_view_job_applicants.jsp' class='btn btn-xs btn-info'><span class="glyphicon glyphicon-search"></span> View Applicants</a>
                                    	<% if(!job.isShortlistComplete()) { %>
                                   			<%if(guide != null){ %>
                                   				<a href='shortListApplicant.html?sin=<%=profile.getSIN() %>'" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                   			<%}else{ %>
                                   					<a href='#' onclick="alert('Interview guide must be set for competition <%=job.getCompetitionNumber() %> before shortlist can be created.'); return false;" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                   			<%} %>
                                   			
                                    <%}}%>
                	</div>
</esd:SecurityAccessRequired>

<form id="frmverify">
   	<input id="appid" name ="appid" type="hidden" value="<%=profile.getSIN()%>">
</form>                      

</body>
</html>
