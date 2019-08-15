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
				
				$('#month-selector').val(<%=tcal.get(Calendar.MONTH)%>);
				$('#year-selector').val(<%=tcal.get(Calendar.YEAR)%>);
				
				$('#date-selector').click(function(){
					document.location = 'adminSchoolMonthlyReport.html?s=' + ${SCHOOLBEAN.schoolID} + '&y='+$('#year-selector').val()+'&m='+$('#month-selector').val();
				});
	
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
									<a href='adminView.html'><img src='includes/images/home.gif' border='0'/></a>
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
								<div id='date-selector-box' align='center'>
									<select id='month-selector' class='requiredInputBox'>
												<option value='<%=Calendar.JANUARY%>'>January</option>
												<option value='<%=Calendar.FEBRUARY%>'>February</option>
												<option value='<%=Calendar.MARCH%>'>March</option>
												<option value='<%=Calendar.APRIL%>'>April</option>
												<option value='<%=Calendar.MAY%>'>May</option>
												<option value='<%=Calendar.JUNE%>'>June</option>
												<option value='<%=Calendar.JULY%>'>July</option>
												<option value='<%=Calendar.AUGUST%>'>August</option>
												<option value='<%=Calendar.SEPTEMBER%>'>September</option>
												<option value='<%=Calendar.OCTOBER%>'>October</option>
												<option value='<%=Calendar.NOVEMBER%>'>November</option>
												<option value='<%=Calendar.DECEMBER%>'>December</option>
											</select> 
											<select id='year-selector' class='requiredInputBox'>
												<%
													Calendar now = Calendar.getInstance();
													for(int i=0; i < 5; i++){
														out.println("<option value='" + now.get(Calendar.YEAR) + "'>" + now.get(Calendar.YEAR) + "</option>");
														now.add(Calendar.YEAR, -1);
													}
												%>
											</select>
											<input type='button' value='Go' id='date-selector' class='requiredInputBox' style='font-weight:bold;color:#ff0000;'/>
								</div>
								<p id='trend-chart' align="center">
									<img src="includes/images/charts/${CHART}" align="middle"/>
								</p>
								<table id="monthly-report" cellspacing="1" cellpadding="6" border="0" align="center">
								
									<tr>
										<th class="displayHeaderTitle">Date</th>
	                	<th class="displayHeaderTitle">Teachers</th>
	                  <th class="displayHeaderTitle">Support Staff</th>
	                  <th class="displayHeaderTitle">Students</th>
	                  <th class="displayHeaderTitle">Total</th>
	                </tr>
	                
	                <c:choose>
		                <c:when test="${fn:length(MONTHLYDAILYREPORTBEANS) > 0}">
			                <c:forEach items="${MONTHLYDAILYREPORTBEANS}" var="report">
			                	<tr class='daily-report-info'>
				                	<td class="displayText"><fmt:formatDate pattern='dd/MM/yyyy' value='${report.dateAdded}'/></td>
				                	<td class="displayText ${report.teacherAlert ? 'daily-report-alert':''}" align="center"><c:out value='${report.teacherTotal}'/> (<fmt:formatNumber pattern='0.00' value='${report.teacherAverage}'/>%)</td>
				                	<td class="displayText ${report.supportStaffAlert ? 'daily-report-alert':''}" align="center"><c:out value='${report.supportStaffTotal}'/> (<fmt:formatNumber pattern='0.00' value='${report.supportStaffAverage}'/>%)</td>
				                	<td class="displayText ${report.studentAlert ? 'daily-report-alert':''}" align="center"><c:out value='${report.studentTotal}'/> (<fmt:formatNumber pattern='0.00' value='${report.studentAverage}'/>%)</td>
				                	<td class="displayText" style="font-weight:bold;" align="center"><c:out value='${report.total}'/> (<fmt:formatNumber pattern='0.00' value='${report.overallAverage}'/>%)</td>
			                	</tr>
			                	<c:if test="${!empty report.additionalComments}">
				                	<tr class='daily-report-additional-comment'>
				                		<td colspan='5' >
				                			<span class='displayHeaderTitle'>Additional Comments:</span><br>
				                			<span class='displayText'><c:out value='${report.additionalComments}'/></span>
				                		</td>
				                	</tr>
			                	</c:if>
			                </c:forEach> 
			                <tr class="monthly-summary-info">
			                	<td class="displayHeaderTitle">Summary</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Teachers:</span><BR><fmt:formatNumber pattern='0.00' value='${MONTHLYSUMMARYBEAN.teacherAverage}'/>%</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Support Staff:</span><BR><fmt:formatNumber pattern='0.00' value='${MONTHLYSUMMARYBEAN.supportStaffAverage}'/>%</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Students:</span><BR><fmt:formatNumber pattern='0.00' value='${MONTHLYSUMMARYBEAN.studentAverage}'/>%</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Total:</span><BR><fmt:formatNumber pattern='0.00' value='${MONTHLYSUMMARYBEAN.totalAverage}'/>%</td>
			                </tr>
		                </c:when>
		                <c:otherwise>
		                	<td colspan='5'>No daily reports for the month of <fmt:formatDate pattern='MMMM yyyy' value='${TODAY}'/>.</td>
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

