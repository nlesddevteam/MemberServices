<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.bean.*,
                 com.awsd.school.dao.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<job:ApplicantLoggedOn/>


<html>

	<head>
		<title>Newfoundland &amp; Labrador English School District - Letter of Employment</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		
		<style type="text/css">
			
			
			@media print {
				
				.content{
					font-family:verdana,sans-serif;
  				font-size:9.5px;
  				margin: auto;
  				width:650px;
					min-height: 675px;
				}
				
				#prnHeader1{display:none;}
				#empTable1{font-size:9.5px;}
			}
			
		</style>
	</head>
	
	<body>
        <table align="center">
        <tr><td>&nbsp;</td></tr>
        <tr><td>
		<div class='main' style="max-width:650px;font-size:10px;">
			<div class='header'>
				<img src="includes/img/empltr_header.jpg" border="0" width='650' />
			</div>
			<br/><br/>
			<div class='content'>
				<p>
					<fmt:formatDate type="date" dateStyle="medium" value="${rec.processedDate}" />
				</p>
				<p>
					${candidate.fullNameReverse}<br/>
					${candidate.address}
				</p>
				<p>
					<b>Re: Competition Number ${rec.competitionNumber}</b> 
				</p>
				<br/>
				<p>
					Dear ${candidate.firstname}
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
							<td>School:</td>
							<td><%=((JobOpportunityBean)request.getAttribute("jbean")).getJobLocation() %></td>
						</tr>
						<tr>
							<td>Position:</td>
							<td><%= ((JobOpportunityBean)request.getAttribute("jbean")).getPositionTitle() %></td>
						</tr>
						<tr>
							<td>Percentage:</td>
							
							<c:choose>
								<c:when test="${rec.totalUnits gt 0}">
									<td><fmt:formatNumber type = "number" minFractionDigits = "2" value = "${rec.totalUnits}" /></td>
									
								</c:when>
								<c:when test="${adreq.units gt 0}">
									<td><fmt:formatNumber type = "number" minFractionDigits = "2" value = "${adreq.units}" /></td>
								</c:when>
								<c:otherwise>
								<td>0.0</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td>Type:</td>
							<td>${rec.employmentStatus.description}</td>
						</tr>
						<c:if test="${adreq.startDate ne null}">
							<tr>
								<td>Start Date (dd/mm/yyyy):</td>
								<td>${adreq.formatedStartDate}</td>
							</tr>
						</c:if>
						<c:if test="${adreq.endDate ne null}">
							<tr>
								<td>End Date (dd/mm/yyyy):</td>
								<td>${adreq.formatedEndDate}</td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</p>
				<p>
					<b>Please note:  If this is a replacement position, the end date may change due to an earlier or later return of the incumbent. 
					Also, please be advised that it may take up to four (4) weeks for processing of any payroll changes relating to this appointment.</b>
				</p>
				<br/>
				<p>
					We wish you continued success in your career.
				</p>
				
				<% AssistantDirectorHR adhr =  AssistantDirectorHR.get(((TeacherRecommendationBean)request.getAttribute("rec")).getOfferAcceptedDate()); %>
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
				Telephone:  (709) 758-2372  &middot; Facsimile:  (709) 758-2706 &middot;  Web Site:  www.nlesd.ca<br />
				Follow @NLESDCA
			</div>
		</div>
		</td></tr>
		<tr><td>&nbsp;</td></tr>
		</table>	
	</body>
	
</html>
