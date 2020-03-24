<%@ page language="java"
         import="java.text.*,
         				 java.util.*,
         				 com.awsd.security.*,
         				 com.esdnl.util.*,
         				 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>
         
<!-- LOAD JAVA TAG LIBRARIES -->
		
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
<%
	JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
%>
<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
	
<script>
	$("#loadingSpinner").css("display","none");
</script>
	
</head>
<body>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Competition # <%=job.getCompetitionNumber()%> Short List Audit</b> (Total Shortlisted Applicants:${ fn:length(auditlist)})</div>
      			 	<div class="panel-body">
									<%if(request.getAttribute("msg")!=null){%>                                  	
                                  	<div class="alert alert-danger">                                    	
                                      	<%=(String)request.getAttribute("msg")%>
                                     </div>
                                  <%}%>

  
                    <div class="table-responsive">
                    	<table id="jobsapp" class="table table-condensed" style="font-size:11px;"> 
						<c:choose>
							<c:when test="${ fn:length(auditlist) > 0}">
								<c:forEach var="entry" items="${auditlist}">
									<tr><td style="background-color:#D3D3D3;"><h5><b>${entry.key} (${entry.value.applicantEmail})</b></h5></td><tr>
									<c:choose>
										<c:when test="${entry.value.jasId gt 0}">
											<c:choose>
												<c:when test="${entry.value.shortlistedReason ne null}">
													<tr><td>${entry.value.shortlistedReason}</td></tr>
												</c:when>
												<c:otherwise>
													<tr><td>
													    <table class="table table-condensed"
															style="font-size: 11px; background-color: #00000;">
    														<tr>
    															<td width="20%">Permanent NLESD Contract?</td>
    															<td width="20%">
    																<c:choose>
    																	<c:when test="${entry.value.permanentContract eq 'Y' }">
    																		Yes
    																	</c:when>
    																	<c:when test="${entry.value.permanentContract eq 'N' }">
    																		No
    																	</c:when>
    																	<c:otherwise>
    																		Any
    																	</c:otherwise>
    																</c:choose>
    															</td>
    															<td width="20%"># Math Courses:</td>
    															<td width="20%">
    																${entry.value.mathCourses }
    															</td>
    															<td width="20%">
    																Degree(s): ${entry.value.degreeList }
    															</td>
    														</tr>
    														<tr>
				    											<td width="20%">Permanent Exp (Months):</td>
				    											<td width="20%">
				    												${entry.value.permanentExp }
				    											</td>
				    											<td width="20%"># English Courses:</td>
				    											<td width="20%">
				    												${entry.value.englishCourses }
				    											</td>
				    											<td width="20%">
				    												Major Subject Group(s): ${entry.value.majorSubjectGroupList } 
				    											</td>
    														</tr>
    														<tr>
    															<td width="20%">Replacement Exp (Months):</td>
    															<td width="20%">
    																${entry.value.replacementExp }
    															</td>
    															<td width="20%"># Music Courses:</td>
    															<td width="20%">
    																${entry.value.musicCourses }
    															</td>
    															<td width="20%">
    																Major(s): ${entry.value.majorList }
    															</td>
    														</tr>
    														<tr>
    															<td width="20%">Repl + Perm (Months):</td>
    															<td width="20%">
    																${entry.value.totalExp }
    															</td>
    															<td width="20%"># Technology Courses:</td>
    															<td width="20%">
    																${entry.value.technologyCourses }
    															</td>
    															<td width="20%">
    																Minor Subject Group(s): ${entry.value.minorSubjectGroupList } 
    															</td>
    														</tr>
    														<tr>
    															<td width="20%"># Sub Days:</td>
    															<td width="20%">
    																${entry.value.subDays }
    															</td>
    															<td width="20%"># Science Courses:</td>
    															<td width="20%">
    																${entry.value.scienceCourses }
    															</td>
    															<td width="20%">
    																Minors(s): ${entry.value.minorList }
    															</td>
    														</tr>
    														<tr>
	    														<td width="20%">TLA Courses > 20 / CEC L2:</td>
	    														<td width="20%">
	    															<c:choose>
	    																<c:when test="${entry.value.isTLARequirements()}">
	    																	Yes
	    																</c:when>
	    																<c:otherwise>
	    																	No
	    																</c:otherwise>
	    															</c:choose>
	    														</td>
	    														<td width="20%"># Social Studies Courses:</td>
	    														<td width="20%">
	    															${entry.value.socialStudiesCourses }
	    														</td>
	    														<td width="20%" rowspan='3'>
	    															Regional Preferences: ${entry.value.regionalPreferenceList}
	    														</td>
    														</tr>
    														<tr>
    															<td width="20%"># Special Ed Courses:</td>
    															<td width="20%">
    																${entry.value.specialEducationCourses}
    															</td>
    															<td width="20%"># Art Courses:</td>
    															<td width="20%">
    																${entry.value.artCourses }
    															</td>
    														</tr>
    														<tr>
    															<td width="20%"># French Courses:</td>
    															<td width="20%">
    																${entry.value.frenchCourses}
    															</td>
    															<td width="20%">Level Of Prof Training:</td>
    															<td width="20%">
    																${entry.value.professionalTrainingLevel }
    															</td>
    														</tr>
    														</table>
    													</td></tr>
												</c:otherwise>											
											</c:choose>
										</c:when>
										<c:otherwise>
											<tr><td>No audit information for applicant</td><tr>
										</c:otherwise>
									</c:choose>
  								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr><td>No audit information for shortlist</td><tr>
							</c:otherwise>
						</c:choose>
						</table>
</div></div></div></div>     

		<br/><br/>                             

</body>
</html>
