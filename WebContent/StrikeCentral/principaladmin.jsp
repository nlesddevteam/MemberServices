<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         isThreadSafe="false"
         import="java.sql.*,
                 java.util.*,com.awsd.personnel.*,
                 com.awsd.strike.*,com.awsd.security.*,com.awsd.school.*,
                 java.text.*"%>
<%!
  User usr = null;
  SchoolStrikeGroup group = null;
  DailySchoolStrikeInfo info = null;
  School school = null;
  SimpleDateFormat sdf = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("STRIKECENTRAL-PRINCIPAL-ADMINVIEW")))
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
  info = (DailySchoolStrikeInfo) request.getAttribute("DailySchoolStrikeInfo");

  sdf = new SimpleDateFormat("dd-MMM-yyyy");
%>

<html>
<head>
<title></title>
<style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>
<script language="JavaScript">
	function toggle(target)
		{
			obj=(document.all) ? document.all[target] : document.getElementById(target);
			obj.style.display=(obj.style.display=='none') ? 'inline' : 'none';
		}
		
	function toggleHeight(target2)
		{
			obj=(parent.document.all) ? parent.document.all[target2] : parent.document.getElementById(target2);
			obj.style.height=(obj.style.height=='26') ? '208' : '26';
		}

  function submitenter(myfield,e)
    {
      var keycode;
      if (window.event) keycode = window.event.keyCode;
      else if (e) keycode = e.which;
      else return true;

      if (keycode == 13)
      {
        return false;
      }
      else if(myfield.value.length > 150)
      {
        return false;
      }
      else
        return true;
    }
</script>
</head>
<body>
  <form name="dailyschoolstrikestatus" method="post" action="updateDailySchoolStrikeInfo.html">
	<table width="100%" cellpadding="0" cellspacing="0" border="0" >	
		<tr>
			<td width="100%" height="26" align="left" valign="middle" style="background-image: url('/MemberServices/MemberAdmin/images/container_title_bg.jpg');">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="100%" align="left" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="4" height="1"><span class="containerTitleWhite">Job Action Admin</span><BR>
						</td>
						<td width="50" align="right" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/minimize_icon.gif" onClick="toggle('bodyContainer');"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><img src="/MemberServices/MemberAdmin/images/close_icon.gif" onClick="document.location='../memberServices.html';" alt="Close" style="cursor: hand;"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><BR>
						</td>
					</tr>											
				</table>										
			</td>
		</tr>		
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">				
				<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
					<tr>
						<td width="100%" align="left" valign="top">
							<span class="boldBlack11pxLower">Job Action Update</span><BR>
							<hr noshade size="1" width="100%">
							<span class="boldBlack10pxTitle">School Name:</span>&nbsp;<span class="normalBlack10pxText"><%=school.getSchoolName()%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="boldBlack10pxTitle">Principal:</span>&nbsp;<span class="normalBlack10pxText"><%=school.getSchoolPrincipal().getFullNameReverse()%></span><BR>
              <%if(group != null){%>
                <span class="boldBlack10pxTitle">Strike Liason:</span>&nbsp;<span class="normalBlack10pxText"><%=group.getGroupCoordinator().getFullNameReverse()%></span><BR>
              <%}else{%>
                <span class="boldBlack10pxTitle">Strike Liason:</span>&nbsp;<span class="normalBlack10pxText">Not Assigned</span><BR>
              <%}%>
              
              <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="5"><BR>
              <span class="boldBlack10pxTitle">Date:</span>&nbsp;&nbsp<span class="normalBlack10pxText"><%=(info != null)?sdf.format(info.getInfoDate()):sdf.format(Calendar.getInstance().getTime())%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <span class="boldBlack10pxTitle">Last Update:</span>&nbsp;&nbsp<span class="normalBlack10pxText"><%=((info != null)&&(info.getInfoLastUpdated()!=null))?info.getInfoLastUpdated():"<font color='#FF0000'><b>None Today</b></font>"%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="medBlueLink" style="text-decoration:none;" href="viewSchoolStrikeHistory.html?school_id=<%=school.getSchoolID()%>">View History</a><BR>
							<img src="/MemberServices/MemberAdmin/images/cccccc_line.gif" width="100%" height="1"><BR>
              <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="8"><BR><BR>
              
              <span class="boldBlack10pxTitle" valign="middle">Number Picketers:</span>&nbsp;&nbsp;
              <input type="text" name="picketers" cols="50" value="<%=(info != null)?info.getNumberPicketers():0%>">

              <BR><BR>
              <span class="boldBlack10pxTitle">Picket Line Incidents:</span><BR>
              <textarea name="pket_line_incidents" rows="3" cols="50"><%=((info != null) &&(info.getPicketLineIncidences()!=null))?info.getPicketLineIncidences():""%></textarea>

              <BR><BR>
              <span class="boldBlack10pxTitle">Student Attendance Issues:</span><BR>
              <textarea name="student_attd" rows="3" cols="50"><%=((info != null) &&(info.getStudentAttendance()!=null))?info.getStudentAttendance():""%></textarea>

              <BR><BR>
              <span class="boldBlack10pxTitle">Student Support Services Issues:</span><BR>
              <textarea name="stud_supp_services_issues" rows="3" cols="50"><%=((info != null) &&(info.getStudentSupportServicesIssues()!=null))?info.getStudentSupportServicesIssues():""%></textarea>

              <BR><BR>
              <span class="boldBlack10pxTitle">Essential Worker Name(s):</span><BR>
              <textarea name="essential_workers" rows="3" cols="50"><%=((info != null) &&(info.getEssentialWorkersNames()!=null))?info.getEssentialWorkersNames():""%></textarea>

              <BR><BR>
              <span class="boldBlack10pxTitle">Essential Worker Issues:</span><BR>
              <textarea name="essential_workers_issues" rows="3" cols="50"><%=((info != null) &&(info.getEssentialWorkersIssues()!=null))?info.getEssentialWorkersIssues():""%></textarea>

              <BR><BR>
              <span class="boldBlack10pxTitle">Transportation Issues:</span><BR>
              <textarea name="transportation_issues" rows="3" cols="50"><%=((info != null) &&(info.getTransportationIssues()!=null))?info.getTransportationIssues():""%></textarea>

              <BR><BR>
              <span class="boldBlack10pxTitle">Irregular Occurrences:</span><BR>
              <textarea name="irregular_occurrences" rows="3" cols="50"><%=((info != null) &&(info.getIrregularOccurrences()!=null))?info.getIrregularOccurrences():""%></textarea>

              <BR><BR>
              <span class="boldBlack10pxTitle">Building Safety/Sanitation Issues:</span><BR>
              <textarea name="bldg_safy_sanitation_issues" rows="3" cols="50"><%=((info != null) &&(info.getBuildingSaftySanitationIssues()!=null))?info.getBuildingSaftySanitationIssues():""%></textarea>
						</td>
					</tr>
					<tr>
					<td width="100%" align="right" valign="middle">
						<img src="/MemberServices/MemberAdmin/images/ok_button_01.gif" border="0"
                 onClick="document.dailyschoolstrikestatus.submit();"
                 onMousedown="src='/MemberServices/MemberAdmin/images/ok_button_02.gif';" 
                 onMouseup="src='/MemberServices/MemberAdmin/images/ok_button_01.gif';">&nbsp;&nbsp;
            <img src="/MemberServices/MemberAdmin/images/cancel_button_01.gif" border="0" 
                 onClick="document.location='../memberServices.html';" 
                 onMousedown="src='/MemberServices/MemberAdmin/images/cancel_button_02.gif';" 
                 onMouseup="src='/MemberServices/MemberAdmin/images/cancel_button_01.gif';"><BR><BR>
					</td>
				</tr>
				</table>
			</td>
		</tr>					
	</table>
</form>
</body>

</html>
