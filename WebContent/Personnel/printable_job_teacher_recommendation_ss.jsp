<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.school.*,
                  com.esdnl.util.*,
                  com.awsd.school.dao.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
		TeacherRecommendationBean rec = (TeacherRecommendationBean) request.getAttribute("RECOMMENDATION_BEAN");
		AssistantDirectorHR adhr =  AssistantDirectorHR.get(rec.getOfferAcceptedDate());
		
		JobOpportunityBean job = rec.getJob();
		JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
		//AdRequestBean ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());
		RequestToHireBean rth = RequestToHireManager.getRequestToHireByCompNum(job.getCompetitionNumber());
		//applicant profile information
   		 ApplicantProfileBean candidate = rec.getCandidate();
		//some of the includes are looking for the variable profile
		//ApplicantProfileBean profile = rec.getCandidate();
		
		pageContext.setAttribute("APPLICANT", candidate, PageContext.SESSION_SCOPE);
	
    
    	ApplicantProfileBean candidate_2 = null;
		if(!StringUtils.isEmpty(rec.getCandidate2()))
				candidate_2 = ApplicantProfileManager.getApplicantProfileBean(rec.getCandidate2());
		
		ApplicantProfileBean candidate_3 = null;
		if(!StringUtils.isEmpty(rec.getCandidate3()))
				candidate_3 = ApplicantProfileManager.getApplicantProfileBean(rec.getCandidate3());
		
		Collection<ReferenceCheckRequestBean> refReq = ReferenceCheckRequestManager.getReferenceCheckRequestBeans(job, candidate);
		
		
		SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	    ApplicantNLESDExperienceSSBean esd_exp = ApplicantNLESDExperienceSSManager.getApplicantNLESDExperienceSSBeanBySin(candidate.getSIN());
	    ApplicantEducationSecSSBean edu = ApplicantEducationSecSSManager.getApplicantEducationSecSSBeanBySin(candidate.getSIN());
	    ApplicantEducationOtherSSBean other_info = ApplicantEducationOtherSSManager.getApplicantEducationOtherSSBean(candidate.getSIN());
	    ApplicantSupervisorBean[] refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(candidate.getSIN());
	    NLESDReferenceListBean[] chks = NLESDReferenceListManager.getReferenceBeansByApplicant(candidate.getSIN());
	    boolean validReference = true;
	    if(chks.length == 0 || refs == null){
	    	validReference = false;
	    }
	  		
    
%>
<html>
	<head>
		<title>
		  <%=job.getCompetitionNumber()%> Candidate Recommendation
		</title>
		<style>
		@media print {		
				
				.content{
					font-family:verdana,sans-serif;
  				font-size:9.5px;
  				margin: auto;
  				width:650px;
					min-height: 675px;
				}
				.mainFooter{display:none;}
				#noPrintThis2 {display:none;}
				#noPrintThis{display:none;}
				#empTable1{font-size:9.5px;}
		 		table { page-break-inside:auto;border:none !important; }
   				tr    { page-break-inside:avoid; page-break-after:auto;border:none !important; }
   				td {border:none !important;}
		
		}
  		.pageBreak {
				page-break-before: always;
			}
			
			.tableTitle {font-weight:bold;width:15%;}
.tableResult {font-weight:normal;width:85%;}
.tableQuestion {font-weight:normal;width:70%;}
.tableAnswer {font-weight:normal;width:30%;}
.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}

input {border:1px solid silver;}	
			
		</style>
		
		<script type='text/javascript'>
			var interval_id;
			$('document').ready(function(){
	      window.print();
	      
	      	});
			
		</script>
	</head>
	
	<body>
	<table align="center">        
        <tr><td>
		<div class='main' style="max-width:650px;font-size:10px;">
			<div class='header'>
				<img src="includes/img/empltr_header.jpg" border="0" width='650' />
			</div>
			<br/><br/>
			<div class='content'>
				<p>
					<fmt:formatDate type="date" dateStyle="medium" value="<%=rec.getProcessedDate()%>" />
				</p>
				<p>
					<%=candidate.getFullNameReverse()%><br/>
					<%=candidate.getAddress()%>
				</p>
				<br/>
				<p>
					<b>Re: Competition Number <%=rec.getCompetitionNumber()%></b> 
				</p>
				<p>
					Dear <%=candidate.getFirstname()%>
				</p>
				<p>
					This letter is to confirm your appointment to a position with the Newfoundland and Labrador English School District.  
					The specifics of your position are listed below.  The terms of your employment will be in keeping with the policies 
					and by laws of the District.  
					You should print a copy of this correspondence for future references.
				</p>
				
				<p>
					<table id="empTable1" class="table table-condensed table-striped" style="font-size:10px;">
						<thead>
						<tr>
						<th colspan=2>Position Details</th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td>School/Work Location:</td>
							<td>${empty rbean.workLocation ?'':rbean.locationDescription}</td>
						</tr>
						<tr>
							<td>Position:</td>
							<td>${empty rbean.jobTitle?'':rbean.jobTitle}</td>
						</tr>
						<tr>
							<td>Hours/Week:</td>
							<td>${empty rbean.positionHours? '':rbean.positionHours}</td>
						</tr>
						<tr>
							<td>10 or 12 Month:</td>
							<td>${rbean.positionTerm eq 0 ?'':rbean.positionTermString}</td>
						</tr>
						<tr>
							<td>Type:</td>
							<td>${rbean.positionType eq 0 ?'':rbean.positionTypeString}</td>
						</tr>
						<tr>
							<td>Start Date:</td>
							<td>${empty rbean.startDate ?'':rbean.startDateFormatted}</td>
						</tr>
						<tr>
							<td>End Date(if applicable):</td>
							<td>${empty rbean.endDate ?'':rbean.endDateFormatted}</td>
						</tr>
						
						</tbody>
					</table>			
				
						
				</p>
				<p>
					Details pertaining to your compensation, eligibility for benefits, and pension plan enrollment will be discussed with you in the near future.  
				</p>
				<br/>
				<p>
					Please note: if this is a replacement position, the end date may change due to an earlier or later return of the incumbent.   
				</p>
				
				
				<p><br/>
				Sincerely,<br />
				<img src='<%= adhr.getSignatureFile() %>' border='0' width='150' /><br />
				<%= adhr.getName() %><br />
				<%= adhr.getTitle() %><br />
				Cc: Personal File<br />
				ec.  School Principal<br />
				</p>
			</div>
			<br/>
			<div style="font-size:9px;text-align:center;">
				95 Elizabeth Avenue, St. John's, NL &middot; A1B 1R6<br />
				Telephone:  (709) 758-2372  &middot; Facsimile:  (709) 758-2706 &middot;  Web Site:  www.nlesd.ca &middot; Follow @NLESDCA
			</div>
		</div>
</td>
</tr>
</table>

<div class='pageBreak'></div>		
<!-- PAGE 2 -->	

<table class="table table-condensed borderless" style="font-size:10px;">
		<thead>
		<tr>
		<th colspan=4 style="font-size:14px;">
		Competition # <%=job.getCompetitionNumber()%> Candidate Recommendation
		</th>
		</tr>
		</thead>
		<tbody>
		<tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 1: DEMOGRAPHICS</td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>NAME:</td>
		<td class="tableResult" colspan=3><%=candidate.getFullName() %></td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>ADDRESS:</td>
		<td class="tableResult" colspan=3>
										<%	
                                  		if(!StringUtils.isEmpty(candidate.getAddress1()))
                                  			out.print(candidate.getAddress1());
                                  		if(!StringUtils.isEmpty(candidate.getAddress2()))
                                					out.print(" &moiddot; " + candidate.getAddress2());
                                  		out.println(", " + candidate.getProvince() + " &nbsp; " + candidate.getCountry() + " &middot; " + candidate.getPostalcode());
                                  		%>
		</td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>TELEPHONE:</td>
		<td class="tableResult" colspan=3>
									<%
                                  		if(!StringUtils.isEmpty(candidate.getHomephone()))
                                  				out.println("Home: " + candidate.getHomephone());
                                  		if(!StringUtils.isEmpty(candidate.getWorkphone()))
                                					out.println(" &middot; Work: " + candidate.getWorkphone());
                                  		if(!StringUtils.isEmpty(candidate.getCellphone()))
                                 				out.println(" &middot; Cell: " + candidate.getCellphone());
                                  	%>
		</td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>DATE OF BIRTH:</td>
		<td class="tableResult" colspan=3><%= (candidate.getDOB()!= null)?candidate.getDOBFormatted():"<SPAN style='color:#FF0000;'>NOT ON RECORD</SPAN>" %></td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>SIN:</td>
		<td class="tableResult" colspan=3><%= !StringUtils.isEmpty(candidate.getSIN2())?candidate.getSIN2():"<SPAN style='color:#FF0000;'>NOT ON RECORD</SPAN>"  %></td>		
		</tr>
		
		<tr>
		<td colspan=4>&nbsp;</td>		
		</tr>
		
		
		
		<tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 2: SCHOOL/POSITION</td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>SCHOOL:</td>
		<td class="tableResult" colspan=3><%=(ass[0].getLocation() > 0)? ass[0].getLocationText():"N/A"%></td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>REGION/ZONE:</td>
		<td class="tableResult" colspan=3><%=ass[0].getRegionText()%></td>		
		</tr>
		
		<tr>
		<td class="tableTitle" colspan=1>POSITION:</td>
		<td class="tableResult" colspan=3><%=rth.getJobTitle()%></td>		
		</tr>
		<tr>
		<td colspan=4>&nbsp;</td>		
		</tr>   
        <tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 3: QUESTIONS</td>		
		</tr>
		<tr>
		<td colspan=4>
		<b>1.) Does this employee own a Permanent Contract with the Board?</b><br/>
					<%	String sch_str = null;
	                           	if(esd_exp != null){
	                           		if(esd_exp.getCurrentlyEmployed().equals("Y")){
	                           			out.println("Yes");
	                           		}else{
	                           			out.println("No");
	                           		}
	                           	}
							%>
	    </td>
		</tr>
		<tr>
		<td colspan=4>
		<b>2.) Does this appointment fill an existing position?</b><br/>
		 				<%=org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent())?"Yes":"No" %> 
                          <% if(org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent())) { %>
		                                            	 - Previous Incumbent: <%=rth.getPreviousIncumbent()%>
		                  <% } %>
		</td>
		</tr>
		<tr>
		<td colspan=4>
		<b>3.) Please indicate employment status being recommended.</b><br/>
		<%=rth.getPositionTypeString()%>
		</td>
		</tr>
		<tr>
		<td class="tableTitleL">Start Date (dd/mm/yyyy):</td>
		<td class="tableResultL" colspan=3><%=rth.getStartDateFormatted()%></td>	
		
			
		</tr>
		<tr>
		<td colspan=4>&nbsp;</td>		
		</tr>
        
        <tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 4: INTERVIEW/REFERENCE</td>		
		</tr>
		
		<tr>
		<td colspan=4>
			<b>1.) Candidates Interviewed:</b><br/>
			<job:JobShortlistDisplay cls='form-control' />
		</td>
		</tr>
		<tr>
		<td colspan=4>
		    <b>2.) Interview Panel:</b><br/>
		    <%=rec.getInterviewPanel() %>
		
		</td>
		</tr>
		<tr>
		<td colspan=4>
			<b>3.) Are the references satisfactory? </b>
			<%=rec.getReferencesSatisfactory() %>
		</td>
		</tr>
		<tr>
		<td colspan=4>
		<b>4.) Should any special conditions be attached to this appointment?</b>
						<%=rec.getSpecialConditions() %>                          
                        <%if(rec.getSpecialConditions().equalsIgnoreCase("Yes")){%>                        	
			                             	<br/><%=rec.getSpecialConditionsComment() %>
			            <%} %>
		</td>
		</tr>
		<tr>
		<td colspan=4>
		<b>5.) Other comments:</b><br/>
		<%=(!StringUtils.isEmpty(rec.getOtherComments())?rec.getOtherComments():"N/A") %>
		</td>
		</tr>
		<tr>
		<td colspan=4>&nbsp;</td>		
		</tr>	
        <tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 5: OTHER CANDIDATES</td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>2nd Choice Candidate:</td>
		<td class="tableResult" colspan=3><%=((candidate_2 != null)?candidate_2.getFullName():"NONE SELECTED") %></td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>3rd Choice Candidate:</td>
		<td class="tableResult" colspan=3><%=((candidate_3 != null)?candidate_3.getFullName():"NONE SELECTED") %></td>		
		</tr>
		
		
		
		<tr>
		<td colspan=4>&nbsp;</td>		
		</tr>

		<tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 6: RECOMMENDATION STATUS</td>		
		</tr>
		<%if(rec.isRecommended()){ %>
		<tr>
		<td class="tableTitle" colspan=1>COMPLETED BY:</td>
		<td class="tableResult" colspan=3><%= rec.getRecommendedByPersonnel().getFullNameReverse() + " on " + rec.getRecommendedDateFormatted() %></td>		
		</tr>
		<%} %>
		<%if(rec.isApproved()){ %>
		<tr>
		<td class="tableTitle" colspan=1>APPROVED BY:</td>
		<td class="tableResult" colspan=3><%= rec.getApprovedByPersonnel().getFullNameReverse() + " on " + rec.getApprovedDateFormatted() %></td>		
		</tr>
		<%} %>
		<%if(rec.isAccepted()){ %>
		<tr>
		<td class="tableTitle" colspan=1>ACCEPTED BY:</td>
		<td class="tableResult" colspan=3><%= rec.getAcceptedByPersonnel().getFullNameReverse() + " on " + rec.getAcceptedDateFormatted() %></td>		
		</tr>
		<%} %>
		<%if(rec.isRejected()){ %>
		<tr>
		<td class="tableTitle" colspan=1>REJECTED BY:</td>
		<td class="tableResult" colspan=3><%= rec.getRejectedByPersonnel().getFullNameReverse() + " on " + rec.getRejectedDateFormatted() %></td>		
		</tr>
		<%} %>
		<%if(rec.isOfferMade()){%>
		<tr>
		<td class="tableTitle" colspan=1>OFFER MADE BY:</td>
		<td class="tableResult" colspan=3><%= rec.getOfferMadeByPersonnel().getFullNameReverse() + " on " + rec.getOfferMadeDateFormatted() %></td>		
		</tr>
		<%} %>
		<%if(rec.isOfferIgnored()){%>
		<tr>
		<td class="tableTitle" colspan=1>OFFER EXPIRED ON:</td>
		<td class="tableResult" colspan=3><%= rec.getOfferValidDateFormatted() %></td>		
		</tr>
		<%} %>
		<%if(rec.isOfferAccepted()){%>
		<tr>
		<td class="tableTitle" colspan=1>OFFER ACCEPTED BY:</td>
		<td class="tableResult" colspan=3>
											<%= rec.getCandidate().getFullNameReverse() + " on " + rec.getOfferAcceptedDateFormatted() %>
											<%if(rec.isLetterOfOfferRequire()){ %>
															<br/><span style="color:Red;">PAPER COPY OF OFFER LETTER REQUESTED.</span>
											<%}%>
		
		</td>		
		</tr>
		<%}%>
		<%if(rec.isOfferRejected()){%>
		<tr>
		<td class="tableTitle" colspan=1>REJECTED BY:</td>
		<td class="tableResult" colspan=3><%= rec.getCandidate().getFullNameReverse() + " on " + rec.getOfferRejectedDateFormatted() %></td>		
		</tr>
		<%}%>
		<%if(rec.isProcessed()){ %>
		<tr>
		<td class="tableTitle" colspan=1>PROCESSED BY:</td>
		<td class="tableResult" colspan=3><%= rec.getProcessedByPersonnel().getFullNameReverse() + " on " + rec.getProcessedDateFormatted() %></td>		
		</tr>
		<%}%>

		</tbody>
		</table>						
							
			
		<div class='pageBreak'></div>		
<!-- PAGE 2 -->	
<!-- APPLICANT PROFILE-->		
		<div align="center"><img src="includes/img/nlesd-colorlogo.png" width="400"><br/><br/>
		<span style="font-size:16px;font-weight:bold;"><%=candidate.getFullNameReverse() %></span>
		</div>
		
		<table class="table table-condensed" style="font-size:10px;">
		<thead>
		<tr>
		<th colspan=4 style="font-weight:bold;border-top:1px solid black;">APPLICANT PROFILE</th>
		</tr>
		</thead>
		<tbody>		
		<tr>
		<td class="tableTitle" colspan=1>NAME:</td>
		<td class="tableResult" colspan=3>
		<%=candidate.getFirstname() + " " + ((candidate.getMiddlename() != null)?candidate.getMiddlename() + " ":"") + candidate.getSurname() 
		          + ((candidate.getMaidenname()!= null)?" ("+candidate.getMaidenname()+")":"")%>
		
		</td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>ADDRESS:</td>
		<td class="tableResult" colspan=3>
					<%=candidate.getAddress1()%>		            
		             <%=(candidate.getAddress2()!=null)?" &middot; "+candidate.getAddress2():""%>		            
		            , <%=candidate.getProvince() + " &middot: " + candidate.getCountry() + " &middot; " + candidate.getPostalcode()%>
		</td>
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>TELEPHONE: </td>
		<td class="tableResult" colspan=3>
		(Res) <%=(candidate.getHomephone()!=null)?candidate.getHomephone():"N/A"%> &middot; 
		(Work) <%=(candidate.getWorkphone()!=null)?candidate.getWorkphone():"N/A"%> &middot; 
		(Cell) <%=(candidate.getCellphone()!=null)?candidate.getCellphone():"N/A"%>
		</td>
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>EMAIL:</td>
		<td class="tableResult" colspan=3><%=candidate.getEmail()%></td>
		</tr>		
		
		</tbody>
		</table>
		
		
<!-- PAGE 4 -->			
		
		<table class="table table-condensed borderless" style="font-size:10px;">
		<thead>
		<tr>
		<th colspan=4 style="font-size:14px;">
		NLESD EXPERIENCE
		</th>
		</tr>
		</thead>
		<tbody>
		 <%if(esd_exp != null){%>
		<tr>
        <td colspan=3>Are you currently employed with the NLESD?</td>
         <td><%=esd_exp.getCurrentlyEmployed().equals("Y") ? "Yes" : "No"%></td>
        </tr>
                       <tr>
                         <td class="tableTitleL">Senority Date</td>
                         <td class="tableResultL"><%=esd_exp.getSenorityDateFormatted()%></td>                       
                         <td class="tableTitleR">Senority Status</td>
                         <td class="tableResultR"><%=esd_exp.getSenorityStatusText()%></td>
                       </tr>
                       <tr>
                         <td>Current NLESD Position(s)</td>
                         <td colspan=3>
                        		<job:ApplicantCurrentPositionsSS  showdelete="view"/>
                         </td>
                       </tr>
        <%}else{%>
                       <tr>
                         <td>No Experience currently on file.</td>
                       </tr>
          <%}%>
          </tbody> 
         </table>
 
 
 <!-- EMPLOYMENT HISTORY -->         	
          <table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th style="font-weight:bold;border-top:1px solid black;">EMPLOYMENT HISTORY</th>
									    </tr>
 										</thead>  
 										<tbody>
 										<tr>
 										<td><job:ApplicantEmploymentSS showdelete="view"/></td>
 										</tr>
 										</tbody>
 										</table>         		   	
          	
          	
                  
                      
   <!-- EDUCATION -->                     
             <table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=2 style="font-weight:bold;border-top:1px solid black;">EDUCATION</th>
									    </tr>
 										</thead>  
 										<tbody>          
                      
                      
          
                     	<%if(edu != null){%>
                         <tr>
                           <td class="tableTitle">Education Level (Highest Level)</td>
                           <td class="tableResult"><%=edu.getEducationLevel() != null ? edu.getEducationLevel() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">School Name</td>
                           <td class="tableResult"><%=edu.getSchoolName() != null ? edu.getSchoolName() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">School Town/City</td>
                           <td class="tableResult"><%=edu.getSchoolCity() != null ? edu.getSchoolCity() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">School Province/State</td>
                           <td class="tableResult"><%=edu.getSchoolProvince() != null ? edu.getSchoolProvince() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">Did you graduate?</td>
                           <td class="tableResult"><%=edu.getGraduatedText() != null ? edu.getGraduatedText() : ""%></td>
                         </tr>
                       <%}else{%>
                         <tr>
                           <td colspan=2><span style="color:Grey;">No education currently on file.</span></td>
                         </tr>
                       <%}%>
                       </tbody>
                     </table>
                     
  <!-- DEGREES AND OTHER PAPER -->                    
          			<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th style="font-weight:bold;border-top:1px solid black;">DEGREES/DIPLOMAS/CERTIFICATES</th>
									    </tr>
 										</thead>  
 										<tbody>
 										<tr>
 										<td><job:ApplicantEducationPostSS showdelete="view"/></td>
 										</tr>
 										</tbody>
 										</table>         		    
         
                   
          
                             	
  
  <!-- OTHER INFORMATION -->             
               				<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th style="font-weight:bold;border-top:1px solid black;">OTHER INFORMATION</th>
									    </tr>
 										</thead>
 										 <tbody> 
               
							               <%if((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())) { %>                    
							                    <tr><td><%=other_info %></td></tr>                  
							                    <%} else { %>
							                    <tr>
												<td colspan=4><span style="color:Grey;">No Other Information currently on file.</span></td>
							                    </tr>
							                    <%}%>             
							               </tbody>
							               </table>
  
  
                          		    
 
 
 <table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=4 style="font-weight:bold;border-top:1px solid black;">REFERENCES</th>
									    </tr>
 										</thead>
 										 <tbody> 
	
	<% if((refs != null) && (refs.length > 0))
                              		{ %>
                             
									    
									      <tr style="font-weight:bold;">
									        <td width='20%'>FULL NAME</td>
									        <td width='20%'>TITLE</td>	
									        <td width='45%'>PRESENT ADDRESS</td>								       								        
									        <td width='15%'>TELEPHONE</td>		
									      </tr>
									   
                              
                              
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
							   
							    <%} else { %>							   
                                    <tr>
							    <td colspan=4><span style="color:Grey;">No References currently on file.</span></td>
							    	</tr>
                                   
                                 <% } %> 
	
	 							</tbody>
							    </table>
	
	
	 <div class="pagebreak"></div>     
		    
		<%
		//NOW WE GET THE LIST BEAN AND DETERMINE REF TYPE
		if(rec.getReferenceId() > 0){
			
		
		NLESDReferenceListBean nlesdref = NLESDReferenceListManager.getReferenceBeansByApplicantRec(rec.getReferenceId(),candidate.getSIN());
		if(nlesdref.getReferenceType().equals("SUPPORT"))
		{
			NLESDReferenceSSSupportBean mref = NLESDReferenceSSSupportManager.getNLESDReferenceSSSupportBean(rec.getReferenceId());
			request.setAttribute("mref", mref);
		%>
			<jsp:include page="printable_rec_nlesd_support_ref.jsp" />
		<% }else if(nlesdref.getReferenceType().equals("MANAGE")) 
		{
			NLESDReferenceSSManageBean mref = NLESDReferenceSSManageManager.getNLESDReferenceSSManageBean(rec.getReferenceId());
			request.setAttribute("mref", mref);
		
		%>
		<jsp:include page="printable_rec_nlesd_manage_ref.jsp" />
		<%}} %>
		
<jsp:include page="print_privacy.jsp" />
	</body>
</html>
