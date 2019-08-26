<%@ page language ="java" 
         session = "true"
         import = "java.util.*,java.text.DateFormat,
         					 com.nlesd.antibullyingpledge.bean.*,
         					 com.nlesd.antibullyingpledge.dao.*,com.esdnl.util.*"
         isThreadSafe="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>
<%
	//SurveyBean[] surveys = SurveyManager.getSuveryBeans();
	List<AntiBullyingPledgeBean> pledges = AntiBullyingPledgeManager.getRandomPledges(4);
	DateFormat fullDF = DateFormat.getDateInstance(DateFormat.FULL);
	Date today = new Date();
%>
<c:set var='pledges' value='<%=pledges%>' />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>NLESD - Bullying Pledge Admin</title>
	<script language="Javascript" src="../Personnel/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
			});
	</script>
	</head>
	<body>
		<table width="780" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
				<td width="*" height="400" valign="top">
					<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>
								<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="headertitle">Random Pledge Listing for <%=fullDF.format(today)%></td></tr>
								</table>
								<table align="center" width="100%" cellspacing="1"  cellpadding="1" border="0" id="showpledges">
									<tr>
										<th>First Name</th>
										<th>Last Name</th>
										<th>Grade</th>
										<th>School</th>
										<th>Email</th>
										<th></th>
									</tr>
									<c:choose>
										<c:when test='${ fn:length(pledges) gt 0 }'>
											<c:forEach items="${pledges}" var="pledge">
												<tr  id='${pledge.pk}'>
													<td ><img src="includes/img/${pledge.fkSchool}.jpg"></td>
													<td >${pledge.firstName}</td>
													<td >${pledge.lastName}</td>
													<td >${pledge.gradeLevel}</td>
													<td >${pledge.schoolName}</td>
													<td >${pledge.email}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td colspan='7'>No pledges submitted today.</td></tr>
										</c:otherwise>
									</c:choose>
								</table>
									<br>&nbsp;<br>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr bgcolor="#000000">
				<td colspan="2"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>