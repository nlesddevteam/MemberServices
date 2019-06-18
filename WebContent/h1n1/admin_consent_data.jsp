<%@ page language="java"
         import="java.util.*,
                 java.text.*"
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
		<meta http-equiv="Cache-Control" content="no-store,no-cache, must-revalidate,post-check=0, pre-check=0,max-age=0">
		<META HTTP-EQUIV="Expires" CONTENT="Mon, 15 Sep 2003 1:00:00 GMT">
		<title>Eastern School District - H1N1 District Advisory System - Administrator View</title>
		<link href="includes/css/h1n1.css" rel="stylesheet" type="text/css">
		<style type="text/css">@import 'includes/css/sunny/jquery-ui-1.7.2.custom.css';</style>
		<style type="text/css">
			#date-selector-box{
				margin: 10px;
				/*padding: 5px 5px 5px 5px;*/
			}
			
			@media print {
				body{
					margin: 10px;
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
		
				$('#monthly-status-report').css('border', 'solid 1px #c4c4c4');
				$('#monthly-status-report').css('background-color', '#FFFF99');
				
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
								<h1 class='displayPageTitle'>Parental Consent Response Summary Report - <fmt:formatDate pattern='MMMM dd, yyyy' value='${TODAY}'/></h1>
								
							  <div id='toolbar-link' align='right'>
									<a href='javascript:window.print();'><img src='includes/images/printer.gif' border='0' style="vertical-align:middle;" /></a>&nbsp;|&nbsp;
									<a href='adminView.html'><img src='includes/images/home.gif' border='0' style="vertical-align:middle;" /></a>
								</div>
								<span class='displayHeaderTitle'><u>School Stats</u> [<c:out value='${SCHOOLSTATSSUMMARYBEAN.reportCount}' default='0'/> reporting]</span><br>
								<table width="100%" id="monthly-status-report" cellspacing="1" cellpadding="6" border="0" align="center">
									<tr>
										<td width='15%'><span class='displayHeaderTitle'>#Teachers:</span><span id='Teachers-Display' class='displayText'><c:out value='${SCHOOLSTATSSUMMARYBEAN.totalTeachers}' default='0'/></span></td>
										<td width='20%'><span class='displayHeaderTitle'>#Support Staff:</span><span id='Support-Staff-Display' class='displayText'><c:out value='${SCHOOLSTATSSUMMARYBEAN.totalSupportStaff}' default='0'/></span></td>
										<td width='15%'><span class='displayHeaderTitle'>#Students:</span><span id='Students-Display' class='displayText'><c:out value='${SCHOOLSTATSSUMMARYBEAN.totalStudents}' default='0'/></span></td>
										<td width='15%'><span class='displayHeaderTitle'>Total:</span><span id='Total-Display' class='displayText'><c:out value='${SCHOOLSTATSSUMMARYBEAN.total}' default='0'/></span></td>
										 
										<td width="*" align="right">
											&nbsp;
										</td>
										
									</tr>
								</table>
								
								<BR><BR>
								
								<table id="monthly-report" cellspacing="1" cellpadding="6" border="0" align="center">
								
									<tr>
										<th class="displayHeaderTitle">School</th>
	                	<th class="displayHeaderTitle"># Consented</th>
	                  <th class="displayHeaderTitle"># Refused</th>
	                  <th class="displayHeaderTitle"># Vaccinated</th>
	                  <th class="displayHeaderTitle">Total</th>
	                </tr>
	                
	                <c:choose>
		                <c:when test="${fn:length(SCHOOLCONSENTDATASUMMARY) > 0}">
			                <c:forEach items="${SCHOOLCONSENTDATASUMMARY}" var="report">
			                	<c:choose>
				                	<c:when test='${report.stats.gradeTotal gt 0}'>
						                <tr class='daily-report-info '>
							                <td class="displayHeaderTitle"><a href='adminSchoolConsentReport.html?s=${report.school.schoolID}'><c:out value='${report.school.schoolName}'/></a></td>
							                <td class="displayText"  align="center"><c:out value='${report.consented}'/> (<fmt:formatNumber pattern='0.00' value='${report.percent_consented}'/>%)</td>
							                <td class="displayText"  align="center"><c:out value='${report.refused}'/>  (<fmt:formatNumber pattern='0.00' value='${report.percent_refused}'/>%)</td>
							                <td class="displayText"  align="center"><c:out value='${report.vaccinated}'/>  (<fmt:formatNumber pattern='0.00' value='${report.percent_vaccinated}'/>%)</td>
							                <td class="displayText" style="font-weight:bold;" align="center"><c:out value='${report.total}'/> / <c:out value='${report.stats.numberStudents}'/></td>
						                </tr>
						                
				                	</c:when>
				                	<c:otherwise>
				                		<tr class='daily-report-info'>
				                				<td class="displayHeaderTitle"><a href='adminSchoolConsentReport.html?s=${report.school.schoolID}'><c:out value='${report.school.schoolName}'/></a></td>
							                	<td colspan='4' class="displayHeaderTitle" style='color:#FF0000;'>Grade level statistics are incomplete.</td>
						                	</tr>
				                	</c:otherwise>
			                	</c:choose>
			                </c:forEach> 
			          
			                <tr class="monthly-summary-info">
			                	<td class="displayHeaderTitle">Summary</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Consented:</span><BR><c:out value='${CONSENTDATASUMMARY.consented}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.totalStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${CONSENTDATASUMMARY.consented / SCHOOLSTATSSUMMARYBEAN.totalStudents * 100.0}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Refused:</span><BR><c:out value='${CONSENTDATASUMMARY.refused}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.totalStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${CONSENTDATASUMMARY.refused / SCHOOLSTATSSUMMARYBEAN.totalStudents * 100.0}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Vaccinated:</span><BR><c:out value='${CONSENTDATASUMMARY.vaccinated}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.totalStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${CONSENTDATASUMMARY.vaccinated / SCHOOLSTATSSUMMARYBEAN.totalStudents * 100.0}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Total:</span><BR><c:out value='${CONSENTDATASUMMARY.total}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.totalStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${CONSENTDATASUMMARY.total / SCHOOLSTATSSUMMARYBEAN.totalStudents * 100.0}'/>%)</td>
			                </tr>
			            
		                </c:when>
		                <c:otherwise>
		                	<td colspan='5'>No daily reports for <fmt:formatDate pattern='MMMM dd, yyyy' value='${TODAY}'/>.</td>
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
