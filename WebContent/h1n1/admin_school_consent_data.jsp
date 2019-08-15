<%@ page language="java"
         import="java.util.*"
         isThreadSafe="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>


<esd:SecurityCheck permissions="H1N1-ADMIN-VIEW" />

<%
	Calendar tcal = Calendar.getInstance();
	tcal.setTime((Date)request.getAttribute("TODAY"));
%>

<html>

	<head>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
		<title>Eastern School District - H1N1 District Advisory System - Principal View</title>
		<link href="includes/css/h1n1.css" rel="stylesheet" type="text/css">
		<style type="text/css">@import 'includes/css/sunny/jquery-ui-1.7.2.custom.css';</style>
		<style type="text/css">
			#date-selector-box{
				margin: 10px;
				/*padding: 5px 5px 5px 5px;*/
			}
			
			@media print {
				body{
					margin-left: 0px;
					margin-right:0px;
				}
    		#date-selector-box{
    			display:none;
    		}
    		
    		#toolbar-link{
    			display:none;
    		}
  		}
		</style>
		<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="includes/js/jquery-ui-1.7.2.custom.min.js"></script>
		<script type="text/javascript">
			$('document').ready(function(){
			
				$('#monthly-report th').css('border-bottom', 'double #333333');
				$('#monthly-report th').css('border-top', 'solid 1px #333333');
				$('#monthly-report td:not(.monthly-summary-info td)').css('border', 'solid 1px #C0C0C0');
				$('#monthly-report tr.daily-report-info:odd').css('background-color', '#DFEAF6');
				$('#monthly-report tr.daily-report-info:even').css('background-color', '#FFFFFF');
				
				//$('#monthly-report tr td.daily-report-alert').css('border', 'solid 2px #FF0000');
				$('#monthly-report tr td.daily-report-alert').css('color', '#FF0000');
				$('#monthly-report tr td.daily-report-alert').css('font-weight', 'bold');
				
				$('#monthly-report tr.daily-report-additional-comment').css('background-color','#FFEBCD');
				
				//$('#monthly-report tr.daily-report-additional-comment:odd').css('background-color', '#DFEAF6');
				//$('#monthly-report tr.daily-report-additional-comment:even').css('background-color', '#FFFFFF');
				//$('#monthly-report tr.daily-report-additional-comment td').css('border-bottom', 'solid 1px #3F3F3F');
				
				$('.monthly-summary-info td').css('background-color', '#F4F4F4');
				$('.monthly-summary-info td').css('border', 'none');
				$('.monthly-summary-info td').css('border-top', 'double #333333');
				$('.monthly-summary-info td').css('border-left', 'solid 1px #333333');
				$('.monthly-summary-info td').css('border-right', 'solid 1px #333333');
				$('.monthly-summary-info td').css('border-bottom', 'solid 1px #333333');
				$('.monthly-summary-info td').css('font-weight', 'bold');
				$('.monthly-summary-info td').css('color', '#FF0000');
	
			});
			
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
								<h1 class='displayPageTitle'><c:out value="${SCHOOLBEAN.schoolName}"/> - <fmt:formatDate pattern='MMMM yyyy' value='${TODAY}'/> Daily Reports</h1>
							
								<div id='toolbar-link' align='right'>
									<a href='javascript:window.print();'><img src='includes/images/printer.gif' border='0'/></a>&nbsp;|&nbsp;
									<a href='consentSummaryData.html'><img src='includes/images/home.gif' border='0'/></a>
								</div>
								<span class='displayHeaderTitle'><u>School stats</u></span><br>
								<table width="100%" id="monthly-status-report" cellspacing="1" cellpadding="6" border="0" align="center" style='border:solid 1px #c4c4c4;background-color:#FFFF99;'>
									<tr>
										<td width='15%'><span class='displayHeaderTitle'>#Teachers:</span><span id='Teachers-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberTeachers}' default='0'/></span></td>
										<td width='20%'><span class='displayHeaderTitle'>#Support Staff:</span><span id='Support-Staff-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberSupportStaff}' default='0'/></span></td>
										<td width='15%'><span class='displayHeaderTitle'>#Students:</span><span id='Students-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberStudents}' default='0'/></span></td>
										<td width='15%'><span class='displayHeaderTitle'>Total:</span><span id='Total-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.total}' default='0'/></span></td>
										<td width='*'>&nbsp;</td>
									</tr>
								</table>
								<BR><BR>
								<table id="monthly-report" cellspacing="1" cellpadding="6" border="0" align="center">
								
									<tr>
										<th class="displayHeaderTitle">Grade</th>
	                	<th class="displayHeaderTitle">Consented</th>
	                  <th class="displayHeaderTitle">Refused</th>
	                  <th class="displayHeaderTitle">Vaccinated</th>
	                  <th class="displayHeaderTitle">Total</th>
	                </tr>
	                
	                <c:choose>
		                <c:when test="${fn:length(SCHOOLGRADECONSENTDATABEANS) > 0}">
			                <c:forEach items="${SCHOOLGRADECONSENTDATABEANS}" var="report">
			                	<tr class='daily-report-info'>
				                	<td class="displayText"><c:out value='${report.grade.name}'/></td>
				                	<td class="displayText" align="center">
				                		<c:out value='${report.consented}'/> / <c:out value='${report.gradeStudents}'/><BR>(<fmt:formatNumber pattern='0.00' value='${report.consentedAverage}'/>%)
				                	</td>
				                	<td class="displayText" align="center">
				                		<c:out value='${report.refused}'/> / <c:out value='${report.gradeStudents}'/><BR>(<fmt:formatNumber pattern='0.00' value='${report.refusedAverage}'/>%)
				                	</td>
				                	<td class="displayText" align="center">
				                		<c:out value='${report.vaccinated}'/> / <c:out value='${report.gradeStudents}'/><BR>(<fmt:formatNumber pattern='0.00' value='${report.vaccinatedAverage}'/>%)
				                	</td>
				                	<td class="displayText" style="font-weight:bold;" align="center">
				                		<c:out value='${report.total}'/> / <c:out value='${report.gradeStudents}'/><BR>(<fmt:formatNumber pattern='0.00' value='${report.overallAverage}'/>%)
				                	</td>
			                	</tr>
			                </c:forEach>
			                
			                <tr class="monthly-summary-info">
			                	<td class="displayHeaderTitle">Summary</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Consented:</span><BR><c:out value='${SCHOOLCONSENTSUMMARYBEAN.consented}'/> / <c:out value='${SCHOOLCONSENTSUMMARYBEAN.stats.numberStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${SCHOOLCONSENTSUMMARYBEAN.percent_consented}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Refused:</span><BR><c:out value='${SCHOOLCONSENTSUMMARYBEAN.refused}'/> / <c:out value='${SCHOOLCONSENTSUMMARYBEAN.stats.numberStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${SCHOOLCONSENTSUMMARYBEAN.percent_refused}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Vaccinated:</span><BR><c:out value='${SCHOOLCONSENTSUMMARYBEAN.vaccinated}'/> / <c:out value='${SCHOOLCONSENTSUMMARYBEAN.stats.numberStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${SCHOOLCONSENTSUMMARYBEAN.percent_vaccinated}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Total:</span><BR><c:out value='${SCHOOLCONSENTSUMMARYBEAN.total}'/> / <c:out value='${SCHOOLCONSENTSUMMARYBEAN.stats.numberStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${SCHOOLCONSENTSUMMARYBEAN.percent_overall}'/>%)</td>
			                </tr>
			                
		                </c:when>
		                <c:otherwise>
		                	<td colspan='5'>No consent data entered.</td>
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
		
	</body>
	
</html>

