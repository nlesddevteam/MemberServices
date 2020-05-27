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
		new AlertBean(new com.awsd.security.SecurityException("Applicant Profile Illegal Access Attempted By " + usr.getPersonnel().getFullNameReverse()));
		
		throw new com.awsd.security.SecurityException("Illegal Access Attempted By " + usr.getPersonnel().getFullNameReverse());
	}

	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("APPLICANT");
  SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
  SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
  
  ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
  ApplicantEducationBean[] edu = ApplicantEducationManager.getApplicantEducationBeans(profile.getSIN());
  ApplicantEducationOtherBean edu_oth = ApplicantEducationOtherManager.getApplicantEducationOtherBean(profile.getSIN());
  boolean validEduOther = false;
  if(!(edu_oth == null)){
	  if(edu_oth.getFormatedTeachingCertificateIssuedDate() != null && edu_oth.getTeachingCertificateLevel() != null){
		  validEduOther = true;
	  }
  }
  
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
  
  if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW-PWD"))
    session.setAttribute("APPLICANT", profile); 
   
  HashMap<Integer, ApplicantSubListInfoBean> sublists = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
  
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
  ApplicantNLESDPermanentExperienceBean[] per = ApplicantNLESDPermExpManager.getApplicantNLESDPermanentExperienceBeans(profile.getSIN());
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
  	
  	$('.confirm_approve_link').click(function(){
  		if(confirm('Are you sure this applicant is approved?'))
  			return true;
  		else
  			return false;
  	});
  	
  	$('.confirm_not_approve_link').click(function(){
  		if(confirm('Are you sure this applicant is not approved?'))
  			return true;
  		else
  			return false;
  	});
  	
  	$('.confirm_reset_link').click(function(){
  		if(confirm('Are you sure you want to reset?'))
  			return true;
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
							    	<td class="tableTitle">Permanent Contract School:</td>
							    	<td>${permContractSchool}</td>
							    	<td class="tableTitle">Permanent Contract Position:</td>
							    	<td>${permContractPosition}</td>
							    </tr>
							    <tr>
							    	<td class="tableTitle">Replacement Contract School:</td>
							    	<td>${repContractSchool}</td>
							    	<td class="tableTitle">Replacement Contract End Date:</td>
							    	<td>${repContractEndDate}</td>
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
           			<table class="table table-condensed" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
				    <thead>
				      <tr style="border-top:1px solid black;">
				        <th width='10%'>YEAR</th>
				        <th width='20%'>REGION (ZONE)</th>
				        <th width='40%'>LIST</th>								        
				        <th width='10%'>APPLIED</th>								        
				        <th width='20%'>STATUS</th>								      
				      </tr>
				    </thead>
				    
				    <tbody>	
           				
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
		             		(Northen)
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
			            	 <a class='confirm_approve_link btn btn-xs btn-success no-print' href='shortListApplicant.html?sin=${UUID}&list_id=${subListApp.subList.id}'><span class="glyphicon glyphicon-ok"></span> Approve</a> &nbsp;
			            	 <a class='confirm_not_approve_link btn btn-xs btn-danger no-print' href='applicantNotApproved.html?sin=${UUID}&list_id=${subListApp.subList.id}'><span class="glyphicon glyphicon-remove"></span> Do Not Approve</a>
			            	 <%
			                 } else { 						
    							
   					%>
   					 <c:choose>
   					 	<c:when test="${subListApp.status eq 'Y'}"><span style="color:Green;">APPROVED</span></c:when>
   						<c:when test="${subListApp.status eq 'N'}"><span style="color:#FF0000;font-size:12px;">NOT APPROVED</span></c:when>
   					    <c:when test="${subListApp.status eq 'W'}"><span style="color:#FF0000;">IN A POSITION/REMOVED</span></c:when>
   					    <c:otherwise><span style="color:#0000FF;">NEW!</span></c:otherwise>
   					 </c:choose>
   					<span style="float:right;text-align:right;" class="no-print"><a class='confirm_reset_link btn btn-xs btn-warning' href='resetApplicantApproval.html?uid=${userID}&list_id=${subListApp.subList.id}'><span class="glyphicon glyphicon-refresh"></span> RESET</a></span>
   							
   					<%	}	               
			    
			    	}else{
			    		
			    		%>
			    		<c:choose>
   					 	<c:when test="${subListApp.status eq 'Y'}"><span style="color:Green;">APPROVED</span></c:when>
   						<c:when test="${subListApp.status eq 'N'}"><span style="color:#FF0000;">NOT APPROVED</span></c:when>
   					   	<c:when test="${subListApp.status eq 'W'}"><span style="color:#FF0000;">IN A POSITION/REMOVED</span></c:when>
   					    <c:otherwise><span style="color:#0000FF;">NEW!</span></c:otherwise>
   					 </c:choose>
			    		
			    	<% } %>
			    	
			    	</td></tr>
			    	<% } %>
            		
           			</tbody></table>
           			
           			
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
	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section4">   
	               	<div class="panel-heading"><b>NLESD Permanent Experience</b> (Total Months: <%=((esd_exp != null)?Integer.toString(esd_exp.getPermanentLTime()):"UNKNOWN")%>)</div>
      			 	<div class="panel-body"> 	
					
					<div class="table-responsive"> 
      			 	       
      			 	       <% if((per != null) && (per.length > 0))
                                  {
                                  %>
							    
							    <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>									    
									      <tr style="border-top:1px solid black;">
									        <th width='20%'>FROM</th>
									        <th width='20%'>TO</th>
									        <th width='20%'>SCHOOL</th>								        
									        <th width='40%'>GRADES AND/OR SUBJECTS TAUGHT</th>		
									      </tr>
									    </thead>
							    
							    <tbody>				    
							    
							    <%						    	
                                    for(int i=0; i < per.length; i ++)
                                    { %>							    
							    <tr>
							    <td><%=sdf.format(per[i].getFrom())%></td>
							    <td><%=sdf.format(per[i].getTo())%></td>
							    <td><%=SchoolDB.getSchool(per[i].getSchoolId()).getSchoolName()%></td>
							    <td><%=per[i].getGradesSubjects()%></td>							    
							    </tr>							    
							    <%}%>							    
							    </tbody>
							    </table>							    
							    <%} else {%>
                                   <span style="color:Grey;">No NLESD Permanent Experience currently on file.</span>
                                   <script>$("#section4").removeClass("panel-success").addClass("panel-danger");</script>
                                <% } %>
							    
					</div>		    
					</div>
					</div>
</div>

                             
                           
                                 
<!-- NLESD REPLACEMENT EXPERIENCE --------------------------------------------------------------->    


<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section5">   
	               	<div class="panel-heading"><b>NLESD Replacement Contract Experience</b>  (Total Months: <%=((esd_exp != null)?Integer.toString(esd_exp.getReplacementTime()):"UNKNOWN")%>)</div>
      			 	<div class="panel-body">
      			 	<div class="table-responsive"> 
								<%
							    if((rpl != null) && (rpl.length > 0))
                                  { %>
                                <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
									    <thead>									    
									      <tr style="border-top:1px solid black;">
									        <th width='20%'>FROM</th>
									        <th width='20%'>TO</th>
									        <th width='20%'>SCHOOL</th>								        
									        <th width='40%'>GRADES AND/OR SUBJECTS TAUGHT</th>		
									      </tr>
									    </thead>							    
							    <tbody>
							     <% for(int i=0; i < rpl.length; i ++) {%>
							    <tr>
							    <td><%=sdf.format(rpl[i].getFrom()) %></td>
							    <td><%=sdf.format(rpl[i].getTo()) %></td>
							    <td><%=SchoolDB.getSchool(rpl[i].getSchoolId()).getSchoolName() %></td>
							    <td><%=rpl[i].getGradesSubjects() %></td>
							    </tr>
							    <% } %>
							    </tbody>
							    </table>							    
							    <%} else { %>
                                   <span style="color:Grey;">No Replacement Contract Experience currently on file.</span>
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
                                    <script>$("#section10").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
					</div>
	
					
	
	
	
	
					</div>
					</div>
</div>


            
                                

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

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section12">   
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
                                  	if(!doc.getType().equal(DocumentType.UNIVERSITY_TRANSSCRIPT) 
                                  			&& !usr.checkPermission("PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL"))
                                  		continue;
                               %>
							    <tr>							   
							    <td><%=doc.getType().getDescription()%></td>	
							    <td><%=sdf_long.format(doc.getCreatedDate())%></td>						    
							    <td><%=((doc.getType().equal(DocumentType.CODE_OF_CONDUCT) && doc.getCreatedDate().before(six_months))? "<span style='color:Red;'>** OUTDATED **</span>" : "<span style='color:Green;'>OK</span>")%></td>
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
                                    <script>$("#section12").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
							    
							    
						
					</div>
					</div>
					</div>
</div>

                             

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
              

<% if(highlyRecommendedPools.size() > 0) { %>
	<!-- HIGHLY RECOMMENDED POOLS --------------------------------------------------------------->
	<div class="panel-group no-print" style="padding-top: 5px;">
		<div class="panel panel-success" id="section14">
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
							<th class="no-print">OPTIONS</th>
						</tr>
					</thead>
					<tbody>
						<% for(JobOpportunityBean j : highlyRecommendedPools.values()) { %>
							<tr>
								<td><%= j.getCompetitionNumber() %></td>
								<td><%= j.getPositionTitle() %></td>
								<td><%= j.getJobLocation() %></td>
								<td class="no-print"><a class='btn btn-xs btn-info' href='view_job_post.jsp?comp_num=<%= j.getCompetitionNumber() %>'>View</a></td>
							</tr>
						<% } %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<% } %>                

<!-- POSITIONS APPLIED FOR --------------------------------------------------------------->
	
<div class="panel-group no-print" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section14">   
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
													<script>$("#section14").removeClass("panel-success").addClass("panel-danger");</script>
												</c:otherwise>
											</c:choose>
											
					</div>
					</div>
</div>

                             
                            
<!-- OPTIONS --------------------------------------------------------------->    
<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">                        
	
					<div align="center" class="no-print" style="padding-bottom:10px;">
					
                                
                                	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE">
                                  	<a href='#' title='Print this page (pre-formatted)' class='btn btn-xs btn-info' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span> Print Profile</a>
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
                                   	<% if(!job.isShortlistComplete() && job.isClosed()) { %>
                                   			<%if(guide != null){ %>
                                   				<%if(validReference){ %>
                                   					<a  class="btn btn-xs btn-primary" id="btn_add_shortlist"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                   				<%}else{ %>
                                   					<a href='#' onclick="alert('Applicant has no current Reference on file. Reference needs to be completed before applicant can be shortlisted.'); return false;" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                   				<%} %>
                                   			<%}else{ %>
                                   				<a href='#' onclick="alert('Interview guide must be set for competition <%=job.getCompetitionNumber() %> before shortlist can be created.'); return false;" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                   			<%} %>
                                   			
                                    <% } %>
                                  <%}else if(session.getAttribute("SUBLIST") != null){
                                  		ApplicantSubListInfoBean li = (ApplicantSubListInfoBean)sublists.get(new Integer(((SubListBean)session.getAttribute("SUBLIST")).getId())); %>
                                      <a href='admin_view_sublist_applicants.jsp' class='btn btn-xs btn-info'><span class="glyphicon glyphicon-search"></span> View Applicants</a>
                                      <%
                                      if((li != null) && li.isNewApplicant()){ %>
                                      <br/><a href='shortListApplicant.html?sin=<%=profile.getSIN()%>' class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                      <a href='applicantNotApproved.html?sin=<%=profile.getSIN()%>' class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-remove"></span> Not Approved</a>
                                     
                                     <% }} %>
                                     
                                   <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>  
                       </div>

  </esd:SecurityAccessRequired>                         
   <form id="frmverify">
   	<input id="appid" name ="appid" type="hidden" value="<%=profile.getSIN()%>">
   </form>                        
<!-- SUBLIST SELECT --------------------------------------------------------------->

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
				  <select name='sublist_id' id='sublist_id' class="form-control" multiple='multiple' style="height:150px;text-transform:Capitalize;">
						<c:forEach items="${SUBLISTBEANS_BY_REGION}" var="entry">
							 <optgroup title="${entry.key.name}" label="${entry.key.name}">
							 		<c:forEach items="${entry.value}" var="sublist">
										<option value='${sublist.id}'>${sublist.title}</option> 			
							 		</c:forEach>
							 </optgroup>
						</c:forEach>
					</select> 
				   </div>
				 <div class="alert alert-info" id="response_msg">Select a Sub List from above to add to. (Selet multiple by holding the CTRL key while clicking)</div>
				  
				  
				
      </div>
      <div class="modal-footer">
        <button type="button" id='btn_add_applicant' class="btn btn-success btn-xs" style="float:left;">Add to Sublist</button>  <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Close</button>
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
				 <div class="alert alert-info" id="response_msg_sl" style="display:none;">Please enter reason for short listing applicant</div>
				  
				  
				
      </div>
      <div class="modal-footer">
      	<button type="button" id='btn_add_shortlist_ok' class="btn btn-success btn-xs" style="float:left;">Add to Shortlist</button>  <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Close</button>
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
		<h4 class="modal-title">Are you sure you want to verify applicant?</h4>	
	 </div>
      <div class="modal-footer">
      	<button type="button" id='btn_confirm_verify' class="btn btn-success btn-xs" style="float:left;">Verify</button>  <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
</div>
</body>
</html>
