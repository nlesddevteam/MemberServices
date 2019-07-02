<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.school.bean.*,
                  com.awsd.school.dao.*,
                  com.awsd.school.*,
                  com.esdnl.util.*,
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
		
		JobOpportunityBean job = rec.getJob();
		JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
		AdRequestBean ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());

		//applicant profile information
    ApplicantProfileBean candidate = rec.getCandidate();
    ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(candidate.getSIN());
    ApplicantEducationBean[] edu = ApplicantEducationManager.getApplicantEducationBeans(candidate.getSIN());
    ApplicantEducationOtherBean edu_oth = ApplicantEducationOtherManager.getApplicantEducationOtherBean(candidate.getSIN());
    ApplicantEsdReplacementExperienceBean[] rpl = ApplicantEsdReplExpManager.getApplicantEsdReplacementExperienceBeans(candidate.getSIN());
    ApplicantSubstituteTeachingExpBean[] sub = ApplicantSubExpManager.getApplicantSubstituteTeachingExpBeans(candidate.getSIN());
    ApplicantExperienceOtherBean[] exp_other = ApplicantExpOtherManager.getApplicantExperienceOtherBeans(candidate.getSIN());
    ApplicantOtherInformationBean other_info = ApplicantOtherInfoManager.getApplicantOtherInformationBean(candidate.getSIN());
    ApplicantSupervisorBean[] refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(candidate.getSIN());
    ApplicantNLESDPermanentExperienceBean[] per = ApplicantNLESDPermExpManager.getApplicantNLESDPermanentExperienceBeans(candidate.getSIN());
    
    ApplicantProfileBean candidate_2 = null;
		if(!StringUtils.isEmpty(rec.getCandidate2()))
				candidate_2 = ApplicantProfileManager.getApplicantProfileBean(rec.getCandidate2());
		
		ApplicantProfileBean candidate_3 = null;
		if(!StringUtils.isEmpty(rec.getCandidate3()))
				candidate_3 = ApplicantProfileManager.getApplicantProfileBean(rec.getCandidate3());
		
		Collection<ReferenceCheckRequestBean> refReq = ReferenceCheckRequestManager.getReferenceCheckRequestBeans(job, candidate);
		
		
		ArrayList<ApplicantDocumentBean> coc = (ArrayList<ApplicantDocumentBean>)ApplicantDocumentManager.getApplicantDocumentBean(candidate, DocumentType.CODE_OF_CONDUCT);
		ArrayList<ApplicantDocumentBean> tc = (ArrayList<ApplicantDocumentBean>)ApplicantDocumentManager.getApplicantDocumentBean(candidate, DocumentType.TEACHING_CERTIFICATE);
		ArrayList<ApplicantDocumentBean> trans = (ArrayList<ApplicantDocumentBean>)ApplicantDocumentManager.getApplicantDocumentBean(candidate, DocumentType.UNIVERSITY_TRANSSCRIPT);
		
		ArrayList<ApplicantCriminalOffenceDeclarationBean> cods = (ArrayList<ApplicantCriminalOffenceDeclarationBean>) ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans(candidate);
		
    SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
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
			//var interval_id;
			$('document').ready(function(){
				
	      
	      
	    //  interval_id = setInterval(printPDFs, 5000);
			});
			
			//function printPDFs(){
			//	var coc = document.getElementById('coc-doc');
			//	if(coc)
			//		coc.printAll();
				
			//	var tc = document.getElementById('tc-doc');
		//		if(tc)
			//		tc.printAll();	
				
			//	var trans = document.getElementById('trans-doc');
			//	if(trans)
			//		trans.printAll();
				
			//	clearInterval(interval_id);
				
				//window.close();
			//}
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
					This letter is to confirm your appointment to a teaching position with the Newfoundland and Labrador English School District.  
					The specifics of your teaching position are listed below.  The terms of your employment will be in keeping with the policies 
					and by laws of the District and the NLTA Provincial Collective Agreement.  
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
							<td>School</td>
							<td><%= job.getJobLocation() %></td>
						</tr>
						<tr>
							<td>Position</td>
							<td><%= job.getPositionTitle() %></td>
						</tr>
						<tr>
							<td>Percentage</td>
							<td><%= rec.getTotalUnits() %></td>
						</tr>
						<tr>
							<td>Type</td>
							<td><%= rec.getEmploymentStatus().getDescription() %></td>
						</tr>
						<%if (ad.getStartDate() != null) { %>
							<tr>
								<td>Start Date (dd/mm/yyyy)</td>
								<td><%= ad.getFormatedStartDate() %></td>
							</tr>
						<%}%>
						<%if (ad.getEndDate() != null) { %>
							<tr>
								<td>End Date (dd/mm/yyyy)</td>
								<td><%= ad.getFormatedEndDate() %></td>
							</tr>
						<%}%>
					</tbody>
					</table>
				</p>
				<p>
					<b>Please note:  If this is a replacement position, the end date may change due to an earlier or later return of the incumbent.Also, please be advised that it may take up to four (4) weeks for processing of any payroll changes relating to this appointment.</b>
				</p>
				<br/>
				<p>
					We wish you continued success in your career.
				</p>
				<% AssistantDirectorHR adhr =  AssistantDirectorHR.get(((TeacherRecommendationBean)request.getAttribute("RECOMMENDATION_BEAN")).getOfferAcceptedDate()); %>
				<p><br/>
				Sincerely,<br />
				<img src='<%= adhr.getSignatureFile() %>' border='0' width='200' /><br />
				<%= adhr.getName() %><br />
				<%= adhr.getTitle() %><br />
				Cc: Personal File<br />
				ec.  School Principal<br />
				</p>
			</div>
			<hr>
			<div style="font-size:9px;text-align:center;">
				95 Elizabeth Avenue, St. John's, NL &middot; A1B 1R6<br />
				Telephone:  (709) 758-2372  &middot; Facsimile:  (709) 758-2706 &middot;  Web Site:  www.nlesd.ca &middot; Follow @NLESDCA
			</div>
		</div>	
</td>
</tr></table>	
		
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
		<td class="tableTitle" colspan=1>%UNIT:</td>
		<td class="tableResult" colspan=3><%=new DecimalFormat("0.00").format(ad.getUnits())%></td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>POSITION:</td>
		<td class="tableResult" colspan=3><%=rec.getPositionType().getDescription() %>
		 							<% if(rec.getPositionType().equal(PositionTypeConstant.OTHER)){%>
                                        <%=rec.getPositionTypeOther() %>
                            		<%} %>		
							<%if(rec.getGSU().length > 0){	%>                             
                             	<table class="table table-condensed table-striped" style="font-size:10px;">
                             	<thead>
                             	<tr><th width="25%">GRADE</th><th width="50%">SUBJECT</th><th width="25%">UNIT %</th></tr>
                             	</thead>
                             	<tbody>                             	
                             	<% for(int i = 0; i < rec.getGSU().length; i++) { %>
                             		<tr>
                             		<td><%=rec.getGSU()[i].getGrade().getGradeName()%></td>                             		
                             		<td><%=((rec.getGSU()[i].getSubject() != null)?rec.getGSU()[i].getSubject().getSubjectName():"N/A")%></td>
                             		<td><%=rec.getGSU()[i].getUnitPercentage()%></td>
                             		</tr>
                            <% } %>
                             	</tbody>
                             	</table>		
							<%} else { %>
                           		None Added.
								<%}%>
		</td>		
		</tr>
		<tr>
		<td colspan=4>&nbsp;</td>		
		</tr>
		<tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 3: QUESTIONS</td>		
		</tr>
		<tr>
		<td colspan=4>
		<b>1.) Does this teacher own a Permanent Contract with the Board?</b><br/>
					<%
                  
                   if(esd_exp != null) {						
                        	if((esd_exp.getPermanentContractSchool() != 0)&&(esd_exp.getPermanentContractSchool() != -1)){ %>
					YES. <%=esd_exp.getPermanentContractLocationText()%>. <%=esd_exp.getPermanentContractPosition()%>
					<% } else if((esd_exp.getContractSchool() != 0)&&(esd_exp.getContractSchool() != -1)){ %>
					REPLACEMENT. <%=esd_exp.getReplacementContractLocationText()%>. (REPLACEMENT ENDDATE: <%=esd_exp.getFormattedContractEndDate()%>)
					<%} else { %>
					NO
					<%}%>
					<%} else {%>
					 NO
                      <%} %>
	    </td>
		</tr>
		<tr>
		<td colspan=4>
		<b>2.) Does this appointment fill an existing position?</b><br/>
		 				<%=(!ad.isVacantPosition()?"YES":"NO")%> - <%= ad.getJobType() %>                         
                        <%if(!ad.isVacantPosition() || (ad.getOwner() != null) || org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason()) ){%>                     
                              <% if(ad.getOwner() != null) {%>
                              	Previous Teacher: <%=ad.getOwner().getFullnameReverse()%>
                              <% } %>
                              <% if(org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason())) { %>
                              	Reason For Vacancy: <%=ad.getVacancyReason()%>
                              <% } %>
                        <%}%>
		</td>
		</tr>
		<tr>
		<td colspan=4>
		<b>3.) Please indicate employment status being recommended.</b><br/>
		<%=rec.getEmploymentStatus().getDescription() %>
		</td>
		</tr>
		<tr>
		<td class="tableTitleL">Start Date (dd/mm/yyyy):</td>
		<td class="tableResultL"><%=ad.getFormatedStartDate()%></td>	
		<td class="tableTitleR">End Date (dd/mm/yyyy):</td>
		<td class="tableResultR"><%=(ad.getEndDate()!=null)?ad.getFormatedEndDate():""%></td>
			
		</tr>
		<tr>
		<td colspan=4>&nbsp;</td>		
		</tr>
		<tr>
		<td colspan=4 style="font-weight:bold;font-size:12px;border-top:1px solid black;">SECTION 4: INTERVIEW/REFERENCE</td>		
		</tr>
		<tr>
		<td colspan=4>
			<b>1.) Teaching methods completed: </b>
			<%= edu_oth.getProfessionalTrainingLevel().getDescription() %>
		</td>
		</tr>
		<tr>
		<td colspan=4>
			<b>2.) Newfoundland Teacher Certification Level: </b> 
			<%= edu_oth.getTeachingCertificateLevel() %>		
		</td>
		</tr>
		<tr>
		<td colspan=4>
			<b>3.) Candidates Interviewed:</b><br/>
			<job:JobShortlistDisplay cls='form-control' />
		</td>
		</tr>
		<tr>
		<td colspan=4>
		    <b>4.) Interview Panel:</b><br/>
		    <%=rec.getInterviewPanel() %>
		
		</td>
		</tr>
		<tr>
		<td colspan=4>
			<b>5.) Are the references satisfactory? </b>
			<%=rec.getReferencesSatisfactory() %>
		</td>
		</tr>
		<tr>
		<td colspan=4>
		<b>6.) Should any special conditions be attached to this appointment?</b>
						<%=rec.getSpecialConditions() %>                          
                        <%if(rec.getSpecialConditions().equalsIgnoreCase("Yes")){%>                        	
			                             	<br/><%=rec.getSpecialConditionsComment() %>
			            <%} %>
		</td>
		</tr>
		<tr>
		<td colspan=4>
		<b>7.) Other comments:</b><br/>
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
<!-- PAGE 4 APPLICANT PROFILE-->		
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
		<%if((esd_exp != null)&&(esd_exp.getPermanentContractSchool() != 0)&&(esd_exp.getPermanentContractSchool() != -1)){%>
		<tr>
		<td class="tableTitleL">Perm. Contract School</td>
		<td class="tableResultL"><%=esd_exp.getPermanentContractLocationText()%></td>
		<td class="tableTitleR">Perm. Contract Position</td>
		<td class="tableResultR"><%=esd_exp.getPermanentContractPosition()%></td>
		</tr>
		<%}%>
		<%if((esd_exp != null)&&(esd_exp.getContractSchool() != 0)&&(esd_exp.getContractSchool() != -1)){%>
		<tr>
		<td class="tableTitleL">Rep. Contract School</td>
		<td class="tableResultL"><%=esd_exp.getReplacementContractLocationText()%></td>
		<td class="tableTitleR">Rep. Contract End Date</td>
		<td class="tableResultR"><%=esd_exp.getFormattedContractEndDate()%></td>
		</tr>
		 <%}%>
		
		</tbody>
		</table>


		
<!-- EDUCATION-->
	<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=6 style="font-weight:bold;border-top:1px solid black;">UNIVERSITY/COLLEGE EDUCATION</th>
									    </tr>
 										</thead>
 										 <tbody> 
					<% if((edu != null) && (edu.length > 0)) { %>
										
									      <tr style="font-weight:bold;">
									        <td width='20%'>INSTITUTION</td>
									        <td width='10%'>DATES (M/Y)</td>
									        <td width='25%'>PROGRAM &amp; FACULITY</td>								        
									        <td width='17%'>MAJOR (#crs)</td>		
									        <td width='17%'>MINOR (#crs)</td>							        
									        <td width='11%'>DEGREE CONF.</td>								      
									      </tr>
									   
									    
									   
									    	
									    <% for(int i=0; i < edu.length; i ++) { %>
							                                   <c:set var="edu" value="<%=edu[i]%>" />
							                                   <tr>  
							                                   <td>${edu.institutionName}</td> 
							                                   <td><fmt:formatDate pattern = "MM/yy" value = "${edu.from}" /> - <fmt:formatDate pattern = "MM/yy" value = "${edu.to}" /></td> 
							                                   <td>${edu.programFacultyName}</td> 
							                                   <td>
							                                   <c:choose>
								                                   <c:when test="${edu.major ne '-1' }">
								                                  		<%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName()%> (${edu.numberMajorCourses})
								                                   </c:when>
							                                   		<c:otherwise>N/A</c:otherwise>
							                                   </c:choose>
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
									    
									    
									    
									    <% } else { %>
									    <tr>
									    <th colspan=6><span style="color:Grey;">No post secondary education currently on file.</span>
							       		</th>
							          <% } %>
                                       </tbody>
							        </table>
                                         

		    
		    <table class="table table-striped table-condensed" style="font-size:10px;">
							   <thead>
							   <tr>
							   <th colspan=4 style="font-weight:bold;border-top:1px solid black;">OTHER EDUCATION</th>
							   </tr>
							   </thead>
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
	                              	<tr><td colpsan=4><span style="color:Grey;">No other education currently on file.</span></td></tr>	                              	
	                               </c:otherwise>
                               </c:choose>
							    
							    
                               </tbody>
                             </table>  
		    
		    
		    

<!-- PERMANENT EXPERIENCE -->
 								<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=4 style="font-weight:bold;border-top:1px solid black;">NLESD PERMANENT EXPERIENCE (Total Months: <%=((esd_exp != null)?Integer.toString(esd_exp.getPermanentLTime()):"UNKNOWN")%>)</th>
									    </tr>
 										</thead>
 										 <tbody> 



								<% if((per != null) && (per.length > 0))
                                  {
                                  %>
							     		<tr style="font-weight:bold;">
									        <td width='20%'>FROM</td>
									        <td width='20%'>TO</td>
									        <td width='20%'>SCHOOL</td>								        
									        <td width='40%'>GRADES AND/OR SUBJECTS TAUGHT</td>		
									      </tr>
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
							   						    
							    <%} else {%>
                                  
                                  <tr> 
                                  <td colspan=4><span style="color:Grey;">No NLESD Permanent Experience currently on file.</span></td>
                                   </tr>
                                <% } %>
					 			</tbody>
							    </table>	



<!-- REPLACEMENT EXPERIENCE -->
 								<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=4 style="font-weight:bold;border-top:1px solid black;">NLESD REPLACEMENT CONTRACT EXPERIENCE (Total Months: <%=((esd_exp != null)?Integer.toString(esd_exp.getReplacementTime()):"UNKNOWN")%>)</th>
									    </tr>
 										</thead>
 										 <tbody> 


		    
		    					<%
							    if((rpl != null) && (rpl.length > 0))
                                  { %>
                               				    
									      <tr style="font-weight:bold;">
									        <td width='20%'>FROM</td>
									        <td width='20%'>TO</td>
									        <td width='20%'>SCHOOL</td>								        
									        <td width='40%'>GRADES AND/OR SUBJECTS TAUGHT</td>		
									      </tr>
									  
							     <% for(int i=0; i < rpl.length; i ++) {%>
							    <tr>
							    <td><%=sdf.format(rpl[i].getFrom()) %></td>
							    <td><%=sdf.format(rpl[i].getTo()) %></td>
							    <td><%=SchoolDB.getSchool(rpl[i].getSchoolId()).getSchoolName() %></td>
							    <td><%=rpl[i].getGradesSubjects() %></td>
							    </tr>
							    <% } %>							   							    
							    <%} else { %>
                                   <tr>
									<td colspan=4><span style="color:Grey;">No Replacement Contract Experience currently on file.</span></td>
                                  	</tr>
                                <% } %> 
                                </tbody>
							    </table> 
                                
                                
<!-- NLESD SUB EXPERIENCE -->
 								<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=4 style="font-weight:bold;border-top:1px solid black;">NLESD SUBSTITUTE TEACHING EXPERIENCE (Total Sub Days: <%=((esd_exp != null)?Integer.toString(esd_exp.getSubstituteTime()):"UNKNOWN")%>)</th>
									    </tr>
 										</thead>
 										 <tbody> 		    
		   						 <% if((sub != null) && (sub.length > 0)) { %>
							   								    
									      <tr style="font-weight:bold;">
									        <td width='30%'>FROM</td>
									        <td width='30%'>TO</td>									       								        
									        <td width='40%'># DAYS PER YEAR</td>		
									      </tr>
									    					   
                               <% for(int i=0; i < sub.length; i ++)
                                   {							   
							   %>
							    <tr>
							    <td><%=sdf.format(sub[i].getFrom()) %></td>
							    <td><%=sdf.format(sub[i].getTo()) %></td>							    
							    <td><%=sub[i].getNumDays() %></td>
							    </tr>
							    <% }%>
							    							    
							    <%} else { %>
							    <tr>
								<td colspan=4>
							   	<span style="color:Grey;">No Substitute Experience currently on file.</span>
							   </td>
							   </tr>
							  
                               <%}%>
                               </tbody>
							    </table>
                               
<!-- OTHER BOARD EXPERIENCE --------------------------------------------------------------->                                   
                 <table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=4 style="font-weight:bold;border-top:1px solid black;">OTHER BOARD EXPERIENCE</th>
									    </tr>
 										</thead>
 										 <tbody> 
               
               
               <%if((exp_other != null) && (exp_other.length > 0))
                                  { %>
                                  
									    
									      <tr style="font-weight:bold;">
									        <td width='15%'>FROM</td>
									        <td width='15%'>TO</td>	
									        <td width='30%'>SCHOOL &amp; BOARD</td>								       								        
									        <td width='40%'>GRADES AND/OR SUBJECTS TAUGHT</td>		
									      </tr>
									    
                                <% for(int i=0; i < exp_other.length; i ++) { %>
							    <tr>
							    <td><%=sdf.format(exp_other[i].getFrom()) %></td>
							    <td><%=sdf.format(exp_other[i].getTo()) %></td>							    
							    <td><%=exp_other[i].getSchoolAndBoard() %></td>
							    <td><%=exp_other[i].getGradesSubjects() %></td>
							    </tr>
							    <% }%>
                                
                                  
                                  
                                  <%} else { %>
							   
                                   <tr>
									    <td colspan=4><span style="color:Grey;">No Other Board Experience currently on file.</span></td>
                                  </tr>
                                 <%}%> 
               
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
		<div align="center"><img src="includes/img/nlesd-colorlogo.png" width="400"></div>
		<br/><br/>
		
		
		<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=4 style="font-weight:bold;border-top:1px solid black;">CANDIDATE REFERENCE CHECK</th>
									    </tr>
 										</thead>
 										 <tbody> 		
										<tr>
										<td class="tableTitle">CANDIDATE:</td>
										<td class="tableResult"><%= candidate.getFullName() %></td>
										</tr>
										<tr>
										<td class="tableTitle">COMP #:</td>
										<td class="tableResult"><%= job.getCompetitionNumber() %></td>
										</tr>
										<tr>
										<td class="tableTitle">POSITION</td>
										<td class="tableResult"><%= job.getPositionTitle() %></td>
										</tr>
										<tr>
										<td class="tableTitle">LOCATION:</td>
										<td class="tableResult"><%=(ass[0].getLocation() > 0)? ass[0].getLocationText():""%></td>
										</tr>
										<tr>
										<td class="tableTitle">REGION:</td>
										<td class="tableResult"><%= ass[0].getRegionText() %></td>
										</tr>
										</tbody>
										</table>
					
					
					
		<%
		//NOW WE GET THE LIST BEAN AND DETERMINE REF TYPE
		NLESDReferenceListBean nlesdref = NLESDReferenceListManager.getReferenceBeansByApplicantRec(rec.getReferenceId(),candidate.getSIN());
		if(nlesdref.getReferenceType().equals("TEACHER"))
		{
			NLESDReferenceTeacherBean mref = NLESDReferenceTeacherManager.getNLESDReferenceTeacherBean(rec.getReferenceId());
			request.setAttribute("mref", mref);
		%>
			<jsp:include page="printable_rec_nlesd_teacher_ref.jsp" />	
			
			
		<% }else if(nlesdref.getReferenceType().equals("GUIDE")) 
		{
			NLESDReferenceGuideBean mref = NLESDReferenceGuideManager.getNLESDReferenceGuideBean(rec.getReferenceId());
			request.setAttribute("mref", mref);
		
		%>
		<jsp:include page="printable_rec_nlesd_guide_ref.jsp" />
		<%}else if(nlesdref.getReferenceType().equals("ADMIN"))
		{
			NLESDReferenceAdminBean mref = NLESDReferenceAdminManager.getNLESDReferenceAdminBean(rec.getReferenceId());
			request.setAttribute("mref", mref);
		%>
		<jsp:include page="printable_rec_nlesd_admin_ref.jsp" />
		<%}else if(nlesdref.getReferenceType().equals("EXTERNAL"))
		{
			NLESDReferenceExternalBean mref = NLESDReferenceExternalManager.getNLESDReferenceExternalBean(rec.getReferenceId());
			request.setAttribute("mref", mref);
		%>
		<jsp:include page="printable_rec_nlesd_external_ref.jsp" />
		<%}else{
			ReferenceBean mref = ReferenceManager.getReferenceBean(rec.getReferenceId());
			request.setAttribute("mref", mref);
		%>
		<jsp:include page="printable_rec_orig_ref.jsp" />
		<%} %>
		
		
  <%if(cods != null && cods.size() > 0) {%>
  	<c:set var='cod' value='<%=cods.get(0) %>' />
  	
  	<div class="pagebreak"></div>
  	
  	<table align="center">      
        <tr><td>
		<div class='main' style="max-width:650px;font-size:10px;">
			<div class='header'>
				<img src="includes/img/empltr_header.jpg" border="0" width='650' />
			</div>
			<br/><br/>
			<div class='content'>
			<br/><br/>
			<table class="table table-striped table-condensed" style="font-size:10px;">
			<tbody>
			<tr>
			<td class="tableTitleL">NAME:</td>
			<td class="tableResultL">${cod.applicant.fullName}</td>
			<td class="tableTitleR">DATE OF BIRTH:</td>
			<td class="tableResultR">
				<c:choose>
				<c:when test="${cod.applicant.DOB eq null}">
				Currently not on file.
				</c:when>
				<c:otherwise>
				${cod.applicant.DOBFormatted}
				</c:otherwise>
				</c:choose>
			</td>
			</tr>
			<tr>
			<td class="tableTitleL">POSITION:</td>
			<td class="tableResultL">${cod.position}</td>
			<td class="tableTitleR">LOCATION/SCHOOL:</td>
			<td class="tableResultR">${cod.location}</td>
			</tr>
			
			</tbody>
			</table>
			<br/><br/>	
            <b>DECLARATION DATE:</b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${cod.declarationDate}" />      	
            <br/><br/>	      	
                  	
           				<ol>
           					<li>
           						<b>I DECLARE</b>, since the last Criminal Reference Check (CRC) provided to Newfoundland and Labrador English School District, or since the last Criminal Offence Declaration (COD) given by me to the district, that:
           							<ol style='padding-top:5px;list-style-type: lower-alpha;'>
           								<li>
           									I have <b>no</b> convictions under the <i>Criminal Code of Canada</i> up and including the date of this
           									declaration for which a pardon has not been issued or granted under the <i>Criminal Records Act (Canada)</i>.
           								</li>
           								<li>
           									I have <b>no</b> charges pending under the <i>Criminal Code of Canada</i> up to and including the date
           									of this declaration.
           								</li>
           							</ol>
           					</li>
           				</ol>
           		
           		<b>OR</b>
           		<br/><br/>	
           		
           				<ol start='2'>
           					<li>
           						I have the following convictions/charges for offences under the <i>Criminal Code of Canada</i>
           						for which a pardon under the <i>Criminal Records Act (Canada)</i> has <b>not</b> been
           						issued or granted.
           						<br/><br/>
           						<b>LIST OF OFFENSES:</b>
           						<br/><br/>
           						<c:choose>
           							<c:when test="${fn:length(cod.offences) gt 0}">				
             						<ol id='ol-offences'>
             							<c:forEach items="${cod.offences}" var='codo'>
              							<li>
              								<ol style='list-style-type: lower-alpha;'>
              									<li><b>Date:</b>&nbsp;&nbsp;<fmt:formatDate type="both" dateStyle="medium" value="${codo.offenceDate}" /></li>
              									<li><b>Court Location:</b>&nbsp;&nbsp;${codo.courtLocation}</li>
              									<li><b>Conviction/Charge:</b>&nbsp;&nbsp;${codo.conviction}</li>
              								</ol>
              							</li>
             							</c:forEach>
             						</ol>
           							</c:when>
           							<c:otherwise>
           								<br /><span style='color:red;padding:0px;'>No offences listed.</span>
           							</c:otherwise>
           						</c:choose>
           					</li>
           				</ol>
			            			
			</div>			
			<hr>
			<div style="font-size:9px;text-align:center;">
				95 Elizabeth Avenue, St. John's, NL &middot; A1B 1R6<br />
				Telephone:  (709) 758-2372  &middot; Facsimile:  (709) 758-2706 &middot;  Web Site:  www.nlesd.ca<br />
				Follow @NLESDCA
			</div>
		</div>
		</td>
		</tr>
		</table>
  <%}%>  
  
 <jsp:include page="print_privacy.jsp" />
  
  <script>
  $('document').ready(function(){
	  window.print();
 });
 
  
  </script>
  
	</body>
</html>
