<%@ page language="java"
	import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*"
	isThreadSafe="false"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>

<esd:SecurityCheck
	permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<esd:SecurityRequiredPageObjectsCheck names='<%=new String[]{ "REFERENCE_BEAN", "PROFILE" }%>'
	scope='<%=PageContext.REQUEST_SCOPE%>'
	redirectTo="/Personnel/admin_index.jsp" />
	
<%
	NLESDReferenceTeacherBean ref = (NLESDReferenceTeacherBean) request.getAttribute("REFERENCE_BEAN");
	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
	
	String val1 = "1";
	String val2 = "2";
	String val3 = "3";
	String val4 = "4";
	String val5 = "-1";
	String val5Text = "N/A";
	String refscale = "5";
	if (!(ref == null)) {
		refscale = ref.getReferenceScale();
	}
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display", "none");
</script>
<style>
.tableTitle {
	font-weight: bold;
	width: 20%;
}

.tableResult {
	font-weight: normal;
	width: 80%;
}

.tableQuestionNum {
	font-weight: bold;
	width: 5%;
}

.tableQuestion {
	width: 95%;
}

.tableAnswer {
	font-style: italic;
	color: Green;
}

.ratingQuestionNum {
	font-weight: bold;
	width: 5%;
}

.ratingQuestion {
	width: 75%;
}

.ratingAnswer {
	width: 20%;
}

input[type="radio"] {
	margin-top: -1px;
	margin-left: 6px;
	margin-right: 2px;
	vertical-align: middle;
}
</style>

</head>
<body>
	<br />
	<%
		if (!StringUtils.isEmpty((String) request.getAttribute("msg"))) {
	%>
	<div class="alert alert-danger" style="text-align: center;"><%=(String) request.getAttribute("msg")%></div>
	<%
		}
	%>
	<div class="panel panel-success">
		<div class="panel-heading">
			Teacher Candidate Reference Check for <b><%=profile.getFullNameReverse()%>.</b>
		</div>
		<div class="panel-body">

			<%
				int totalRates = 0;
				int totalScore = 0;
				totalRates = ref.getPossibleTotal() * 4;
				totalScore = ref.getTotalScore().intValue();
				double resultScore = 0;
				if (totalRates > 0) {

					resultScore = 100 * totalScore / totalRates;
				}
			%>



			<div class="alert alert-info">
				The total score for
				<%=profile.getFullNameReverse()%>
				on this reference from
				<%=ref.getProvidedBy()%>
				is
				<%=resultScore%>%. (<%=ref.getTotalScore()%>
				out of a possible
				<%=totalRates%>).
			</div>


			<div class="table-responsive">

				<table class='table table-striped table-condensed'
					style='font-size: 12px;'>
					<tbody>
						<tr>
							<td class="tableTitle">Candidates Name:</td>
							<td class="tableResult"><%=profile.getFullName()%></td>
						</tr>
						<tr>
							<td class="tableTitle">Person providing reference:</td>
							<td class="tableResult"><%=ref.getProvidedBy()%></td>
						</tr>
						<tr>
							<td class="tableTitle">Position:</td>
							<td class="tableResult"><%=ref.getProvidedByPosition()%></td>
						</tr>
						<tr>
							<td class="tableTitle">Date Provided:</td>
							<td class="tableResult"><%=ref.getDateProvided()%></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Reference Questions ------------------------------------------------------------>

	<div class="panel panel-success">
		<div class="panel-heading">Reference Questions</div>
		<div class="panel-body">
			The following reference checks should have been completed and
			attached to the teacher recommendation form.
			<div class="table-responsive">
				<table class="table table-striped table-condensed"
					style="font-size: 12px; padding-top: 3px; border-top: 1px solid silver;">
					<thead>
						<tr>
							<th class="tableQuestionNum">#</th>
							<th class="tableQuestion">QUESTION/ANSWER</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Q1.</td>
							<td>Did the candidate ask permission to use your name as a
								reference?<br />
							<span class="tableAnswer"><%=ref.getQ1()%></span>
							</td>
						</tr>
						<tr>
							<td>Q2.</td>
							<td>How long have you known this teacher?<br />
							<span class="tableAnswer"><%=ref.getQ2()%></span></td>
						</tr>
						<tr>
							<td>Q3.</td>
							<td>How long have they worked in your school?<br />
							<span class="tableAnswer"><%=ref.getQ3()%></span></td>
						</tr>
						<tr>
							<td>Q4.</td>
							<td>What has been their most recent assignment?<br />
							<span class="tableAnswer"><%=ref.getQ4()%></span></td>
						</tr>
						<tr>
							<td>Q5.</td>
							<td>Did this teacher complete a professional learning plan?<br />
							<span class="tableAnswer"><%=ref.getQ5()%></span></td>
						</tr>
						<tr>
							<td>Q6.</td>
							<td>Was the learning plan successfully followed and
								completed?<br />
							<span class="tableAnswer"><%=ref.getQ6()%></span>
							</td>
						</tr>

					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Domain One ------------------------------------------------------------------------------------->

	<div class="panel panel-success">
		<div class="panel-heading">Domain 1: Planning and Preparation</div>
		<div class="panel-body">
			<%
				if (refscale.equals("4") || refscale.equals("5")) {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val4%>
			please rate the teacher on the following statements (<%=val4%>-Proficient,
			<%=val3%>-Competent,
			<%=val2%>-Developing Competence and
			<%=val1%>-Needs Improvement)

			<%
				}
				else {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val5%>
			please rate the teacher on the following statements (<%=val5%>-Proficient,
			<%=val4%>-Competent,
			<%=val3%>-Developing Competence,
			<%=val2%>-Needs Improvement and
			<%=val1%>-N/A)

			<%
				}
			%>
			<div align="left" style="padding-top: 5px;">
				<a href="includes/rubric.pdf" class="btn btn-xs btn-default"
					target="_blank">View Scoring Rubric</a>
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-condensed"
					style="margin-top: 10px; font-size: 12px; padding-top: 3px; border-top: 1px solid silver;">
					<thead>
						<tr>
							<th class="ratingQuestionNum">#</th>
							<th class="ratingQuestion">STATEMENT</th>
							<th class="ratingAnswer">RATING</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>S1.</td>
							<td>This teacher demonstrates knowledge of content and
								pedagogy:</td>
							<td class="tableAnswer"><%=ref.getScale1().equals("-1") ? "N/A" : ref.getScale1()%></td>
						</tr>
						<tr>
							<td>S2.</td>
							<td>This teacher demonstrates knowledge of students:</td>
							<td class="tableAnswer"><%=ref.getScale2().equals("-1") ? "N/A" : ref.getScale2()%></td>
						</tr>
						<tr>
							<td>S3.</td>
							<td>This teacher selects instructional goals:</td>
							<td class="tableAnswer"><%=ref.getScale3().equals("-1") ? "N/A" : ref.getScale3()%></td>
						</tr>
						<tr>
							<td>S4.</td>
							<td>This teacher demonstrates knowledge of resources:</td>
							<td class="tableAnswer"><%=ref.getScale4().equals("-1") ? "N/A" : ref.getScale4()%></td>
						</tr>
						<tr>
							<td>S5.</td>
							<td>This teacher designs coherent instruction:</td>
							<td class="tableAnswer"><%=ref.getScale5().equals("-1") ? "N/A" : ref.getScale5()%></td>
						</tr>
						<tr>
							<td>S6.</td>
							<td>This teacher designs appropriate student assessment:</td>
							<td class="tableAnswer"><%=ref.getScale6().equals("-1") ? "N/A" : ref.getScale6()%></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td colspan=2>Additional Comments:<br /> <span
								class="tableAnswer"><%=(!StringUtils.isEmpty(ref.getDomain1Comments()) ? ref.getDomain1Comments() : "none")%></span>
							</td>

						</tr>

					</tbody>
				</table>





			</div>
		</div>
	</div>

	<!-- Domain Two ------------------------------------------------------------------------------------->

	<div class="panel panel-success">
		<div class="panel-heading">Domain 2: The Classroom Environment</div>
		<div class="panel-body">
			<%
				if (refscale.equals("4") || refscale.equals("5")) {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val4%>
			please rate the teacher on the following statements (<%=val4%>-Proficient,
			<%=val3%>-Competent,
			<%=val2%>-Developing Competence and
			<%=val1%>-Needs Improvement)

			<%
				}
				else {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val5%>
			please rate the teacher on the following statements (<%=val5%>-Proficient,
			<%=val4%>-Competent,
			<%=val3%>-Developing Competence,
			<%=val2%>-Needs Improvement and
			<%=val1%>-N/A)
			<%
				}
			%>
			<div align="left" style="padding-top: 5px;">
				<a href="includes/rubric.pdf" class="btn btn-xs btn-default"
					target="_blank">View Scoring Rubric</a>
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-condensed"
					style="margin-top: 10px; font-size: 12px; padding-top: 3px; border-top: 1px solid silver;">
					<thead>
						<tr>
							<th class="ratingQuestionNum">#</th>
							<th class="ratingQuestion">STATEMENT</th>
							<th class="ratingAnswer">RATING</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>S7.</td>
							<td>This teacher creates an environment of respect and
								rapport:</td>
							<td class="tableAnswer"><%=ref.getScale7().equals("-1") ? "N/A" : ref.getScale7()%></td>
						</tr>
						<tr>
							<td>S8.</td>
							<td>This teacher establishes a culture for learning:</td>
							<td class="tableAnswer"><%=ref.getScale8().equals("-1") ? "N/A" : ref.getScale8()%></td>
						</tr>
						<tr>
							<td>S9.</td>
							<td>This teacher demonstrates appropriate classroom
								procedures:</td>
							<td class="tableAnswer"><%=ref.getScale9().equals("-1") ? "N/A" : ref.getScale9()%></td>
						</tr>
						<tr>
							<td>S10.</td>
							<td>This teacher manages student behavior:</td>
							<td class="tableAnswer"><%=ref.getScale10().equals("-1") ? "N/A" : ref.getScale10()%></td>
						</tr>
						<tr>
							<td>S11.</td>
							<td>This teacher organizes physical space to meet the needs
								of individual learners:</td>
							<td class="tableAnswer"><%=ref.getScale11().equals("-1") ? "N/A" : ref.getScale11()%></td>
						</tr>

						<tr>
							<td>&nbsp;</td>
							<td colspan=2>Additional Comments:<br /> <span
								class="tableAnswer"><%=(!StringUtils.isEmpty(ref.getDomain2Comments()) ? ref.getDomain2Comments() : "none")%></span>
							</td>

						</tr>

					</tbody>
				</table>





			</div>
		</div>
	</div>


	<!-- Domain Three ------------------------------------------------------------------------------------->

	<div class="panel panel-success">
		<div class="panel-heading">Domain 3: Instruction</div>
		<div class="panel-body">
			<%
				if (refscale.equals("4") || refscale.equals("5")) {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val4%>
			please rate the teacher on the following statements (<%=val4%>-Proficient,
			<%=val3%>-Competent,
			<%=val2%>-Developing Competence and
			<%=val1%>-Needs Improvement)

			<%
				}
				else {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val5%>
			please rate the teacher on the following statements (<%=val5%>-Proficient,
			<%=val4%>-Competent,
			<%=val3%>-Developing Competence,
			<%=val2%>-Needs Improvement and
			<%=val1%>-N/A)

			<%
				}
			%>
			<div align="left" style="padding-top: 5px;">
				<a href="includes/rubric.pdf" class="btn btn-xs btn-default"
					target="_blank">View Scoring Rubric</a>
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-condensed"
					style="margin-top: 10px; font-size: 12px; padding-top: 3px; border-top: 1px solid silver;">
					<thead>
						<tr>
							<th class="ratingQuestionNum">#</th>
							<th class="ratingQuestion">STATEMENT</th>
							<th class="ratingAnswer">RATING</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>S12.</td>
							<td>This teacher communicates clearly and accurately:</td>
							<td class="tableAnswer"><%=ref.getScale12().equals("-1") ? "N/A" : ref.getScale12()%></td>
						</tr>
						<tr>
							<td>S13.</td>
							<td>This teacher uses questioning and discussion techniques:</td>
							<td class="tableAnswer"><%=ref.getScale13().equals("-1") ? "N/A" : ref.getScale13()%></td>
						</tr>
						<tr>
							<td>S14.</td>
							<td>This teacher engages students in learning:</td>
							<td class="tableAnswer"><%=ref.getScale14().equals("-1") ? "N/A" : ref.getScale14()%></td>
						</tr>
						<tr>
							<td>S15.</td>
							<td>This teacher demonstrates and utilizes appropriate
								formative accessment strategies:</td>
							<td class="tableAnswer"><%=ref.getScale15().equals("-1") ? "N/A" : ref.getScale15()%></td>
						</tr>
						<tr>
							<td>S16.</td>
							<td>This teacher demonstrates flexibility and
								responsiveness:</td>
							<td class="tableAnswer"><%=ref.getScale16().equals("-1") ? "N/A" : ref.getScale16()%></td>
						</tr>

						<tr>
							<td>&nbsp;</td>
							<td colspan=2>Additional Comments:<br /> <span
								class="tableAnswer"><%=(!StringUtils.isEmpty(ref.getDomain3Comments()) ? ref.getDomain3Comments() : "none")%></span>
							</td>

						</tr>

					</tbody>
				</table>





			</div>
		</div>
	</div>


	<!-- Domain Four ------------------------------------------------------------------------------------->

	<div class="panel panel-success">
		<div class="panel-heading">Domain 4: Professional
			Responsibilities</div>
		<div class="panel-body">
			<%
				if (refscale.equals("4") || refscale.equals("5")) {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val4%>
			please rate the teacher on the following statements (<%=val4%>-Proficient,
			<%=val3%>-Competent,
			<%=val2%>-Developing Competence and
			<%=val1%>-Needs Improvement)
			<%
				}
				else {
			%>
			On a scale of
			<%=val1%>
			to
			<%=val5%>
			please rate the teacher on the following statements (<%=val5%>-Proficient,
			<%=val4%>-Competent,
			<%=val3%>-Developing Competence,
			<%=val2%>-Needs Improvement and
			<%=val1%>-N/A)
			<%
				}
			%>
			<div align="left" style="padding-top: 5px;">
				<a href="includes/rubric.pdf" class="btn btn-xs btn-default"
					target="_blank">View Scoring Rubric</a>
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-condensed"
					style="margin-top: 10px; font-size: 12px; padding-top: 3px; border-top: 1px solid silver;">
					<thead>
						<tr>
							<th class="ratingQuestionNum">#</th>
							<th class="ratingQuestion">STATEMENT</th>
							<th class="ratingAnswer">RATING</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>S17.</td>
							<td>This teacher reflects on teaching:</td>
							<td class="tableAnswer"><%=ref.getScale17().equals("-1") ? "N/A" : ref.getScale17()%>
							</td>
						</tr>
						<tr>
							<td>S18.</td>
							<td>This teacher maintains accurate records:</td>
							<td class="tableAnswer"><%=ref.getScale18().equals("-1") ? "N/A" : ref.getScale18()%>
							</td>
						</tr>
						<tr>
							<td>S19.</td>
							<td>This teacher communicates with families and/or other
								community stakeholders:</td>
							<td class="tableAnswer"><%=ref.getScale19().equals("-1") ? "N/A" : ref.getScale19()%></td>
						</tr>
						<tr>
							<td>S20.</td>
							<td>This teacher contributes to the school and district:</td>
							<td class="tableAnswer"><%=ref.getScale20().equals("-1") ? "N/A" : ref.getScale20()%></td>
						</tr>
						<tr>
							<td>S21.</td>
							<td>This teacher grows and develops professionally:</td>
							<td class="tableAnswer"><%=ref.getScale21().equals("-1") ? "N/A" : ref.getScale21()%></td>
						</tr>
						<tr>
							<td>S22.</td>
							<td>This teacher shows professionalism:</td>
							<td class="tableAnswer"><%=ref.getScale22().equals("-1") ? "N/A" : ref.getScale22()%></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td colspan=2>Additional Comments:<br /> <span
								class="tableAnswer"><%=(!StringUtils.isEmpty(ref.getDomain4Comments()) ? ref.getDomain4Comments() : "none")%></span>
							</td>

						</tr>

					</tbody>
				</table>





			</div>
		</div>
	</div>


	<!-- Other Information ----------------------------------------------------------------------------------->

	<div class="panel panel-success">
		<div class="panel-heading">Other Information</div>
		<div class="panel-body">

			<div class="table-responsive">
				<table class="table table-striped table-condensed"
					style="margin-top: 10px; font-size: 12px; padding-top: 3px; border-top: 1px solid silver;">

					<thead>
						<tr>
							<th class="tableQuestionNum">#</th>
							<th class="tableQuestion">QUESTION/ANSWER</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Q7.</td>
							<td>If given the opportunity would you hire this candidate?<br>
								<span class="tableAnswer"><%=ref.getQ7()%></span>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>Additional Comments:<br /> <span class="tableAnswer"><%=(!StringUtils.isEmpty(ref.getQ7Comment()) ? ref.getQ7Comment() : "none")%></span>
							</td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
	</div>

	<div align="center">
		<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
	</div>

	<br />
	<br />


</body>
</html>
