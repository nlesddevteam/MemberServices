<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.awsd.security.*;"
         isThreadSafe="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>


<esd:SecurityCheck permissions="H1N1-PRINCIPAL-VIEW" />

<%
	User usr = (User) session.getAttribute("usr");
	School school = (School) request.getAttribute("SCHOOLBEAN");
%>

<html>

	<head>
		<title>Eastern School District - H1N1 District Advisory System - Principal View</title>
		<link href="includes/css/h1n1.css" rel="stylesheet" type="text/css">
		<style type="text/css">@import 'includes/css/sunny/jquery-ui-1.7.2.custom.css';</style>
		<style type="text/css">
			#add_daily_report_dialog{
				font: 80% "Trebuchet MS", sans-serif;
			}
			
			#response_msg td, #change_school_totals_response_msg td{
				font-family:Verdana,Arial,sans-serif;
				font-size: 10pt;
				border:solid 1px #eeb420; 
				color:#363636; 
				background: #fbf9ee url('includes/images/info-icon.gif') center left no-repeat;
				text-align:center;
				padding:5px 15px 5px 25px;
				text-align:center;
			}
			
			#date-selector-box{
				margin: 10px;
				/*padding: 5px 5px 5px 5px;*/
			}
		</style>
		<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="includes/js/jquery-ui-1.7.2.custom.min.js"></script>
		<script type="text/javascript">
			$('document').ready(function(){
			
				$('#monthly-report th').css('border-bottom', 'solid 1px #333333');
				$('#monthly-report th').css('border-top', 'solid 1px #333333');
				$('#monthly-report td:not(.monthly-summary-info td)').css('border', 'solid 1px #C0C0C0');
				$('#monthly-report tr.daily-report-info:odd').css('background-color', '#DFEAF6');
				$('#monthly-report tr.daily-report-info:even').css('background-color', '#FFFFFF');
				$('#monthly-report tr.daily-report-additional-comment:odd').css('background-color', '#DFEAF6');
				$('#monthly-report tr.daily-report-additional-comment:even').css('background-color', '#FFFFFF');
				
				$('#monthly-report tr.daily-report-additional-comment td').css('border-bottom', 'solid 1px #3F3F3F');
				
				$('.monthly-summary-info').css('background-color', '#FFFFFF');
				$('.monthly-summary-info td').css('border', 'none');
				$('.monthly-summary-info td').css('border-top', 'solid 1px #333333');
				$('.monthly-summary-info td').css('border-bottom', 'solid 1px #333333');
				$('.monthly-summary-info td').css('font-weight', 'bold');
				$('.monthly-summary-info td').css('color', '#FF0000');
				
				$('#parental_consent_response_dialog').dialog({
					autoOpen: false,
					bgiframe: true,
					width:500,
					height: 400,
					modal: true,
					hide: 'explode',
					buttons: {
						'close': function(){$('#parental_consent_response_dialog').dialog('close');},
						'submit': function(){submitGradeConsentData();}
					}
				});

				$('#btn_show_parental_consent_response_dialog').click(function(){
					showParentalConsentResponseDialog();
				});
				
				
			});

			function showParentalConsentResponseDialog(){
				$('#parental_consent_response_dialog').dialog('open');
			}
			
			function submitGradeConsentData(){
			
				var expr = /^\d+(.[0-9]([0-9])?)?$/; 
				
				$('#change_school_totals_response_msg').hide();
				
				if(($('#total-consented').val() == '') || !expr.test($('#total-consented').val())){
					$('#change_school_totals_response_msg > td').html('Invalid total # of consented.');
					$('#change_school_totals_response_msg > td').css('font-weight', 'bold');
					$('#change_school_totals_response_msg > td').css('color', '#FF0000');
					$('#change_school_totals_response_msg').show();
				}
				else if(($('#total-refused').val() == '') || !expr.test($('#total-refused').val())){
					$('#change_school_totals_response_msg > td').html('Invalid total # of refused.');
					$('#change_school_totals_response_msg > td').css('font-weight', 'bold');
					$('#change_school_totals_response_msg > td').css('color', '#FF0000');
					$('#change_school_totals_response_msg').show();
				}
				else if(($('#total-vaccinated').val() == '') || !expr.test($('#total-vaccinated').val())){
					$('#change_school_totals_response_msg > td').html('Invalid total # of vaccinated.');
					$('#change_school_totals_response_msg > td').css('font-weight', 'bold');
					$('#change_school_totals_response_msg > td').css('color', '#FF0000');
					$('#change_school_totals_response_msg').show();
				}
				else{
					$('#change_school_totals_response_msg > td').html('<img src="includes/images/ajax-loader.gif" style="vertical-align:middle;"/> Saving Consent Data...');
					//$('#change_school_totals_response_msg > td').css('background-image', 'none');
					$('#change_school_totals_response_msg').show();
					
					$.post("addConsentData.html", 
						{ 
							school_id : <%=school.getSchoolID()%>,
							grade_id: $('#grade-id').val(),
							consented_total: $('#total-consented').val(),
							refused_total: $('#total-refused').val(),
							vaccinated_total: $('#total-vaccinated').val()
						}, 
						function(data){
							parseAddConsentDataResponse(data);
						}, "xml");
				}
			}
			
			function parseAddConsentDataResponse(data){
				var xmlDoc=data.documentElement;
				var status = xmlDoc.getElementsByTagName("RESPONSE-STATUS")[0].childNodes[0].nodeValue;
				var msg = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
				
				if(status == 'COMPLETE'){
					$('#parental_consent_response_dialog').dialog('close');
					document.location.reload();
				}
				else{
					$('#change_school_totals_response_msg > td').html(msg);
					$('#change_school_totals_response_msg').show();
				}
			}
		</script>
	</head>

	<body>
		<table width="780" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#FFFFFF" style="border: thin solid Black;">
			<tr>
				<td colspan="2">
					<img src="includes/images/header.png" alt="Eastern School District H1N1 District Advisory System" width="780" height="98" border="0">
				</td>
			</tr>
			<tr>
				<td>
					<table width="95%" border="0" cellspacing="2" cellpadding="2" class="maintable" align="center">
						<tr>
							<td>
								<!-- Mainbody content here --> 
								<h1 class='displayPageTitle'><c:out value="${SCHOOLBEAN.schoolName}"/> - <fmt:formatDate pattern='MMMM yyyy' value='${TODAY}'/> Parental Consent Response</h1>
							
								<span class='displayHeaderTitle'><u>School stats</u></span><br>
								<table width="100%" id="monthly-status-report" cellspacing="1" cellpadding="6" border="0" align="center" style='border:solid 1px #c4c4c4;background-color:#FFFF99;'>
									<tr>
										<td width='12%'><span class='displayHeaderTitle'>#Teachers:</span><span id='Teachers-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberTeachers}' default='0'/></span></td>
										<td width='20%'><span class='displayHeaderTitle'>#Support Staff:</span><span id='Support-Staff-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberSupportStaff}' default='0'/></span></td>
										<td width='12%'><span class='displayHeaderTitle'>#Students:</span><span id='Students-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberStudents}' default='0'/></span></td>
										<td width='12%'><span class='displayHeaderTitle'>Total:</span><span id='Total-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.total}' default='0'/></span></td>
										<td width="*" align="right">
											<a class='menu' id="btn_show_parental_consent_response_dialog" href="#">Add/Update Consent Data</a> |
											<a class='menu' href="principalView.html">Home</a>
										</td>
									</tr>
								</table><br><br>
								<table id="monthly-report" cellspacing="1" cellpadding="6" border="0" align="center">
								
									<tr>
										<th class="displayHeaderTitle">Grade</th>
	                	<th class="displayHeaderTitle"># Consented</th>
	                  <th class="displayHeaderTitle"># Refused</th>
	                  <th class="displayHeaderTitle"># Vaccinated</th>
	                  <th class="displayHeaderTitle">Total</th>
	                </tr>
	                
	                <c:choose>
		                <c:when test="${fn:length(CONSENTDATA) > 0}">
			                <c:forEach items="${CONSENTDATA}" var="report">
			                	<tr class='daily-report-info'>
				                	<td class="displayText"><c:out value='${report.grade.name}'/></td>
				                	<td class="displayText" align="center"><c:out value='${report.consented}'/></td>
				                	<td class="displayText" align="center"><c:out value='${report.refused}'/></td>
				                	<td class="displayText" align="center"><c:out value='${report.vaccinated}'/> </td>
				                	<td class="displayText" style="font-weight:bold;" align="center"><c:out value='${report.total}'/></td>
			                	</tr>
			                </c:forEach>
			                <!--  
			                <tr class="monthly-summary-info">
			                	<td class="displayHeaderTitle">Summary</td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.teacherSummary}'/></td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.supportStaffSummary}'/></td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.studentSummary}'/></td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.total}'/></td>
			                </tr>
			                -->
		                </c:when>
		                <c:otherwise>
		                	<td colspan='5'>No parental consent data entered.</td>
		                </c:otherwise>
	                </c:choose>
									
								</table>
								
								<!--End Mainbody --> 
							</td>
						</tr>
					</table>
					<BR><BR>
				</td>
			</tr>
			<tr style="background-color: Black;">
				<td colspan="2">
					<div align="center" class="copyright">&copy; 2009 Eastern School District. All Rights Reserved.</div>
				</td>
			</tr>
		</table>
		
		<div id="parental_consent_response_dialog" title="Parental Consent Response">
			<table cellspacing='3' cellpadding='3' border='0' align="center">
				<tr>
					<td class="displayHeaderTitle" valign="top" align="center">Grade</td>
					<td class="displayHeaderTitle" valign="top" align="center"># Consented</td>
					<td class="displayHeaderTitle" valign="top" align="center"># Refused</td>
					<td class="displayHeaderTitle" valign="top" align="center"># Vaccinated</td>
				</tr>
				<tr style='padding-bottom:15px;'>
					<td class="displayText" align="center">
						<select id='grade-id' name='grade_id' class='requiredInputBox'>
							<c:forEach items="${GRADES}" var="g">
								<option value="${g.id}">${g.name}</option>
							</c:forEach>
						</select>
					</td>
					
					<td class="displayText" align="center">
						<input type='text' id='total-consented' class='requiredInputBox' style='width:50px;' value='0'  />
					</td>
				
					<td class="displayText" align="center">
						<input type='text' id='total-refused' class='requiredInputBox' style='width:50px;' value='0' />
					</td>
					
					<td class="displayText" align="center">
						<input type='text' id='total-vaccinated' class='requiredInputBox' style='width:50px;' value='0' />
					</td>
				</tr>
				
				<tr id='change_school_totals_response_msg'>
					<td colspan='4' class='ui-corner-all'>Enter grade totals for each each grade in your school above. To update a grade reenter the totals for that specific grade.</td>
				</tr>
			</table>
		</div>
		
	</body>
	
</html>
