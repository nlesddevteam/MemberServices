<%@ page language ="java" 
         session = "true"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/cidb.tld" prefix="cidb" %>

<html>
	<head>
		<title>Eastern School District - Critical Issues Database</title>
		<link href="../includes/css/cidb.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="../includes/js/menu.js"></script>
		<script language="JavaScript" src="../includes/js/CalendarPopup.js"></script>
		<script type="text/javascript">
			var reportdatepicker = null;
			
			function init_datepicker(){
		    reportdatepicker = new CalendarPopup(document.forms['frmImportReport'].elements['report_date']);
		    reportdatepicker.year_scroll = true;
		    reportdatepicker.time_comp = false;
	    }
		</script>
	</head>
	
	<body onload="init_datepicker();">
	
		<esd:SecurityCheck permissions="CIDB-ADMIN-VIEW" />
		
		<table width="780" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#FFFFFF" style="border: thin solid #000000;">
			<tr bgcolor="#000000">
				<td>
					<table align="center" width="100%" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td><div align="left" class="toptext"></div></td>
							<td><div align="right" class="toptext"><a href="index.jsp" class="topmenu">Home</a>&nbsp;</div></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr valign="top">
				<td><img src="../includes/images/header.gif" alt="Critical Issues Database" width="780" height="97" border="0"></td>
			</tr>
			<tr valign="top">
				<td valign="top" style='height:400px;'>
					<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" class="maintable">
						<tr>
							<td style='padding-top:10px;padding-bottom:50px;'>
								<!-- Mainbody content here -->
		
								<table align="center" width="90%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="header1">Import Report</td></tr>
								</table>
								<form id="frmImportReport" action="sendSurveyInvitation.html" method="post" ENCTYPE="multipart/form-data">
									<input type="hidden" name="op" value="import" />
									
									<table align="center" width="75%" cellspacing="2" cellpadding="2" border="0">
										<tr>
                    	<td colspan="2" align="center" valign="middle" class="requiredText"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                    </tr>
										<%if(request.getAttribute("msg")!=null){%>
                    	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
                        <td colspan="2" align="center">
                        	<%=(String)request.getAttribute("msg")%>
                        </td>
                      </tr>
                    <%}%>
                    
                    <tr style="height:18px;">
                      <td class="label" valign="top"  align="right"><span class="requiredStar">*&nbsp;</span>Report Date:</td>
                      <td valign="top">
                        <table cellpadding="0" cellspacing="0">
                          <tr>
                            <td><input class="requiredinput_date" type="text" name="report_date" id="report_date" style="height:19px;width:100px;" value="" readonly/></td>
                            <td>
                              <img class="requiredinput_popup_cal" src="../includes/images/cal_popup_01.gif" alt="choose date"
                                  onmouseover="this.src='../includes/images/cal_popup_02.gif';"
                                  onmouseout="this.src='../includes/images/cal_popup_01.gif';"
                                  onclick="reportdatepicker.popup();"><br>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    
                    <tr>
                    	<td class="label" align="right"><span class="requiredStar">*&nbsp;</span>School:</td>
                    	<td><cidb:Schools id='school_id' style="width:250px;" cls="requiredInputBox" /></td>
                    </tr>
                    
                    <tr>
                    	<td class="label" align="right"><span class="requiredStar">*&nbsp;</span>Report Type:</td>
                    	<td><cidb:ReportTypeList id='report_type' style="width:250px;" cls="requiredInputBox" /></td>
                    </tr>
                    
                    <tr>
                    	<td class="label" align="right"><span class="requiredStar">*&nbsp;</span>Report File (PDF):</td>
                    	<td><input type="file" id='report_file' name='report_file' style="width:250px;" class="requiredInputBox" /></td>
                    </tr>
                    
                    <tr>
                    	<td colspan="2" align="center"><a href="" onclick="document.forms[0].submit(); return false;" onMouseOver="dsgo('b13')" onMouseOut="dsleave('b13')"><img src="../includes/images/imprep-off.jpg" alt="Import a Report" name="b13" id="b13" width="75" height="68" border="0"></a></td>
                    </tr>
                                    
									</table>
								</form>
								<br>&nbsp;<br>
			
								<!--End Mainbody -->
								<br>&nbsp;<br>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2009 Eastern School District. All Rights Reserved.</div></td>
			</tr>
		</table>

	</body>
	
</html>
