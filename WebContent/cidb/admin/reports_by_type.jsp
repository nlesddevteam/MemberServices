<%@ page language ="java" 
         session = "true"
         import="com.esdnl.criticalissues.bean.*,
                 com.esdnl.criticalissues.constant.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/cidb.tld" prefix="cidb" %>

<%
	ReportTypeConstant type = (ReportTypeConstant) request.getAttribute("REPORT_TYPE");
	ReportBean[] reports = (ReportBean[]) request.getAttribute("REPORT_BEANS");
%>


<html>
	<head>
		<title>Eastern School District - Critical Issues Database</title>
		<link href="../includes/css/cidb.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../includes/js/menu.js"></script>
		<script type="text/javascript">
			var sel_row = -1;
			function rowSelected(id){
				var row = document.getElementById('row_' + id);
				if(row){
					row.className = 'selected';
					
					if(sel_row >= 0){
						row = document.getElementById('row_' + sel_row);
						if(row)
							row.className = 'color' + (sel_row % 2);
					}
					
					sel_row = id;
				}
			}
			
			function doCommand(cmd){
				var check = true;
				
				var frm = document.forms[0];
				
				if(sel_row > -1){
					if(cmd == 1)
						frm.action = 'addReportActionItem.html';
					else if(cmd == 2){
						frm.action = 'deleteReport.html';
						check = confirm('Are you sure you want delete the report?');
					}
					
					if(check)
						frm.submit();
				}
				else{
					var mb = document.getElementById('msgbox');
				 	if(mb)
				 		mb.innerHTML = 'Please select a report.';
				}	
			}
			
			function viewReport(fn) {
				var w = 640;
				var h = 480;
				
			  var winl = (screen.width-w)/2;
			  var wint = (screen.height - h - 25 )/2;
			
			  window.open('/cidb/admin/reports/'+fn,'CIDB_REPORT',"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars=1,resizable=0,width="+w+",height="+h);
			}
		</script>
	</head>
	
	<body>
	
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
									<tr><td class="header1"><%=type.getDescription() %></td></tr>
								</table>
								<br>
								<form id="frmReportOperationController" action="" method="post">
									<table width="75%" cellpadding="3" cellspacing="0" border="0" align="center" style="border:solid 1px #333333;">
	                	
	                  <tr>
	                  	<th colspan='2' class='displayColumnHeader' style="border-left:none;">Report Date</th>
	                    <th width="50%" class='displayColumnHeader'>School</th>
	                    <th width="*" class='displayColumnHeader'># Outstanding Action Items</th>
	                  </tr>
	                  <%if(reports.length > 0){
	                    for(int i=0; i < reports.length; i++){%>
	                      <tr id="row_<%=i%>" class='color<%=(i%2)%>'>
	                        <td width="3%" align="center" class='displayColumnSelect'>
	                        	<input type="radio" name="report_id" value="<%= reports[i].getReportId() %>" onclick="rowSelected(<%=i%>);"/>
	                        </td>
	                        <td width="20%" class='displayColumn'><%=reports[i].getReportDateFormatted()%></td>
	                        <td width="50%" class='displayColumn'><%=reports[i].getSchool().getSchoolName()%></td>
	                        <td width="*" align="center" class='displayColumn'><%=reports[i].getOutstandingItems()%></td>
	                    	</tr>
	                  	<%}%>
	                  <%}else{%>
	                   	<tr><td colspan='3' class="displayText">No reports on file.</td></tr>
	                  <%}%>
	                </table>
	                <br>
	                <table align="center"  cellspacing="2" cellpadding="2" border="0">
										<tr style='padding-top:10px;'>
											<td><a href="javascript:doCommand(1);" onMouseOver="dsgo('b7')" onMouseOut="dsleave('b7')"><img src="../includes/images/addai-off.jpg" alt="Add Action Report" name="b7" id="b7" width="75" height="68" border="0"></a></td>
											<!-- 
											<td><a href="linkone.jsp" onMouseOver="dsgo('b8')" onMouseOut="dsleave('b8')"><img src="../includes/images/deleteai-off.jpg" alt="Delete Action Item" name="b8" id="b8" width="75" height="68" border="0"></a></td>
											<td><a href="linkone.jsp" onMouseOver="dsgo('b12')" onMouseOut="dsleave('b12')"><img src="../includes/images/viewai-off.jpg" alt="View Action Item" name="b12" id="b12" width="75" height="68" border="0"></a></td>
											-->
											<td><a href="linkone.jsp" onMouseOver="dsgo('b11')" onMouseOut="dsleave('b11')"><img src="../includes/images/listai-off.jpg" alt="List Action Item" name="b11" id="b11" width="75" height="68" border="0"></a></td>
											<td><a href="import_report.jsp" onMouseOver="dsgo('b13')" onMouseOut="dsleave('b13')"><img src="../includes/images/imprep-off.jpg" alt="Import a Report" name="b13" id="b13" width="75" height="68" border="0"></a></td>
											<td><a href="javascript:;" target='_blank' onMouseOver="dsgo('b9')" onMouseOut="dsleave('b9')"><img src="../includes/images/viewreport-off.jpg" alt="View Report" name="b9" id="b9" width="75" height="68" border="0"></a></td>
											<td><a href="javascript:doCommand(2);" onMouseOver="dsgo('b10')" onMouseOut="dsleave('b10')"><img src="../includes/images/deleterep-off.jpg" alt="Delete Report" name="b10" id="b10" width="75" height="68" border="0"></a></td>
										</tr>
									</table>
                	<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" style='display:'>
	                	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
	                  	<td align="center" id="msgbox">
	                    	<%=((request.getAttribute("msg")!=null)?request.getAttribute("msg"):"&nbsp;")%>
	                   	</td>
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
