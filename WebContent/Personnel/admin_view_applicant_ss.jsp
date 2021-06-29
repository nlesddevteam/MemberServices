<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                   java.util.concurrent.TimeUnit,
                  java.text.SimpleDateFormat, 
				  java.util.Date,  
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

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-VIEW-SHORTLIST" />

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
    //if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW-PWD"))
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
<c:set var="SDSBEAN" value="<%=empbean == null ? null : empbean.getSenioritySupport() ==  null ? null: empbean.getSenioritySupport()%>"/> 

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
      			 				<input type='hidden' id="appname" value="${fullName}">
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
					    				<span style="color:Red;">
					    				<span class="glyphicon glyphicon-remove"></span> 
					    				This Profile has not been verified.</span>
					    				<esd:SecurityAccessRequired roles="ADMINISTRATOR,MANAGER OF HR,MANAGER OF HR - PERSONNEL,SEO - PERSONNEL,SENIOR EDUCATION OFFICIER">
							   			<button type="button" class="btn btn-success btn-xs" id="butVerify" style="margin-left:10px;"><span class="glyphicon glyphicon-thumbs-up"></span> Verify</button> 
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
								    <c:choose>
							    	<c:when test="${SDSBEAN eq null}">
							    		<td class="tableTitleR">Years of Service:</td>
							    		<td class="tableResultR">N/A</td>
							    	</c:when>
							    	<c:otherwise>
						    		<c:choose>
						    			<c:when test="${ SDSBEAN.unionCode eq '01' || SDSBEAN.unionCode eq '03' || SDSBEAN.unionCode eq '04' || SDSBEAN.unionCode eq '10' || SDSBEAN.unionCode eq '11' }">
						    				<td class="tableTitleR">Seniority Date:</td>
						    				<c:choose>
					    						<c:when test="${SDSBEAN ne null}">
					    							<c:choose>
					    								<c:when test="${ SDSBEAN.seniorityDate1 ne null}">
					    									<td class="tableResultR">${ SDSBEAN.seniorityDate1Formatted}</td>
					    								</c:when>
					    								<c:when test="${SDSBEAN.seniorityDate2 ne null}">
					    									<td class="tableResultR">${ SDSBEAN.seniorityDate2Formatted}</td>
					    								</c:when>
					    								<c:otherwise>
					    									<td class="tableResultR">N/A</td>
					    								</c:otherwise>
					    							</c:choose>
					    						</c:when>
						    					<c:otherwise>N/A </c:otherwise>
						    				</c:choose>
						    			</c:when>
						    			<c:when test="${ SDSBEAN.unionCode eq '02' || SDSBEAN.unionCode eq '05' || SDSBEAN.unionCode eq '06' || SDSBEAN.unionCode eq '07' || SDSBEAN.unionCode eq '08' || SDSBEAN.unionCode eq '09' }">
						    					<td class="tableTitleR">Days Worked:</td>
						    					<c:choose>
						    						<c:when test="${seniorityTotal ne ''}"><td class="tableResultR">${seniorityTotal}</td></c:when>
						    						<c:otherwise>N/A</c:otherwise>
						    					</c:choose>
						    			</c:when>
						    			<c:otherwise>
						    				<td class="tableTitleR">Years of Service:</td>
						    				<td class="tableResultR">N/A</td>
						    			</c:otherwise>
						    		</c:choose>
						    		</c:otherwise>
						    		</c:choose>
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
							 <a title='Admin Delete Reference (Will place a completed reference in Other References)' class='btn-xs btn btn-danger' href='#' onclick="deleteref(this,'<%=refs[i].getId()%>')">DEL</a>
							</td>		  
					</esd:SecurityAccessRequired>		 
							 </td>
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
 
                                  	
<!-- 9. DOCUMENTATION --------------------------------------------------------------->
 <% if(usr.checkPermission("PERSONNEL-ADMIN-VIEW-DOCS") || usr.checkPermission("PERSONNEL-OTHER-MANAGER-VIEW")) { %>
	 
 
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
	                                    	}else{
	                                    		if(doc.getTypeSS().equal(DocumentTypeSS.LETTER)) {
	                                    			continue;
	                                    		}
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
  
<!-- Letters --------------------------------------------------------------->
 <% if(usr.checkPermission("PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL")) { %>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section12">   
	               	<div class="panel-heading">
	              <b>Letters</b>
	               	<div style="float:right;">
	               		<a href="#" data-toggle="modal" data-target="#add_letter_dialog" id="btn_show_add_letter_dialog" class="btn btn-xs btn-primary" onclick="return false;"><span class="glyphicon glyphicon-plus"></span> Add Letter</a>
	              </div>
	              	</div>
      			 	<div class="panel-body"> 							
					<div class="table-responsive">     			 	       
      			 	       
							  <% if((docs != null) && (docs.size() > 0))  { %>
                              
                              <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;" id="tblletters">
									    <thead>									    
									      <tr style="border-top:1px solid black;">
									      	<th width='60%'>Title</th>
									        <th width='25%'>UPLOADED</th>
									        <th class="no-print" width='15%'>OPTIONS</th>		
									      </tr>
									    </thead>							    
							    <tbody>
                              
                              
                              <%
                                	int i=0;
                                  for(ApplicantDocumentBean doc : docs)
                                  {
                                  	//only select roles get docs other then transcripts.
                                  	if(!doc.getTypeSS().equal(DocumentTypeSS.LETTER)){
                                  		continue;
                                  	}
                               %>
							    <tr>							   
							    <td><%=doc.getDescription()%></td>	
							    <td><%=sdf_long.format(doc.getCreatedDate())%></td>						    
							    <td class="no-print">
							    <a class='viewdoc btn btn-xs btn-info' href='viewApplicantDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a> &nbsp;
							    <a class='viewdoc delete-doc btn btn-xs btn-danger' href='deleteApplicantDocument.html?id=<%=doc.getDocumentId()%>'>DELETE</a>
							    </td>
							    </tr>
							    <%  }%>
							    </tbody>
							    </table>
							    <%}	else { %>							   
                                    <span style="color:Grey;">No Letter(s) currently on file.</span>
                                    <script>$("#section12").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
							    
							    
						
					</div>
					</div>
					</div>                              
</div>                              
  <%} %>
<!-- CRIMINAL OFFENCE DECLARATIONS --------------------------------------------------------------->
<% if(usr.checkPermission("PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL") || usr.checkPermission("PERSONNEL-OTHER-MANAGER-VIEW")) { %>

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
							    <a class='viewdoc btn btn-xs btn-info' href='viewApplicantCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>'>VIEW</a>
							    <% if(usr.checkPermission("PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL")){ %>
							    	<a class='viewdoc delete-cod btn btn-xs btn-danger' href='deleteApplicantCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>'>DELETE</a>
							    <%} %>
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
<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-OTHER-MANAGER-VIEW">                        
	
					<div align="center" class="no-print" style="padding-bottom:10px;">
                                    <a href='#' title='Print this page (pre-formatted)' class='btn btn-xs btn-info' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span> Print Profile</a>
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
                    			<%if(session.getAttribute("JOB") != null){
                                  		JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
                                  		boolean isShortlisted = ApplicantProfileManager.getApplicantShortlistMap(job).containsKey(profile.getUID());
                                  		InterviewGuideBean guide = null;
                                  		
                                  		if(job != null) {
                                  			guide = InterviewGuideManager.getInterviewGuideBean(job);
                                  		}
                                  %>
                                  <a href='admin_view_job_applicants.jsp' class='btn btn-xs btn-info'><span class="glyphicon glyphicon-search"></span> View Applicants</a>
                                    	<% if(!job.isShortlistComplete()) { %>
                                    		<%if(guide != null){ %>
                                    			<% if(!isShortlisted) { %>
                                   					<a href='shortListApplicant.html?sin=<%=profile.getSIN() %>'" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                   				<% } else { %>
                                   					<br/>
													<div>
														<span class='text-danger'>Already Shortlisted for competition <%= job.getCompetitionNumber() %></span>
													</div>
													<br />
												<% } %>
                                   			<%}else{ %>
                                   					<a href='#' onclick="alert('Interview guide must be set for competition <%=job.getCompetitionNumber() %> before shortlist can be created.'); return false;" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-plus"></span> Add to Shortlist</a>
                                   			<%} %>
                                   			
                                    <%}}%>
                	</div>
</esd:SecurityAccessRequired>

<form id="frmverify">
   	<input id="appid" name ="appid" type="hidden" value="<%=profile.getSIN()%>">
</form>                      
 <!-- Modal for adding new letter -->
<div id="add_letter_dialog" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add Applicant Letter</h4>
                <div class="alert alert-danger" style="display:none;" id="dalertadd" align="center">
							<span id="dmessageadd"></span>
				</div>
				<div class="alert alert-success" style="display:none;" id="dalertadds" align="center">
							<span id="dmessageadds"></span>
				</div>
              </div>
               <div class="modal-body">
                   <p class="text-warning" id="title3">Letter Title:</p>
		    		<p>
		    		<input type="text" id="ltitle" name="ltitle" size="35">					
		    		</p>
                   <p class="text-warning" id="title3">Letter:</p>
		    		<p>
		    		<input type="file" id="ldocument" name="ldocument" accept="application/pdf">(PDF file format only)					
		    		</p>
			</div>
      <div class="modal-footer">
      	<button type="button" id='btn_confirm_letter' class="btn btn-success btn-xs" style="float:left;">Add Letter</button>  <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

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
</body>
</html>
