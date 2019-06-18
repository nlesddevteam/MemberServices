<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,com.awsd.security.*,
                 com.awsd.strike.*,com.awsd.school.*,com.awsd.personnel.*"
         isThreadSafe="false"%>

<%!
  User usr = null;
  SchoolStrikeGroup group = null;
  DailySchoolStrikeInfoHistory history = null;
  DailySchoolStrikeInfo info = null;
  School school = null;
  Iterator h_iter = null;
  SimpleDateFormat sdf = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("STRIKECENTRAL-ADMINVIEW")
      || usr.getUserPermissions().containsKey("STRIKECENTRAL-PRINCIPAL-ADMINVIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
  school = (School) request.getAttribute("School");
  group = (SchoolStrikeGroup) request.getAttribute("SchoolStrikeGroup");
  history = (DailySchoolStrikeInfoHistory) request.getAttribute("DailySchoolStrikeInfoHistory");
  
  h_iter = history.iterator();

  sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
%>


<html>
<head>
<title>Eastern School District - School Strike History</title>
<link rel="stylesheet" href="http://www.esdnl.ca/scripts/weathercentral.css">
<link rel="stylesheet" href="http://www.esdnl.ca/scripts/memberservices-new.css">
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" >
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="100%" align="left" valign="top">
							<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
								<tr>
									<td width="100%" align="left" valign="top">
                    <br>
										<span class="boldBlack11pxLower">&nbsp;School Strike History</span><BR>
                    <hr noshade size="1" width="100%"><BR>
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" colspan="5" style="background-color: #4791C5;">
                            <img src="http://www.esdnl.ca/images/spacer.gif" width="1" height="1"><BR>
                          </td>
                        </tr>      
                        <tr height="25" style="background-color: #DBEAF5;">
                          <td width="100%" colspan="4" align="left" valign="middle">
                            &nbsp;<span class='boldBlack11pxLower'><%=school.getSchoolName()%></span>
                          </td>
                        </tr>
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
                            <img src="http://www.esdnl.ca/images/spacer.gif" width="1" height="1"><BR>
                          </td>
                        </tr>
                        <tr style="padding-left:5px">
                            <td width="100%" colspan="4"><span class="boldBlack10pxTitle">Principal:</span>&nbsp;<span class="normalGrey10pxText"><%=school.getSchoolPrincipal().getFullNameReverse()%></span></td>
                        </tr>
                        <tr style="padding-left:5px">
                          <%if(group != null){%>
                            <td width="100%" colspan="4"><span class="boldBlack10pxTitle">Strike Liason:</span>&nbsp;<span class="normalGrey10pxText"><%=group.getGroupCoordinator().getFullNameReverse()%></span></td>
                          <%}else{%>
                            <td width="100%" colspan="4"><span class="boldBlack10pxTitle">Strike Liason:</span>&nbsp;<span class="normalGrey10pxText">Not Assigned</span></td>
                          <%}%>
                        </tr>
                        <tr><td width="100%" colspan="4"><br></td></tr>
                        <%while((h_iter != null) && h_iter.hasNext()){
                            info = (DailySchoolStrikeInfo) h_iter.next();
                        %>
                              <tr style="padding-left:5px"><td width="100%" align="left" colspan="4"><span class="boldGrey12pxText"><%=sdf.format(info.getInfoDate())%></span></td></tr>
                              <tr style="padding-left:15px">
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Picketers</span></td>
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Picket Line Incidents</span></td>
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Student Attendance</span></td>
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Student Support Services Issues</span></td>
                              </tr>
                              <tr style="padding-left:15px">
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=info.getNumberPicketers()%></span></td>
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getPicketLineIncidences()!=null)?info.getPicketLineIncidences():"&nbsp;"%></span></td>
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getStudentAttendance()!=null)?info.getStudentAttendance():"&nbsp;"%></span></td>
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getStudentSupportServicesIssues()!=null)?info.getStudentSupportServicesIssues():"&nbsp;"%></span></td>
                              </tr>
                              <tr style="padding-left:15px">
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Essential Worker(s)</span></td>
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Essential Worker Issues</span></td>
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Transportation Issues</span></td>
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Irregular Occurrences</span></td>
                              </tr>
                              <tr style="padding-left:15px">
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getEssentialWorkersNames()!=null)?info.getEssentialWorkersNames():"&nbsp;"%></span></td>
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getEssentialWorkersIssues()!=null)?info.getEssentialWorkersIssues():"&nbsp;"%></span></td>
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getTransportationIssues()!=null)?info.getTransportationIssues():"&nbsp;"%></span></td>
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getIrregularOccurrences()!=null)?info.getIrregularOccurrences():"&nbsp;"%></span></td>
                              </tr>
                              <tr style="padding-left:15px">
                                <td width="25%" valign="bottom"><span class="boldBlack10pxTitle">Building Safety &amp; Sanitation Issues</span></td>
                                <td width="75%" colspan="3" valign="bottom"><span class="boldBlack10pxTitle">Last Update</span></td>
                              </tr>
                              <tr style="padding-left:15px">
                                <td valign="top" width="25%"><span class="normalGrey10pxText"><%=(info.getBuildingSaftySanitationIssues()!=null)?info.getBuildingSaftySanitationIssues():"&nbsp;"%></span></td>
                                <td valign="top" width="75%" colspan="3"><span class="normalGrey10pxText"><%=(info.getInfoLastUpdated()!=null)?info.getInfoLastUpdated():"&nbsp;"%></span></td>
                              </tr>
                              <%if(h_iter.hasNext()){%>
                                <tr>
                                  <td width="100%" colspan="5"><hr color="#C1CDD8" width="100%" size="1" noshade style="border:dashed;"></td>
                                </tr>
                              <%}%>
                          <%}%>
                        <tr><td><br></td></tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
    <tr>
      <td width="100%" align="center"><a class="medBlueLink" href="javascript:window.location.href=document.referrer;">Back</a></td>
    </tr>
	</table>
</body>
</html>
