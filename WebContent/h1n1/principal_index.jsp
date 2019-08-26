<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.awsd.security.*"
         isThreadSafe="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>


<esd:SecurityCheck permissions="H1N1-PRINCIPAL-VIEW" />

<%
	User usr = (User) session.getAttribute("usr");
	School school = (School) request.getAttribute("SCHOOLBEAN");
	Calendar tcal = Calendar.getInstance();
	tcal.setTime((Date)request.getAttribute("TODAY"));
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
				
				$('#add_daily_report_dialog').dialog({
					autoOpen: false,
					bgiframe: true,
					width:500,
					height: 250,
					modal: true,
					hide: 'explode',
					buttons: {
						'close': function(){$('#add_daily_report_dialog').dialog('close');},
						'submit': function(){submitDailyReport();}
					}
				});	
				
				<%
					if(school.getSchoolStats() == null)
						out.println("$('#btn_show_add_daily_report_dialog').bind('click', showChangeSchoolTotalsDialog);");
					else
						out.println("$('#btn_show_add_daily_report_dialog').bind('click', showAddDailyReportDialog);");
				%>
				
				$('#change_school_totals_dialog').dialog({
					autoOpen: false,
					bgiframe: true,
					width:500,
					height: 250,
					modal: true,
					hide: 'explode',
					buttons: {
						'close': function(){$('#change_school_totals_dialog').dialog('close');},
						'submit': function(){submitChangeSchoolStats();}
					}
				});
				
				$('#btn_show_change_school_totals_dialog').click(function(){
					$("#change_school_totals_dialog").dialog('open');
				});
				
				$('#month-selector').val(<%=tcal.get(Calendar.MONTH)%>);
				$('#year-selector').val(<%=tcal.get(Calendar.YEAR)%>);
				
				$('#date-selector').click(function(){
					document.location = 'principalView.html?y='+$('#year-selector').val()+'&m='+$('#month-selector').val();
				});
				
				$('#report_date').datepicker({
					showButtonPanel: true, 
					buttonImage: 'includes/images/cal_popup_01.gif',
					showOn: 'button',
					showAnim: 'drop',
					dateFormat: 'dd/mm/yy'
				});
				
				<%
					if(school.getSchoolStats() == null){
						out.println("$('#change_school_totals_dialog').dialog('open');");
					}
				%>
				
			});
			
			function showAddDailyReportDialog(){
				$('#add_daily_report_dialog').dialog('open');
			}
			
			function showChangeSchoolTotalsDialog(){
				$('#change_school_totals_dialog').dialog('open');
			}
			
			function submitDailyReport(){
			
				var expr = /^\d+(.[0-9]([0-9])?)?$/;
				
				$('#response_msg').hide();
				
				if(($('#teachers_total').val() == '') || !expr.test($('#teachers_total').val())){
					$('#response_msg > td').html('Invalid # teachers off sick.');
					$('#response_msg > td').css('font-weight', 'bold');
					$('#response_msg > td').css('color', '#FF0000');
					$('#response_msg').show();
				}
				else if(($('#support_staff_total').val() == '') || !expr.test($('#support_staff_total').val())){
					$('#response_msg > td').html('Invalid # support staff off sick.');
					$('#response_msg > td').css('font-weight', 'bold');
					$('#response_msg > td').css('color', '#FF0000');
					$('#response_msg').show();
				}
				else if(($('#students_total').val() == '') || !expr.test($('#students_total').val())){
					$('#response_msg > td').html('Invalid # students off sick.');
					$('#response_msg > td').css('font-weight', 'bold');
					$('#response_msg > td').css('color', '#FF0000');
					$('#response_msg').show();
				}
				else{
					$('#response_msg > td').html('<img src="includes/images/ajax-loader.gif" style="vertical-align:middle;"/> Saving Report...');
					//$('#response_msg > td').css('background-image', 'none');
					$('#response_msg').show();
			
					$.post("addDailyReport.html", 
						{ 
							personnel_id : <%=usr.getPersonnel().getPersonnelID()%>,
							school_id : <%=school.getSchoolID()%>,
							report_date : $('#report_date').val(),
							teacher_total : $('#teachers_total').val(),
							support_staff_total : $('#support_staff_total').val(),
							student_total : $('#students_total').val(), 
							additional_comments : $('#additional_comments').val()
						}, 
						function(data){
							parseAddDailyReportResponse(data);
						}, "xml");
				}
			}
			
			function parseAddDailyReportResponse(data){
				var xmlDoc=data.documentElement;
				var status = xmlDoc.getElementsByTagName("RESPONSE-STATUS")[0].childNodes[0].nodeValue;
				var msg = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
				
				if(status == 'COMPLETE'){
					$('#add_daily_report_dialog').dialog('close');
					document.location.reload();
				}
				else{
					$('#response_msg > td').html(msg);
					$('#response_msg').show();
				}
			}
			
			function submitChangeSchoolStats(){
			
				var expr = /^\d+(.[0-9]([0-9])?)?$/; 
				
				$('#change_school_totals_response_msg').hide();
				
				if(($('#school_teachers_total').val() == '') || !expr.test($('#school_teachers_total').val())){
					$('#change_school_totals_response_msg > td').html('Invalid total # of teachers.');
					$('#change_school_totals_response_msg > td').css('font-weight', 'bold');
					$('#change_school_totals_response_msg > td').css('color', '#FF0000');
					$('#change_school_totals_response_msg').show();
				}
				else if(($('#school_support_staff_total').val() == '') || !expr.test($('#school_support_staff_total').val())){
					$('#change_school_totals_response_msg > td').html('Invalid total # of support staff.');
					$('#change_school_totals_response_msg > td').css('font-weight', 'bold');
					$('#change_school_totals_response_msg > td').css('color', '#FF0000');
					$('#change_school_totals_response_msg').show();
				}
				else if(($('#school_students_total').val() == '') || !expr.test($('#school_students_total').val())){
					$('#change_school_totals_response_msg > td').html('Invalid total # of students.');
					$('#change_school_totals_response_msg > td').css('font-weight', 'bold');
					$('#change_school_totals_response_msg > td').css('color', '#FF0000');
					$('#change_school_totals_response_msg').show();
				}
				else{
					$('#change_school_totals_response_msg > td').html('<img src="includes/images/ajax-loader.gif" style="vertical-align:middle;"/> Saving School Stats...');
					//$('#change_school_totals_response_msg > td').css('background-image', 'none');
					$('#change_school_totals_response_msg').show();
					
					$.post("changeSchoolStats.html", 
						{ 
							school_id : <%=school.getSchoolID()%>, 
							teacher_total: $('#school_teachers_total').val(),
							support_staff_total: $('#school_support_staff_total').val(),
							student_total: $('#school_students_total').val(),
							grade_0: $('#grade_0_total').val(),
							grade_1: $('#grade_1_total').val(),
							grade_2: $('#grade_2_total').val(),
							grade_3: $('#grade_3_total').val(),
							grade_4: $('#grade_4_total').val(),
							grade_5: $('#grade_5_total').val(),
							grade_6: $('#grade_6_total').val(),
							grade_7: $('#grade_7_total').val(),
							grade_8: $('#grade_8_total').val(),
							grade_9: $('#grade_9_total').val(),
							grade_10: $('#grade_10_total').val(),
							grade_11: $('#grade_11_total').val(),
							grade_12: $('#grade_12_total').val()
						}, 
						function(data){
							parseChangeSchoolStatsResponse(data);
						}, "xml");
				}
			}
			
			function parseChangeSchoolStatsResponse(data){
				var xmlDoc=data.documentElement;
				var status = xmlDoc.getElementsByTagName("RESPONSE-STATUS")[0].childNodes[0].nodeValue;
				var msg = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
				
				if(status == 'COMPLETE'){
					$('#Teachers-Display').html(xmlDoc.getElementsByTagName("TEACHERS")[0].childNodes[0].nodeValue);
					$('#Support-Staff-Display').html(xmlDoc.getElementsByTagName("SUPPORT-STAFF")[0].childNodes[0].nodeValue);
					$('#Students-Display').html(xmlDoc.getElementsByTagName("STUDENTS")[0].childNodes[0].nodeValue);
					$('#Total-Display').html(xmlDoc.getElementsByTagName("TOTAL")[0].childNodes[0].nodeValue);
					
					$('#btn_show_add_daily_report_dialog').unbind('click', showChangeSchoolTotalsDialog);
					$('#btn_show_add_daily_report_dialog').bind('click', showAddDailyReportDialog);
					
					$('#change_school_totals_dialog').dialog('close');
					
					$('#change_school_totals_response_msg > td').html('Enter school totals for each group above.');
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
								<h1 class='displayPageTitle'><c:out value="${SCHOOLBEAN.schoolName}"/> - <fmt:formatDate pattern='MMMM yyyy' value='${TODAY}'/> Daily Reports</h1>
							
								<span class='displayHeaderTitle'><u>School stats</u></span><br>
								<table width="100%" id="monthly-status-report" cellspacing="1" cellpadding="6" border="0" align="center" style='border:solid 1px #c4c4c4;background-color:#FFFF99;'>
									<tr>
										<td width='12%'><span class='displayHeaderTitle'>#Teachers:</span><span id='Teachers-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberTeachers}' default='0'/></span></td>
										<td width='20%'><span class='displayHeaderTitle'>#Support Staff:</span><span id='Support-Staff-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberSupportStaff}' default='0'/></span></td>
										<td width='12%'><span class='displayHeaderTitle'>#Students:</span><span id='Students-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.numberStudents}' default='0'/></span></td>
										<td width='12%'><span class='displayHeaderTitle'>Total:</span><span id='Total-Display' class='displayText'><c:out value='${SCHOOLBEAN.schoolStats.total}' default='0'/></span></td>
										<td width="*" align="right">
											<a class='menu' id="btn_show_add_daily_report_dialog" href="#">Daily Report</a> |
											<a class='menu' id="btn_show_change_school_totals_dialog" href="#">School Stats</a><BR>
											<a class='menu' href="viewParentalConsent.html?school_id=${SCHOOLBEAN.schoolID}">Parental Consent Response</a>
										</td>
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
				                	<td class="displayText" align="center"><c:out value='${report.teacherTotal}'/> (<fmt:formatNumber pattern='0.00' value='${report.teacherTotal / SCHOOLBEAN.schoolStats.numberTeachers * 100.0}'/>%)</td>
				                	<td class="displayText" align="center"><c:out value='${report.supportStaffTotal}'/> (<fmt:formatNumber pattern='0.00' value='${report.supportStaffTotal / SCHOOLBEAN.schoolStats.numberSupportStaff * 100.0}'/>%)</td>
				                	<td class="displayText" align="center"><c:out value='${report.studentTotal}'/> (<fmt:formatNumber pattern='0.00' value='${report.studentTotal / SCHOOLBEAN.schoolStats.numberStudents * 100.0}'/>%)</td>
				                	<td class="displayText" style="font-weight:bold;" align="center"><c:out value='${report.total}'/> (<fmt:formatNumber pattern='0.00' value='${report.total / SCHOOLBEAN.schoolStats.total * 100.0}'/>%)</td>
			                	</tr>
			                	<tr class='daily-report-additional-comment'>
			                		<td colspan='5' >
			                			<span class='displayHeaderTitle'>Additional Comments:</span><br>
			                			<span class='displayText'><c:out value='${report.additionalComments}'/></span>
			                		</td>
			                	</tr>
			                </c:forEach> 
			                <tr class="monthly-summary-info">
			                	<td class="displayHeaderTitle">Summary</td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.teacherSummary}'/></td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.supportStaffSummary}'/></td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.studentSummary}'/></td>
			                	<td class="displayHeaderTitle" align="center"><c:out value='${MONTHLYSUMMARYBEAN.total}'/></td>
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
		
		
		<div id="add_daily_report_dialog" title="Add Daily Report">
			<table cellspacing='3' cellpadding='3' border='0' align="center">
				<tr>
					<td class="displayHeaderTitle" valign="top">Date:</td>
					<td class="displayText">
						<input id='report_date' class="requiredInputbox" type="text" 
                   style="height:19px;width:100px;" 
                   value="<%= new SimpleDateFormat("dd/MM/yyyy").format((Date)request.getAttribute("TODAY"))%>" 
                   readonly="readonly"/>
					</td>
				</tr>
				
				<tr>
					<td class="displayHeaderTitle" valign="top"># Teachers:</td>
					<td class="displayText"><input type='text' id='teachers_total' class='requiredInputBox' style='width:50px;' /></td>
				</tr>
				
				<tr>
					<td class="displayHeaderTitle" valign="top"># Support Staff:</td>
					<td class="displayText"><input type='text' id='support_staff_total' class='requiredInputBox' style='width:50px;' /></td>
				</tr>
				
				<tr>
					<td class="displayHeaderTitle" valign="top"># Students:</td>
					<td class="displayText"><input type='text' id='students_total' class='requiredInputBox' style='width:50px;' /></td>
				</tr>
				
				<tr>
					<td colspan='2' class="displayHeaderTitle">Additional Comments:</td>
				</tr>
				<tr>
					<td colspan='2' class="displayHeaderTitle">
						<textarea id='additional_comments' style='width:300px;height:100px;'></textarea></td>
				</tr>
				
				<tr id='response_msg'>
					<td colspan='2' class='ui-corner-all' valign='middle'>Enter the total number off sick <br>for each group above.</td>
				</tr>
			</table>
		</div>
		
		<div id="change_school_totals_dialog" title="School Stats">
			<table cellspacing='3' cellpadding='3' border='0' align="center">
				
				<tr>
					<td class="displayHeaderTitle" valign="top"># Teachers:</td>
					<td class="displayText">
						<input type='text' id='school_teachers_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.numberTeachers}' />
					</td>
				</tr>
				
				<tr>
					<td class="displayHeaderTitle" valign="top"># Support Staff:</td>
					<td class="displayText">
						<input type='text' id='school_support_staff_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.numberSupportStaff}'  />
					</td>
				</tr>
				
				<tr>
					<td class="displayHeaderTitle" valign="top"># Students Total:</td>
					<td class="displayText">
						<input type='text' id='school_students_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.numberStudents}' />
					</td>
				</tr>
				
				<tr>
					<td class="displayHeaderTitle" valign="top"># Kindergarden Total:</td>
					<td class="displayText">
						<input type='text' id='grade_0_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade0Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 1 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_1_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade1Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top">#Grade 2 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_2_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade2Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 3 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_3_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade3Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 4 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_4_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade4Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 5 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_5_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade5Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 6 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_6_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade6Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 7 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_7_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade7Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 8 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_8_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade8Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 9 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_9_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade9Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 10 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_10_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade10Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 11 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_11_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade11Total}' />
					</td>
				</tr>
				<tr>
					<td class="displayHeaderTitle" valign="top"># Grade 12 Total:</td>
					<td class="displayText">
						<input type='text' id='grade_12_total' class='requiredInputBox' style='width:50px;' value='${SCHOOLBEAN.schoolStats.grade12Total}' />
					</td>
				</tr>
				
				<tr id='change_school_totals_response_msg'>
					<td colspan='2' class='ui-corner-all'>Enter school totals for each group above.</td>
				</tr>
				
			</table>
		</div>
		
	</body>
	
</html>
