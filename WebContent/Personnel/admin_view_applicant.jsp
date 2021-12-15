<%@ page language="java"
         import="java.util.*,
         					java.util.stream.*,
                  java.text.*,
                  java.util.concurrent.TimeUnit,
                  java.text.SimpleDateFormat, 
				  java.util.Date,  
                  com.awsd.security.*,
                  com.awsd.school.*,		           
		          com.awsd.school.dao.*,
                  com.awsd.school.bean.*,
                  com.nlesd.school.bean.*,
		          com.nlesd.school.service.*,  
                  com.awsd.mail.bean.AlertBean,
                  com.esdnl.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.awsd.security.crypto.*,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.database.sds.*" 
         isThreadSafe="false"%>


<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->		
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<%
	User usr = (User)session.getAttribute("usr");
	if(!usr.checkPermission("PERSONNEL-ADMIN-VIEW") && (session.getAttribute("SUBLIST") == null) && (session.getAttribute("JOB") == null)) {
		//new AlertBean(new com.awsd.security.SecurityException("Applicant Profile Illegal Access Attempted By " + usr.getPersonnel().getFullNameReverse()));
		
		throw new com.awsd.security.SecurityException("Illegal Access Attempted By " + usr.getPersonnel().getFullNameReverse());
	}

	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("APPLICANT");
  SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
  SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
  
  ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
  ApplicantEducationBean[] edu = ApplicantEducationManager.getApplicantEducationBeans(profile.getSIN());
  ApplicantEducationOtherBean edu_oth = ApplicantEducationOtherManager.getApplicantEducationOtherBean(profile.getSIN());
  boolean validEduOther = false;
  if((edu_oth != null) && (edu_oth.getFormatedTeachingCertificateIssuedDate() != null) && (edu_oth.getTeachingCertificateLevel() != null)){
		  validEduOther = true;
  }
  
  ApplicantNLESDPermanentExperienceBean[] per = ApplicantNLESDPermExpManager.getApplicantNLESDPermanentExperienceBeans(profile.getSIN());
  ApplicantEsdReplacementExperienceBean[] rpl = ApplicantEsdReplExpManager.getApplicantEsdReplacementExperienceBeans(profile.getSIN());
  ApplicantSubstituteTeachingExpBean[] sub = ApplicantSubExpManager.getApplicantSubstituteTeachingExpBeans(profile.getSIN());
  ApplicantExperienceOtherBean[] exp_other = ApplicantExpOtherManager.getApplicantExperienceOtherBeans(profile.getSIN());
  ApplicantOtherInformationBean other_info = ApplicantOtherInfoManager.getApplicantOtherInformationBean(profile.getSIN());
  ApplicantSupervisorBean[] refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
  Map<String, JobOpportunityBean> highlyRecommendedPools = JobOpportunityManager.getApplicantHighlyRecommendedPoolCompetitionsMap(profile.getSIN());
  
  //ReferenceBean[] chks = ReferenceManager.getReferenceBeans(profile);
  NLESDReferenceListBean[] chks = NLESDReferenceListManager.getReferenceBeansByApplicant(profile.getSIN());
  boolean validReference = true;
  if(chks.length == 0 || refs == null){
  	validReference = false;
  }
  
  RegionBean[] regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(profile).values().toArray(new RegionBean[0]);
  Collection<ApplicantDocumentBean> docs = ApplicantDocumentManager.getApplicantDocumentBean(profile);
  Collection<ApplicantCriminalOffenceDeclarationBean> cods = ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans(profile);
  
  if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW-PWD")) {
    session.setAttribute("APPLICANT", profile); 
  }
   
  HashMap<Integer, ApplicantSubListInfoBean> sublists = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
  
  EmployeeBean empbean = EmployeeManager.getEmployeeBeanByApplicantProfile(profile);
  
  Collection<InterviewSummaryBean> interviewSummaries = InterviewSummaryManager.getInterviewSummaryBeans(profile)
  		.stream().filter(isb -> (isb.getCompetition().getJobAwardedDate() != null || isb.getCompetition().getJobType().equal(JobTypeConstant.POOL)) && !isb.getCompetition().isUnadvertise()).collect(Collectors.toList());
  
  SimpleDateFormat sdf_medium = new SimpleDateFormat("MMM d, yyyy");
  
  Calendar cal = Calendar.getInstance();
  cal.clear(Calendar.HOUR);
  cal.clear(Calendar.MINUTE);
  cal.clear(Calendar.SECOND);
  cal.clear(Calendar.MILLISECOND);
  cal.add(Calendar.MONTH, -6);
  Date six_months = cal.getTime();
  //used to populate sub prefs
  Collection<SchoolZoneBean> zones = SchoolZoneService.getSchoolZoneBeans();
  School[] schools = null;
  HashMap<Integer, School> sel = ApplicantSubPrefManager.getApplicantSubPrefsMap(profile);  
  HashMap<Integer, ApplicantSubListInfoBean> sublists2  = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
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
<c:set var="SDSID" value="<%=((empbean != null) && org.apache.commons.lang.StringUtils.isNotBlank(empbean.getEmpId()))?empbean.getEmpId():\"N/A\" %>"/>
<c:set var="seniorityTotal" value="<%=empbean != null && empbean.getSeniority(EmployeeSeniorityBean.Union.NLTA) != null ? empbean.getSeniority(EmployeeSeniorityBean.Union.NLTA).getSeniorityTotal() :\"\"  %>"/> 
<c:set var="permContractSchool" value="<%=(esd_exp != null)&&(esd_exp.getPermanentContractSchool() != 0)&&(esd_exp.getPermanentContractSchool() != -1)?esd_exp.getPermanentContractLocationText() :\"N/A\"%>"/>
<c:set var="permContractPosition" value="<%=(esd_exp != null)&&(esd_exp.getPermanentContractSchool() != 0)&&(esd_exp.getPermanentContractSchool() != -1)?esd_exp.getPermanentContractPosition() :\"N/A\"%>"/>
<c:set var="repContractSchool" value="<%=(esd_exp != null)&&(esd_exp.getContractSchool() != 0)&&(esd_exp.getContractSchool() != -1)?esd_exp.getReplacementContractLocationText():\"N/A\"%>"/>
<c:set var="repContractEndDate" value="<%=(esd_exp != null)&&(esd_exp.getContractSchool() != 0)&&(esd_exp.getContractSchool() != -1)?esd_exp.getFormattedContractEndDate():\"N/A\"%>"/>
<c:set var="validEduOtherVar" value="<%=validEduOther%>"/>
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
					"lengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "All"]]
				}	  
	  );
	  $("#subPrefs").DataTable(
				{
					"order": [[ 0, "asc" ]],
					"lengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "All"]]	
				});
  	
  	$('.delete-cod').click(function(){
  		if(confirm('Are you sure you want to delete this Criminal Offence Declaration?')){
  			$(this).css({'color': "white"}).html('Deleting...');
  			return true;
  		}
  		else
  			return false;
  	});
  	
  	$('.delete-doc').click(function(){
  		if(confirm('Are you sure you want to delete this Document?')){
  			$(this).css({'color': "white"}).html('Deleting...');
  			return true;
  		}
  		else
  			return false;
  	});
  	


  	
  	$('#btn_show_add_applicant_dialog').click(function(){
  			
			$("#add_applicant_dialog").dialog('open');
			return false;
		});
  	
  	$('#btn_add_shortlist').click(function(){
  		if($("#hidshowsl").val() == "Y"){
  			$('#add_shortlist_dialog').modal('show');
  	  		return false;
  		}else{
  			var url="shortListApplicant.html?sin=" + $("#id").val();
  			$(location).attr('href',url);
  			
  		}
		});
  
  	$('#btn_add_shortlist_ok').click(function(){
	  	$("#response_msg_sl").hide();
			if($("#shortlistreason").val() == ""){
				$("#response_msg_sl").show();
			}else{
				var url="shortListApplicant.html?sin=" + $("#id").val() + "&slnotes=" + $("#shortlistreason").val();
	  			$(location).attr('href',url);
			}	
		});
  	
  	$('#butVerify').click(function(){
  		$('#confirm_verify_dialog').modal('show');
		});
  	
  	$('#btn_confirm_verify').click(function(){
  		var url="applicantVerification.html";
  		$('#frmverify').attr('action',url);
			$('#frmverify').submit();
  		$('#confirm_verify_dialog').modal('hide');
	});
  	
  	$('#btn_confirm_letter').click(function(){
  		$('#dalertadds').hide();
  		$('#dalertadd').hide();
  		if($('#ltitle').val() == ""){
  			$('#dmessageadd').text("Please enter title");
  			$('#dalertadd').show();
  		}else if($('#ldocument').val() == ""){
  			$('#dmessageadd').text("Please select letter");
  			$('#dalertadd').show();
  		}else{
  			//$('#dmessageadds').text("Good to Go");
  		  	//$('#dalertadds').show();
  		  	addApplicantLetter();
  		}
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
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:30%;}

input {    
    border:1px solid silver;
}

</style>
</head>
<body>
                      
<!-- DEMOGRAPHICS --------------------------------------------------------------->  

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>DEMOGRAPHICS</b></div>
      			 	<div class="panel-body">      
      			 	<span style="color:grey;">TEACHER/TLA/ADMIN PROFILE for:</span>			<br/>		 	
      			 				<span style="font-size:20px;padding-top:10px;color:#007d01;font-weight:bold;">${nameDisplay}</span><br/>
      			 				<input type="hidden" id="hidshowsl" value="<%=session.getAttribute("sfilterparams") == null ? 'Y':'N'%>">
      			 				<input type="hidden" id="id" value="<%=profile.getSIN() %>">
      			 				<input type='hidden' id="appname" value="${fullName}">
      			 	 			<c:if test="${APPLICANT.modifiedDate ne null}">
                       				<span style="color:Silver;text-align:right;">Last Modified: <fmt:formatDate pattern='MMMM dd, yyyy' value='${APPLICANT.modifiedDate}'/></span>
                     			</c:if>
      			 			<br/>
      			 			
                    	<div class="table-responsive">       			 	       
      			 	       <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
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
					    				<c:choose>
					    					<c:when test="${ validEduOtherVar eq true}">
					    						<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> This Profile has not been verified.</span>
					    						<esd:SecurityAccessRequired roles="ADMINISTRATOR,MANAGER OF HR,MANAGER OF HR - PERSONNEL,SEO - PERSONNEL,SENIOR EDUCATION OFFICIER">
													<button type="button" class="btn btn-success btn-xs" id="butVerify" style="margin-left:10px;"><span class="glyphicon glyphicon-thumbs-up"></span> Verify</button>
												</esd:SecurityAccessRequired>
					    					</c:when>
					    					<c:otherwise>
					    						<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> Applicant has incomplete profile:  Missing Other Education - Certification Level and/or Certification Issue Date </span>
					    					
					    					</c:otherwise>
					    				</c:choose>
					    				
					    				
					    			</c:otherwise>
					    		</c:choose>
					    		</td>
							    </tr>
				 
							    <tr>
							    <td class="tableTitle">NAME:</td>
							    <td class="tableResult">${fullName}</td>
							    <%if(usr.checkRole("ADMINISTRATOR")){%>
	                            <td class="tableTitle">UUID:</td>
							    <td class="tableResult">${UUID}</td>
							     <%} else {%>
							    <td colspan=2></td>
							    <%}%>
							    							    
							    </tr>
							     <tr>
							    <td class="tableTitle">ADDRESS:</td>
							    <td colspan=3>${primaryAddress}${secondaryAddress}, ${prov }, ${country } &nbsp; ${postalCode }</td>
							    </tr>
							    <tr>
							    <td class="tableTitle">TEL #:</td>
							    <td>${homePhone}</td>
							    <td class="tableTitle">CELL #:</td>
							    <td>${cellPhone}</td>
							    
							    </tr>
							    <tr>
							    <td class="tableTitle">WORK #:</td>
							    <td>${workPhone}</td>
							    <td class="tableTitle">EMAIL:</td>
							    <!-- Check to see if email is old esdnl, wsdnl, ncsd, or lsb. -->
	                                      <c:set var="emailCheck" value ="${emailAddress}"/>
		                                      <c:choose>
			                                      <c:when test="${fn:endsWith(emailCheck,'@esdnl.ca') or fn:endsWith(emailCheck,'@wnlsd.ca') or fn:endsWith(emailCheck,'@lsb.ca') or fn:endsWith(emailCheck,'@ncsd.ca')}">
			                                       <td title="Email needs updating. Invalid address." style="background-color:#fde8ec;"><span>${emailAddress} (Need update)</span></td>   
			                                      </c:when>
		                                      <c:otherwise>
		                                      		 <td><a href="mailto:${emailAddress}">${emailAddress}</a></td>   
		                                      </c:otherwise>
	                                      </c:choose>							    
							    
							    
							   
							    </tr>
							   
						<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-PWD"> 
								    <tr class="no-print">
								    <td class="tableTitle">Password:</td>
								    <td>${password}</td>
								    <td><a href="#" class="btn btn-info btn-xs" onclick="onSendApplicantLoginInfoEmail(<%=profile.getUID()%>);"><span class="glyphicon glyphicon-envelope"></span> Email Credentials</a></td>
								    <td><a href="/employment/index.jsp?uid=<%=PasswordEncryption.encrypt(profile.getSIN())%>" class="btn btn-warning btn-xs" target="_blank"><span class="glyphicon glyphicon-user"></span> Sign In</a></td>
								    </tr>
						</esd:SecurityAccessRequired>
							    
							    <tr>
							    	<td class="tableTitle">SDS Employee ID:</td>
								    <td>${SDSID}</td>
							    	<td class="tableTitle">Years of Service:</td>
							    	<td>
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
							    
							    <tr>
										<td colspan='1' class='tableTitle'>Self-Reported Contract:</td>
								    <c:choose>
									    <c:when test="${permContractSchool ne 'N/A' }">
										    <td colspan='3'>${permContractSchool} ending ${permContractPosition} - PERMANENT</td>
									    </c:when>
									    <c:when test="${repContractSchool ne 'N/A' }">
										    <td colspan='3'>${repContractSchool} ending ${repContractEndDate} - REPLACEMENT</td>
									    </c:when>
									    <c:otherwise>
										    	<td colspan='3'>N/A</td>
									    </c:otherwise>
								    </c:choose>
							    </tr>
							    
							    <tr>
							    	<td class='tableTitle'>SDS Current Contract:</td>
							    	<td colspan='3'>
							    		<%
							    			if(empbean != null && empbean.getCurrentPositions() != null) { 
							    				List<EmployeePositionBean> positions = empbean.getCurrentPositions().stream().filter(p -> p.getEndDate().after(new Date())).collect(Collectors.toList());
							    				System.out.println(positions);
							    				if(positions != null && positions.size() > 0) {
							    					String pp = positions.stream()
								    						.filter(p -> p.getFteHours() > 0)
								    						.sorted((EmployeePositionBean p1, EmployeePositionBean p2) -> {
								    							if((p1.getEndDate() != null) && (p2.getEndDate() != null) && (p1.getEndDate().compareTo(p2.getEndDate()) != 0)) {
								    								return -1 * p1.getEndDate().compareTo(p2.getEndDate());
								    							}
								    							else  if((p1.getStartDate() != null) && (p2.getStartDate() != null) && (p1.getStartDate().compareTo(p2.getStartDate()) != 0)) {
								    								return -1 * p1.getStartDate().compareTo(p2.getStartDate());
								    							}
								    							else {
								    								return -1 * p1.getSchoolYear().compareTo(p2.getSchoolYear());
								    							}
								    						})
								    						.map(p -> p.toString())
								    						.collect(Collectors.joining("<br />"));
								    				out.println(!StringUtils.isEmpty(pp) ? pp : "N/A");
							    				}
							    				else {
							    					out.println("N/A");
							    				}
							    			}
							    			else {
							    				out.println("N/A");
							    			}
							    		%>
							    	</td>
							    </tr>
							    
							    </tbody>
							    </table>
      			 	       
						</div>
	              
	              </div>
  </div>
  </div>
       			 
<!-- SUBLIST APPLICATIONS --------------------------------------------------------------->   
	
<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-APROVETO-SUBLIST">  	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section1">   
	               	<div class="panel-heading"><b>Current SubList Applications</b></div>
      			 	<div class="panel-body"> 	
                              	
                                	
         
               <%
           			ApplicantSubListInfoBean subL = null;           		
           			@SuppressWarnings("unchecked")
           			Map.Entry<Integer, ApplicantSubListInfoBean>[] entry = (Map.Entry<Integer, ApplicantSubListInfoBean>[])sublists.entrySet().toArray(new Map.Entry[0]);
           			
           			if(entry.length > 0){
           				
           			%>	
           			<table class="table table-condensed" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;" id="sublisttable">
				    <thead>
				      <tr style="border-top:1px solid black;">
				        <th width='10%'>YEAR</th>
				        <th width='15%'>REGION (ZONE)</th>
				        <th width='35%'>LIST</th>								        
				        <th width='10%'>APPLIED</th>								        
				        <th width='30%'>STATUS</th>								      
				      </tr>
				    </thead>
				    
				    <tbody>	
           			<tr><td colspan='5'>
           			<div class="alert alert-danger" role="alert" id="response_msg_sld" style="display:none;">
  					</div>
           			</td></tr>	
           			<% 	
            		for(int i=0; i < entry.length; i++){
            		subL = (ApplicantSubListInfoBean) entry[i].getValue();
            		
            	%>	
		            <c:set var="subListApp" value="<%=subL%>"/> 
		         <tr>    		             		             
		             	<td>${subListApp.subList.schoolYear}</td>
		             
		             
		             
		             <c:choose>
		             	<c:when test="${(subListApp.subList.region.zone.zoneName eq 'avalon') or (subListApp.subList.region.zone.zoneName eq 'eastern')}">
		             		<td class="region1">Avalon
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.zone.zoneName eq 'central'}">
		             		<td class="region2">Central
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.zone.zoneName eq 'western'}">
		             		<td class="region3">Western
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.zone.zoneName eq 'labrador'}">
		             		<td class="region4">Labrador
		             	</c:when>
		             	<c:otherwise>
		             		<td class="region5">Provincial
		             	</c:otherwise>
		             </c:choose>
		             
		             <c:choose>
		             	<c:when test="${(subListApp.subList.region.name eq 'all eastern region') or (subListApp.subList.region.name eq 'all avalon region')}">
		             		(All)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'avalon west'}">
		             		(West)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'avalon east'}">
		             		(East)
		             	</c:when>         	
		             	<c:when test="${subListApp.subList.region.name eq 'all central region'}">
		             		(All)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'vista'}">
		             		(Vista)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'burin'}">
		             		(Burin)
		             	</c:when>
		             	<c:when test="${(subListApp.subList.region.name eq 'central') and (subListApp.subList.region.zone.zoneName eq 'central')}">
		             		(Central)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'all western region'}">
		             		(All)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'green bay / white bay'}">
		             		(Green Bay / White Bay)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'northern'}">
		             		(Northern)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'southern'}">
		             		(Southern)
		             	</c:when>
		             	<c:when test="${(subListApp.subList.region.name eq 'central') and (subListApp.subList.region.zone.zoneName eq 'western')}">
		             		(Central)
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'all labrador region'}">
		             		(All)		             	
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'coastal'}">
		             		(Coastal)		             	
		             	</c:when>
		             	<c:when test="${subListApp.subList.region.name eq 'western'}">
		             		(Western)		             	
		             	</c:when>
		             	<c:when test="${(subListApp.subList.region.zone.zoneName eq 'labrador') and (subListApp.subList.region.name eq 'eastern')}">
		             		(Eastern)		             	
		             	</c:when>		             	
		             	<c:otherwise>
		             			             	
		             	</c:otherwise>
		             	</c:choose>
		             	
		             	</td>
		             	<td>${subListApp.subList.title}</td>               	
		        		<td>${subListApp.appliedDateFormatted}</td>			   
			    
			    <td>			                                				
			    <%if(usr.checkPermission("PERSONNEL-ADMIN-APROVETO-SUBLIST")){
			             if(subL.isNewApplicant()){
			            	 
			            	 %>
			            	 <a class='confirm_approve_link btn btn-xs btn-success no-print' onclick="openSublistDialog('${UUID}','${subListApp.subList.id}','A')"><span class="glyphicon glyphicon-ok"></span> Approve</a> &nbsp;
			            	 <a class='confirm_not_approve_link btn btn-xs btn-danger no-print' onclick="openSublistDialog('${UUID}','${subListApp.subList.id}','NA')"><span class="glyphicon glyphicon-remove"></span> Do Not Approve</a>&nbsp;
			            	 <a class='confirm_not_approve_link btn btn-xs btn-info no-print' onclick="showHistory('${UUID}','${subListApp.subList.id}')"><span class="glyphicon glyphicon-list-alt"></span> History</a>
			            	 <%
			                 } else { 						
    							
   					%>
   					 <c:choose>
   					 	<c:when test="${subListApp.status eq 'Y'}"><span style="color:Green;">APPROVED</span></c:when>
   						<c:when test="${subListApp.status eq 'N'}"><span style="color:#FF0000;">NOT APPROVED</span></c:when>
   					    <c:when test="${subListApp.status eq 'W'}"><span style="color:#D2691E;">IN A POSITION/REMOVED</span></c:when>
   					    <c:otherwise><span style="color:#6495ED;">NEW!</span></c:otherwise>
   					 </c:choose>
 <!--  MODIFIED -->   					 
   					 &nbsp;<span  class="no-print"><a class='confirm_not_approve_link btn btn-xs btn-info no-print' onclick="showHistory('${UUID}','${subListApp.subList.id}')"><span class="glyphicon glyphicon-list-alt"></span> History</a></span>
   					 &nbsp;<span class="no-print"><a class='confirm_reset_link btn btn-xs btn-warning' onclick="openSublistDialog('${UUID}','${subListApp.subList.id}','R')"><span class="glyphicon glyphicon-refresh"></span> Reset</a></span>
   							
   					<%	}	               
			    
			    	}else{
			    		
			    		%>
			    		<c:choose>
   					 	<c:when test="${subListApp.status eq 'Y'}"><span style="color:Green;">APPROVED</span></c:when>
   						<c:when test="${subListApp.status eq 'N'}"><span style="color:#FF0000;">NOT APPROVED</span></c:when>
   					   	<c:when test="${subListApp.status eq 'W'}"><span style="color:#D2691E;">IN A POSITION/REMOVED</span></c:when>
   					    <c:otherwise><span style="color:##6495ED;">NEW!</span></c:otherwise>
   					 </c:choose>
			    		
			    	<% } %>
			    	
			    	</td></tr>
			    	<tr id="sl${subListApp.subList.id}" style="display: none;">
			    	<td colspan='5'>
			    	<div class="alert alert-info">
					    	<span id="sltitle${subListApp.subList.id}" style="font-size:12px;font-weight:bold;"></span><br/>
					    	<input class="form-control"  type="text" id="sltext${subListApp.subList.id}" style="width:100%;" maxlength="250"><br/>
					    	<div style="text-align:right;padding-top:3px;">
					    	<input type="button" class="btn btn-xs btn-danger" onclick="submitSubListRow('${subListApp.subList.id}',this.value)" id="bs${subListApp.subList.id}" value="">
					    	<input type="button" class="btn btn-xs btn-primary" onclick="cancelSubListRow('${subListApp.subList.id}',this.value)" id="bc${subListApp.subList.id}" value="">
					    	</div>
					    	</div>
					 </td></tr>
			    	<% } %>
            		<tr id="historyrow" style="display: none;" class="info">
            		<td colspan='5' align="center">	
            		<span style="color:#6495ED;font-weight:bold;font-size:12px;">AUDIT HISTORY</span><br/>	            		
  <!-- -END MODIFIED -->          			    	
						    	<table class="table table-striped table-condensed" style="width:100%;font-size:11px;" id="historytable">						    	
						    	<tr>
						    	<th width="80%">ACTION</th>
						    	<th width="20%" align="center">DATE</th>						    	
						    	</tr>						    
						    	</table>
						    	
						    	<input type="button" class="btn btn-xs btn-danger" onclick="closeTableHistory()" value="CLOSE">
			    	</td>
			    	</tr>
           			</tbody>
           			</table>
           			
           			
           			<% }else { %>
	                        <span style="color:Grey;">No substitute applications on record for current school year.</span>
	                        <script>$("#section1").removeClass("panel-success").addClass("panel-danger");</script>
	                <%}%>
	                <br/>
	                <div class="no-print">	                 
	                <a href="#" data-toggle="modal" data-target="#add_applicant_dialog" id="btn_show_add_applicant_dialog" class="btn btn-xs btn-primary" onclick="return false;"><span class="glyphicon glyphicon-plus"></span> Add Sublist</a>
					</div></div>
					</div>
</div>					
 </esd:SecurityAccessRequired>	


<!--SUBSTITUTE PREFERENCES ----------------------------------------------------------------------------------------->

	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b>SUBSTITUTE PREFERENCES</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
				<table id="subPrefs" class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
					<thead>
						<tr>
							<th width=45%'>SCHOOL</th>
							<th width='20%'>CITY/TOWN</th>	
							<th width='20%'>REGION</th>
							<th width='15%'>REGIONAL ZONE</th>							
														        															       
						</tr>
					</thead>
					<tbody>
					    <%Collection<RegionBean> regions = null;
                        	for(SchoolZoneBean zone : zones) {
                             	regions = RegionManager.getRegionBeans(zone);
                             	
						%>
							<% for(RegionBean region : regions) { %>
								<%if(regions == null || regions.size() <= 1) { %>
									<%schools = SchoolDB.getSchools(zone).toArray(new School[0]);                                      		
                                	for(int j=0; j < schools.length; j++){%>
	                                	<% if(sel.containsKey(schools[j].getSchoolID())){%>
	                                		<tr>
	                                		<td><%=schools[j].getSchoolName()%></td>
	                                		<td>schools[j].getTownCity()</td>
	                                		<td style="text-transform:Capitalize;"><%=zone.getZoneName()%></td>
	                                		<td  style="color:Silver;">N/A</td>
	                                		</tr>
	                                	<% }%>
	                                <%} %>
									
                        		<%}else{
                        			if(region.getName().contains("all")) continue;%>
                        			<%schools = SchoolDB.getSchools(region).toArray(new School[0]);
                        			%>
                        			<%for(int j=0; j < schools.length; j++){%>
	                                	<% if(sel.containsKey(schools[j].getSchoolID())){%>
	                                		<tr>
	                                		<td><%=schools[j].getSchoolName()%></td>
	                                		<td><%=schools[j].getTownCity()%></td>
	                                		<td  style="text-transform:Capitalize;"><%=zone.getZoneName()%></td>
	                                		<td style="text-transform:Capitalize;"><%=region.getName() %></td>
	                                		</tr>
	                                	<% }%>
	                                <%} %>
                        		<%}%>
                        	<%}%> 
                        <%}%> 
					</tbody>
					</table> 
				
				</div>
			</div>
		</div>
	</div>

	


<!-- POST SECONDARY EDUCATION --------------------------------------------------------------->

	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section2">   
	               	<div class="panel-heading"><b>University/College Education</b></div>
      			 	<div class="panel-body"> 	
								
									    <% if((edu != null) && (edu.length > 0)) {
									    	
									    	%>
									    	
									    <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>
									      <tr style="border-top:1px solid black;">
									        <th width='20%'>INSTITUTION</th>
									        <th width='10%'>DATES (M/Y)</th>
									        <th width='25%'>PROGRAM &amp; FACULITY</th>								        
									        <th width='17%'>MAJOR (#crs)</th>		
									        <th width='17%'>MINOR (#crs)</th>							        
									        <th width='11%'>DEGREE CONF.</th>								      
									      </tr>
									    </thead>
									    
									    <tbody> 
									    	
									    <% for(int i=0; i < edu.length; i ++) { %>
							                                   <c:set var="edu" value="<%=edu[i]%>" />
							                                   <tr>  
							                                   <td>${edu.institutionName}</td> 
							                                   <td><fmt:formatDate pattern = "MM/yy" value = "${edu.from}" /> - <fmt:formatDate pattern = "MM/yy" value = "${edu.to}" /></td> 
							                                   <td>${edu.programFacultyName}</td> 
							                                   <td>
							                                    <% if(edu[i].getMajor_other() > 0) { %>
                                      									<%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName() + (edu[i].getMajor_other() > 0 ? ", " + SubjectDB.getSubject(edu[i].getMajor_other()).getSubjectName():"") +	" (" + edu[i].getNumberMajorCourses()+ ")<br/>"%> 
                                      							<% } else if(edu[i].getMajor() != -1) { %>
                                        								 <%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName() + " (" + edu[i].getNumberMajorCourses()+ ")<br/>"%>        
                                        						<%} else {%>
                                       									N/A<br/>
																<%} %>
							              						</td> 
							                                   <td>
							                                    <c:choose>
							                                   		<c:when test="${edu.minor ne '-1' }">
							                                  			<%=SubjectDB.getSubject(edu[i].getMinor()).getSubjectName()%> (${edu.numberMinorCourses})
							                                   		</c:when>
							                                   		<c:otherwise>N/A</c:otherwise>
							                                   </c:choose>
							                                   </td>
							                                   		<td><%=((!StringUtils.isEmpty(edu[i].getDegreeConferred()))?DegreeManager.getDegreeBeans(edu[i].getDegreeConferred()).getAbbreviation():"&nbsp;")%></td> 
							                                   </tr>
							                                     <% } %>
									    
									    </tbody>
							        </table>
									    
									    <% } else { %>
							         <span style="color:Grey;">No post secondary education currently on file.</span>
							         <script>$("#section2").removeClass("panel-success").addClass("panel-danger");</script>
							          <% } %>
							        	
					</div>
					</div>
</div>
  
            
                             


<!-- OTHER EDUCATION --------------------------------------------------------------->
	
 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section3">   
	               	<div class="panel-heading"><b>Other Education</b></div>
      			 	<div class="panel-body"> 	
	  						<c:set var="eduOther" value="<%=edu_oth%>" />
                               
                         <div class="table-responsive"> 
      			 	       
      			 	       <table class="table table-striped table-condensed" style="font-size:12px;">
							   
							    <tbody>
							    
							     <c:choose>
	                               <c:when test="${eduOther ne null}">
	                               
		                            <tr>
								    <td class="tableTitle">Training Method:</td>
								    <td class="tableResult">${eduOther.professionalTrainingLevel.description}</td>							    
		                            <td class="tableTitle"></td>
								    <td class="tableResult"></td>							    
								    </tr>
								    <tr>
								    <td class="tableTitle">Special Ed. Courses:</td>
								    <td class="tableResult">${eduOther.numberSpecialEducationCourses}</td>								    							    
		                            <td class="tableTitle">French Language Courses:</td>
								    <td class="tableResult">${eduOther.numberFrenchCourses }</td>							    
								    </tr>
								    <tr>
								    <td class="tableTitle"> Math Courses:</td>
								    <td class="tableResult">${eduOther.numberMathCourses }</td>								    
		                            <td class="tableTitle">English Courses:</td>
								    <td class="tableResult">${eduOther.numberEnglishCourses }</td>								    
								    </tr>
								    <tr>
								    <td class="tableTitle">Music Courses:</td>
								    <td class="tableResult">${eduOther.numberMusicCourses }</td>								    
		                            <td class="tableTitle">Technology Courses:</td>
								    <td class="tableResult">${eduOther.numberTechnologyCourses }</td>								    
								    </tr>
								     <tr>
								    <td class="tableTitle">Social Studies Courses:</td>
								    <td class="tableResult">${eduOther.numberSocialStudiesCourses }</td>								    
		                            <td class="tableTitle">Art Courses:</td>
								    <td class="tableResult">${eduOther.numberArtCourses }</td>								    
								    </tr>
							        <tr>
								    <td class="tableTitle">Science Courses:</td>
								    <td class="tableResult">${eduOther.numberScienceCourses }</td>								    
		                            <td class="tableTitle">Total Completed:</td>
								    <td class="tableResult">
								    <c:choose>
								    <c:when test="${eduOther.totalCoursesCompleted le '0' }">
								    <!-- <span style="color:red;">0 - PROFILE INCOMPLETE</span>-->
								    ${eduOther.totalCoursesCompleted }
								    </c:when>
								    <c:otherwise>${eduOther.totalCoursesCompleted }</c:otherwise>
								    </c:choose>
								    </td>								    
								    </tr>
								    <tr>
								    <td class="tableTitle">Certification Level:</td>
								    <td class="tableResult"><%=!StringUtils.isEmpty(edu_oth.getTeachingCertificateLevel()) ? edu_oth.getTeachingCertificateLevel() : "N/A"%></td>								    
		                            <td class="tableTitle">Certification Issue Date:</td>
								    <td class="tableResult"><%=((edu_oth.getTeachingCertificateIssuedDate() != null) ? sdf.format(edu_oth.getTeachingCertificateIssuedDate()) : "N/A")%></td>								    
								    </tr>
							     </c:when>
	                               <c:otherwise>
	                              	<tr><td colspan=4><span style="color:Grey;">No other education currently on file.</span></td></tr>
	                              	<script>$("#section3").removeClass("panel-success").addClass("panel-danger");</script>
	                               </c:otherwise>
                               </c:choose>
							    
							    
                               </tbody>
                             </table>  
                          </div>     
                               
					</div>
					</div>
</div>                               
                                                 
<!-- NLESD PERMANENT EXPERIENCE --------------------------------------------------------------->

	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section4">
			<div class="panel-heading">
				<b>NLESD Permanent Experience</b> (Total Months: <%=((esd_exp != null) ? Integer.toString(esd_exp.getPermanentLTime()) : "UNKNOWN")%>)
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						boolean hasPermanents = (empbean != null) && empbean.hasPermanentPositions();
						if (((per != null) && (per.length > 0)) || hasPermanents) {
					%>
					<table class="table table-condensed table-striped"
						style="font-size: 11px; background-color: #FFFFFF; margin-top: 10px;">
						<thead>
							<tr style="border-top: 1px solid black;">
								<th width='20%'>FROM</th>
								<th width='20%'>TO</th>
								<th width='20%'>SCHOOL</th>
								<th width='40%'>GRADES AND/OR SUBJECTS TAUGHT</th>
							</tr>
						</thead>

						<tbody>
						<tr><td colspan='4'><i><b>Self-Reported Permanent Positions:</b></i></td></tr>
						<% if((per != null) && (per.length > 0)) { %>
							<% int cnt = 1; 
								for (int i = 0; i < per.length; i++) { %>
								<tr>
									<td>[<%= cnt++ %>]&nbsp;<%=sdf.format(per[i].getFrom())%></td>
									<td><%=sdf.format(per[i].getTo())%></td>
									<td><%=SchoolDB.getLocationText(per[i].getSchoolId())%></td>
									<td><%=per[i].getGradesSubjects()%></td>
								</tr>
							<% } %>
						<% } else { %>
							<tr><td colspan='4'>No records found.</td></tr>
						<% } %>
						<tr><td colspan='4'><i><b>SDS Permanent Positions:</b></i></td></tr>
						<% if(hasPermanents) { %>
								<% int cnt = 1;
									for(EmployeePositionBean p : empbean.getPermanentPositions()) { %>
										<tr>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>>[<%= cnt++ %>][<i><%= p.getSchoolYear() %></i>]&nbsp;<%= p.getStartDateFormatted() %></td>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>><%= p.getEndDate() != null ? p.getEndDateFormatted() : "&nbsp;" %></td>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>><%= p.getLocation() %></td>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>>
												<%= p.getPosition() %> (<%= p.getTenure() %>) (<span <%= p.isLeave() ? " class='text-danger' style='font-weight: bold;'" : "" %>><%= p.getPositionType() %></span>) <%= p.isError() ? "(ERROR)" : "" %>
											</td>
										</tr>
								<% } %>
						<% } else { %>
							<tr><td colspan='4'>No records found.</td></tr>
						<% } %>
						</tbody>
					</table>
					<% } else { %>
						<span style="color: Grey;">No NLESD Permanent Experience currently on file.</span>
						<script>$("#section4").removeClass("panel-success").addClass("panel-danger");</script>
					<% } %>

				</div>
			</div>
		</div>
	</div>

	<!-- NLESD REPLACEMENT EXPERIENCE --------------------------------------------------------------->

	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section5">
			<div class="panel-heading">
				<b>NLESD Replacement Contract Experience</b> (Total Months: <%=((esd_exp != null) ? Integer.toString(esd_exp.getReplacementTime()) : "UNKNOWN")%>)
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						boolean hasReplacements = (empbean != null) && empbean.hasReplacementPositions();
						if (((rpl != null) && (rpl.length > 0)) || hasReplacements) {
					%>
					<table class="table table-condensed table-striped"
						style="font-size: 11px; background-color: #FFFFFF; margin-top: 10px;">
						<thead>
							<tr style="border-top: 1px solid black;">
								<th width='20%'>FROM</th>
								<th width='20%'>TO</th>
								<th width='20%'>SCHOOL</th>
								<th width='40%'>GRADES AND/OR SUBJECTS TAUGHT</th>
							</tr>
						</thead>
						<tbody>
							<tr><td colspan='4'><i><b>Self-Reported Replacement Positions:</b></i></td></tr>
							<% if((rpl != null) && (rpl.length > 0)) { %>
								<% int cnt = 1; 
									for (int i = 0; i < rpl.length; i++) { %>
									<tr>
										<td>[<%= cnt++ %>]&nbsp;<%=sdf.format(rpl[i].getFrom())%></td>
										<td><%=sdf.format(rpl[i].getTo())%></td>
										<td><%=SchoolDB.getLocationText(rpl[i].getSchoolId())%></td>
										<td><%=rpl[i].getGradesSubjects()%></td>
									</tr>
								<% } %>
							<% } else { %>
								<tr><td colspan='4'>No records found.</td></tr>
							<% } %>
							<tr><td colspan='4'><i><b>SDS Replacement Positions:</b></i></td></tr>
							<% if(hasReplacements) { %>
								<% int cnt = 1;
									for(EmployeePositionBean p : empbean.getReplacementPositions()) { %>
										<tr>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>>[<%= cnt++ %>][<i><%= p.getSchoolYear() %></i>]&nbsp;<%= p.getStartDateFormatted() %></td>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>><%= p.getEndDate() != null ? p.getEndDateFormatted() : "&nbsp;" %></td>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>><%= p.getLocation() %></td>
											<td <%= p.isError() ? " class='text-danger' style='font-weight: bold;'" : "" %>>
												<%= p.getPosition() %> (<%= p.getTenure() %>) (<span <%= p.isLeave() ? " class='text-danger' style='font-weight: bold;'" : "" %>><%= p.getPositionType() %></span>) <%= p.isError() ? "(ERROR)" : "" %>
											</td>
										</tr>
								<% } %>
							<% } else { %>
								<tr><td colspan='4'>No records found.</td></tr>
							<% } %>
						</tbody>
					</table>
					<% } else { %>
						<span style="color: Grey;">No Replacement Contract Experience currently on file.</span>
						<script>$("#section5").removeClass("panel-success").addClass("panel-danger");</script>
					<% } %>
				</div>
			</div>
		</div>
	</div>



	<!-- SUBSTITUE TEACHING EXPERIENCE --------------------------------------------------------------->

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section6">   
	               	<div class="panel-heading"><b>NLESD Substitute Teaching Experience</b> (Total Sub Days: <%=((esd_exp != null)?Integer.toString(esd_exp.getSubstituteTime()):"UNKNOWN")%>)</div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
      			 	       
      			 	       
							   <% if((sub != null) && (sub.length > 0)) { %>
							   <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>									    
									      <tr style="border-top:1px solid black;">
									        <th width='30%'>FROM</th>
									        <th width='30%'>TO</th>									       								        
									        <th width='40%'># DAYS PER YEAR</th>		
									      </tr>
									    </thead>
							    <tbody>							   
                               <% for(int i=0; i < sub.length; i ++)
                                   {							   
							   %>
							    <tr>
							    <td><%=sdf.format(sub[i].getFrom()) %></td>
							    <td><%=sdf.format(sub[i].getTo()) %></td>							    
							    <td><%=sub[i].getNumDays() %></td>
							    </tr>
							    <% }%>
							    </tbody>
							    </table>							    
							    <%} else { %>
							   <span style="color:Grey;">No Substitute Experience currently on file.</span>
							   <script>$("#section6").removeClass("panel-success").addClass("panel-danger");</script>
                               <%}%>
					</div>
					</div>
					</div>
</div>


                                
<!-- OTHER BOARD EXPERIENCE --------------------------------------------------------------->                                 

	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section7">   
	               	<div class="panel-heading"><b>Other Board Experience</b></div>
      			 	<div class="panel-body"> 	
	
					<div class="table-responsive"> 
      			 	       
      			 	      
							  <%if((exp_other != null) && (exp_other.length > 0))
                                  { %>
                                   <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>
									    
									      <tr style="border-top:1px solid black;">
									        <th width='15%'>FROM</th>
									        <th width='15%'>TO</th>	
									        <th width='30%'>SCHOOL &amp; BOARD</th>								       								        
									        <th width='40%'>GRADES AND/OR SUBJECTS TAUGHT</th>		
									      </tr>
									    </thead>
							    
							    <tbody>
                                <% for(int i=0; i < exp_other.length; i ++) { %>
							    <tr>
							    <td><%=sdf.format(exp_other[i].getFrom()) %></td>
							    <td><%=sdf.format(exp_other[i].getTo()) %></td>							    
							    <td><%=exp_other[i].getSchoolAndBoard() %></td>
							    <td><%=exp_other[i].getGradesSubjects() %></td>
							    </tr>
							    <% }%>
                                </tbody>
							    </table> 
                                  
                                  
                                  <%} else { %>
							   
                                   <span style="color:Grey;">No Other Board Experience currently on file.</span>
                                   <script>$("#section7").removeClass("panel-success").addClass("panel-danger");</script>
                                 <%}%> 
							    
							    
						
					</div>
					</div>
					</div>
</div>

                                 
                            

<!-- OTHER INFORMATION --------------------------------------------------------------->
<div class="page-break"></div>	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section8">   
	               	<div class="panel-heading"><b>Other Information</b></div>
      			 	<div class="panel-body"> 	
					<%if((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())) { %>                    
                    <span style="font-size:11px;"><%=other_info %></span>                    
                    <%} else { %>
                    <span style="color:Grey;">No Other Information currently on file.</span>
                    <script>$("#section8").removeClass("panel-success").addClass("panel-danger");</script>
                    <%}%>                    
					</div>
					</div>
</div>

                               



   
                                

<!-- REFERENCES --------------------------------------------------------------->
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section10">   
	               	<div class="panel-heading"><b>References</b></div>
      			 	<div class="panel-body"> 	
	
					<div class="table-responsive"> 
      			 	       
      			 	       
							  <% if((refs != null) && (refs.length > 0))
                              		{ %>
                              <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>
									    
									      <tr style="border-top:1px solid black;">
									        <th width='25%'>NAME (TITLE)</th>									   
									        <th width='25%'>ADDRESS</th>								       								        
									        <th width='15%'>TELEPHONE</th>
									        <th width='15%'>EMAIL</th>
									        <th width='10%'>STATUS</th>
									        <esd:SecurityAccessRequired roles="ADMINISTRATOR">
									        <th width='10%'>OPTIONS</th>		
									        </esd:SecurityAccessRequired>
									      </tr>
									    </thead>
							    
							    <tbody>
                              
                              
                              <% for(int i=0; i < refs.length; i ++)
                                  	{
                               %>
							    <tr>
							    <td width='25%'><%=refs[i].getName()%> (<%=refs[i].getTitle()%>)</td>							    				    
							    <td width='25%'><%=refs[i].getAddress()%></td>
							    <td width='15%'><%=refs[i].getTelephone()%></td>
							    <%out.println("<td width='15%'>" + ((refs[i].getApplicantRefRequestBean()!= null)?refs[i].getApplicantRefRequestBean().getEmailAddress():"N/A" ) +"</td>");%>
							    
							<%    
							    Date date = new Date();		//Get today			
									int refResendTimeLeft=0;	
									int refDiff,refDiffResend,refDateRequested,refCurrentDate,refTimeLeft;; //declare ints 			
									int refExpiredTimeY = 8760; //# hours in a year exact. References expire after a year.
									
									int refExpiredTimeM = 720; //# hours in a month exact. References expire after a year, can del after a month
									
									int refExpiredTimeToResend = 72; //#cant resend a ref request until 72 hours after initial send.		
									
									int refExpiredTimeToDel = 720; //#cant delete a ref request until 720 hours (30 days) after completion.
									
									if  (refs[i].getApplicantRefRequestBean()!=null && refs[i].getApplicantRefRequestBean().getDateStatus()!=null) {								
									 
										refDateRequested = (int) TimeUnit.MILLISECONDS.toHours(refs[i].getApplicantRefRequestBean().getDateStatus().getTime());
									 	refCurrentDate = (int) TimeUnit.MILLISECONDS.toHours(date.getTime());			 
									 	refDiff = (refCurrentDate - refDateRequested)-12;	//do the math hours
									 	refDiffResend = refDateRequested + refExpiredTimeToResend+12; //Add the date time to current date			 
									 	refResendTimeLeft =refDiffResend - refDateRequested; //get hours			 	
									 	refTimeLeft =  refExpiredTimeToResend-refDiff;
									 	//Dont use LONGS use the TimeUnit!!!!
									 	if(refTimeLeft < 0) {
									 		refTimeLeft = 0;
									 	}
									} else {
										refDateRequested = 0; //if null 0 all variables
									 	refCurrentDate = 0;									
									 	refDiff = 0;		
									 	refDiffResend = 0;
									 	refResendTimeLeft = 0;
									 	refTimeLeft =  0;
									 	
									}		
			
			
			if(refDiff >refExpiredTimeY) { 
				out.println("<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> EXPIRED</td>");					
				} else {					
						if(refs[i].getApplicantRefRequestBean() == null || refs[i].getApplicantRefRequestBean().getRequestStatus() == null){
						out.println("<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>");						
						} else if(refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST SENT")){	
						out.println("<td width='10%' class='info' style='text-align:center;'><i class='far fa-clock'></i> SENT/PENDING</td>");							
						} else if(refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")){
						out.println("<td width='10%' class='success' style='text-align:center;'><i class='fas fa-check'></i> COMPLETED</td>");							
						} else if(refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST DECLINED")){
						out.println("<td width='10%' class=danger' style='text-align:center;'><i class='fas fa-times'></i> DECLINED</td>");							
						} else {
						out.println("<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>");					
						}								
			}%>						
				<esd:SecurityAccessRequired roles="ADMINISTRATOR">											
					<td  width='10%' style='text-align:right;'>
					<a title='Admin Delete Reference (Will place this reference in Other References Area)' class='btn-xs btn btn-danger' href='#' onclick="deleteref(this,'<%=refs[i].getId()%>')">DEL</a>
					</td>
				</esd:SecurityAccessRequired>			  
							    </tr>
							    <%}%>
							    </tbody>
							    </table>
							    <%} else { %>							   
                                    <span style="color:Grey;">No References currently on file.</span>
                                    <script>$("#section10").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
					</div>
	
					
	
	
	
	
					</div>
					</div>
</div>

<%if((!usr.checkPermission("PERSONNEL-ADMIN-VIEW") && session.getAttribute("JOB") != null) || usr.checkPermission("PERSONNEL-ADMIN-VIEW")) {%>

<!-- REFERENCE CHECKS --------------------------------------------------------------->

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section9">   
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
                                    <script>$("#section9").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
							    
							    
						
					</div>
					</div>
					</div>
</div>
 <%}%>
            
                                

<!-- REGIONAL REFERENCES --------------------------------------------------------------->
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section11">   
	               	<div class="panel-heading"><b>Regional Preferences</b></div>
      			 	<div class="panel-body"> 	
					
					<%if((regionPrefs != null) && (regionPrefs.length > 0)) { %>
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
                    		 <script>$("#section11").removeClass("panel-success").addClass("panel-danger");</script>
                    	<%} %>
	
					</div>
					</div>
</div>

                      


<!-- DOCUMENTATION --------------------------------------------------------------->

	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section12">
			<div class="panel-heading">
				<b>Documents</b>
			</div>
			<div class="panel-body">

				<div class="table-responsive">

					<% if ((docs != null) && (docs.size() > 0)) { %>
						<table class="table table-condensed table-striped" style="font-size: 11px; background-color: #FFFFFF; margin-top: 10px;">
							<thead>
	
								<tr style="border-top: 1px solid black;">
									<th width='50%'>TYPE</th>
									<th width='20%'>UPLOADED</th>
									<th width='15%'>STATUS</th>
									<th class="no-print" width='15%'>OPTIONS</th>
								</tr>
							</thead>
							<tbody>
								<%
									int i = 0;
									for (ApplicantDocumentBean doc : docs) {
										 //only select roles get docs other then transcripts.
										if ((!doc.getType().equal(DocumentType.UNIVERSITY_TRANSSCRIPT) && !usr.checkPermission("PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL")) || (doc.getType().equal(DocumentType.LETTER)
												|| (doc.getType().equal(DocumentType.COVID19_VAX)))
												) {
											continue;
										}
								%>
								<tr>
									<td><%=doc.getType().getDescription()%></td>
									<td><%=sdf_long.format(doc.getCreatedDate())%></td>
									<td><%=((doc.getType().equal(DocumentType.CODE_OF_CONDUCT) && doc.getCreatedDate().before(six_months)) ? "<span style='color:Red;'>** OUTDATED **</span>" : "<span style='color:Green;'>OK</span>")%></td>
									<td class="no-print"><a class='viewdoc btn btn-xs btn-info'
										href='viewApplicantDocument.html?id=<%=doc.getDocumentId()%>'
										target='_blank'>VIEW</a> &nbsp; <a
										class='viewdoc delete-doc btn btn-xs btn-danger'
										href='deleteApplicantDocument.html?id=<%=doc.getDocumentId()%>'>DEL</a>
									</td>
								</tr>
								<% } %>
							</tbody>
						</table>
					<% } else { %>
						<span style="color: Grey;">No Documentation currently on file.</span>
						<script>$("#section12").removeClass("panel-success").addClass("panel-danger");</script>
					<% } %>

				</div>
			</div>
		</div>
	</div>
	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19-STATUS">
	<!-- Letters --------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-info" id="section16">
			<div class="panel-heading">
				<b>COVID-19 Proof of Vaccination</b>
				<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19">
				<div style="float:right;">
				<a href="#" data-toggle="modal" data-target="#add_exemption_dialog" id="btn_show_add_exemtion_dialog" class="btn btn-xs btn-primary" onclick="return false;"><span class="glyphicon glyphicon-plus"></span> Add Exemption</a>
				<%if(profile.getStBean() == null){ %>
					<a id="btn_add_status" class="btn btn-xs btn-warning" onclick="addspecialstatus('<%=profile.getSIN() %>','${emailAddress}');"> Add Special Status</a>
				<%} %>
				</div>
				</esd:SecurityAccessRequired>
				
			</div>
			<div class="panel-body">
				<br/>
				<%if(profile.getStBean() == null){ %>
					<div id="divsdsstatus" class="alert alert-warning" role="alert" style="display:none;"></div>
				<%}else{ %>
					<div id="divsdsstatus" class="alert alert-warning" role="alert">
						<%=profile.getStBean().getStatusText() %>
					</div>
				<%} %>
				
				<div id="divsdserror" class="alert alert-danger" role="alert" style="display:none;"></div>
				<br />
				<div class="table-responsive">
				<table class="table table-condensed table-striped" style="font-size: 11px; background-color: #FFFFFF; margin-top: 10px;" id="tblcovid19">
							<thead>
								<tr style="border-top: 1px solid black;">
									<th width='20%'>TITLE</th>
									<th width='20%'>UPLOADED</th>
									<th width='45%'>STATUS</th>
									<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19">
									<th class="no-print" width='15%'>OPTIONS</th>
									</esd:SecurityAccessRequired>
								</tr>
							</thead>
							<tbody>	
						<% if ((docs != null) && (docs.size() > 0) && (docs.stream().filter(d -> d.getType().equal(DocumentType.COVID19_VAX)).count() > 0)) { %>
								
																
										
										
								<%	for (ApplicantDocumentBean doc : docs) {
											//only select roles get docs other then transcripts.
											if (!doc.getType().equal(DocumentType.COVID19_VAX)) {
												continue;
											} 
								%>
									<tr>
										<td width='20%'><%=doc.getType().toString()%></td>
										<td width='20%'><%=sdf_long.format(doc.getCreatedDate())%></td>
										<td width='45%'>
										<% if(doc.getType().equal(DocumentType.COVID19_VAX)){ %>
										<% if(doc.getClBean() == null){ %>
											
													<span style="color:Orange;"><i class="fas fa-ban"></i> Not Verified</span>
												
										<%}else{ %>
											<% if(doc.getClBean().getDateVerified() != null){ %>
												
														<% if(doc.getClBean().isExcemptionDoc()){ %>
														<div  id="divverify">
															<span style="color:Green;"><span id="spvdate">Exemption uploaded on <%=doc.getClBean().getDateVerifiedFormatted() %></span> by <span id="spvby"  style="text-transform: capitalize;"><%=doc.getClBean().getVerifiedBy() %></span></span>
														</div>
												<%}else{%>
														<div  id="divverify">
															<span style="color:Green;"><span id="spvdate">Verified on <%=doc.getClBean().getDateVerifiedFormatted() %></span> by <span id="spvby"  style="text-transform: capitalize;"><%=doc.getClBean().getVerifiedBy() %></span></span>
														</div>
												<%} %>	
													
											<%}else{ %>
												<% if(doc.getClBean().getRejectedDate() != null){ 
												%>
														<div  id="divrejected">
															<span style="color:Red;"><span id="rejdate"><i class="fas fa-times"></i> Rejected on <%=doc.getClBean().getRejectedDateFormatted() %></span> by <span id="rejby"  style="text-transform: capitalize;"><%=doc.getClBean().getRejectedBy() %>
															<br />Notes: <%=doc.getClBean().getRejectedNotes() %>
															</span></span>
														</div>
												<%}else{ %>
													<span style="color:Orange;" id="divnotver<%=doc.getDocumentId()%>"><i class="fas fa-ban"></i> Not Verified</span>
												     <div  id="divverify<%=doc.getDocumentId()%>" style="display:none;" >
															<span style="color:Green;"><i class="fas fa-check"></i> Verified on <span id="spvdate<%=doc.getDocumentId()%>"></span> by <span id="spvby<%=doc.getDocumentId()%>"></span></span>
														</div>
														<div  id="divrejected<%=doc.getDocumentId()%>" style="display:none;" >
															<span style="color:Red;"><i class="fas fa-times"></i> Rejected on 
															<span id="rejdate<%=doc.getDocumentId()%>"></span> by <span id="rejby<%=doc.getDocumentId()%>"></span>
															<br />Notes: <span id="rejnotes<%=doc.getDocumentId()%>"></span>
															</span>
														</div>
												<%} %>
											<%} %>
										<%} %>
									
									<%} else {%>
							
									<span style="color:Orange;"><i class="fas fa-ban"></i> Not Verified</span>
								
									
									<%}%>
										
										</td>
										<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19">
										<td class="no-print"  width='15%'>
											<a title='View documentation' class='viewdoc btn btn-sm btn-info' href='viewApplicantDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'><i class="far fa-file-alt"></i></a> &nbsp;
											<a title='Delete documentation'  class='viewdoc delete-doc btn btn-sm btn-danger' href='deleteApplicantDocument.html?id=<%=doc.getDocumentId()%>'><i class="far fa-trash-alt"></i></a> &nbsp;
											<% if(doc.getClBean() == null){ %>
												<a title='Verify and Approve this documentation'  class='viewdoc  btn btn-sm btn-success' onclick="verifycovid19('<%=doc.getDocumentId()%>',this);"><i class='far fa-check-circle'></i></a>
											<%}else{ %>
												<% if(doc.getClBean().getDateVerified() == null){ %>
													<% if(doc.getClBean().getRejectedDate() ==  null){ %>
														<span id='covidbut<%=doc.getDocumentId()%>'>
														<a title='Verify and Approve this documentation' class='viewdoc  btn btn-sm btn-success' onclick="verifycovid19('<%=doc.getDocumentId()%>',this);"><i class='far fa-check-circle'></i></a> &nbsp;
														<a title='Reject this documentation' class='viewdoc  btn btn-sm btn-danger' onclick="rejectcovid19list('<%=doc.getDocumentId()%>',this);"><i class='fas fa-ban'></i></a>
														</span>
													<%} %>
												<%} %>
											<%} %>
										</td>
										</esd:SecurityAccessRequired>
									</tr>
									
								<% } %>
								
								<% } else { %>
									<tr><td colspan='4'><span style="color: Grey;">No COVID19 doc(s) currently on file.</span>
									<script>
									$("#section16").removeClass("panel-success").addClass("panel-danger");
									
									</script></td></tr>							
								<% } %>
								</tbody>
						</table>
							
					
				</div>
			</div>
		</div>
	</div>
	</esd:SecurityAccessRequired>	
	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
	<!-- Letters --------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section19">
			<div class="panel-heading">
				<b>Letters</b>
				<div style="float:right;">
				<a href="#" data-toggle="modal" data-target="#add_letter_dialog" id="btn_show_add_letter_dialog" class="btn btn-xs btn-primary" onclick="return false;"><span class="glyphicon glyphicon-plus"></span> Add Letter</a>
				</div>
				</div>
			<div class="panel-body">
				<div class="table-responsive">
				
						<% if ((docs != null) && (docs.size() > 0) && (docs.stream().filter(d -> d.getType().equal(DocumentType.LETTER)).count() > 0)) { %>
								
								<table class="table table-condensed table-striped" style="font-size: 11px; background-color: #FFFFFF; margin-top: 10px;" id="tblletters">
							<thead>
								<tr style="border-top: 1px solid black;">
									<th width='60%'>Title</th>
									<th width='25%'>UPLOADED</th>
									<th class="no-print" width='15%'>OPTIONS</th>
								</tr>
							</thead>
							<tbody>									
										
										
								<%	for (ApplicantDocumentBean doc : docs) {
											//only select roles get docs other then transcripts.
											if (!doc.getType().equal(DocumentType.LETTER)) {
												continue;
											} 
								%>
									<tr>
										<td><%=doc.getDescription()%></td>
										<td><%=sdf_long.format(doc.getCreatedDate())%></td>
										<td class="no-print">
											<a class='viewdoc btn btn-xs btn-info' href='viewApplicantDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a> &nbsp; <a class='viewdoc delete-doc btn btn-xs btn-danger' href='deleteApplicantDocument.html?id=<%=doc.getDocumentId()%>'>DELETE</a>
										</td>
									</tr>
								<% } %>
								</tbody>
						</table>
								<% } else { %>
									<span style="color: Grey;">No Letter(s) currently on file.</span>
									<script>$("#section19").removeClass("panel-success").addClass("panel-danger");</script>								
								<% } %>
							
					
				</div>
			</div>
		</div>
	</div>
	</esd:SecurityAccessRequired>

<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL">
	<!-- CRIMINAL OFFENCE DECLARATIONS --------------------------------------------------------------->
		<div class="panel-group" style="padding-top: 5px;">
			<div class="panel panel-success" id="section13">
				<div class="panel-heading">
					<b>Criminal Offence Declarations</b>
				</div>
				<div class="panel-body">

					<div class="table-responsive">
						<%
							if ((cods != null) && (cods.size() > 0)) {
						%>

						<table class="table table-condensed table-striped"
							style="font-size: 11px; background-color: #FFFFFF; margin-top: 10px;">
							<thead>
								<tr style="border-top: 1px solid black;">
									<th width='25%'>DECLARATION DATE</th>
									<th width='60%'>OFFENCES PRESENT</th>
									<th width='15%' class="no-print">OPTIONS</th>
								</tr>
							</thead>

							<tbody>

								<%
									int i = 0;
								for (ApplicantCriminalOffenceDeclarationBean cod : cods) {
								%>
								<tr>
									<td><%=sdf_long.format(cod.getDeclarationDate())%></td>
									<td><%=((cod.getOffences() != null) ? cod.getOffences().size() : 0)%></td>
									<td class="no-print"><a
										class='viewdoc btn btn-xs btn-info'
										href='viewApplicantCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>'>VIEW</a> <a
										class='viewdoc delete-cod btn btn-xs btn-danger'
										href='deleteApplicantCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>'>DEL</a>
									</td>

								</tr>
								<%
									}
								%>
							</tbody>
						</table>

						<%
							} else {
						%>
						<span style="color: Grey;">None CODs currently on file.</span>
						<script>$("#section13").removeClass("panel-success").addClass("panel-danger");</script>
						<%
							}
						%>
					</div>

				</div>
			</div>
		</div>
	</esd:SecurityAccessRequired>              
 
 <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
 
 	<!-- INTERVIEW SUMMARIES ----------------------------------------------------------------------------------------->
	<% if (interviewSummaries.size() > 0) { %>
		<div class="panel-group" style="padding-top: 5px;">
			<div class="panel panel-success" id="section12">
				<div class="panel-heading">
					<b>Recent Interview Summaries</b>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-condensed"
							style="font-size: 11px;">
							<thead>
								<tr>
									<th width="20%">COMP #</th>
									<th width="50%">TITLE</th>
									<th width="20%">DATE</th>
									<th width="10%">OPTIONS</th>
								</tr>
							</thead>
							<tbody>
								<% for (InterviewSummaryBean isb : interviewSummaries) { %>
									<tr>
										<td><%= isb.getCompetition().getCompetitionNumber() %></td>
										<td><%= isb.getCompetition().getPositionTitle( )%></td>
										<td><%= sdf_medium.format(isb.getCreated()) %></td>
										<td><a class="btn btn-xs btn-primary"
											href="/MemberServices/Personnel/applicantViewCompetitionInterviewSummary.html?id=<%= isb.getInterviewSummaryId() %>">VIEW</a></td>
									</tr>
								<% } %>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	<% } %>

	<!-- HIGHLY RECOMMENDED POOLS --------------------------------------------------------------->
	<% if(highlyRecommendedPools.size() > 0) { %>
		<div class="panel-group no-print" style="padding-top: 5px;">
			<div class="panel panel-success" id="section15">
				<div class="panel-heading">
					<b>Pool Competitions with HIGHLY RECOMMENDED status</b>
				</div>
				<div class="panel-body">
					<table class="table table-condensed table-striped" id="hcpools" style="font-size: 11px;">
						<thead>
							<tr>
								<th>COMPETITION NUMBER</th>
								<th>TITLE</th>
								<th>LOCATION</th>
								<th class="no-print">VIEW</th>
							</tr>
						</thead>
						<tbody>
							<% for(JobOpportunityBean j : highlyRecommendedPools.values()) { %>
								<tr>
									<td><%= j.getCompetitionNumber() %></td>
									<td><%= j.getPositionTitle() %></td>
									<td><%= j.getJobLocation() %></td>
									<td class="no-print">
									<a class='btn btn-xs btn-info' href='view_job_post.jsp?comp_num=<%= j.getCompetitionNumber() %>'>JOB</a>
									</td>
								</tr>
							<% } %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	<% } %> 

	<!-- POSITIONS APPLIED FOR --------------------------------------------------------------->
	<div class="panel-group no-print" style="padding-top: 5px;">
		<div class="panel panel-success" id="section14">
			<div class="panel-heading">
				<b>Positions Applied</b>
			</div>
			<div class="panel-body">
				<c:choose>
					<c:when test='${fn:length(jobs) gt 0}'>
						<table class="table table-condensed table-striped" id="jobsapp"
							style="font-size: 11px;">
							<thead>
								<tr>
									<th>COMPETITION NUMBER</th>
									<th>TITLE</th>
									<th>DATE APPLIED</th>
									<th>LOCATION</th>
									<th>SHORTLISTED</th>
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
										<td>
											<c:choose>
												<c:when test="${g.shortlisted }">YES</c:when>
												<c:otherwise>NO</c:otherwise>
											</c:choose>
										</td>
										<td class="no-print"><a class='btn btn-xs btn-info'
											href='view_job_post.jsp?comp_num=${g.compNum}'>VIEW</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<span style="color: Grey;">No Applications on file.</span>
						<script>$("#section14").removeClass("panel-success").addClass("panel-danger");</script>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	
	</esd:SecurityAccessRequired>
                             
                            
<!-- OPTIONS --------------------------------------------------------------->    
<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">

		<div align="center" class="no-print" style="padding-bottom: 10px;">
		<a href='#' title='Print this page (pre-formatted)'
					class='btn btn-xs btn-info'
					onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span
					class="glyphicon glyphicon-print"></span> Print Profile</a>
			<esd:SecurityAccessRequired
				permissions="PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE">
				<%if(!(profile.isDeleted())) { %>
					<a id='btn_delete_applicant' class="btn btn-xs btn-danger"
						onclick="openDeleteApplicant('<%=profile.getUID()%>','D')"><span
						class="glyphicon glyphicon-remove"></span> Delete Applicant</a>
				<%}else{ %>
					<a id='btn_delete_applicant' class="btn btn-xs btn-danger"
						onclick="openDeleteApplicant('<%=profile.getUID()%>','R')"><span
						class="glyphicon glyphicon-remove"></span> Restore Applicant</a>
				<%} %>
			</esd:SecurityAccessRequired>
			<%
				if (session.getAttribute("JOB") != null) {
					JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
					InterviewGuideBean guide = InterviewGuideManager.getInterviewGuideBean(job);;
	
					TeacherRecommendationBean[]  rec = RecommendationManager.getTeacherRecommendationBean(job.getCompetitionNumber());
					
					boolean recInProgress = false;
					if((rec != null) && (rec.length > 0)) {
						recInProgress = (!rec[0].isRejected() && !rec[0].isOfferRejected() && !rec[0].isProcessed() && !rec[0].isOfferIgnored());
						
						if(rec[0].isProcessed() && job.isReopened() && job.getReopenedDate().after(rec[0].getProcessedDate())) {
							recInProgress = false;
						}
					}
					
					boolean isShortlisted = ApplicantProfileManager.getApplicantShortlistMap(job).containsKey(profile.getUID());
			%>
					<a href='admin_view_job_applicants.jsp' class='btn btn-xs btn-info'><span class="glyphicon glyphicon-search"></span> View Applicants</a>
					<% if (!job.isShortlistComplete() && job.isClosed() && !recInProgress) { %>
						<% if (guide != null) { %>
							<% if (validReference) { %>
								<% if(!isShortlisted) { %>
									<a class="btn btn-xs btn-primary" id="btn_add_shortlist"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
								<% } else { %>
									<br/>
									<div>
										<span class='text-danger'>Already Shortlisted for competition <%= job.getCompetitionNumber() %></span>
									</div>
									<br />
								<% } %>
							<% } else { %>
								<a href='#'
									onclick="alert('Applicant has no current Reference on file. Reference needs to be completed before applicant can be shortlisted.'); return false;"
									class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
							<% } %>
						<% } else { %>
							<a href='#'
								onclick="alert('Interview guide must be set for competition <%=job.getCompetitionNumber()%> before shortlist can be created.'); return false;"
								class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
						<% } %>
					<% } %>
			<% } else if (session.getAttribute("SUBLIST") != null) {
					ApplicantSubListInfoBean li = (ApplicantSubListInfoBean) sublists.get(new Integer(((SubListBean) session.getAttribute("SUBLIST")).getId())); %>
					<a href='admin_view_sublist_applicants.jsp' class='btn btn-xs btn-info'><span class="glyphicon glyphicon-search"></span> View Applicants</a>
					<% if ((li != null) && li.isNewApplicant()) { %>
						<br />
						<a href='shortListApplicant.html?sin=<%=profile.getSIN()%>' class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a> 
						<a href='applicantNotApproved.html?sin=<%=profile.getSIN()%>' class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-remove"></span> Not Approved</a>
					<% } %> 
			<% } %>
			<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
			<br />
			<br />
		</div>

	</esd:SecurityAccessRequired>                         

   <form id="frmverify">
   	<input id="appid" name ="appid" type="hidden" value="<%=profile.getSIN()%>">
   </form>                        

<!-- SUBLIST SELECT --------------------------------------------------------------->
<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
		<!-- Modal Revised for Bootstrap -->
		<div id="add_applicant_dialog" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add to Sub List:</h4>
					</div>
					<div class="modal-body">

						<div class="form-group">
							<select name='sublist_id' id='sublist_id' class="form-control"
								multiple='multiple'
								style="height: 150px; text-transform: Capitalize;">
								<c:forEach items="${SUBLISTBEANS_BY_REGION}" var="entry">
									<optgroup title="${entry.key.name}" label="${entry.key.name}">
										<c:forEach items="${entry.value}" var="sublist">
											<option value='${sublist.id}'>${sublist.title}</option>
										</c:forEach>
									</optgroup>
								</c:forEach>
							</select>
						</div>
						<div class="alert alert-info" id="response_msg">Select a Sub
							List from above to add to. (Select multiple by holding the CTRL
							key while clicking)</div>

					</div>
					<div class="modal-footer">
						<button type="button" id='btn_add_applicant'
							class="btn btn-success btn-xs" style="float: left;">Add
							to Sublist</button>
						<button type="button" class="btn btn-danger btn-xs"
							data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
		
		<!-- Modal for shortlisting -->
		<div id="add_shortlist_dialog" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Applicant to Shortlist</h4>
					</div>
					<div class="modal-body">

						<div class="form-group">
							<h4 class="modal-title">Reason For Short Listing Applicant:</h4>
							<textarea rows="10" cols="60" id="shortlistreason"></textarea>
						</div>
						<div class="alert alert-info" id="response_msg_sl"
							style="display: none;">Please enter reason for short
							listing applicant</div>
					</div>
					<div class="modal-footer">
						<button type="button" id='btn_add_shortlist_ok'
							class="btn btn-success btn-xs" style="float: left;">Add
							to Shortlist</button>
						<button type="button" class="btn btn-danger btn-xs"
							data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
		
		<!-- Modal for confirming applicant verification -->
		<div id="confirm_verify_dialog" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Applicant Verification</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<h4 class="modal-title">Are you sure you want to verify
								applicant?</h4>
						</div>
						<div class="modal-footer">
							<button type="button" id='btn_confirm_verify'
								class="btn btn-success btn-xs" style="float: left;">Verify</button>
							<button type="button" class="btn btn-danger btn-xs"
								data-dismiss="modal">Close</button>
						</div>
					</div>

				</div>
			</div>
		</div>
		
		<!-- Modal for adding new letter -->
		<div id="add_letter_dialog" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Applicant Letter</h4>
						<div class="alert alert-danger" style="display: none;"
							id="dalertadd" align="center">
							<span id="dmessageadd"></span>
						</div>
						<div class="alert alert-success" style="display: none;"
							id="dalertadds" align="center">
							<span id="dmessageadds"></span>
						</div>
					</div>
					<div class="modal-body">
						<input type='hidden' id='addedbyid' name='addedbyid' value='<%= usr.getPersonnel().getPersonnelID() %>' />
						<p class="text-warning" id="title3">Letter Title:</p>
						<p>
							<input type="text" id="ltitle" name="ltitle" size="35">
						</p>
						<p class="text-warning" id="title3">Letter:</p>
						<p>
							<input type="file" id="ldocument" name="ldocument"
								accept="application/pdf">(PDF file format only)
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" id='btn_confirm_letter'
							class="btn btn-success btn-xs" style="float: left;">Add
							Letter</button>
						<button type="button" class="btn btn-danger btn-xs"
							data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
	</esd:SecurityAccessRequired>
	<esd:SecurityAccessRequired
				permissions="PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE">
			<!-- Modal for deleting app -->
		<div id="delete_app_dialog" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"><span id="delemptext"></span></h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<h4 class="modal-title"><span id="spandelete"></span></h4>
						</div>
						<div class="modal-footer">
							<button type="button" id='btn_delete_app_ok'
								class="btn btn-success btn-xs" style="float: left;"></button>
							<button type="button" class="btn btn-danger btn-xs"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		</esd:SecurityAccessRequired>
		<div class="modal fade" id="modalreject">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">Reject COVID19 Vaccination Document</h4>
        <input type='hidden' id='hidbutton'>
      </div>
      <div class="modal-body">
        <p>
        <h4 class="modal-title"><span id="spandesc">Reason For Rejection</span></h4>
        </p>
        <p>
		<h4 class="modal-title"><span id="spandesc">Please remember these notes will be sent back to the Employee\Applicant and saved in the system</span></h4>
        </p>
        <p>
        <textarea rows="7" cols="75" id='txtreason' name='txtreason'></textarea>
        </p>
        <p>
        <div class="alert alert-danger" role="alert" id="errmsg" style="display:none;">
  			Please enter reason for rejection
		</div>
        </p>
      </div>
      <div class="modal-footer">
        <button type="button" id='btn_reject_doc_ok' class="btn btn-success btn-xs" style="float: left;" onclick="sumbitRejectDocumentApp();">Reject</button>
		<button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="modal fade" id="add_exemption_dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">Add COVID19 Exemption Document</h4>
        <input type='hidden' id='hidbutton'>
      </div>
      <div class="modal-body">
        Please select and upload a valid exemption document for this employee:
        <p>
			<input type="file" id="exdocument" class="form-control" name="exdocument" accept="application/pdf">(PDF file format only)
		</p>
        <p>
        <div class="alert alert-danger" role="alert" id="errmsgex" style="display:none;">	Please select a valid PDF  file.</div>
        </p>
      </div>
      <div class="modal-footer">
        <button type="button" id='btn_add_exemption_ok' class="btn btn-success btn-xs" style="float: left;" onclick="addCovid19Exemption('20');">ADD FILE</button>
		<button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">CLOSE</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>