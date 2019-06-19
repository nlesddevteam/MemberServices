<%@ page language ="java" 
         session = "true"
         import = "java.util.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*"
         isThreadSafe="false"%><%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>Fund 3 MANAGEMENT SYSTEM</title>
				<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		 <link rel="stylesheet" href="includes/css/jquery-ui.css" />	
		<script type="text/javascript" src="includes/js/jquery-1.10.2.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		<script src="includes/js/jquery.validate.js" type="text/javascript"></script>
		<script src="includes/js/tableExport.js" type="text/javascript"></script>
		<script src="includes/js/jquery.base64.js" type="text/javascript"></script>
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
		<script type="text/javascript">
			function printData()
			{
				window.print();
			}
		</script>

			
	</head>

	<body>
			
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
			<td align="center">
			<jsp:include page="header.jsp" />
			<br />
			<br />
			</td>
			</tr>
			<tr>
				<td align="center">
					<table width="95%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td align="center">
					<table width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
					<tr><td  align="center" class='fund3header' colspan='2'>VIEW MY REPORTS</td></tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center">
					<table cellspacing="1" style="font-size: 11px;" cellpadding="1" border="0" class='datatable' id="sresults" width="100%">
						<thead>
							<tr>
								<th class='fund3tableheader'>Report Title</th>
								<th class='fund3tableheader'>Date Created</th>
								<th class='fund3tableheader'>Last Used</th>
								<th class='fund3tableheader'></th>
								<th class='fund3tableheader'></th>
								<th class='fund3tableheader'></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
                                 	<c:when test='${fn:length(reports) gt 0}'>
                                		<c:forEach items='${reports}' var='g'>
                                			<tr class='guide-info'>
                                				<td class="displayText1" >${g.reportTitle}</td>
                                				<td class="displayText1" >${g.dateCreatedFormatted}</td>
                                				<td class="displayText1" >${g.dateLastUsedFormatted}</td>
                                      			<td class="displayText1" ><a href='runCustomReport.html?reportid=${g.id}'>Run Report</a></td>
                                      			<td class="displayText1" ><a href='editCustomReport.html?reportid=${g.id}'>Edit Report</a></td>
                                      			<td class="displayText1" ><a href='#' onclick="ajaxDeleteReport(${g.id});$(this).closest ('tr').remove ();">Remove Report</a></td>
                                   			</tr>
                                		</c:forEach>
                                	</c:when>
								<c:otherwise>
									<tr><td colspan='6'>NO REPORTS FOUND</td></tr>
								</c:otherwise>
							</c:choose>
							<tr><td colspan="6"><hr size="1" /></td></tr>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			</td>
			</tr>
				<jsp:include page="fund3links.jsp" />
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>