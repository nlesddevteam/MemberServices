<%@ page language="java"
	import="java.util.*,
                  java.text.*,
                  com.awsd.school.*,
                  com.awsd.school.bean.*,
                  com.esdnl.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*"
	isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>

<esd:SecurityCheck
	permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<esd:SecurityRequiredPageObjectsCheck names='<%=new String[]{ "JOB", "JOB_SHORTLIST" }%>'
	scope='<%=PageContext.SESSION_SCOPE%>'
	redirectTo="/Personnel/admin_index.jsp" />

<%
	JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
	ApplicantProfileBean[] applicants = null;

	if (session.getAttribute("JOB_SHORTLIST") instanceof ApplicantProfileBean[])
		applicants = (ApplicantProfileBean[]) session.getAttribute("JOB_SHORTLIST");
	else if (session.getAttribute("JOB_SHORTLIST") instanceof TreeMap) {
		TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>> regions = (TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>>) session
				.getAttribute("JOB_SHORTLIST");

		TreeSet<ApplicantProfileBean> appls = new TreeSet<ApplicantProfileBean>();

		for (Map.Entry<String, TreeMap<Integer, Vector<ApplicantProfileBean>>> r : regions.entrySet()) {
			for (Map.Entry<Integer, Vector<ApplicantProfileBean>> d : r.getValue().entrySet()) {
				appls.addAll(d.getValue());
			}
		}

		applicants = appls.toArray(new ApplicantProfileBean[0]);
	}

	SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

	ApplicantEducationBean[] edu = null;
	ApplicantEducationOtherBean edu_oth = null;
	ApplicantEsdReplacementExperienceBean[] rpl = null;
	ApplicantSubstituteTeachingExpBean[] sub = null;
	ApplicantExperienceOtherBean[] exp_other = null;
	ApplicantSupervisorBean[] refs = null;
	ApplicantOtherInformationBean other_info = null;
	ApplicantEsdExperienceBean esd_exp = null;
	RegionBean[] regionPrefs = null;
	ApplicantNLESDPermanentExperienceBean[] per = null;
%>
<html>
<head>
<title>Printable Shortlist</title>
<style>
@media print {
	.content {
		font-family: verdana, sans-serif;
		font-size: 9.5px;
		margin: auto;
		width: 650px;
		min-height: 675px;
	}
	.mainFooter {
		display: none;
	}
	#noPrintThis2 {
		display: none;
	}
	#noPrintThis {
		display: none;
	}
	#empTable1 {
		font-size: 9.5px;
	}
	table {
		page-break-inside: auto;
		border: none !important;
	}
	tr {
		page-break-inside: avoid;
		page-break-after: auto;
		border: none !important;
	}
	td {
		border: none !important;
	}
}

.pageBreak {
	page-break-after: always;
}

.tableTitle {
	font-weight: bold;
	width: 15%;
}

.tableResult {
	font-weight: normal;
	width: 85%;
}

.tableQuestion {
	font-weight: normal;
	width: 70%;
}

.tableAnswer {
	font-weight: normal;
	width: 30%;
}

.tableTitleL {
	font-weight: bold;
	width: 15%;
}

.tableResultL {
	font-weight: normal;
	width: 35%;
}

.tableTitleR {
	font-weight: bold;
	width: 15%;
}

.tableResultR {
	font-weight: normal;
	width: 35%;
}

input {
	border: 1px solid silver;
}
</style>

</head>
<body>

	<%
		if (applicants.length > 0) {
	%>
	<div align="center">
		<img src="includes/img/nlesd-colorlogo.png" width="400">
	</div>
	<br />
	<br />
	<%
		for (int j = 0; j < applicants.length; j++) {
				edu = ApplicantEducationManager.getApplicantEducationBeans(applicants[j].getSIN());
				edu_oth = ApplicantEducationOtherManager.getApplicantEducationOtherBean(applicants[j].getSIN());
				rpl = ApplicantEsdReplExpManager.getApplicantEsdReplacementExperienceBeans(applicants[j].getSIN());
				sub = ApplicantSubExpManager.getApplicantSubstituteTeachingExpBeans(applicants[j].getSIN());
				exp_other = ApplicantExpOtherManager.getApplicantExperienceOtherBeans(applicants[j].getSIN());
				refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(applicants[j].getSIN());
				esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(applicants[j].getSIN());
				other_info = ApplicantOtherInfoManager.getApplicantOtherInformationBean(applicants[j].getSIN());
				regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(applicants[j])
						.values().toArray(new RegionBean[0]);
				per = ApplicantNLESDPermExpManager
						.getApplicantNLESDPermanentExperienceBeans(applicants[j].getSIN());
	%>







	<!-- APPLICANT PROFILE-->
	<div align="center">
		<span style="font-size: 18px; font-weight: bold;"><%=applicants[j].getFullNameReverse()%></span><br />
		<span style="font-size: 14px; font-weight: bold;"><%=job.getCompetitionNumber()%></span>
		- <span style="font-size: 14px; font-weight: bold;"><%=job.getPositionTitle()%></span>
	</div>

	<table class="table table-condensed" style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=4
					style="font-weight: bold; border-top: 1px solid black;">APPLICANT
					PROFILE</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="tableTitle" colspan=1>NAME:</td>
				<td class="tableResult" colspan=3><%=applicants[j].getFirstname() + " "
							+ ((applicants[j].getMiddlename() != null) ? applicants[j].getMiddlename() + " " : "")
							+ applicants[j].getSurname()
							+ ((applicants[j].getMaidenname() != null)
									? " (" + applicants[j].getMaidenname() + ")"
									: "")%></td>
			</tr>
			<tr>
				<td class="tableTitle" colspan=1>ADDRESS:</td>
				<td class="tableResult" colspan=3><%=applicants[j].getAddress1()%>
					<%=(applicants[j].getAddress2() != null) ? " &middot; " + applicants[j].getAddress2() : ""%>
					, <%=applicants[j].getProvince() + " &middot: " + applicants[j].getCountry() + " &middot; "
							+ applicants[j].getPostalcode()%></td>
			</tr>
			<tr>
				<td class="tableTitle" colspan=1>TELEPHONE:</td>
				<td class="tableResult" colspan=3>(Res) <%=(applicants[j].getHomephone() != null) ? applicants[j].getHomephone() : "N/A"%>
					&middot; (Work) <%=(applicants[j].getWorkphone() != null) ? applicants[j].getWorkphone() : "N/A"%>
					&middot; (Cell) <%=(applicants[j].getCellphone() != null) ? applicants[j].getCellphone() : "N/A"%>
				</td>
			</tr>
			<tr>
				<td class="tableTitle" colspan=1>EMAIL:</td>
				<td class="tableResult" colspan=3><%=applicants[j].getEmail()%></td>
			</tr>
			<%
				if ((esd_exp != null) && (esd_exp.getPermanentContractSchool() != 0)
								&& (esd_exp.getPermanentContractSchool() != -1)) {
			%>
			<tr>
				<td class="tableTitleL">Perm. Contract School</td>
				<td class="tableResultL"><%=esd_exp.getPermanentContractLocationText()%></td>
				<td class="tableTitleR">Perm. Contract Position</td>
				<td class="tableResultR"><%=esd_exp.getPermanentContractPosition()%></td>
			</tr>
			<%
				}
			%>
			<%
				if ((esd_exp != null) && (esd_exp.getContractSchool() != 0)
								&& (esd_exp.getContractSchool() != -1)) {
			%>
			<tr>
				<td class="tableTitleL">Rep. Contract School</td>
				<td class="tableResultL"><%=esd_exp.getReplacementContractLocationText()%></td>
				<td class="tableTitleR">Rep. Contract End Date</td>
				<td class="tableResultR"><%=esd_exp.getFormattedContractEndDate()%></td>
			</tr>
			<%
				}
			%>

		</tbody>
	</table>

	<!-- EDUCATION-->

	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=6
					style="font-weight: bold; border-top: 1px solid black;">UNIVERSITY/COLLEGE
					EDUCATION</th>
			</tr>
		</thead>
		<tbody>
			<%
				if ((edu != null) && (edu.length > 0)) {
			%>

			<tr style="font-weight: bold;">
				<td width='20%'>INSTITUTION</td>
				<td width='10%'>DATES (M/Y)</td>
				<td width='25%'>PROGRAM &amp; FACULITY</td>
				<td width='17%'>MAJOR (#crs)</td>
				<td width='17%'>MINOR (#crs)</td>
				<td width='11%'>DEGREE CONF.</td>
			</tr>
			<%
				for (int i = 0; i < edu.length; i++) {
			%>
			<c:set var="edu" value="<%=edu[i]%>" />
			<tr>
				<td>${edu.institutionName}</td>
				<td><fmt:formatDate pattern="MM/yy" value="${edu.from}" /> - <fmt:formatDate
						pattern="MM/yy" value="${edu.to}" /></td>
				<td>${edu.programFacultyName}</td>
				<td><c:choose>
						<c:when test="${edu.major ne '-1' }">
							<%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName()%> (${edu.numberMajorCourses})
								                                   </c:when>
						<c:otherwise>N/A</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when test="${edu.minor ne '-1' }">
							<%=SubjectDB.getSubject(edu[i].getMinor()).getSubjectName()%> (${edu.numberMinorCourses})
							                                   		</c:when>
						<c:otherwise>N/A</c:otherwise>
					</c:choose></td>
				<td><%=((!StringUtils.isEmpty(edu[i].getDegreeConferred()))
									? DegreeManager.getDegreeBeans(edu[i].getDegreeConferred()).getAbbreviation()
									: "&nbsp;")%></td>
			</tr>
			<%
				}
			%>
			<%
				} else {
			%>
			<tr>
				<th colspan=6><span style="color: Grey;">No post
						secondary education currently on file.</span></th>
				<%
					}
				%>
			
		</tbody>
	</table>


	<!-- OTHER EDUCATION -->
	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=4
					style="font-weight: bold; border-top: 1px solid black;">OTHER
					EDUCATION</th>
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
						<td class="tableTitle">Math Courses:</td>
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
						<td class="tableResult"><c:choose>
								<c:when test="${eduOther.totalCoursesCompleted le '0' }">
									<!-- <span style="color:red;">0 - PROFILE INCOMPLETE</span>-->
								    ${eduOther.totalCoursesCompleted }
								    </c:when>
								<c:otherwise>${eduOther.totalCoursesCompleted }</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<td class="tableTitle">Certification Level:</td>
						<td class="tableResult"><%=!StringUtils.isEmpty(edu_oth.getTeachingCertificateLevel())
									? edu_oth.getTeachingCertificateLevel()
									: "N/A"%></td>
						<td class="tableTitle">Certification Issue Date:</td>
						<td class="tableResult"><%=((edu_oth.getTeachingCertificateIssuedDate() != null)
									? sdf.format(edu_oth.getTeachingCertificateIssuedDate())
									: "N/A")%></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td colpsan=4><span style="color: Grey;">No other
								education currently on file.</span></td>
					</tr>
				</c:otherwise>
			</c:choose>


		</tbody>
	</table>


	<!-- PERMANENT EXPERIENCE -->
	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=4
					style="font-weight: bold; border-top: 1px solid black;">NLESD
					PERMANENT EXPERIENCE (Total Months: <%=((esd_exp != null) ? Integer.toString(esd_exp.getPermanentLTime()) : "UNKNOWN")%>)
				</th>
			</tr>
		</thead>
		<tbody>



			<%
				if ((per != null) && (per.length > 0)) {
			%>
			<tr style="font-weight: bold;">
				<td width='20%'>FROM</td>
				<td width='20%'>TO</td>
				<td width='20%'>SCHOOL</td>
				<td width='40%'>GRADES AND/OR SUBJECTS TAUGHT</td>
			</tr>
			<%
				for (int i = 0; i < per.length; i++) {
			%>
			<tr>
				<td><%=sdf.format(per[i].getFrom())%></td>
				<td><%=sdf.format(per[i].getTo())%></td>
				<td><%=SchoolDB.getSchool(per[i].getSchoolId()).getSchoolName()%></td>
				<td><%=per[i].getGradesSubjects()%></td>
			</tr>
			<%
				}
			%>

			<%
				} else {
			%>

			<tr>
				<td colspan=4><span style="color: Grey;">No NLESD
						Permanent Experience currently on file.</span></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>



	<!-- REPLACEMENT EXPERIENCE -->
	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=4
					style="font-weight: bold; border-top: 1px solid black;">NLESD
					REPLACEMENT CONTRACT EXPERIENCE (Total Months: <%=((esd_exp != null) ? Integer.toString(esd_exp.getReplacementTime()) : "UNKNOWN")%>)
				</th>
			</tr>
		</thead>
		<tbody>
			<%
				if ((rpl != null) && (rpl.length > 0)) {
			%>

			<tr style="font-weight: bold;">
				<td width='20%'>FROM</td>
				<td width='20%'>TO</td>
				<td width='20%'>SCHOOL</td>
				<td width='40%'>GRADES AND/OR SUBJECTS TAUGHT</td>
			</tr>

			<%
				for (int i = 0; i < rpl.length; i++) {
			%>
			<tr>
				<td><%=sdf.format(rpl[i].getFrom())%></td>
				<td><%=sdf.format(rpl[i].getTo())%></td>
				<td><%=SchoolDB.getSchool(rpl[i].getSchoolId()).getSchoolName()%></td>
				<td><%=rpl[i].getGradesSubjects()%></td>
			</tr>
			<%
				}
			%>
			<%
				} else {
			%>
			<tr>
				<td colspan=4><span style="color: Grey;">No Replacement
						Contract Experience currently on file.</span></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>

	<!-- NLESD SUB EXPERIENCE -->
	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=4
					style="font-weight: bold; border-top: 1px solid black;">NLESD
					SUBSTITUTE TEACHING EXPERIENCE (Total Sub Days: <%=((esd_exp != null) ? Integer.toString(esd_exp.getSubstituteTime()) : "UNKNOWN")%>)
				</th>
			</tr>
		</thead>
		<tbody>
			<%
				if ((sub != null) && (sub.length > 0)) {
			%>

			<tr style="font-weight: bold;">
				<td width='30%'>FROM</td>
				<td width='30%'>TO</td>
				<td width='40%'># DAYS PER YEAR</td>
			</tr>

			<%
				for (int i = 0; i < sub.length; i++) {
			%>
			<tr>
				<td><%=sdf.format(sub[i].getFrom())%></td>
				<td><%=sdf.format(sub[i].getTo())%></td>
				<td><%=sub[i].getNumDays()%></td>
			</tr>
			<%
				}
			%>

			<%
				} else {
			%>
			<tr>
				<td colspan=4><span style="color: Grey;">No Substitute
						Experience currently on file.</span></td>
			</tr>

			<%
				}
			%>
		</tbody>
	</table>
	<!-- OTHER BOARD EXPERIENCE --------------------------------------------------------------->


	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=4
					style="font-weight: bold; border-top: 1px solid black;">OTHER
					BOARD EXPERIENCE</th>
			</tr>
		</thead>
		<tbody>


			<%
				if ((exp_other != null) && (exp_other.length > 0)) {
			%>

			<tr style="font-weight: bold;">
				<td width='15%'>FROM</td>
				<td width='15%'>TO</td>
				<td width='30%'>SCHOOL &amp; BOARD</td>
				<td width='40%'>GRADES AND/OR SUBJECTS TAUGHT</td>
			</tr>

			<%
				for (int i = 0; i < exp_other.length; i++) {
			%>
			<tr>
				<td><%=sdf.format(exp_other[i].getFrom())%></td>
				<td><%=sdf.format(exp_other[i].getTo())%></td>
				<td><%=exp_other[i].getSchoolAndBoard()%></td>
				<td><%=exp_other[i].getGradesSubjects()%></td>
			</tr>
			<%
				}
			%>

			<%
				} else {
			%>

			<tr>
				<td colspan=4><span style="color: Grey;">No Other Board
						Experience currently on file.</span></td>
			</tr>
			<%
				}
			%>

		</tbody>
	</table>

	<!-- OTHER INFORMATION -->
	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th style="font-weight: bold; border-top: 1px solid black;">OTHER
					INFORMATION</th>
			</tr>
		</thead>
		<tbody>

			<%
				if ((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())) {
			%>
			<tr>
				<td><%=other_info%></td>
			</tr>
			<%
				} else {
			%>
			<tr>
				<td colspan=4><span style="color: Grey;">No Other
						Information currently on file.</span></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>


	<!-- REFERENCES -->
	<table class="table table-striped table-condensed"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=4
					style="font-weight: bold; border-top: 1px solid black;">REFERENCES</th>
			</tr>
		</thead>
		<tbody>

			<%
				if ((refs != null) && (refs.length > 0)) {
			%>


			<tr style="font-weight: bold;">
				<td width='20%'>FULL NAME</td>
				<td width='20%'>TITLE</td>
				<td width='45%'>PRESENT ADDRESS</td>
				<td width='15%'>TELEPHONE</td>
			</tr>



			<%
				for (int i = 0; i < refs.length; i++) {
			%>
			<tr>
				<td><%=refs[i].getName()%></td>
				<td><%=refs[i].getTitle()%></td>
				<td><%=refs[i].getAddress()%></td>
				<td><%=refs[i].getTelephone()%></td>
			</tr>
			<%
				}
			%>

			<%
				} else {
			%>
			<tr>
				<td colspan=4><span style="color: Grey;">No References
						currently on file.</span></td>
			</tr>

			<%
				}
			%>

		</tbody>
	</table>


	<!-- REGIONAL REFERENCES --------------------------------------------------------------->

	<table class="table table-striped table-condensed" id="regPrefs"
		style="font-size: 10px;">
		<thead>
			<tr>
				<th colspan=2
					style="font-weight: bold; border-top: 1px solid black;">REGIONAL
					PREFERENCES</th>
			</tr>
		</thead>
		<tbody>

			<%
				if ((regionPrefs != null) && (regionPrefs.length > 0)) {
			%>

			<tr style="font-weight: bold;">
				<td width="45%">REGION</td>
				<td width="45%">REGIONAL ZONE</td>
			</tr>


			<%
				for (int i = 0; i < regionPrefs.length; i++) {
			%>

			<tr>
				<td><c:set var="whatRegion"
						value="<%=regionPrefs[i].getZone()%>" /> <c:set var="whatZone"
						value="<%=regionPrefs[i].getName()%>" /> <c:choose>
						<c:when
							test="${whatRegion eq 'avalon' or whatRegion eq 'eastern' }">Avalon Region</c:when>
						<c:when test="${whatRegion eq 'central'}">Central Region</c:when>
						<c:when test="${whatRegion eq 'western'}">Western Region</c:when>
						<c:when test="${whatRegion eq 'labrador'}">Labrador Region</c:when>
						<c:when test="${whatRegion eq 'provincial'}">Provincial</c:when>
						<c:otherwise>
							<span style="color: Red;">ERROR: Unknown Region</span>
						</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when
							test="${whatZone eq 'nlesd - provincal' or whatZone eq 'nlesd - provincial'}">All Province</c:when>
						<c:when test="${whatZone eq 'all labrador region'}">All Labrador</c:when>
						<c:when test="${whatZone eq 'all western region'}">All Western Zone</c:when>
						<c:when test="${whatZone eq 'all central region'}">All Central Zone</c:when>
						<c:when
							test="${whatZone eq 'all eastern region' or whatZone eq 'all avalon region'}">All Avalon Zone</c:when>
						<c:otherwise>
							<span style="text-transform: Capitalize;"><%=regionPrefs[i].getName()%>
								Regional Zone</span>
						</c:otherwise>
					</c:choose></td>
			</tr>
			<%
				}
			%>


			<%
				} else {
			%>
			<tr>
				<td colspan=2><span style="color: Grey;">No References
						currently on file.</span></td>
			</tr>
			<%
				}
			%>

		</tbody>
	</table>

	<div class='pageBreak'></div>

	<%
		}
	%>

	<script>
		$('document').ready(function() {
			window.print();
		});
	</script>
	<%
		}
		else {
	%>
	<br />
	<br />
	<div class="alert alert-danger" align="center">Sorry, no
		applicants currently shortlisted to print.</div>
	<br />
	<br />
	<%
		}
	%>
</body>
</html>
