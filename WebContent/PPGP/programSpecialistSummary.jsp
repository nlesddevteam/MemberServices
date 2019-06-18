<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,
                java.text.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>
       
<esd:SecurityCheck permissions='PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST' />

<%
  User usr = (User) session.getAttribute("usr");
  Schools schools = new Schools();
  Personnel principal = null;
  int colorcnt = 0;
%>
<html>
	<head>
		<title>Principals Summary</title>
		<link rel="stylesheet" href="css/summary.css">
	</head>

	<body topmargin="10" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
	
		<table align="center" width="60%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="33%" valign="top" align="left">
					<a href='viewGrowthPlanProgramSpecialistSummary.html'><img src="images/progspeclpsummary.png" border='0' /></a><BR />
				</td>
				<td width="33%" valign="middle" align="left">
				  <table align="center">
				    <tr>
				      <td>
				        <b>Name:</b>
				      </td>
				      <td>
				        <%=usr.getPersonnel().getFullName()%>
				      </td>
				    </tr>
				    <tr>
				      <td>
				        <b>Date:</b>
				      </td>
				      <td>
				        <%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%><BR>
				      </td>
				    </tr>
				  </table>
				</td>
				<td width="*" align="right">
				  <table align='right' style="border-collapse: collapse; border:solid 0px #FFCC00;" cellpadding='10' cellspacing='0'>
				    <tr>
				      <td align="center" style="border-right:solid 1px #E0E0E0;" >
				        <a href="printProgramSpecialistSummary.jsp"><img src="images/print-off.png" border="0" alt="Print Program Specialist School Summary Sheets" /></a>
				      </td>
				      <td align="center" style="border-left:solid 1px #E0E0E0;">
				        <a href="searchPGP.html"><img src="images/search-off.png" border="0" alt="PGP Search" /></a>
				      </td>
				    </tr>
				  </table>
				</td>
			</tr>
		</table>
	
		<table align="center" width="60%" cellpadding="5" cellspacing="0" border="0">
			<tr>
				<td width="30%" bgcolor="#0066CC">&nbsp;</td>
				<td  width="30%" bgcolor="#0066CC" valign="middle" >
					<span class="title2">School Name</span><BR>
				</td>
				<td  bgcolor="#0066CC" valign="middle" >
					<span class="title2">Summary</span><BR>
				</td>
			</tr>
			<% for(School school : schools) {
			    principal = school.getSchoolPrincipal();
			%>  <tr>
			      <td bgcolor="<%=((colorcnt%2)==0)?"#F4F4F4":"#E1E1E1"%>">&nbsp;</td>
			      <td bgcolor="<%=((colorcnt%2)==0)?"#F4F4F4":"#E1E1E1"%>" valign="middle" align="left">
			        <span class="text"><%=school.getSchoolName()%></span>
			      </td>
			      <td bgcolor="<%=((colorcnt%2)==0)?"#F4F4F4":"#E1E1E1"%>" valign="middle" align="left">
			        <span class="text">
			        <% if(principal!=null) { %>
			          <span class="text"><a href="viewGrowthPlanPrincipalSummary.html?pid=<%=principal.getPersonnelID()%>" title="Click here to view detailed summary">view</a></span>
			        <% } else { %>
			          <FONT COLOR="#FF0000">No Principal On Record.</FONT>
			        <% } %>
			        </span>
			      </td>
			    </tr>
			<%   colorcnt++;
			    }
			%>
		</table>
	
		<table height="5" width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="100%" valign="bottom" bgcolor="#FFCC00">
					<img src="images/spacer.gif" width="1" height="5"><BR>
				</td>
			</tr>
		</table>
	
	</body>
</html>