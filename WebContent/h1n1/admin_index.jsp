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
		<title>Eastern School District - H1N1 District Advisory System - Admin View</title>
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

				$('#loading_dialog').dialog({
					autoOpen: false,
					bgiframe: true,
					width:270,
					height: 200,
					modal: true,
					hide: 'explode',
					closeOnEscape: false,
					draggable: false,
					resizable: false,   
					open: function(event, ui) { 
						$(".ui-dialog-titlebar").hide(); 
					}
				});	
				
				$('#date-selector').datepicker({
					showButtonPanel: true, 
					buttonImage: 'includes/images/cal_popup_01.gif',
					showOn: 'both',
					showAnim: 'drop',
					dateFormat: 'dd/mm/yy',
					onSelect: function(dateText, inst) {
						$("#loading_dialog").dialog('open');
						document.location = 'adminView.html?vd=' + dateText;
					}
				});

				$('#chart-type, #view-range').change(function(){
					document.location = 'adminView.html?vd=' + $('#date-selector').val() 
						+ '&chart_type=' + $('#chart-type').val() + '&vr=' + $('#view-range').val();
				});

				$('#chart-type').val(${CHARTTYPE});
				$('#view-range').val(${VIEWRANGE});
				
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
								<h1 class='displayPageTitle'>Daily Summary Report - <fmt:formatDate pattern='MMMM dd, yyyy' value='${TODAY}'/></h1>
								
							  <div id='toolbar-link' align='right'>
									<a href='consentSummaryData.html'>Parental Consent Summary</a>&nbsp;|&nbsp;<a href='javascript:window.print();'><img src='includes/images/printer.gif' border='0' style="vertical-align:middle;"/></a>
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
								
								<div id='date-selector-box' align='center'>
									<table>
										<tr style="height:18px;">
                      <td class="displayHeaderTitle" valign="middle"  align="right"><span class="requiredStar">*&nbsp;</span>View Date:</td>
                      <td valign="top">
                      	<input id='date-selector' class="requiredInputbox" type="text" 
                            	 style="height:19px;width:100px;" 
                            	 value="<%=new SimpleDateFormat("dd/MM/yyyy").format((Date)request.getAttribute("TODAY"))%>" 
                            	 readonly="readonly"/>
                      </td>
                    </tr>
                    
                    <tr style="height:18px;">
                      <td class="displayHeaderTitle" valign="middle"  align="right"><span class="requiredStar">*&nbsp;</span>Chart Type:</td>
                      <td valign="top">
                      	<select id='chart-type' name='chart_type' class="requiredInputbox">
                      		<option value='1'>Line Chart</option>
                      		<option value='2' SELECTED="selected">Clustered Bar Chart</option>
                      	</select>
                      </td>
                    </tr>
                    
                    <tr style="height:18px;">
                      <td class="displayHeaderTitle" valign="middle"  align="right"><span class="requiredStar">*&nbsp;</span>Time Frame:</td>
                      <td valign="top">
                      	<select id='view-range' name='view_range' class="requiredInputbox">
                      		<option value='-1' SELECTED="selected">One Month</option>
                      		<option value='-2'>Two Months</option>
                      		<option value='-3'>Three Months</option>
                      	</select>
                      </td>
                    </tr>
                  </table>
								</div>
								<p id='trend-chart' align="center">
									<img src="includes/images/charts/${CHART}" align="middle"/>
								</p>
								<table id="monthly-report" cellspacing="1" cellpadding="6" border="0" align="center">
								
									<tr>
										<th class="displayHeaderTitle">School</th>
	                	<th class="displayHeaderTitle">Teachers</th>
	                  <th class="displayHeaderTitle">Support Staff</th>
	                  <th class="displayHeaderTitle">Students</th>
	                  <th class="displayHeaderTitle">Total</th>
	                </tr>
	                
	                <c:choose>
		                <c:when test="${fn:length(DAILYSCHOOLINFOBEANS) > 0}">
			                <c:forEach items="${DAILYSCHOOLINFOBEANS}" var="report">
			                	<c:choose>
				                	<c:when test='${!empty report.dailyReport.dateAdded}'>
						                <tr class='daily-report-info ${report.alert ? "daily-report-alert":"" }'>
							                <td class="displayHeaderTitle"><a href='adminSchoolMonthlyReport.html?s=${report.school.schoolID}'><c:out value='${report.school.schoolName}'/></a></td>
							                <td class="displayText ${report.teacherAlert ? 'daily-report-alert':''}" align="center"><c:out value='${report.dailyReport.teacherTotal}'/> / <c:out value='${report.stats.numberTeachers}'/> (<fmt:formatNumber pattern='0.00' value='${report.teacherPercentage}'/>%)<BR>(<fmt:formatNumber pattern='0.00' value='${report.difference.teacherDifference / report.stats.numberTeachers * 100.0}'/>%)</td>
							                <td class="displayText ${report.supportStaffAlert ? 'daily-report-alert':''}" align="center"><c:out value='${report.dailyReport.supportStaffTotal}'/> / <c:out value='${report.stats.numberSupportStaff}'/> (<fmt:formatNumber pattern='0.00' value='${report.supportStaffPercentage}'/>%)<BR>(<fmt:formatNumber pattern='0.00' value='${report.difference.supportStaffDifference / report.stats.numberSupportStaff * 100.0}'/>%)</td>
							                <td class="displayText ${report.studentAlert ? 'daily-report-alert':''}" align="center"><c:out value='${report.dailyReport.studentTotal}'/> / <c:out value='${report.stats.numberStudents}'/> (<fmt:formatNumber pattern='0.00' value='${report.studentPercentage}'/>%)<BR>(<fmt:formatNumber pattern='0.00' value='${report.difference.studentDifference / report.stats.numberStudents * 100.0}'/>%)</td>
							                <td class="displayText" style="font-weight:bold;" align="center"><c:out value='${report.dailyReport.total}'/> / <c:out value='${report.stats.total}'/>  (<fmt:formatNumber pattern='0.00' value='${report.overallPercentage}'/>%)</td>
						                </tr>
						                <c:if test="${!empty report.dailyReport.additionalComments}">
						                	<tr class='daily-report-additional-comment'>
							                	<td colspan='5' style='padding-left:20px;'>
							                		<span class='displayHeaderTitle'>Additional Comments:</span><br>
							                		<span class='displayText'><c:out value='${report.dailyReport.additionalComments}'/></span>
							                	</td>
						                	</tr>
						                </c:if>
				                	</c:when>
				                	<c:otherwise>
				                		<tr class='daily-report-info'>
				                				<td class="displayHeaderTitle"><a href='adminSchoolMonthlyReport.html?s=${report.school.schoolID}'><c:out value='${report.school.schoolName}'/></a></td>
							                	<td colspan='4' class="displayHeaderTitle" style='color:#FF0000;'>No report for this period.</td>
						                	</tr>
				                	</c:otherwise>
			                	</c:choose>
			                </c:forEach> 
			          
			                <tr class="monthly-summary-info">
			                	<td class="displayHeaderTitle">Summary</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Teachers:</span><BR><c:out value='${DAILYSUMMARYBEAN.teacherSummary}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.totalTeachers}'/><br/>(<fmt:formatNumber pattern='0.00' value='${DAILYSUMMARYBEAN.teacherSummary / SCHOOLSTATSSUMMARYBEAN.totalTeachers * 100.0}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Support Staff:</span><BR><c:out value='${DAILYSUMMARYBEAN.supportStaffSummary}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.totalSupportStaff}'/><br/>(<fmt:formatNumber pattern='0.00' value='${DAILYSUMMARYBEAN.supportStaffSummary / SCHOOLSTATSSUMMARYBEAN.totalSupportStaff * 100.0}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Students:</span><BR><c:out value='${DAILYSUMMARYBEAN.studentSummary}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.totalStudents}'/><br/>(<fmt:formatNumber pattern='0.00' value='${DAILYSUMMARYBEAN.studentSummary / SCHOOLSTATSSUMMARYBEAN.totalStudents * 100.0}'/>%)</td>
			                	<td class="displayHeaderTitle" align="center"><span style='color:#333333;'>Total:</span><BR><c:out value='${DAILYSUMMARYBEAN.total}'/> / <c:out value='${SCHOOLSTATSSUMMARYBEAN.total}'/><br/>(<fmt:formatNumber pattern='0.00' value='${DAILYSUMMARYBEAN.total / SCHOOLSTATSSUMMARYBEAN.total * 100.0}'/>%)</td>
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
		
		<div id="loading_dialog">
			<table cellspacing='3' cellpadding='3' border='0' align="center">
				<tr>
					<td class="displayHeaderTitle" align="center" valign="middle"><img src='includes/images/admin-index-loader.gif' />
					<br><span style="font-weight:bold;color:#C0C0C0;">Loading...</span></td>
				</tr>
			</table>
		</div>
		
	</body>
	
</html>
